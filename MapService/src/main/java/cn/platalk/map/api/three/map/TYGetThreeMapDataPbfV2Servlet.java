package cn.platalk.map.api.three.map;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.platalk.map.caching.TYCachingPool;
import cn.platalk.map.caching.TYCachingType;
import cn.platalk.map.entity.base.impl.map.TYBuilding;
import cn.platalk.map.entity.base.impl.map.TYMapDataFeatureRecord;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

import cn.platalk.core.pbf.threedata.TYThreeDataPbf.ThreeDataPbf;
import cn.platalk.core.pbf.threedata.wrapper.TYThreePbfDataBuilder;
import cn.platalk.mysql.TYMysqlDBHelper;
import cn.platalk.servlet.TYBaseHttpServlet;
import cn.platalk.servlet.TYParameterChecker;

@WebServlet("/web/three/GetMapDataPbfV2")
public class TYGetThreeMapDataPbfV2Servlet extends TYBaseHttpServlet {

    private static final long serialVersionUID = -652247259445376881L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        System.out.println("request three mapdata");
        String buildingID = request.getParameter("buildingID");

        if (!TYParameterChecker.isValidBuildingID(buildingID)) {
            respondError(request, response, errorDescriptionInvalidBuildingID(buildingID));
            return;
        }

        TYBuilding building = TYMysqlDBHelper.getBuilding(buildingID);
        if (building == null) {
            respondError(request, response, errorDescriptionNotExistBuildingID(buildingID));
            return;
        }

        ThreeDataPbf dataPbf;
        if (TYCachingPool.existDataID(buildingID, TYCachingType.ThreeDataPbf)) {
            dataPbf = (ThreeDataPbf) TYCachingPool.getCachingData(buildingID, TYCachingType.ThreeDataPbf);
        } else {
            List<TYMapDataFeatureRecord> mapDataRecordList = TYMysqlDBHelper.getMapDataRecords(buildingID);
            List<TYMapDataFeatureRecord> optimizedList = TYThreeFeatureOptimizer.optimize(mapDataRecordList);
            List<TYMapDataFeatureRecord> simplifiedList = new ArrayList<>();
            for (TYMapDataFeatureRecord record : optimizedList) {
                Geometry g = record.getGeometryData();
                Geometry sg = TopologyPreservingSimplifier.simplify(g, 0);
                record.setGeometryData(sg);
                simplifiedList.add(record);
            }

            TYThreePbfDataBuilder builder = new TYThreePbfDataBuilder(building);
            dataPbf = builder.buildingThreeDataPbf(simplifiedList);
            TYCachingPool.setCachingData(buildingID, dataPbf, TYCachingType.ThreeDataPbf);
        }

        OutputStream output = response.getOutputStream();
        dataPbf.writeTo(output);
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }
}
