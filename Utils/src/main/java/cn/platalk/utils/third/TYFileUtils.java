package cn.platalk.utils.third;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class TYFileUtils {

	public final static String FILE_EXTENSION_SEPARATOR = ".";

	public static StringBuilder readFile(String filePath, String charsetName) {
		File file = new File(filePath);
		StringBuilder fileContent = new StringBuilder();
		if (file == null || !file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(
					file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!fileContent.toString().equals("")) {
					fileContent.append("\r\n");
				}
				fileContent.append(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	public static boolean writeFile(String filePath, String content,
			boolean append) {
		if (TYStringUtils.isEmpty(content)) {
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

	public static boolean writeFile(String filePath, List<String> contentList,
			boolean append) {
		if (TYListUtils.isEmpty(contentList)) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			makeDirs(filePath);
			fileWriter = new FileWriter(filePath, append);
			int i = 0;
			for (String line : contentList) {
				if (i++ > 0) {
					fileWriter.write("\r\n");
				}
				fileWriter.write(line);
			}
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

	public static boolean writeFile(String filePath, List<String> contentList) {
		return writeFile(filePath, contentList, false);
	}

	public static boolean writeFile(String filePath, InputStream stream) {
		return writeFile(filePath, stream, false);
	}

	public static boolean writeFile(String filePath, InputStream stream,
			boolean append) {
		return writeFile(filePath != null ? new File(filePath) : null, stream,
				append);
	}

	public static boolean writeFile(File file, InputStream stream) {
		return writeFile(file, stream, false);
	}

	@SuppressWarnings("SameReturnValue")
	public static boolean writeFile(File file, InputStream stream,
									boolean append) {
		OutputStream o = null;
		try {
			makeDirs(file.getAbsolutePath());
			o = new FileOutputStream(file, append);
			byte[] data = new byte[1024];
			int length = -1;
			while ((length = stream.read(data)) != -1) {
				o.write(data, 0, length);
			}
			o.flush();
			return true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (o != null) {
				try {
					o.close();
					stream.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	public static boolean copyFile(String sourceFilePath, String destFilePath) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(sourceFilePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		}
		return writeFile(destFilePath, inputStream);
	}

	public static List<String> readFileToList(String filePath,
			String charsetName) {
		File file = new File(filePath);
		List<String> fileContent = new ArrayList<>();
		if (file == null || !file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(
					file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	public static String getFileNameWithoutExtension(String filePath) {
		if (TYStringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int extPos = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePos = filePath.lastIndexOf(File.separator);
		if (filePos == -1) {
			return (extPos == -1 ? filePath : filePath.substring(0,
					extPos));
		}
		if (extPos == -1) {
			return filePath.substring(filePos + 1);
		}
		return (filePos < extPos ? filePath.substring(filePos + 1,
				extPos) : filePath.substring(filePos + 1));
	}

	public static String getFileName(String filePath) {
		if (TYStringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePos = filePath.lastIndexOf(File.separator);
		return (filePos == -1) ? filePath : filePath.substring(filePos + 1);
	}

	public static String getFolderName(String filePath) {
		if (TYStringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePos = filePath.lastIndexOf(File.separator);
		return (filePos == -1) ? "" : filePath.substring(0, filePos);
	}

	public static String getFileExtension(String filePath) {
		if (TYStringUtils.isBlank(filePath)) {
			return filePath;
		}

		int extPos = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePos = filePath.lastIndexOf(File.separator);
		if (extPos == -1) {
			return "";
		}
		return (filePos >= extPos) ? "" : filePath.substring(extPos + 1);
	}

	public static boolean makeDirs(String filePath) {
		String folderName = getFolderName(filePath);
		if (TYStringUtils.isEmpty(folderName)) {
			return false;
		}

		File folder = new File(folderName);
		return (folder.exists() && folder.isDirectory()) || folder
				.mkdirs();
	}

	public static boolean makeFolders(String filePath) {
		return makeDirs(filePath);
	}

	public static boolean isFileExist(String filePath) {
		if (TYStringUtils.isBlank(filePath)) {
			return false;
		}

		File file = new File(filePath);
		return (file.exists() && file.isFile());
	}

	public static boolean isFolderExist(String directoryPath) {
		if (TYStringUtils.isBlank(directoryPath)) {
			return false;
		}

		File dire = new File(directoryPath);
		return (dire.exists() && dire.isDirectory());
	}

	@SuppressWarnings("UnusedReturnValue")
	public static boolean deleteFile(String path) {
		if (TYStringUtils.isBlank(path)) {
			return true;
		}

		File file = new File(path);
		if (!file.exists()) {
			return true;
		}

		if (file.isFile()) {
			return file.delete();
		}

		if (!file.isDirectory()) {
			return false;
		}

		for (File f : file.listFiles()) {
			if (f.isFile()) {
				f.delete();
			} else if (f.isDirectory()) {
				deleteFile(f.getAbsolutePath());
			}
		}
		return file.delete();
	}

	public static long getFileSize(String path) {
		if (TYStringUtils.isBlank(path)) {
			return -1;
		}
		File file = new File(path);
		return (file.exists() && file.isFile() ? file.length() : -1);
	}
}
