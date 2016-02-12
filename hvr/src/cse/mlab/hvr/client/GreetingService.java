package cse.mlab.hvr.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cse.mlab.hvr.shared.Medication;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	  String greetServer(String name) throws IllegalArgumentException;
	  String signupToPhr(UserProfile userProfile);
	  String loginToPhr(User user);
	  String checkEmailAvailability(String email);
	  UserProfile getProfile(String email);
	  Response saveProfile(UserProfile userProfile);
	  Response saveMedications(Medication medication);
}
