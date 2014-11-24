package com.example.messageapp;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import appControl.DAO;

public class ViewSubscriptionsActivity extends Activity {

	private ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_subscriptions);
		
		listview = (ListView) findViewById(R.id.categoryListForTopics);
		
		try {
			getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Display the list of topics user is subscribed to. 
	 */
	public void getResponse() throws Exception {
		DAO response = new DAO();
		JSONObject result = response.getServerResponseContent("/topics/subscriptions");
		JSONArray results = result.getJSONArray("topics");
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < results.length(); i++) {
			JSONObject o = results.getJSONObject(i);
			list.add(o.getString("name"));
		}

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

		listview.setAdapter(arrayAdapter);
	}
}
