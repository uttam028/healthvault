package cse.mlab.hvr.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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
	  String saveProfile(UserProfile userProfile);
}
