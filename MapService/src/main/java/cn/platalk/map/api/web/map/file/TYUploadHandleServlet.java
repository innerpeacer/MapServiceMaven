package cn.platalk.map.api.web.map.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.platalk.map.core.config.TYMapEnvironment;

@WebServlet("/web/UploadShpZip")
public class TYUploadHandleServlet extends HttpServlet {
	private static final long serialVersionUID = -5378881543564352448L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter outWriter = response.getWriter();

		// Default to map zip
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}

		String message = "";
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 100);
			factory.setRepository(tmpFile);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setProgressListener(new ProgressListener() {
				public void update(long pBytesRead, long pContentLength, int arg2) {
//					System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
				}
			});
			upload.setHeaderEncoding("UTF-8");
			if (!ServletFileUpload.isMultipartContent(request)) {
				return;
			}

			upload.setFileSizeMax(1024 * 1024 * 50);
			upload.setSizeMax(1024 * 1024 * 100);
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					System.out.println(name + "=" + value);
				} else {
					String filename = item.getName();
					System.out.println(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}

					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					filename = filename.substring(filename.lastIndexOf("/") + 1);
					String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
					System.out.println("上传的文件的扩展名是：" + fileExtName);

					InputStream in = item.getInputStream();
					String saveFilename = filename;

					String saveDir = TYMapEnvironment.GetRawMapDir();
					if (filename.toLowerCase().indexOf("beacon") != -1) {
						System.out.println("上传Beacon文件");
						saveDir = TYMapEnvironment.GetRawBeaconDir();
					}

					if (filename.toLowerCase().indexOf("uwb") != -1) {
						System.out.println("上传UWB文件");
						saveDir = TYMapEnvironment.GetRawUwbDir();
					}

					if (filename.toLowerCase().indexOf("theme") != -1) {
						System.out.println("上传Theme文件");
						saveDir = TYMapEnvironment.GetRawThemeDir();
					}
					String savePath = String.format("%s%s%s", saveDir, File.separator, saveFilename);
					FileOutputStream out = new FileOutputStream(savePath);

					byte[] buffer = new byte[1024];
					int len;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
					// 删除处理文件上传时生成的临时文件
					// item.delete();
					message = "文件上传成功！\n";
					message += savePath;
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			message = "单个文件超出最大值！！！";
		} catch (FileUploadBase.SizeLimitExceededException e) {
			e.printStackTrace();
			message = "上传文件的总的大小超出限制的最大值！！！";
		} catch (Exception e) {
			e.printStackTrace();
			message = "文件上传失败！";
		}

		outWriter.println(message);
		outWriter.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}
