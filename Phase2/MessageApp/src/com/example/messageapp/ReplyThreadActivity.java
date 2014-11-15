package com.example.messageapp;

import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import appControl.DAO;
import appControl.Session;

public class ReplyThreadActivity extends Activity {

	Session session;
	String threadID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply_thread);
		
		session = new Session(getApplicationContext());
		threadID = getIntent().getStringExtra("threadID");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_reply, menu);
		return true;
	}

	public void onSubmitReply(View v) throws Exception {
		EditText replyText = (EditText) findViewById(R.id.replyBodyText);
		String replyBody = replyText.getText().toString();
		
		DAO dao = new DAO();
		JSONObject replyResponse = dao.replyThread(replyBody, threadID, session.getSessionID());
		
		String message = (String) replyResponse.get("message");
		Toast msg = Toast.makeText(this, message, Toast.LENGTH_LONG);
		msg.show();
		
		if (replyResponse.get("success").equals(true)) {
			finish();
		} else {
			replyText.setText("");
		}
	}
	
}
