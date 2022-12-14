package cn.platalk.map.route.core_v3;

import java.util.ArrayList;
import java.util.List;

import cn.platalk.map.entity.base.impl.map.TYLocalPoint;
import cn.platalk.map.entity.base.map.*;
import com.vividsolutions.jts.geom.GeometryFactory;


public class TYServerRouteManagerV3 {
	static final String TAG = TYServerRouteManagerV3.class.getSimpleName();

	TYLocalPoint startPoint;
	TYLocalPoint endPoint;

	final IPServerRouteNetworkDatasetV3 networkDataset;
	GeometryFactory factory = new GeometryFactory();
	final List<TYIMapInfo> allMapInfoArray = new ArrayList<>();

	public TYServerRouteManagerV3(TYIBuilding building, List<TYIMapInfo> mapInfoArray, List<TYIRouteNodeRecordV3> nodes,
								  List<TYIRouteLinkRecordV3> links, List<TYIMapDataFeatureRecord> mapdata) {
		allMapInfoArray.addAll(mapInfoArray);
		networkDataset = new IPServerRouteNetworkDatasetV3(allMapInfoArray, nodes, links, mapdata);
	}

	public synchronized IPServerRouteResultObjectV3 requestRoute(TYLocalPoint start, TYLocalPoint end,
			TYServerRouteOptions options) {
		// options.setUseSameFloor(true);
		// System.out.println(options.isUseSameFloor());
		return networkDataset.getShortestPathV3(start, end, options);
	}
}
