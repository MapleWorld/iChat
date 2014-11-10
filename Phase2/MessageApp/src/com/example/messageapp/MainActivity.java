package com.example.messageapp;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.LinearLayout;
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
		
		//If this is an admin user, load the admin controls
		if(session.isAdmin()) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.admin_buttons);
			
			Button banUserButton = new Button(this);
			
			banUserButton.setText("Ban user");
			banUserButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), BanUserActivity.class);
					startActivity(intent);
				}
			});
			
			layout.addView(banUserButton);
			
			//setContentView(layout);
			
		}
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
