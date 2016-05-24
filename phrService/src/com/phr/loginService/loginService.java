package com.phr.loginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import com.phr.util.ServiceUtil;

@Path("/login")
public class loginService {

	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;

	static {
		DatabaseUtil.loadDriver();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response login(JAXBElement<User> userJaxbElement) {
		Response response = new Response();
		try {
			User user = userJaxbElement.getValue();
			System.out.println("pass from client:"+user.getPassword());
			connection = DatabaseUtil.connectToDatabase();
				statement = connection.createStatement();
				preparedStatement = connection
						.prepareStatement("select * from phr.users where email=?");
				preparedStatement.setString(1, user.getEmail());
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					int state = resultSet.getInt("state");
					if(state==0){
						response.setCode(2);
						response.setMessage("Please verify your account before login");
					} else {
						// check password
						if (user.getPassword().equals(
								resultSet.getString("password"))) {
							response.setCode(0);
						} else {
							response.setCode(3);
							response.setMessage("Incorrect password");
						}
						
					}
				} else {
					response.setCode(1);
					response.setMessage("Incorrect username");
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.setCode(-1);
			response.setMessage("Service not available, please try later");
			e.printStackTrace();
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

	@Path("resetpassword/{email}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	/**
	 * 
	 * @param email
	 * @return code=0: success, code=-1:failure, code=1: user doesn't exist
	 */
	public Response resetPassword(@PathParam("email") String email) {
		Response response = new Response();
		try {
			connection = DatabaseUtil.connectToDatabase();
			statement = connection.createStatement();

			preparedStatement = connection
					.prepareStatement("select email from phr.users where state=1 and email=?");
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String newPass = ServiceUtil.randomString(12);

			} else {
				response.setCode(1);
				response.setMessage("User does not exist");
			}
		} catch (Exception ex) {

		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}

		}
		return response;

	}
}
