package cse.mlab.hvr.shared;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class Medication implements Serializable{

	private static final long serialVersionUID = 13;

	long id;
	String email;
	String name;
//	int strength;
//	String strengthUnit;
//	int dosage;
//	String dosageUnit;
//	String consumeProcess;
//	String consumeFrequency;
	String dosage;
	String frequency;
	String reason;
	String startDate;
	String endDate;
//	String isPrescribed;
//	String prescribedBy;
//	String prescribedDate;
//	String instruction;
//	String prescribedQuantity;
//	String note;

	public Medication() {
		// TODO Auto-generated constructor stub
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public long getId() {
		return id;
	}



	public String getEmail() {
		return email;
	}



	public String getName() {
		return name;
	}



	public String getDosage() {
		return dosage;
	}



	public String getFrequency() {
		return frequency;
	}



	public String getReason() {
		return reason;
	}



	public String getStartDate() {
		return startDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setId(long id) {
		this.id = id;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setDosage(String dosage) {
		this.dosage = dosage;
	}



	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}



	public void setReason(String reason) {
		this.reason = reason;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
//		return "email:"+ email + ", name:" + name + ", strength:"+ strength + ", st unit:"+ strengthUnit + ", dosage:" + dosage + ", dosage unit:"
//				+ dosageUnit + ", consume proc:"+ consumeProcess + ", con freq:" + consumeFrequency + ", reason:" + reason + ", start date:"+ startDate
//				+ ", end date:"+ endDate + ", is presribed:"+ isPrescribed + ", prescribed by:"+ prescribedBy + "precsribed date:"+ prescribedDate + ", instruction:"
//				+ instruction + ", pres quantity:"+ prescribedQuantity + ", note";
		return "email:"+ email + ", name:" + name + ", reason:" + reason + ", start date:"+ startDate
		+ ", end date:"+ endDate ;

	}
	
	public String toJsonString(){
		String json = "";
		
		return json;
	}
}
