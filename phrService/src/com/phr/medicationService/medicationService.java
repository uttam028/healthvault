package com.phr.medicationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

@Path("/medication")
public class medicationService {

	private Connection connection;
	//private PreparedStatement preparedStatement;

//	static {
//		System.out.println("loading database driver.......medication service");
//		DatabaseUtil.loadDriver();
//	}
	
	@Path("endusing/{id}/{enddate}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response endUsingMedication(@PathParam("id") String id, @PathParam("enddate") String endDate){
		Response response = new Response();
		try {

			// setup the connection with the DB.
			// connect =
			// DriverManager.getConnection("jdbc:mysql://localhost/PHR?"
			// + "user=hasini&password=admin123");

			System.out.println("Connecting database...end using function");
			connection = DatabaseUtil.connectToDatabase();
			try {
				long medicId = Long.parseLong(id);
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
	

	@Path("{email}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public MedicationList getMedicationHistory(@PathParam("email") String email) {

		MedicationList medicationList = new MedicationList();
		try {

			System.out.println("Connecting to database...");
			connection = DatabaseUtil.connectToDatabase();
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

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response createMedicatioHistory(
			JAXBElement<MedicationList> medicationJaxbElement) {
		MedicationList medicationList = medicationJaxbElement.getValue();
		Response response = new Response();
		try {

			// setup the connection with the DB.
			// connect =
			// DriverManager.getConnection("jdbc:mysql://localhost/PHR?"
			// + "user=hasini&password=admin123");

			System.out.println("Connecting database...");
			connection = DatabaseUtil.connectToDatabase();
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

	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response updateMedicatioHistory(@PathParam("id") String id,
			JAXBElement<Medication> medicationJaxbElement) {
		Medication medication = medicationJaxbElement.getValue();
		Response response = new Response();
		try {

			// setup the connection with the DB.
			// connect =
			// DriverManager.getConnection("jdbc:mysql://localhost/PHR?"
			// + "user=hasini&password=admin123");

			System.out.println("Connecting database...");
			connection = DatabaseUtil.connectToDatabase();
			try {
				long medicId = Long.parseLong(id);
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

	

	@Path("{email}/{list}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteMedications(@PathParam("email") String email,
			@PathParam("list") String list) {
		Response response = new Response(0, "");
		System.out.println("list:"+ list);
		try {
			String listAfterDecode = new String(Base64.decodeBase64(list));
			String[] medList = listAfterDecode.trim().split("\\|");
			connection = DatabaseUtil.connectToDatabase();
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

}
