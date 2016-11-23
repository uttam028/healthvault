package com.phr.signupService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import org.json.JSONObject;

import com.phr.util.DatabaseUtil;
import com.phr.util.Response;
import com.phr.util.ServiceUtil;
import com.phr.util.UserProfile;

@Path("/signup")
public class SignupService {

	private Connection connection = null;
	private Statement statement;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	static {
		DatabaseUtil.loadDriver();
	}

	@Path("{userid}")
	@GET
	@Produces("application/json")
	public String getUser(@PathParam("userid") String userid) {

		try {

			// setup the connection with the DB.
			// connect =
			// DriverManager.getConnection("jdbc:mysql://localhost/PHR?"
			// + "user=hasini&password=admin123");
			System.out.println("Connecting database...");
			connection = DatabaseUtil.connectToDatabase();
			try {

				statement = connection.createStatement();
				preparedStatement = connection
						.prepareStatement("SELECT * FROM PHR.USERS WHERE EMAIL=?");
				preparedStatement.setString(1, userid);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					return "TRUE";

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			System.out.println("Closing the connection.");
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}
		}

		return "FALSE";
	}

	/**
	 * 
	 * @param userProfileJAXBElement
	 * @return code=0: success, =1: user is not authorized, =-1: failure
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response createUser(JAXBElement<UserProfile> userProfileJAXBElement) {
		UserProfile userProfile = userProfileJAXBElement.getValue();
		// String success = "";
		Response response = new Response();
		boolean invitationActivated = false;
		boolean proceedToSignup = false;
		try {

			System.out.println("Connecting database...");
			connection = DatabaseUtil.connectToDatabase();
			try {
				statement = connection.createStatement();
				String email = userProfile.getEmail().trim();

				if(invitationActivated){
					//check invitation and then proceed
					preparedStatement = connection
							.prepareStatement("SELECT email FROM phr.user_invitation WHERE EMAIL=?");
					preparedStatement.setString(1, email);
					ResultSet resultSet = preparedStatement.executeQuery();
					if(resultSet.next()){
						proceedToSignup = true;
					} else {
						proceedToSignup = false;
					}
					
				} else {
					proceedToSignup = true;
				}
				
				if (proceedToSignup) {
					preparedStatement = connection
							.prepareStatement("insert ignore into  PHR.USERS (email, password, token) values ( ?,?,?)");
					preparedStatement.setString(1, email);
					preparedStatement.setString(2, userProfile.getPassword());
					preparedStatement
							.setString(3, ServiceUtil.randomString(32));
					preparedStatement.execute();

					preparedStatement = connection
							.prepareStatement("insert ignore into  PHR.USER_PROFILE (email, first_name, last_name) values ( ?,?, ?)");
					preparedStatement.setString(1, email);
					preparedStatement.setString(2, userProfile.getFirstName());
					preparedStatement.setString(3, userProfile.getLastName());

					preparedStatement.execute();
					// success = "The user " + userProfile.getEmail()
					// + " successfully signed up.";

					preparedStatement = connection
							.prepareStatement("select token from phr.users where email=?");
					preparedStatement.setString(1, email);
					resultSet = preparedStatement.executeQuery();
					if (resultSet.next()) {
						// got token
						String token = resultSet.getString("token");
						String subject = "Activate Speech Marker Account";
						String name = userProfile.getFirstName()==null?"":userProfile.getFirstName() + " " 
									+ userProfile.getLastName()==null?"":userProfile.getLastName();
						String emailBody = 	"\n\nThank you for joining us.\n"
											+ "\nClick below link to complete your registration:\n"
											+ ServiceUtil.getServiceRoot() + "verifyemail?token=" + token;

						ServiceUtil.sendEmail(email, subject, emailBody, name);
					}

					// } catch (MySQLIntegrityConstraintViolationException e){
					// success = "The user " + userProfile.getEmail() +
					// " already exist in the system." ;
					response.setCode(0);
				} else {
					// user has no invitation
					response.setCode(1);
					response.setMessage("User is not authorized to sign up");
					return response;
				}

			} catch (SQLException e) {
				// success =
				// "Error in user creation. Please refer server logs for more information";
				e.printStackTrace();
				response.setCode(-1);
				response.setMessage("Error in user creation. Try later.");
			}
		} finally {
			System.out.println("Closing the connection.");
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}
		}
		return response;
	}


	@GET
	@Path("verification/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response emailVerification(@PathParam("token") String token) {
		Response response = new Response();
		try {
			connection = DatabaseUtil.connectToDatabase();
			statement = connection.createStatement();

			preparedStatement = connection
					.prepareStatement("select * from phr.users where token=?");
			preparedStatement.setString(1, token);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int state = resultSet.getInt("state");
				if(state == 0){
					preparedStatement = connection
							.prepareStatement("update phr.users set state=1 where token=?");
					preparedStatement.setString(1, token);
					preparedStatement.executeUpdate();
					//successfully update account state
					response.setCode(0);
				} else {
					//email already verified
					response.setCode(2);
				}
			} else {
				//token does not exist
				response.setCode(1);
				response.setMessage("Invalid verification code. Please sign up.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}

		}
		return response;

	}

	@Path("test")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject sayPlainTextHello(JSONObject inputJsonObj)
			throws Exception {

		String input = (String) inputJsonObj.get("input");
		String output = "The input you sent is :" + input;
		JSONObject outputJsonObj = new JSONObject();
		outputJsonObj.put("output", output);

		return outputJsonObj;
	}

}