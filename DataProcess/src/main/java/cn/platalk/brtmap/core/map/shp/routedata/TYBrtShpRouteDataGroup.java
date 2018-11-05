package cn.platalk.brtmap.core.map.shp.routedata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import cn.platalk.brtmap.core.map.base.TYIBrtShpDataManager;
import cn.platalk.brtmap.core.map.shp.routedata.TYBrtShpRouteParser.TYBrtRouteShpParserListener;

public class TYBrtShpRouteDataGroup implements TYBrtRouteShpParserListener {
	private static final String[] ROUTE_LAYERS = { "NODE", "VIRTUAL_NODE", "JUNCTION", "LINK", "VIRTUAL_LINK" };
	TYIBrtShpDataManager shpDataManager;

	List<TYBrtShpBuildingNode> nodeArray = new ArrayList<TYBrtShpBuildingNode>();
	List<TYBrtShpBuildingNode> virtualNodeArray = new ArrayList<TYBrtShpBuildingNode>();
	List<TYBrtShpBuildingNode> junctionArray = new ArrayList<TYBrtShpBuildingNode>();

	List<TYBrtShpBuildingLink> linkArray = new ArrayList<TYBrtShpBuildingLink>();
	List<TYBrtShpBuildingLink> virtualLinkArray = new ArrayList<TYBrtShpBuildingLink>();

	private int currentLayerIndex = 0;

	public TYBrtShpRouteDataGroup() {

	}

	public void setShpDataManager(TYIBrtShpDataManager manager) {
		this.shpDataManager = manager;
	}

	public void startParseingRouteShp() {
		// System.out.println("startProcessRouteShp");

		nodeArray.clear();
		virtualNodeArray.clear();
		junctionArray.clear();
		linkArray.clear();
		virtualLinkArray.clear();

		currentLayerIndex = 0;
		doParsingRouteShp();
	}

	public void didFinishParsingRouteShpDataList(List<TYBrtShpRouteRecord> records, int layer) {
		// System.out.println("didFinishParsingRouteShpDataList: " + layer);
		processShpData(records, layer);
		getNextShp();
		doParsingRouteShp();
	}

	private void processShpData(List<TYBrtShpRouteRecord> records, int layer) {
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
			return;
		}
	}

	private int getOffset(int index) {
		return 10000 * (index + 1);
	}

	private List<TYBrtShpBuildingLink> processPolylineShp(List<TYBrtShpRouteRecord> records, int layer,
			boolean isVirtual) {
		// System.out.println("processPolylineShp: " + layer);

		int offsetForID = getOffset(layer);
		List<TYBrtShpBuildingLink> resultList = new ArrayList<TYBrtShpBuildingLink>();
		for (TYBrtShpRouteRecord record : records) {
			int gid = record.geometryID + offsetForID;
			LineString line = (LineString) record.geometry;
			// double length = record.geometry.calculateLength2D();
			double length = record.geometry.getLength();

			TYBrtShpBuildingLink link = new TYBrtShpBuildingLink(gid, isVirtual, record.isOneWay());
			link.line = line;
			link.length = length;
			link.geometryData = record.geometryData;

			resultList.add(link);
		}
		// System.out.println("Links: " + resultList.size());
		return resultList;
	}

	private List<TYBrtShpBuildingNode> processPointShp(List<TYBrtShpRouteRecord> records, int layer,
			boolean isVirtual) {
		// System.out.println("processPointShp: " + layer);

		int offsetForID = getOffset(layer);
		List<TYBrtShpBuildingNode> resultList = new ArrayList<TYBrtShpBuildingNode>();
		for (TYBrtShpRouteRecord record : records) {
			Point point = (Point) record.geometry;
			int gid = record.geometryID + offsetForID;
			TYBrtShpBuildingNode node = new TYBrtShpBuildingNode(gid, isVirtual);
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
				TYBrtShpRouteParser parser = new TYBrtShpRouteParser(shpPath);
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

	private List<TYBrtShpRouteDataGroupListener> listeners = new ArrayList<TYBrtShpRouteDataGroup.TYBrtShpRouteDataGroupListener>();

	public void addDataGroupListener(TYBrtShpRouteDataGroupListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeDataGroupListener(TYBrtShpRouteDataGroupListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	private void notifyFetchDataGroup() {
		for (TYBrtShpRouteDataGroupListener listener : listeners) {
			listener.didFinishFetchDataGroup();
		}
	}

	public interface TYBrtShpRouteDataGroupListener {
		public void didFinishFetchDataGroup();
	}

}
