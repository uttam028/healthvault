package cse.mlab.hvr.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cse.mlab.hvr.shared.Medication;
import cse.mlab.hvr.shared.MedicationList;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.TestPrefaceModel;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	  String greetServer(String name) throws IllegalArgumentException;
	  
	  Response signupToPhr(UserProfile userProfile);
	  Response loginToPhr(User user);
	  Response resetPassword(String email);
	  String checkEmailAvailability(String email);
	  
	  UserProfile getProfile(String email);
	  Response saveProfile(UserProfile userProfile);
	  
	  Response saveMedication(Medication medication);
	  Response updateMedication(Medication medication);
	  String getMedications(String email);
	  MedicationList getMedicationsList(String email);
	  Response deleteMedications(String email, String list);
	  Response stopUsingMedication(long id, String endDate);
	  
	  
	  //speech test
	  ArrayList<TestPrefaceModel> getAvailableSpeechTest();
}
