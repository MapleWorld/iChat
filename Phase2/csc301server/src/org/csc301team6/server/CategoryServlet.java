package org.csc301team6.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class CategoryServlet extends HttpServlet {

	public CategoryServlet() {

	}

	/* There is only one thing this servlet does, which is return a 
	 * list of all categories from the database. The categories are
	 * written into the response as a JSON string.
	 * */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		String responseText = CategoryDTO.getAllCategoriesAsJSON();

		response.getWriter().println(responseText);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Pattern newTopicPattern = Pattern.compile("^\\/categories\\/create$");
		Matcher newThreadMatcher = newTopicPattern.matcher(request
				.getRequestURI());
		JSONObject jResp;

		if (newThreadMatcher.find()) {
			doNewCategory(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			jResp = new JSONObject();
			jResp.put("message", "Illegal request");
			response.getWriter().println(jResp.toString());
		}
	}

	
	private HttpServletResponse doNewCategory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String line;
		String jsonInput = "";
		JSONObject jo;
		BufferedReader br = request.getReader();
		String category_name;
		String sessionID;
		JSONObject jResp;
		long category_id = -1;

		jResp = new JSONObject();

		response.setContentType("application/json");

		sessionID = request.getHeader("SESSIONID");

		if (sessionID == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			jResp.put("success", false);
			jResp.put("message", "No session token provided");
		} else {

			while ((line = br.readLine()) != null) {
				jsonInput += line;
			}

			try {
				jo = new JSONObject(jsonInput);
				category_name = jo.getString("name");
				category_id = CategoryDTO.createCategory(sessionID, category_name);

				if (category_id < 0) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					jResp.put("success", false);
					jResp.put("message", "Error creating category from provided name");
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
					jResp.put("success", true);
					jResp.put("message", "Category created");
					jResp.put("cat_id", category_id);
				}

			} catch (JSONException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				jResp.put("success", false);
				jResp.put("message", "Error parsing request");
			} catch (UnauthorizedException ue) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				jResp.put("success", false);
				jResp.put("message", ue.getMessage());
			}
		}

		response.getWriter().println(jResp.toString());
		return response;
	}

}
