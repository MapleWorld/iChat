package org.csc301team6.server;

import java.sql.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ThreadsDTO {
	private ThreadsDTO(){
		
	}
	
	public static long createThread(String sessionID, 
									String title, 
									String body, 
									int cat_id, 
									int[] topics)
									throws UnauthorizedException{
		long thread_id = -1;
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		long userid;
		CSC301User user;
		
		try {
			userid = SessionDTO.getUserIDFromSessionID(sessionID);
			user = UserDTO.fetchUserByID(userid);
			if(user.getBanned()){
				throw new UnauthorizedException("User is banned and cannot post.");
			}
			
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			
			ps = conn.prepareStatement("insert into thread (cat_id, title, body, created_at) "+
										"values (?, ?, ?, NOW())", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, cat_id);
			ps.setString(2, title);
			ps.setString(3, body);
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			if(rs.next()){
				thread_id = rs.getInt(1);
			} else {
				thread_id = -1;
			}
			
			rs.close();
			ps.close();
			
		} catch (UnauthorizedException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new UnauthorizedException("Database connection error");
		} finally {
			try {
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return thread_id;
	}
	
	public static String getThreadPageAsJSONString(long thread_id, long page){
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		long RPP = mgr.getRepliesPerPage();
		long maxReply = page * mgr.getRepliesPerPage();
		long idx;
		JSONObject jThread;
		JSONArray jReplies;
		JSONArray jTopics;
		JSONObject jReply;

		jThread = new JSONObject();

		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select t.*, u.username from thread t inner join user u on t.user_id = u.id "
										+" where t.id = ?");
			ps.setLong(1, thread_id);
			rs = ps.executeQuery();
			
			if(rs.next()){
				jThread.put("title", rs.getString("title"));
				jThread.put("username", rs.getString("username"));
				jThread.put("body", rs.getString("body"));
				jThread.put("timestamp", rs.getTimestamp("created_at").toString());
				
				//TODO: Get list of topics (need to implement TopicDTO first)
				//For now we will default to an empty array
				
				jTopics = new JSONArray();
				
				//Get list of replies
				
				ps = conn.prepareStatement("select r.*, u.username from reply r "+
											" inner join user u on r.user_id = u.id "+
											" where thread_id = ? order by created_at asc;");
				ps.setLong(1, thread_id);
				
				rs = ps.executeQuery();
				
				jReplies = new JSONArray();
				
				for(idx = 0; rs.next(); idx++){
					if(idx >= ((page - 1) * RPP) && idx < maxReply) {
						//This reply is on the target page
						jReply = new JSONObject();
						jReply.put("username", rs.getString("username"));
						jReply.put("body", rs.getString("body"));
						jReply.put("timestamp", rs.getTimestamp("created_at").toString());
						jReplies.put(jReply);
					}
				}
				
				//Now we can use the value of idx to find out how many pages there are
				jThread.put("pages", (idx / RPP) + 1);
				jThread.put("this_page", page);
				
				//If no replies are found, jReplies will be an empty array.
				//Otherwise it will contain the replies within the page range
				
				jThread.put("replies", jReplies);
				jThread.put("topics", jTopics);
				
			} else {
				//No thread found with this ID
				jThread = null;
			}
					
			rs.close();
			ps.close();				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return jThread == null? null : jThread.toString();
	}
}
