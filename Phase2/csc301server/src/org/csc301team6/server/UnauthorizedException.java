package org.csc301team6.server;

//This gets thrown when an action fails due to lack of authorization

public class UnauthorizedException extends Exception {
	private String message;
	
	public UnauthorizedException(String msg){
		super();
		message = msg;
	}
	
	public String getMessage() {
		return message;
	}
}
