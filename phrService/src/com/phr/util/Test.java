package com.phr.util;

import org.apache.commons.codec.binary.Base64;


public class Test {
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
		
	}
}
