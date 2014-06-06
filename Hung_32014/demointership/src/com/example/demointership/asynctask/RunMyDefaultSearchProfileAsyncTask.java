package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;

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
			RestaurantsObject[] response = new Gson().fromJson(
					Server.getJSON(Server.requestGet(ServerURL.URL
							+ ServerURL.getKeyRunMyDefaultsearch()
							+ "access_token=" + params[0] + "&latitude="
							+ params[1] + "&longitude=" + params[2]
							+ "&search_profile_id=" + params[3])),
					RestaurantsObject[].class);
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
