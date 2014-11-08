package org.csc301team6.server;

/*
 * Data model for a user of this application
 * Used for packaging together user information
 * returned from a database query.
 */
public class CSC301User {

	private String username;
	private long id;
	private boolean banned;
	private boolean isAdminV;

	public CSC301User(boolean isAdminSet) {
		isAdminV = isAdminSet;
	}

	public void setID(long new_id) {
		id = new_id;
	}

	public long getID() {
		return id;
	}

	public void setBanned(boolean set_banned) {
		banned = set_banned;
	}

	public boolean getBanned() {
		return banned;
	}

	public void setUsername(String new_username) {
		username = new_username;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAdmin(){
		return isAdminV;
	}
	
}
