package cse.mlab.hvr.shared;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response implements Serializable{
	private static final long serialVersionUID = 5744986706947752922L;

	int code;
	String message;
	
	public Response() {
		// TODO Auto-generated constructor stub
		this.code = -1;
		this.message = "Please try later";
	}
	public Response(int code, String message) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
}
