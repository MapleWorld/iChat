package org.csc301team6.server;

import java.sql.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoryDTO {

	private CategoryDTO() {
	}

	/* Return a JSON string listing all categories found 
	 * in the database. 
	 * */
	public static String getAllCategoriesAsJSON() {
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		JSONArray category_array;
		JSONObject category;
		JSONObject category_obj;

		category_obj = new JSONObject();

		try {
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select * from category");
			rs = ps.executeQuery();

			category_array = new JSONArray();

			/* Read all categories from the database */
			while (rs.next()) {
				category = new JSONObject();
				category.put("id", rs.getInt("id"));
				category.put("name", rs.getString("name"));
				category_array.put(category);
			}

			category_obj = new JSONObject();
			category_obj.put("categories", category_array);

		} catch (SQLException e) {
			category_obj = new JSONObject();
			
			// Some sort of problem occurred, so we return an empty array
			category_obj.put("categories", new JSONArray());
			e.printStackTrace();
		} catch (JSONException je) {
			category_obj = new JSONObject();
			
			// Unable to insert some category data into the array
			// Return an empty array to be safe
			category_obj.put("categories", new JSONArray());
			je.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return category_obj.toString();
	}
	
	public static long createCategory(String sessionID, String name) throws UnauthorizedException {
		long category_id = -1;
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
			
			if (!user.isAdmin()) {
				throw new UnauthorizedException(
						"User is not Admin and cannot create category.");
			}

			conn = DriverManager.getConnection(mgr.getJDBCURL());

			// We have multiple inserts taking place that must be atomic
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(
					"insert into category (name) " + "values (?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, name);

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				category_id = rs.getInt(1);
			} else {
				category_id = -1;
			}

			rs.close();
			ps.close();
		} catch (UnauthorizedException e) {
			//Provided session ID is not valid
			e.printStackTrace();
			throw e;
		} catch (SQLException se) {
			//Error executing one of the updates
			se.printStackTrace();
			
			try {
				// Revert what we did if there is a failure
				conn.rollback();
			} catch (SQLException se2) {
				se.printStackTrace();
			}
			
			throw new UnauthorizedException("An error has occurred");
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return category_id;
	}
}
