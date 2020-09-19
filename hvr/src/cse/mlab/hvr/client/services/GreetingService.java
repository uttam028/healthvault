package cse.mlab.hvr.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.SpeechSession;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.UserRole;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	  //login
	  Response getSessionInformation(SpeechSession session);
	  Response signupToPhr(UserProfile userProfile);
	  Response loginToPhr(User user);
	  Response logout(SpeechSession session);
	  Response resetPassword(String email);
	  Response changePassword(String email, String oldPassword, String newPassword);
	  Boolean checkEmailAvailability(String email);
	  UserRole getRole(String email);
}
