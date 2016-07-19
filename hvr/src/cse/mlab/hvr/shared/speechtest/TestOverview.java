package cse.mlab.hvr.shared.speechtest;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TestOverview implements IsSerializable {
	private String id;
	private String name;
	private String alias;
	private String createdBy;
	private String creationDate;
	private String modificationDate;
	private String overview;
	private String description;
	private int subtestCount;
	private boolean isActive;
	private boolean isConsentFileAvailable;
	private String consentFileName;
	// 0=created by owner, 1=edit in progress, 2=pending to authorize,
	// 3=published, 4=delete in progress, 5= deleted
	private int publishState;

	public TestOverview() {
		// TODO Auto-generated constructor stub
	}

	public TestOverview(String id, String name, String alias, String createdBy,
			String creationDate, String modificationDate, String overview,
			String description, int subtestCount, boolean isActive,
			boolean isConsentFileAvailable, String consentFileName,
			int publishState) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.overview = overview;
		this.description = description;
		this.subtestCount = subtestCount;
		this.isActive = isActive;
		this.isConsentFileAvailable = isConsentFileAvailable;
		this.consentFileName = consentFileName;
		this.publishState = publishState;
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

	public int getSubtestCount() {
		return subtestCount;
	}

	public boolean isActive() {
		return isActive;
	}

	public boolean isConsentFileAvailable() {
		return isConsentFileAvailable;
	}

	public String getConsentFileName() {
		return consentFileName;
	}

	public int getPublishState() {
		return publishState;
	}

}
