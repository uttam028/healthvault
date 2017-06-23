package cse.mlab.hvr.server;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import cse.mlab.hvr.client.GreetingService;
import cse.mlab.hvr.shared.Diagnosis;
import cse.mlab.hvr.shared.DiagnosisList;
import cse.mlab.hvr.shared.Medication;
import cse.mlab.hvr.shared.MedicationList;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.Session;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.UserRole;
import cse.mlab.hvr.shared.study.Enrollment;
import cse.mlab.hvr.shared.study.MyStudyDataModel;
import cse.mlab.hvr.shared.study.Participation;
import cse.mlab.hvr.shared.study.Recording;
import cse.mlab.hvr.shared.study.SpeechTest;
import cse.mlab.hvr.shared.study.StudyPrefaceModel;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	static String serverRoot = "";
	static String signupPath = "";
	static String loginPath = "";
	static String profilePath = "";

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		serverRoot = config.getServletContext().getInitParameter("serverRoot");
		signupPath = getInitParameter("signupPath");
		loginPath = getInitParameter("loginPath");
		profilePath = getInitParameter("profilePath");
		System.out.println("server root : " + serverRoot + ", signuppath : "
				+ signupPath + ", loginpath : " + loginPath);
	}

	public String greetServer(String input) throws IllegalArgumentException {
		// // Verify that the input is valid.
		// if (!FieldVerifier.isValidName(input)) {
		// // If the input is not valid, throw an IllegalArgumentException back
		// // to
		// // the client.
		// throw new IllegalArgumentException(
		// "Name must be at least 4 characters long");
		// }

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	@Override
	public Response getSessionInformation(Session session) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + loginPath + "sessioninfo";
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, session);
		// System.out.println("Client Response \n"
		// + nameResource.getEntity(String.class));
		Response response = nameResource.getEntity(Response.class);
		System.out.println("convert response : " + response.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff session call: " + (end - start));

		return response;
	}
	
	@Override
	public Response signupToPhr(UserProfile userProfile) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// userProfile.setPassword("t");
		System.out.println("f" + userProfile.getFirstName() + ", l:"
				+ userProfile.getLastName() + ", e:" + userProfile.getEmail()
				+ ", p:" + userProfile.getPassword());
		String url = serverRoot + signupPath;
		WebResource service = client.resource(url);
		service.accept(MediaType.APPLICATION_XML);
		ClientResponse nameResource = service.put(ClientResponse.class,
				userProfile);
		Response response = nameResource.getEntity(Response.class);
		System.out.println("Client Response \n" + response.getCode());

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff sign up call: " + (end - start));

		return response;

	}
	
	
	@Override
	public Response logout(Session session) {
		
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + loginPath + "logout";
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, session);
		// System.out.println("Client Response \n"
		// + nameResource.getEntity(String.class));
		Response response = nameResource.getEntity(Response.class);
		System.out.println("convert response : " + response.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff logout call: " + (end - start));

		return response;
	}

	@Override
	public Response resetPassword(String email) {

		System.out.println("email :"+ email);
		String json = "{\"email\":\""+email+"\", \"oldPassword\":\"\", \"newPassword\":\"\"}";
		
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + loginPath + "resetpassword/";
		System.out.println("url:" + url);
		WebResource service = client.resource(url);
		ClientResponse clientResponse = service.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
		String jsonResponse = clientResponse.getEntity(String.class);
		Response response = new Gson().fromJson(jsonResponse, Response.class);
		System.out.println("Client Response \n" + response.getCode());

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff reset pass call: " + (end - start));

		return response;

	}
	
	@Override
	public Response changePassword(String email, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		String json = "{\"email\":\""+email+"\", \"oldPassword\":\""+ oldPassword +"\", \"newPassword\":\""+ newPassword +"\"}";
		System.out.println("json:"+json);		
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + loginPath + "changepassword/";
		WebResource service = client.resource(url);
		ClientResponse clientResponse = service.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
		String jsonResponse = clientResponse.getEntity(String.class);
		Response response = new Gson().fromJson(jsonResponse, Response.class);
		System.out.println("Client Response \n" + response.getCode());

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff reset pass call: " + (end - start));

		return response;
	}

	@Override
	public String checkEmailAvailability(String email) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + signupPath + email;
		WebResource service = client.resource(url);
		String response = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		// System.out.println("Client Response \n"
		// + nameResource.getEntity(String.class));
		System.out.println("availability response : " + response);
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff email availability call: "
				+ (end - start));

		return response;
	}
	
	@Override
	public UserRole getRole(String email) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		/*
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);

		String url = serverRoot + "userrole/" + email;
		WebResource service = client.resource(url);
		UserRole response = service.accept(MediaType.APPLICATION_JSON).get(
				UserRole.class);
		System.out.println("availability response : " + response);
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff email availability call: "
				+ (end - start));
		
		return response;*/
		return new UserRole(true, false, false);
	}
	

	@Override
	public Response loginToPhr(User user) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + loginPath;
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, user);
		// System.out.println("Client Response \n"
		// + nameResource.getEntity(String.class));
		Response response = nameResource.getEntity(Response.class);
		System.out.println("convert response : " + response.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff login call: " + (end - start));

		return response;
	}

	@Override
	public Response profileUpdateRequired(String email) {
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + profilePath + "updaterequired/" +email;
		WebResource service = client.resource(url);
		String reply = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		System.out.println("update required response:"+ reply);
		Response response = new Gson().fromJson(reply, Response.class);
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff update required call: " + (end - start));

		return response;
	}
	
	@Override
	public UserProfile getProfile(String email) {

		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + profilePath + email;
		WebResource service = client.resource(url);
		String response = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		System.out.println("profile xml:" + response);
		UserProfile profile = new Gson().fromJson(response, UserProfile.class);
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff get profile call: " + (end - start));

		return profile;
	}
	

	@Override
	public Response saveProfile(UserProfile userProfile) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + profilePath;
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, userProfile);
		Response profileUpdateResult = nameResource.getEntity(Response.class);

		System.out.println("response code:" + profileUpdateResult.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff update profile call: " + (end - start));

		return profileUpdateResult;
	}

	//-----------------------------------medication--------------------------//
	@Override
	public Response saveMedication(Medication medication) {
		// TODO Auto-generated method stub

		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + "/medication/";
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, medication);
		Response saveMedicResult = nameResource.getEntity(Response.class);

		System.out.println("response code:" + saveMedicResult.getCode()
				+ "message:" + saveMedicResult.getMessage());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff save medic call: " + (end - start));
		return saveMedicResult;

	}

	@Override
	public Response updateMedication(Medication medication) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + "/medication/update/" + medication.getId();
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, medication);
		Response updateMedicResult = nameResource.getEntity(Response.class);

		System.out.println("response code:" + updateMedicResult.getCode()
				+ "message:" + updateMedicResult.getMessage());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff update medic call: " + (end - start));
		return updateMedicResult;
	}

	@Override
	public String getMedications(String email) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/medication/" + email;
		WebResource service = client.resource(url);

		String response = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);

		MedicationList medicationList = new Gson().fromJson(response,
				MedicationList.class);
		List<Medication> medList = medicationList.getMedicationList();
		Iterator<Medication> it = medList.iterator();
		while (it.hasNext()) {
			Medication med = (Medication) it.next();
			System.out.println("id:" + med.getId() + ", email:"
					+ med.getEmail() + ", name:" + med.getName());
		}
		System.out.println("response:" + response);
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff update profile call: " + (end - start));
		return response;
	}

	@Override
	public MedicationList getMedicationsList(String email) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/medication/" + email;
		WebResource service = client.resource(url);

		String response = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);

		MedicationList medicationList = new Gson().fromJson(response,
				MedicationList.class);
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff get medic list: " + (end - start));
		return medicationList;
	}

	@Override
	public Response deleteMedications(String email, String list) {
		// TODO Auto-generated method stub
		System.out.println("list:" + list);
		list = Base64.encodeBase64String(list.getBytes());
		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/medication/" + email + "/" + list;
		WebResource service = client.resource(url);

		String result = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		Response response = new Gson().fromJson(result, Response.class);

		System.out.println("delete response:" + response.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff delete medic list: " + (end - start));

		return response;
	}

	@Override
	public Response stopUsingMedication(long id, String endDate) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/medication/endusing/" + id + "/" + endDate;
		WebResource service = client.resource(url);

		String result = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		Response response = new Gson().fromJson(result, Response.class);

		System.out.println("end using response:" + response.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff delete medic list: " + (end - start));

		return response;
	}

	public void test() {
		Medication medication = new Medication();
		medication.setEmail("z@gmail.com");
		medication.setName("tes");
//		medication.setStrength(1245);
//		medication.setStrengthUnit("kg");
//		medication.setConsumeFrequency("weekly");
//		medication.setConsumeProcess("pacha");
//		medication.setDosage(3);
//		medication.setDosageUnit("stripe");
		medication.setEndDate("2015-12-31");
//		medication.setInstruction("take it somehow");
//		medication.setIsPrescribed("otc");
//		medication.setNote("");
//		medication.setPrescribedBy("me");
//		medication.setPrescribedDate("2012-08-27");
//		medication.setPrescribedQuantity("3 time daily");
		medication.setReason("");
		medication.setStartDate("2014-12-07");

		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/medication/medication/z@gmail.com/1";

		try {
			System.out.println("Going to call end using....");
			WebResource service = client.resource(url);
			Response response = service.accept(MediaType.APPLICATION_JSON).get(
					Response.class);
			System.out.println("end using response:" + response.getCode());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("GreetingServiceImpl.test()");
			e.printStackTrace();
		}
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff end using: " + (end - start));

		// return response;

	}
	
	//--------------------------------------diagnosis----------------------------------//
	
	@Override
	public Response saveDiagnosis(Diagnosis diagnosis) {
		System.out.println("dianosis: "+ diagnosis.toString());
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + "/diagnosis/";
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, diagnosis);
		Response saveDiagResult = nameResource.getEntity(Response.class);

		System.out.println("response code:" + saveDiagResult.getCode()
				+ "message:" + saveDiagResult.getMessage());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff save diagnosis call: " + (end - start));
		return saveDiagResult;
	}
	
	@Override
	public DiagnosisList getDiagnosisList(String email) {
		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/diagnosis/" + email;
		WebResource service = client.resource(url);

		String response = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);

		DiagnosisList diagnosisList = new Gson().fromJson(response,
				DiagnosisList.class);
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff get diagnosis list: " + (end - start));
		return diagnosisList;
	}
	
	@Override
	public Response updateDiagnosis(Diagnosis diagnosis) {
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + "/diagnosis/update/" + diagnosis.getId();
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, diagnosis);
		Response updateDiagnosisResult = nameResource.getEntity(Response.class);

		System.out.println("response code:" + updateDiagnosisResult.getCode()
				+ "message:" + updateDiagnosisResult.getMessage());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff update diagnosis call: " + (end - start));
		return updateDiagnosisResult;
	}
	
	@Override
	public Response deleteDiagnosis(String email, String list) {
		System.out.println("list:" + list);
		list = Base64.encodeBase64String(list.getBytes());
		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/diagnosis/" + email + "/" + list;
		WebResource service = client.resource(url);

		String result = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		Response response = new Gson().fromJson(result, Response.class);

		System.out.println("delete response:" + response.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff delete diagnosis list: " + (end - start));

		return response;
	}

	// ------------------speech test----------------------//
	@Override
	public ArrayList<StudyPrefaceModel> getOpenStudies(String email) {

		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/study/open/" + email;
		WebResource service = client.resource(url);

		String response = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);

		// ArrayList<StudyPrefaceModel> models = new Gson().fromJson(response,
		// new Typetok);
		Gson gson = new Gson();
		Type listOfTestObject = new TypeToken<ArrayList<StudyPrefaceModel>>() {
		}.getType();
		ArrayList<StudyPrefaceModel> list = gson.fromJson(response,
				listOfTestObject);

		System.out.println("response from server:" + response);

		System.out.println("length of list : " + list.size());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff get open study: " + (end - start));

		return list;
		/*
		 * String pathRoot = "http://10.32.10.188:8080/hvr/metadata/study/";
		 * String studyId = "1"; StudyOverview studyOverview1= new
		 * StudyOverview(studyId, "Notre Dame Parkinson Study", "test1",
		 * "ahossain@nd.edu", "", "",
		 * "This is a test for parkinson patient which will take around 10 minutes."
		 * , "", true, true, pathRoot +studyId+ "/" + "consent.pdf", 2, "1" ,
		 * "1");
		 * 
		 * StudyOverview studyOverview2= new StudyOverview("2",
		 * "Concussion Study", "test2", "ahossain@nd.edu", "", "",
		 * "This is a test for parkinson patient which will take around 10 minutes."
		 * , "", true, true, pathRoot + "2/" + "consent.pdf", 2, "2" , "2");
		 * 
		 * 
		 * ArrayList<QA> qaList1 = new ArrayList<>(); qaList1.add(new
		 * QA("Can I take the Test?",
		 * "If you are suffering from neuorological disorder, you can take the test."
		 * )); qaList1.add(new QA("How long the test will be?",
		 * "It is around 25 minutes, please don't refresh or back button in the test part."
		 * )); qaList1.add(new QA("Is there any reward for this test?",
		 * "For each time, you will earn 10 points and if you achieve 50 points over three months duration you will get 10 dollar scratch card."
		 * ));
		 * 
		 * ArrayList<QA> qaList2 = new ArrayList<>(); qaList2.add(new
		 * QA("Can I take the Test?",
		 * "If you have possibility of concussion, you can take part."));
		 * qaList2.add(new QA("How long the test will be?",
		 * "It is around 15 minutes, please don't refresh or back button in the test part."
		 * ));
		 * 
		 * ArrayList<HealthStatusQuestion> healthQuestions = new ArrayList<>();
		 * HealthStatusQuestion q1 = new HealthStatusQuestion(
		 * "Have you received or are currently receiving speech therapy for your condition?"
		 * , "", false, 1, true);
		 * 
		 * HealthStatusQuestion q2 = new HealthStatusQuestion(
		 * "What medication are you taking for your current condition?" , "",
		 * false, 2, true); HealthStatusQuestion q3 = new HealthStatusQuestion(
		 * "Any other relevant medical information (Condition, Treatments, Medications?)"
		 * , "Type 'NA' if not applicable", false, 3, true);
		 * 
		 * healthQuestions.add(q1); healthQuestions.add(q2);
		 * healthQuestions.add(q3);
		 * 
		 * StudyPrefaceModel model1 = new StudyPrefaceModel(studyOverview1,
		 * qaList1, healthQuestions);
		 * 
		 * StudyPrefaceModel model2 = new StudyPrefaceModel(studyOverview2,
		 * qaList2, healthQuestions);
		 * 
		 * ArrayList<StudyPrefaceModel> testPrefaceModels = new ArrayList<>();
		 * //testPrefaceModels.add(model1); testPrefaceModels.add(model2);
		 * return testPrefaceModels;
		 */
	}

	@Override
	public ArrayList<MyStudyDataModel> getMyStudies(String email) {
		try {
			long start = Calendar.getInstance().getTimeInMillis();

			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			String url = serverRoot + "/study/enrolled/" + email;
			WebResource service = client.resource(url);

			String response = service.accept(MediaType.APPLICATION_JSON).get(
					String.class);

			// ArrayList<StudyPrefaceModel> models = new
			// Gson().fromJson(response,
			// new Typetok);
			Gson gson = new Gson();
			Type listOfTestObject = new TypeToken<ArrayList<MyStudyDataModel>>() {}.getType();
			ArrayList<MyStudyDataModel> list = gson.fromJson(response,
					listOfTestObject);

			System.out.println("response from server:" + response);

			System.out.println("length of list : " + list.size());
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("time diff get open study: " + (end - start));

			return list;

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
/*
		try {
			String pathRoot = "http://10.32.10.188:8080/hvr/metadata/study/";
			String studyId = "1";
			StudyOverview studyOverview1 = new StudyOverview(
					studyId,
					"Notre Dame Parkinson Study",
					"test1",
					"ahossain@nd.edu",
					"",
					"",
					"This is a test for parkinson patient which will take around 10 minutes.",
					"", true, true, pathRoot + studyId + "/" + "consent.pdf",
					2, "1", "1");

			Compliance compliance = new Compliance("1", "weekly", 1, 40);
			ArrayList<MyStudyDataModel> myStudies = new ArrayList<>();
			ArrayList<QA> qaList1 = new ArrayList<>();
			qaList1.add(new QA("Can I take the Test?",
					"If you are suffering from neuorological disorder, you can take the test."));
			qaList1.add(new QA(
					"How long the test will be?",
					"It is around 25 minutes, please don't refresh or back button in the test part."));
			qaList1.add(new QA(
					"Is there any reward for this test?",
					"For each time, you will earn 10 points and if you achieve 50 points over three months duration you will get 10 dollar scratch card."));

			MyStudyDataModel model1 = new MyStudyDataModel(studyOverview1,
					compliance, qaList1, "2016-08-01", "2016-08-17", 3);
			myStudies.add(model1);
			myStudies.add(model1);
			return myStudies;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
		*/
	}

	@Override
	public SpeechTest getSpeechTestMetadata(String testId) {
		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/study/metadata/" + testId;
		WebResource service = client.resource(url);

		String result = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		System.out.println("response from server : " + result);
		SpeechTest speechTest = new Gson().fromJson(result, SpeechTest.class);

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff get meta data: " + (end - start));
		return speechTest;

		/*
		 * // TODO Auto-generated method stub String pathRoot =
		 * "http://10.32.10.188:8080/hvr/metadata/test/"+ testId + "/"; int
		 * subtestId1 = 1; String subtest1Path = pathRoot + subtestId1 + "/";
		 * TestFragment sub1frag1 = new TestFragment(1,
		 * "We saw several wild animals", "", subtest1Path + 1 +
		 * "/sentences1.mp3", false, -1, true, 5000); TestFragment sub1frag2 =
		 * new TestFragment(2, "My physician wrote out a prescription", "",
		 * subtest1Path + 2 + "/sentences2.mp3", false, -1, true, 5000);
		 * TestFragment sub1frag3 = new TestFragment(3,
		 * "The municipal judge sentenced the criminal", "", subtest1Path + 3 +
		 * "/sentences3.mp3", false, -1, true, 5000); ArrayList<TestFragment>
		 * test1Fragments = new ArrayList<>(); test1Fragments.add(sub1frag1);
		 * test1Fragments.add(sub1frag2); test1Fragments.add(sub1frag3); SubTest
		 * subTest1 = new SubTest(subtestId1, "test1", "", "", "", true,
		 * "Please repeat the sentences after I say them", subtest1Path +
		 * "/repeatSentences.mp3", test1Fragments);
		 * 
		 * int subtestId2 = 2; String subtest2Path = pathRoot + subtestId2 +
		 * "/"; TestFragment sub2frag1 = new TestFragment(1, "Participate", "",
		 * subtest2Path + 1 + "/comp1.mp3", false, -1, true, 2500); TestFragment
		 * sub2frag2 = new TestFragment(2, "Application", "", subtest2Path + 2 +
		 * "/comp2.mp3", false, -1, true, 2500); TestFragment sub2frag3 = new
		 * TestFragment(3, "Education", "", subtest2Path + 3 + "/comp3.mp3",
		 * false, -1, true, 2500); TestFragment sub2frag4 = new TestFragment(4,
		 * "Difficulty", "", subtest2Path + 4 + "/comp4.mp3", false, -1, true,
		 * 2500); TestFragment sub2frag5 = new TestFragment(5,
		 * "Congratulations", "", subtest2Path + 5 + "/comp5.mp3", false, -1,
		 * true, 2500); TestFragment sub2frag6 = new TestFragment(6,
		 * "Possibility", "", subtest2Path + 6 + "/comp6.mp3", false, -1, true,
		 * 2500); TestFragment sub2frag7 = new TestFragment(7, "Mathematical",
		 * "", subtest2Path + 7 + "/comp7.mp3", false, -1, true, 2500);
		 * TestFragment sub2frag8 = new TestFragment(8, "Opportunity", "",
		 * subtest2Path + 8 + "/comp8.mp3", false, -1, true, 3000); TestFragment
		 * sub2frag9 = new TestFragment(9, "Statistical Analysis", "",
		 * subtest2Path + 9 + "/comp9.mp3", false, -1, true, 4000); TestFragment
		 * sub2frag10 = new TestFragment(10, "Methodist Episcopal Church", "",
		 * subtest2Path + 10 + "/comp10.mp3", false, -1, true, 5000);
		 * 
		 * ArrayList<TestFragment> test2Fragments = new ArrayList<>();
		 * test2Fragments.add(sub2frag1); test2Fragments.add(sub2frag2);
		 * test2Fragments.add(sub2frag3); test2Fragments.add(sub2frag4);
		 * test2Fragments.add(sub2frag5); test2Fragments.add(sub2frag6);
		 * test2Fragments.add(sub2frag7); test2Fragments.add(sub2frag8);
		 * test2Fragments.add(sub2frag9); test2Fragments.add(sub2frag10);
		 * SubTest subTest2 = new SubTest(subtestId2, "test2", "", "", "", true,
		 * "Please repeat the words after I say them", subtest2Path +
		 * "/repeatWords.mp3", test2Fragments);
		 * 
		 * 
		 * int subtestId3 = 3; String subtest3Path = pathRoot + subtestId3 +
		 * "/"; TestFragment sub3frag1 = new TestFragment(1, "", subtest3Path +
		 * 1 +"/family.jpg", subtest3Path + 1 + "/picture.mp3", true, 5000,
		 * false, -1); ArrayList<TestFragment> test3Fragments = new
		 * ArrayList<>(); test3Fragments.add(sub3frag1); SubTest subTest3 = new
		 * SubTest(subtestId3, "test1", "", "", "", true, "", "",
		 * test3Fragments);
		 * 
		 * ArrayList<SubTest> subTests = new ArrayList<>();
		 * subTests.add(subTest1); subTests.add(subTest2);
		 * subTests.add(subTest3);
		 * 
		 * StudyOverview testOverview= new StudyOverview("1", "Parkinson Study",
		 * "test1", "ahossain@nd.edu", "", "",
		 * "This is a test for parkinson patient which will take around 10 minutes."
		 * , "", true, true, pathRoot + "consent.pdf", 2, "1" , "1");
		 * 
		 * ArrayList<HealthStatusQuestion> healthQuestions = new ArrayList<>();
		 * HealthStatusQuestion q1 = new HealthStatusQuestion(
		 * "Have you received or are currently receiving speech therapy for your condition?"
		 * , "", false, 1, true);
		 * 
		 * HealthStatusQuestion q2 = new HealthStatusQuestion(
		 * "What medication are you taking for your current condition?" , "",
		 * false, 2, true); HealthStatusQuestion q3 = new HealthStatusQuestion(
		 * "Any other relevant medical information (Condition, Treatments, Medications?)"
		 * , "Type 'NA' if not applicable", false, 3, true);
		 * 
		 * healthQuestions.add(q1); healthQuestions.add(q2);
		 * healthQuestions.add(q3);
		 * 
		 * SpeechTestMetadata speechTestMetadata = new
		 * SpeechTestMetadata(testOverview, subTests); return
		 * speechTestMetadata;
		 */
	}

	@Override
	public Response enrollToStudy(String studyId, String email) {
		
		Enrollment enrollment = new Enrollment(0, Integer.parseInt(studyId), email, "", 3);
		
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + "/study/enrollment/create";
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, enrollment);
		// System.out.println("Client Response \n"
		// + nameResource.getEntity(String.class));
		Response response = nameResource.getEntity(Response.class);
		System.out.println("convert response : " + response.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff login call: " + (end - start));

		return response;
		
	}

	@Override
	public Response startParticipation(String studyId, String email) {
		
		Participation participation = new Participation();
		participation.setStudyId(Integer.parseInt(studyId));
		participation.setUserId(email);
		
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + "/study/participation/start";
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, participation);
		// System.out.println("Client Response \n"
		// + nameResource.getEntity(String.class));
		Response response = nameResource.getEntity(Response.class);
		System.out.println("response start participation : " + response.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff start part call: " + (end - start));

		return response;
	}
	
	@Override
	public Response endParticipation(String studyId, String email) {
		
		Participation participation = new Participation();
		participation.setStudyId(Integer.parseInt(studyId));
		participation.setUserId(email);

		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + "/study/participation/end";
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.post(ClientResponse.class, participation);
		// System.out.println("Client Response \n"
		// + nameResource.getEntity(String.class));
		Response response = nameResource.getEntity(Response.class);
		System.out.println("response of end participation: " + response.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff end part call: " + (end - start));

		return response;
	}
	
	@Override
	public Response relocateSoundFile(Recording recording) {
		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/files/relocate";
		WebResource service = client.resource(url);

		ClientResponse clientResponse = service.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, new Gson().toJson(recording));
		String jsonResponse = clientResponse.getEntity(String.class);
		Response response = new Gson().fromJson(jsonResponse, Response.class);
		System.out.println("Client Response \n" + response.getCode());

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff relocate call: " + (end - start));

		return response;
	}
	
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
