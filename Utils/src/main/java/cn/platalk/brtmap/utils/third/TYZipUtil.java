package cn.platalk.brtmap.utils.third;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class TYZipUtil {

	static void testUnZip() {
		String zipFilePath = "File/00210100.zip";
		try {
			unzip(zipFilePath, "/Users/innerpeacer/Desktop/TestUnzip", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void zip(String srcRootDir, File file, ZipOutputStream zos)
			throws Exception {
		if (file == null) {
			return;
		}

		if (file.isFile()) {
			int count, bufferLen = 1024;
			byte data[] = new byte[bufferLen];

			String subPath = file.getAbsolutePath();
			int index = subPath.indexOf(srcRootDir);
			if (index != -1) {
				subPath = subPath.substring(srcRootDir.length()
						+ File.separator.length());
			}
			ZipEntry entry = new ZipEntry(subPath);
			zos.putNextEntry(entry);
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			while ((count = bis.read(data, 0, bufferLen)) != -1) {
				zos.write(data, 0, count);
			}
			bis.close();
			zos.closeEntry();
		} else {
			File[] childFileList = file.listFiles();
			for (int n = 0; n < childFileList.length; n++) {
				childFileList[n].getAbsolutePath().indexOf(
						file.getAbsolutePath());
				zip(srcRootDir, childFileList[n], zos);
			}
		}
	}

	public static void zip(String srcPath, String zipPath, String zipFileName)
			throws Exception {
		if (TYStringUtils.isEmpty(srcPath) || TYStringUtils.isEmpty(zipPath)
				|| TYStringUtils.isEmpty(zipFileName)) {
			System.out.println("Parameter is null");
			throw new Exception("Parameter is null");
		}
		CheckedOutputStream cos = null;
		ZipOutputStream zos = null;
		try {
			File srcFile = new File(srcPath);

			if (srcFile.isDirectory() && zipPath.indexOf(srcPath) != -1) {
				throw new Exception(
						"zipPath must not be the child directory of srcPath.");
			}

			File zipDir = new File(zipPath);
			if (!zipDir.exists() || !zipDir.isDirectory()) {
				zipDir.mkdirs();
			}

			String zipFilePath = zipPath + File.separator + zipFileName;
			File zipFile = new File(zipFilePath);
			if (zipFile.exists()) {
				SecurityManager securityManager = new SecurityManager();
				securityManager.checkDelete(zipFilePath);
				zipFile.delete();
			}

			cos = new CheckedOutputStream(new FileOutputStream(zipFile),
					new CRC32());
			zos = new ZipOutputStream(cos);

			String srcRootDir = srcPath;
			if (srcFile.isFile()) {
				int index = srcPath.lastIndexOf(File.separator);
				if (index != -1) {
					srcRootDir = srcPath.substring(0, index);
				}
			}
			zip(srcRootDir, srcFile, zos);
			zos.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void unzip(String zipFilePath, String unzipFilePath,
			boolean includeZipFileName) throws Exception {
		File zipFile = new File(zipFilePath);
		if (includeZipFileName) {
			String fileName = zipFile.getName();
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			unzipFilePath = unzipFilePath + File.separator + fileName;
		}

		File unzipFileDir = new File(unzipFilePath);
		if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
			unzipFileDir.mkdirs();
		}

		ZipEntry entry = null;
		String entryFilePath = null, entryDirPath = null;
		File entryFile = null, entryDir = null;
		int index = 0, count = 0, bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ZipFile zip = new ZipFile(zipFile);
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
		while (entries.hasMoreElements()) {
			entry = entries.nextElement();

			entryFilePath = unzipFilePath + File.separator + entry.getName();
			entryFilePath = entryFilePath.replace("/", File.separator);
			entryFilePath = entryFilePath.replace("\\", File.separator);
			index = entryFilePath.lastIndexOf(File.separator);
			if (index != -1) {
				entryDirPath = entryFilePath.substring(0, index);
			} else {
				entryDirPath = "";
			}
			entryDir = new File(entryDirPath);
			if (!entryDir.exists() || !entryDir.isDirectory()) {
				entryDir.mkdirs();
			}

			entryFile = new File(entryFilePath);
			if (entryFile.exists()) {
				entryFile.delete();
			}

			if (!entry.isDirectory()) {
				bos = new BufferedOutputStream(new FileOutputStream(entryFile));
				bis = new BufferedInputStream(zip.getInputStream(entry));
				while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
					bos.write(buffer, 0, count);
				}
				bos.flush();
				bos.close();
			} else {
				entryDir.mkdirs();
			}
		}
		zip.close();
	}
}
