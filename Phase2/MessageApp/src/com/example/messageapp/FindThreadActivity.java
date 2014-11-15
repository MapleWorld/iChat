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

public class FindThreadActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_thread);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void findThreadByThreadID(View v) throws Exception {
		EditText idText = (EditText) findViewById(R.id.input_id);
		String inputID = idText.getText().toString();

		Intent intent = new Intent(this, ViewThreadActivity.class);
		intent.putExtra("threadID", inputID);
		startActivity(intent);
	}

	public void findThreadByCategoryName(View v) throws Exception {
		EditText idText = (EditText) findViewById(R.id.input_category_name);
		String categoryName = idText.getText().toString();

		// Perform a POST request to get a list of threads for the given
		// category ID and display the list of threads
		DAO findAllThreadsInThisCategory = new DAO();

		JSONObject threads = new JSONObject();
		threads = findAllThreadsInThisCategory
				.getThreadsByCategoryName(categoryName);

		Intent intent = new Intent(this, ViewListThreadActivity.class);
		intent.putExtra("thread", threads.toString());
		startActivity(intent);

	}

	public void findThreadByTopicID(View v) throws Exception {
		EditText idText = (EditText) findViewById(R.id.input_id);
		String inputID = idText.getText().toString();

		// Perform a POST request to get a list of threads for the given
		// topic ID and display the list of threads
		DAO login = new DAO();
		JSONObject result = login.getServerResponseContent("/threads/by_topic/"
				+ inputID + "/1");

		if (result != null) {
			Intent intent = new Intent(this, ViewListThreadActivity.class);
			intent.putExtra("thread", result.toString());
			startActivity(intent);
		} else {
			// Clear the form and display an error notification if the thread
			// cannot be found
			idText.setText("");
			Toast msg = Toast.makeText(this,
					"Couldn't Find Thread With Given ID", Toast.LENGTH_LONG);
			msg.show();
		}
	}

}