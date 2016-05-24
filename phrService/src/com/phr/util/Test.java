package com.phr.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;


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
		
		/* String to split. */
		  String str = "/onethree ";
		  String[] temp;
		 
		  /* delimiter */
		  String delimiter = "-";
		  /* given string will be split by the argument delimiter provided. */
		  temp = str.split(delimiter);
		  /* print substrings */
		  for(int i =0; i < temp.length ; i++)
		    System.out.println(temp[i]);
		
		String list = "/hjdkjs2 ";
		String delim = "\\|";
		String [] medList = list.split(delim);
		String medListInQuery = "(";
		for(int i=0;i<medList.length;i++){
			medListInQuery += (medList[i].trim() + ",");
			System.out.println("t:"+ medList[i]);
		}
		//medListInQuery = medListInQuery.substring(0, medListInQuery.length()-1) + ")";
		//System.out.println("med list in query:"+ medListInQuery + ", original:"+ list + ".........."+ medList.length);

		String n = "3|5";
		String e = Base64.encodeBase64String(n.getBytes());
		System.out.println("encoded:"+ e);
		String d = new String(Base64.decodeBase64(e));
		System.out.println("decode:"+d);
		
		
		try {
			System.out.println(Test.getMD5String("uttam"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
