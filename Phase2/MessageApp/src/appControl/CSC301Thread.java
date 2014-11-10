package appControl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CSC301Thread {

	private String username;
	private long userid;
	private String timestamp;
	private String title;
	private String body;
	private ArrayList<CSC301Reply> replies;
	
	public CSC301Thread (String setUsername, 
						long setUserid, 
						String setTimestamp,
						String setTitle,
						String setBody) {
		
		username = setUsername;
		userid = setUserid;
		timestamp = setTimestamp;
		title = setTitle;
		body = setBody;
		
		replies = new ArrayList<CSC301Reply>();
	}
	
	//Create thread from a JSON object
	public CSC301Thread (JSONObject jo) throws Exception {
		JSONArray jReplies;
		JSONObject jReply;
		CSC301Reply reply;

		replies = new ArrayList<CSC301Reply>();
		
		try {
			username = jo.getString("username");
			userid = jo.getLong("userid");
			timestamp = jo.getString("timestamp");
			title = jo.getString("title");
			body = jo.getString("body");
			jReplies = jo.getJSONArray("replies");
			
			for(int idx = 0; idx < jReplies.length(); idx++) {
				jReply = (JSONObject) jReplies.get(idx);
				
				reply = new CSC301Reply(jReply.getString("username"), 
										jReply.getLong("userid"),
										jReply.getString("timestamp"),
										jReply.getString("body"));
				
				replies.add(reply);
				
			}
		} catch (JSONException je) {
			throw new Exception();
		}
	}
	
	public String getUsername() {
		return username;
	}

	public long getUserid() {
		return userid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public void addReply(CSC301Reply reply) {
		replies.add(reply);
	}
	
	public CSC301Reply[] getReplies() {
		return (CSC301Reply[]) replies.toArray(new CSC301Reply[0]);
	}
}
