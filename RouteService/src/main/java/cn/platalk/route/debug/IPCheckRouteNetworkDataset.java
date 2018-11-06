package cn.platalk.route.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;

import cn.platalk.brtmap.entity.base.TYIRouteLinkRecord;
import cn.platalk.brtmap.entity.base.TYIRouteNodeRecord;

public class IPCheckRouteNetworkDataset {

	private List<IPCheckerLink> m_linkArray = new ArrayList<IPCheckerLink>();
	private List<IPCheckerLink> m_virtualLinkArray = new ArrayList<IPCheckerLink>();
	public List<IPCheckerNode> m_nodeArray = new ArrayList<IPCheckerNode>();
	private List<IPCheckerNode> m_virtualNodeArray = new ArrayList<IPCheckerNode>();

	private Map<String, IPCheckerLink> m_allLinkDict = new HashMap<String, IPCheckerLink>();
	private Map<Integer, IPCheckerNode> m_allNodeDict = new HashMap<Integer, IPCheckerNode>();

	GeometryFactory factory = new GeometryFactory();

	private List<IPCheckerNode> nodeFinished = new ArrayList<IPCheckerNode>();
	private List<IPCheckerNode> nodeToCheck = new ArrayList<IPCheckerNode>();

	public IPCheckRouteNetworkDataset(List<TYIRouteNodeRecord> nodes, List<TYIRouteLinkRecord> links) {
		extractNodes(nodes);
		extractLinks(links);
		processNodesAndLinks();
	}

	private void print(String s) {
		System.out.println(s);
	}

	public void topologicalCheck() {
		print("============== topologicalCheck ================");
		print(m_allLinkDict.size() + " Links");
		print(m_allNodeDict.size() + " Nodes");

		duplicatCheck();
		missingCheck();

		// checkDataset();
		// printData();

	}

	void printData() {
		String linkID = "3003430032";
		IPCheckerLink link = m_allLinkDict.get(linkID);
		if (link == null) {
			print("Link: " + linkID + " is null");
		} else {
			print(link.toString());
		}
	}

	void checkDataset() {
		print("checkDataset");
		// while (true) {
		//
		// }
		nodeFinished.clear();
		nodeToCheck.addAll(m_allNodeDict.values());

		int partCount = 0;
		while (true) {
			if (nodeToCheck.size() > 0) {
				partCount++;
				searchNode(nodeToCheck.get(0));
			} else {
				break;
			}
		}

		// for (IPCheckerNode node : m_allNodeDict.values()) {
		// searchNode(node);
		// break;
		// }
		print("Node Finished: " + nodeFinished.size());
		print(partCount + " parts Found");

		int colorCount[] = { 0, 0, 0 };
		for (IPCheckerNode node : m_allNodeDict.values()) {
			switch (node.color) {
			case None:
				colorCount[0]++;
				break;
			case Found:
				colorCount[1]++;
				break;

			case Finished:
				colorCount[2]++;
				break;

			default:
				break;
			}
		}

		print("Node Status: " + colorCount[0] + ", " + colorCount[1] + ", " + colorCount[2]);
	}

	int index = 0;

	private void searchNode(IPCheckerNode node) {
		if (node.color == TYCheckColor.Finished) {
			return;
		}

		if (node.color == TYCheckColor.None) {
			node.color = TYCheckColor.Found;
			for (IPCheckerLink link : node.adjacencies) {
				IPCheckerNode nextNode = link.nextNode;
				if (nextNode.color == TYCheckColor.None) {
					searchNode(nextNode);
				}
			}
			node.color = TYCheckColor.Finished;
			nodeFinished.add(node);
			nodeToCheck.remove(node);
			// print(index++ + ": " + node.getNodeID());
		}

		if (node.color == TYCheckColor.Found) {

		}

	}

	private void duplicatCheck() {
		Set<String> nodeCoordSet = new HashSet<String>();
		for (IPCheckerNode node : m_allNodeDict.values()) {
			String cs = String.format("%f%f", node.getPos().getX(), node.getPos().getY());
			nodeCoordSet.add(cs);
		}

		if (nodeCoordSet.size() != m_allNodeDict.size()) {
			print("Error: Duplicate Node!");
		} else {
			print("No Duplicate Node");
		}

		Set<String> linkSet = new HashSet<String>();
		for (IPCheckerLink link : m_allLinkDict.values()) {
			String cs = String.format("%d%d", link.currentNodeID, link.nextNodeID);

			if (linkSet.contains(cs)) {
				// print("Duplicat Link Error: " + link.toString());
			}
			linkSet.add(cs);
		}

		if (linkSet.size() != m_allLinkDict.size()) {
			print("Error: Duplicate Link!");
		} else {
			print("No Duplicate Link");
		}

		Map<String, List<IPCheckerLink>> duplicatMap = new HashMap<String, List<IPCheckerLink>>();
		for (IPCheckerLink link : m_allLinkDict.values()) {
			String cs = String.format("%d%d", link.currentNodeID, link.nextNodeID);

			List<IPCheckerLink> list = duplicatMap.get(cs);
			if (list == null) {
				list = new ArrayList<IPCheckerLink>();
				duplicatMap.put(cs, list);
			}
			list.add(link);
		}

		int count = 0;
		for (List<IPCheckerLink> list : duplicatMap.values()) {
			if (list.size() > 1) {
				count++;
				print("============================");
				for (IPCheckerLink link : list) {
					print(link.toString());
				}
			}
		}
		print(count + "");
	}

	private void missingCheck() {
		print("missingCheck");
		for (IPCheckerLink link : m_allLinkDict.values()) {
			if (link.nextNode == null) {
				print("Bad Link: " + link.getLinkID());
			}
		}
	}

	@Override
	public String toString() {
		return String.format("Route Network Dataset: %d Links and %d Nodes",
				m_linkArray.size() + m_virtualLinkArray.size(), m_nodeArray.size() + m_virtualNodeArray.size());
	}

	protected void extractNodes(List<TYIRouteNodeRecord> nodes) {
		WKBReader reader = new WKBReader();
		try {
			for (int i = 0; i < nodes.size(); i++) {
				TYIRouteNodeRecord nodeRecord = nodes.get(i);
				IPCheckerNode node = new IPCheckerNode(nodeRecord.getNodeID(), nodeRecord.isVirtual());

				Point pos = (Point) reader.read(nodeRecord.getGeometryData());
				node.setPos(pos);

				m_allNodeDict.put(node.getNodeID(), node);

				if (node.isVirtual()) {
					m_virtualNodeArray.add(node);
				} else {
					m_nodeArray.add(node);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void extractLinks(List<TYIRouteLinkRecord> links) {
		WKBReader reader = new WKBReader();
		try {
			Map<String, List<IPCheckerLink>> duplicatMap = new HashMap<String, List<IPCheckerLink>>();
			for (IPCheckerLink link : m_allLinkDict.values()) {
				String cs = String.format("%d%d", link.currentNodeID, link.nextNodeID);
				List<IPCheckerLink> list = duplicatMap.get(cs);
				if (list == null) {
					list = new ArrayList<IPCheckerLink>();
					duplicatMap.put(cs, list);
				}
				list.add(link);
			}

			for (int i = 0; i < links.size(); ++i) {
				TYIRouteLinkRecord linkRecord = links.get(i);
				LineString line = (LineString) reader.read(linkRecord.getGeometryData());

				IPCheckerLink forwardLink = new IPCheckerLink(linkRecord.getLinkID(), linkRecord.isVirtual());
				forwardLink.currentNodeID = linkRecord.getHeadNode();
				forwardLink.nextNodeID = linkRecord.getEndNode();
				forwardLink.length = linkRecord.getLength();

				forwardLink.setLine(line);

				String forwardLinkKey = forwardLink.currentNodeID + "" + forwardLink.nextNodeID;
				m_allLinkDict.put(forwardLinkKey, forwardLink);

				if (forwardLink.isVirtual()) {
					m_virtualLinkArray.add(forwardLink);
				} else {
					m_linkArray.add(forwardLink);
				}

				// =============================
				List<IPCheckerLink> list = duplicatMap.get(forwardLinkKey);
				if (list == null) {
					list = new ArrayList<IPCheckerLink>();
					duplicatMap.put(forwardLinkKey, list);
				}
				list.add(forwardLink);
				// =============================

				if (!linkRecord.isOneWay()) {
					IPCheckerLink reverseLink = new IPCheckerLink(linkRecord.getLinkID(), linkRecord.isVirtual());
					reverseLink.currentNodeID = linkRecord.getEndNode();
					reverseLink.nextNodeID = linkRecord.getHeadNode();
					reverseLink.length = linkRecord.getLength();
					reverseLink.setLine((LineString) line.reverse());

					String reverseLinkKey = reverseLink.currentNodeID + "" + reverseLink.nextNodeID;
					m_allLinkDict.put(reverseLinkKey, reverseLink);

					if (reverseLink.isVirtual()) {
						m_virtualLinkArray.add(reverseLink);
					} else {
						m_linkArray.add(reverseLink);
					}

					list = duplicatMap.get(reverseLinkKey);
					if (list == null) {
						list = new ArrayList<IPCheckerLink>();
						duplicatMap.put(reverseLinkKey, list);
					}
					list.add(reverseLink);
				}
			}

			int count = 0;
			for (List<IPCheckerLink> list : duplicatMap.values()) {
				if (list.size() > 1) {
					count++;
					print("============================");
					for (IPCheckerLink link : list) {
						print(link.toString());
					}
				}
			}
			print(count + "");

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void processNodesAndLinks() {
		for (IPCheckerLink link : m_allLinkDict.values()) {
			IPCheckerNode headNode = m_allNodeDict.get(link.currentNodeID);
			IPCheckerNode endNode = m_allNodeDict.get(link.nextNodeID);

			headNode.addLink(link);
			link.nextNode = endNode;
		}
	}

}
