package cn.platalk.map.vectortile.builder;

public class TYCoordTransform {
	class TransCoord2D {
		public double x;
		public double y;

		public TransCoord2D(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	// Original
	TransCoord2D o1;
	TransCoord2D o2;
	TransCoord2D o3;

	// Target
	TransCoord2D t1;
	TransCoord2D t2;
	TransCoord2D t3;

	TransCoord2D oDelta_12;
	TransCoord2D oDelta_13;

	TransCoord2D tDelta_12;
	TransCoord2D tDelta_13;

	public TYCoordTransform(double[] originalCoordArray, double[] targetCoordArray) throws Exception {
		if (originalCoordArray == null || originalCoordArray.length != 6 || targetCoordArray == null
				|| targetCoordArray.length != 6) {
			throw new Exception("Invalid Input");
		}
		o1 = new TransCoord2D(originalCoordArray[0], originalCoordArray[1]);
		o2 = new TransCoord2D(originalCoordArray[2], originalCoordArray[3]);
		o3 = new TransCoord2D(originalCoordArray[4], originalCoordArray[5]);

		t1 = new TransCoord2D(targetCoordArray[0], targetCoordArray[1]);
		t2 = new TransCoord2D(targetCoordArray[2], targetCoordArray[3]);
		t3 = new TransCoord2D(targetCoordArray[4], targetCoordArray[5]);

		oDelta_12 = new TransCoord2D(o2.x - o1.x, o2.y - o1.y);
		oDelta_13 = new TransCoord2D(o3.x - o1.x, o3.y - o1.y);

		tDelta_12 = new TransCoord2D(t2.x - t1.x, t2.y - t1.y);
		tDelta_13 = new TransCoord2D(t3.x - t1.x, t3.y - t1.y);
	}

	public double[] getTransformedCoordinate(double[] coordArray) {
		TransCoord2D orignalCoord = new TransCoord2D(coordArray[0], coordArray[1]);
		TransCoord2D delatCoord = new TransCoord2D(orignalCoord.x - o1.x, orignalCoord.y - o1.y);

		double lamda = (delatCoord.x * oDelta_13.y - delatCoord.y * oDelta_13.x)
				/ (oDelta_12.x * oDelta_13.y - oDelta_12.y * oDelta_13.x);
		double miu = (delatCoord.x * oDelta_12.y - delatCoord.y * oDelta_12.x)
				/ (oDelta_13.x * oDelta_12.y - oDelta_13.y * oDelta_12.x);

		TransCoord2D targetCoord = new TransCoord2D(0, 0);
		targetCoord.x = t1.x + lamda * tDelta_12.x + miu * tDelta_13.x;
		targetCoord.y = t1.y + lamda * tDelta_12.y + miu * tDelta_13.y;
		return new double[] { targetCoord.x, targetCoord.y };
	}
}
