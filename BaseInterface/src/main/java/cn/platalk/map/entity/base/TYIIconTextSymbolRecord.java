package cn.platalk.map.entity.base;

import cn.platalk.common.TYIJsonFeature;

public interface TYIIconTextSymbolRecord extends TYIJsonFeature {
	public int getUID();

	public int getSymbolID();

	public boolean isIconVisible();

	public double getIconSize();

	public double getIconRotate();

	public double getIconOffsetX();

	public double getIconOffsetY();

	public boolean isTextVisible();

	public double getTextSize();

	public String getTextFont();

	public String getTextColor();

	public double getTextRotate();

	public double getTextOffsetX();

	public double getTextOffsetY();

	public double getLevelMin();

	public double getLevelMax();

	public String getOtherPaint();

	public String getOtherLayout();

	public String getDescription();

	public int getPriority();
}
