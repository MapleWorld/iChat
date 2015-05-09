package com.example.messageapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SubscriptionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscriptions);
	}
	
	public void viewSubscriptions(View v) {
		Intent intent = new Intent(this, ViewSubscriptionsActivity.class);
		startActivity(intent);
	}
}
