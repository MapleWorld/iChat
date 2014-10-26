package com.example.messageapp;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import appControl.DAO;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	public void openSettings(MenuItem item) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void register(View v) throws Exception {

		EditText userNameText = (EditText) findViewById(R.id.user_name);
		EditText userPasswordText = (EditText) findViewById(R.id.user_password);

		String userName = userNameText.getText().toString();
		String userPassword = userPasswordText.getText().toString();

		// Validate the inputs
		// Store the inputs into database
		DAO createAccount = new DAO();
		
		JSONObject result = createAccount.createUser(userName, userPassword);

		if(result != null) {
			if (result.get("success").equals(true)) {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				userNameText.setText("");
				userPasswordText.setText("");
				String message = (String) result.get("message");
				Toast msg = Toast.makeText(this, message, Toast.LENGTH_LONG);
				msg.show();
			}
		} else {
			Toast msg = Toast.makeText(this, "Error communicating with server", Toast.LENGTH_LONG);
			msg.show();
		}
		
	}
}
