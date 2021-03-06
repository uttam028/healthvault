package cse.mlab.hvr.server;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cse.mlab.hvr.client.services.ProfileService;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.UserProfile;
import cse.mlab.hvr.shared.Util;
import cse.mlab.hvr.shared.study.PreTestAnswers;
import cse.mlab.hvr.shared.study.PreTestQuestion;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ProfileServiceImpl extends RemoteServiceServlet implements ProfileService {

	static String serverRoot = "";
	static String signupPath = "";
	static String loginPath = "";
	static String profilePath = "";

	private static final String DRIVER = "com.mysql.jdbc.Driver";

	// private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String dbUrl, dbUsername, dbPassword;

	static {
		try {
			Class.forName(DRIVER).newInstance();
			System.out.println("Load DB driver successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public Response profileUpdateRequired(String email) {

		Connection connection = null;
		Response response = new Response();
		try {

			connection = connect();
			try {

				connection.createStatement();
				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from phr.user_profile where email=?");

				preparedStatement.setString(1, email);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					String firstName = resultSet.getString("first_name");
					String lastName = resultSet.getString("last_name");
					String gender = resultSet.getString("gender");
					int birthYear = 0;
					try {
						birthYear = resultSet.getInt("birth_year");
						birthYear = birthYear > 1900 ? birthYear : 0;
					} catch (Exception e) {
						// TODO: handle exception
					}
					if(Util.isEmptyString(firstName) || Util.isEmptyString(lastName) || Util.isEmptyString(gender) || birthYear<=0){
						response.setCode(1);
					}else {
						response.setCode(0);
					}
					
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

		return response;

	}

	@Override
	public UserProfile getProfile(String email) {


		Connection connection = null;
		UserProfile profile = new UserProfile();
		try {

			System.out.println("Connecting to database...");
			connection = connect();
			try {

				connection.createStatement();
				PreparedStatement preparedStatement = connection
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
					profile.setHandedness(resultSet.getString("handedness"));
					profile.setHearing(resultSet.getString("hearing"));
					profile.setVision(resultSet.getString("vision"));
					profile.setSwallowing(resultSet.getString("swallowing"));
					profile.setDentures(resultSet.getString("dentures"));
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

		return profile;

	}

	@Override
	public ArrayList<PreTestQuestion> getProfileInformation(String email) {

		Connection connection = null;
		ArrayList<PreTestQuestion> profileQuestionList = new ArrayList<>();
		try {

			connection = connect();
			try {
				if (Util.isEmptyString(email)) {
					// empty email
				} else {
					String query = "select * from phr.profile_question a left join phr.profile_question_answer b on a.question_id = b.question_id and b.user_id=? and is_active=1 order by order_id";
					
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, email);
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						try {
							
							PreTestQuestion profileQuestion = new PreTestQuestion();
							profileQuestion.setTestId("0");
							String questionId = resultSet.getString("question_id");
							profileQuestion.setQuestionId(questionId);
							String question = resultSet.getString("question");
							profileQuestion.setQuestion(question);
							String type = resultSet.getString("type");
							profileQuestion.setType(type);
							String possibleAnswers = resultSet.getString("possible_answers");
							profileQuestion.setPossibleAnswers(possibleAnswers);
							int displayLevel = resultSet.getInt("display_level");
							profileQuestion.setDisplayLevel(displayLevel);
							int order = resultSet.getInt("order_id");
							profileQuestion.setOrder(order);
							int active_value = resultSet.getInt("is_active");
							if(active_value > 0){
								profileQuestion.setActive(true);
							}else{
								profileQuestion.setActive(false);
							}
							
							int required = resultSet.getInt("is_required");
							if(required > 0){
								profileQuestion.setRequired(true);
							}else {
								profileQuestion.setRequired(false);
							}
							String parentId = resultSet.getString("parent_question_id");
							profileQuestion.setParentId(parentId);
							int hasChild = resultSet.getInt("has_child");
							if(hasChild > 0){
								profileQuestion.setHasChild(true);
							}else {
								profileQuestion.setHasChild(false);
							}
							
							String childVisibleDependentAnswer = resultSet.getString("child_dependent_answer");
							profileQuestion.setChildVisibleDependentAnswer(childVisibleDependentAnswer);
							String defaultAnswer = resultSet.getString("answer");
							profileQuestion.setDefaultAnswer(defaultAnswer);

							profileQuestionList.add(profileQuestion);
						} catch (Exception e) {
							// TODO: handle exception
						}

					}

				}
			} catch (SQLException e) {
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
		return profileQuestionList;

	}

	@Override
	public Response updateProfileInfo(PreTestAnswers preTestAnswers) {

		Connection connection = null;
		Response response = new Response();
		try {
			connection = connect();
			String query = "insert into phr.profile_question_answer values(?,?, now(), ?) on duplicate key update answer=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			for(int i=0;i<preTestAnswers.getAllQuestionId().size();i++){
				preparedStatement.setString(1, preTestAnswers.getUserId());
				preparedStatement.setString(2, preTestAnswers.getAllQuestionId().get(i));
				preparedStatement.setString(3, preTestAnswers.getAllQuestionAnswer().get(i));
				preparedStatement.setString(4, preTestAnswers.getAllQuestionAnswer().get(i));
				preparedStatement.execute();
			}
			response.setCode(0);

		} catch (Exception e) {
			// TODO: handle exception
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

	@Override
	public ArrayList<PreTestQuestion> getPhysicalInformation(String email) {

		Connection connection = null;
		ArrayList<PreTestQuestion> physicalQuestionList = new ArrayList<>();
		try {

			connection = connect();
			try {
				if (Util.isEmptyString(email)) {
					// empty email
				} else {
					String query = "select * from phr.physical_question a left join phr.physical_question_answer b on a.question_id = b.question_id and b.user_id=? and is_active=1 order by order_id";
					
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, email);
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						try {
							
							PreTestQuestion physicalQuestion = new PreTestQuestion();
							physicalQuestion.setTestId("0");
							String questionId = resultSet.getString("question_id");
							physicalQuestion.setQuestionId(questionId);
							String question = resultSet.getString("question");
							physicalQuestion.setQuestion(question);
							String type = resultSet.getString("type");
							physicalQuestion.setType(type);
							String possibleAnswers = resultSet.getString("possible_answers");
							physicalQuestion.setPossibleAnswers(possibleAnswers);
							int displayLevel = resultSet.getInt("display_level");
							physicalQuestion.setDisplayLevel(displayLevel);
							int order = resultSet.getInt("order_id");
							physicalQuestion.setOrder(order);
							int active_value = resultSet.getInt("is_active");
							if(active_value > 0){
								physicalQuestion.setActive(true);
							}else{
								physicalQuestion.setActive(false);
							}
							
							int required = resultSet.getInt("is_required");
							if(required > 0){
								physicalQuestion.setRequired(true);
							}else {
								physicalQuestion.setRequired(false);
							}
							String parentId = resultSet.getString("parent_question_id");
							physicalQuestion.setParentId(parentId);
							int hasChild = resultSet.getInt("has_child");
							if(hasChild > 0){
								physicalQuestion.setHasChild(true);
							}else {
								physicalQuestion.setHasChild(false);
							}
							
							String childVisibleDependentAnswer = resultSet.getString("child_dependent_answer");
							physicalQuestion.setChildVisibleDependentAnswer(childVisibleDependentAnswer);
							String defaultAnswer = resultSet.getString("answer");
							physicalQuestion.setDefaultAnswer(defaultAnswer);

							physicalQuestionList.add(physicalQuestion);
						} catch (Exception e) {
							// TODO: handle exception
						}

					}

				}
			} catch (SQLException e) {
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
		return physicalQuestionList;

	}

	@Override
	public Response updatePhysicalInfo(PreTestAnswers preTestAnswers) {

		Connection connection = null;
		Response response = new Response();
		try {
			connection = connect();
			String query = "insert into phr.physical_question_answer values(?,?, now(), ?) on duplicate key update answer=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			for(int i=0;i<preTestAnswers.getAllQuestionId().size();i++){
				preparedStatement.setString(1, preTestAnswers.getUserId());
				preparedStatement.setString(2, preTestAnswers.getAllQuestionId().get(i));
				preparedStatement.setString(3, preTestAnswers.getAllQuestionAnswer().get(i));
				preparedStatement.setString(4, preTestAnswers.getAllQuestionAnswer().get(i));
				preparedStatement.execute();
			}
			response.setCode(0);

		} catch (Exception e) {
			// TODO: handle exception
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



	@Override
	public Response saveProfile(UserProfile userProfile) {

		Connection connection = null;
		Response response = new Response();
		try {
			System.out.println("Connecting database...");
			connection = connect();
			try {
				connection.createStatement();

				PreparedStatement preparedStatement = connection
						.prepareStatement("update phr.user_profile set first_name=?, last_name=?, gender=?, birth_year=?, primary_language=?, phone_number=?, address=?,"
								+ " handedness=?, hearing=?, vision=?, swallowing=?, dentures=? where email=?");
				preparedStatement.setString(1, userProfile.getFirstName());
				preparedStatement.setString(2, userProfile.getLastName());				
				preparedStatement.setString(3, userProfile.getGender());
				preparedStatement.setString(4, String.valueOf(userProfile.getBirthYear()));
				preparedStatement.setString(5, userProfile.getPrimaryLanguage());
				preparedStatement.setString(6, userProfile.getPhoneNumber());
				preparedStatement.setString(7, userProfile.getAddress());
				preparedStatement.setString(8, userProfile.getHandedness());
				preparedStatement.setString(9, userProfile.getHearing());
				preparedStatement.setString(10, userProfile.getVision());
				preparedStatement.setString(11, userProfile.getSwallowing());
				preparedStatement.setString(12, userProfile.getDentures());

				preparedStatement.setString(13, userProfile.getEmail());
				
				System.out.println("query: " + preparedStatement.toString());
				preparedStatement.execute();

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
