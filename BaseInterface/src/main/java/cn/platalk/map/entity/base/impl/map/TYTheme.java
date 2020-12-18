package cn.platalk.map.entity.base.impl.map;

import cn.platalk.map.entity.base.map.TYITheme;

public class TYTheme implements TYITheme {
	protected String themeID;
	protected String themeName;
	protected String spriteName;

	public TYTheme() {
		
	}

	public TYTheme(String themeID, String themeName, String spriteName) {
		this.themeID = themeID;
		this.themeName = themeName;
		this.spriteName = spriteName;
	}

	@Override
	public String getThemeID() {
		return themeID;
	}

	public void setThemeID(String themeID) {
		this.themeID = themeID;
	}

	@Override
	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	@Override
	public String getSpriteName() {
		return spriteName;
	}

	public void setSpriteName(String spriteName) {
		this.spriteName = spriteName;
	}
}
