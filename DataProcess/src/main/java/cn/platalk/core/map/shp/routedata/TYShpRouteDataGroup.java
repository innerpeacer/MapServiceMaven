package cn.platalk.core.map.shp.routedata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import cn.platalk.core.map.base.TYIShpDataManager;
import cn.platalk.core.map.shp.routedata.TYShpRouteParser.TYBrtRouteShpParserListener;

public class TYShpRouteDataGroup implements TYBrtRouteShpParserListener {
	private static final String[] ROUTE_LAYERS = { "NODE", "VIRTUAL_NODE", "JUNCTION", "LINK", "VIRTUAL_LINK" };
	TYIShpDataManager shpDataManager;

	final List<TYShpBuildingNode> nodeArray = new ArrayList<>();
	final List<TYShpBuildingNode> virtualNodeArray = new ArrayList<>();
	final List<TYShpBuildingNode> junctionArray = new ArrayList<>();

	final List<TYShpBuildingLink> linkArray = new ArrayList<>();
	final List<TYShpBuildingLink> virtualLinkArray = new ArrayList<>();

	private int currentLayerIndex = 0;

	public TYShpRouteDataGroup() {

	}

	public void setShpDataManager(TYIShpDataManager manager) {
		this.shpDataManager = manager;
	}

	public void startParsingRouteShp() {
		// System.out.println("startProcessRouteShp");

		nodeArray.clear();
		virtualNodeArray.clear();
		junctionArray.clear();
		linkArray.clear();
		virtualLinkArray.clear();

		currentLayerIndex = 0;
		doParsingRouteShp();
	}

	public void didFinishParsingRouteShpDataList(List<TYShpRouteRecord> records, int layer) {
		// System.out.println("didFinishParsingRouteShpDataList: " + layer);
		processShpData(records, layer);
		getNextShp();
		doParsingRouteShp();
	}

	private void processShpData(List<TYShpRouteRecord> records, int layer) {
		switch (layer) {
		case 0:
			nodeArray.addAll(processPointShp(records, layer, false));
			break;

		case 1:
			virtualNodeArray.addAll(processPointShp(records, layer, true));
			break;

		case 2:
			junctionArray.addAll(processPointShp(records, layer, false));
			break;

		case 3:
			linkArray.addAll(processPolylineShp(records, layer, false));
			break;

		case 4:
			virtualLinkArray.addAll(processPolylineShp(records, layer, true));
			break;

		default:
		}
	}

	private int getOffset(int index) {
		return 10000 * (index + 1);
	}

	private List<TYShpBuildingLink> processPolylineShp(List<TYShpRouteRecord> records, int layer,
			boolean isVirtual) {
		// System.out.println("processPolylineShp: " + layer);

		int offsetForID = getOffset(layer);
		List<TYShpBuildingLink> resultList = new ArrayList<>();
		for (TYShpRouteRecord record : records) {
			int gid = record.geometryID + offsetForID;
			LineString line = (LineString) record.geometry;
			// double length = record.geometry.calculateLength2D();
			double length = record.geometry.getLength();

			TYShpBuildingLink link = new TYShpBuildingLink(gid, isVirtual, record.isOneWay());
			link.line = line;
			link.length = length;
			link.geometryData = record.geometryData;

			resultList.add(link);
		}
		// System.out.println("Links: " + resultList.size());
		return resultList;
	}

	private List<TYShpBuildingNode> processPointShp(List<TYShpRouteRecord> records, int layer,
			boolean isVirtual) {
		// System.out.println("processPointShp: " + layer);

		int offsetForID = getOffset(layer);
		List<TYShpBuildingNode> resultList = new ArrayList<>();
		for (TYShpRouteRecord record : records) {
			Point point = (Point) record.geometry;
			int gid = record.geometryID + offsetForID;
			TYShpBuildingNode node = new TYShpBuildingNode(gid, isVirtual);
			node.pos = point;
			node.geometryData = record.geometryData;
			resultList.add(node);
		}
		// System.out.println("Nodes: " + resultList.size());
		return resultList;
	}

	@Override
	public void didFailedParsingRouteDataList(Throwable error) {

	}

	private void doParsingRouteShp() {
		// System.out.println("doParsingRouteShp");

		if (hasNextShp()) {
			String layer = ROUTE_LAYERS[currentLayerIndex];
			String shpPath = shpDataManager.getRouteShpPath(layer);

			if (new File(shpPath).exists()) {
				// System.out.println("Process : " + layer);
				TYShpRouteParser parser = new TYShpRouteParser(shpPath);
				parser.addParserListener(this);
				parser.setLayerIndex(currentLayerIndex);
				parser.parse();
			} else {
				System.out.println("Not Exist: " + layer);
				getNextShp();
				doParsingRouteShp();
			}
		} else {
			// System.out.println("Route Over!");
			notifyFetchDataGroup();
		}
	}

	private void getNextShp() {
		currentLayerIndex++;
	}

	private boolean hasNextShp() {
		return currentLayerIndex != ROUTE_LAYERS.length;
	}

	private final List<TYBrtShpRouteDataGroupListener> listeners = new ArrayList<>();

	public void addDataGroupListener(TYBrtShpRouteDataGroupListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeDataGroupListener(TYBrtShpRouteDataGroupListener listener) {
		listeners.remove(listener);
	}

	private void notifyFetchDataGroup() {
		for (TYBrtShpRouteDataGroupListener listener : listeners) {
			listener.didFinishFetchDataGroup();
		}
	}

	public interface TYBrtShpRouteDataGroupListener {
		void didFinishFetchDataGroup();
	}

}
