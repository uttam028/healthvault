package cse.mlab.hvr.shared;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class Medication {

	long id;
	String email;
	String name;
	int strength;
	String strengthUnit;
	int dosage;
	String dosageUnit;
	String consumeProcess;
	String consumeFrequency;
	String reason;
	String startDate;
	String endDate;
	String isPrescribed;
	String prescribedBy;
	String prescribedDate;
	String instruction;
	String prescribedQuantity;
	String note;

	public Medication() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getStrength() {
		return strength;
	}


	public void setStrength(int strength) {
		this.strength = strength;
	}


	public String getStrengthUnit() {
		return strengthUnit;
	}


	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit;
	}


	public int getDosage() {
		return dosage;
	}


	public void setDosage(int dosage) {
		this.dosage = dosage;
	}


	public String getDosageUnit() {
		return dosageUnit;
	}


	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}


	public String getConsumeProcess() {
		return consumeProcess;
	}


	public void setConsumeProcess(String consumeProcess) {
		this.consumeProcess = consumeProcess;
	}


	public String getConsumeFrequency() {
		return consumeFrequency;
	}


	public void setConsumeFrequency(String consumeFrequency) {
		this.consumeFrequency = consumeFrequency;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getIsPrescribed() {
		return isPrescribed;
	}


	public void setIsPrescribed(String isPrescribed) {
		this.isPrescribed = isPrescribed;
	}


	public String getPrescribedBy() {
		return prescribedBy;
	}


	public void setPrescribedBy(String prescribedBy) {
		this.prescribedBy = prescribedBy;
	}


	public String getPrescribedDate() {
		return prescribedDate;
	}


	public void setPrescribedDate(String prescribedDate) {
		this.prescribedDate = prescribedDate;
	}


	public String getInstruction() {
		return instruction;
	}


	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}


	public String getPrescribedQuantity() {
		return prescribedQuantity;
	}


	public void setPrescribedQuantity(String prescribedQuantity) {
		this.prescribedQuantity = prescribedQuantity;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "email:"+ email + ", name:" + name + ", strength:"+ strength + ", st unit:"+ strengthUnit + ", dosage:" + dosage + ", dosage unit:"
				+ dosageUnit + ", consume proc:"+ consumeProcess + ", con freq:" + consumeFrequency + ", reason:" + reason + ", start date:"+ startDate
				+ ", end date:"+ endDate + ", is presribed:"+ isPrescribed + ", prescribed by:"+ prescribedBy + "precsribed date:"+ prescribedDate + ", instruction:"
				+ instruction + ", pres quantity:"+ prescribedQuantity + ", note";
	}
}
