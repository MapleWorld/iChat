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

	// Reads an InputStream and converts it to a String.
	public String readIt(InputStream stream, int len) throws IOException,
			UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

	class sendUser extends AsyncTask<String, String, JSONObject> {

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

				// For some reason, con.getErrorStream() != null would be true
				// even if con.getErrorStream() is in fact null
				// Must print it first, WTF!!!
				System.out.println(con.getErrorStream() != null);

				if (con.getErrorStream() != null) {
					is = con.getErrorStream();
				} else {
					is = con.getInputStream();
				}

				String contentAsString = readIt(is, 500);
				JSONObject jObject = new JSONObject(contentAsString);

				// Check Response Code
				int responseCode = con.getResponseCode();
				jObject.put("response", responseCode);

				return jObject;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("IO Exception");
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.out.println("JSON Exception");
				e.printStackTrace();
			}
			return null;
		}

	}

	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	class downloadUrl extends AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... params) {
			InputStream is = null;
			int len = 500;

			try {
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setReadTimeout(10000 /* milliseconds */);
				conn.setConnectTimeout(15000 /* milliseconds */);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				// Starts the query
				conn.connect();
				int response = conn.getResponseCode();
				is = conn.getInputStream();

				// Convert the InputStream into a string
				String contentAsString = readIt(is, len);
				JSONObject jObject = new JSONObject(contentAsString);

				return jObject;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			return null;
		}

	}

}
