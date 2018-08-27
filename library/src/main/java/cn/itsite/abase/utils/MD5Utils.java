package cn.itsite.abase.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String encrypt(String message) {
        String encode;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(message.getBytes());
            encode = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            encode = String.valueOf(message.hashCode());
        }
        return encode;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
