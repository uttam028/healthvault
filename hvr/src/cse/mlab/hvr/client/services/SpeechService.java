package cse.mlab.hvr.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.study.MyStudyDataModel;
import cse.mlab.hvr.shared.study.PreTestAnswers;
import cse.mlab.hvr.shared.study.PreTestQuestion;
import cse.mlab.hvr.shared.study.Recording;
import cse.mlab.hvr.shared.study.SpeechTest;
import cse.mlab.hvr.shared.study.StudyPrefaceModel;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("speech")
public interface SpeechService extends RemoteService {
	  //speech test
	  ArrayList<StudyPrefaceModel> getOpenStudies(String email);
	  ArrayList<MyStudyDataModel> getMyStudies(String email);
	  ArrayList<PreTestQuestion> getPreTestQuestions(String testId);
	  Response submitPreTestAnswer(PreTestAnswers preTestAnswers);
	  SpeechTest getSpeechTestMetadata(String testId);
	  Response enrollToStudy(String studyId, String email);
	  Response startParticipation(String studyId, String email);
	  Response endParticipation(String studyId, String email, String participationId);
	  Response relocateSoundFile(Recording recording);
}
