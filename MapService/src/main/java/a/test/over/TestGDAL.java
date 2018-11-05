package a.test.over;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gdal.gdal.gdal;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.ogr;

import cn.platalk.brtmap.core.config.TYBrtMapEnvironment;
import cn.platalk.brtmap.utils.third.TYZipUtil;

@WebServlet("/testGdal")
public class TestGDAL extends HttpServlet {
	private static final long serialVersionUID = -3535588845108542020L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("user.dir");
		System.out.println(System.getProperty("user.dir"));
		String env = System.getProperty("java.library.path");
		System.out.println("java.library.path");
		System.out.println(System.getProperty("java.library.path"));

		File zipFile = new File(TYBrtMapEnvironment.GetRawDataRootDir(), "07550023.zip");
		if (zipFile.exists()) {
			System.out.println("exist");
			try {
				TYZipUtil.unzip(zipFile.getAbsolutePath(),
						new File(TYBrtMapEnvironment.GetRawDataRootDir(), "07550023").toString(), false);
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

		// String strVectorFile = "File/poly.shp";
		// String strVectorFile =
		// "/Users/innerpeacer/Dev/apache-tomcat-7.0.59/webapps/BrtMapServer/WEB-INF/File/07550023/MAP_SHP/B01/B01_ROOM.shp";
		String strVectorFile = "/usr/local/apache-tomcat-7.0.81/webapps/BrtMapServer/WEB-INF/File/07550023/MAP_SHP/B01/B01_ROOM.shp";
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
		// dv.CopyDataSource(ds, "File/test.geojson");
		// dv.CopyDataSource(ds,
		// "/Users/innerpeacer/Dev/apache-tomcat-7.0.59/webapps/BrtMapServer/WEB-INF/File/test.geojson");
		dv.CopyDataSource(ds, "/usr/local/apache-tomcat-7.0.81/webapps/BrtMapServer/WEB-INF/File/test.geojson");

		System.out.println("转换成功！");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Test");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
