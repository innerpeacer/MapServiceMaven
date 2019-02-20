package cn.platalk.map.entity.base.impl;

class IPEncryption {
	public static final String TAG = "Encryption";

	public static String KEY = "6^)(9-p35@%3#4S!4S0)$Y%%^&5(j.&^&o(*0)$Y%!#O@*GpG@=+@j.&6^)(0-=+";
	public static String PASSWORD_FOR_CONTENT = "innerpeacer-content";

	static final int BUFFERLEN = 1024;

	public static String encryptString(String originalName, String key) {
		byte[] PASSVALUE = PASSWORD_FOR_CONTENT.getBytes();
		byte[] KEYVALUE = key.getBytes();

		int keyLength = KEYVALUE.length;
		int pa_pos = 0;

		for (int i = 0; i < KEYVALUE.length; i++) {
			KEYVALUE[i] ^= PASSVALUE[pa_pos];
			pa_pos++;
			if (pa_pos == PASSVALUE.length) {
				pa_pos = 0;
			}
		}
		// ============================================================

		byte[] originalBytes = originalName.getBytes();
		int oLength = originalBytes.length;
		byte[] buffer = new byte[oLength];
		System.arraycopy(originalBytes, 0, buffer, 0, oLength);

		int key_pos = 0;
		for (int i = 0; i < oLength; i++) {
			buffer[i] ^= KEYVALUE[key_pos];
			key_pos++;
			if (key_pos == keyLength) {
				key_pos = 0;
			}

		}
		return new String(buffer);
	}

	public static byte[] encryptBytes(byte[] originalBytes) {
		return encryptBytes(originalBytes, KEY);
	}

	public static byte[] encryptBytes(byte[] originalBytes, String key) {
		byte[] PASSVALUE = PASSWORD_FOR_CONTENT.getBytes();
		byte[] KEYVALUE = key.getBytes();

		int keyLength = KEYVALUE.length;
		int pa_pos = 0;

		for (int i = 0; i < KEYVALUE.length; i++) {
			KEYVALUE[i] ^= PASSVALUE[pa_pos];
			pa_pos++;
			if (pa_pos == PASSVALUE.length) {
				pa_pos = 0;
			}
		}

		int oLength = originalBytes.length;
		byte[] encryptedBytes = new byte[oLength];
		System.arraycopy(originalBytes, 0, encryptedBytes, 0, oLength);
		int key_pos = 0;
		for (int i = 0; i < oLength; i++) {
			encryptedBytes[i] ^= KEYVALUE[key_pos];
			key_pos++;
			if (key_pos == keyLength) {
				key_pos = 0;
			}
		}
		return encryptedBytes;
	}

	// public static void encryptFile(String original, String encrypted,
	// String password) {
	// byte[] PASSVALUE = password.getBytes();
	// String keyString = new String(KEY);
	// byte[] KEYVALUE = keyString.getBytes();
	//
	// int keyLength = KEYVALUE.length;
	//
	// int pa_pos = 0;
	//
	// for (int i = 0; i < KEYVALUE.length; i++) {
	// KEYVALUE[i] ^= PASSVALUE[pa_pos];
	// pa_pos++;
	// if (pa_pos == PASSVALUE.length) {
	// pa_pos = 0;
	// }
	// }
	//
	// RandomAccessFile input;
	// RandomAccessFile output;
	// try {
	// input = new RandomAccessFile(original, "rw");
	// output = new RandomAccessFile(encrypted, "rw");
	//
	// byte buffer[] = new byte[BUFFERLEN];
	//
	// int c;
	//
	// while ((c = input.read(buffer)) != -1) {
	// int key_pos = 0;
	// for (int i = 0; i < c; i++) {
	// buffer[i] ^= KEYVALUE[key_pos];
	// key_pos++;
	// if (key_pos == keyLength) {
	// key_pos = 0;
	// }
	// }
	// output.write(buffer, 0, c);
	// }
	//
	// input.close();
	// output.close();
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

}
