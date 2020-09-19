package cse.mlab.hvr.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.study.MyStudyDataModel;
import cse.mlab.hvr.shared.study.PreTestAnswers;
import cse.mlab.hvr.shared.study.PreTestQuestion;
import cse.mlab.hvr.shared.study.Recording;
import cse.mlab.hvr.shared.study.SpeechTest;
import cse.mlab.hvr.shared.study.StudyPrefaceModel;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface SpeechServiceAsync {
	//speech test
	void getOpenStudies(String email, AsyncCallback<ArrayList<StudyPrefaceModel>> callback);
	void getMyStudies(String email, AsyncCallback<ArrayList<MyStudyDataModel>> callback);
	void getPreTestQuestions(String testId, AsyncCallback<ArrayList<PreTestQuestion>> callback);
	void submitPreTestAnswer(PreTestAnswers preTestAnswers, AsyncCallback<Response> callback);
	void getSpeechTestMetadata(String testId, AsyncCallback<SpeechTest> callback);
	void enrollToStudy(String studyId, String email, AsyncCallback<Response> callback);
	void startParticipation(String studyId, String email, AsyncCallback<Response> callback);
	void endParticipation(String studyId, String email, String participationId, AsyncCallback<Response> callback);
	void relocateSoundFile(Recording recording, AsyncCallback<Response> callback);
}
