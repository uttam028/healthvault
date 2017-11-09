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
	private String splitString;
	public Recording() {
		// TODO Auto-generated constructor stub
	}
	public Recording(int id, int participationId, String userId, String studyId, int subtestId, String startTime,
			String endTime, String fileIdentifier, String uploadTime, String quality, int retakeCounter,
			String splitString) {
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
		this.splitString = splitString;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
		return id;
	}
	public int getParticipationId() {
		return participationId;
	}
	public String getUserId() {
		return userId;
	}
	public String getStudyId() {
		return studyId;
	}
	public int getSubtestId() {
		return subtestId;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public String getFileIdentifier() {
		return fileIdentifier;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public String getQuality() {
		return quality;
	}
	public int getRetakeCounter() {
		return retakeCounter;
	}
	public String getSplitString() {
		return splitString;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setParticipationId(int participationId) {
		this.participationId = participationId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
	public void setSubtestId(int subtestId) {
		this.subtestId = subtestId;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setFileIdentifier(String fileIdentifier) {
		this.fileIdentifier = fileIdentifier;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public void setRetakeCounter(int retakeCounter) {
		this.retakeCounter = retakeCounter;
	}
	public void setSplitString(String splitString) {
		this.splitString = splitString;
	}
	
}
