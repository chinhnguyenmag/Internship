package com.example.demointership.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;

public class Utils {
	public static String TAG = "Utils";
	public static final int DEFAULT_BUFFER_SIZE = 8192;
	private static String formatEmail = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,4}$";

	public static boolean isJellyBeanOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	public static boolean isICSOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	public static boolean isHoneycombOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean isGingerbreadOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	public static boolean isFroyoOrHigher() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/**
	 * Check SdCard
	 * 
	 * @return
	 */
	public static boolean isExtStorageAvailable() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * Check internet
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null) {
			return activeNetworkInfo.isConnected();
		}
		return false;
	}

	/**
	 * Check wifi
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo != null) {
			return wifiNetworkInfo.isConnected();
		}
		return false;
	}

	/**
	 * Check on/off gps
	 * 
	 * @return
	 */
	public static boolean checkAvailableGps(Context context) {
		LocationManager manager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	/**
	 * Download data url
	 * 
	 * @param urlString
	 * @return
	 * @throws IOException
	 */
	public static InputStream downloadUrl(String urlString) throws IOException {
		HttpURLConnection conn = buildHttpUrlConnection(urlString);
		conn.connect();

		InputStream stream = conn.getInputStream();
		return stream;
	}

	/**
	 * Returns an {@link HttpURLConnection} using sensible default settings for
	 * mobile and taking care of buggy behavior prior to Froyo.
	 */
	public static HttpURLConnection buildHttpUrlConnection(String urlString)
			throws MalformedURLException, IOException {
		Utils.disableConnectionReuseIfNecessary();

		URL url = new URL(urlString);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setDoInput(true);
		conn.setRequestMethod("GET");
		return conn;
	}

	/**
	 * Prior to Android 2.2 (Froyo), {@link HttpURLConnection} had some
	 * frustrating bugs. In particular, calling close() on a readable
	 * InputStream could poison the connection pool. Work around this by
	 * disabling connection pooling.
	 */
	public static void disableConnectionReuseIfNecessary() {
		// HTTP connection reuse which was buggy pre-froyo
		if (!isFroyoOrHigher()) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	/**
	 * Check an email is valid or not
	 * 
	 * @param email
	 *            the email need to check
	 * @return {@code true} if valid, {@code false} if invalid
	 */
	public static boolean isValidEmail(Context context, String email) {
		boolean result = false;
		Pattern pt = Pattern.compile(formatEmail);
		Matcher mt = pt.matcher(email);
		if (mt.matches())
			result = true;
		return result;
	}
}
