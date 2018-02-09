package com.mmbao.saas.utils;

import java.security.MessageDigest;

/**
 * Created by ${Dota.Wang} on 2017/11/25.
 */
public class Md5Utils {
    public static String encode(String pwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] bs = digest.digest(pwd.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bs) {
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(number);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
