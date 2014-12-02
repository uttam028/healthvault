package com.phr.userProfileService;
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

import com.phr.util.DatabaseUtil;
import com.phr.util.UserProfile;


@Path("/profile")
public class UserProfileService {
	
	    private Connection connection = null;
	    private Statement statement ;
	    private PreparedStatement preparedStatement = null;
	    private ResultSet resultSet = null;
	    
	    
	    static{    	
	    	DatabaseUtil.loadDriver();
	    }

	    @Path("{email}")
	    @GET
	    @Produces("application/json")
	    public UserProfile getUser(@PathParam("email") String email) {
	       
	    	UserProfile profile = new UserProfile();
	    	try {			
            
	    	System.out.println("Connecting to database...");
	    	connection = DatabaseUtil.connectToDatabase(); 
            try{
                
                statement = connection.createStatement();              
                preparedStatement = connection.prepareStatement("SELECT * FROM PHR.USER_PROFILE WHERE EMAIL=?");
                
                preparedStatement.setString(1,email ); 
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                	 profile.setEmail(resultSet.getString("EMAIL"));
                	 profile.setFirstName(resultSet.getString("FIRST_NAME"));
                	 profile.setLastName(resultSet.getString("LAST_NAME"));
                	 profile.setBirthDay(resultSet.getString("BIRTHDAY"));
                	 profile.setAddress(resultSet.getString("ADDRESS"));
                	 profile.setMobileNum(Long.parseLong(resultSet.getString("CONTACT_NO")));               	 		
                
                }
              }
    	      catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
	    	}  finally {
	    	    System.out.println("Closing the connection.");
	    	    if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
	    	}	    	
	    	
	    	return profile;
	    }

	    @PUT
	    @Consumes(MediaType.APPLICATION_XML)
	    @Produces(MediaType.APPLICATION_XML)
	    public String createUser(JAXBElement<UserProfile> userProfileJAXBElement){
	        UserProfile userProfile = userProfileJAXBElement.getValue();
	        return saveUser(userProfile);
	         
	    }

	    private String saveUser(UserProfile userProfile){

	        String success = "";
	        try {
	        	System.out.println("Connecting database...");
	        	connection = DatabaseUtil.connectToDatabase(); 
	           try{
	        	statement = connection.createStatement();
	            
           
	            preparedStatement = connection
	                    .prepareStatement("update PHR.USER_PROFILE set ADDRESS=?, BIRTHDAY = ?, CONTACT_NO =? where EMAIL=?");
	            preparedStatement.setString(1, userProfile.getAddress());
	            preparedStatement.setString(2, userProfile.getBirthDay());
	            preparedStatement.setLong(3, userProfile.getMobileNum() );
	            preparedStatement.setString(4, userProfile.getEmail());

	            preparedStatement.execute();
	            success = "The profile " + userProfile.getEmail() + " updated successfully." ;
     
	            }
	           catch (SQLException e) {
	        	   success = "Error in updating profile. Please refer server logs for more information";
	            e.printStackTrace();
	        }
	    } finally {
    	    System.out.println("Closing the connection.");
    	    if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
    	}	
	        return success;
	    }
	   

}