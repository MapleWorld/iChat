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
		String topics_values = "";
		
		try {
			userid = SessionDTO.getUserIDFromSessionID(sessionID);
			user = UserDTO.fetchUserByID(userid);
			if(user.getBanned()){
				throw new UnauthorizedException("User is banned and cannot post.");
			}
			
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			
			//We have multiple inserts taking place that must be atomic
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement("insert into thread (cat_id, title, body, user_id, created_at, updated_at) "+
										"values (?, ?, ?, ?, NOW(), NOW())", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, cat_id);
			ps.setString(2, title);
			ps.setString(3, body);
			ps.setLong(4, user.getID());
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			if(rs.next()){
				thread_id = rs.getInt(1);
			} else {
				thread_id = -1;
			}
			
			rs.close();
			ps.close();
			
			//Now we must set up the topic <-> thread relationships
			//For now, we will silently ignore topics that are from the
			//wrong category. Should we throw an error instead? Up for debate.
			if(topics.length > 0 && thread_id > 0) {
				for(int idx = 0; idx < topics.length; idx++){
					topics_values += ("(?, ?)");
					if(idx + 1 < topics.length){
						topics_values += ", ";
					} else {
						topics_values += " ";
					}
				}
				
				ps = conn.prepareStatement("insert into thread_topics (thread_id, topic_id) values " + topics_values);

				for(int idx = 0; idx < topics.length * 2; idx += 2) {
					ps.setLong(idx + 1, thread_id);
					ps.setLong(idx + 2, topics[idx / 2]);
				}
				
				ps.executeUpdate();
				
				ps.close();
					
				//Get rid of topic associations where provided topic was
				//from the wrong category.
				
				ps = conn.prepareStatement("delete tt from thread_topics tt " +
											" inner join topic top on tt.topic_id = top.id " +
											" inner join thread tr on tt.thread_id = tr.id " +
											" where top.cat_id <> tr.cat_id ");
				ps.executeUpdate();
			}
			
			conn.commit();
			
		} catch (UnauthorizedException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException se) {
			se.printStackTrace();
			try {
				//Revert what we did if there is a failure
				conn.rollback();
			} catch (SQLException se2) {
				se.printStackTrace();
			}
			throw new UnauthorizedException("An error has occurred");
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
		long pages;
		JSONObject jThread;
		JSONArray jReplies;
		JSONArray jTopics;
		JSONObject jTopic;
		JSONObject jReply;
		
		if(page < 1) page = 1;

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
				
				rs.close();
				ps.close();
				
				jTopics = new JSONArray();
				
				ps = conn.prepareStatement("select top.id, top.name from thread tr "+
											" inner join thread_topics tt on tr.id = tt.thread_id " +
											" inner join topic top on top.id = tt.topic_id " +
											" where tr.id = ?");
				ps.setLong(1, thread_id);
				
				rs = ps.executeQuery();
				
				while(rs.next()){
					jTopic = new JSONObject();
					jTopic.put("id", rs.getLong("id"));
					jTopic.put("name", rs.getString("name"));
					jTopics.put(jTopic);
				}
				
				//Get list of replies
				
				ps = conn.prepareStatement("select r.*, u.username from reply r "+
											" inner join user u on r.user_id = u.id "+
											" where thread_id = ? order by created_at asc " +
											" limit ? offset ?");
				ps.setLong(1, thread_id);
				ps.setLong(2, mgr.getRepliesPerPage());
				ps.setLong(3, mgr.getRepliesPerPage() * (page - 1));
				
				rs = ps.executeQuery();
				
				jReplies = new JSONArray();
				
				while(rs.next()){
					jReply = new JSONObject();
					jReply.put("username", rs.getString("username"));
					jReply.put("body", rs.getString("body"));
					jReply.put("timestamp", rs.getTimestamp("created_at").toString());
					jReplies.put(jReply);
				}
				
				rs.close();
				ps.close();
				
				
				//Determine how many pages there are
				ps = conn.prepareStatement("select count(*) numReplies from reply where thread_id = ?");
				ps.setLong(1, thread_id);
				
				rs = ps.executeQuery();
				
				if(rs.next()) {
					pages = rs.getLong("numReplies");
				} else {
					pages = 0;
				}

				rs.close();
				ps.close();
				
				jThread.put("this_page", page);
				jThread.put("pages", pages / mgr.getRepliesPerPage() + 1);
				
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
	
	public static long addReply(String sessionID, long thread_id, String body)
		throws UnauthorizedException {
		

		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		long userid;
		CSC301User user;
		long reply_id = -1;
		
		try {
			userid = SessionDTO.getUserIDFromSessionID(sessionID);
			user = UserDTO.fetchUserByID(userid);
			if(user.getBanned()){
				throw new UnauthorizedException("User is banned and cannot post.");
			}
			
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			
			ps = conn.prepareStatement("insert into reply (user_id, thread_id, body, created_at) "+
										"values (?, ?, ?, NOW())", Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, userid);
			ps.setLong(2, thread_id);
			ps.setString(3, body);
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			
			if(rs.next()){
				reply_id = rs.getInt(1);
			} else {
				reply_id = -1;
			}
			
			rs.close();
			ps.close();
			
		} catch (UnauthorizedException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException se) {
			se.printStackTrace();
			if(se.getSQLState().equals("23000")){
				//This is likely a foreign key integrity violation due to
				//an invalid thread_id provided
				throw new UnauthorizedException("Invalid thread ID");
			} else {
				throw new UnauthorizedException("An error has occurred");
			}
		} finally {
			try {
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return reply_id;
	}
	
	public static String listThreadsByCategoryAsJsonString(long category_id, long page) {
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		JSONObject jResp;
		JSONArray jThreads;
		JSONObject jThreadHeading;
		long pages;
		
		if(page < 1) page = 1;
		
		jResp = new JSONObject();
		
		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select * from thread where cat_id = ? " +
										"order by created_at desc limit ? offset ?");
			
			ps.setLong(1, category_id);
			ps.setLong(2, mgr.getThreadsPerPage());
			ps.setLong(3, mgr.getThreadsPerPage() * (page - 1));
			
			rs = ps.executeQuery();
			
			jThreads = new JSONArray();
			
			while(rs.next()){
				jThreadHeading = new JSONObject();
				jThreadHeading.put("id", rs.getLong("id"));
				jThreadHeading.put("title", rs.getString("title"));
				jThreadHeading.put("update_timestamp", rs.getTimestamp("updated_at").toString());
				
				jThreads.put(jThreadHeading);
			}
				
			rs.close();
			ps.close();
			
			ps = conn.prepareStatement("select count(*) numThreads from thread " +
										"where cat_id = ?");
			
			ps.setLong(1, category_id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				pages = (rs.getLong("numThreads") / mgr.getThreadsPerPage()) + 1;
			} else {
				pages = 0;
			}
			
			jResp.put("pages", pages);
			jResp.put("this_page", page);
			
			jResp.put("threads", jThreads);
					
			rs.close();
			ps.close();				
		} catch (SQLException e) {
			jResp = null;
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return jResp == null? null : jResp.toString();
	}
	
	public static String listThreadsByTopicAsJSONString(long topic_id, long page) {
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		JSONObject jResp;
		JSONArray jThreads;
		JSONObject jThreadHeading;
		long pages;
		
		if(page < 1) page = 1;
		
		jResp = new JSONObject();
		
		
		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select tr.* from thread_topics tt inner join thread tr " +
										" on tt.thread_id = tr.id where tt.topic_id = ? " +
										" order by tr.created_at desc limit ? offset ?");
			ps.setLong(1, topic_id);
			ps.setLong(2, mgr.getThreadsPerPage());
			ps.setLong(3, mgr.getThreadsPerPage() * (page - 1));
			
			rs = ps.executeQuery();
			
			jThreads = new JSONArray();
			
			while(rs.next()){
				jThreadHeading = new JSONObject();
				jThreadHeading.put("id", rs.getLong("id"));
				jThreadHeading.put("title", rs.getString("title"));
				jThreadHeading.put("update_timestamp", rs.getTimestamp("updated_at").toString());
				
				jThreads.put(jThreadHeading);
			}
				
			rs.close();
			ps.close();
			
			ps = conn.prepareStatement("select count(*) numThreads from thread_topics tt " +
										" inner join thread tr on tt.thread_id = tr.id " +
										" where tt.topic_id = ?");
			
			ps.setLong(1, topic_id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				pages = (rs.getLong("numThreads") / mgr.getThreadsPerPage()) + 1;
			} else {
				pages = 0;
			}
			
			jResp.put("pages", pages);
			jResp.put("this_page", page);
			
			jResp.put("threads", jThreads);
					
			rs.close();
			ps.close();				
		} catch (SQLException e) {
			jResp = null;
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return jResp == null? null : jResp.toString();
	}
	
}
