package org.csc301team6.server;

import java.sql.*;

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
}
