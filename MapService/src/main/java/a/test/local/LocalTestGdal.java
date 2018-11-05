package a.test.local;

import java.io.File;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.ogr;

import cn.platalk.brtmap.core.config.TYBrtMapEnvironment;
import cn.platalk.brtmap.core.config.TYServerEnviroment;
import cn.platalk.brtmap.utils.third.TYZipUtil;

public class LocalTestGdal {
	public static void main(String[] args) {

		TYServerEnviroment.initialize();

		System.out.println("user.dir");
		System.out.println(System.getProperty("user.dir"));
		String env = System.getProperty("java.library.path");
		System.out.println("java.library.path");
		System.out.println(System.getProperty("java.library.path"));

		File zipFile = new File(TYBrtMapEnvironment.GetRawDataRootDir(), "05910001.zip");
		if (zipFile.exists()) {
			System.out.println("exist");
			try {
				TYZipUtil.unzip(zipFile.getAbsolutePath(),
						new File(TYBrtMapEnvironment.GetRawDataRootDir(), "05910001").toString(), false);
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

		String strVectorFile = "File/poly.shp";
		// String strVectorFile =
		// "/Users/innerpeacer/Dev/apache-tomcat-7.0.59/webapps/BrtMapServer/WEB-INF/File/07550023/MAP_SHP/B01/B01_ROOM.shp";
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
		dv.CopyDataSource(ds, "File/test.geojson");
		// dv.CopyDataSource(ds,
		// "/Users/innerpeacer/Dev/apache-tomcat-7.0.59/webapps/BrtMapServer/WEB-INF/File/test.geojson");
		System.out.println("转换成功！");

	}
}
