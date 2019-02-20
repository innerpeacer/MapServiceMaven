package cn.platalk.brtmap.vectortile.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Envelope;

import cn.platalk.brtmap.vectortile.pbf.VectorTile;
import cn.platalk.map.entity.base.TYIBuilding;
import cn.platalk.map.entity.base.TYICity;
import cn.platalk.map.entity.base.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.TYIIconSymbolRecord;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.map.entity.base.TYIMapInfo;
import cn.platalk.map.entity.base.impl.TYMapInfo;

public class TYVectorTileBuilder {

	String buildingID;
	TYICity city;
	TYIBuilding building;
	List<TYIMapInfo> mapInfos;
	TYBrtGeometrySet geometrySet;
	TYIBrtMvtEncoder mvtEncoder;
	private int tileCount;

	// private int tileIndex;

	public TYVectorTileBuilder(String buildingID) {
		this.buildingID = buildingID;
		geometrySet = new TYBrtGeometrySet(buildingID);
	}

	public void addData(TYICity city, TYIBuilding building, List<TYIMapInfo> mapInfoList,
			List<TYIMapDataFeatureRecord> mapDataRecords, List<TYIFillSymbolRecord> fillSymbolList,
			List<TYIIconSymbolRecord> iconSymbolList) {
		System.out.println(mapDataRecords.size() + " records");
		this.city = city;
		this.building = building;
		this.mapInfos = new ArrayList<TYIMapInfo>();
		this.mapInfos.addAll(mapInfoList);
		geometrySet.addData(mapInfoList, mapDataRecords, fillSymbolList, iconSymbolList);
		geometrySet.processData();
	}

	public void buildTile() throws Exception {
		System.out.println("buildTile");

		if (TYVectorTileSettings.GetTileRoot() == null) {
			throw new Exception(
					"Error: TileRoot Cannot be null, please set TileRoot By calling TYVectorTileSettings.SetTileRoot()");
		}

		TYBrtCBMBuilder.generateCBMJson(city, building, mapInfos);

		TYIMapInfo mapInfo = geometrySet.getMapInfos().get(0);
		// TYBrtBoundingBox bb = TYBrtBoundingBox.boundingBoxForMapInfo(mapInfo,
		// 0.2);

		TYMapInfo maxMapInfo = new TYMapInfo();
		maxMapInfo.setXmax(mapInfo.getMapExtent().getXmax());
		maxMapInfo.setXmin(mapInfo.getMapExtent().getXmin());
		maxMapInfo.setYmax(mapInfo.getMapExtent().getYmax());
		maxMapInfo.setYmin(mapInfo.getMapExtent().getYmin());
		for (TYIMapInfo info : geometrySet.getMapInfos()) {
			maxMapInfo.setXmax(Math.max(maxMapInfo.getMapExtent().getXmax(), info.getMapExtent().getXmax()));
			maxMapInfo.setXmin(Math.min(maxMapInfo.getMapExtent().getXmin(), info.getMapExtent().getXmin()));
			maxMapInfo.setYmax(Math.max(maxMapInfo.getMapExtent().getYmax(), info.getMapExtent().getYmax()));
			maxMapInfo.setYmin(Math.min(maxMapInfo.getMapExtent().getYmin(), info.getMapExtent().getYmin()));
		}
		maxMapInfo.setSize_x(maxMapInfo.getMapExtent().getXmax() - maxMapInfo.getMapExtent().getXmin());
		maxMapInfo.setSize_y(maxMapInfo.getMapExtent().getYmax() - maxMapInfo.getMapExtent().getYmin());
		// System.out.println(mapInfo);
		// System.out.println(maxMapInfo);

		mapInfo = maxMapInfo;
		TYBrtBoundingBox bb = TYBrtBoundingBox.boundingBoxForMapInfo(mapInfo, 0.2);
		TYBrtTileCoord minTile = bb.getMaxCoveringTile();
		// System.out.println(bb);

		int startZoom = Math.max(TYVectorTileSettings.GetDefaultMinZoom(), minTile.zoom);
		int[] tileRange = bb.getTileRange(startZoom);

		tileCount = getTileCount(tileRange, startZoom);
		// tileIndex = 0;
		System.out.println("Tile Number: " + tileCount);

		for (int x = tileRange[0]; x <= tileRange[1]; ++x) {
			for (int y = tileRange[2]; y <= tileRange[3]; ++y) {
				TYBrtTileCoord tile = new TYBrtTileCoord(startZoom, x, y);
				buildTilesForLevel(tile, mapInfo);
			}
		}
		System.out.println("Finish!");
	}

	private int getTileCount(int[] tileRange, int startZoom) {
		int count = 0;
		for (int x = tileRange[0]; x <= tileRange[1]; ++x) {
			for (int y = tileRange[2]; y <= tileRange[3]; ++y) {
				TYBrtTileCoord tile = new TYBrtTileCoord(startZoom, x, y);
				count += getSingleTileCount(tile);
			}
		}
		return count;
	}

	private int getSingleTileCount(TYBrtTileCoord tile) {
		int count = 1;
		if (tile.zoom < TYVectorTileSettings.GetDefaultMaxZoom()) {
			List<TYBrtTileCoord> children = tile.getChildren();
			for (TYBrtTileCoord child : children) {
				count += getSingleTileCount(child);
			}
		}
		return count;
	}

	private void buildTilesForLevel(TYBrtTileCoord tile, TYIMapInfo info) throws IOException {
		buildSingleTile(tile, info);
		if (tile.zoom < TYVectorTileSettings.GetDefaultMaxZoom()) {
			List<TYBrtTileCoord> children = tile.getChildren();
			for (TYBrtTileCoord child : children) {
				buildTilesForLevel(child, info);
			}
		}
	}

	private void buildSingleTile(TYBrtTileCoord tile, TYIMapInfo info) throws IOException {
		// if (tile.zoom == 2) {
		// System.out.println("buildSingleTile: " + tile + ", "
		// + tile.getBoundingBox());
		// }
		// tileIndex++;
		// System.out.println("Tile: " + tileIndex + "/" + tileCount);

		TYBrtBoundingBox bb = tile.getBoundingBox();
		final Envelope tileEnvelope = new Envelope(bb.west, bb.east, bb.north, bb.south);
		final Envelope clipEnvelope = new Envelope(tileEnvelope);

		double bufferWidth = tileEnvelope.getWidth() * 0.5;
		double bufferHeight = tileEnvelope.getWidth() * 0.5;
		clipEnvelope.expandBy(bufferWidth, bufferHeight);

		// final VectorTile.Tile mvt = TYBrtMvtEncoder.encodeBrtMvt("room",
		// geometrySet.roomList, DEFAULT_MVT_PARAMS, tileEnvelope,
		// clipEnvelope);
		mvtEncoder = getMvtEncoder();
		final VectorTile.Tile mvt = mvtEncoder.encodeBrtTile(geometrySet, TYBrtVectorTileParams.DEFAULT_MVT_PARAMS,
				tileEnvelope, clipEnvelope, tile);
		String vectorTileRoot = TYVectorTileSettings.GetTileDir();
		String fileName = String.format("%s/%s/%d/%d/%d.pbf", vectorTileRoot, buildingID, tile.zoom, tile.x, tile.y);
		fileName = fileName.replace("/", File.separator);
		TYBrtFileUtils.makeFolders(fileName);

		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(mvt.toByteArray());
		fos.close();
	}

	private TYIBrtMvtEncoder getMvtEncoder() {
		if (TYVectorTileSettings.GetMvtVersion() == 1) {
			return new TYBrtMvtEncoder_v1();
		}

		if (TYVectorTileSettings.GetMvtVersion() == 2) {
			return new TYBrtMvtEncoder_v2();
		}
		return null;
	}
}
