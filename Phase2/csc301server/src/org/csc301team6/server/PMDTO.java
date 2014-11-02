package org.csc301team6.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PMDTO {

	private PMDTO(){
		//PMDTO does not get instantiated
	}
	
	public static boolean sendPM(String sessionID, 
								long userid_to, 
								String subject, 
								String body) throws UnauthorizedException {
		
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		long userid_from;

		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			
			userid_from = SessionDTO.getUserIDFromSessionID(sessionID);
			
			ps = conn.prepareStatement("insert into message (userid_from, userid_to, send_time, subject, body) "
										+" values (?, ?, NOW(), ?, ?)");

			ps.setLong(1, userid_from);
			ps.setLong(2, userid_to);
			ps.setString(3, subject);
			ps.setString(4, body);
			
			ps.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
			throw new UnauthorizedException("An error has occurred");
		} catch (UnauthorizedException e) {
			e.printStackTrace();
			throw e;
		} finally {
	
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
