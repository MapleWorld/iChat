package org.csc301team6.server;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;

public class SessionDTO {
	private static SecureRandom sessionRNG = null;

	private SessionDTO() {
		// this doesn't get called
	}

	//Start a new session if the credentials are valid
	public static String createSession(String username, String password) {
		ConfigManager mgr = ConfigManager.getInstance();
		String sessionID = null;
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		byte[] sessionBytes = new byte[32];
		long userid;
		boolean banned;

		if (sessionRNG == null) {
			sessionRNG = new SecureRandom();
		}

		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select * from user where username = ? and password = ?");
			ps.setString(1, username);
			ps.setString(2, getSHA256FromString(username + password));
			rs = ps.executeQuery();

			if (rs.next()) {
				userid = rs.getLong("id");
				banned = rs.getInt("banned") == 1;
				
				rs.close();
				ps.close();

				if(!banned) {
					sessionRNG.nextBytes(sessionBytes);
					sessionID = getSHA256FromBytes(sessionBytes);
					ps = conn.prepareStatement("insert into sessions (sessionid, userid, expires) values (?, ?, DATE_ADD(NOW(), INTERVAL ? MINUTE))");
					ps.setString(1, sessionID);
					ps.setLong(2, userid);
					ps.setInt(3, mgr.getSessionDurationMinutes());

					ps.executeUpdate();
					ps.close();
				} else {
					sessionID = null;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			sessionID = null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return sessionID;
	}

	//Given a session ID, look up the associated user ID
	public static long getUserIDFromSessionID(String sessionID)
			throws UnauthorizedException {
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		long userid = -1;

		destroyExpiredSessions();

		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select * from sessions s inner join user u on s.userid = u.id where sessionid = ?");
			ps.setString(1, sessionID);
			rs = ps.executeQuery();

			if (rs.next()) {
				if(rs.getInt("u.banned") == 0) {
					userid = rs.getLong("s.userid");
				} else {
					throw new UnauthorizedException("User is banned, session is not valid");
				}

			} else {
				throw new UnauthorizedException("Session is not valid");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new UnauthorizedException("An error has occurred");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return userid;
	}

	/* Update the expiration time for the session */
	public static void updateSession(String sessionID)
			throws UnauthorizedException {
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;

		destroyExpiredSessions();

		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("update sessions set expires = DATE_ADD(NOW(), INTERVAL ? MINUTE)"
									 + " where sessionid = ?");
			ps.setInt(1, mgr.getSessionDurationMinutes());
			ps.setString(2, sessionID);

			if (ps.executeUpdate() < 1) {
				throw new UnauthorizedException("Session not created");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Get rid of all sessions which have expired May be expensive to do on
	 * every request if sessions table is large
	 */
	private static void destroyExpiredSessions() {
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;

		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("delete from sessions where expires < NOW()");
			ps.executeUpdate();
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
	}

	/* Destroy a specific session */
	public static boolean destroySession(String sessionID) {
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		int destroyed = 0;

		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("delete from sessions where sessionid = ?");
			ps.setString(1, sessionID);
			destroyed = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			destroyed = 0;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return destroyed > 0 ? true : false;
	}

	//Return a hex string which is the computed SHA-256 hash of the argument
	public static String getSHA256FromString(String str) {
		MessageDigest md = null;
		String sb;
		byte[] bytes;

		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (Exception e) {
			// don't care about this
			e.printStackTrace();
		}

		md.update(str.getBytes());
		bytes = md.digest();

		sb = new String();
		for (int i = 0; i < bytes.length; i++) {
			sb = sb + String.format("%1$02x", bytes[i]);
		}

		return sb.toString();
	}

	//Return a hex string which is the computed SHA-256 hash of the argument
	public static String getSHA256FromBytes(byte[] bytes) {
		MessageDigest md = null;
		String sb;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (Exception e) {
			// don't care about this
			e.printStackTrace();
		}

		md.update(bytes);
		md.digest();

		sb = new String();
		for (int i = 0; i < bytes.length; i++) {
			sb = sb + String.format("%1$02x", bytes[i]);
		}

		return sb.toString();
	}
}
