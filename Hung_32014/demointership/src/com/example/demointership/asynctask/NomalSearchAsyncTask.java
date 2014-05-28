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
import com.example.demointership.listener.NomalSearchListener;
import com.example.demointership.model.RestaurantsObject;
import com.google.gson.Gson;

public class NomalSearchAsyncTask extends AsyncTask<String, Void, Boolean> {
	WeakReference<Context> mContext;
	WeakReference<NomalSearchListener> mListener;
	ProgressDialog mDialog;
	public NomalSearchAsyncTask(Context context, NomalSearchListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<NomalSearchListener>(listener);
	}
	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		try {
			JSONObject jObject = new JSONObject();
			jObject.put("access_token", params[0]);
			jObject.put("latitude", params[1]);
			jObject.put("longitude", params[2]);
			jObject.put("name", params[3]);
			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			RestaurantsObject[] response = new Gson().fromJson(Server.getJSON(Server
					.requestPost(ServerURL.URL + ServerURL.getKeyForgot(),
							stringEntity)), RestaurantsObject[].class);
			if(response == null) {
				result = false;
			} else {
				Temp.listRestaurantObject = response;
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		NomalSearchListener listener = mListener.get();
		if (result) {
			listener.onNomalSearchListenerComplete();
		} else {
			listener.onNomalSearchListenerFailed();
		}
		mDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mDialog = new ProgressDialog(mContext.get());
		mDialog.setMessage("loading");
		mDialog.show();
	}

}
