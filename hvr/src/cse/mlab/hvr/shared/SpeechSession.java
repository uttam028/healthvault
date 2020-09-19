package cse.mlab.hvr.shared;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SpeechSession implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sessionId;
	private String user;
	//1=active, 0=logout, -1=invalid
	private int state;
	
	public SpeechSession() {
		// TODO Auto-generated constructor stub
	}

	public SpeechSession(String sessionId, String user, int state) {
		super();
		this.sessionId = sessionId;
		this.user = user;
		this.state = state;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getUser() {
		return user;
	}

	public int getState() {
		return state;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setState(int state) {
		this.state = state;
	}
	
}
