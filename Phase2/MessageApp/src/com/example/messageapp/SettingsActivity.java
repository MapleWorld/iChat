package com.example.messageapp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import appControl.CSC301ConnectionManager;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	protected void onResume(){
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		Log.e("com.example.messageapp","calling onSharedPreferenceChanged...!");
		String serverAddress = PreferenceManager.getDefaultSharedPreferences(
				this).getString("pref_server_address", "");
		boolean serverIsHTTPS = PreferenceManager.getDefaultSharedPreferences(
				this).getBoolean("pref_server_https", true);
		String serverProtocol = serverIsHTTPS ? "https://" : "http://";
		CSC301ConnectionManager connMgr = CSC301ConnectionManager.getInstance();

		connMgr.setUseHTTPS(serverIsHTTPS);
		connMgr.setServerURL(serverProtocol + serverAddress);
	}
}
