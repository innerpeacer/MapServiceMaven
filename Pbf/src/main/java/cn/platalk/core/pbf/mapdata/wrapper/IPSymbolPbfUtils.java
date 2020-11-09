package cn.platalk.core.pbf.mapdata.wrapper;

import java.util.List;

import cn.platalk.core.pbf.mapdata.TYSymbolPbf.TYFillSymbolPbf;
import cn.platalk.core.pbf.mapdata.TYSymbolPbf.TYIconSymbolPbf;
import cn.platalk.core.pbf.mapdata.TYSymbolPbf.TYRenderingSymbolsPbf;
import cn.platalk.map.entity.base.impl.map.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.map.TYIconSymbolRecord;

class IPSymbolPbfUtils {

	static TYRenderingSymbolsPbf symbolsToPbf(String mapID, List<TYFillSymbolRecord> fillRecords,
			List<TYIconSymbolRecord> iconRecords) {
		TYRenderingSymbolsPbf.Builder symbolsBuilder = TYRenderingSymbolsPbf.newBuilder();
		symbolsBuilder.setMapID(mapID);

		for (TYFillSymbolRecord record : fillRecords) {
			symbolsBuilder.addFillSymbols(fillSymbolToPbf(record));
		}

		for (TYIconSymbolRecord record : iconRecords) {
			symbolsBuilder.addIconSymbols(iconSymbolToPbf(record));
		}
		return symbolsBuilder.build();
	}

	static TYIconSymbolPbf iconSymbolToPbf(TYIconSymbolRecord record) {
		TYIconSymbolPbf.Builder iconBuilder = TYIconSymbolPbf.newBuilder();
		iconBuilder.setSymbolID(record.symbolID);
		iconBuilder.setIcon(record.icon);
		return iconBuilder.build();
	}

	static TYFillSymbolPbf fillSymbolToPbf(TYFillSymbolRecord record) {
		TYFillSymbolPbf.Builder fillBuilder = TYFillSymbolPbf.newBuilder();
		fillBuilder.setSymbolID(record.symbolID);
		fillBuilder.setFillColor(record.fillColor);
		fillBuilder.setOutlineColor(record.outlineColor);
		fillBuilder.setLineWidth((float) record.lineWidth);
		return fillBuilder.build();
	}
}
