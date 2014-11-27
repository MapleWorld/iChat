package com.example.messageapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import appControl.DAO;
import appControl.Session;

public class MainActivity extends Activity implements OnItemClickListener {
	private Session session;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mListView;
	private String[] mDrawerItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		session = new Session(getApplicationContext());
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		// If this is an admin user, load the admin controls
		if (session.isAdmin()) {
			mDrawerItems = getResources().getStringArray(R.array.admin_drawer_array);
		} else {
			mDrawerItems = getResources().getStringArray(R.array.drawer_array);
		}
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mDrawerItems));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerList.setItemChecked(0, true);
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		// Display the list of categories.
		mListView = (ListView) findViewById(R.id.main_list_view);
		
		try {
			DAO response = new DAO();
			JSONObject result = response.getServerResponseContent("/categories");
			JSONArray results = result.getJSONArray("categories");
			ArrayList<String> list = new ArrayList<String>();

			for (int i = 0; i < results.length(); i++) {
				JSONObject o = results.getJSONObject(i);
				list.add(o.getString("name"));
			}

			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, list);

			mListView.setAdapter(arrayAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ListView allItems = (ListView) findViewById(R.id.main_list_view);
		String categoryName = allItems.getItemAtPosition(position).toString();
		DAO findThreads = new DAO();
		JSONObject threads = null;
		
		try {
			threads = findThreads.getThreadsByCategoryName(categoryName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (threads != null) {
			Intent intent = new Intent(this, ViewListThreadActivity.class);
			intent.putExtra("thread", threads.toString());
			startActivity(intent);
		} else {
			Toast msg = Toast.makeText(this, "Failed For Some Reason",
					Toast.LENGTH_LONG);
			msg.show();
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        
        // Handle action buttons
        switch(item.getItemId()) {
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
	/**
	 *  The click listener for ListView in the navigation drawer
	 */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		mDrawerList.setItemChecked(0, true);
		mDrawerLayout.closeDrawer(mDrawerList);

		Intent intent = null;

		switch (mDrawerItems[position]) {
		case "Subscriptions":
			intent = new Intent(this, ViewSubscriptionsActivity.class);
			break;
		case "Topics":
			intent = new Intent(this, ViewTopicListActivity.class);
			break;
		case "Threads":
			// Authenticate the user account with the server
			DAO thread = new DAO();
			JSONObject allThreads = null;

			try {
				allThreads = thread.getAllThreads();
				intent = new Intent(this, ViewListThreadActivity.class);
				intent.putExtra("thread", allThreads.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case "New Category":
			intent = new Intent(this, CreateCategoryActivity.class);
			break;
		case "New Thread":
			intent = new Intent(this, CreateThreadActivity.class);
			break;
		case "New Topic":
			intent = new Intent(this, CreateTopicActivity.class);
			break;
		case "Find Thread":
			intent = new Intent(this, FindThreadActivity.class);
			break;
		case "Ban User":
			intent = new Intent(this, BanUserActivity.class);
			break;
		case "Logout":
			DAO logout = new DAO();
			JSONObject result;

			try {
				result = logout.logoutUser(session.getUserDetails().get(
						"session"));

				String message = result.getString("message");
				Toast msg = Toast.makeText(this, message, Toast.LENGTH_LONG);
				msg.show();

				session.logoutUser();
				intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		}
		
		if (intent != null) {
			startActivity(intent);
		}
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
