package cn.platalk.map.vectortile.builder;

import java.util.*;

import com.vividsolutions.jts.algorithm.CGAlgorithms;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateArrays;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.TopologyException;
import com.vividsolutions.jts.geom.util.AffineTransformation;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

import cn.platalk.map.vectortile.pbf.VectorTile;

final class TYJtsAdapter {

	public static MvtTileGeomResult createTileGeom(Geometry g,
			Envelope tileEnvelope, GeometryFactory geomFactory,
			MvtLayerParams mvtLayerParams, MvtIGeometryFilter filter,
			TYTileCoord tile) {
		return createTileGeom(flatFeatureList(g), tileEnvelope, geomFactory,
				mvtLayerParams, filter, tile);
	}

	public static MvtTileGeomResult createTileGeom(List<Geometry> g,
			Envelope tileEnvelope, GeometryFactory geomFactory,
			MvtLayerParams mvtLayerParams, MvtIGeometryFilter filter,
			TYTileCoord tile) {
		return createTileGeom(g, tileEnvelope, tileEnvelope, geomFactory,
				mvtLayerParams, filter, tile);
	}

	public static MvtTileGeomResult createTileGeom(List<Geometry> g,
			Envelope tileEnvelope, Envelope clipEnvelope,
			GeometryFactory geomFactory, MvtLayerParams mvtLayerParams,
			MvtIGeometryFilter filter, TYTileCoord tile) {

		final Geometry tileClipGeom = geomFactory.toGeometry(clipEnvelope);

		final AffineTransformation t = new AffineTransformation();
		final double xDiff = tileEnvelope.getWidth();
		final double yDiff = tileEnvelope.getHeight();
		final double xOffset = -tileEnvelope.getMinX();
		final double yOffset = -tileEnvelope.getMinY();

		t.translate(xOffset, yOffset);
		t.scale(1d / (xDiff / (double) mvtLayerParams.extent), -1d
				/ (yDiff / (double) mvtLayerParams.extent));
		t.translate(0d, (double) mvtLayerParams.extent);

		// The area contained in BOTH the 'original geometry', g, AND the 'clip
		// envelope geometry' is the 'tile geometry'
		final List<Geometry> intersectedGeoms = flatIntersection(tileClipGeom,
				g);
		final List<Geometry> transformedGeoms = new ArrayList<>(
				intersectedGeoms.size());

		// Transform intersected geometry
		Geometry nextTransformGeom;
		Object nextUserData;
		for (Geometry nextInterGeom : intersectedGeoms) {
			nextUserData = nextInterGeom.getUserData();

			// nextTransformGeom = t.transform(nextInterGeom);
			nextTransformGeom = TYGeomTransform.brtTransform(nextInterGeom,
					tile, mvtLayerParams);

			// Floating --> Integer, still contained within doubles
			nextTransformGeom.apply(MvtRoundingFilter.INSTANCE);

			// TODO: Refactor line simplification
			nextTransformGeom = TopologyPreservingSimplifier.simplify(
					nextTransformGeom, .1d); // Can't use 0d, specify value <
												// .5d

			nextTransformGeom.setUserData(nextUserData);

			// Apply filter on transformed geometry
			if (filter.accept(nextTransformGeom)) {
				transformedGeoms.add(nextTransformGeom);
			}
		}
		return new MvtTileGeomResult(intersectedGeoms, transformedGeoms);
	}

	static List<Geometry> flatIntersection(Geometry envelope, Geometry data) {
		return flatIntersection(envelope, flatFeatureList(data));
	}

	private static List<Geometry> flatIntersection(Geometry envelope,
			List<Geometry> dataGeoms) {
		final List<Geometry> intersectedGeoms = new ArrayList<>(
				dataGeoms.size());

		Geometry nextIntersected;
		for (Geometry nextGeom : dataGeoms) {
			try {

				// AABB intersection culling
				if (envelope.getEnvelopeInternal().intersects(
						nextGeom.getEnvelopeInternal())) {

					nextIntersected = envelope.intersection(nextGeom);
					if (!nextIntersected.isEmpty()) {
						nextIntersected.setUserData(nextGeom.getUserData());
						intersectedGeoms.add(nextIntersected);
					}
				}

			} catch (TopologyException e) {
				// LoggerFactory.getLogger(MvtJtsAdapter.class).error(
				// e.getMessage(), e);
				e.printStackTrace();
			}
		}

		return intersectedGeoms;
	}

	public static VectorTile.Tile.GeomType toGeomType(Geometry geometry) {
		VectorTile.Tile.GeomType result = VectorTile.Tile.GeomType.UNKNOWN;

		if (geometry instanceof Point || geometry instanceof MultiPoint) {
			result = VectorTile.Tile.GeomType.POINT;

		} else if (geometry instanceof LineString
				|| geometry instanceof MultiLineString) {
			result = VectorTile.Tile.GeomType.LINESTRING;

		} else if (geometry instanceof Polygon
				|| geometry instanceof MultiPolygon) {
			result = VectorTile.Tile.GeomType.POLYGON;
		}

		return result;
	}

	public static List<Geometry> flatFeatureList(Geometry geom) {
		final List<Geometry> singleGeoms = new ArrayList<>();
		final Stack<Geometry> geomStack = new Stack<>();

		Geometry nextGeom;
		int nextGeomCount;

		geomStack.push(geom);
		while (!geomStack.isEmpty()) {
			nextGeom = geomStack.pop();

			if (nextGeom instanceof Point || nextGeom instanceof MultiPoint
					|| nextGeom instanceof LineString
					|| nextGeom instanceof MultiLineString
					|| nextGeom instanceof Polygon
					|| nextGeom instanceof MultiPolygon) {

				singleGeoms.add(nextGeom);

			} else if (nextGeom instanceof GeometryCollection) {

				// Push all child geometries
				nextGeomCount = nextGeom.getNumGeometries();
				for (int i = 0; i < nextGeomCount; ++i) {
					geomStack.push(nextGeom.getGeometryN(i));
				}

			}
		}

		return singleGeoms;
	}

	public static List<VectorTile.Tile.Feature> toFeatures(Geometry geometry,
			MvtLayerProps layerProps, MvtIUserDataConverter userDataConverter) {
		return toFeatures(flatFeatureList(geometry), layerProps,
				userDataConverter);
	}

	public static List<VectorTile.Tile.Feature> toFeatures(
			Collection<Geometry> flatGeoms, MvtLayerProps layerProps,
			MvtIUserDataConverter userDataConverter) {

		// Guard: empty geometry
		if (flatGeoms.isEmpty()) {
			return Collections.emptyList();
		}

		final List<VectorTile.Tile.Feature> features = new ArrayList<>();
		final MvtVec2d cursor = new MvtVec2d();

		VectorTile.Tile.Feature nextFeature;

		for (Geometry nextGeom : flatGeoms) {
			cursor.set(0d, 0d);
			nextFeature = toFeature(nextGeom, cursor, layerProps,
					userDataConverter);
			if (nextFeature != null) {
				features.add(nextFeature);
			}
		}

		return features;
	}

	private static VectorTile.Tile.Feature toFeature(Geometry geom,
			MvtVec2d cursor, MvtLayerProps layerProps,
			MvtIUserDataConverter userDataConverter) {

		// Guard: UNKNOWN Geometry
		final VectorTile.Tile.GeomType mvtGeomType = TYJtsAdapter
				.toGeomType(geom);
		if (mvtGeomType == VectorTile.Tile.GeomType.UNKNOWN) {
			return null;
		}

		final VectorTile.Tile.Feature.Builder featureBuilder = VectorTile.Tile.Feature
				.newBuilder();
		final boolean mvtClosePath = MvtUtil.shouldClosePath(mvtGeomType);
		final List<Integer> mvtGeom = new ArrayList<>();

		featureBuilder.setType(mvtGeomType);

		if (geom instanceof Point || geom instanceof MultiPoint) {

			// Encode as MVT point or multipoint
			mvtGeom.addAll(ptsToGeomCmds(geom, cursor));

		} else if (geom instanceof LineString
				|| geom instanceof MultiLineString) {

			// Encode as MVT linestring or multi-linestring
			for (int i = 0; i < geom.getNumGeometries(); ++i) {
				mvtGeom.addAll(linesToGeomCmds(geom.getGeometryN(i),
						mvtClosePath, cursor, 1));
			}

		} else if (geom instanceof MultiPolygon || geom instanceof Polygon) {

			// Encode as MVT polygon or multi-polygon
			for (int i = 0; i < geom.getNumGeometries(); ++i) {

				final Polygon nextPoly = (Polygon) geom.getGeometryN(i);
				final List<Integer> nextPolyGeom = new ArrayList<>();
				boolean valid = true;

				// Add exterior ring
				final LineString exteriorRing = nextPoly.getExteriorRing();

				// Area must be non-zero
				final double exteriorArea = CGAlgorithms
						.signedArea(exteriorRing.getCoordinates());
				if (((int) Math.round(exteriorArea)) == 0) {
					continue;
				}

				// Check CCW Winding (must be positive area)
				if (exteriorArea < 0d) {
					CoordinateArrays.reverse(exteriorRing.getCoordinates());
				}

				nextPolyGeom.addAll(linesToGeomCmds(exteriorRing, mvtClosePath,
						cursor, 2));

				// Add interior rings
				for (int ringIndex = 0; ringIndex < nextPoly
						.getNumInteriorRing(); ++ringIndex) {

					final LineString nextInteriorRing = nextPoly
							.getInteriorRingN(ringIndex);

					// Area must be non-zero
					final double interiorArea = CGAlgorithms
							.signedArea(nextInteriorRing.getCoordinates());
					if (((int) Math.round(interiorArea)) == 0) {
						continue;
					}

					// Check CW Winding (must be negative area)
					if (interiorArea > 0d) {
						CoordinateArrays.reverse(nextInteriorRing
								.getCoordinates());
					}

					// Interior ring area must be < exterior ring area, or
					// entire geometry is invalid
					if (Math.abs(exteriorArea) <= Math.abs(interiorArea)) {
						valid = false;
						break;
					}

					nextPolyGeom.addAll(linesToGeomCmds(nextInteriorRing,
							mvtClosePath, cursor, 2));
				}

				if (valid) {
					mvtGeom.addAll(nextPolyGeom);
				}
			}
		}

		if (mvtGeom.size() < 1) {
			return null;
		}

		featureBuilder.addAllGeometry(mvtGeom);

		// Feature Properties
		userDataConverter.addTags(geom.getUserData(), layerProps,
				featureBuilder);

		Map<String, Object> userData = (Map<String, Object>) geom.getUserData();
		int id = (Integer) userData.get("id");
		featureBuilder.setId(id);

		return featureBuilder.build();
	}

	private static List<Integer> ptsToGeomCmds(final Geometry geom,
			final MvtVec2d cursor) {

		// Guard: empty geometry coordinates
		final Coordinate[] geomCoords = geom.getCoordinates();
		if (geomCoords.length <= 0) {
			Collections.emptyList();
		}

		final List<Integer> geomCmds = new ArrayList<>(
				geomCmdBuffLenPts(geomCoords.length));

		final MvtVec2d mvtPos = new MvtVec2d();

		int moveCmdLen = 0;

		// Insert placeholder for 'MoveTo' command header
		geomCmds.add(0);

		Coordinate nextCoord;

		for (int i = 0; i < geomCoords.length; ++i) {
			nextCoord = geomCoords[i];
			mvtPos.set(nextCoord.x, nextCoord.y);

			// Ignore duplicate MVT points
			if (i == 0 || !equalAsInts(cursor, mvtPos)) {
				++moveCmdLen;
				moveCursor(cursor, geomCmds, mvtPos);
			}
		}

		if (moveCmdLen <= MvtGeomCmdHdr.CMD_HDR_LEN_MAX) {

			// Write 'MoveTo' command header to first index
			geomCmds.set(0, MvtGeomCmdHdr.cmdHdr(MvtGeomCmd.MoveTo, moveCmdLen));

			return geomCmds;

		} else {

			// Invalid geometry, need at least 1 'MoveTo' value to make points
			return Collections.emptyList();
		}
	}

	private static List<Integer> linesToGeomCmds(final Geometry geom,
			final boolean closeEnabled, final MvtVec2d cursor,
			final int minLineToLen) {

		final Coordinate[] geomCoords = geom.getCoordinates();

		// Check geometry for repeated end points
		final int repeatEndCoordCount = countCoordRepeatReverse(geomCoords);
		final int minExpGeomCoords = geomCoords.length - repeatEndCoordCount;

		// Guard/Optimization: Not enough geometry coordinates for a line
		if (minExpGeomCoords < 2) {
			Collections.emptyList();
		}

		final List<Integer> geomCmds = new ArrayList<>(geomCmdBuffLenLines(
				minExpGeomCoords, closeEnabled));

		final MvtVec2d mvtPos = new MvtVec2d();

		// Initial coordinate
		Coordinate nextCoord = geomCoords[0];
		mvtPos.set(nextCoord.x, nextCoord.y);

		// Encode initial 'MoveTo' command
		geomCmds.add(MvtGeomCmdHdr.cmdHdr(MvtGeomCmd.MoveTo, 1));

		moveCursor(cursor, geomCmds, mvtPos);

		final int lineToCmdHdrIndex = geomCmds.size();

		// Insert placeholder for 'LineTo' command header
		geomCmds.add(0);

		int lineToLength = 0;

		for (int i = 1; i < minExpGeomCoords; ++i) {
			nextCoord = geomCoords[i];
			mvtPos.set(nextCoord.x, nextCoord.y);

			// Ignore duplicate MVT points in sequence
			if (!equalAsInts(cursor, mvtPos)) {
				++lineToLength;
				moveCursor(cursor, geomCmds, mvtPos);
			}
		}

		if (lineToLength >= minLineToLen
				&& lineToLength <= MvtGeomCmdHdr.CMD_HDR_LEN_MAX) {

			// Write 'LineTo' 'command header'
			geomCmds.set(lineToCmdHdrIndex,
					MvtGeomCmdHdr.cmdHdr(MvtGeomCmd.LineTo, lineToLength));

			if (closeEnabled) {
				geomCmds.add(MvtGeomCmdHdr.closePathCmdHdr());
			}

			return geomCmds;

		} else {

			// Invalid geometry, need at least 1 'LineTo' value to make a
			// Multiline or Polygon
			return Collections.emptyList();
		}
	}

	private static int countCoordRepeatReverse(Coordinate[] coords) {
		int repeatCoords = 0;

		final Coordinate firstCoord = coords[0];
		Coordinate nextCoord;

		for (int i = coords.length - 1; i > 0; --i) {
			nextCoord = coords[i];
			if (equalAsInts2d(firstCoord, nextCoord)) {
				++repeatCoords;
			} else {
				break;
			}
		}

		return repeatCoords;
	}

	private static void moveCursor(MvtVec2d cursor, List<Integer> geomCmds,
			MvtVec2d mvtPos) {

		// Delta, then zigzag
		geomCmds.add(MvtZigZag.encode((int) mvtPos.x - (int) cursor.x));
		geomCmds.add(MvtZigZag.encode((int) mvtPos.y - (int) cursor.y));

		cursor.set(mvtPos);
	}

	private static boolean equalAsInts2d(Coordinate a, Coordinate b) {
		return ((int) a.getOrdinate(0)) == ((int) b.getOrdinate(0))
				&& ((int) a.getOrdinate(1)) == ((int) b.getOrdinate(1));
	}

	private static boolean equalAsInts(MvtVec2d a, MvtVec2d b) {
		return ((int) a.x) == ((int) b.x) && ((int) a.y) == ((int) b.y);
	}

	private static int geomCmdBuffLenPts(int coordCount) {

		// 1 MoveTo Header, 2 parameters * coordCount
		return 1 + (coordCount * 2);
	}

	private static int geomCmdBuffLenLines(int coordCount, boolean closeEnabled) {

		// MoveTo Header, LineTo Header, Optional ClosePath Header, 2 parameters
		// * coordCount
		return 2 + (closeEnabled ? 1 : 0) + (coordCount * 2);
	}
}
