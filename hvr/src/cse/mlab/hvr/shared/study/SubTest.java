package cse.mlab.hvr.shared.study;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SubTest implements IsSerializable {
	private int subtestId;
	private String name;
	private String alias;
	private String creationDate;
	private String modificationDate;
	private boolean active;
	private String commonInstructionText;
	private String commonInstrcutionAudioFileName;
	private ArrayList<TestFragment> subtestFragments;

	public SubTest() {
		// TODO Auto-generated constructor stub
	}

	public SubTest(int subtestId, String name, String alias,
			String creationDate, String modificationDate, boolean active,
			String commonInstructionText,
			String commonInstrcutionAudioFileName,
			ArrayList<TestFragment> subtestFragments) {
		super();
		this.subtestId = subtestId;
		this.name = name;
		this.alias = alias;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
		this.active = active;
		this.commonInstructionText = commonInstructionText;
		this.commonInstrcutionAudioFileName = commonInstrcutionAudioFileName;
		this.subtestFragments = subtestFragments;
	}

	public int getSubtestId() {
		return subtestId;
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public boolean isActive() {
		return active;
	}

	public String getCommonInstructionText() {
		return commonInstructionText;
	}

	public String getCommonInstrcutionAudioFileName() {
		return commonInstrcutionAudioFileName;
	}

	public ArrayList<TestFragment> getSubtestFragments() {
		return subtestFragments;
	}


}
