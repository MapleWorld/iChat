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

public class CreateTopicActivity extends Activity {

	Session session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_topic);
		// Session class instance
		session = new Session(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void createTopic(View V) throws Exception {
		EditText categoryText = (EditText) findViewById(R.id.category_name);
		EditText topicText = (EditText) findViewById(R.id.new_topic);

		String categoryName = categoryText.getText().toString();
		String topicName = topicText.getText().toString();

		Integer categoryID = null;

		DAO serverDAO = new DAO();
		JSONObject result = serverDAO.getServerResponseContent("/categories");
		JSONArray results = result.getJSONArray("categories");

		// Get the category ID that matches the given category
		for (int i = 0; i < results.length(); i++) {
			JSONObject o = results.getJSONObject(i);
			if (o.getString("name").equals(categoryName)) {
				categoryID = o.getInt("id");
				break;
			}
		}

		// Perform POST request to create a new topic and handle successes
		// and failures from the response
		if (categoryID != null) {
			JSONObject result2 = serverDAO.createTopic(categoryID.toString(),
					topicName, session.getUserDetails().get("session"));

			String message = (String) result2.get("message");
			Toast msg = Toast.makeText(this, message, Toast.LENGTH_LONG);
			msg.show();

			if (result2.get("success").equals(true)) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			} else {
				// Clear the form
				categoryText.setText("");
				topicText.setText("");
			}
		} else {
			Toast msg = Toast.makeText(this, "category not exist",
					Toast.LENGTH_LONG);
			msg.show();
		}
	}

}