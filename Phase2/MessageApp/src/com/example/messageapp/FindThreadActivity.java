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

		DAO login = new DAO();
		JSONObject result = login.getServerResponseContent("/threads/view/"
				+ inputID + "/1");

		if (result != null) {
			Intent intent = new Intent(this, ViewThreadActivity.class);
			intent.putExtra("thread", result.toString());
			startActivity(intent);
		} else {
			idText.setText("");
			Toast msg = Toast.makeText(this,
					"Couldn't Find Thread With Given Thread ID",
					Toast.LENGTH_LONG);
			msg.show();
		}

	}

	public void findThreadByCategoryID(View v) throws Exception {
		EditText idText = (EditText) findViewById(R.id.input_id);
		String inputID = idText.getText().toString();

		DAO login = new DAO();
		JSONObject result = login
				.getServerResponseContent("/threads/by_category/" + inputID
						+ "/1");

		if (result != null) {
			Intent intent = new Intent(this, ViewListThreadActivity.class);
			intent.putExtra("thread", result.toString());
			startActivity(intent);
		} else {
			idText.setText("");
			Toast msg = Toast.makeText(this,
					"Couldn't Find Thread With Given ID", Toast.LENGTH_LONG);
			msg.show();
		}
	}

	public void findThreadByTopicID(View v) throws Exception {
		EditText idText = (EditText) findViewById(R.id.input_id);
		String inputID = idText.getText().toString();

		DAO login = new DAO();
		JSONObject result = login.getServerResponseContent("/threads/by_topic/"
				+ inputID + "/1");

		if (result != null) {
			Intent intent = new Intent(this, ViewListThreadActivity.class);
			intent.putExtra("thread", result.toString());
			startActivity(intent);
		} else {
			idText.setText("");
			Toast msg = Toast.makeText(this,
					"Couldn't Find Thread With Given ID", Toast.LENGTH_LONG);
			msg.show();
		}
	}

}