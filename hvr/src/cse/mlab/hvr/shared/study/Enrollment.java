package cse.mlab.hvr.shared.study;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Enrollment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int studyId;
	private String userId;
	private String enrollmentDate;
	private int state;

	public Enrollment() {
		// TODO Auto-generated constructor stub
	}

	public Enrollment(int id, int studyId, String userId,
			String enrollmentDate, int state) {
		super();
		this.id = id;
		this.studyId = studyId;
		this.userId = userId;
		this.enrollmentDate = enrollmentDate;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public int getStudyId() {
		return studyId;
	}

	public String getUserId() {
		return userId;
	}

	public String getEnrollmentDate() {
		return enrollmentDate;
	}

	public int getState() {
		return state;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setEnrollmentDate(String enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public void setState(int state) {
		this.state = state;
	}

}
