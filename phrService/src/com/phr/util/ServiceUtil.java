package com.phr.util;

import java.security.SecureRandom;

public class ServiceUtil {
	
	static final String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	public static String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( alphanumeric.charAt( rnd.nextInt(alphanumeric.length()) ) );
	   return sb.toString();
	}
	
	public static void main(String[] args) {
		String str = ServiceUtil.randomString(32);
		System.out.println(str);
	}
}
