package com.example.messageapp;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import appControl.DAO;
import appControl.Session;

public class EditThreadActivity extends Activity {

	Session session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_thread);
		session = new Session(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
/*
	// Create a new thread
	public void editThread(View V) throws Exception {
		EditText categoryText = (EditText) findViewById(R.id.thread_category_name);
		EditText topicText = (EditText) findViewById(R.id.thread_topic_name);
		EditText threadText = (EditText) findViewById(R.id.new_thread_name);
		EditText threadBodyText = (EditText) findViewById(R.id.thread_body);

		String categoryName = categoryText.getText().toString();
		String topicName = topicText.getText().toString();
		String threadName = threadText.getText().toString();
		String threadBody = threadBodyText.getText().toString();

		Integer categoryID = null;
		String topicID = "";

		DAO serverDAO = new DAO();

		JSONObject categoryResponse = serverDAO
				.getServerResponseContent("/categories");
		JSONArray categories = categoryResponse.getJSONArray("categories");

		// Get the category ID that matches the given category
		for (int i = 0; i < categories.length(); i++) {
			JSONObject o = categories.getJSONObject(i);
			if (o.getString("name").equals(categoryName)) {
				categoryID = o.getInt("id");
				break;
			}
		}

		// Get the topic ID that matches the given topic
		if (topicName != null && categoryID != null) {
			JSONObject topicResponse = serverDAO
					.getServerResponseContent("/topics/list/"
							+ categoryID.toString());
			JSONArray topics = topicResponse.getJSONArray("topics");

			for (int i = 0; i < topics.length(); i++) {
				JSONObject o = topics.getJSONObject(i);
				if (o.getString("name").equals(topicName)) {
					Integer id = o.getInt("id");
					topicID = id.toString();
					break;
				}
			}
		}

		// Perform POST request to create a new thread and handle successes
		// and failures from the response
		if (categoryID != null) {
			JSONObject threadResponse = serverDAO.editThread(
					categoryID.toString(), topicID, threadName, threadBody,
					session.getUserDetails().get("session"));

			String message = (String) threadResponse.get("message");
			Toast msg = Toast.makeText(this, message, Toast.LENGTH_LONG);
			msg.show();

			if (threadResponse.get("success").equals(true)) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			} else {
				// Clear the form
				categoryText.setText("");
				topicText.setText("");
				threadText.setText("");
				threadBodyText.setText("");
			}
		} else {
			Toast msg = Toast.makeText(this, "category not exist",
					Toast.LENGTH_LONG);
			msg.show();
		}
	}
*/
}