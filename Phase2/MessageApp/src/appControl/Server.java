package appControl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;

public class Server {

	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	public String downloadUrl(String myurl) throws IOException {
		InputStream is = null;
		int len = 500;

		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			int response = conn.getResponseCode();
			System.out.println("The response is: " + response);
			is = conn.getInputStream();

			// Convert the InputStream into a string
			String contentAsString = readIt(is, len);
			// System.out.println(contentAsString);
			return contentAsString;

			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	// Reads an InputStream and converts it to a String.
	public String readIt(InputStream stream, int len) throws IOException,
			UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

	class createUser extends AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... params) {
			InputStream is = null;
			try {
				// Establish Connection
				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setRequestMethod("POST");
				con.setDoOutput(true);

				// Send post request
				DataOutputStream wr = new DataOutputStream(
						con.getOutputStream());
				wr.writeBytes(params[1]);
				wr.flush();
				wr.close();

				// Check Response Code
				int responseCode = con.getResponseCode();

				if (con.getErrorStream() != null) {
					is = con.getErrorStream();
				} else {
					is = con.getInputStream();
				}

				String contentAsString = readIt(is, 500);
				JSONObject jObject = new JSONObject(contentAsString);
				jObject.put("response", responseCode);

				return jObject;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("failed wtf man");
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.out.println("failed wtf man");
				e.printStackTrace();
			}
			return null;
		}

	}

	protected void onPostExecute(JSONObject data) {
		// do further things with JSONObject as this runs on UI thread.
	}

}
