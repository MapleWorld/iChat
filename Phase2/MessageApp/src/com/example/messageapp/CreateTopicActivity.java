package com.example.messageapp;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import appControl.DAO;

public class CreateTopicActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void createTopic(View V) throws Exception{
		EditText categoryText = (EditText) findViewById(R.id.category_name);
		EditText topicText = (EditText) findViewById(R.id.new_topic);

		String categoryName = categoryText.getText().toString();
		String topicName = topicText.getText().toString();
		String categoryID = null;

		DAO response = new DAO();
		JSONObject result = response.getServerResponseContent("http://10.0.2.2:8080/categories");
		JSONArray results = result.getJSONArray("categories");
		for (int i=0; i < results.length(); i++) {
			JSONObject o = results.getJSONObject(i);
			if(o.getString("name") == categoryName){
				categoryID = o.getString("id");
				break;
			}
		}
		if(categoryID != null){
			DAO createTopic = new DAO();
			JSONObject result2 = createTopic.createTopic(categoryID, topicName);

			if (result2.get("success").equals(true)) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			} else {
				categoryText.setText("");
				topicText.setText("");
				String message = (String) result2.get("message");
				Toast msg = Toast.makeText(this, message, Toast.LENGTH_LONG);
				msg.show();
			}
		}else{
			Toast msg = Toast.makeText(this, "category not exist",
					Toast.LENGTH_LONG);
			msg.show();
			
		}
		
	}

}