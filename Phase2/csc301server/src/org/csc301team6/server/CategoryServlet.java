package org.csc301team6.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}
