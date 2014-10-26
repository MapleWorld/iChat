package com.example.messageapp;

import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import appControl.DAO;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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

}
