package cse.mlab.hvr.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.study.PreTestAnswers;
import cse.mlab.hvr.shared.study.PreTestQuestion;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ProfileServiceAsync {

	void getProfile(String email, AsyncCallback<UserProfile> callback);
	void getProfileInformation(String email, AsyncCallback<ArrayList<PreTestQuestion>> callback);
	void updateProfileInfo(PreTestAnswers preTestAnswers, AsyncCallback<Response> callback);
	void getPhysicalInformation(String email, AsyncCallback<ArrayList<PreTestQuestion>> callback);
	void updatePhysicalInfo(PreTestAnswers preTestAnswers, AsyncCallback<Response> callback);
	void saveProfile(UserProfile userProfile, AsyncCallback<Response> callback);
	void profileUpdateRequired(String email, AsyncCallback<Response> callback);
}
