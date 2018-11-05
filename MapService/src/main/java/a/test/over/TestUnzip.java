package a.test.over;

import java.io.File;

import cn.platalk.brtmap.core.config.TYBrtMapEnvironment;
import cn.platalk.brtmap.utils.third.TYZipUtil;

public class TestUnzip {

	public static void main(String[] args) throws Exception {
		File zipFile = new File(TYBrtMapEnvironment.GetRawDataRootDir(),
				"07550023.zip");
		if (zipFile.exists()) {
			System.out.println("exist");
			// TYZipUtil.unzip(zipFile.getAbsolutePath(), new File(
			// TYBrtMapEnvironment.GetRawDataRootDir(), "07550023")
			// .toString(), false);
			TYZipUtil.unzip(zipFile.getAbsolutePath(),
					TYBrtMapEnvironment.GetRawDataRootDir(), false);
		} else {
			System.out.println("not exist");
		}
	}
}