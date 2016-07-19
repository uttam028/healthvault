package cse.mlab.hvr.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TestPrefaceModel implements Serializable, IsSerializable {
	private static final long serialVersionUID = 2;

	private String id;
	private String displayName;
	private String description;
	private ArrayList<QA> qaList;

	public TestPrefaceModel() {
		// TODO Auto-generated constructor stub
	}

	public TestPrefaceModel(String id, String displayName, String description,
			ArrayList<QA> qaList) {
		super();
		this.id = id;
		this.displayName = displayName;
		this.description = description;
		this.qaList = qaList;
	}

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<QA> getQaList() {
		return qaList;
	}

}