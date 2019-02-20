package cn.platalk.map.core.pbf;

import innerpeacer.mapdata.pbf.TYSymbolPbf.TYFillSymbolPbf;
import innerpeacer.mapdata.pbf.TYSymbolPbf.TYIconSymbolPbf;
import innerpeacer.mapdata.pbf.TYSymbolPbf.TYRenderingSymbolsPbf;

import java.util.List;

import cn.platalk.map.entity.base.impl.TYFillSymbolRecord;
import cn.platalk.map.entity.base.impl.TYIconSymbolRecord;

public class TYWebMapSymbol2PbfUtils {

	static TYRenderingSymbolsPbf symbolsToPbf(String mapID,
			List<TYFillSymbolRecord> fillRecords,
			List<TYIconSymbolRecord> iconRecords) {
		TYRenderingSymbolsPbf.Builder symbolsBuilder = TYRenderingSymbolsPbf
				.newBuilder();
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
