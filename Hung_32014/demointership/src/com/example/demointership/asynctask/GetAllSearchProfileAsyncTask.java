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
import com.example.demointership.listener.GetAllSearchProfileListener;
import com.example.demointership.model.SearchProfileObject;
import com.google.gson.Gson;

public class GetAllSearchProfileAsyncTask extends
		AsyncTask<String, Void, Boolean> {
	WeakReference<Context> mContext;
	WeakReference<GetAllSearchProfileListener> mListener;
	ProgressDialog mDialog;

	public GetAllSearchProfileAsyncTask(Context context,
			GetAllSearchProfileListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<GetAllSearchProfileListener>(
				listener);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		try {
			JSONObject jObject = new JSONObject();
			jObject.put("access_token", params[0]);
			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			SearchProfileObject[] response = new Gson().fromJson(Server
					.getJSON(Server.requestPost(
							ServerURL.URL
									+ ServerURL.getKeyGetAllsearchprofile(),
							stringEntity)), SearchProfileObject[].class);
			if (response != null) {
				Temp.listSearchProfileObject = response;
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
		GetAllSearchProfileListener listener = mListener.get();
		if (result) {
			listener.onGetAllSearchProfileListenerComplete();
		} else {
			listener.onGetAllSearchProfileListenerFailed();
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
