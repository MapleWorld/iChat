package com.example.messageapp;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import appControl.DAO;
import appControl.Session;

public class EditReplyActivity extends Activity {

	Session session;
	String replyBody;
	long replyID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_reply);
		
		session = new Session(getApplicationContext());
		
		replyBody = getIntent().getStringExtra("replyBody");
		replyID = Long.parseLong(getIntent().getStringExtra("replyID"));
		
		((EditText) findViewById(R.id.editReplyBodyText)).setText(replyBody);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_reply, menu);
		return true;
	}

	public void onSubmitEdit(View v) {
		DAO dao = new DAO();
		String newBody = ((EditText) findViewById(R.id.editReplyBodyText)).getText().toString();
		
		if (dao.editReply(newBody, replyID, session.getSessionID())) {
			Toast.makeText(this, "Reply edited successfully", Toast.LENGTH_LONG).show();
			finish();
		} else {
			Toast.makeText(this, "Error editing reply", Toast.LENGTH_LONG).show();
		}
	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
