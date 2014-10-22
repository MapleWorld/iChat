package org.csc301team6.server;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoryDTO {
	
	private CategoryDTO() {
	}
	
	public static String getAllCategoriesAsJSON(){
		ConfigManager mgr = ConfigManager.getInstance();
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		JSONArray category_array;
		JSONObject category;
		JSONObject category_obj;
		
		category_obj = new JSONObject();
		
		try{
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select * from category");
			rs = ps.executeQuery();
			
			category_array = new JSONArray();
			
			while(rs.next()){
				category = new JSONObject();
				category.put("id", rs.getInt("id"));
				category.put("name", rs.getString("name"));
				category_array.put(category);
			}
			
			category_obj = new JSONObject();
			category_obj.put("categories", category_array);
			
		} catch (SQLException e) {
			category_obj = new JSONObject();
			category_obj.put("categories", new JSONArray());
			e.printStackTrace();
		} catch (JSONException je) {
			category_obj = new JSONObject();
			category_obj.put("categories", new JSONArray());
			je.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return category_obj.toString();
	}
}
