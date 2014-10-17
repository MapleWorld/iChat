package com.example.messageapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

	public void register(View v) {

		EditText userNameText = (EditText) findViewById(R.id.user_name);
		EditText userPasswordText = (EditText) findViewById(R.id.user_password);
		EditText userEmailText = (EditText) findViewById(R.id.user_email);

		String userName = userNameText.getText().toString();
		String userPassword = userPasswordText.getText().toString();
		String userEmail = userEmailText.getText().toString();

		// Validate the inputs
		// Store the inputs into database
		boolean ValideInputs = true;

		if (ValideInputs) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		} else {
			userNameText.setText("");
			userPasswordText.setText("");
			userEmailText.setText("");
			Toast msg = Toast
					.makeText(this, "Invalide Username or Password or Email",
							Toast.LENGTH_LONG);
			msg.show();
		}
	}
}
