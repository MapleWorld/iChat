package com.example.messageapp;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import appControl.DAO;
import appControl.Session;

public class EditThreadActivity extends Activity {

	Session session;
	long threadID;
	String bodyText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_thread);
		session = new Session(getApplicationContext());
		
		threadID = Long.parseLong(getIntent().getStringExtra("threadID"));
		bodyText = getIntent().getStringExtra("bodyText");
		
		((EditText) findViewById(R.id.thread_body)).setText(bodyText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void editThreadBody(View v) {
		EditText threadBodyText = (EditText) findViewById(R.id.thread_body);
		String threadBody = threadBodyText.getText().toString();
		
		DAO dao = new DAO();
		
		JSONObject resp = new JSONObject();
		
		resp = dao.editThreadBody(threadID, threadBody, session.getSessionID());
		
		if (resp != null) {
			try {
				if(resp.getBoolean("success")) {
					Toast.makeText(this, "Thread body edited successfully", Toast.LENGTH_LONG).show();
					finish();

				} else {
					Toast.makeText(this, "Error editing thread body", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				Toast.makeText(this, "Error communicating with server", Toast.LENGTH_LONG).show();

			}
		} else {
			Toast.makeText(this, "Error communicating with server", Toast.LENGTH_LONG).show();
		}
	}
	
	public void editThreadTopics(View v){
		Toast.makeText(this, "Not implemented for this phase", Toast.LENGTH_LONG).show();
	}

}