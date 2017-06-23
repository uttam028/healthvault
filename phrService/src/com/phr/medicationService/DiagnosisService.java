package com.phr.medicationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import org.apache.commons.codec.binary.Base64;

import com.phr.util.DatabaseUtil;
import com.phr.util.Response;

@Path("/diagnosis")
public class DiagnosisService {

	private Connection connection;
	private PreparedStatement preparedStatement;

//	static {
//		System.out.println("loading database driver....diagnosis service");
//		DatabaseUtil.loadDriver();
//	}
	
	

	@Path("{email}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public DiagnosisList getDiagnosisHistory(@PathParam("email") String email) {

		DiagnosisList diagnosisList = new DiagnosisList();
		try {

			System.out.println("Connecting to database...");
			connection = DatabaseUtil.connectToDatabase();
			try {

				preparedStatement = connection
						.prepareStatement("select * from phr.diagnosis_history where user_email=? order by diagnosis_id desc");

				preparedStatement.setString(1, email);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Diagnosis temp = new Diagnosis();
					temp.setId(resultSet.getLong("diagnosis_id"));
					temp.setEmail(resultSet.getString("user_email"));
					temp.setCondition(resultSet.getString("neuro_condition"));
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

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response createDiagnosisHistory(
			JAXBElement<Diagnosis> diagnosisJaxbElement) {
		Diagnosis diagnosis = diagnosisJaxbElement.getValue();
		Response response = new Response();
		try {
			System.out.println("diagnosis:" + diagnosis.toString());
			connection = DatabaseUtil.connectToDatabase();
			try {
				String query = "insert into phr.diagnosis_history (user_email, creation_date, modification_date, neuro_condition, "
						+ "diagnosis_date, note) values "
						+ "(?, curdate(), curdate(),?,?,?)";

				preparedStatement = connection.prepareStatement(query);

				System.out.println(diagnosis.toString());
				if (diagnosis.getEmail() != null) {
					preparedStatement.setString(1, diagnosis.getEmail());
				}
				if (diagnosis.getCondition() != null) {
					preparedStatement.setString(2, diagnosis.getCondition());
				}
				if (diagnosis.getDiagnosisDate() != null) {
					preparedStatement.setString(3, diagnosis.getDiagnosisDate());
				}
				if (diagnosis.getNote() != null) {
					preparedStatement.setString(4, diagnosis.getNote());
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

	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response updateDiagnosisHistory(@PathParam("id") String id,
			JAXBElement<Diagnosis> diagnosisJaxbElement) {
		Diagnosis diagnosis = diagnosisJaxbElement.getValue();
		Response response = new Response();
		try {


			System.out.println("Connecting database...");
			connection = DatabaseUtil.connectToDatabase();
			try {
				long diagnosisId = Long.parseLong(id);
				String query = "update phr.diagnosis_history set modification_date=curdate(), "
				+ "neuro_condition=?, diagnosis_date=?, note=? "
				+ " where diagnosis_id=" + diagnosisId;

				preparedStatement = connection.prepareStatement(query);

				System.out.println(diagnosis.toString());
				if (diagnosis.getCondition() != null) {
					preparedStatement.setString(1, diagnosis.getCondition());
				}
				if (diagnosis.getDiagnosisDate() != null) {
					preparedStatement.setString(2, diagnosis.getDiagnosisDate());
				}
				if (diagnosis.getNote() != null) {
					preparedStatement.setString(3, diagnosis.getNote());
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

	

	@Path("{email}/{list}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteDiagnosis(@PathParam("email") String email,
			@PathParam("list") String list) {
		Response response = new Response(0, "");
		System.out.println("list:"+ list);
		try {
			String listAfterDecode = new String(Base64.decodeBase64(list));
			String[] diagnosisIdList = listAfterDecode.trim().split("\\|");
			connection = DatabaseUtil.connectToDatabase();
			for (String diagId : diagnosisIdList) {
				try {
					preparedStatement = connection
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

}
