package com.phr.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/files")
public class FileUpload {
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;

//	static {
//		DatabaseUtil.loadDriver();
//	}

	private static final String upload_location = "/phrdata/speechdata/";
	private static final String storage_location = "/phrdata/speechdata/userdata/";
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream _inputStream,
			@FormDataParam("file") FormDataContentDisposition _contentDisposition, @Context HttpServletRequest request) {
		System.out.println("In upload file method:"+ _contentDisposition.getFileName());

		if(!getFileExtension(_contentDisposition.getFileName()).contains("wav")){
			System.out.println("returning status 400 unsupported file type");
			return Response.status(400).entity("unsupported file type").build();
		}
		
//		String uniqueFileId = UUID.randomUUID().toString(); 
//		String fileName = uniqueFileId + ".wav";
		String filePath = upload_location	+ _contentDisposition.getFileName();
//		String filePath = upload_location	+ fileName;
		System.out.println("file path:"+ filePath);
		

		// save the file to the server
		saveFile(_inputStream, filePath);
		double fileSize = getFileSize(filePath);
		System.out.println("file size:"+ fileSize);
		if(fileSize > 30){
			//delete files and retrun error code
			deleteFile(filePath);
			return Response.status(400).entity("file size too long").build();
		}
		
		System.out.println("extension:"+ getFileExtension(filePath) + ", mime type:"+ getMIMEType(filePath));
		
		
		/*
		if(getFileExtension(filePath).contains("wav") && getMIMEType(filePath).contains("wav")){
			//we can proceed
		}else {
			//delete files and retrun error code
			deleteFile(filePath);
			return Response.status(400).entity("unsupported file type").build();
		}
		*/
		try {
			connection = DatabaseUtil.connectToDatabase();
			statement = connection.createStatement();

			preparedStatement = connection.prepareStatement("insert into  phr.file_receiver (uuid, original_name, ip, receive_time) values ( ?, ?, ?, now())");
			preparedStatement.setString(1, _contentDisposition.getFileName());
			preparedStatement.setString(2, _contentDisposition.getFileName());
			preparedStatement.setString(3, request.getRemoteAddr());
			preparedStatement.execute();
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return Response.status(200).entity("success").build();

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
	
	@POST
	@Path("relocate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public com.phr.util.Response relocateUploadedFile(final Recording recording){
		com.phr.util.Response response = new com.phr.util.Response();
		try {
			int participationId = recording.getParticipationId();
			String userId = recording.getUserId();
			String studyId = recording.getStudyId();
			System.out.println("partid:"+ participationId + ", user id:"+ userId + ", study id:"+ studyId + ", subtest:" + recording.getSubtestId() + "file :"+ recording.getFileIdentifier());
			try {
				connection = DatabaseUtil.connectToDatabase();

				
				if(participationId<=0){
					if(!ServiceUtil.isEmptyString(userId) && !ServiceUtil.isEmptyString(studyId)){
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
					if(ServiceUtil.isEmptyString(userId)){
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
				
				if(ServiceUtil.isEmptyString(recording.getFileIdentifier())){
					return response;
				}
				
				if(recording.getSubtestId()<=0){
					return response;
				}
				
				String query = "insert into phr.speech_recording (participation_id, subtest_id, start_time, end_time, upload_time, file_identifier, retake_counter)"
						+ " values (?, ?, ?, ?, now(), ?, ?)";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1,  String.valueOf(participationId));
				preparedStatement.setString(2, String.valueOf(recording.getSubtestId()));
				preparedStatement.setString(3, validateTime(recording.getStartTime())? recording.getStartTime():null);
				preparedStatement.setString(4, validateTime(recording.getEndTime())? recording.getEndTime(): null);
				preparedStatement.setString(5, recording.getFileIdentifier());
				preparedStatement.setInt(6, recording.getRetakeCounter());
				
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
	
}
