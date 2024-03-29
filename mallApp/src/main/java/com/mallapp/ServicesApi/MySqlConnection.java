package com.mallapp.ServicesApi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.mallapp.Application.MallApplication;
import com.mallapp.Constants.ApiConstants;
import com.mallapp.SharedPreferences.SharedPreferenceUserProfile;
import com.mallapp.utils.StringUtils;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

public class MySqlConnection {

	/** The time it takes for our client to timeout */
	public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds

	/** Single instance of our HttpClient */
	private static HttpClient mHttpClient;

	/**
	 * Get our single instance of our HttpClient object.
	 * 
	 * @return an HttpClient object with connection parameters set
	 */
	private static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
			final HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return mHttpClient;
	}

	/**
	 * Performs an HTTP Post request to the specified url with the specified
	 * parameters.
	 * 
	 * @param url
	 *            The web address to post the request to
	 * @param header
	 *            The parameters to send via the request
	 * @return The result of the request
	 * @throws Exception
	 */

	public static String executeHttpPost(String url, JSONObject header) throws Exception {
		BufferedReader in = null;
		String result = "";
		//
		Log.w("Sendiong request...." , url);

		try { 
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(url);
			
			StringEntity entity = new StringEntity(header.toString(), HTTP.UTF_8);
			entity.setContentType("application/json");
			request.setEntity(entity);

			HttpResponse response = client.execute(request);
			HttpEntity httpEntity = response.getEntity();
			
		    if (httpEntity != null) {
		        InputStream is = httpEntity.getContent();
		        result = StringUtils.convertStreamToString(is);
		        
		        //jsonObj1  = new JSONObject(result);
				Log.i("", "Result: " + result);
				//Log.w("", "Result: " + jsonObj1.toString());
		    }
		    
			return result;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * Performs an HTTP GET request to the specified url.
	 * 
	 * @param url
	 *            The web address to post the request to
	 * @return The result of the request
	 * @throws Exception
	 */
	public static String executeHttpGet(String url) throws Exception {
		BufferedReader in = null;
		String result = "";

		try {
			HttpClient client = getHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");

			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}

			in.close();
			result = sb.toString();
			return result;

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public static String executeHttpPostWithHeader(String url, JSONObject header, Context context) throws Exception {
		BufferedReader in = null;
		String result = "";
		//
		Log.w("Sendiong request...." , url);

		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);

			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(url);
			request.addHeader("Auth-Token", SharedPreferenceUserProfile.getUserToken(context));
			StringEntity entity = new StringEntity(header.toString(), HTTP.UTF_8);
			entity.setContentType("application/json");
			request.setEntity(entity);

			HttpResponse response = client.execute(request);
			HttpEntity httpEntity = response.getEntity();

			if (httpEntity != null) {
				InputStream is = httpEntity.getContent();
				result = StringUtils.convertStreamToString(is);

				//jsonObj1  = new JSONObject(result);
				Log.i("", "Result: " + result);
				//Log.w("", "Result: " + jsonObj1.toString());
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	// HTTP POST request
	public static  String sendPost(JSONObject jsonObject) throws Exception {

		String url = ApiConstants.SAVE_LOYALTY_CARD;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Auth-Token", SharedPreferenceUserProfile.getUserToken(MallApplication.appContext));
		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonObject.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		return response.toString();
	}


}
