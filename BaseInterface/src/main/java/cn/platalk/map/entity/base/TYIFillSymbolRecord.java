package cn.platalk.map.entity.base;

public interface TYIFillSymbolRecord {
	public int getUID();

	public int getSymbolID();

	public String getFillColor();

	public String getOutlineColor();

	public double getLineWidth();

	public double getLevelMin();

	public double getLevelMax();

	public boolean isVisible();

}
