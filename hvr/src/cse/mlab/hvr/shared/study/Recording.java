package cse.mlab.hvr.shared.study;

import java.io.Serializable;

public class Recording implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int participationId;
	private String userId;
	private String studyId;
	private int subtestId;
	private String startTime;
	private String endTime;
	private String fileIdentifier;
	private String uploadTime;
	private String quality;
	private int retakeCounter;
	public Recording() {
		// TODO Auto-generated constructor stub
	}
	public Recording(int id, int participationId, String userId,
			String studyId, int subtestId, String startTime, String endTime,
			String fileIdentifier, String uploadTime, String quality,
			int retakeCounter) {
		super();
		this.id = id;
		this.participationId = participationId;
		this.userId = userId;
		this.studyId = studyId;
		this.subtestId = subtestId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.fileIdentifier = fileIdentifier;
		this.uploadTime = uploadTime;
		this.quality = quality;
		this.retakeCounter = retakeCounter;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParticipationId() {
		return participationId;
	}
	public void setParticipationId(int participationId) {
		this.participationId = participationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStudyId() {
		return studyId;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
	public int getSubtestId() {
		return subtestId;
	}
	public void setSubtestId(int subtestId) {
		this.subtestId = subtestId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getFileIdentifier() {
		return fileIdentifier;
	}
	public void setFileIdentifier(String fileIdentifier) {
		this.fileIdentifier = fileIdentifier;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public int getRetakeCounter() {
		return retakeCounter;
	}
	public void setRetakeCounter(int retakeCounter) {
		this.retakeCounter = retakeCounter;
	}
	
}
