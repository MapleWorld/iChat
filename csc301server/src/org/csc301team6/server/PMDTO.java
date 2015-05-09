package org.csc301team6.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	public static String viewPMAsJSONString(String sessionID, long pmID) throws UnauthorizedException {
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		long userid;
		JSONObject pmData;
		JSONObject jResp;
		long userid_from;
		long userid_to;
		
		jResp = new JSONObject();
		
		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			
			userid = SessionDTO.getUserIDFromSessionID(sessionID);

			ps = conn.prepareStatement("select m.*, u1.username username_from, "
										+ " u2.username username_to from message m "
										+ " inner join user u1 on u1.id = m.userid_from "
										+ " inner join user u2 on u2.id = m.userid_to "
										+ " where m.id = ?");
			
			ps.setLong(1, pmID);
			rs = ps.executeQuery();
			
			if(rs.next()){
				userid_from = rs.getLong("userid_from");
				userid_to   = rs.getLong("userid_to");
				
				if(userid == userid_to || userid == userid_from) {
					pmData = new JSONObject();
					pmData.put("msg_id", pmID);
					pmData.put("userid_to", userid_to);
					pmData.put("userid_from", userid_from);
					pmData.put("username_from", rs.getString("username_from"));
					pmData.put("username_to", rs.getString("username_to"));
					pmData.put("subject", rs.getString("subject"));
					pmData.put("msg_body", rs.getString("body"));
					pmData.put("datetime", rs.getTimestamp("send_time").toString());
					
					jResp.put("pm", pmData);
					jResp.put("success", true);
					
					rs.close();
					ps.close();
					
					if(userid == userid_to) {
						//Mark the message as "read" (no longer unread)
						ps = conn.prepareStatement("update message set unread = 0 where id = ?");
						ps.setLong(1, pmID);
						ps.executeUpdate();
						
						ps.close();
					}
					
				} else {
					//User tried to access a message that it did not send or receive
					throw new UnauthorizedException("Access denied");
				}
			} else {
				jResp = new JSONObject();
				jResp.put("pm", JSONObject.NULL);
				jResp.put("message", "Message does not exist");
				jResp.put("success", false);		
			}


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

		return jResp.toString();
	}
	
	public static String viewMsgBoxPageAsJSONString(String sessionID, long inboxPage, int boxType) throws UnauthorizedException {
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		long userid;
		JSONObject inboxData;
		JSONArray inboxMsgs;
		JSONObject pmData;
		
		if(inboxPage < 1){
			inboxPage = 1;
		}

		inboxData = new JSONObject();
		
		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			
			userid = SessionDTO.getUserIDFromSessionID(sessionID);
			
			if(boxType == ConfigManager.BOX_TYPE_INBOX){
				ps = conn.prepareStatement("select m.*, u.username from message m "
										+ " inner join user u on u.id = m.userid_from "
										+ "where m.userid_to = ? limit ? offset ?");
			} else {
				ps = conn.prepareStatement("select m.*, u.username from message m "
										+ " inner join user u on u.id = m.userid_to "
										+ "where m.userid_from = ? limit ? offset ?");
			}
			ps.setLong(1, userid);
			ps.setLong(2, mgr.getInboxPMsPerPage());
			ps.setLong(3, (inboxPage - 1) * mgr.getInboxPMsPerPage());
			
			rs = ps.executeQuery();
			
			inboxMsgs = new JSONArray();
			
			while(rs.next()){
				pmData = new JSONObject();
				pmData.put("msg_id", rs.getLong("id"));
				pmData.put("username", rs.getString("username"));
				pmData.put("subject", rs.getString("subject"));
				pmData.put("datetime", rs.getTimestamp("send_time").toString());
				pmData.put("unread", rs.getInt("unread") == 1);
				
				inboxMsgs.put(pmData);	
			}

			inboxData.put("messages", inboxMsgs);
			
			rs.close();
			ps.close();
			
			if(boxType == ConfigManager.BOX_TYPE_INBOX) {
				ps = conn.prepareStatement("select count(*) numMsgs from message where userid_to = ?");
			} else {
				ps = conn.prepareStatement("select count(*) numMsgs from message where userid_from = ?");
			}
			
			ps.setLong(1, userid);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				inboxData.put("pages", (rs.getLong("numMsgs") / mgr.getInboxPMsPerPage()) + 1);
			} else {
				inboxData.put("pages", 0);
			}
			
			rs.close();
			ps.close();
			
			inboxData.put("this_page", inboxPage);
			inboxData.put("success", true);

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
		
		return inboxData.toString();
	}
}
