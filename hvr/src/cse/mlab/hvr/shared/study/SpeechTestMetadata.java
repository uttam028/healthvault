package cse.mlab.hvr.shared.study;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SpeechTestMetadata implements IsSerializable{
	private StudyOverview testOverview;
	private ArrayList<SubTest> subTests;
	public SpeechTestMetadata() {
		// TODO Auto-generated constructor stub
	}
	public SpeechTestMetadata(StudyOverview testOverview,
			ArrayList<SubTest> subTests) {
		super();
		this.testOverview = testOverview;
		this.subTests = subTests;
	}
	public StudyOverview getTestOverview() {
		return testOverview;
	}
	public ArrayList<SubTest> getSubTests() {
		return subTests;
	}
	
}
