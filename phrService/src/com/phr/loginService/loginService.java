package com.phr.loginService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import com.phr.signupService.UserProfile;
import com.phr.util.DatabaseUtil;

@Path("/login")
public class loginService {


	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	
    static{    	
    	DatabaseUtil.loadDriver();
    }


    @Path("{userid}")
    @GET
    @Produces("application/json")
    public String getUser(@PathParam("userid") String userid) {
       
    	  	
    	return "FALSE";
    }

	
	
	
	
	  
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String login(JAXBElement<User> userJaxbElement ) {
    	User user;
		
try {
			user = userJaxbElement.getValue();
	    	connection = DatabaseUtil.connectToDatabase();            
            try{
                
                statement = connection.createStatement();              
                preparedStatement = connection.prepareStatement("SELECT * FROM PHR.USERS WHERE EMAIL=?");
                preparedStatement.setString(1, user.getEmail()); 
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                	//check password
                	if(user.getPassword().equals(resultSet.getString("PASSWORD"))){
                		return "TRUE";
                	}          
                	System.out.println("Incorrect password");
                	return "FALSE";              
                }else{
                	System.out.println("Incorrect Username");
                	return "FALSE";                	
                }
              }
    	      catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
	    	} finally {
	    	    System.out.println("Closing the connection.");
	    	    if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
	    	}	    	
    	
    	return "FALSE";
	
}
	
	
	
}
