package com.example.messageapp;

import com.example.messageapp.RegisterActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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
		boolean checkUserAccount = true;

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

}