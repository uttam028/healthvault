package com.phr.signupClient;


import java.net.URI;
import java.sql.Date;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.phr.util.UserProfile;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class signupTest {

	public static void main(String[] args) {
		
	    ClientConfig config = new DefaultClientConfig();
	    Client client = Client.create(config);
	    client.addFilter(new LoggingFilter());
	    WebResource service = client.resource(getBaseURI());
		
	    UserProfile user= new UserProfile("ra@gmail.com", "abc","aaa","aaa","10/10/10","aaa",10010);
	    
	   String response = service.path("signup").path("signup").path(user.getEmail()).accept(MediaType.APPLICATION_JSON).get(String.class);
	   System.out.println("Client Response \n"+ response);		
	   
//	   ClientResponse nameResource =  service.path("signup").path("signup").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, user) ;
//	     
//	   System.out.println("Client Response \n"+ nameResource.getEntity(String.class));
	   
	   ClientResponse nameResource =  service.path("signup").path("signup").accept(MediaType.APPLICATION_XML).put(ClientResponse.class, user) ;
 	   System.out.println("Client Response \n"+ nameResource.getEntity(String.class));
	   
	   response = service.path("signup").path("signup").path(user.getEmail()).accept(MediaType.APPLICATION_JSON).get(String.class);
	   System.out.println("Client Response \n"+ response);	
	   
	    
	    // System.out.println("Response \n" + getResponse(nameResource) + "\n\n");
	   JSONObject inputJsonObj = new JSONObject();
	   inputJsonObj.put("input", "Value");
	   nameResource =  service.path("signup").path("signup").accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, inputJsonObj.toString()) ;
	   System.out.println("Client Response \n"+ response);	
	   
	   
	   
	    
	}
	
	
	

		private static String getClientResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_XML).get(ClientResponse.class)
		.toString();
		}



		private static String getResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_XML).get(String.class);
		}

	   private static URI getBaseURI() {
	    return UriBuilder.fromUri("http://m-health.cse.nd.edu:8000/phrService").build();
	  }
	
}
