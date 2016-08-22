package cse.mlab.hvr.shared.study;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Participation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String userId;
	private int studyId;
	private String startTime;
	private String endTime;
	private int state;
	public Participation() {
		// TODO Auto-generated constructor stub
	}
	public Participation(int id, String userId, int studyId, String startTime,
			String endTime, int state) {
		super();
		this.id = id;
		this.userId = userId;
		this.studyId = studyId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public int getStudyId() {
		return studyId;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public int getState() {
		return state;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
