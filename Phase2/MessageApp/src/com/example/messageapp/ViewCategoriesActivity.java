package com.example.messageapp;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import appControl.DAO;

public class ViewCategoriesActivity extends Activity {

	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_categories);

		listview = (ListView) findViewById(R.id.listView1);

		try {
			getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getResponse() throws Exception {
		DAO response = new DAO();
		JSONObject result = response
				.getServerResponseContent("/categories");
		JSONArray results = result.getJSONArray("categories");
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
