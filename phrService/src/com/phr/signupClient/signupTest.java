package com.phr.signupClient;


import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

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
		String path = "http://10.32.10.188:8080/phrservice/signup";
		String url = "http://10.32.10.188:8080/phrservice/login/changepassword";
	    WebResource service = client.resource(url);
	    //service.header("Content-Type", new String("application/json"));
	    String json = "{\"email\":\"ahossain@nd.edu\", \"oldPassword\":\"old\", \"newPassword\":\"new\"}";
		ClientResponse clientResponse = service.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
		System.out.println("response:"+ clientResponse);
		String jsonResponse = clientResponse.getEntity(String.class);
		System.out.println("json response:"+ jsonResponse);
	   
	   
	    
	}
	
	
	

		private static String getClientResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_XML).get(ClientResponse.class)
		.toString();
		}



		private static String getResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_XML).get(String.class);
		}

	   private static URI getBaseURI() {
	    //return UriBuilder.fromUri("http://m-health.cse.nd.edu:8000/phrService").build();
		   return UriBuilder.fromUri("http://localhost:8080/phrService").build();
	  }
	
}
