package cse.mlab.hvr.server;

import java.io.InputStream;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cse.mlab.hvr.client.services.GreetingService;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.SpeechSession;
import cse.mlab.hvr.shared.User;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.UserRole;
import cse.mlab.hvr.shared.Util;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	static String serverRoot = "";
	static String signupPath = "";
	static String loginPath = "";
	static String profilePath = "";

	private static final String DRIVER = "com.mysql.jdbc.Driver";

	// private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String dbUrl, dbUsername, dbPassword;
	static final String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	static {
		try {
			Class.forName(DRIVER).newInstance();
			System.out.println("Load DB driver successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(alphanumeric.charAt(rnd.nextInt(alphanumeric.length())));
		return sb.toString();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		serverRoot = config.getServletContext().getInitParameter("serverRoot");
		signupPath = getInitParameter("signupPath");
		loginPath = getInitParameter("loginPath");
		profilePath = getInitParameter("profilePath");
		System.out
				.println("server root : " + serverRoot + ", signuppath : " + signupPath + ", loginpath : " + loginPath);

		Properties properties = new Properties();
		InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/system.properties");

		try {
			properties.load(inputStream);
			dbUrl = properties.getProperty("db_host") + "/" + properties.getProperty("db_schema")
					+ "?serverTimezone=UTC";
			dbUsername = properties.getProperty("db_username");
			dbPassword = properties.getProperty("db_password");
			System.out.println("db prop, dburl:" + dbUrl + ", user:" + dbUsername + ", pass:" + dbPassword);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Connection connect() {
		try {
			return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Response getSessionInformation(SpeechSession session) {

		Connection connection = null;
		Response response = new Response();
		try {
			String sessionId = session.getSessionId();
			if (!Util.isEmptyString(sessionId)) {
				try {
					connection = connect();

					PreparedStatement preparedStatement = connection
							.prepareStatement("select user_id from phr.session_info where state=1 and session_id=?");
					preparedStatement.setString(1, sessionId);

					ResultSet resultSet = preparedStatement.executeQuery();
					if (resultSet.next()) {
						String userId = resultSet.getString("user_id");
						response.setCode(0);
						response.setMessage(userId);
					}

				} catch (Exception e) {
					// TODO: handle exception
				} finally {
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

	@Override
	public Response signupToPhr(UserProfile userProfile) {


		Connection connection = null;
		Response response = new Response();
		boolean invitationActivated = false;
		boolean proceedToSignup = false;
		try {

			System.out.println("Connecting database...");
			connection = connect();
			try {
				// statement = connection.createStatement();
				String email = userProfile.getEmail().trim();
				PreparedStatement preparedStatement = null;

				if (invitationActivated) {
					// check invitation and then proceed
					preparedStatement = connection
							.prepareStatement("select email from phr.user_invitation where email=?");
					preparedStatement.setString(1, email);
					ResultSet resultSet = preparedStatement.executeQuery();
					if (resultSet.next()) {
						proceedToSignup = true;
					} else {
						proceedToSignup = false;
					}

				} else {
					proceedToSignup = true;
				}

				if (proceedToSignup) {
					preparedStatement = connection
							.prepareStatement("insert ignore into  phr.users (email, password, token) values ( ?,?,?)");
					preparedStatement.setString(1, email);
					preparedStatement.setString(2, userProfile.getPassword());
					preparedStatement.setString(3, randomString(32));
					preparedStatement.execute();

					preparedStatement = connection.prepareStatement(
							"insert ignore into  phr.user_profile (email, first_name, last_name) values ( ?,?, ?)");
					preparedStatement.setString(1, email);
					preparedStatement.setString(2, userProfile.getFirstName());
					preparedStatement.setString(3, userProfile.getLastName());

					preparedStatement.execute();
					// success = "The user " + userProfile.getEmail()
					// + " successfully signed up.";
					
					System.out.println("created user profile, going to send activation email");

					preparedStatement = connection.prepareStatement("select token from phr.users where email=?");
					preparedStatement.setString(1, email);
					ResultSet resultSet = preparedStatement.executeQuery();
					if (resultSet.next()) {
						// got token
						String token = resultSet.getString("token");
						String subject = "Activate Speech Marker Account";
						String name = userProfile.getFirstName() == null ? ""
								: userProfile.getFirstName() + " " + userProfile.getLastName() == null ? ""
										: userProfile.getLastName();
						String emailBody = "\nThank you for registering with the University of Notre Dame Speech Marker Initiative.\n"
								+ "\nClick the link below to complete your registration:\n"
								+ Util.getEmailVerificationRoot() + "verifyemail?token=" + token;
						
						System.out.println("email writing done, now sending.....");

						sendEmail(email, subject, emailBody, name);
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
				// "Error in user creation. Please refer server logs for more
				// information";
				e.printStackTrace();
				response.setCode(-1);
				response.setMessage("Error in user creation. Try later.");
			}
		}catch (Exception e) {
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

	private boolean sendEmail(String toAddress, String subject, String messageBody, String name) {
		try {
			InputStream propertiesInputStream = null;
			Properties properties = new Properties();
			propertiesInputStream = getServletContext().getResourceAsStream("/WEB-INF/system.properties");
			properties.load(propertiesInputStream);
			final String emailAccount = properties.getProperty("gmail_account");
			final String emailUser = properties.getProperty("gmail_user");
			//final String emailPassword = "jzgzxxfqklbiisdr";
			final String emailPassword = "dvmpowduhtxnwkjp";
			
			// properties.getProperty("gmail_password");
			
			
			System.out.println("email account "+ emailAccount + ", user:"+ emailUser + ", email pass:" + emailPassword);

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailUser, emailPassword);
					// return new PasswordAuthentication("ndspeechrepo",
					// "mcomlab2017");

				}
			});

			try {
				
				Message message = new MimeMessage(session);

				message.setFrom(new InternetAddress(emailAccount));

				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));

				message.setSubject(subject);
				if (Util.isEmptyString(name.trim())) {
					name = "User";
				}
				String header = "Dear " + name + ",\n";
				String footer = "\n\nRegards,\nMobile Computing Lab,\nUniversity of Notre Dame";
				message.setText(header + messageBody + footer);
				Transport.send(message);

				return true;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Response logout(SpeechSession session) {

		Connection connection = null;
		Response response = new Response();
		try {
			String sessionId = session.getSessionId();
			if (!Util.isEmptyString(sessionId)) {
				try {
					connection = connect();
					connection.createStatement();

					PreparedStatement preparedStatement = connection.prepareStatement(
							"update phr.session_info set state=0, logout_time=now() where session_id=?");
					preparedStatement.setString(1, sessionId);
					preparedStatement.executeUpdate();
					response.setCode(0);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
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

	@Override
	public Response resetPassword(String email) {


		Connection connection = null;
		Response response = new Response();
		try {
			connection = connect();

			PreparedStatement preparedStatement = connection
					.prepareStatement("select email from phr.users where state=1 and email=?");
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String newPass = randomString(12);
				String encryptedNewpass = Util.getMD5String(newPass);
				preparedStatement = connection.prepareStatement("update phr.users set password= ? where email=?");
				preparedStatement.setString(1, encryptedNewpass);
				preparedStatement.setString(2, email);
				preparedStatement.executeUpdate();

				try {
					String subject = "Reset Speech Marker Password";
					String emailBody = "\n\nYour password has been reset, and use new one to login. Please change your password from the profile section.\n"
							+ "\n New Password:		" + newPass;

					sendEmail(email, subject, emailBody, "");

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

	@Override
	public Response changePassword(String email, String oldPassword, String newPassword) {

		Connection connection = null;
		Response response = new Response();
		try {


			System.out.println("email:" + email + ", old pass :" + oldPassword + ", new pass:" + newPassword);

			if (Util.isEmptyString(email) || Util.isEmptyString(oldPassword) || Util.isEmptyString(newPassword)) {
				response.setCode(1);
				response.setMessage("Please try with valid input");
				return response;
			}

			try {
				connection = connect();
				connection.createStatement();

				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from phr.users where email=? and password=?");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, oldPassword);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					preparedStatement = connection.prepareStatement("update phr.users set password= ? where email=?");
					preparedStatement.setString(1, newPassword);
					preparedStatement.setString(2, email);
					preparedStatement.executeUpdate();
					response.setCode(0);

				} else {
					response.setCode(2);
					response.setMessage("Old password is not correct");
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
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

	@Override
	public Boolean checkEmailAvailability(String email) {

		Connection connection = null;
		try {

			System.out.println("Connecting database...");
			connection = connect();
			try {

				// statement = connection.createStatement();
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from phr.users where email=?");
				preparedStatement.setString(1, email);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					return true;

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

		return false;

	}

	@Override
	public UserRole getRole(String email) {

		return new UserRole(true, false, false);
	}

	@Override
	public Response loginToPhr(User user) {


		Connection conn = null;
		Response response = new Response();
		try {
			System.out.println("pass from client:" + user.getPassword());
			conn = connect();
			// connection.createStatement();
			PreparedStatement preparedStatement = conn.prepareStatement("select * from phr.users where email=?");
			preparedStatement.setString(1, user.getEmail());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int state = resultSet.getInt("state");
				if (state == 0) {
					response.setCode(2);
					response.setMessage("Please verify your account before login");
				} else {
					// check password
					if (user.getPassword().equals(resultSet.getString("password"))) {
						String uuid = UUID.randomUUID().toString();
						try {
							preparedStatement = conn.prepareStatement(
									"insert into  phr.session_info (session_id, user_id, login_time) values ( ?,?,now())");
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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ignore) {
				}
		}
		return response;

	}

}
