package cn.platalk.brtmap.vectortile.builder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import cn.platalk.brtmap.vectortile.pbf.VectorTile;

import com.google.protobuf.ProtocolStringList;
import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

final class MvtReader {
	private static final int MIN_LINE_STRING_LEN = 6; // MoveTo,1 + LineTo,1
	private static final int MIN_POLYGON_LEN = 9; // MoveTo,1 + LineTo,2 +
													// ClosePath

	static List<Geometry> loadMvt(Path p, GeometryFactory geomFactory,
			MvtITagConverter tagConverter) throws IOException {
		return loadMvt(p, geomFactory, tagConverter, RING_CLASSIFIER_V2_1);
	}

	static List<Geometry> loadMvt(Path p, GeometryFactory geomFactory,
			MvtITagConverter tagConverter, RingClassifier ringClassifier)
			throws IOException {
		final List<Geometry> geometries;

		try (final InputStream is = new FileInputStream(p.toFile())) {
			geometries = loadMvt(is, geomFactory, tagConverter, ringClassifier);
		}

		return geometries;
	}

	static List<Geometry> loadMvt(InputStream is, GeometryFactory geomFactory,
			MvtITagConverter tagConverter) throws IOException {
		return loadMvt(is, geomFactory, tagConverter, RING_CLASSIFIER_V2_1);
	}

	static List<Geometry> loadMvt(InputStream is, GeometryFactory geomFactory,
			MvtITagConverter tagConverter, RingClassifier ringClassifier)
			throws IOException {

		final List<Geometry> tileGeoms = new ArrayList<>();
		final VectorTile.Tile mvt = VectorTile.Tile.parseFrom(is);
		final MvtVec2d cursor = new MvtVec2d();

		for (VectorTile.Tile.Layer nextLayer : mvt.getLayersList()) {

			final ProtocolStringList keysList = nextLayer.getKeysList();
			final List<VectorTile.Tile.Value> valuesList = nextLayer
					.getValuesList();

			for (VectorTile.Tile.Feature nextFeature : nextLayer
					.getFeaturesList()) {

				final Long id = nextFeature.hasId() ? nextFeature.getId()
						: null;

				final VectorTile.Tile.GeomType geomType = nextFeature.getType();

				if (geomType == VectorTile.Tile.GeomType.UNKNOWN) {
					continue;
				}

				final List<Integer> geomCmds = nextFeature.getGeometryList();
				cursor.set(0d, 0d);
				final Geometry nextGeom = readGeometry(geomCmds, geomType,
						geomFactory, cursor, ringClassifier);
				if (nextGeom != null) {
					tileGeoms.add(nextGeom);
					nextGeom.setUserData(tagConverter.toUserData(id,
							nextFeature.getTagsList(), keysList, valuesList));
				}
			}
		}

		return tileGeoms;
	}

	private static Geometry readGeometry(List<Integer> geomCmds,
			VectorTile.Tile.GeomType geomType, GeometryFactory geomFactory,
			MvtVec2d cursor, RingClassifier ringClassifier) {
		Geometry result = null;

		switch (geomType) {
		case POINT:
			result = readPoints(geomFactory, geomCmds, cursor);
			break;
		case LINESTRING:
			result = readLines(geomFactory, geomCmds, cursor);
			break;
		case POLYGON:
			result = readPolys(geomFactory, geomCmds, cursor, ringClassifier);
			break;
		default:
			// LoggerFactory.getLogger(MvtReader.class).error(
			// "readGeometry(): Unhandled geometry type [{}]", geomType);
			System.out.println("readGeometry(): Unhandled geometry type [{}]"
					+ geomType);
		}

		return result;
	}

	private static Geometry readPoints(GeometryFactory geomFactory,
			List<Integer> geomCmds, MvtVec2d cursor) {
		// Guard: must have header
		if (geomCmds.isEmpty()) {
			return null;
		}

		/** Geometry command index */
		int i = 0;

		// Read command header
		final int cmdHdr = geomCmds.get(i++);
		final int cmdLength = MvtGeomCmdHdr.getCmdLength(cmdHdr);
		final MvtGeomCmd cmd = MvtGeomCmdHdr.getCmd(cmdHdr);

		// Guard: command type
		if (cmd != MvtGeomCmd.MoveTo) {
			return null;
		}

		// Guard: minimum command length
		if (cmdLength < 1) {
			return null;
		}

		// Guard: header data unsupported by geometry command buffer
		// (require header and at least 1 value * 2 params)
		if (cmdLength * MvtGeomCmd.MoveTo.getParamCount() + 1 > geomCmds.size()) {
			return null;
		}

		final CoordinateSequence coordSeq = geomFactory
				.getCoordinateSequenceFactory().create(cmdLength, 2);
		int coordIndex = 0;
		Coordinate nextCoord;

		while (i < geomCmds.size() - 1) {
			cursor.add(MvtZigZag.decode(geomCmds.get(i++)),
					MvtZigZag.decode(geomCmds.get(i++)));

			nextCoord = coordSeq.getCoordinate(coordIndex++);
			nextCoord.setOrdinate(0, cursor.x);
			nextCoord.setOrdinate(1, cursor.y);
		}

		return coordSeq.size() == 1 ? geomFactory.createPoint(coordSeq)
				: geomFactory.createMultiPoint(coordSeq);
	}

	private static Geometry readLines(GeometryFactory geomFactory,
			List<Integer> geomCmds, MvtVec2d cursor) {

		// Guard: must have header
		if (geomCmds.isEmpty()) {
			return null;
		}

		/** Geometry command index */
		int i = 0;

		int cmdHdr;
		int cmdLength;
		MvtGeomCmd cmd;
		List<LineString> geoms = new ArrayList<>(1);
		CoordinateSequence nextCoordSeq;
		Coordinate nextCoord;

		while (i <= geomCmds.size() - MIN_LINE_STRING_LEN) {

			// --------------------------------------------
			// Expected: MoveTo command of length 1
			// --------------------------------------------

			// Read command header
			cmdHdr = geomCmds.get(i++);
			cmdLength = MvtGeomCmdHdr.getCmdLength(cmdHdr);
			cmd = MvtGeomCmdHdr.getCmd(cmdHdr);

			// Guard: command type and length
			if (cmd != MvtGeomCmd.MoveTo || cmdLength != 1) {
				break;
			}

			// Update cursor position with relative move
			cursor.add(MvtZigZag.decode(geomCmds.get(i++)),
					MvtZigZag.decode(geomCmds.get(i++)));

			// --------------------------------------------
			// Expected: LineTo command of length > 0
			// --------------------------------------------

			// Read command header
			cmdHdr = geomCmds.get(i++);
			cmdLength = MvtGeomCmdHdr.getCmdLength(cmdHdr);
			cmd = MvtGeomCmdHdr.getCmd(cmdHdr);

			// Guard: command type and length
			if (cmd != MvtGeomCmd.LineTo || cmdLength < 1) {
				break;
			}

			// Guard: header data length unsupported by geometry command buffer
			// (require at least (1 value * 2 params) + current_index)
			if ((cmdLength * MvtGeomCmd.LineTo.getParamCount()) + i > geomCmds
					.size()) {
				break;
			}

			nextCoordSeq = geomFactory.getCoordinateSequenceFactory().create(
					1 + cmdLength, 2);

			// Set first point from MoveTo command
			nextCoord = nextCoordSeq.getCoordinate(0);
			nextCoord.setOrdinate(0, cursor.x);
			nextCoord.setOrdinate(1, cursor.y);

			// Set remaining points from LineTo command
			for (int lineToIndex = 0; lineToIndex < cmdLength; ++lineToIndex) {

				// Update cursor position with relative line delta
				cursor.add(MvtZigZag.decode(geomCmds.get(i++)),
						MvtZigZag.decode(geomCmds.get(i++)));

				nextCoord = nextCoordSeq.getCoordinate(lineToIndex + 1);
				nextCoord.setOrdinate(0, cursor.x);
				nextCoord.setOrdinate(1, cursor.y);
			}

			geoms.add(geomFactory.createLineString(nextCoordSeq));
		}

		return geoms.size() == 1 ? geoms.get(0) : geomFactory
				.createMultiLineString(geoms.toArray(new LineString[geoms
						.size()]));
	}

	private static Geometry readPolys(GeometryFactory geomFactory,
			List<Integer> geomCmds, MvtVec2d cursor,
			RingClassifier ringClassifier) {

		// Guard: must have header
		if (geomCmds.isEmpty()) {
			return null;
		}

		/** Geometry command index */
		int i = 0;

		int cmdHdr;
		int cmdLength;
		MvtGeomCmd cmd;
		List<LinearRing> rings = new ArrayList<>(1);
		CoordinateSequence nextCoordSeq;
		Coordinate nextCoord;

		while (i <= geomCmds.size() - MIN_POLYGON_LEN) {

			// --------------------------------------------
			// Expected: MoveTo command of length 1
			// --------------------------------------------

			// Read command header
			cmdHdr = geomCmds.get(i++);
			cmdLength = MvtGeomCmdHdr.getCmdLength(cmdHdr);
			cmd = MvtGeomCmdHdr.getCmd(cmdHdr);

			// Guard: command type and length
			if (cmd != MvtGeomCmd.MoveTo || cmdLength != 1) {
				break;
			}

			// Update cursor position with relative move
			cursor.add(MvtZigZag.decode(geomCmds.get(i++)),
					MvtZigZag.decode(geomCmds.get(i++)));

			// --------------------------------------------
			// Expected: LineTo command of length > 1
			// --------------------------------------------

			// Read command header
			cmdHdr = geomCmds.get(i++);
			cmdLength = MvtGeomCmdHdr.getCmdLength(cmdHdr);
			cmd = MvtGeomCmdHdr.getCmd(cmdHdr);

			// Guard: command type and length
			if (cmd != MvtGeomCmd.LineTo || cmdLength < 2) {
				break;
			}

			// Guard: header data length unsupported by geometry command buffer
			// (require at least (2 values * 2 params) + (current index 'i') +
			// (1 for ClosePath))
			if ((cmdLength * MvtGeomCmd.LineTo.getParamCount()) + i + 1 > geomCmds
					.size()) {
				break;
			}

			nextCoordSeq = geomFactory.getCoordinateSequenceFactory().create(
					2 + cmdLength, 2);

			// Set first point from MoveTo command
			nextCoord = nextCoordSeq.getCoordinate(0);
			nextCoord.setOrdinate(0, cursor.x);
			nextCoord.setOrdinate(1, cursor.y);

			// Set remaining points from LineTo command
			for (int lineToIndex = 0; lineToIndex < cmdLength; ++lineToIndex) {

				// Update cursor position with relative line delta
				cursor.add(MvtZigZag.decode(geomCmds.get(i++)),
						MvtZigZag.decode(geomCmds.get(i++)));

				nextCoord = nextCoordSeq.getCoordinate(lineToIndex + 1);
				nextCoord.setOrdinate(0, cursor.x);
				nextCoord.setOrdinate(1, cursor.y);
			}

			// --------------------------------------------
			// Expected: ClosePath command of length 1
			// --------------------------------------------

			// Read command header
			cmdHdr = geomCmds.get(i++);
			cmdLength = MvtGeomCmdHdr.getCmdLength(cmdHdr);
			cmd = MvtGeomCmdHdr.getCmd(cmdHdr);

			if (cmd != MvtGeomCmd.ClosePath || cmdLength != 1) {
				break;
			}

			// Set last point from ClosePath command
			nextCoord = nextCoordSeq.getCoordinate(nextCoordSeq.size() - 1);
			nextCoord.setOrdinate(0, nextCoordSeq.getOrdinate(0, 0));
			nextCoord.setOrdinate(1, nextCoordSeq.getOrdinate(0, 1));

			rings.add(geomFactory.createLinearRing(nextCoordSeq));
		}

		// Classify rings
		final List<Polygon> polygons = ringClassifier.classifyRings(rings,
				geomFactory);
		if (polygons.size() < 1) {
			return null;

		} else if (polygons.size() == 1) {
			return polygons.get(0);

		} else {
			return geomFactory.createMultiPolygon(polygons
					.toArray(new Polygon[polygons.size()]));
		}
	}

	interface RingClassifier {
		List<Polygon> classifyRings(List<LinearRing> rings,
				GeometryFactory geomFactory);
	}

	static final RingClassifier RING_CLASSIFIER_V1 = new PolyRingClassifierV1();

	static final RingClassifier RING_CLASSIFIER_V2_1 = new PolyRingClassifierV2_1();

	private static final class PolyRingClassifierV2_1 implements RingClassifier {

		@Override
		public List<Polygon> classifyRings(List<LinearRing> rings,
				GeometryFactory geomFactory) {
			final List<Polygon> polygons = new ArrayList<>();
			final List<LinearRing> holes = new ArrayList<>();

			double outerArea = 0d;
			LinearRing outerPoly = null;

			for (LinearRing r : rings) {
				double area = CGAlgorithms.signedArea(r.getCoordinates());

				if (!r.isRing()) {
					continue; // sanity check, could probably be handled in a
								// isSimple() check
				}

				if (area == 0d) {
					continue; // zero-area
				}

				if (area > 0d) {
					if (outerPoly != null) {
						polygons.add(geomFactory.createPolygon(outerPoly,
								holes.toArray(new LinearRing[holes.size()])));
						holes.clear();
					}

					// Pos --> CCW, Outer
					outerPoly = r;
					outerArea = area;

				} else {

					if (Math.abs(outerArea) < Math.abs(area)) {
						continue; // Holes must have less area, could probably
									// be handled in a isSimple() check
					}

					// Neg --> CW, Hole
					holes.add(r);
				}
			}

			if (outerPoly != null) {
				holes.toArray();
				polygons.add(geomFactory.createPolygon(outerPoly,
						holes.toArray(new LinearRing[holes.size()])));
			}

			return polygons;
		}
	}

	private static final class PolyRingClassifierV1 implements RingClassifier {

		@Override
		public List<Polygon> classifyRings(List<LinearRing> rings,
				GeometryFactory geomFactory) {
			final List<Polygon> polygons = new ArrayList<>();
			final List<LinearRing> holes = new ArrayList<>();

			double outerArea = 0d;
			LinearRing outerPoly = null;

			for (LinearRing r : rings) {
				double area = CGAlgorithms.signedArea(r.getCoordinates());

				if (!r.isRing()) {
					continue; // sanity check, could probably be handled in a
								// isSimple() check
				}

				if (area == 0d) {
					continue; // zero-area
				}

				if (outerPoly == null || (outerArea < 0 == area < 0)) {
					if (outerPoly != null) {
						polygons.add(geomFactory.createPolygon(outerPoly,
								holes.toArray(new LinearRing[holes.size()])));
						holes.clear();
					}

					// Pos --> CCW, Outer
					outerPoly = r;
					outerArea = area;

				} else {

					if (Math.abs(outerArea) < Math.abs(area)) {
						continue; // Holes must have less area, could probably
									// be handled in a isSimple() check
					}

					// Neg --> CW, Hole
					holes.add(r);
				}
			}

			if (outerPoly != null) {
				holes.toArray();
				polygons.add(geomFactory.createPolygon(outerPoly,
						holes.toArray(new LinearRing[holes.size()])));
			}

			return polygons;
		}
	}
}
