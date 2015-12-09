package cse.mlab.hvr.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void signupToPhr(UserProfile userProfile, AsyncCallback<String> callback);
	void loginToPhr(User user, AsyncCallback<String> callback);
	void checkEmailAvailability(String email, AsyncCallback<String> callback);
	void getProfile(String email, AsyncCallback<UserProfile> callback);
	void saveProfile(UserProfile userProfile, AsyncCallback<Response> callback);
}
