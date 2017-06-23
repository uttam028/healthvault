package com.phr.study;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import com.google.gson.Gson;
import com.phr.util.DatabaseUtil;
import com.phr.util.Response;
import com.phr.util.ServiceUtil;

@Path("/study")
public class StudyServices {

	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;

//	static {
//		System.out.println("loading database driver........study service");
//		DatabaseUtil.loadDriver();
//	}

	@Path("open/{email}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ArrayList<StudyPrefaceModel> getOpenStudies(
			@PathParam("email") String email) {
		ArrayList<StudyPrefaceModel> openStudies = new ArrayList<>();
		try {

			System.out.println("Connecting database...end using function");
			connection = DatabaseUtil.connectToDatabase();
			try {
				if (ServiceUtil.isEmptyString(email)) {
					// empty email
				} else {
					String query = "select * from phr.study where id not in (select study_id from phr.enrollment where user_id=?) and is_active=1";

					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, email);
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						try {
							StudyOverview overview = new StudyOverview();
							String studyId = resultSet.getString("id");
							overview.setId(studyId);
							overview.setName(resultSet.getString("name"));
							overview.setOverview(resultSet
									.getString("short_description"));
							overview.setDescription(resultSet
									.getString("long_description"));
							overview.setConsentFileAvailable(resultSet
									.getInt("is_consent_available") == 1 ? true
									: false);
							String consentFilePath = resultSet
									.getString("consent_file_path");
							if (!ServiceUtil.isEmptyString(consentFilePath)) {
								consentFilePath = ServiceUtil.getStudyRoot()
										+ studyId + "/" + consentFilePath;
							}
							overview.setConsentFileName(consentFilePath);
							overview.setSpeechTestId(resultSet
									.getString("speech_test_id"));
							overview.setComplianceId(resultSet
									.getString("compliance_id"));

							ArrayList<QA> faqs = new ArrayList<>();
							try {
								query = "select question, answer from phr.study_faq where study_id=? and is_active=1 order by sequence_id";
								preparedStatement = connection
										.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								ResultSet faqResultset = preparedStatement
										.executeQuery();
								while (faqResultset.next()) {
									QA temp = new QA(faqResultset.getString(1),
											faqResultset.getString(2));
									faqs.add(temp);
								}

							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}

							ArrayList<HealthStatusQuestion> healthQuestions = new ArrayList<>();
							try {
								query = "select question, hint, is_answer_required from phr.study_health_questions where study_id=? and is_active=1 order by sequence_id";
								preparedStatement = connection
										.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								ResultSet healthQResult = preparedStatement
										.executeQuery();
								while (healthQResult.next()) {
									HealthStatusQuestion question = new HealthStatusQuestion();
									question.setQuestion(healthQResult
											.getString("question"));
									question.setHint(healthQResult
											.getString("hint"));
									question.setAnswerRequired(healthQResult
											.getInt("is_answer_required") == 1 ? true
											: false);
									question.setQuestion_type(healthQResult.getString("question_type"));
									question.setDefault_answer(healthQResult.getString("default_answer"));
									question.setAnswer_options(healthQResult.getString("answer_options"));
									healthQuestions.add(question);
								}

							} catch (Exception e) {
								// TODO: handle exception
							}

							StudyPrefaceModel study = new StudyPrefaceModel(
									overview, faqs, healthQuestions);
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
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException ignore) {
				}
		}
		return openStudies;

	}

	@Path("enrolled/{email}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public ArrayList<MyStudyDataModel> getMyStudies(
			@PathParam("email") String email) {
		ArrayList<MyStudyDataModel> myStudies = new ArrayList<>();
		try {

			System.out.println("Connecting database...end using function");
			connection = DatabaseUtil.connectToDatabase();
			try {
				if (ServiceUtil.isEmptyString(email)) {
					// empty email
				} else {
					String query = "select * from phr.study where id in (select study_id from phr.enrollment where user_id=?) and is_active=1";

					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, email);
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						try {
							StudyOverview overview = new StudyOverview();
							String studyId = resultSet.getString("id");
							overview.setId(studyId);
							overview.setName(resultSet.getString("name"));
							overview.setOverview(resultSet
									.getString("short_description"));
							overview.setDescription(resultSet
									.getString("long_description"));
							overview.setConsentFileAvailable(resultSet
									.getInt("is_consent_available") == 1 ? true
									: false);
							String consentFilePath = resultSet
									.getString("consent_file_path");
							if (!ServiceUtil.isEmptyString(consentFilePath)) {
								consentFilePath = ServiceUtil.getStudyRoot()
										+ studyId + "/" + consentFilePath;
							}
							overview.setConsentFileName(consentFilePath);
							overview.setSpeechTestId(resultSet
									.getString("speech_test_id"));
							String complianceId = resultSet
									.getString("compliance_id");
							overview.setComplianceId(complianceId);

							ArrayList<QA> faqs = new ArrayList<>();
							try {
								query = "select question, answer from phr.study_faq where study_id=? and is_active=1 order by sequence_id";
								preparedStatement = connection
										.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								ResultSet faqResultset = preparedStatement
										.executeQuery();
								while (faqResultset.next()) {
									QA temp = new QA(faqResultset.getString(1),
											faqResultset.getString(2));
									faqs.add(temp);
								}

							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}

							Compliance compliance = new Compliance();
							compliance.setId(complianceId);
							try {
								if (!ServiceUtil.isEmptyString(complianceId)) {
									query = "select * from phr.study_compliance where id=?";
									preparedStatement = connection.prepareStatement(query);
									preparedStatement.setString(1, complianceId);
									ResultSet complianceResult = preparedStatement.executeQuery();
									if (complianceResult.next()) {
										compliance.setTimeFrame(complianceResult.getString("time_slot_in_days"));
										compliance.setNumberOfParticipationInTimeFrame(complianceResult.getInt("number_of_participation_in_a_slot"));
										compliance.setPercentageToMaintain(complianceResult.getInt("minimum_percentage"));
										compliance.setMessage(complianceResult.getString("message"));
									}
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

							String enrollmentDate = "";
							try {
								query = "select date(enrollment_date) from phr.enrollment where state=3 and study_id=? and user_id=?";
								preparedStatement = connection
										.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								preparedStatement.setString(2, email);
								ResultSet enrollResult = preparedStatement
										.executeQuery();
								if (enrollResult.next()) {
									enrollmentDate = enrollResult.getString(1);
								}

							} catch (Exception e) {
								// TODO: handle exception
							}

							String lastParticipationDate = "";
							try {
								query = "select date(end_time) from phr.participation where state=3 and study_id=? and user_id=?";
								preparedStatement = connection
										.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								preparedStatement.setString(2, email);
								ResultSet lastPartResult = preparedStatement
										.executeQuery();
								if (lastPartResult.next()) {
									lastParticipationDate = lastPartResult.getString(1);
								}

							} catch (Exception e) {
								// TODO: handle exception
							}

							int totalParticipationCount = -1;
							try {
								query = "select count(*) from phr.participation where state=3 and study_id=? and user_id=?";
								preparedStatement = connection
										.prepareStatement(query);
								preparedStatement.setString(1, studyId);
								preparedStatement.setString(2, email);
								ResultSet totalParticipationResult = preparedStatement
										.executeQuery();
								if (totalParticipationResult.next()) {
									totalParticipationCount = totalParticipationResult
											.getInt(1);
								}

							} catch (Exception e) {
								// TODO: handle exception
							}
							MyStudyDataModel myStudy = new MyStudyDataModel(
									overview, compliance, faqs, enrollmentDate,
									lastParticipationDate,
									totalParticipationCount);
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

	@Path("metadata/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public SpeechTest getSpeechTest(@PathParam("id") String testId) {
		SpeechTest speechTest = new SpeechTest();
		try {
			System.out.println("Connecting database...get speech data : "
					+ testId);
			connection = DatabaseUtil.connectToDatabase();
			speechTest.setTestId(testId);

			ArrayList<SubTest> subtestList = new ArrayList<>();
			String query = "select * from phr.speech_subtest where is_active=1 and test_id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, testId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				try {
					SubTest subTest = new SubTest();
					int subtestId = resultSet.getInt("subtest_id");
					subTest.setSubtestId(subtestId);
					subTest.setName(resultSet.getString("name"));
					subTest.setActive(true);
					subTest.setCommonInstructionText(resultSet
							.getString("common_instruction_text"));
					String instructionAudioPath = resultSet
							.getString("common_instruction_audio_file_path");
					if (!ServiceUtil.isEmptyString(instructionAudioPath)) {
						instructionAudioPath = ServiceUtil.getTestRoot()
								+ testId + "/" + subtestId + "/"
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
							int fragmentId = fragResultSet
									.getInt("fragment_id");
							fragment.setFragmentId(fragmentId);
							fragment.setText(fragResultSet.getString("text"));
							String imagePath = fragResultSet
									.getString("image_file_path");
							if (!ServiceUtil.isEmptyString(imagePath)) {
								imagePath = ServiceUtil.getTestRoot() + testId
										+ "/" + subtestId + "/" + fragmentId
										+ "/" + imagePath;
							}
							fragment.setImageFileName(imagePath);

							String audioPath = fragResultSet
									.getString("audio_file_path");
							if (!ServiceUtil.isEmptyString(audioPath)) {
								audioPath = ServiceUtil.getTestRoot() + testId
										+ "/" + subtestId + "/" + fragmentId
										+ "/" + audioPath;
							}
							fragment.setAudioFileName(audioPath);
							fragment.setTimerAvailable(fragResultSet
									.getInt("is_timer_enable") == 1 ? true
									: false);
							fragment.setDurationOfTimer(fragResultSet
									.getInt("time_of_timer"));
							fragment.setNextButtonAvailable(fragResultSet
									.getInt("is_next_button_enable") == 1 ? true
									: false);
							fragment.setDurationToShowNextButton(fragResultSet
									.getInt("time_to_show_next_button"));
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
		Gson gson = new Gson();
		String obj = gson.toJson(speechTest);
		System.out.println("Object : " + obj);
		return speechTest;
	}

	@POST
	@Path("enrollment/create")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response enroll(JAXBElement<Enrollment> jaxbElement) {
		Response response = new Response();
		try {
			Enrollment enrollment = jaxbElement.getValue();
			System.out.println("study id : " + enrollment.getStudyId()
					+ ", user id : " + enrollment.getUserId());
			connection = DatabaseUtil.connectToDatabase();

			String query = "insert into phr.enrollment (study_id, user_id, enrollment_date, state) values (?,?,now(),3)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,
					String.valueOf(enrollment.getStudyId()));
			preparedStatement.setString(2, enrollment.getUserId());
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

	@Path("participation/start")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response addParticipation(JAXBElement<Participation> jaxbElement) {
		Participation participation = jaxbElement.getValue();
		Response response = new Response();
		try {
			connection = DatabaseUtil.connectToDatabase();

			String query = "insert into phr.participation (study_id, user_id, start_time, state) values (?,?,now(),0)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,
					String.valueOf(participation.getStudyId()));
			preparedStatement.setString(2, participation.getUserId());
			preparedStatement.execute();
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

	@Path("participation/end")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response endParticipation(JAXBElement<Participation> jaxbElement) {
		Participation participation = jaxbElement.getValue();
		Response response = new Response();
		try {
			connection = DatabaseUtil.connectToDatabase();

			String query = "select * from phr.participation where study_id=? and user_id=? and state<3 order by participation_id desc";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,
					String.valueOf(participation.getStudyId()));
			preparedStatement.setString(2, participation.getUserId());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int participationId = resultSet.getInt("participation_id");
				System.out.println("Participation id :"+ participationId);
				query = "update phr.participation set end_time=now(), state=3 where participation_id="
						+ participationId;
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.execute();
				response.setCode(0);
			}

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

}
