package cn.platalk.core.pbf.route.wrapper;

import cn.platalk.core.pbf.route.TYRoutePropertyPbf.TYRouteLinkV3PropertiesPbf;
import cn.platalk.core.pbf.route.TYRoutePropertyPbf.TYRouteNodeV3PropertiesPbf;
import cn.platalk.map.entity.base.map.TYIRouteLinkRecordV3;
import cn.platalk.map.entity.base.map.TYIRouteNodeRecord;
import cn.platalk.map.entity.base.map.TYIRouteNodeRecordV3;

import java.nio.IntBuffer;

public class IPRoutePropertiesPbfUtils {

	public static TYRouteLinkV3PropertiesPbf propertiesToPbf(TYIRouteLinkRecordV3 link) {
		TYRouteLinkV3PropertiesPbf.Builder builder = TYRouteLinkV3PropertiesPbf.newBuilder();
		builder.setLinkID(link.getLinkID());
		builder.setLength(link.getLength());
		builder.setHeadNode(link.getHeadNode());
		builder.setEndNode(link.getEndNode());
		builder.setVirtual(false);    // No virtual links in V3 route network
		builder.setOneWay(link.isOneWay());
		if (link.getLinkName() != null && link.getLinkName().length() > 0) {
			builder.setLinkName(link.getLinkName());
		}
		builder.setFloor(link.getFloor());
		builder.setLevel(link.getLevel());
		builder.setReverse(link.isReverse());
		if (link.getRoomID() != null && link.getRoomID().length() > 0) {
			builder.setRoomID(link.getRoomID());
		}
		builder.setOpen(link.isOpen());
		if (link.getOpenTime() != null && link.getOpenTime().length() > 0) {
			builder.setOpenTime(link.getOpenTime());
		}
		builder.setAllowSnap(link.isAllowSnap());
		builder.setLinkType(link.getLinkType());
		return builder.build();
	}

	public static TYRouteNodeV3PropertiesPbf propertiesToPbf(TYIRouteNodeRecordV3 node) {
		TYRouteNodeV3PropertiesPbf.Builder builder = TYRouteNodeV3PropertiesPbf.newBuilder();
		builder.setNodeID(node.getNodeID());
		builder.setVirtual(false);    // No virtual nodes in V3 route network
		if (node.getNodeName() != null && node.getNodeName().length() > 0) {
			builder.setNodeName(node.getNodeName());
		}
		if (node.getCategoryID() != null && node.getCategoryID().length() > 0) {
			builder.setCategoryID(node.getCategoryID());
		}
		builder.setFloor(node.getFloor());
		builder.setLevel(node.getLevel());
		builder.setSwitching(node.isSwitching());
		builder.setSwitchID(node.getSwitchingID());
		builder.setDirection(node.getDirection());
		builder.setNodeType(node.getNodeType());
		builder.setOpen(node.isOpen());
		if (node.getOpenTime() != null && node.getOpenTime().length() > 0) {
			builder.setOpenTime(node.getOpenTime());
		}
		if (node.getRoomID() != null && node.getRoomID().length() > 0) {
			builder.setRoomID(node.getRoomID());
		}
		return builder.build();
	}
}
