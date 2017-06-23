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

@Path("/medication")
public class medicationService {

	private Connection connection;
	private PreparedStatement preparedStatement;

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

				preparedStatement = connection.prepareStatement(query);

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

				preparedStatement = connection
						.prepareStatement("select * from phr.medication_history where user_email=? order by medication_id desc");

				preparedStatement.setString(1, email);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Medication temp = new Medication();
					temp.setId(resultSet.getLong("medication_id"));
					temp.setEmail(resultSet.getString("user_email"));
					temp.setName(resultSet.getString("name"));
//					try {
//						temp.setStrength(resultSet.getInt("strength"));
//					} catch (Exception e) {
//						// TODO: handle exception
//						temp.setStrength(0);
//					}
//					temp.setStrengthUnit(resultSet.getString("strength_unit"));
//					try {
//						temp.setDosage(resultSet.getInt("dosage"));
//					} catch (Exception e) {
//						// TODO: handle exception
//						temp.setDosage(0);
//					}
//					temp.setDosageUnit(resultSet.getString("dosage_unit"));
//					temp.setConsumeProcess(resultSet
//							.getString("consume_process"));
//					temp.setConsumeFrequency(resultSet
//							.getString("consume_frequency"));
					temp.setReason(resultSet.getString("reason"));
					temp.setStartDate(resultSet.getString("start_date"));
					temp.setEndDate(resultSet.getString("end_date"));
//					temp.setIsPrescribed(resultSet.getString("is_prescribed"));
//					temp.setPrescribedBy(resultSet.getString("prescribed_by"));
//					temp.setPrescribedDate(resultSet
//							.getString("prescribed_date"));
//					temp.setInstruction(resultSet.getString("instruction"));
//					temp.setPrescribedQuantity(resultSet
//							.getString("prescription_quantity"));
//					temp.setNote(resultSet.getString("note"));

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
//				String query = "insert into phr.medication_history (user_email, creation_date, modification_date, name, "
//						+ "strength, strength_unit, dosage, dosage_unit, consume_process, consume_frequency, reason, start_date, end_date, "
//						+ "is_prescribed, prescribed_by, prescribed_date, instruction, prescription_quantity, note) values "
//						+ "(?, curdate(), curdate(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String query = "insert into phr.medication_history (user_email, creation_date, modification_date, name, "
						+ "reason, start_date, end_date) values "
						+ "(?, curdate(), curdate(),?,?,?,?)";

				preparedStatement = connection.prepareStatement(query);

				System.out.println(medication.toString());
				if (medication.getEmail() != null) {
					preparedStatement.setString(1, medication.getEmail());
				}
				if (medication.getName() != null) {
					preparedStatement.setString(2, medication.getName());
				}
//				preparedStatement.setInt(3, medication.getStrength());
//				if (medication.getStrengthUnit() != null) {
//					preparedStatement
//							.setString(4, medication.getStrengthUnit());
//				}
//				preparedStatement.setInt(5, medication.getDosage());
//				if (medication.getDosageUnit() != null) {
//					preparedStatement.setString(6, medication.getDosageUnit());
//				}
//				if (medication.getConsumeProcess() != null) {
//					preparedStatement.setString(7,
//							medication.getConsumeProcess());
//				}
//				if (medication.getConsumeFrequency() != null) {
//					preparedStatement.setString(8,
//							medication.getConsumeFrequency());
//				}
				if (medication.getReason() != null) {
					preparedStatement.setString(3, medication.getReason());
				}
				if (medication.getStartDate() != null) {
					preparedStatement.setString(4, medication.getStartDate());
				}
				if (medication.getEndDate() != null) {
					preparedStatement.setString(5, medication.getEndDate());
				}
//				if (medication.getIsPrescribed() != null) {
//					preparedStatement.setString(12,
//							medication.getIsPrescribed());
//				}
//				if (medication.getPrescribedBy() != null) {
//					preparedStatement.setString(13,
//							medication.getPrescribedBy());
//				}
//				if (medication.getPrescribedDate() != null) {
//					preparedStatement.setString(14,
//							medication.getPrescribedDate());
//				}
//				if (medication.getInstruction() != null) {
//					preparedStatement
//							.setString(15, medication.getInstruction());
//				}
//				if (medication.getPrescribedQuantity() != null) {
//					preparedStatement.setString(16,
//							medication.getPrescribedQuantity());
//				}
//				if (medication.getNote() != null) {
//					preparedStatement.setString(17, medication.getNote());
//				}

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
				String query = "update phr.medication_history set name=?, modification_date=curdate(), "
				+ "reason=?, start_date=?, end_date=? "
				+ " where medication_id=" + medicId;

				preparedStatement = connection.prepareStatement(query);

				System.out.println(medication.toString());
				if (medication.getName() != null) {
					preparedStatement.setString(1, medication.getName());
				}
//				preparedStatement.setInt(2, medication.getStrength());
//				if (medication.getStrengthUnit() != null) {
//					preparedStatement
//							.setString(3, medication.getStrengthUnit());
//				}
//				preparedStatement.setInt(4, medication.getDosage());
//				if (medication.getDosageUnit() != null) {
//					preparedStatement.setString(5, medication.getDosageUnit());
//				}
//				if (medication.getConsumeProcess() != null) {
//					preparedStatement.setString(6,
//							medication.getConsumeProcess());
//				}
//				if (medication.getConsumeFrequency() != null) {
//					preparedStatement.setString(7,
//							medication.getConsumeFrequency());
//				}
				if (medication.getReason() != null) {
					preparedStatement.setString(2, medication.getReason());
				}
				if (medication.getStartDate() != null) {
					preparedStatement.setString(3, medication.getStartDate());
				}
				if (medication.getEndDate() != null) {
					preparedStatement.setString(4, medication.getEndDate());
				}
//				if (medication.getIsPrescribed() != null) {
//					preparedStatement.setString(11,
//							medication.getIsPrescribed());
//				}
//				if (medication.getPrescribedBy() != null) {
//					preparedStatement.setString(12,
//							medication.getPrescribedBy());
//				}
//				if (medication.getPrescribedDate() != null) {
//					preparedStatement.setString(13,
//							medication.getPrescribedDate());
//				}
//				if (medication.getInstruction() != null) {
//					preparedStatement
//							.setString(14, medication.getInstruction());
//				}
//				if (medication.getPrescribedQuantity() != null) {
//					preparedStatement.setString(15,
//							medication.getPrescribedQuantity());
//				}
//				if (medication.getNote() != null) {
//					preparedStatement.setString(16, medication.getNote());
//				}

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
					preparedStatement = connection
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
