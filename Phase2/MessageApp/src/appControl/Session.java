package appControl;

import java.util.HashMap;

import com.example.messageapp.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Session {

	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared preference mode
	int PRIVATE_MODE = 0;

	// Shared preference file name
	private static final String PREF_NAME = "AndroidHivePref";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";

	// Email address (make variable public to access from outside)
	public static final String KEY_SESSION = "session";
	
	public static final String KEY_ADMIN = "isAdmin";
	
	public static final String KEY_USERID = "userID";

	// Constructor
	public Session(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(String name, String sessionID, boolean isAdmin, long userID) {
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		// Storing name in preference
		editor.putString(KEY_NAME, name);

		// Storing email in preference
		editor.putString(KEY_SESSION, sessionID);
		
		editor.putBoolean(KEY_ADMIN, isAdmin);
		
		editor.putLong(KEY_USERID, userID);

		// commit changes
		editor.commit();
	}

	/**
	 * Check login method will check user login status If false it will redirect
	 * user to login page Else won't do anything
	 * */
	public void checkLogin() {
		// Check login status
		if (!this.isLoggedIn()) {
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}

	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));

		// user email id
		user.put(KEY_SESSION, pref.getString(KEY_SESSION, null));

		// return user
		return user;
	}

	public long getUserID() {
		return pref.getLong("userID", 0);
	}
	
	/**
	 * Clear session details
	 * */
	public void logoutUser() {
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();

		// After logout redirect user to Login Activity
		Intent i = new Intent(_context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		_context.startActivity(i);
	}

	public boolean isAdmin() {
		return pref.getBoolean(KEY_ADMIN, false);
	}
	
	public boolean isLoggedIn() {
		return pref.getBoolean(IS_LOGIN, false);
	}
}
