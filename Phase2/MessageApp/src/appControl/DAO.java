package appControl;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

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
	public JSONObject loginAccount(String username, String password)
			throws InterruptedException, ExecutionException, JSONException {

		Server server = new Server();
		JSONObject account = new JSONObject("{\"username\":\"" + username
				+ "\",\"password\":\"" + password + "\"}");

		JSONObject result = server.new sendPOSTRequest().execute("/login",
				account.toString()).get();
		return result;
	}

	// Check user name and password and create user
	// Connect to the server, check for duplicate user name
	// Create user and update server database
	public JSONObject createUser(String username, String password)
			throws Exception {

		Server server = new Server();
		JSONObject account = new JSONObject("{\"username\":\"" + username
				+ "\",\"password\":\"" + password + "\"}");

		JSONObject result = server.new sendPOSTRequest().execute("/register",
				account.toString()).get();
		return result;

	}

	public JSONObject createTopicWithSession(String categoryID,
			String topicName, String sessionID) throws Exception {

		Server server = new Server();
		JSONObject topic = new JSONObject("{\"category\":\"" + categoryID
				+ "\",\"name\":\"" + topicName + "\"}");

		System.out.println(sessionID);

		JSONObject result = server.new sendPOSTRequest().execute("/topics/create",
				topic.toString(), sessionID).get();
		return result;

	}

	public JSONObject getServerResponseContent(String uri)
			throws InterruptedException, ExecutionException {

		Server server = new Server();
		JSONObject result = server.new downloadUrl().execute(uri).get();

		return result;

	}
	
	public JSONObject logoutUser(String sessionID) throws Exception{
		
		Server server = new Server();
		JSONObject result = server.new sendPOSTRequest().execute("/logout", "logout", sessionID).get();
		System.out.println(result);
		return result;
		
	}

}
