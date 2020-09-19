package cse.mlab.hvr.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.SpeechSession;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.UserRole;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {

	void getSessionInformation(SpeechSession session, AsyncCallback<Response> callback);
	void signupToPhr(UserProfile userProfile, AsyncCallback<Response> callback);
	void loginToPhr(User user, AsyncCallback<Response> callback);
	void logout(SpeechSession session, AsyncCallback<Response> callback);
	void resetPassword(String email, AsyncCallback<Response> callback);
	void changePassword(String email, String oldPassword, String newPassword, AsyncCallback<Response> callback);
	void checkEmailAvailability(String email, AsyncCallback<Boolean> callback);
	void getRole(String email, AsyncCallback<UserRole> asyncCallback);

}
