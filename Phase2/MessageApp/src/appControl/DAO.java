package appControl;

/*
 Creates a User instance
 Validates a User by username and password
 Creates a Post instance
 Gets Posts by thread id
 Creates a Thread instance
 Creates Category instance
 Creates a Topic instance
 Abstract definitions of data handling functions to 
 be implemented in an actual DAO.
 */

public class DAO {

	// Check user name and password and login user
	// Check user name and password with the server database
	// Login the user if they match with the database
	public boolean loginAccount(String username, String password) {
		if (checkUsername(username)) {
			if (checkPassword(password)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean checkUsername(String username) {
		return true;
	}

	public boolean checkPassword(String password) {
		return true;
	}

	// Check user name and password and create user
	// Connect to the server, check for duplicate user name
	// Create user and update server database
	public boolean createUser(String username, String password) {
		if (validateUsername(username)) {
			if (validatePassword(password)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean validateUsername(String username) {
		return true;
	}

	public boolean validatePassword(String password) {
		return true;
	}
}
