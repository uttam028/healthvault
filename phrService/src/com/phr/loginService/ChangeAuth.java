package com.phr.loginService;

public class ChangeAuth {
	private String email;
	private String oldPassword;
	private String newPassword;
	
	public ChangeAuth() {
		// TODO Auto-generated constructor stub
	}

	public ChangeAuth(String email, String oldPassword, String newPassword) {
		super();
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
