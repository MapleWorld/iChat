package appControl;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DAO {

	public JSONObject timeOut(JSONObject result) throws JSONException {
		if (result == null) {
			JSONObject timeOut = new JSONObject();
			timeOut.put("success", false);
			timeOut.put("message", "Connection Timed Out");
			return timeOut;
		} else {
			return result;
		}
	}

	/**
	 * Login the user if the given username and password is a match with the
	 * data source.
	 */
	public JSONObject loginAccount(String username, String password)
			throws InterruptedException, ExecutionException, JSONException {

		Server server = new Server();
		JSONObject account = new JSONObject();
		account.put("username", username);
		account.put("password", password);

		JSONObject result = server.new sendPOSTRequest().execute("/login",
				account.toString()).get();

		System.out.println(result.toString());
		return timeOut(result);
	}

	/**
	 * Given an user name and password, create a new user.
	 */
	public JSONObject createUser(String username, String password)
			throws Exception {

		Server server = new Server();
		JSONObject account = new JSONObject();
		account.put("username", username);
		account.put("password", password);

		JSONObject result = server.new sendPOSTRequest().execute("/register",
				account.toString()).get();

		return timeOut(result);

	}

	/**
	 * Given the category ID, topic name and a valid session, create a new
	 * topic.
	 */
	public JSONObject createTopic(String categoryID, String topicName,
			String sessionID) throws Exception {
		Server server = new Server();
		JSONObject topic = new JSONObject();
		topic.put("category", categoryID);
		topic.put("name", topicName);

		JSONObject result = server.new sendPOSTRequest().execute(
				"/topics/create", topic.toString(), sessionID).get();

		return timeOut(result);
	}
	
	public JSONObject deleteTopic(String topicID, String sessionID) throws Exception {
		Server server = new Server();

		JSONObject result = server.new sendPOSTRequest().execute(
				"/topics/delete/" + topicID, (new JSONObject("{}")).toString(), sessionID).get();
		
		return timeOut(result);
	}
	
	/**
	 * Ban a user
	 * 
	 * @param userID
	 * @return
	 */
	public boolean banUser(long userID, String sessionID) {
		Server server = new Server();
		JSONObject result;
		boolean success = false;

		try {
			result = server.new sendPOSTRequest().execute(
					"/users/ban/" + userID, "", sessionID).get();
			success = result.getBoolean("success");
		} catch (Exception e) {
			success = false;
		}

		return success;
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
		return timeOut(result);
	}

	/**
	 * Edit an existing thread with the given category and topic id, and thread
	 * name and body.
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
		return timeOut(result);
	}

	public JSONObject replyThread(String replyBody, String threadID,
			String sessionID) throws Exception {
		Server server = new Server();
		JSONObject replyReq = new JSONObject();

		replyReq.put("thread_id", threadID);
		replyReq.put("body", replyBody);

		JSONObject result = server.new sendPOSTRequest().execute(
				"/threads/reply", replyReq.toString(), sessionID).get();

		return timeOut(result);
	}

	public boolean deleteReply(long replyID, String sessionID) {
		boolean success = false;
		Server server = new Server();
		JSONObject result;

		try {
			result = server.new sendPOSTRequest().execute(
					"/threads/reply/delete/" + replyID, "", sessionID).get();
			success = result.getBoolean("success");
			if (result.optString("message") != null) {
				Log.e("com.example.messageapp", result.optString("message"));
			}
		} catch (Exception e) {
			Log.e("com.example.messageapp", "", e);
		}

		return success;
	}

	public boolean editReply(String newBody, long replyID, String sessionID) {
		JSONObject replyReq = new JSONObject();
		JSONObject result;
		Server server = new Server();
		boolean success;

		try {
			replyReq.put("body", newBody);
			result = server.new sendPOSTRequest().execute(
					"/threads/reply/edit/" + replyID, replyReq.toString(),
					sessionID).get();
			success = result.getBoolean("success");
		} catch (Exception e) {
			success = false;
			Log.e("com.example.messageapp", "exception", e);
		}

		return success;
	}

	/**
	 * Given an user's session ID, log out the user.
	 */
	public JSONObject logoutUser(String sessionID) throws Exception {
		Server server = new Server();
		JSONObject result = server.new sendPOSTRequest().execute("/logout",
				"logout", sessionID).get();
		return timeOut(result);
	}

	/**
	 * Helper method to get the server response JSON object for a given URL.
	 * 
	 * @throws JSONException
	 */
	public JSONObject getServerResponseContent(String uri)
			throws InterruptedException, ExecutionException, JSONException {
		Server server = new Server();
		JSONObject result = server.new downloadUrl().execute(uri).get();

		return timeOut(result);
	}

	public JSONObject createCategory(String categoryName, String sessionID)
			throws Exception {
		Server server = new Server();

		JSONObject category = new JSONObject();
		category.put("name", categoryName);

		JSONObject result = server.new sendPOSTRequest().execute(
				"/categories/create/", category.toString(), sessionID).get();

		return timeOut(result);
	}

	public JSONObject deleteThread(String threadID, String sessionID)
			throws Exception {
		Server server = new Server();
		JSONObject category = new JSONObject("{}");

		JSONObject result = server.new sendPOSTRequest().execute(
				"/threads/delete/" + threadID, category.toString(), sessionID)
				.get();

		return timeOut(result);
	}

}
