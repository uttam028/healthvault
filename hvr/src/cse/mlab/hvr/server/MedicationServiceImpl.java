package cse.mlab.hvr.server;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.codec.binary.Base64;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cse.mlab.hvr.client.services.MedicationService;
import cse.mlab.hvr.shared.Diagnosis;
import cse.mlab.hvr.shared.DiagnosisList;
import cse.mlab.hvr.shared.Medication;
import cse.mlab.hvr.shared.MedicationList;
import cse.mlab.hvr.shared.Response;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MedicationServiceImpl extends RemoteServiceServlet implements MedicationService {

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
	public Response saveMedication(MedicationList medicationList) {


		Connection connection = null;
		Response response = new Response();
		try {


			System.out.println("Connecting database...");
			connection = connect();
			try {
//				String query = "insert into phr.medication_history (user_email, creation_date, modification_date, name, "
//						+ "strength, strength_unit, dosage, dosage_unit, consume_process, consume_frequency, reason, start_date, end_date, "
//						+ "is_prescribed, prescribed_by, prescribed_date, instruction, prescription_quantity, note) values "
//						+ "(?, curdate(), curdate(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String query = "insert into phr.medication_history (user_email, creation_date, modification_date, name, "
						+ "dosage, frequency, reason, start_date, end_date) values "
						+ "(?, curdate(), curdate(),?,?,?,?,?,?)";

				PreparedStatement preparedStatement = connection.prepareStatement(query);
				
				
				String query1 = "select last_insert_id() as id";
				PreparedStatement preparedStatement1 = connection.prepareStatement(query1);

				
				String weirdSeparator = "_|_";
				
				
				
				String ids = "";
				
				for(int i=0;i<medicationList.getMedicationList().size();i++){
					
					Medication medication = medicationList.getMedicationList().get(i);
					
					if (medication.getEmail() != null) {
						preparedStatement.setString(1, medication.getEmail());
					}
					if (medication.getName() != null) {
						preparedStatement.setString(2, medication.getName());
					}
					if (medication.getDosage() != null) {
						preparedStatement.setString(3, medication.getDosage());
					}
					if (medication.getFrequency() != null) {
						preparedStatement.setString(4, medication.getFrequency());
					}
					if (medication.getReason() != null) {
						preparedStatement.setString(5, medication.getReason());
					}
					if (medication.getStartDate() != null) {
						preparedStatement.setString(6, medication.getStartDate());
					}
					if (medication.getEndDate() != null) {
						preparedStatement.setString(7, medication.getEndDate());
					}

					System.out.println(preparedStatement.toString());
					preparedStatement.execute();

					ResultSet resultSet = preparedStatement1.executeQuery();
					if (resultSet.next()) {
						ids = ids + weirdSeparator + resultSet.getString("id");
					}
					
				}
				

				
				response.setCode(0);
				response.setMessage(ids.substring(3));
				// } catch (MySQLIntegrityConstraintViolationException e){
				// success = "The user " + userProfile.getEmail() +
				// " already exist in the system." ;
			} catch (SQLException e) {
				response.setMessage("Error in creation medication history");
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
	public Response updateMedication(Medication medication) {

		Connection connection = null;
		Response response = new Response();
		try {
			System.out.println("Connecting database...");
			connection = connect();
			try {
				long medicId = medication.getId();
//				String query = "update phr.medication_history set name=?, modification_date=curdate(), "
//						+ "strength=?, strength_unit=?, dosage=?, dosage_unit=?, consume_process=?, consume_frequency=?, reason=?, start_date=?, end_date=?, "
//						+ "is_prescribed=?, prescribed_by=?, prescribed_date=?, instruction=?, prescription_quantity=?, note=? where medication_id=" + medicId;
				String query = "update phr.medication_history set name=?, modification_date=curdate(), dosage=?, frequency=?, "
				+ "reason=?, start_date=?, end_date=? where medication_id=" + medicId;

				PreparedStatement preparedStatement = connection.prepareStatement(query);

				System.out.println(medication.toString());
				if (medication.getName() != null) {
					preparedStatement.setString(1, medication.getName());
				}
				if (medication.getDosage() != null) {
					preparedStatement.setString(2, medication.getDosage());
				}
				if (medication.getFrequency() != null) {
					preparedStatement.setString(3, medication.getFrequency());
				}
				if (medication.getReason() != null) {
					preparedStatement.setString(4, medication.getReason());
				}
				if (medication.getStartDate() != null) {
					preparedStatement.setString(5, medication.getStartDate());
				}
				if (medication.getEndDate() != null) {
					preparedStatement.setString(6, medication.getEndDate());
				}

				System.out.println(preparedStatement.toString());
				preparedStatement.execute();

				response.setCode(0);

			} catch (SQLException e) {
				response.setMessage("Error in creation medication history");
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
	public MedicationList getMedicationsList(String email) {

		Connection connection = null;
		MedicationList medicationList = new MedicationList();
		try {

			System.out.println("Connecting to database...");
			connection = connect();
			try {

				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from phr.medication_history where user_email=? order by medication_id desc");

				preparedStatement.setString(1, email);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Medication temp = new Medication();
					temp.setId(resultSet.getLong("medication_id"));
					temp.setEmail(resultSet.getString("user_email"));
					temp.setName(resultSet.getString("name"));
					temp.setDosage(resultSet.getString("dosage"));
					temp.setFrequency(resultSet.getString("frequency"));
					temp.setReason(resultSet.getString("reason"));
					temp.setStartDate(resultSet.getString("start_date"));
					temp.setEndDate(resultSet.getString("end_date"));

					medicationList.getMedicationList().add(temp);
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

		return medicationList;

	}

	@Override
	public Response deleteMedications(String email, String list) {

		Connection connection = null;
		Response response = new Response(0, "");
		System.out.println("list:"+ list);
		try {
			String listAfterDecode = new String(Base64.decodeBase64(list));
			String[] medList = listAfterDecode.trim().split("\\|");
			connection = connect();
			for (String medId : medList) {
				try {
					PreparedStatement preparedStatement = connection
							.prepareStatement("delete from phr.medication_history where user_email=? and medication_id= ?");
					preparedStatement.setString(1, email);
					preparedStatement.setInt(2, Integer.parseInt(medId));
					int deletedRows = preparedStatement.executeUpdate();
					System.out.println("response from delete : "+ deletedRows);
					if (deletedRows == 0) {
						response.setCode(-1);
					}

				} catch(SQLException e){
					System.out.println("sqlException in individual delete " + e.getMessage());
				}catch (Exception e) {
					System.out.println("Exception in individual delete " + e.getMessage());
					// TODO: handle exception
				}
			}

		} catch (Exception ex) {
			System.out.println("Exception in individual delete " + ex.getMessage());
			response.setCode(-1);
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
	public Response stopUsingMedication(long id, String endDate) {
//		long start = Calendar.getInstance().getTimeInMillis();
//
//		ClientConfig config = new DefaultClientConfig();
//		Client client = Client.create(config);
//		String url = serverRoot + "/medication/endusing/" + id + "/" + endDate;
//		WebResource service = client.resource(url);
//
//		String result = service.accept(MediaType.APPLICATION_JSON).get(String.class);
//		Response response = new Gson().fromJson(result, Response.class);
//
//		System.out.println("end using response:" + response.getCode());
//		long end = Calendar.getInstance().getTimeInMillis();
//		System.out.println("time diff delete medic list: " + (end - start));
//
//		return response;
		Connection connection = null;
		Response response = new Response();
		try {

			// setup the connection with the DB.
			// connect =
			// DriverManager.getConnection("jdbc:mysql://localhost/PHR?"
			// + "user=hasini&password=admin123");

			System.out.println("Connecting database...end using function");
			connection = connect();
			try {
				long medicId = id;
				String query = "update phr.medication_history set modification_date=curdate(), "
						+ "end_date=? where medication_id=" + medicId;

				PreparedStatement preparedStatement = connection.prepareStatement(query);

				if (endDate != null) {
					preparedStatement.setString(1, endDate);
				}

				System.out.println(preparedStatement.toString());
				preparedStatement.execute();

				response.setCode(0);
				// } catch (MySQLIntegrityConstraintViolationException e){
				// success = "The user " + userProfile.getEmail() +
				// " already exist in the system." ;
			} catch (SQLException e) {
				response.setMessage("Error in creation medication history");
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
	public Response saveDiagnosis(Diagnosis diagnosis) {
//		System.out.println("dianosis: " + diagnosis.toString());
//		long start = Calendar.getInstance().getTimeInMillis();
//		ClientConfig config = new DefaultClientConfig();
//		Client client = Client.create(config);
//		// WebResource service = client.resource(serverRoot + signupPath);
//		String url = serverRoot + "/diagnosis/";
//		WebResource service = client.resource(url);
//		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML).put(ClientResponse.class, diagnosis);
//		Response saveDiagResult = nameResource.getEntity(Response.class);
//
//		System.out.println("response code:" + saveDiagResult.getCode() + "message:" + saveDiagResult.getMessage());
//		long end = Calendar.getInstance().getTimeInMillis();
//		System.out.println("time diff save diagnosis call: " + (end - start));
//		return saveDiagResult;
		Connection connection = null;
		Response response = new Response();
		try {
			System.out.println("diagnosis:" + diagnosis.toString());
			connection = connect();
			try {
				String query = "insert into phr.diagnosis_history (user_email, creation_date, modification_date, neuro_condition, subcategory,"
						+ "diagnosis_date, note) values "
						+ "(?, curdate(), curdate(),?,?,?,?)";

				PreparedStatement preparedStatement = connection.prepareStatement(query);

				System.out.println(diagnosis.toString());
				if (diagnosis.getEmail() != null) {
					preparedStatement.setString(1, diagnosis.getEmail());
				}
				if (diagnosis.getCondition() != null) {
					preparedStatement.setString(2, diagnosis.getCondition());
				}
				if(diagnosis.getSubcategory() != null){
					preparedStatement.setString(3, diagnosis.getSubcategory());
				}
				
				if (diagnosis.getDiagnosisDate() != null) {
					preparedStatement.setString(4, diagnosis.getDiagnosisDate());
				}
				if (diagnosis.getNote() != null) {
					preparedStatement.setString(5, diagnosis.getNote());
				}

				System.out.println(preparedStatement.toString());
				preparedStatement.execute();

				query = "select last_insert_id() as id";
				preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					response.setMessage(resultSet.getString("id"));
				}

				response.setCode(0);
				// } catch (MySQLIntegrityConstraintViolationException e){
				// success = "The user " + userProfile.getEmail() +
				// " already exist in the system." ;
			} catch (SQLException e) {
				response.setMessage("Error in creation diagnosis history");
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
	public DiagnosisList getDiagnosisList(String email) {
//		long start = Calendar.getInstance().getTimeInMillis();
//
//		ClientConfig config = new DefaultClientConfig();
//		Client client = Client.create(config);
//		String url = serverRoot + "/diagnosis/" + email;
//		WebResource service = client.resource(url);
//
//		String response = service.accept(MediaType.APPLICATION_JSON).get(String.class);
//
//		DiagnosisList diagnosisList = new Gson().fromJson(response, DiagnosisList.class);
//		long end = Calendar.getInstance().getTimeInMillis();
//		System.out.println("time diff get diagnosis list: " + (end - start));
//		return diagnosisList;
		Connection connection = null;
		DiagnosisList diagnosisList = new DiagnosisList();
		try {

			System.out.println("Connecting to database...");
			connection = connect();
			try {

				PreparedStatement preparedStatement = connection
						.prepareStatement("select * from phr.diagnosis_history where user_email=? order by diagnosis_id desc");

				preparedStatement.setString(1, email);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Diagnosis temp = new Diagnosis();
					temp.setId(resultSet.getLong("diagnosis_id"));
					temp.setEmail(resultSet.getString("user_email"));
					temp.setCondition(resultSet.getString("neuro_condition"));
					temp.setSubcategory(resultSet.getString("subcategory"));
					temp.setDiagnosisDate(resultSet.getString("diagnosis_date"));
					temp.setNote(resultSet.getString("note"));

					diagnosisList.getDiagnosisList().add(temp);
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

		return diagnosisList;

	}

	@Override
	public Response updateDiagnosis(Diagnosis diagnosis) {
//		long start = Calendar.getInstance().getTimeInMillis();
//		ClientConfig config = new DefaultClientConfig();
//		Client client = Client.create(config);
//		// WebResource service = client.resource(serverRoot + signupPath);
//		String url = serverRoot + "/diagnosis/update/" + diagnosis.getId();
//		WebResource service = client.resource(url);
//		ClientResponse nameResource = service.accept(MediaType.APPLICATION_XML).put(ClientResponse.class, diagnosis);
//		Response updateDiagnosisResult = nameResource.getEntity(Response.class);
//
//		System.out.println(
//				"response code:" + updateDiagnosisResult.getCode() + "message:" + updateDiagnosisResult.getMessage());
//		long end = Calendar.getInstance().getTimeInMillis();
//		System.out.println("time diff update diagnosis call: " + (end - start));
//		return updateDiagnosisResult;
		Connection connection = null;
		Response response = new Response();
		try {


			System.out.println("Connecting database...");
			connection = connect();
			try {
				long diagnosisId = diagnosis.getId();
				String query = "update phr.diagnosis_history set modification_date=curdate(), "
				+ "neuro_condition=?, subcategory=?, diagnosis_date=?, note=? "
				+ " where diagnosis_id=" + diagnosisId;

				PreparedStatement preparedStatement = connection.prepareStatement(query);

				System.out.println(diagnosis.toString());
				if (diagnosis.getCondition() != null) {
					preparedStatement.setString(1, diagnosis.getCondition());
				}
				
				if (diagnosis.getSubcategory() != null) {
					preparedStatement.setString(2, diagnosis.getSubcategory());
				}
				
				if (diagnosis.getDiagnosisDate() != null) {
					preparedStatement.setString(3, diagnosis.getDiagnosisDate());
				}
				if (diagnosis.getNote() != null) {
					preparedStatement.setString(4, diagnosis.getNote());
				}

				System.out.println(preparedStatement.toString());
				preparedStatement.execute();

				response.setCode(0);
				// } catch (MySQLIntegrityConstraintViolationException e){
				// success = "The user " + userProfile.getEmail() +
				// " already exist in the system." ;
			} catch (SQLException e) {
				response.setMessage("Error in creation diagnosis history");
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
	public Response deleteDiagnosis(String email, String list) {
//		System.out.println("list:" + list);
//		list = Base64.encodeBase64String(list.getBytes());
//		long start = Calendar.getInstance().getTimeInMillis();
//
//		ClientConfig config = new DefaultClientConfig();
//		Client client = Client.create(config);
//		String url = serverRoot + "/diagnosis/" + email + "/" + list;
//		WebResource service = client.resource(url);
//
//		String result = service.accept(MediaType.APPLICATION_JSON).get(String.class);
//		Response response = new Gson().fromJson(result, Response.class);
//
//		System.out.println("delete response:" + response.getCode());
//		long end = Calendar.getInstance().getTimeInMillis();
//		System.out.println("time diff delete diagnosis list: " + (end - start));
//
//		return response;
		Connection connection = null;
		Response response = new Response(0, "");
		System.out.println("list:"+ list);
		try {
			String listAfterDecode = new String(Base64.decodeBase64(list));
			String[] diagnosisIdList = listAfterDecode.trim().split("\\|");
			connection = connect();
			for (String diagId : diagnosisIdList) {
				try {
					PreparedStatement preparedStatement = connection
							.prepareStatement("delete from phr.diagnosis_history where user_email=? and diagnosis_id= ?");
					preparedStatement.setString(1, email);
					preparedStatement.setInt(2, Integer.parseInt(diagId));
					int deletedRows = preparedStatement.executeUpdate();
					System.out.println("response from delete : "+ deletedRows);
					if (deletedRows == 0) {
						response.setCode(-1);
					}

				} catch(SQLException e){
					System.out.println("sqlException in individual delete " + e.getMessage());
				}catch (Exception e) {
					System.out.println("Exception in individual delete " + e.getMessage());
					// TODO: handle exception
				}
			}

		} catch (Exception ex) {
			System.out.println("Exception in individual delete " + ex.getMessage());
			response.setCode(-1);
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
