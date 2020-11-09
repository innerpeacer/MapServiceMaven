package cn.platalk.map.vectortile.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.impl.map.TYLngLat;
import cn.platalk.map.entity.base.impl.map.TYLocalPoint;
import cn.platalk.map.entity.base.impl.map.TYMapExtent;
import cn.platalk.map.entity.base.impl.map.TYMapInfo;
import cn.platalk.map.entity.base.map.*;
import com.vividsolutions.jts.geom.Envelope;

import cn.platalk.common.TYCoordProjection;
import cn.platalk.map.vectortile.cbm.json.TYCBMBuilder;
import cn.platalk.map.vectortile.cbm.pbf.TYCBMPbfBuilder;
import cn.platalk.map.vectortile.pbf.VectorTile;
import cn.platalk.utils.third.TYFileUtils;

public class TYVectorTileBuilder {

	String buildingID;
	TYICity city;
	TYIBuilding building;
	Envelope buildingEnvelope;
	List<TYIMapInfo> mapInfos;
	List<TYIMapDataFeatureRecord> mapDataRecords;
	List<TYIFillSymbolRecord> fillSymbols;
	List<TYIIconSymbolRecord> iconSymbols;
	List<TYIIconTextSymbolRecord> iconTextSymbols;
	TYGeometrySet geometrySet;
	TYIMvtEncoder mvtEncoder;
	private int tileCount;
	private int emptyTileCount;
	private boolean isForNative = false;

	// private int tileIndex;

	public TYVectorTileBuilder(String buildingID) {
		this.buildingID = buildingID;
		geometrySet = new TYGeometrySet(buildingID);
	}

	public void addData(TYICity city, TYIBuilding building, List<TYIMapInfo> mapInfoList,
			List<TYIMapDataFeatureRecord> mapDataRecords, List<TYIFillSymbolRecord> fillSymbolList,
			List<TYIIconSymbolRecord> iconSymbolList) {
		addData(city, building, mapInfoList, mapDataRecords, fillSymbolList, iconSymbolList,
				new ArrayList<TYIIconTextSymbolRecord>());
	}

	public void addData(TYICity city, TYIBuilding building, List<TYIMapInfo> mapInfoList,
			List<TYIMapDataFeatureRecord> mapDataRecords, List<TYIFillSymbolRecord> fillSymbolList,
			List<TYIIconSymbolRecord> iconSymbolList, List<TYIIconTextSymbolRecord> iconTextSymbolList) {
		System.out.println(mapDataRecords.size() + " records");
		this.city = city;
		this.building = building;

		double xmin = building.getBuildingExtent().getXmin();
		double ymin = building.getBuildingExtent().getYmin();
		TYLngLat sw = TYCoordProjection.mercatorToLngLat(new TYLocalPoint(xmin, ymin));

		double xmax = building.getBuildingExtent().getXmax();
		double ymax = building.getBuildingExtent().getYmax();
		TYLngLat ne = TYCoordProjection.mercatorToLngLat(new TYLocalPoint(xmax, ymax));
		buildingEnvelope = new Envelope(sw.getLng(), ne.getLng(), sw.getLat(), ne.getLat());

		this.mapInfos = new ArrayList<>();
		this.mapInfos.addAll(mapInfoList);

		this.mapDataRecords = new ArrayList<>();
		this.mapDataRecords.addAll(mapDataRecords);

		this.fillSymbols = new ArrayList<>();
		this.fillSymbols.addAll(fillSymbolList);
		this.iconSymbols = new ArrayList<>();
		this.iconSymbols.addAll(iconSymbolList);
		this.iconTextSymbols = new ArrayList<>();
		this.iconTextSymbols.addAll(iconTextSymbolList);

		geometrySet.addData(mapInfoList, mapDataRecords, fillSymbolList, iconSymbolList);
		geometrySet.processData();
	}

	public void generateCBM() throws Exception {
		System.out.println("generateCBM");

		if (TYVectorTileSettings.GetTileRoot() == null) {
			throw new Exception(
					"Error: TileRoot Cannot be null, please set TileRoot By calling TYVectorTileSettings.SetTileRoot()");
		}

		TYCBMBuilder.generateCBMJson(city, building, mapInfos, mapDataRecords, fillSymbols, iconSymbols,
				iconTextSymbols);
	}

	public void generateCBMPbf() throws Exception {
		System.out.println("generateCBMPbf");
		if (TYVectorTileSettings.GetTileRoot() == null) {
			throw new Exception(
					"Error: TileRoot Cannot be null, please set TileRoot By calling TYVectorTileSettings.SetTileRoot()");
		}
		TYCBMPbfBuilder.generateCBMPbf(city, building, mapInfos, mapDataRecords, fillSymbols, iconTextSymbols);
	}

	public void buildTile(boolean isNative) throws Exception {
		System.out.println("buildTile");
		this.isForNative = isNative;

		if (TYVectorTileSettings.GetTileRoot() == null) {
			throw new Exception(
					"Error: TileRoot Cannot be null, please set TileRoot By calling TYVectorTileSettings.SetTileRoot()");
		}

		TYIMapInfo mapInfo = geometrySet.getMapInfos().get(0);
		// TYBrtBoundingBox bb = TYBrtBoundingBox.boundingBoxForMapInfo(mapInfo,
		// 0.2);

		TYMapInfo maxMapInfo = new TYMapInfo();
		maxMapInfo.setMapExtent(TYMapExtent.copyFrom(mapInfo.getMapExtent()));
		for (TYIMapInfo info : geometrySet.getMapInfos()) {
			maxMapInfo.getMapExtent().extendWith(info.getMapExtent());
		}
		maxMapInfo.setSize_x(maxMapInfo.getMapExtent().getXmax() - maxMapInfo.getMapExtent().getXmin());
		maxMapInfo.setSize_y(maxMapInfo.getMapExtent().getYmax() - maxMapInfo.getMapExtent().getYmin());
		// System.out.println(mapInfo);
		// System.out.println(maxMapInfo);

		mapInfo = maxMapInfo;
		TYBoundingBox bb = TYBoundingBox.boundingBoxForMapInfo(mapInfo, 0.2);
		TYTileCoord minTile = bb.getMaxCoveringTile();
		// System.out.println(bb);

		int startZoom = Math.max(TYVectorTileSettings.GetDefaultMinZoom(), minTile.zoom);
		int[] tileRange = bb.getTileRange(startZoom);

		tileCount = getTileCount(tileRange, startZoom);
		emptyTileCount = 0;
		// tileIndex = 0;
		System.out.println("Tile Number: " + tileCount);

		for (int x = tileRange[0]; x <= tileRange[1]; ++x) {
			for (int y = tileRange[2]; y <= tileRange[3]; ++y) {
				TYTileCoord tile = new TYTileCoord(startZoom, x, y);
				buildTilesForLevel(tile, mapInfo);
			}
		}

		System.out.println("Empty Tile Number: " + emptyTileCount);
		System.out.println("Not Empty Tile Number: " + (tileCount - emptyTileCount));
		System.out.println("Finish!");
	}

	private int getTileCount(int[] tileRange, int startZoom) {
		int count = 0;
		for (int x = tileRange[0]; x <= tileRange[1]; ++x) {
			for (int y = tileRange[2]; y <= tileRange[3]; ++y) {
				TYTileCoord tile = new TYTileCoord(startZoom, x, y);
				count += getSingleTileCount(tile);
			}
		}
		return count;
	}

	private int getSingleTileCount(TYTileCoord tile) {
		int count = 1;
		if (tile.zoom < TYVectorTileSettings.GetDefaultMaxZoom()) {
			List<TYTileCoord> children = tile.getChildren();
			for (TYTileCoord child : children) {
				count += getSingleTileCount(child);
			}
		}
		return count;
	}

	private void buildTilesForLevel(TYTileCoord tile, TYIMapInfo info) throws IOException {
		buildSingleTile(tile, info);
		if (tile.zoom < TYVectorTileSettings.GetDefaultMaxZoom()) {
			List<TYTileCoord> children = tile.getChildren();
			for (TYTileCoord child : children) {
				buildTilesForLevel(child, info);
			}
		}
	}

	boolean flag = true;

	private void buildSingleTile(TYTileCoord tile, TYIMapInfo info) throws IOException {
		// if (tile.zoom == 2) {
		// System.out.println("buildSingleTile: " + tile + ", "
		// + tile.getBoundingBox());
		// }
		// tileIndex++;
		// System.out.println("Tile: " + tileIndex + "/" + tileCount);

		TYBoundingBox bb = tile.getBoundingBox();
		final Envelope tileEnvelope = new Envelope(bb.west, bb.east, bb.north, bb.south);
		final Envelope clipEnvelope = new Envelope(tileEnvelope);

		// if (!buildingEnvelope.intersects(tileEnvelope)) {
		// emptyTileCount++;
		// return;
		// }

		double bufferWidth = tileEnvelope.getWidth() * 0.5;
		double bufferHeight = tileEnvelope.getWidth() * 0.5;
		clipEnvelope.expandBy(bufferWidth, bufferHeight);

		// final VectorTile.Tile mvt = TYBrtMvtEncoder.encodeBrtMvt("room",
		// geometrySet.roomList, DEFAULT_MVT_PARAMS, tileEnvelope,
		// clipEnvelope);
		mvtEncoder = getMvtEncoder();
		mvtEncoder.setForNative(isForNative);
		final VectorTile.Tile mvt = mvtEncoder.encodeBrtTile(geometrySet, TYVectorTileParams.DEFAULT_MVT_PARAMS,
				tileEnvelope, clipEnvelope, tile);
		if (mvt == null && isForNative) {
			emptyTileCount++;
			return;
		}

		String vectorTileRoot = TYVectorTileSettings.GetTileDir();
		String fileName = String.format("%s/%s/%d/%d/%d.pbf", vectorTileRoot, buildingID, tile.zoom, tile.x, tile.y);
		fileName = fileName.replace("/", File.separator);
		TYFileUtils.makeFolders(fileName);

		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(mvt.toByteArray());
		fos.close();
	}

	private TYIMvtEncoder getMvtEncoder() {
		if (TYVectorTileSettings.GetMvtVersion() == 1) {
			return new TYMvtEncoder_v1();
		}

		if (TYVectorTileSettings.GetMvtVersion() == 2) {
			return new TYMvtEncoder_v2();
		}
		return null;
	}
}
