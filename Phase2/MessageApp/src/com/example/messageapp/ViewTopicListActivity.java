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

public class ViewTopicListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_topic_list);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_topic_list, menu);
		return true;
	}

	public void ShowTopicList(View v) throws Exception {

		EditText categoryText = (EditText) findViewById(R.id.category_name2);
		String categoryName = categoryText.getText().toString();
		Integer categoryID = null;

		DAO response = new DAO();
		JSONObject result = response.getServerResponseContent("/categories");
		JSONArray results = result.getJSONArray("categories");

		for (int i = 0; i < results.length(); i++) {
			JSONObject o = results.getJSONObject(i);
			if (o.getString("name").equals(categoryName)) {
				categoryID = o.getInt("id");
				break;
			}
		}

		if (categoryID != null) {
			Intent intent = new Intent(this,
					ViewTopicListByCategoryActivity.class);
			intent.putExtra("catID", categoryID.toString());
			startActivity(intent);
		} else {
			Toast msg = Toast.makeText(this, "category not exist",
					Toast.LENGTH_LONG);
			msg.show();

		}

	}

}
