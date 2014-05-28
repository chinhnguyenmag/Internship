package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.Util.Temp;
import com.example.demointership.listener.RunMyDefaultSearchProfileListener;
import com.example.demointership.model.RestaurantsObject;
import com.google.gson.Gson;

public class RunMyDefaultSearchProfileAsyncTask extends
		AsyncTask<String, Void, Boolean> {
	WeakReference<Context> mContext;
	WeakReference<RunMyDefaultSearchProfileListener> mListener;
	ProgressDialog mDialog;

	public RunMyDefaultSearchProfileAsyncTask(Context context,
			RunMyDefaultSearchProfileListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<RunMyDefaultSearchProfileListener>(
				listener);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		try {
			JSONObject jObject = new JSONObject();
			jObject.put("access_token", params[0]);
			jObject.put("latitude", Float.parseFloat(params[1]));
			jObject.put("longitude", Float.parseFloat(params[2]));
			jObject.put("search_profile_id", Integer.parseInt(params[3]));
			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			RestaurantsObject[] response = new Gson().fromJson(Server
					.getJSON(Server.requestPost(
							ServerURL.URL
									+ ServerURL.getKeyRunMyDefaultsearch(),
							stringEntity)), RestaurantsObject[].class);
			if (response != null) {
				Temp.listRestaurantObject = response;
			} else {
				result = false;
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		RunMyDefaultSearchProfileListener listener = mListener.get();
		if (result) {
			listener.onRunMyDefaultSearchProfileListenerComplete();
		} else {
			listener.onRunMyDefaultSearchProfileListenerFailed();
		}
		mDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mDialog = new ProgressDialog(mContext.get());
		mDialog.setMessage("Loading");
		mDialog.show();
	}

}
