package com.example.messageapp;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import appControl.CSC301ListViewItem;
import appControl.DAO;
import appControl.Session;

public class ViewTopicListByCategoryActivity extends Activity {
	private ListView listview;
	private String categoryID;
	private Session session;
	private ArrayAdapter<CSC301ListViewItem	> arrayAdapter;
	private ArrayList<CSC301ListViewItem> itemsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_topic_list_by_category);
		session = new Session(getApplicationContext());
		listview = (ListView) findViewById(R.id.topicListView);
		
		try {
			displayTopicList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(session.isAdmin()) {
			listview.setOnItemLongClickListener(new OnItemLongClickListener() {
	
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					final String topicName = ((TextView) view).getText().toString();
					
					// Build delete dialog
					AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(ViewTopicListByCategoryActivity.this);
					deleteDialogBuilder.setTitle("Delete Topic");
					deleteDialogBuilder.setMessage("Delete " + topicName + "?");
					deleteDialogBuilder.setCancelable(false);
	                
					deleteDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int position) {
							try {
								// Get the topic ID that matches the selected topic
								DAO serverDAO = new DAO();
								String topicID = "";
								JSONObject topicResponse = serverDAO
										.getServerResponseContent("/topics/list/"
												+ categoryID);
								JSONArray topics = topicResponse.getJSONArray("topics");
	
								for (int i = 0; i < topics.length(); i++) {
									JSONObject o = topics.getJSONObject(i);
									if (o.getString("name").equals(topicName)) {
										Integer id = o.getInt("id");
										topicID = id.toString();
										break;
									}
								}
								
								JSONObject result = serverDAO.deleteTopic(topicID, session.getUserDetails().get("session"));
								String message = (String) result.get("message");
								
								if (result.get("success").equals(true)) {
									itemsList.remove(position);
									arrayAdapter.notifyDataSetChanged();
									
									Toast msg = Toast.makeText(getApplicationContext(), topicName + " deleted successfully", Toast.LENGTH_LONG);
									msg.show();
								} else {
									Toast msg = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
									msg.show();
								}
							} catch (Exception e) {
								Toast msg = Toast.makeText(getApplicationContext(), "Failed to delete topic",
										Toast.LENGTH_LONG);
								msg.show();
							}
						}
	
					});
	                
					deleteDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					
					// Create and display the deletion prompt
					AlertDialog deleteDialog = deleteDialogBuilder.create();
					deleteDialog.show();
					return true;
				}
				
			});
		}
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				long topicID;
				JSONObject jThreadData;
				
				try {
					topicID = ((CSC301ListViewItem) listview.getItemAtPosition(position)).getID();
					
					jThreadData = (new DAO()).getThreadsByTopicID(topicID, 1);
					
					if(jThreadData != null) {
						Intent intent = new Intent(getApplicationContext(), ViewListThreadActivity.class);
						intent.putExtra("thread", jThreadData.toString());
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(), 
								"Error loading threads", 
								Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), 
							"Error loading threads", 
							Toast.LENGTH_LONG).show();
				}
				
			}
		});
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
	public void displayTopicList() {
		
		try {
			Intent intent = getIntent();
			categoryID = intent.getStringExtra("catID");
			
			// Perform a POST request to get the list of topics for the given category
			// and display it
			DAO response = new DAO();
			JSONObject result = response.getServerResponseContent("/topics/list/"
					+ categoryID);
			JSONArray results = result.getJSONArray("topics");
			itemsList = new ArrayList<CSC301ListViewItem>();
	
			for (int i = 0; i < results.length(); i++) {
				JSONObject o = results.getJSONObject(i);
				itemsList.add(new CSC301ListViewItem(o.getLong("id"), o.getString("name")));
			}
	
			arrayAdapter = new ArrayAdapter<CSC301ListViewItem>(this,
					android.R.layout.simple_list_item_1, itemsList);
	
			listview.setAdapter(arrayAdapter);
		} catch (Exception e) {
			Toast.makeText(this, "Error loading topics", Toast.LENGTH_LONG).show();
		}
	}

}
