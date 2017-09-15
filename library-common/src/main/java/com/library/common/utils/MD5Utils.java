package com.library.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class MD5Utils {

    public static String str2MD5(String str) {
	// 加密后的字符串
	String encodeStr = null;
	try {
	    MessageDigest md5 = MessageDigest.getInstance("MD5");
	    BASE64Encoder base64en = new BASE64Encoder();
	    encodeStr = base64en.encode(md5.digest(str.getBytes("utf-8")));
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	return encodeStr;
    }
}
