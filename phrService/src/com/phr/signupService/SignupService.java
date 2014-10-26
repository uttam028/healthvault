package com.phr.signupService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import org.json.JSONObject;


@Path("/signup")
public class SignupService {
	
	    private Connection connect = null;
	    private Statement statement = null;
	    private PreparedStatement preparedStatement = null;
	    private ResultSet resultSet = null;

	    @Path("{userid}")
	    @GET
	    @Produces("application/json")
	    public String getUser(@PathParam("userid") String userid) {
	       
	    	try {
				Class.forName("com.mysql.jdbc.Driver");
			
            // setup the connection with the DB.
            connect = DriverManager.getConnection("jdbc:mysql://localhost/PHR?"
                    + "user=root&password=admin123");
            statement = connect.createStatement();
            
            preparedStatement = connect
                    .prepareStatement("SELECT * FROM PHR.USERS WHERE EMAIL=?");
            preparedStatement.setString(1,userid ); 
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
            	return "TRUE"; 
            
            }
	    	} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return "FALSE";
	    }

	    @PUT
	    @Consumes(MediaType.APPLICATION_XML)
	    @Produces(MediaType.APPLICATION_XML)
	    public String createUser(JAXBElement<UserProfile> userProfileJAXBElement){
	        UserProfile userProfile = userProfileJAXBElement.getValue();
	        if( saveUser(userProfile))
	            return "The user "+ userProfile.getEmail() + " created successfully !!" ;
	        else
	            return "Error in user creation";
	    }

	    private boolean saveUser(UserProfile userProfile){

	        boolean success = false;
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            // setup the connection with the DB.
	            connect = DriverManager.getConnection("jdbc:mysql://localhost/PHR?"
	                    + "user=root&password=admin123");
	            statement = connect.createStatement();
	            
	            preparedStatement = connect
	                    .prepareStatement("insert into  PHR.USERS values ( ?,?)");
	            preparedStatement.setString(1, userProfile.getEmail() );
	            preparedStatement.setString(2, userProfile.getPassword());
	            preparedStatement.execute();
	            
	            preparedStatement = connect
	                    .prepareStatement("insert into  PHR.USER_PROFILE values ( ?,?, ?, ?, ? , ?)");
	            preparedStatement.setString(1, userProfile.getEmail() );
	            preparedStatement.setString(2, userProfile.getFirstName() );
	            preparedStatement.setString(3, userProfile.getLastName() );
	            preparedStatement.setString(4, userProfile.getBirthDay() );
	            preparedStatement.setString(5, userProfile.getAddress() );
	            preparedStatement.setLong(6, userProfile.getMobileNum() );

	            preparedStatement.execute();
	            success = true;
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return success;
	    }
	    
	    
	    @POST
	    @Produces(MediaType.APPLICATION_JSON)
	    @Consumes(MediaType.APPLICATION_JSON)
	    public JSONObject sayPlainTextHello(JSONObject inputJsonObj) throws Exception {

	      String input = (String) inputJsonObj.get("input");
	      String output = "The input you sent is :" + input;
	      JSONObject outputJsonObj = new JSONObject();
	      outputJsonObj.put("output", output);

	      return outputJsonObj;
	    }

}