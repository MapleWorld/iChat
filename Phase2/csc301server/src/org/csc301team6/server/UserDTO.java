package org.csc301team6.server;

import java.sql.*;

public class UserDTO {

	public static void createUser(String username, String password) throws UserCreationException{
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select username from user where username = ?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				rs.close();
				ps.close();
				throw new UserCreationException("Username already exists");		
			} else {
				rs.close();
				ps.close();
				
				ps = conn.prepareStatement("insert into user (username, password, created_at)"
											+" values (?, ?, NOW())");
				ps.setString(1, username);
				ps.setString(2, SessionDTO.getSHA256FromString(username+password));
				if(ps.executeUpdate() != 1) {
					throw new UserCreationException("Error creating user");
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserCreationException("Error creating user");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public static CSC301User fetchUserByID(long userid){
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		CSC301User user = null;
		
		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select * from user where id = ?");
			ps.setLong(1, userid);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				user = new CSC301User();
				user.setBanned(rs.getInt("banned") > 0);
				user.setID(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
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
		
		return user;
	}
	
	public static CSC301User fetchUserByName(String name){
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		CSC301User user = null;
		
		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select * from user where username = ?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				user = new CSC301User();
				user.setBanned(rs.getInt("banned") > 0);
				user.setID(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
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
		
		return user;
	}
}
