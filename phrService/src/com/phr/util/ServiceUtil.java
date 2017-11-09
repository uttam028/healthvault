package com.phr.util;

import java.security.SecureRandom;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ServiceUtil {
	
	static final String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	static{
		System.out.println("Statis code execution from service util...");
	}

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
	
	/*
	public static String getServiceRoot(){
		return "http://10.32.10.188:8080/hvr/";
	}*/
	
	public static String getEmailVerificationRoot(){
		//return "http://129.74.247.110:8080/hvr/";
		//return "http://speechmarker.com/";
		return "https://speechmarker.com/hvr/";
	}
	
	public static String getStudyRoot(){
		//return "http://10.32.10.188:8080/hvr/metadata/study/";
		//return "http://129.74.247.110:8080/hvr/metadata/study/";
		//return "http://129.74.247.110/hvr/metadata/study/";
		return "https://speechmarker.com/hvr/metadata/study/";
	}
	
	public static String getTestRoot(){
		//return "http://10.32.10.188:8080/hvr/metadata/test/";
		//return "http://129.74.247.110:8080/hvr/metadata/test/";
		//return "http://129.74.247.110/hvr/metadata/test/";
		return "https://speechmarker.com/hvr/metadata/test/";
	}
	
	public static void main(String[] args) {
		String str = ServiceUtil.randomString(32);
		System.out.println(str);
	}
	
	public static boolean sendEmail(String toAddress, String subject, String messageBody, String name) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("ndspeechrepo",
								"mcomlab2016");
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ndspeechrespo@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toAddress));
			message.setSubject(subject);
			if(ServiceUtil.isEmptyString(name.trim())){
				name = "User";
			}
			String header = "Dear "+ name + ",\n";
			String footer = "\n\nRegards,\nSpeech Marker Initiative"; 
			message.setText(header + messageBody + footer);

			Transport.send(message);

			System.out.println("Done");
			return true;
		} catch (MessagingException e) {
			// throw new RuntimeException(e);
		}
		return false;
	}

}
