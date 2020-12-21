package cn.platalk.map.entity.base.impl.map;

import cn.platalk.map.entity.base.map.TYITheme;
import org.json.JSONObject;

public class TYTheme implements TYITheme {
	static final String KEY_JSON_THEME_ID = "themeID";
	static final String KEY_JSON_THEME_NAME = "themeName";
	static final String KEY_JSON_THEME_SPRITE_NAME = "spriteName";

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

	@Override
	public JSONObject toJson() {
		JSONObject themeJsonObject = new JSONObject();
		themeJsonObject.put(KEY_JSON_THEME_ID, themeID);
		themeJsonObject.put(KEY_JSON_THEME_NAME, themeName);
		themeJsonObject.put(KEY_JSON_THEME_SPRITE_NAME, spriteName);
		return themeJsonObject;
	}
}
