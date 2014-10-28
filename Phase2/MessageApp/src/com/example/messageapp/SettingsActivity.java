package com.example.messageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import appControl.CSC301ConnectionManager;

public class SettingsActivity extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
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
