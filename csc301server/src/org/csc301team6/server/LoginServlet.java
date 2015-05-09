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

/*
 * This servlet handles login requests.
 * A successful login request will be made with a username
 * and password combination which agree with the values in the
 * database. The response will be a session ID if the login
 * attempt is successful, otherwise a null session ID will be 
 * provided to the user along with a message stating the attempt
 * was unsuccessful.
 */
public class LoginServlet extends HttpServlet {
	
	// GET requests are not permitted for /login
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject jResp;

		jResp = new JSONObject();
		jResp.put("message", "Method not allowed");

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

		response.getWriter().println(jResp.toString());
	}

	//Process the login attempt
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String line;
		String jsonInput = "";
		JSONObject jo;
		BufferedReader br = request.getReader();
		String username;
		String password;
		String sessionID;
		JSONObject jResp;
		CSC301User user;

		jResp = new JSONObject();

		response.setContentType("application/json");

		//Load the request data
		while ((line = br.readLine()) != null) {
			jsonInput += line;
		}

		//Attempt to parse the received data as JSON
		try {
			jo = new JSONObject(jsonInput);
			username = jo.getString("username");
			password = jo.getString("password");

		} catch (JSONException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jResp.put("success", false);
			jResp.put("SESSIONID", JSONObject.NULL);
			jResp.put("message", "Error parsing request");
			response.getWriter().println(jResp.toString());
			return;
		}

		//This function will compare the provided username and password
		//with the contents of the database to determine if the user
		//has provided valid credentials.
		sessionID = SessionDTO.createSession(username, password);

		if (sessionID == null) {
			//User provided invalid credentials
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			jResp.put("success", false);
			jResp.put("SESSIONID", JSONObject.NULL);
			jResp.put("message", "Authentication failure");
		} else {
			//User has been granted a session
			try {
				user = UserDTO.fetchUserByID(SessionDTO.getUserIDFromSessionID(sessionID));
				
				if(user != null && user.isAdmin()) {
					jResp.put("admin", true);
				} else {
					jResp.put("admin", false);
				}
				
				response.setStatus(HttpServletResponse.SC_OK);
				jResp.put("success", true);
				jResp.put("SESSIONID", sessionID);
				jResp.put("message", "Authenticated");
				jResp.put("userid", user.getID());
				
			} catch (UnauthorizedException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jResp.put("success", false);
				jResp.put("SESSIONID", JSONObject.NULL);
				jResp.put("message", "Authentication failure");
			}

		}

		response.getWriter().println(jResp.toString());
	}
}
