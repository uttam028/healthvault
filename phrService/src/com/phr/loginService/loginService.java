package com.phr.loginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import com.phr.util.DatabaseUtil;
import com.phr.util.Md5Utils;
import com.phr.util.Response;
import com.phr.util.ServiceUtil;

@Path("/login")
public class loginService {

	private Connection connection;
	//private PreparedStatement preparedStatement;

//	static {
//		System.out.println("loading database driver....login service");
//		DatabaseUtil.loadDriver();
//	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response login(JAXBElement<User> userJaxbElement) {
		Response response = new Response();
		try {
			User user = userJaxbElement.getValue();
			System.out.println("pass from client:"+user.getPassword());
			connection = DatabaseUtil.connectToDatabase();
				connection.createStatement();
				PreparedStatement preparedStatement = connection
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
						if (user.getPassword().equals(resultSet.getString("password"))) {
							String uuid = UUID.randomUUID().toString();
							try {
								preparedStatement = connection
										.prepareStatement("insert into  phr.session_info (session_id, user_id, login_time) values ( ?,?,now())");
								preparedStatement.setString(1, uuid);
								preparedStatement.setString(2, user.getEmail());
								preparedStatement.execute();
								response.setCode(0);
								response.setMessage(uuid);
								
							} catch (Exception e) {
								// TODO: handle exception
							}
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

	@Path("resetpassword")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	/**
	 * 
	 * @param email
	 * @return code=0: success, code=-1:failure, code=1: user doesn't exist
	 */
	public Response resetPassword(final ChangeAuth auth) {
		Response response = new Response();
		try {
			connection = DatabaseUtil.connectToDatabase();
			connection.createStatement();

			PreparedStatement preparedStatement = connection
					.prepareStatement("select email from phr.users where state=1 and email=?");
			preparedStatement.setString(1, auth.getEmail());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String newPass = ServiceUtil.randomString(12);
				String encryptedNewpass = Md5Utils.getMD5String(newPass);
				preparedStatement = connection.prepareStatement("update phr.users set password= ? where email=?");
				preparedStatement.setString(1, encryptedNewpass);
				preparedStatement.setString(2, auth.getEmail());
				preparedStatement.executeUpdate();
				
				try {
					String subject = "Reset Speech Marker Password";
					String emailBody = 	"\n\nYour password has been reset, and use new one to login. Please change your password from the profile section.\n"
										+ "\n New Password:		" + newPass;

					ServiceUtil.sendEmail(auth.getEmail(), subject, emailBody, "");
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				response.setCode(0);
				
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
	
	@POST
	@Path("sessioninfo")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response getSessionInfo(JAXBElement<Session> jaxbElement){
		Response response = new Response();
		try {
			Session session = jaxbElement.getValue();
			
			String sessionId = session.getSessionId();
			if(!ServiceUtil.isEmptyString(sessionId)){
				try {
					connection = DatabaseUtil.connectToDatabase();
					connection.createStatement();

					PreparedStatement preparedStatement = connection.prepareStatement("select user_id from phr.session_info where state=1 and session_id=?");
					preparedStatement.setString(1, sessionId);
					
					ResultSet resultSet = preparedStatement.executeQuery();
					if (resultSet.next()) {
						String userId = resultSet.getString("user_id");
						response.setCode(0);
						response.setMessage(userId);
					} 
					
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					if (connection != null)
						try {
							connection.close();
						} catch (SQLException ignore) {
						}			
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return response;
	}
	
	@POST
	@Path("logout")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response logout(JAXBElement<Session> jaxbElement){
		Response response = new Response();
		try {
			Session session = jaxbElement.getValue();
			
			String sessionId = session.getSessionId();
			if(!ServiceUtil.isEmptyString(sessionId)){
				try {
					connection = DatabaseUtil.connectToDatabase();
					connection.createStatement();

					PreparedStatement preparedStatement = connection.prepareStatement("update phr.session_info set state=0, logout_time=now() where session_id=?");
					preparedStatement.setString(1, sessionId);
					preparedStatement.executeUpdate();
					response.setCode(0);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally{
					if (connection != null)
						try {
							connection.close();
						} catch (SQLException ignore) {
						}			
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return response;
	}
	
	@POST
	@Path("changepassword")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response changePassword(final ChangeAuth changeAuth){
		Response response = new Response();
		try {
			//JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
			String email = changeAuth.getEmail();
			String oldPass = changeAuth.getOldPassword();
			String newPass = changeAuth.getNewPassword();
			
			System.out.println("email:"+ email + ", old pass :"+ oldPass + ", new pass:"+ newPass);
			
			if(ServiceUtil.isEmptyString(email) || ServiceUtil.isEmptyString(oldPass) || ServiceUtil.isEmptyString(newPass)){
				response.setCode(1);
				response.setMessage("Please try with valid input");
				return response;
			}
			
			try {
				connection = DatabaseUtil.connectToDatabase();
				connection.createStatement();

				PreparedStatement preparedStatement = connection.prepareStatement("select * from phr.users where email=? and password=?");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, oldPass);
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					preparedStatement = connection.prepareStatement("update phr.users set password= ? where email=?");
					preparedStatement.setString(1, newPass);
					preparedStatement.setString(2, email);
					preparedStatement.executeUpdate();
					response.setCode(0);
					
				}else {
					response.setCode(2);
					response.setMessage("Old password is not correct");
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException ignore) {
					}			
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return response;
	}
	
	@POST
	@Path("test")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String test(final String input){
		return "{\"result\": \"Hello world\"}";
		
	}
	
}
