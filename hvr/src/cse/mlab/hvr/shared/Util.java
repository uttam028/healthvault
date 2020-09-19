package cse.mlab.hvr.shared;

import java.security.MessageDigest;

public class Util {

	public static boolean isEmptyString(String msg){
		if(msg == null || msg.isEmpty()){
			return true;
		}
		return false;
	}
	public static String getEmailVerificationRoot(){
		//return "http://129.74.247.110:8080/hvr/";
		//return "http://speechmarker.com/";
		return "https://speechbiomarker.com/hvr/";
	}
	
	public static String getStudyRoot(){
		//return "http://10.32.10.188:8080/hvr/metadata/study/";
		//return "http://129.74.247.110:8080/hvr/metadata/study/";
		//return "http://129.74.247.110/hvr/metadata/study/";
		
		//return "https://speechmarker.com/hvr/metadata/study/";
		return "https://speechbiomarker.com/hvr/metadata/study/";
	}
	
	public static String getTestRoot(){
		//return "http://10.32.10.188:8080/hvr/metadata/test/";
		//return "http://129.74.247.110:8080/hvr/metadata/test/";
		//return "http://129.74.247.110/hvr/metadata/test/";
		//return "https://speechmarker.com/hvr/metadata/test/";
		return "https://speechbiomarker.com/hvr/metadata/test/";
	}

	
	public static boolean isEmailFOrmatValid(Object value) {
		if (value == null)
			return true;

		String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";

		boolean valid = false;

		if (value.getClass().toString().equals(String.class.toString())) {
			valid = ((String) value).matches(emailPattern);
		} else {
			valid = ((Object) value).toString().matches(emailPattern);
		}

		return valid;
	}
	
	/**
	 * Generate MD5 digest.
	 * 
	 * @param input
	 *            input data to be hashed.
	 * @return MD5 digest.
	 */
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


}
