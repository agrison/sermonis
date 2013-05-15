package me.grison.sermonis.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Crypto Utilities.
 * 
 * Part taken from: http://stackoverflow.com/questions/6486121/aes-encryption-in-java
 */
public class CryptoUtils {
	/**
	 * Encrypt a text with a secret key.
	 */
    public static String encrypt(String secretKey, String text) throws Exception {
        byte[] rawKey = getRawKey(secretKey.getBytes());
        byte[] result = encrypt(rawKey, text.getBytes());
        return toHex(result);
    }

    /**
     * Decrypt a previously encrypted text with a secret key.
     */
    public static String decrypt(String secretKey, String encryptedText) throws Exception {
        byte[] rawKey = getRawKey(secretKey.getBytes());
        byte[] enc = toByte(encryptedText);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        return skey.getEncoded();
    }


    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encrypted);
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }
    
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }
    
    /**
     * MD5 the given String.
     */
    public static String md5(String text) {
		try {
			return toHex(MessageDigest.getInstance("MD5").digest(text.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
    }
    
    /**
     * Shuffle the given String.
     */
    public static String shuffleString(String string) {
      final List<String> letters = Arrays.asList(string.split(""));
      Collections.shuffle(letters);
      final String sLetters = letters.toString();
      return sLetters.substring(1, sLetters.length() - 1).replace(", ", "");
    }
}