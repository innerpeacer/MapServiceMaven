package cn.platalk.core.pbf.cbm.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.platalk.core.pbf.cbm.TYSymbolPbf.FillSymbolPbf;
import cn.platalk.core.pbf.cbm.TYSymbolPbf.IconTextSymbolPbf;
import cn.platalk.core.pbf.cbm.TYSymbolPbf.SymbolCollectionPbf;
import cn.platalk.map.entity.base.TYIFillSymbolRecord;
import cn.platalk.map.entity.base.TYIIconTextSymbolRecord;

public class TYSymbol2PbfUtils {

	public static FillSymbolPbf fillSymbolToPbf(TYIFillSymbolRecord fillSymbol) {
		FillSymbolPbf.Builder builder = FillSymbolPbf.newBuilder();
		builder.setUID(fillSymbol.getUID());
		builder.setSymbolID(fillSymbol.getSymbolID());
		builder.setFillColor(fillSymbol.getFillColor());
		builder.setOutlineColor(fillSymbol.getOutlineColor());
		builder.setOutlineWidth(fillSymbol.getLineWidth());
		builder.setLevelMin(fillSymbol.getLevelMin());
		builder.setLevelMax(fillSymbol.getLevelMax());
		builder.setVisible(fillSymbol.isVisible());
		return builder.build();
	}

	public static IconTextSymbolPbf iconTextSymbolToPbf(TYIIconTextSymbolRecord iconTextSymbol) {
		IconTextSymbolPbf.Builder builder = IconTextSymbolPbf.newBuilder();
		builder.setUID(iconTextSymbol.getUID());
		builder.setSymbolID(iconTextSymbol.getSymbolID());
		builder.setIconVisible(iconTextSymbol.isIconVisible());
		builder.setIconSize(iconTextSymbol.getIconSize());
		builder.setIconRotate(iconTextSymbol.getIconRotate());
		builder.setIconOffsetX(iconTextSymbol.getIconOffsetX());
		builder.setIconOffsetY(iconTextSymbol.getIconOffsetY());
		builder.setTextVisible(iconTextSymbol.isTextVisible());
		builder.setTextSize(iconTextSymbol.getTextSize());
		builder.setTextRotate(iconTextSymbol.getTextRotate());
		builder.setTextFont(iconTextSymbol.getTextFont());
		builder.setTextColor(iconTextSymbol.getTextColor());
		builder.setTextOffsetX(iconTextSymbol.getTextOffsetX());
		builder.setTextOffsetY(iconTextSymbol.getTextOffsetY());
		builder.setLevelMin(iconTextSymbol.getLevelMin());
		builder.setLevelMax(iconTextSymbol.getLevelMax());
		builder.setPriority(iconTextSymbol.getPriority());
		if (iconTextSymbol.getOtherPaint() != null && iconTextSymbol.getOtherPaint().length() > 0) {
			builder.setOtherPaint(iconTextSymbol.getOtherPaint());
		}
		if (iconTextSymbol.getOtherLayout() != null && iconTextSymbol.getOtherLayout().length() > 0) {
			builder.setOtherLayout(iconTextSymbol.getOtherLayout());
		}
		return builder.build();
	}

	public static SymbolCollectionPbf layerSymbolMapToPbf(Map<String, List<Integer>> layerMap) {
		SymbolCollectionPbf.Builder builder = SymbolCollectionPbf.newBuilder();
		{
			List<Integer> symbols = layerMap.get("floor");
			symbols = (symbols != null) ? symbols : new ArrayList<Integer>();
			builder.addAllFloor(symbols);
		}
		{
			List<Integer> symbols = layerMap.get("room");
			symbols = (symbols != null) ? symbols : new ArrayList<Integer>();
			builder.addAllRoom(symbols);
		}
		{
			List<Integer> symbols = layerMap.get("asset");
			symbols = (symbols != null) ? symbols : new ArrayList<Integer>();
			builder.addAllAsset(symbols);
		}
		{
			List<Integer> symbols = layerMap.get("facility");
			symbols = (symbols != null) ? symbols : new ArrayList<Integer>();
			builder.addAllFacility(symbols);
		}
		{
			List<Integer> symbols = layerMap.get("label");
			symbols = (symbols != null) ? symbols : new ArrayList<Integer>();
			builder.addAllLabel(symbols);
		}
		{
			List<Integer> symbols = layerMap.get("extrusion");
			symbols = (symbols != null) ? symbols : new ArrayList<Integer>();
			builder.addAllExtrusion(symbols);
		}
		return builder.build();
	}

}
