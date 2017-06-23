package cse.mlab.hvr.shared;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProfile implements Serializable{
	private static final long serialVersionUID = 5744986706947752922L;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	private int birthYear;
	private String primaryLanguage;
	private String phoneNumber;
	private String address;
	private int height;
	private int weight;
	
	private String handedness;
	private String hearing;
	private String vision;
	private String swallowing;
	private String dentures;

	public UserProfile() {
	}

	public UserProfile(String email, String password, String firstName,
			String lastName, String gender, int birthYear,
			String primaryLanguage, String phoneNumber, String address,
			int height, int weight, String handedness, String hearing,
			String vision, String swallowing, String dentures) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthYear = birthYear;
		this.primaryLanguage = primaryLanguage;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.height = height;
		this.weight = weight;
		this.handedness = handedness;
		this.hearing = hearing;
		this.vision = vision;
		this.swallowing = swallowing;
		this.dentures = dentures;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public String getPrimaryLanguage() {
		return primaryLanguage;
	}

	public void setPrimaryLanguage(String primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getHandedness() {
		return handedness;
	}

	public void setHandedness(String handedness) {
		this.handedness = handedness;
	}

	public String getHearing() {
		return hearing;
	}

	public void setHearing(String hearing) {
		this.hearing = hearing;
	}

	public String getVision() {
		return vision;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public String getSwallowing() {
		return swallowing;
	}

	public void setSwallowing(String swallowing) {
		this.swallowing = swallowing;
	}

	public String getDentures() {
		return dentures;
	}

	public void setDentures(String dentures) {
		this.dentures = dentures;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
