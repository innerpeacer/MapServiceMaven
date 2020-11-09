package cn.platalk.map.entity.base.map;

import cn.platalk.common.TYIJsonFeature;

public interface TYIIconTextSymbolRecord extends TYIJsonFeature {
	int getUID();

	int getSymbolID();

	boolean isIconVisible();

	double getIconSize();

	double getIconRotate();

	double getIconOffsetX();

	double getIconOffsetY();

	boolean isTextVisible();

	double getTextSize();

	String getTextFont();

	String getTextColor();

	double getTextRotate();

	double getTextOffsetX();

	double getTextOffsetY();

	double getLevelMin();

	double getLevelMax();

	String getOtherPaint();

	String getOtherLayout();

	String getDescription();

	int getPriority();
}
