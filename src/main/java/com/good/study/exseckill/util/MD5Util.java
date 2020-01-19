package com.good.study.exseckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 76366 md5 工具类实现
 */
public class MD5Util {

	// 盐
	public static final String salt = "1a2b3c4d";

	/**
	 * md5加密
	 * 
	 * @param src 加密前
	 * @return 加密后
	 */
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}

	// 加盐操作
	public static String inputPassToFormPass(String inputpass) {
		String str = "" + salt.charAt(0) + salt.charAt(2) + inputpass + salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}

	public static String formPassToDBPass(String formPass, String saltDb) {
		String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}

	public static String inputPassToDBPass(String input, String saltDb) {
		String formPass = inputPassToFormPass(input);
		String dbPass= formPassToDBPass(formPass, saltDb);
		return dbPass;
	}
	
	
	public static void main(String[] args) {
		System.out.println(inputPassToFormPass("123456"));
		System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
	}
}
