package com.mmbao.saas.utils;

import android.util.Base64;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtil {
	    private final static String DES = "DES";
	    private final static String key = "saas1.0_app*#!DesKey";
	
	   public static void main(String[] args) throws Exception {
	    }
	
	
	    /**
	     * Description 根据键值进行加密
	     * @param data
	     * @return
	     * @throws Exception
	     */
	
	    public static String encrypt(String data) throws Exception {
	        byte[] bt = encrypt(data.getBytes("utf-8"));
//	        String strs = new BASE64Encoder().encode(bt);
	        String strs = Base64.encodeToString(bt, Base64.DEFAULT);
//			LogcatUtil.i("strs="+strs);
			return strs.replace("\n", "");
	    }
	
	
	    /**
	     * Description 根据键值进行解密
	     * @param data
	     * @return
	     * @throws Exception
	     * @throws IOException
	     * @throws Exception
	     */
	    public static String decrypt(String data) throws Exception {
	    	byte[] buf = null;
	    	byte[] bt = null;
	        if (data == null)
		           return null;
//		    BASE64Decoder decoder = new BASE64Decoder();
//		    buf = decoder.decodeBuffer(data);
		    
		    buf = Base64.decode(data, Base64.DEFAULT);
		    
		    bt = decrypt(buf);
//		    String result =  new String(bt,"UTF-8");
//			LogcatUtil.i("result="+result);
			return new String(bt);
	    }
	
	
	    /**
	     * Description 根据键值进行加密
	     * @param data
	     * @return
	     * @throws Exception
	     */
	    private static byte[] encrypt(byte[] data) throws Exception {
	        // 生成一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	 
	        // 从原始密钥数据创建DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key.getBytes());

	        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	        SecretKey securekey = keyFactory.generateSecret(dks);
	 
	        // Cipher对象实际完成加密操作
	        Cipher cipher = Cipher.getInstance(DES);
	 
	        // 用密钥初始化Cipher对象
	        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
	 
	        return cipher.doFinal(data);
	    }
	     
	     
	    /**
	     * Description 根据键值进行解密
	     * @param data
	     * @return
	     * @throws Exception
	     */
	    private static byte[] decrypt(byte[] data) throws Exception {
	        // 生成一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	        // 从原始密钥数据创建DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key.getBytes());
	        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	        SecretKey securekey = keyFactory.generateSecret(dks);
	        // Cipher对象实际完成解密操作
	        Cipher cipher = Cipher.getInstance(DES);
	        // 用密钥初始化Cipher对象
	        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
	        return cipher.doFinal(data);
	    }
	    
	    
	}

	
