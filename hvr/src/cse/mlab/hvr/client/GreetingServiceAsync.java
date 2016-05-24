package cse.mlab.hvr.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cse.mlab.hvr.shared.Medication;
import cse.mlab.hvr.shared.MedicationList;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void signupToPhr(UserProfile userProfile, AsyncCallback<Response> callback);
	void loginToPhr(User user, AsyncCallback<Response> callback);
	void resetPassword(String email, AsyncCallback<Response> callback);
	void checkEmailAvailability(String email, AsyncCallback<String> callback);
	void getProfile(String email, AsyncCallback<UserProfile> callback);
	void saveProfile(UserProfile userProfile, AsyncCallback<Response> callback);
	void saveMedication(Medication medication, AsyncCallback<Response> callback);
	void updateMedication(Medication medication, AsyncCallback<Response> callback);
	void getMedications(String email, AsyncCallback<String> callback);
	void getMedicationsList(String email, AsyncCallback<MedicationList> callback);
	void deleteMedications(String email, String list, AsyncCallback<Response> callback);
	void stopUsingMedication(long id, String endDate, AsyncCallback<Response> callback);
}
