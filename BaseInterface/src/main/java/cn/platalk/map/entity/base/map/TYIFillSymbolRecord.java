package cn.platalk.map.entity.base.map;

import cn.platalk.common.TYIJsonFeature;

public interface TYIFillSymbolRecord extends TYIJsonFeature {
	int getUID();

	int getSymbolID();

	String getFillColor();

	String getOutlineColor();

	double getLineWidth();

	double getLevelMin();

	double getLevelMax();

	boolean isVisible();

}
