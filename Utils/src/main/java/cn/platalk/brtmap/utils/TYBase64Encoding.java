package cn.platalk.brtmap.utils;

import org.apache.commons.codec.binary.Base64;

public class TYBase64Encoding {
	// public static void main(String[] args) throws
	// UnsupportedEncodingException {
	// String originalString = "Hello World";
	// System.out.println("originalString: " + originalString);
	//
	// byte[] originalBytes = originalString.getBytes();
	// String encodeResult = Base64.encodeBase64String(originalBytes);
	// System.out.println("encodeResult: " + encodeResult);
	//
	// for (int i = 0; i < originalBytes.length; i++) {
	// System.out.print(originalBytes[i]);
	// }
	//
	// byte[] decodeBytes = Base64.decodeBase64(encodeResult);
	// String decodeString = new String(decodeBytes);
	// System.out.println("decodeResult: " + decodeString);
	//
	// for (int i = 0; i < decodeBytes.length; i++) {
	// System.out.print(decodeBytes[i]);
	// }
	// }

	public static String encodeBytes(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public static byte[] decodeString(String encodedString) {
		return Base64.decodeBase64(encodedString);
	}

	public static String encodeStringForString(String str) {
		return Base64.encodeBase64String(str.getBytes());
	}

	public static String decodeStringForString(String str) {
		return new String(Base64.decodeBase64(str));
	}

}
