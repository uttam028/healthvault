package cse.mlab.hvr.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.study.PreTestAnswers;
import cse.mlab.hvr.shared.study.PreTestQuestion;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("profile")
public interface ProfileService extends RemoteService {
	  
	  UserProfile getProfile(String email);
	  ArrayList<PreTestQuestion> getProfileInformation(String email);
	  Response updateProfileInfo(PreTestAnswers preTestAnswers);	  
	  ArrayList<PreTestQuestion> getPhysicalInformation(String email);
	  Response updatePhysicalInfo(PreTestAnswers preTestAnswers);
	  Response saveProfile(UserProfile userProfile);
	  Response profileUpdateRequired(String email);
}
