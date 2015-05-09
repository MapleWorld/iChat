package appControl;

public class User {

	private String username;
	private String password;
	private String id;

	public User(String username, String password, String id) {
		this.username = username;
		this.password = password;
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String Password) {
		this.password = Password;
	}

	public String getID() {
		return this.id;
	}

	public void setID(String ID) {
		this.id = ID;
	}

}
