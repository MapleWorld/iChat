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

public class ViewListThreadActivity extends Activity {

	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_list_thread);

		listview = (ListView) findViewById(R.id.threadListView);

		try {
			displayThreadList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayThreadList() throws Exception {
		Intent intentN = getIntent();
		String threadListString = intentN.getStringExtra("thread");

		JSONObject JSONString = new JSONObject(threadListString);

		JSONArray result = JSONString.getJSONArray("threads");
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < result.length(); i++) {
			JSONObject o = result.getJSONObject(i);
			list.add(o.getString("title"));
		}

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

		listview.setAdapter(arrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}