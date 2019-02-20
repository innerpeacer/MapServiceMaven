package cn.platalk.map.vectortile.builder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class TYFileUtils {
	static boolean makeFolders(String filePath) {
		return makeDirs(filePath);
	}

	static boolean makeDirs(String filePath) {
		String folderName = getFolderName(filePath);
		if (folderName == null || folderName.length() == 0) {
			return false;
		}

		File folder = new File(folderName);
		return (folder.exists() && folder.isDirectory()) ? true : folder
				.mkdirs();
	}

	static String getFolderName(String filePath) {
		if (filePath == null || filePath.length() == 0) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
	}

	public static boolean writeFile(String filePath, String content,
			boolean append) {
		if (content == null) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			makeDirs(filePath);
			fileWriter = new FileWriter(filePath, append);
			fileWriter.write(content);
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	public static boolean writeFile(String filePath, String content) {
		return writeFile(filePath, content, false);
	}
}
