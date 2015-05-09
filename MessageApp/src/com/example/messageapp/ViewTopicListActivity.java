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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import appControl.CSC301ListViewItem;
import appControl.DAO;

public class ViewTopicListActivity extends Activity implements
		OnItemClickListener{

	private ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_topic_list);
		ShowTopicList(null);
		listview.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_topic_list, menu);
		return true;
	}
	
	/**
	 * Display a list of topics
	 */
	public void ShowTopicList(View v) {
		
		
		ArrayList<CSC301ListViewItem> catList = new ArrayList<CSC301ListViewItem>();
	
		try {
			
			listview = (ListView) findViewById(R.id.categoryListForTopics);
			
			DAO response = new DAO();
			JSONObject result = response.getServerResponseContent("/categories");
			JSONArray results = result.getJSONArray("categories");
	
			for (int i = 0; i < results.length(); i++) {
				JSONObject o = results.getJSONObject(i);
				catList.add(new CSC301ListViewItem(o.getLong("id"), o.getString("name")));
			}
	
			ArrayAdapter<CSC301ListViewItem> arrayAdapter = new ArrayAdapter<CSC301ListViewItem>(this,
					android.R.layout.simple_list_item_1, catList);
	
			listview.setAdapter(arrayAdapter);
			
		} catch (Exception e) {
			Toast.makeText(this, "Error loading categories", Toast.LENGTH_LONG).show();

		}
		
	}
	
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		ListView allItems = (ListView) findViewById(R.id.categoryListForTopics);
		
		long itemID = ((CSC301ListViewItem) allItems.getItemAtPosition(position)).getID();

		Intent intent = new Intent(this, ViewTopicListByCategoryActivity.class);
		intent.putExtra("catID", Long.toString(itemID));
		startActivity(intent);

	}

}
