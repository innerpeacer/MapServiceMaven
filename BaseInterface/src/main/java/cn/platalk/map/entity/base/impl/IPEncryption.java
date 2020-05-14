package cn.platalk.map.entity.base.impl;

class IPEncryption {
	public static final String TAG = "Encryption";

	public static final String KEY = "6^)(9-p35@%3#4S!4S0)$Y%%^&5(j.&^&o(*0)$Y%!#O@*GpG@=+@j.&6^)(0-=+";
	public static final String PASSWORD_FOR_CONTENT = "innerpeacer-content";

	static final int BUFFER_LEN = 1024;

	public static String encryptString(String originalName, String key) {
		byte[] originalBytes = originalName.getBytes();
        byte[] encryptedBytes = encryptBytes(originalBytes, key);
		return new String(encryptedBytes);
	}

	public static byte[] encryptBytes(byte[] originalBytes) {
		return encryptBytes(originalBytes, KEY);
	}

	public static byte[] encryptBytes(byte[] originalBytes, String key) {
		byte[] PASS_VALUE = PASSWORD_FOR_CONTENT.getBytes();
		byte[] KEY_VALUE = key.getBytes();

		int keyLength = KEY_VALUE.length;
		int pa_pos = 0;

		for (int i = 0; i < KEY_VALUE.length; i++) {
			KEY_VALUE[i] ^= PASS_VALUE[pa_pos];
			pa_pos++;
			if (pa_pos == PASS_VALUE.length) {
				pa_pos = 0;
			}
		}

		int oLength = originalBytes.length;
		byte[] encryptedBytes = new byte[oLength];
		System.arraycopy(originalBytes, 0, encryptedBytes, 0, oLength);
		int key_pos = 0;
		for (int i = 0; i < oLength; i++) {
			encryptedBytes[i] ^= KEY_VALUE[key_pos];
			key_pos++;
			if (key_pos == keyLength) {
				key_pos = 0;
			}
		}
		return encryptedBytes;
	}
}
