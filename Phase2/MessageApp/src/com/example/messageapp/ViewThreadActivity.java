package com.example.messageapp;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import appControl.CSC301Reply;
import appControl.CSC301Thread;
import appControl.DAO;
import appControl.Session;

public class ViewThreadActivity extends Activity {

	Session session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_thread);
	}

	@Override
	protected void onResume() {
		super.onResume();
		session = new Session(getApplicationContext());
		
		((LinearLayout) findViewById(R.id.viewThreadReplyLayout)).removeAllViews();
		
		displayThread();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Display a thread. 
	 */
	public void displayThread() {
		Intent intentN = getIntent();
		String threadString = intentN.getStringExtra("threadID");
		JSONObject jo;
		CSC301Thread threadData;
		CSC301Reply[] replyData;
		LinearLayout repliesLayout;
		LinearLayout singleReplyLayout;
		TextView replyAuthor;
		TextView replyBody;
		TextView replyTimestamp;
		Button btn;
		JSONObject result;
		
		DAO dao = new DAO();
		
		try {
			result = dao.getServerResponseContent("/threads/view/"+ threadString + "/1");
		} catch(Exception e) {
			result = null;
			Toast.makeText(this, "Error connecting to server", Toast.LENGTH_LONG).show();
		}

		if (result == null) {
			Toast.makeText(this, "Error downloading thread", Toast.LENGTH_LONG).show();
		} else {
			try {
				jo = result;
				threadData = new CSC301Thread(jo);
				replyData = (CSC301Reply[]) threadData.getReplies();
				
				repliesLayout = (LinearLayout) findViewById(R.id.viewThreadReplyLayout);
				
				((TextView) findViewById(R.id.viewThreadTitleText)).setText(jo.getString("title"));
				((TextView) findViewById(R.id.viewThreadUsernameText)).setText("Posted by: "+jo.getString("username"));
				((TextView) findViewById(R.id.viewThreadTimestampText)).setText("Posted at: "+jo.getString("timestamp"));
				((TextView) findViewById(R.id.viewThreadBodyText)).setText(jo.getString("body"));
				
				for(int idx = 0; idx < replyData.length; idx++) {
					singleReplyLayout = new LinearLayout(this);
					singleReplyLayout.setOrientation(LinearLayout.VERTICAL);
					
					replyAuthor = new TextView(this);
					replyAuthor.setText(replyData[idx].getUsername());
					
					replyBody = new TextView(this);
					replyBody.setText(replyData[idx].getBody());
					
					replyTimestamp = new TextView(this);
					replyTimestamp.setText(replyData[idx].getTimestamp());
					
					singleReplyLayout.addView(replyAuthor);
					singleReplyLayout.addView(replyTimestamp);
					singleReplyLayout.addView(replyBody);
					
					if(session.isAdmin()) {
						btn = new Button(this);
						
						btn.setText("^ Delete ^");
						btn.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								Toast.makeText(getApplicationContext(), "Not implemented", Toast.LENGTH_LONG).show();
							}
						});
						singleReplyLayout.addView(btn);
					}
					
					if(replyData[idx].getUserid() == session.getUserID()) {
						btn = new Button(this);
						btn.setTag(R.string.editReplyBodyTag, replyData[idx].getBody());
						btn.setTag(R.string.editReplyIDTag, replyData[idx].getReplyID());
						
						btn.setText("^ Edit ^");
						btn.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(getApplicationContext(), EditReplyActivity.class);
								intent.putExtra("replyBody", (String) v.getTag(R.string.editReplyBodyTag));
								intent.putExtra("replyID", (String) Long.toString((Long)v.getTag(R.string.editReplyIDTag)));
								startActivity(intent);
							}
						});
						singleReplyLayout.addView(btn);
					}
					
					repliesLayout.addView(singleReplyLayout);
					
				}
				
			} catch (Exception e) {
				Log.e("com.example.messageapp", "exception", e);
				Toast.makeText(this, "Error rendering thread", Toast.LENGTH_LONG).show();
			}
		}

	}

}
