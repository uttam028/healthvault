package cse.mlab.hvr.shared.study;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gwt.user.client.rpc.IsSerializable;

import cse.mlab.hvr.shared.QA;
import cse.mlab.hvr.shared.study.HealthStatusQuestion;

@XmlRootElement
public class StudyPrefaceModel implements Serializable, IsSerializable {
	private static final long serialVersionUID = 2;

	private StudyOverview studyOverview;
	private ArrayList<QA> qaList;
	private ArrayList<HealthStatusQuestion> healthStatusQuestions;
	public StudyPrefaceModel() {
		// TODO Auto-generated constructor stub
	}
	public StudyPrefaceModel(StudyOverview studyOverview, ArrayList<QA> qaList,
			ArrayList<HealthStatusQuestion> healthStatusQuestions) {
		super();
		this.studyOverview = studyOverview;
		this.qaList = qaList;
		this.healthStatusQuestions = healthStatusQuestions;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public StudyOverview getStudyOverview() {
		return studyOverview;
	}
	public ArrayList<QA> getQaList() {
		return qaList;
	}
	public ArrayList<HealthStatusQuestion> getHealthStatusQuestions() {
		return healthStatusQuestions;
	}
}