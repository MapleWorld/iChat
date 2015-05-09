package appControl;

public class CSC301Reply {
	private String username;
	private long userid;
	private String timestamp;
	private String body;
	private long replyID;
	
	public CSC301Reply(String setUsername, long setUserid, String setTimestamp, String setBody, long setReplyID) {
		username  = setUsername;
		userid    = setUserid;
		timestamp = setTimestamp;
		body      = setBody;
		replyID   = setReplyID;
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
	
	public String getBody() {
		return body;
	}
	
	public long getReplyID() {
		return replyID;
	}
}
