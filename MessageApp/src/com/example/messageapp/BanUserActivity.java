package com.example.messageapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import appControl.DAO;
import appControl.Session;

public class BanUserActivity extends Activity {

	Session session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ban_user);
		session = new Session(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ban_user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onBanUser(View v) {
		long userID;
		
		userID = Long.parseLong(((EditText) findViewById(R.id.userIDBanText)).getText().toString());
		
		if(new DAO().banUser(userID, session.getUserDetails().get("session"))){
			Toast.makeText(this, "User banned successfully", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "Error banning user", Toast.LENGTH_LONG).show();
		}
	
	}
}
