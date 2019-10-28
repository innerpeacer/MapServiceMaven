package cn.platalk.map.vectortile.cbm.pbf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.platalk.core.pbf.cbm.TYCbmPbf.CBMPbf;
import cn.platalk.core.pbf.cbm.wrapper.TYCity2PbfUtils;
import cn.platalk.core.pbf.cbm.wrapper.TYSymbol2PbfUtils;
import cn.platalk.map.entity.base.TYIBuilding;
import cn.platalk.map.entity.base.TYICity;
import cn.platalk.map.entity.base.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.TYIIconTextSymbolRecord;
import cn.platalk.map.entity.base.TYIMapDataFeatureRecord;
import cn.platalk.map.entity.base.TYIMapInfo;
import cn.platalk.map.vectortile.builder.TYVectorTileSettings;
import cn.platalk.map.vectortile.cbm.json.TYSymbolExtractor;

public class TYCBMPbfBuilder {
	static String FILE_CBM_PBF = "%s.pbf";

	private static String getCBMPbfPath(String buildingID) {
		String fileName = String.format(FILE_CBM_PBF, buildingID);
		String pbfDir = TYVectorTileSettings.GetCBMDir();
		return new File(pbfDir, fileName).toString();
	}

	public static void generateCBMPbf(TYICity city, TYIBuilding building, List<TYIMapInfo> mapInfoList,
			List<TYIMapDataFeatureRecord> mapDataRecords, List<TYIFillSymbolRecord> fillSymbols,
			List<TYIIconTextSymbolRecord> iconTextSymbols) throws IOException {
		if (!"V4".equals(building.getRouteURL())) {
			return;
		}

		CBMPbf pbf = buildPbf(city, building, mapInfoList, mapDataRecords, fillSymbols, iconTextSymbols);
		String pbfPath = getCBMPbfPath(building.getBuildingID());
		FileOutputStream file = new FileOutputStream(pbfPath);
		pbf.writeTo(file);
	}

	public static CBMPbf buildPbf(TYICity city, TYIBuilding building, List<TYIMapInfo> mapInfos,
			List<TYIMapDataFeatureRecord> mapDataRecords, List<TYIFillSymbolRecord> fillSymbols,
			List<TYIIconTextSymbolRecord> iconTextSymbols) {

		CBMPbf.Builder builder = CBMPbf.newBuilder();
		builder.setBuildingID(building.getBuildingID());
		builder.addCities(TYCity2PbfUtils.cityToPbf(city));
		builder.addBuildings(TYCity2PbfUtils.buildingToPbf(building));
		for (TYIMapInfo info : mapInfos) {
			builder.addMapInfos(TYCity2PbfUtils.mapInfoToPbf(info));
		}

		for (TYIFillSymbolRecord fill : fillSymbols) {
			builder.addFillSymbols(TYSymbol2PbfUtils.fillSymbolToPbf(fill));
		}

		for (TYIIconTextSymbolRecord iconText : iconTextSymbols) {
			builder.addIconTextSymbols(TYSymbol2PbfUtils.iconTextSymbolToPbf(iconText));
		}

		Map<String, List<Integer>> layerSymbolMap = TYSymbolExtractor.extractSymbolList(mapDataRecords, fillSymbols,
				iconTextSymbols);
		builder.setSymbols(TYSymbol2PbfUtils.layerSymbolMapToPbf(layerSymbolMap));
		return builder.build();
	}
}
