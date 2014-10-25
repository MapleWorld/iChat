package org.csc301team6.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class TopicDTO {
	
	private TopicDTO(){
		
	}
	
	public static long createTopic(String sessionID, 
									String name,
									int cat_id)
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
			
			ps = conn.prepareStatement("insert into topic (cat_id, name, user_id, created_at) "+
										"values (?, ?, ?, NOW())", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, cat_id);
			ps.setString(2, name);
			ps.setLong(3, user.getID());
			
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
	
	
	
	
	public static String listtopicsbyCategoryAsJsonString(long category_id){
		  ConfigManager mgr = ConfigManager.getInstance();
		  Connection conn = null;
          PreparedStatement ps;
          ResultSet rs;
          JSONObject jResp;
          JSONArray  jTopics;
          JSONObject jTopicHeading;
          
          
          
          jResp = new JSONObject();
          
          try {
        	  conn = DriverManager.getConnection(mgr.getJDBCURL());
        	  ps =conn.prepareStatement("select * from topic where cat_id = ? ");
        	  ps.setLong(1, category_id);
 
        	  
        	  
        	  rs=ps.executeQuery();
        	  
        	  jTopics = new JSONArray();
        	  
        	  while(rs.next()){
        		  jTopicHeading = new JSONObject();
        		  jTopicHeading.put("id", rs.getLong("id"));
        		  jTopicHeading.put("name",rs.getString("name"));
        		  jTopicHeading.put("create_timestamp", rs.getTimestamp("created_at").toString());
        		  
        		  jTopics.put(jTopicHeading);
        		  
        	  }
        	  
        	  rs.close();
        	  ps.close();
        	  
        	 // ps = conn.prepareStatement("select count(*) numTopics from topic"  +
        	   //                                          "where cat_id = ?");
        	  
        	  
        	  //ps.setLong(1, category_id);
        	  
        	  
        	  //jResp.put("topics", jTopics);
        	  
        	  
        	  //rs.close();
        	  
        	 // ps.close();
          }catch (SQLException e){
        	  jResp = null;
        	  e.printStackTrace();
          }finally {
        	  try {
        		   conn.close();
        	  } catch (SQLException e) {
        		  e.printStackTrace();
        	  }	  
        	  
          }
	return jResp == null? null: jResp.toString();
 }
}	
