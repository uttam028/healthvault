package cse.mlab.hvr.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cse.mlab.hvr.shared.Medication;
import cse.mlab.hvr.shared.MedicationList;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.Session;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.study.MyStudyDataModel;
import cse.mlab.hvr.shared.study.Recording;
import cse.mlab.hvr.shared.study.SpeechTest;
import cse.mlab.hvr.shared.study.StudyPrefaceModel;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void getSessionInformation(Session session, AsyncCallback<Response> callback);
	void signupToPhr(UserProfile userProfile, AsyncCallback<Response> callback);
	void loginToPhr(User user, AsyncCallback<Response> callback);
	void logout(Session session, AsyncCallback<Response> callback);
	void resetPassword(String email, AsyncCallback<Response> callback);
	void changePassword(String email, String oldPassword, String newPassword, AsyncCallback<Response> callback);
	void checkEmailAvailability(String email, AsyncCallback<String> callback);

	void getProfile(String email, AsyncCallback<UserProfile> callback);
	void saveProfile(UserProfile userProfile, AsyncCallback<Response> callback);
	void profileUpdateRequired(String email, AsyncCallback<Response> callback);
	
	void saveMedication(Medication medication, AsyncCallback<Response> callback);
	void updateMedication(Medication medication, AsyncCallback<Response> callback);
	void getMedications(String email, AsyncCallback<String> callback);
	void getMedicationsList(String email, AsyncCallback<MedicationList> callback);
	void deleteMedications(String email, String list, AsyncCallback<Response> callback);
	void stopUsingMedication(long id, String endDate, AsyncCallback<Response> callback);
	
	//speech test
	void getOpenStudies(String email, AsyncCallback<ArrayList<StudyPrefaceModel>> callback);
	void getMyStudies(String email, AsyncCallback<ArrayList<MyStudyDataModel>> callback);
	void getSpeechTestMetadata(String testId, AsyncCallback<SpeechTest> callback);
	void enrollToStudy(String studyId, String email, AsyncCallback<Response> callback);
	void startParticipation(String studyId, String email, AsyncCallback<Response> callback);
	void endParticipation(String studyId, String email, AsyncCallback<Response> callback);
	void relocateSoundFile(Recording recording, AsyncCallback<Response> callback);
	
	//SpeechTestMetadata getSpeechTestMetadata(String testId);
}
