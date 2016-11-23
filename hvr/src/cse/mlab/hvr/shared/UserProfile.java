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

	public UserProfile() {
	}
	public UserProfile(String email, String password, String firstName,
			String lastName, String gender, int birthYear,
			String primaryLanguage, String phoneNumber, String address,
			int height, int weight) {
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
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getGender() {
		return gender;
	}
	public int getBirthYear() {
		return birthYear;
	}
	public String getPrimaryLanguage() {
		return primaryLanguage;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public int getHeight() {
		return height;
	}
	public int getWeight() {
		return weight;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	public void setPrimaryLanguage(String primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}

}
