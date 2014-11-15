package appControl;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
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

	public JSONObject deleteTopic(String topicID, String sessionID)
			throws Exception {
		Server server = new Server();

		JSONObject result = server.new sendPOSTRequest().execute(
				"/topics/delete/" + topicID, (new JSONObject("{}")).toString(),
				sessionID).get();

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

	public JSONObject editThreadBody(long threadID, String newBody,
			String sessionID) {
		JSONObject result;
		JSONObject editData;
		Server server = new Server();

		try {
			editData = new JSONObject();
			editData.put("body", newBody);

			result = server.new sendPOSTRequest()
					.execute("/threads/edit/" + threadID, editData.toString(),
							sessionID).get();
		} catch (Exception e) {
			result = null;
		}

		return result;
	}

	public JSONObject editThreadTopics(long threadID, long[] topics,
			String sessionID) {
		JSONObject result;
		JSONObject editData;
		JSONArray editArray;
		Server server = new Server();

		try {
			editArray = new JSONArray();
			for (int idx = 0; idx < topics.length; idx++) {
				editArray.put(topics[idx]);
			}

			editData = new JSONObject();
			editData.put("topic_ids", editArray);

			result = server.new sendPOSTRequest()
					.execute("/threads/edit/" + threadID, editData.toString(),
							sessionID).get();
		} catch (Exception e) {
			result = null;
		}

		return result;
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

	// Given Category Name, return a json array of all the categories
	public JSONArray getCategories() throws InterruptedException,
			ExecutionException, JSONException {
		JSONObject categoryResponse = this
				.getServerResponseContent("/categories");
		JSONArray categories = categoryResponse.getJSONArray("categories");
		return categories;
	}

	public JSONObject getThreadsByCategoryName(String categoryName)
			throws InterruptedException, ExecutionException, JSONException {

		JSONArray categoriesArray = getCategories();
		String[] categoryID = findCategoryID(categoriesArray, categoryName);
		JSONObject threads = getThreadsByCategoryID(categoryID, categoriesArray);
		return threads;
	}
	

	// Given Category Name, find its ID.
	public JSONObject getAllThreads() throws InterruptedException,
			ExecutionException, JSONException {

		JSONArray categoriesArray = getCategories();
		String[] categoryIDs = getAllCategoriesIDs(categoriesArray);
		JSONObject threads = getThreadsByCategoryID(categoryIDs,
				categoriesArray);

		return threads;
	}

	// Given category id, return all threads belongs to that category
	public JSONObject getThreadsByCategoryID(String[] categoryIDs,
			JSONArray categoriesArray) throws JSONException,
			InterruptedException, ExecutionException {
		
		JSONObject CategoryThreads;
		JSONArray allThreads = new JSONArray();

		for (int i = 0; i < categoryIDs.length; i++) {
			CategoryThreads = this
					.getServerResponseContent("/threads/by_category/"
							+ categoryIDs[i] + "/1");

			JSONArray resultCategoryThreads = CategoryThreads
					.getJSONArray("threads");

			for (int k = 0; k < resultCategoryThreads.length(); k++) {
				JSONObject singleThread = resultCategoryThreads
						.getJSONObject(k);
				allThreads.put(singleThread);
			}
		}

		JSONObject result = new JSONObject();
		result.put("threads", allThreads);

		return result;

	}

	// Given Category Name, find its ID.
	public String[] findCategoryID(JSONArray categoryArray, String categoryName)
			throws JSONException {
		String[] categoryIDs = new String[1];
		for (int i = 0; i < categoryArray.length(); i++) {
			JSONObject o = categoryArray.getJSONObject(i);
			if (o.getString("name").equals(categoryName)) {
				categoryIDs[0] = o.getString("id");
				break;
			}
		}
		return categoryIDs;
	}

	public String[] getAllCategoriesIDs(JSONArray categoriesArray)
			throws JSONException {

		String[] categoryIDs = new String[categoriesArray.length()];

		for (int i = 0; i < categoriesArray.length(); i++) {
			JSONObject o = categoriesArray.getJSONObject(i);
			categoryIDs[i] = o.getString("id");
		}

		return categoryIDs;

	}

}
