package com.example.messageapp;



import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import appControl.DAO;
import appControl.Session;

public class CreateCategoryActivity extends Activity {
	Session session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_category);
		// Session class instance
		session = new Session(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_category, menu);
		return true;
	}
	
	public void createCategory(View v) throws Exception{
		EditText categoryText = (EditText) findViewById(R.id.category_name);

		String categoryName = categoryText.getText().toString();
		DAO serverDAO = new DAO();
		// Perform POST request to create a new category and handle successes
		// and failures from the response
		JSONObject result = serverDAO.createCategory(categoryName, 
				session.getUserDetails().get("session"));

		String message = (String) result.get("message");
		Toast msg = Toast.makeText(this, message, Toast.LENGTH_LONG);
		msg.show();

		if (result.get("success").equals(true)) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		} else {
			// Clear the form
			categoryText.setText("");
		
		} 

	}


}
