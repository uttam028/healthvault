package cse.mlab.hvr.shared.study;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SpeechTest implements Serializable{
	private String testId;
	private String name;
	private String description;
	private ArrayList<SubTest> subTests;
	
	public SpeechTest() {
		// TODO Auto-generated constructor stub
	}

	public SpeechTest(String testId, String name, String description,
			ArrayList<SubTest> subTests) {
		super();
		this.testId = testId;
		this.name = name;
		this.description = description;
		this.subTests = subTests;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<SubTest> getSubTests() {
		return subTests;
	}

	public void setSubTests(ArrayList<SubTest> subTests) {
		this.subTests = subTests;
	}
	
	
	
}
