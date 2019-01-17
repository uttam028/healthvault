package com.phr.util;

import java.security.MessageDigest;


public class Test {
	
	public static byte[] getMd5Digest(byte[] input) throws Exception {
		// MessageDigest md5 = perThreadMd5.get();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.reset();
		md5.update(input);
		return md5.digest();
	}

	public static String getMD5String(String input) throws Exception {
		byte[] byteData = getMd5Digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		
		String sep = "_|_";
		String ids = "";
		for(int i=0;i<10;i++){
			ids = ids + sep + i;
		}
		System.out.println("before: "+ ids);
		System.out.println("final: "+ ids.substring(3));
	}
}
