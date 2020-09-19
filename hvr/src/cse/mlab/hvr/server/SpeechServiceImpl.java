package cse.mlab.hvr.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cse.mlab.hvr.client.services.SpeechService;
import cse.mlab.hvr.shared.QA;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.Util;
import cse.mlab.hvr.shared.study.Compliance;
import cse.mlab.hvr.shared.study.HealthStatusQuestion;
import cse.mlab.hvr.shared.study.MyStudyDataModel;
import cse.mlab.hvr.shared.study.PreTestAnswers;
import cse.mlab.hvr.shared.study.PreTestQuestion;
import cse.mlab.hvr.shared.study.Recording;
import cse.mlab.hvr.shared.study.SpeechTest;
import cse.mlab.hvr.shared.study.StudyOverview;
import cse.mlab.hvr.shared.study.StudyPrefaceModel;
import cse.mlab.hvr.shared.study.SubTest;
import cse.mlab.hvr.shared.study.TestFragment;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SpeechServiceImpl extends RemoteServiceServlet implements SpeechService {

	static String serverRoot = "";
	static String signupPath = "";
	static String loginPath = "";
	static String profilePath = "";

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	private static final String upload_location = "/project/speechmarker/speechdata";
	private static final String storage_location = "/project/speechmarker/speechdata/userdata/";


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

			// gmailAccount = properties.getProperty("gmail_account");
			// gmailUser = properties.getProperty("gmail_user");
			// gmailPass = properties.getProperty("gmail_password");
			// System.out.println("Gmail account:"+ gmailAccount + ", user:"+
			// gmailUser + ", gmail pass:"+ gmailPass);

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
	public ArrayList<StudyPrefaceModel> getOpenStudies(String email) {

		Connection conn = null;
		ArrayList<StudyPrefaceModel> openStudies = new ArrayList<>();
		try {

			System.out.println("Connecting database...end using function");
			conn = connect();
			try {
				if (Util.isEmptyString(email)) {
					// empty email
				} else {
					String query = "select * from phr.study where id not in (select study_id from phr.enrollment where user_id=?) and is_active=1";

					PreparedStatement preparedStatement = conn.prepareStatement(query);
					preparedStatement.setString(1, email);
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						try {
							StudyOverview overview = new StudyOverview();
							String studyId = resultSet.getString("id");
							overview.setId(studyId);
							overview.setName(resultSet.getString("name"));
							overview.setOverview(resultSet.getString("short_description"));
							overview.setDescription(resultSet.getString("long_description"));
							overview.setConsentFileAvailable(
									resultSet.getInt("is_consent_available") == 1 ? true : false);
							String consentFilePath = resultSet.getString("consent_file_path");
							if (!Util.isEmptyString(consentFilePath)) {
								consentFilePath = Util.getStudyRoot() + studyId + "/" + consentFilePath;
							}
							overview.setConsentFileName(consentFilePath);
							overview.setSpeechTestId(resultSet.getString("speech_test_id"));
							overview.setComplianceId(resultSet.getString("compliance_id"));

							ArrayList<QA> faqs = new ArrayList<>();
							try {
								query = "select question, answer from phr.study_faq where study_id=? and is_active=1 order by sequence_id";
								preparedStatement = conn.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								ResultSet faqResultset = preparedStatement.executeQuery();
								while (faqResultset.next()) {
									QA temp = new QA(faqResultset.getString(1), faqResultset.getString(2));
									faqs.add(temp);
								}

							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}

							ArrayList<HealthStatusQuestion> healthQuestions = new ArrayList<>();
							try {
								query = "select question, hint, is_answer_required from phr.study_health_questions where study_id=? and is_active=1 order by sequence_id";
								preparedStatement = conn.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								ResultSet healthQResult = preparedStatement.executeQuery();
								while (healthQResult.next()) {
									HealthStatusQuestion question = new HealthStatusQuestion();
									question.setQuestion(healthQResult.getString("question"));
									question.setHint(healthQResult.getString("hint"));
									question.setAnswerRequired(
											healthQResult.getInt("is_answer_required") == 1 ? true : false);
									question.setQuestion_type(healthQResult.getString("question_type"));
									question.setDefault_answer(healthQResult.getString("default_answer"));
									question.setAnswer_options(healthQResult.getString("answer_options"));
									healthQuestions.add(question);
								}

							} catch (Exception e) {
								// TODO: handle exception
							}

							StudyPrefaceModel study = new StudyPrefaceModel(overview, faqs, healthQuestions);
							openStudies.add(study);

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
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ignore) {
				}
		}
		return openStudies;

	}

	@Override
	public ArrayList<MyStudyDataModel> getMyStudies(String email) {

		Connection connection = null;
		ArrayList<MyStudyDataModel> myStudies = new ArrayList<>();
		try {

			System.out.println("Connecting database...end using function");
			connection = connect();
			try {
				if (Util.isEmptyString(email)) {
					// empty email
				} else {
					String query = "select * from phr.study where id in (select study_id from phr.enrollment where user_id=?) and is_active=1";

					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, email);
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						try {
							StudyOverview overview = new StudyOverview();
							String studyId = resultSet.getString("id");
							overview.setId(studyId);
							overview.setName(resultSet.getString("name"));
							overview.setOverview(resultSet.getString("short_description"));
							overview.setDescription(resultSet.getString("long_description"));
							overview.setConsentFileAvailable(
									resultSet.getInt("is_consent_available") == 1 ? true : false);
							String consentFilePath = resultSet.getString("consent_file_path");
							if (!Util.isEmptyString(consentFilePath)) {
								consentFilePath = Util.getStudyRoot() + studyId + "/" + consentFilePath;
							}
							overview.setConsentFileName(consentFilePath);
							overview.setSpeechTestId(resultSet.getString("speech_test_id"));
							String complianceId = resultSet.getString("compliance_id");
							overview.setComplianceId(complianceId);

							ArrayList<QA> faqs = new ArrayList<>();
							try {
								query = "select question, answer from phr.study_faq where study_id=? and is_active=1 order by sequence_id";
								preparedStatement = connection.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								ResultSet faqResultset = preparedStatement.executeQuery();
								while (faqResultset.next()) {
									QA temp = new QA(faqResultset.getString(1), faqResultset.getString(2));
									faqs.add(temp);
								}

							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}

							Compliance compliance = new Compliance();
							compliance.setId(complianceId);
							try {
								if (!Util.isEmptyString(complianceId)) {
									query = "select * from phr.study_compliance where id=?";
									preparedStatement = connection.prepareStatement(query);
									preparedStatement.setString(1, complianceId);
									ResultSet complianceResult = preparedStatement.executeQuery();
									if (complianceResult.next()) {
										compliance.setTimeFrame(complianceResult.getString("time_slot_in_days"));
										compliance.setNumberOfParticipationInTimeFrame(
												complianceResult.getInt("number_of_participation_in_a_slot"));
										compliance
												.setPercentageToMaintain(complianceResult.getInt("minimum_percentage"));
										compliance.setMessage(complianceResult.getString("message"));
									}
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

							String enrollmentDate = "";
							try {
								query = "select date(enrollment_date) from phr.enrollment where state=3 and study_id=? and user_id=?";
								preparedStatement = connection.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								preparedStatement.setString(2, email);
								ResultSet enrollResult = preparedStatement.executeQuery();
								if (enrollResult.next()) {
									enrollmentDate = enrollResult.getString(1);
								}

							} catch (Exception e) {
								// TODO: handle exception
							}

							String lastParticipationDate = "";
							try {
								query = "select date(end_time) from phr.participation where state=3 and study_id=? and user_id=?";
								preparedStatement = connection.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								preparedStatement.setString(2, email);
								ResultSet lastPartResult = preparedStatement.executeQuery();
								if (lastPartResult.next()) {
									lastParticipationDate = lastPartResult.getString(1);
								}

							} catch (Exception e) {
								// TODO: handle exception
							}

							int totalParticipationCount = -1;
							try {
								query = "select count(*) from phr.participation where state=3 and study_id=? and user_id=?";
								preparedStatement = connection.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								preparedStatement.setString(2, email);
								ResultSet totalParticipationResult = preparedStatement.executeQuery();
								if (totalParticipationResult.next()) {
									totalParticipationCount = totalParticipationResult.getInt(1);
								}

							} catch (Exception e) {
								// TODO: handle exception
							}
							MyStudyDataModel myStudy = new MyStudyDataModel(overview, compliance, faqs, enrollmentDate,
									lastParticipationDate, totalParticipationCount);
							myStudies.add(myStudy);

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
		return myStudies;

	}

	@Override
	public ArrayList<PreTestQuestion> getPreTestQuestions(String testId) {
		Connection connection = null;
		ArrayList<PreTestQuestion> pretestQuestionList = new ArrayList<>();
		try {

			connection = connect();
			try {
				if (Util.isEmptyString(testId)) {
					// empty email
				} else {
					String query = "select * from phr.pretest_question where test_id=? and is_active=1 order by order_id";

					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, testId);
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						try {

							PreTestQuestion pretestQuestion = new PreTestQuestion();
							pretestQuestion.setTestId(testId);
							String questionId = resultSet.getString("question_id");
							pretestQuestion.setQuestionId(questionId);
							String question = resultSet.getString("question");
							pretestQuestion.setQuestion(question);
							String type = resultSet.getString("type");
							pretestQuestion.setType(type);
							String possibleAnswers = resultSet.getString("possible_answers");
							pretestQuestion.setPossibleAnswers(possibleAnswers);
							int displayLevel = resultSet.getInt("display_level");
							pretestQuestion.setDisplayLevel(displayLevel);
							int order = resultSet.getInt("order_id");
							pretestQuestion.setOrder(order);
							int active_value = resultSet.getInt("is_active");
							if (active_value > 0) {
								pretestQuestion.setActive(true);
							} else {
								pretestQuestion.setActive(false);
							}

							int required = resultSet.getInt("is_required");
							if (required > 0) {
								pretestQuestion.setRequired(true);
							} else {
								pretestQuestion.setRequired(false);
							}
							String parentId = resultSet.getString("parent_question_id");
							pretestQuestion.setParentId(parentId);
							int hasChild = resultSet.getInt("has_child");
							if (hasChild > 0) {
								pretestQuestion.setHasChild(true);
							} else {
								pretestQuestion.setHasChild(false);
							}

							String childVisibleDependentAnswer = resultSet.getString("child_dependent_answer");
							pretestQuestion.setChildVisibleDependentAnswer(childVisibleDependentAnswer);
							String defaultAnswer = resultSet.getString("default_answer");
							pretestQuestion.setDefaultAnswer(defaultAnswer);

							pretestQuestionList.add(pretestQuestion);
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
		return pretestQuestionList;

	}

	@Override
	public Response submitPreTestAnswer(PreTestAnswers preTestAnswers) {

		Connection connection = null;
		Response response = new Response();
		try {
			System.out.println("submit pretest answer, " + preTestAnswers.getParticipationId());
			connection = connect();
			String query = "insert into phr.pretest_question_answer values(?,?, now(), ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			for (int i = 0; i < preTestAnswers.getAllQuestionId().size(); i++) {
				preparedStatement.setString(1, preTestAnswers.getParticipationId());
				preparedStatement.setString(2, preTestAnswers.getAllQuestionId().get(i));
				preparedStatement.setString(3, preTestAnswers.getAllQuestionAnswer().get(i));
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
	public SpeechTest getSpeechTestMetadata(String testId) {
		Connection connection = null;
		SpeechTest speechTest = new SpeechTest();
		try {
			System.out.println("Connecting database...get speech data : " + testId);
			connection = connect();
			speechTest.setTestId(testId);

			ArrayList<SubTest> subtestList = new ArrayList<>();
			String query = "select * from phr.speech_subtest where is_active=1 and test_id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, testId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				try {
					SubTest subTest = new SubTest();
					int subtestId = resultSet.getInt("subtest_id");
					subTest.setSubtestId(subtestId);
					subTest.setName(resultSet.getString("name"));
					subTest.setActive(true);
					subTest.setCommonInstructionText(resultSet.getString("common_instruction_text"));
					String instructionAudioPath = resultSet.getString("common_instruction_audio_file_path");
					if (!Util.isEmptyString(instructionAudioPath)) {
						instructionAudioPath = Util.getTestRoot() + testId + "/" + subtestId + "/"
								+ instructionAudioPath;
					}
					subTest.setCommonInstrcutionAudioFileName(instructionAudioPath);

					ArrayList<TestFragment> fragmentList = new ArrayList<>();
					query = "select * from phr.speech_fragment where test_id=? and subtest_id=?";
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, testId);
					preparedStatement.setString(2, String.valueOf(subtestId));
					ResultSet fragResultSet = preparedStatement.executeQuery();
					while (fragResultSet.next()) {
						try {
							TestFragment fragment = new TestFragment();
							int fragmentId = fragResultSet.getInt("fragment_id");
							fragment.setFragmentId(fragmentId);
							fragment.setText(fragResultSet.getString("text"));
							String imagePath = fragResultSet.getString("image_file_path");
							if (!Util.isEmptyString(imagePath)) {
								imagePath = Util.getTestRoot() + testId + "/" + subtestId + "/" + fragmentId + "/"
										+ imagePath;
							}
							fragment.setImageFileName(imagePath);

							String audioPath = fragResultSet.getString("audio_file_path");
							if (!Util.isEmptyString(audioPath)) {
								audioPath = Util.getTestRoot() + testId + "/" + subtestId + "/" + fragmentId + "/"
										+ audioPath;
							}
							fragment.setAudioFileName(audioPath);
							fragment.setTimerAvailable(fragResultSet.getInt("is_timer_enable") == 1 ? true : false);
							fragment.setDurationOfTimer(fragResultSet.getInt("time_of_timer"));
							fragment.setNextButtonAvailable(
									fragResultSet.getInt("is_next_button_enable") == 1 ? true : false);
							fragment.setDurationToShowNextButton(fragResultSet.getInt("time_to_show_next_button"));
							fragmentList.add(fragment);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					subTest.setSubtestFragments(fragmentList);
					subtestList.add(subTest);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				speechTest.setSubTests(subtestList);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		} finally {
			System.out.println("Closing the connection.");
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}
		}
		// Gson gson = new Gson();
		// String obj = gson.toJson(speechTest);
		// System.out.println("Object : " + obj);
		return speechTest;

	}

	@Override
	public Response enrollToStudy(String studyId, String email) {

		Connection connection = null;
		Response response = new Response();
		try {
			connection = connect();

			String query = "insert into phr.enrollment (study_id, user_id, enrollment_date, state) values (?,?,now(),3)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, studyId);
			preparedStatement.setString(2, email);
			preparedStatement.execute();
			response.setCode(0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.setMessage("Service not available, please try later");
			e.printStackTrace();
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
	public Response startParticipation(String studyId, String email) {

		Connection connection = null;
		Response response = new Response();
		try {
			connection = connect();

			String query = "insert into phr.participation (study_id, user_id, start_time, state) values (?,?,now(),0)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, studyId);
			preparedStatement.setString(2, email);
			preparedStatement.execute();

			query = "select last_insert_id() as id";
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				response.setMessage(resultSet.getString("id"));
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
	public Response endParticipation(String studyId, String email, String participationId) {

		Connection connection = null;
		Response response = new Response();
		try {
			connection = connect();

			int id = Integer.parseInt(participationId);
			System.out.println("Participation id :" + participationId);
			String query = "update phr.participation set end_time=now(), state=3 where participation_id="
					+ id;
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
			response.setCode(0);
		} catch (Exception e) {
			// TODO: handle exception
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
	public Response relocateSoundFile(Recording recording) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Response response = new Response();
		try {
			int participationId = recording.getParticipationId();
			String userId = recording.getUserId();
			String studyId = recording.getStudyId();
			System.out.println("partid:"+ participationId + ", user id:"+ userId + ", study id:"+ studyId + ", subtest:" + recording.getSubtestId() + "file :"+ recording.getFileIdentifier());
			try {
				connection = connect();

				
				if(participationId<=0){
					if(!Util.isEmptyString(userId) && !Util.isEmptyString(studyId)){
						//get userid from participation
						String query = "select max(participation_id) from phr.participation where user_id=? and study_id=?";
						preparedStatement = connection.prepareStatement(query);
						preparedStatement.setString(1, userId);
						preparedStatement.setString(2, studyId);
						
						ResultSet resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							participationId = resultSet.getInt(1);
						}else {
							return response;
						}
						
					}else {
						return response;
					}
				}else {
					if(Util.isEmptyString(userId)){
						String query = "select user_id from phr.participation where participation_id=" + participationId;
						preparedStatement = connection.prepareStatement(query);
						ResultSet resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							userId = resultSet.getString(1);
						}else {
							return response;
						}
						
					}
				}
				
				if(Util.isEmptyString(recording.getFileIdentifier())){
					return response;
				}
				
				if(recording.getSubtestId()<=0){
					return response;
				}
				
				String query = "insert into phr.speech_recording (participation_id, subtest_id, start_time, end_time, upload_time, file_identifier, retake_counter, split_fragment)"
						+ " values (?, ?, ?, ?, now(), ?, ?, ?)";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1,  String.valueOf(participationId));
				preparedStatement.setString(2, String.valueOf(recording.getSubtestId()));
				preparedStatement.setString(3, validateTime(recording.getStartTime())? recording.getStartTime():null);
				preparedStatement.setString(4, validateTime(recording.getEndTime())? recording.getEndTime(): null);
				preparedStatement.setString(5, recording.getFileIdentifier());
				preparedStatement.setInt(6, recording.getRetakeCounter());
				preparedStatement.setString(7, recording.getSplitString());
				
				preparedStatement.execute();
				response.setCode(0);
				
				String directory = storage_location + userId + "/";
				System.out.println("storage location:"+ directory);
				if(makeSurePathExist(directory)){
					String currentPath = upload_location + recording.getFileIdentifier() + ".wav";
					String newPath = directory + recording.getFileIdentifier() + ".wav";
					System.out.println("cur path:"+ currentPath + ", new path:"+ newPath);
					FileUtils.moveFile(new File(currentPath), new File(newPath));
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
		}
		return response;

	}
	
	// save uploaded file to a defined location on the server
	private void saveFile(InputStream _inputStream, String _location) {

		try {
			System.out.println("File write path:" + _location);
			OutputStream _outputStream = new FileOutputStream(new File(_location));
			int read = 0;
			byte[] bytes = new byte[1024];

			_outputStream = new FileOutputStream(new File(_location));
			while ((read = _inputStream.read(bytes)) != -1) {
				_outputStream.write(bytes, 0, read);
			}
			_outputStream.flush();
			_outputStream.close();
			System.out.println("File writing completed");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private double getFileSize(String filePath){
		try {
			File file =new File(filePath);
			if(file.exists()){

				double bytes = file.length();
				double kilobytes = (bytes / 1024);
				double megabytes = (kilobytes / 1024);
				return megabytes;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return -1;
	}
	
	private String getFileExtension(String filePath){
		try {
			String extension = FilenameUtils.getExtension(filePath);
			System.out.println("file extension : "+ extension);
			return extension;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	
	private String getMIMEType(String filePath){
		try {
			File file = new File(filePath);
			if(file.exists()){
				return URLConnection.guessContentTypeFromName(file.getName());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";

	}
	
	private boolean deleteFile(String filePath){
		try {
			File file = new File(filePath);
			return file.delete();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	private boolean validateTime(String time){
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sf.parse(time);
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	private boolean directoryExist(String directory){
		File file = new File(directory);
		if(file.exists() && file.isDirectory()){
			return true;
		}else {
			return false;
		}
		
	}
	
	private boolean makeSurePathExist(String directory){
		if(directoryExist(directory)){
			System.out.println("directory exist : "+ directory);
			return true;
		}else {
			System.out.println("directory does not exist : "+ directory);
			File file = new File(directory);
			boolean status = file.mkdirs();
			System.out.println("directory creation result : "+ status);
			return directoryExist(directory);
		}
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
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
