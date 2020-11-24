package cn.platalk.core.pbf.route.wrapper;


import cn.platalk.core.pbf.route.TYRouteNetworkPbf;
import cn.platalk.core.pbf.route.TYRouteNetworkPbf.TYRouteNetworkV3Pbf;
import cn.platalk.core.pbf.route.TYRouteNetworkPbf.TYRouteLinkV3Pbf;
import cn.platalk.core.pbf.route.TYRouteNetworkPbf.TYRouteNodeV3Pbf;
import cn.platalk.map.entity.base.map.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.map.TYIRouteNodeRecordV3;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import java.util.List;

public class TYRouteNetworkPbfBuilder {
	static WKBReader reader = new WKBReader();

	public static TYRouteNetworkV3Pbf generateRouteNetworkObject(String buildingID, List<TYIRouteLinkRecordV3> linkList, List<TYIRouteNodeRecordV3> nodeList) {
		TYRouteNetworkV3Pbf.Builder routeNetworkBuilder = TYRouteNetworkV3Pbf.newBuilder();
		routeNetworkBuilder.setBuildingID(buildingID);
		for (TYIRouteLinkRecordV3 link : linkList) {
			routeNetworkBuilder.addLinks(buildLinkV3Pbf(link));
		}
		for (TYIRouteNodeRecordV3 node : nodeList) {
			routeNetworkBuilder.addNodes(buildNodeV3Pbf(node));
		}
		return routeNetworkBuilder.build();
	}

	static TYRouteLinkV3Pbf buildLinkV3Pbf(TYIRouteLinkRecordV3 link) {
		TYRouteLinkV3Pbf.Builder linkBuilder = TYRouteLinkV3Pbf.newBuilder();
		try {
			LineString ls = (LineString) reader.read(link.getGeometryData());
			linkBuilder.setGeometry(IPRouteGeometryPbfUtils.lineStringToPbf(ls));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		linkBuilder.setProperties(IPRoutePropertiesPbfUtils.propertiesToPbf(link));
		return linkBuilder.build();
	}

	static TYRouteNodeV3Pbf buildNodeV3Pbf(TYIRouteNodeRecordV3 node) {
		TYRouteNodeV3Pbf.Builder nodeBuilder = TYRouteNodeV3Pbf.newBuilder();
		try {
			Point p = (Point) reader.read(node.getGeometryData());
			nodeBuilder.setGeometry(IPRouteGeometryPbfUtils.pointToPbf(p));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		nodeBuilder.setProperties(IPRoutePropertiesPbfUtils.propertiesToPbf(node));
		return nodeBuilder.build();
	}
}
