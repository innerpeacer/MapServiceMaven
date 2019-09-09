package cn.platalk.map.entity.base;

import cn.platalk.common.TYIJsonFeature;

public interface TYIFillSymbolRecord extends TYIJsonFeature {
	public int getUID();

	public int getSymbolID();

	public String getFillColor();

	public String getOutlineColor();

	public double getLineWidth();

	public double getLevelMin();

	public double getLevelMax();

	public boolean isVisible();

}
