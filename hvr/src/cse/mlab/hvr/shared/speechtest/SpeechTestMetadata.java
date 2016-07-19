package cse.mlab.hvr.shared.speechtest;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SpeechTestMetadata implements IsSerializable{
	private TestOverview testOverview;
	private ArrayList<HealthStatusQuestion> healthStatusQuestions;
	private ArrayList<SubTest> subTests;
	public SpeechTestMetadata() {
		// TODO Auto-generated constructor stub
	}
	public SpeechTestMetadata(TestOverview testOverview,
			ArrayList<HealthStatusQuestion> healthStatusQuestions,
			ArrayList<SubTest> subTests) {
		super();
		this.testOverview = testOverview;
		this.healthStatusQuestions = healthStatusQuestions;
		this.subTests = subTests;
	}
	public TestOverview getTestOverview() {
		return testOverview;
	}
	public ArrayList<HealthStatusQuestion> getHealthStatusQuestions() {
		return healthStatusQuestions;
	}
	public ArrayList<SubTest> getSubTests() {
		return subTests;
	}
	
	
}
