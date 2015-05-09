package com.example.messageapp;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import appControl.DAO;

public class ViewCategoriesActivity extends Activity implements
		OnItemClickListener {

	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_categories);

		listview = (ListView) findViewById(R.id.categoryListView);

		try {
			getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}

		listview.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ListView allItems = (ListView) findViewById(R.id.categoryListView);

		String categoryName = allItems.getItemAtPosition(position).toString();

		DAO findThreads = new DAO();
		JSONObject threads = null;
		try {
			threads = findThreads.getThreadsByCategoryName(categoryName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (threads != null) {
			Intent intent = new Intent(this, ViewListThreadActivity.class);
			intent.putExtra("thread", threads.toString());
			startActivity(intent);
		} else {
			Toast msg = Toast.makeText(this, "Failed For Some Reason",
					Toast.LENGTH_LONG);
			msg.show();
		}
	}

	/**
	 * Display the list of categories.
	 */
	public void getResponse() throws Exception {
		DAO response = new DAO();
		JSONObject result = response.getServerResponseContent("/categories");
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
