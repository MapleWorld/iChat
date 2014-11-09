package appControl;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

public class DAO {

	/**
	 * Login the user if the given username and password is a match with the
	 * data source.
	 */
	public JSONObject loginAccount(String username, String password)
			throws InterruptedException, ExecutionException, JSONException {

		Server server = new Server();
		JSONObject account = new JSONObject("{\"username\":\"" + username
				+ "\",\"password\":\"" + password + "\"}");

		JSONObject result = server.new sendPOSTRequest().execute("/login",
				account.toString()).get();
		return result;
	}

	/**
	 * Given an username and password, create a new user.
	 */
	public JSONObject createUser(String username, String password)
			throws Exception {

		Server server = new Server();
		JSONObject account = new JSONObject("{\"username\":\"" + username
				+ "\",\"password\":\"" + password + "\"}");

		JSONObject result = server.new sendPOSTRequest().execute("/register",
				account.toString()).get();
		return result;

	}

	/**
	 * Given the category ID, topic name and a valid session, create a new
	 * topic.
	 */
	public JSONObject createTopic(String categoryID, String topicName,
			String sessionID) throws Exception {
		Server server = new Server();
		JSONObject topic = new JSONObject("{\"category\":\"" + categoryID
				+ "\",\"name\":\"" + topicName + "\"}");

		JSONObject result = server.new sendPOSTRequest().execute(
				"/topics/create", topic.toString(), sessionID).get();
		return result;
	}

	/**
	 * Create a new thread with the given category and topic id, and thread name
	 * and body.
	 */
	public JSONObject createThread(String categoryID, String topicID,
			String threadName, String threadBody, String sessionID)
			throws Exception {
		Server server = new Server();
		JSONObject threadPOST = new JSONObject("{\"category\":\"" + categoryID
				+ "\",\"title\":\"" + threadName + "\",\"body\":\""
				+ threadBody + "\",\"topic_ids\":[" + topicID + "]}");
		JSONObject result = server.new sendPOSTRequest().execute(
				"/threads/new", threadPOST.toString(), sessionID).get();
		return result;
	}
	
	/**
	 * Edit an existing thread with the given category and topic id, and thread name
	 * and body.
	 */
	public JSONObject editThread(String categoryID, String topicID,
			String threadName, String threadBody, String sessionID)
			throws Exception {
		Server server = new Server();
		JSONObject threadPOST = new JSONObject("{\"category\":\"" + categoryID
				+ "\",\"title\":\"" + threadName + "\",\"body\":\""
				+ threadBody + "\",\"topic_ids\":[" + topicID + "]}");
		JSONObject result = server.new sendPOSTRequest().execute(
				"/threads/edit", threadPOST.toString(), sessionID).get();
		return result;
	}
	
	/**
	 * Given an user's session ID, log out the user.
	 */
	public JSONObject logoutUser(String sessionID) throws Exception {
		Server server = new Server();
		JSONObject result = server.new sendPOSTRequest().execute("/logout",
				"logout", sessionID).get();
		return result;
	}

	/**
	 * Helper method to get the server response JSON object for a given URL.
	 */
	public JSONObject getServerResponseContent(String uri)
			throws InterruptedException, ExecutionException {
		Server server = new Server();
		JSONObject result = server.new downloadUrl().execute(uri).get();

		return result;
	}
}
