package cse.mlab.hvr.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	  String greetServer(String name) throws IllegalArgumentException;
	  
	  
	  
	  //login
	  Response getSessionInformation(Session session);
	  Response signupToPhr(UserProfile userProfile);
	  Response loginToPhr(User user);
	  Response logout(Session session);
	  Response resetPassword(String email);
	  Response changePassword(String email, String oldPassword, String newPassword);
	  String checkEmailAvailability(String email);
	  
	  UserProfile getProfile(String email);
	  Response saveProfile(UserProfile userProfile);
	  Response profileUpdateRequired(String email);
	  
	  Response saveMedication(Medication medication);
	  Response updateMedication(Medication medication);
	  String getMedications(String email);
	  MedicationList getMedicationsList(String email);
	  Response deleteMedications(String email, String list);
	  Response stopUsingMedication(long id, String endDate);
	  
	  
	  //speech test
	  ArrayList<StudyPrefaceModel> getOpenStudies(String email);
	  ArrayList<MyStudyDataModel> getMyStudies(String email);
	  SpeechTest getSpeechTestMetadata(String testId);
	  Response enrollToStudy(String studyId, String email);
	  Response startParticipation(String studyId, String email);
	  Response endParticipation(String studyId, String email);
	  Response relocateSoundFile(Recording recording);
	  
	  
	  
}
