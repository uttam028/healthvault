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
	public static boolean isEmptyString(String msg){
		if(msg == null || msg.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static String getStudyRoot(){
		return "http://10.32.10.188:8080/hvr/metadata/study/";
	}
	
	public static String getTestRoot(){
		return "http://10.32.10.188:8080/hvr/metadata/test/";
	}
	
	public static void main(String[] args) {
		String str = ServiceUtil.randomString(32);
		System.out.println(str);
	}
	
}
