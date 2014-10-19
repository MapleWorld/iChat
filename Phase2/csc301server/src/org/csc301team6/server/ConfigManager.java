package org.csc301team6.server;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Iterator;

public class ConfigManager {
	
	private static ConfigManager mgr = null;
	private static String dbURL;
	private static final int SESSION_DURATION_MINUTES = 180;
	
	private ConfigManager(){
		InputStream in = ClassLoader.getSystemResourceAsStream("db.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuffer file_contents = new StringBuffer("");
		JSONObject json;
		String line;
		
		try {
			while((line = br.readLine()) != null){
				file_contents.append(line);
			}
		} catch (IOException e) {
			file_contents = null;
		}
		
		if(file_contents == null){
			dbURL = null;
		} else {
			json = new JSONObject(file_contents.toString());

			dbURL = "jdbc:mysql://"+json.getString("hostname")+":"+json.getInt("port")+
					"/"+json.getString("schema")+"?user="+json.getString("user")+
					"&password="+json.getString("pass");
		
		}
		
	}
	
	public String getJDBCURL(){
		return dbURL;
	}
	
	public int getSessionDurationMinutes(){
		return SESSION_DURATION_MINUTES;
	}
	
	public static ConfigManager getInstance() {
		if(mgr == null){
			mgr = new ConfigManager();
		}
		return mgr;
	}
}
