package com.example.messageapp;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewListThreadActivity extends Activity implements
		OnItemClickListener {

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

		listview.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		ListView allItems = (ListView) findViewById(R.id.threadListView);
		
		String itemContent = allItems.getItemAtPosition(position).toString();
		String[] itemID = itemContent.split(":");

		if (itemID.length > 0) {
			Intent intent = new Intent(this, ViewThreadActivity.class);
			intent.putExtra("threadID", itemID[0]);
			startActivity(intent);
		} else {
			Toast msg = Toast.makeText(this, "Failed For Some Reason",
					Toast.LENGTH_LONG);
			msg.show();
		}
	}

	/**
	 * Display the list of threads.
	 */
	public void displayThreadList() throws Exception {
		Intent intentN = getIntent();
		String threadListString = intentN.getStringExtra("thread");

		JSONObject JSONString = new JSONObject(threadListString);

		JSONArray result = JSONString.getJSONArray("threads");
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < result.length(); i++) {
			JSONObject o = result.getJSONObject(i);
			list.add(o.getString("id") + ":" + o.getString("title"));
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