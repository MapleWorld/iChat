package org.csc301team6.server;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoryDTO {
	private static ConfigManager mgr;
	
	private CategoryDTO() {
	}
	
	public static String getAllCategoriesAsJSON(){
		mgr = ConfigManager.getInstance();
		String categories_json = "{\"categories\":[]}";
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		JSONArray category_array;
		JSONObject category;
		JSONObject category_obj;
		
		try{
			System.out.println(mgr.getJDBCURL());
			conn = DriverManager.getConnection(mgr.getJDBCURL());
			ps = conn.prepareStatement("select * from category");
			rs = ps.executeQuery();
			
			category_array = new JSONArray();
			
			while(rs.next()){
				category = new JSONObject("{}");
				category.put("id", rs.getInt("id"));
				category.put("name", rs.getString("name"));
				category_array.put(category);
			}
			
			//category_array.toJSONArray(null);
			
			category_obj = new JSONObject("{}");
			category_obj.put("categories", category_array);
			categories_json = category_obj.toString();
			
		} catch (SQLException e) {
			categories_json = "{\"categories\":[]}";
			e.printStackTrace();
		} catch (JSONException je) {
			categories_json = "{\"categories\":[]}";
			je.printStackTrace();
		}
		return categories_json;
	}
}
