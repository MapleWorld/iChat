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

		String url = "http://10.0.2.2:8080/login";
		Server server = new Server();
		JSONObject account = new JSONObject("{\"username\":\"" + username
				+ "\",\"password\":\"" + password + "\"}");

		JSONObject result = server.new sendUser().execute(url,
				account.toString()).get();
		return result;
	}

	// Check user name and password and create user
	// Connect to the server, check for duplicate user name
	// Create user and update server database
	public JSONObject createUser(String username, String password)
			throws Exception {

		String url = "http://10.0.2.2:8080/register";
		Server server = new Server();
		JSONObject account = new JSONObject("{\"username\":\"" + username
				+ "\",\"password\":\"" + password + "\"}");

		JSONObject result = server.new sendUser().execute(url,
				account.toString()).get();
		return result;

	}
	
	public JSONObject createTopic(String categoryID, String topicName)
			throws Exception {

		String url = "http://10.0.2.2:8080/topics/create";
		Server server = new Server();
		JSONObject topic = new JSONObject("{\"category\":\"" + categoryID
				+ "\",\"name\":\"" + topicName + "\"}");

		JSONObject result = server.new createTopic().execute(url,
				topic.toString()).get();
		return result;

	}


	public JSONObject getServerResponseContent(String url)
			throws InterruptedException, ExecutionException {

		Server server = new Server();
		JSONObject result = server.new downloadUrl().execute(url).get();

		return result;

	}

}
