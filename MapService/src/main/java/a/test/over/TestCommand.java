package a.test.over;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestCommand {

	public static void main(String[] args) {
		System.out.println("Test Command");

		try {
			// String shpath = "script/test.sh";
			// Process ps = Runtime.getRuntime().exec(shpath);
			// Process ps = Runtime.getRuntime().exec("sh script/test.sh");
			Process ps = Runtime
					.getRuntime()
					.exec("/Users/innerpeacer/.nvm/versions/node/v4.1.0/bin/node script/test.js font simhei");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					ps.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}

			BufferedReader brError = new BufferedReader(new InputStreamReader(
					ps.getErrorStream(), "gb2312"));
			String errline = null;
			while ((errline = brError.readLine()) != null) {
				System.out.println(errline);
			}

			int c = ps.waitFor();
			System.out.println("Result: " + c);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
