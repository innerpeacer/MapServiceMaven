package a.test.local;

import java.io.File;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.ogr;

import cn.platalk.brtmap.utils.third.TYZipUtil;

public class TestRouteShpV2 {
	public static void main(String[] args) {

		String root = "File";
		File zipFile = new File(root, "07550023.zip");
		if (zipFile.exists()) {
			System.out.println("exist");
			try {
				TYZipUtil.unzip(zipFile.getAbsolutePath(), new File(root, "07550023").toString(), false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("not exist");
		}

		ogr.RegisterAll();
		// 为了支持中文路径，请添加下面这句代码
		gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
		// 为了使属性表字段支持中文，请添加下面这句
		gdal.SetConfigOption("SHAPE_ENCODING", "");

		// String strVectorFile = root +
		// "/v3/05120002/MAP_SHP/F02/F02_NODE.shp";
		// String strVectorFile = root +
		// "/v3/05120002/MAP_SHP/F02/F02_LINK.shp";
		// String strVectorFile = root +
		// "/v3/05710010/MAP_SHP/B01/B01_LINK.shp";
		String strVectorFile = root + "/v3/05710010/MAP_SHP/B01/B01_NODE.shp";

		System.out.println("Path: " + new File(strVectorFile).getAbsolutePath());
		if (new File(strVectorFile).exists()) {
			System.out.println("Exist!");
		} else {
			System.out.println("Not Exist!");
		}
		// 打开数据
		DataSource ds = ogr.Open(strVectorFile, 0);
		if (ds == null) {
			System.out.println("打开文件失败！");
			return;
		}

		System.out.println("打开文件成功！");
		System.out.println(ds.toString());

		org.gdal.ogr.Driver dv = ogr.GetDriverByName("GeoJSON");
		if (dv == null) {
			System.out.println("打开驱动失败！");
			return;
		}
		System.out.println("打开驱动成功！");
		dv.CopyDataSource(ds, root + "/node.geojson");
		// dv.CopyDataSource(ds, root + "/link.geojson");
		System.out.println("转换成功！");
	}
}
