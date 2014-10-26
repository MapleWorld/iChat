package com.example.messageapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
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

	public void logoutUser(View v) {
		session.logoutUser();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

}
