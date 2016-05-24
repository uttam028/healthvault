package cse.mlab.hvr.server;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import cse.mlab.hvr.client.GreetingService;
import cse.mlab.hvr.shared.Answer;
import cse.mlab.hvr.shared.Medication;
import cse.mlab.hvr.shared.MedicationList;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;

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
	public Response signupToPhr(UserProfile userProfile) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		//userProfile.setPassword("t");
		System.out.println("f"+userProfile.getFirstName()+", l:"+ userProfile.getLastName() + ", e:"+ userProfile.getEmail()+ ", p:"+ userProfile.getPassword());
		String url = serverRoot + signupPath;
		WebResource service = client.resource(url);
		service.accept(MediaType.APPLICATION_XML);
		ClientResponse nameResource = service.put(ClientResponse.class, userProfile);
		Response response = nameResource.getEntity(Response.class);
		System.out.println("Client Response \n" + response.getCode());

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff sign up call: " + (end - start));

		return response;
		
	}
	
	@Override
	public Response resetPassword(String email) {
		// TODO Auto-generated method stub
		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + loginPath + "resetpassword/" + email;
		System.out.println("url:"+url);
		WebResource service = client.resource(url);
		String result = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		Response response = new Gson().fromJson(result, Response.class);
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
		System.out
				.println(profile.getFirstName() + "," + profile.getBirthDay());

		List<Answer> profileAnswers = profile.getQuestionAnswer();
		Iterator<Answer> it = profileAnswers.iterator();
		while (it.hasNext()) {
			Answer temp = (Answer) it.next();
			System.out.println("question id:" + temp.getQuestionId()
					+ ", answer:" + temp.getAnswer());
		}

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

	@Override
	public Response saveMedication(Medication medication) {
		// TODO Auto-generated method stub

		long start = Calendar.getInstance().getTimeInMillis();
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		// WebResource service = client.resource(serverRoot + signupPath);
		String url = serverRoot + "/medication/medication/";
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, medication);
		Response saveMedicResult = nameResource.getEntity(Response.class);

		System.out.println("response code:" + saveMedicResult.getCode() + "message:" + saveMedicResult.getMessage());
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
		String url = serverRoot + "/medication/medication/update/"+medication.getId();
		WebResource service = client.resource(url);
		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML)
				.put(ClientResponse.class, medication);
		Response updateMedicResult = nameResource.getEntity(Response.class);

		System.out.println("response code:" + updateMedicResult.getCode() + "message:" + updateMedicResult.getMessage());
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
		String url = serverRoot + "/medication/medication/" + email;
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
		String url = serverRoot + "/medication/medication/" + email;
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
		System.out.println("list:"+ list);
		list = Base64.encodeBase64String(list.getBytes());
		long start = Calendar.getInstance().getTimeInMillis();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		String url = serverRoot + "/medication/medication/" + email + "/" + list;
		WebResource service = client.resource(url);

		String result = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		Response response = new Gson().fromJson(result, Response.class);

		System.out.println("delete response:"+ response.getCode());
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
		String url = serverRoot + "/medication/medication/endusing/" + id + "/" + endDate;
		WebResource service = client.resource(url);

		String result = service.accept(MediaType.APPLICATION_JSON).get(
				String.class);
		Response response = new Gson().fromJson(result, Response.class);

		System.out.println("end using response:"+ response.getCode());
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff delete medic list: " + (end - start));

		return response;
	}
	
	
	public void test() {
		Medication medication = new Medication();
		medication.setEmail("z@gmail.com");
		medication.setName("tes");
		medication.setStrength(1245);
		medication.setStrengthUnit("kg");
		medication.setConsumeFrequency("weekly");
		medication.setConsumeProcess("pacha");
		medication.setDosage(3);
		medication.setDosageUnit("stripe");
		medication.setEndDate("2015-12-31");
		medication.setInstruction("take it somehow");
		medication.setIsPrescribed("otc");
		medication.setNote("");
		medication.setPrescribedBy("me");
		medication.setPrescribedDate("2012-08-27");
		medication.setPrescribedQuantity("3 time daily");
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
			System.out.println("end using response:"+ response.getCode());
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("GreetingServiceImpl.test()");
			e.printStackTrace();
		}
		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("time diff end using: " + (end - start));

		//return response;

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
