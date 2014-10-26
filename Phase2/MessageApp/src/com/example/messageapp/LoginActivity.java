package com.example.messageapp;

import com.example.messageapp.RegisterActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import appControl.DAO;
import appControl.CSC301ConnectionManager;

public class LoginActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//This is where we initialize server connection settings for the first time,
		//since LoginActivity is the first to start.
		
		CSC301ConnectionManager connMgr = CSC301ConnectionManager.getInstance();
		
		String serverAddress = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_server_address", "10.0.2.2:8080");
		boolean serverIsHTTPS = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pref_server_https", false);
		String serverProtocol = serverIsHTTPS ? "https://" : "http://";
		
		connMgr.setUseHTTPS(serverIsHTTPS);
		connMgr.setServerURL(serverProtocol + serverAddress);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void login(View v) {

		EditText userNameText = (EditText) findViewById(R.id.user_name);
		EditText userPasswordText = (EditText) findViewById(R.id.user_password);

		String userName = userNameText.getText().toString();
		String userPassword = userPasswordText.getText().toString();

		// Need to check the user account with the server
		DAO login = new DAO();
		boolean checkUserAccount = login.loginAccount(userName,userPassword);

		if (checkUserAccount) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		} else { // Send out a notification
			userNameText.setText("");
			userPasswordText.setText("");
			Toast msg = Toast.makeText(this, "Incorrect Username or Password",
					Toast.LENGTH_LONG);
			msg.show();
		}
		
	}

	public void register(View v) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	
	public void openSettings(MenuItem item) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

}