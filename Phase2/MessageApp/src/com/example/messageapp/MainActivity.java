package com.example.messageapp;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import appControl.DAO;
import appControl.Session;

public class MainActivity extends Activity {

	// Session Manager Class
	Session session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Session class instance
		session = new Session(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void viewCategories(View v) {
		Intent intent = new Intent(this, ViewCategoriesActivity.class);
		startActivity(intent);
	}

	public void createTopic(View v) {
		Intent intent = new Intent(this, CreateTopicActivity.class);
		startActivity(intent);
	}

	public void viewTopic(View v) {
		Intent intent = new Intent(this, ViewTopicListActivity.class);
		startActivity(intent);
	}

	public void createThread(View v) {
		Intent intent = new Intent(this, CreateThreadActivity.class);
		startActivity(intent);
	}

	public void findThread(View v) {
		Intent intent = new Intent(this, FindThreadActivity.class);
		startActivity(intent);
	}

	public void logoutUser(View v) throws Exception {
		DAO logout = new DAO();
		JSONObject result = logout.logoutUser(session.getUserDetails().get(
				"session"));

		String message = result.getString("message");
		Toast msg = Toast.makeText(this, message, Toast.LENGTH_LONG);
		msg.show();

		session.logoutUser();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
}
