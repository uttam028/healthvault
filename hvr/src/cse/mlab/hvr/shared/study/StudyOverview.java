package cse.mlab.hvr.shared.study;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StudyOverview implements IsSerializable {
	private String id;
	private String name;
	private String alias;
	private String createdBy;
	private String startDate;
	private String endDate;
	private String creationDate;
	private String modificationDate;
	private String overview;
	private String description;
//	private int subtestCount;
	private boolean active;
	private boolean consentFileAvailable;
	private String consentFileName;
	// 0=created by owner, 1=edit in progress, 2=pending to authorize,
	// 3=published, 4=delete in progress, 5= deleted, 6=expired
	private int publishState;
	private String speechTestId;
	private String complianceId;
	
	public StudyOverview() {
		// TODO Auto-generated constructor stub
	}

	public StudyOverview(String id, String name, String alias,
			String createdBy, String startDate, String endDate,
			String creationDate, String modificationDate, String overview,
			String description, boolean active, boolean consentFileAvailable,
			String consentFileName, int publishState, String speechTestId,
			String complianceId) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.createdBy = createdBy;
		this.startDate = startDate;
		this.endDate = endDate;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.overview = overview;
		this.description = description;
		this.active = active;
		this.consentFileAvailable = consentFileAvailable;
		this.consentFileName = consentFileName;
		this.publishState = publishState;
		this.speechTestId = speechTestId;
		this.complianceId = complianceId;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public String getOverview() {
		return overview;
	}

	public String getDescription() {
		return description;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isConsentFileAvailable() {
		return consentFileAvailable;
	}

	public String getConsentFileName() {
		return consentFileName;
	}

	public int getPublishState() {
		return publishState;
	}

	public String getSpeechTestId() {
		return speechTestId;
	}

	public String getComplianceId() {
		return complianceId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setConsentFileAvailable(boolean consentFileAvailable) {
		this.consentFileAvailable = consentFileAvailable;
	}

	public void setConsentFileName(String consentFileName) {
		this.consentFileName = consentFileName;
	}

	public void setPublishState(int publishState) {
		this.publishState = publishState;
	}

	public void setSpeechTestId(String speechTestId) {
		this.speechTestId = speechTestId;
	}

	public void setComplianceId(String complianceId) {
		this.complianceId = complianceId;
	}


}
