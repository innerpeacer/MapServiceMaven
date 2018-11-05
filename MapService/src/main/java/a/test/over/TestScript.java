package a.test.over;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestScript {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		String script = "echo 1";
		Process ps = Runtime.getRuntime().exec(script);

		BufferedReader br = new BufferedReader(new InputStreamReader(
				ps.getInputStream()));
		String line = null;
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
	}
}
