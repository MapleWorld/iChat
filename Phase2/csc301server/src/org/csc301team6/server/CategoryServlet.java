package org.csc301team6.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CategoryServlet extends HttpServlet {
	    
	    public CategoryServlet(){
	    	
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	    {
	        response.setContentType("text/html");
	        response.setStatus(HttpServletResponse.SC_OK);
	        String responseText = CategoryDTO.getAllCategoriesAsJSON();
	        
	        response.getWriter().println(responseText);
	    }

}
