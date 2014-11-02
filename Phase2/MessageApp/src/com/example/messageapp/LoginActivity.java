package com.example.messageapp;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

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
import appControl.Session;

public class LoginActivity extends Activity {

	// Session Manager Class
	Session session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Initialize server connection settings
		CSC301ConnectionManager connMgr = CSC301ConnectionManager.getInstance();

		String serverAddress = PreferenceManager.getDefaultSharedPreferences(
				this).getString("pref_server_address", "10.0.2.2:8080");
		boolean serverIsHTTPS = PreferenceManager.getDefaultSharedPreferences(
				this).getBoolean("pref_server_https", false);
		String serverProtocol = serverIsHTTPS ? "https://" : "http://";

		connMgr.setUseHTTPS(serverIsHTTPS);
		connMgr.setServerURL(serverProtocol + serverAddress);

		// Initialize session manager
		session = new Session(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.action_settings:
				openSettings(item);
				return true;
			default:
				return false;
		}
	}
	
	public void login(View v) throws InterruptedException, ExecutionException,
			JSONException {
		EditText userNameText = (EditText) findViewById(R.id.user_name);
		EditText userPasswordText = (EditText) findViewById(R.id.user_password);

		String userName = userNameText.getText().toString();
		String userPassword = userPasswordText.getText().toString();

		// Authenticate the user account with the server
		DAO login = new DAO();
		JSONObject result = login.loginAccount(userName, userPassword);

		if (result.get("success").equals(true)) {
			Intent intent = new Intent(this, MainActivity.class);
			session.createLoginSession(userName, result.getString("SESSIONID"));
			startActivity(intent);
		} else {
			// Clear the form and display a notification if the user failed to login
			userNameText.setText("");
			userPasswordText.setText("");
			String message = (String) result.get("message");
			Toast msg = Toast.makeText(this, message, Toast.LENGTH_LONG);
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