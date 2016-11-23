package cse.mlab.hvr.shared;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean userRole;
	private boolean adminRole;
	private boolean researcherRole;
	public UserRole() {
		// TODO Auto-generated constructor stub
	}
	public UserRole(boolean userRole, boolean adminRole, boolean researcherRole) {
		super();
		this.userRole = userRole;
		this.adminRole = adminRole;
		this.researcherRole = researcherRole;
	}

	public boolean isUserRole() {
		return userRole;
	}

	public void setUserRole(boolean userRole) {
		this.userRole = userRole;
	}

	public boolean isAdminRole() {
		return adminRole;
	}

	public void setAdminRole(boolean adminRole) {
		this.adminRole = adminRole;
	}

	public boolean isResearcherRole() {
		return researcherRole;
	}

	public void setResearcherRole(boolean researcherRole) {
		this.researcherRole = researcherRole;
	}
	
	
}
