package org.csc301team6.server;

//This gets thrown when an action fails due to lack of authorization

public class UnauthorizedException extends Exception {
	public UnauthorizedException(){
		super();
	}
}
