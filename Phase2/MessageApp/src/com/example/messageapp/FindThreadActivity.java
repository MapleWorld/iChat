package com.example.messageapp;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
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
		JSONObject result = login.getServerResponseContent("/threads/view/"+ 1 + "/1");
		
		if(result != null){
			Intent intent = new Intent(this, ViewThreadActivity.class);
			intent.putExtra("thread", result.toString());
			startActivity(intent);
		} else {
			idText.setText("");
		}
		
	}
	
	public void findThreadByCategoryID(View v) {
		Intent intent = new Intent(this, ViewThreadActivity.class);
		startActivity(intent);
	}
	
	public void findThreadByTopicID(View v) {
		Intent intent = new Intent(this, ViewThreadActivity.class);
		startActivity(intent);
	}

}