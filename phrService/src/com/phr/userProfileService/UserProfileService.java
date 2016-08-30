package com.phr.userProfileService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import com.phr.util.DatabaseUtil;
import com.phr.util.Response;
import com.phr.util.UserProfile;

@Path("/profile")
public class UserProfileService {

	private Connection connection = null;
	private Statement statement;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	static {
		DatabaseUtil.loadDriver();
	}

	@Path("{email}")
	@GET
	@Produces("application/json")
	public UserProfile getUser(@PathParam("email") String email) {

		UserProfile profile = new UserProfile();
		try {

			System.out.println("Connecting to database...");
			connection = DatabaseUtil.connectToDatabase();
			try {

				statement = connection.createStatement();
				preparedStatement = connection
						.prepareStatement("select * from phr.user_profile where email=?");

				preparedStatement.setString(1, email);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					profile.setEmail(resultSet.getString("email"));
					profile.setFirstName(resultSet.getString("first_name"));
					profile.setLastName(resultSet.getString("last_name"));
					profile.setGender(resultSet.getString("gender"));
					int birthYear = 0;
					try {
						birthYear = resultSet.getInt("birth_year");
					} catch (Exception e) {
						// TODO: handle exception
					}
					profile.setBirthYear(birthYear);
					profile.setPrimaryLanguage(resultSet.getString("primary_language"));
					profile.setPhoneNumber(resultSet.getString("phone_number"));
					profile.setAddress(resultSet.getString("address"));
				}

				/*
				preparedStatement = connection
						.prepareStatement("select * from phr.question_answer where user_email=?");
				preparedStatement.setString(1, email);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Answer temp = new Answer();
					temp.setQuestionId(resultSet.getInt("question_id"));
					temp.setAnswer(resultSet.getString("answer"));
					profile.getQuestionAnswer().add(temp);
				}*/
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

		return profile;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response createUser(JAXBElement<UserProfile> userProfileJAXBElement) {
		UserProfile userProfile = userProfileJAXBElement.getValue();
		return saveUser(userProfile);

	}

	private Response saveUser(UserProfile userProfile) {

		Response response = new Response();
		try {
			System.out.println("Connecting database...");
			connection = DatabaseUtil.connectToDatabase();
			try {
				statement = connection.createStatement();

				preparedStatement = connection
						.prepareStatement("update phr.user_profile set first_name=?, last_name=?, gender=?, birth_year=?, primary_language=?, phone_number=?, address=? where email=?");
				preparedStatement.setString(1, userProfile.getFirstName());
				preparedStatement.setString(2, userProfile.getLastName());				
				preparedStatement.setString(3, userProfile.getGender());
				preparedStatement.setString(4, String.valueOf(userProfile.getBirthYear()));
				preparedStatement.setString(5, userProfile.getPrimaryLanguage());
				preparedStatement.setString(6, userProfile.getPhoneNumber());
				preparedStatement.setString(7, userProfile.getAddress());
				preparedStatement.setString(8, userProfile.getEmail());
				
				System.out.println("query: " + preparedStatement.toString());
				preparedStatement.execute();

				
				/*
				Iterator<Answer> it = userProfile.getQuestionAnswer()
						.iterator();
				while (it.hasNext()) {
					Answer temp = (Answer) it.next();
					preparedStatement = connection
							.prepareStatement("insert into phr.question_answer (user_email, question_id, answer ) "
									+ "values (?, ?, ?) on duplicate key update answer=values(answer)");
					preparedStatement.setString(1, userProfile.getEmail());
					preparedStatement.setInt(2, temp.getQuestionId());
					preparedStatement.setString(3, temp.getAnswer());
					preparedStatement.execute();
				}*/
				//success = "The profile " + userProfile.getEmail()
						//+ " updated successfully.";
				response.setCode(0);
				response.setMessage("The profile " + userProfile.getEmail() + " updated successfully.");

			} catch (SQLException e) {
				response.setCode(-1);
				response.setMessage("Error in updating profile. Please refer server logs for more information");
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
		return response;
	}

}