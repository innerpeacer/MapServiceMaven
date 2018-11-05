// package cn.platalk.brtmap.api.web.map;
//
// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileReader;
// import java.io.IOException;
// import java.io.PrintWriter;
//
// import javax.servlet.ServletContext;
// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.gdal.gdal.gdal;
// import org.gdal.ogr.DataSource;
// import org.gdal.ogr.ogr;
//
// @WebServlet("/checkGdal")
// public class CheckGdalServlet extends HttpServlet {
// private static final long serialVersionUID = -3535588845108542020L;
//
// public void doGet(HttpServletRequest request, HttpServletResponse response)
// throws ServletException, IOException {
// response.setContentType("text/json;charset=UTF-8");
//
// ServletContext context = request.getServletContext();
//
// System.out.println("context path: " + context.getContextPath());
//
// System.out.println("user.dir");
// System.out.println(System.getProperty("user.dir"));
// String env = System.getProperty("java.library.path");
// System.out.println("java.library.path");
// System.out.println(System.getProperty("java.library.path"));
//
// ogr.RegisterAll();
// // 为了支持中文路径，请添加下面这句代码
// gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");
// // 为了使属性表字段支持中文，请添加下面这句
// gdal.SetConfigOption("SHAPE_ENCODING", "");
//
// String strVectorFile = context.getRealPath("File/shp/F01_ROOM.shp");
// System.out.println("Path: " + new File(strVectorFile).getAbsolutePath());
// if (new File(strVectorFile).exists()) {
// System.out.println("Exist!");
// } else {
// System.out.println("Not Exist!");
// }
// // 打开数据
// DataSource ds = ogr.Open(strVectorFile, 0);
// if (ds == null) {
// System.out.println("打开文件失败！");
// return;
// }
//
// System.out.println("打开文件成功！");
// System.out.println(ds.toString());
//
// org.gdal.ogr.Driver dv = ogr.GetDriverByName("GeoJSON");
// if (dv == null) {
// System.out.println("打开驱动失败！");
// return;
// }
// System.out.println("打开驱动成功！");
// dv.CopyDataSource(ds, context.getRealPath("File/test.geojson"));
// System.out.println("转换成功！");
//
// File f = new File(context.getRealPath("File/test.geojson"));
//
// FileReader reader = new FileReader(f);
// BufferedReader bReader = new BufferedReader(reader);
// StringBuilder sb = new StringBuilder();
// String s = "";
// while ((s = bReader.readLine()) != null) {
// sb.append(s + "\n");//
// System.out.println(s);
// }
// bReader.close();
//
// response.setContentType("text/html");
// PrintWriter out = response.getWriter();
// out.println(sb.toString());
// out.flush();
// out.close();
// }
//
// public void doPost(HttpServletRequest request, HttpServletResponse response)
// throws ServletException, IOException {
// doGet(request, response);
// }
//
// }
