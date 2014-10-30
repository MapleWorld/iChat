package com.example.messageapp;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import appControl.DAO;

public class ViewTopicListByCategoryActivity extends Activity {
	private ListView listview;
	private String catID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_topic_list_by_category);
		listview = (ListView) findViewById(R.id.topicListView);

		try {
			displayTopicList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_topic_list_by_category, menu);
		return true;
	}

	/**
	 * Display a list of topics for a given category
	 */
	public void displayTopicList() throws Exception {
		Intent intent = getIntent();
		catID = intent.getStringExtra("catID");
		
		// Perform a POST request to get the list of topics for the given category
		// and display it
		DAO response = new DAO();
		JSONObject result = response.getServerResponseContent("/topics/list/"
				+ catID);
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
