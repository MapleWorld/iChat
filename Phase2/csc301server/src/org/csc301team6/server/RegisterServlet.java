package org.csc301team6.server;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterServlet extends HttpServlet {
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        
        response.getWriter().println("{\"message\":\"Method not allowed\"}");
    }
    
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	String line;
    	String jsonInput = "";
    	JSONObject jo;
    	BufferedReader br = request.getReader();
    	String username;
    	String password;
    	String sessionID;
    	JSONObject jResp;
    	
    	jResp = new JSONObject();
    	
        response.setContentType("application/json");
    	
    	while((line = br.readLine()) != null){
    		jsonInput += line;
    	}
    	
    	try {
    		jo = new JSONObject(jsonInput);
    		username = jo.getString("username");
    		password = jo.getString("password");
    		
    	} catch (JSONException e) {
    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    		jResp.put("success", false);
    		jResp.put("SESSIONID", JSONObject.NULL);
    		jResp.put("message", e.getMessage());
    		response.getWriter().println(jResp.toString());
    		return;
    	}
    	
    	try {
    		UserDTO.createUser(username, password);
    	} catch (UserCreationException e) {
    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    		jResp.put("success", false);
    		jResp.put("SESSIONID", JSONObject.NULL);
    		jResp.put("message", e.getMessage());
    		response.getWriter().println(jResp.toString());
    		return;
    	}
    	

    	sessionID = SessionDTO.createSession(username, password);
  
    	if(sessionID == null){
    		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		jResp.put("success", false);
    		jResp.put("SESSIONID", JSONObject.NULL);
    		jResp.put("message", "Error creating session for new user");
    	} else {
    		jResp.put("success", true);
    		jResp.put("SESSIONID", sessionID);
    		jResp.put("message", "User created successfully");
    	}
    	
    	response.getWriter().println(jResp.toString());
    }
}
