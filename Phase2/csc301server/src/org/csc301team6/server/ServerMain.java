package org.csc301team6.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ServerMain extends AbstractHandler {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>CSC301 test</h1>");
    }
 
    public static void main(String[] args) throws Exception {
    	
        Server server = new Server(8080);
        //server.setHandler(new TestServer());
        
    	ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    	context.setContextPath("/");
    	server.setHandler(context);
    	context.addServlet(new ServletHolder(new CategoryServlet()),"/categories");
    	context.addServlet(new ServletHolder(new RegisterServlet()),"/register");
    	context.addServlet(new ServletHolder(new LoginServlet()),"/login");
    	context.addServlet(new ServletHolder(new LogoutServlet()),"/logout");
        server.start();
        server.join();
    }
}