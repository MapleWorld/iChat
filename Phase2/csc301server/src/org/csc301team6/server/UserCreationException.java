package org.csc301team6.server;

public class UserCreationException extends Exception {
	private String message;
	
	public UserCreationException(String msg){
		super();
		message = new String(msg);
	}
	
	public String getMessage() {
		return message;
	}
}

