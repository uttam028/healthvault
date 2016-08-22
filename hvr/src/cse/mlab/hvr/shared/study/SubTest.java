package cse.mlab.hvr.shared.study;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SubTest implements IsSerializable {
	private int subtestId;
	private String name;
	private boolean active;
	private String commonInstructionText;
	private String commonInstrcutionAudioFileName;
	private ArrayList<TestFragment> subtestFragments;

	public SubTest() {
		// TODO Auto-generated constructor stub
	}

	public SubTest(int subtestId, String name, boolean active,
			String commonInstructionText,
			String commonInstrcutionAudioFileName,
			ArrayList<TestFragment> subtestFragments) {
		super();
		this.subtestId = subtestId;
		this.name = name;
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
