package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.listener.ForGotPasswordListener;
import com.example.demointership.model.ResponseObject;
import com.google.gson.Gson;

public class ForGotPasswordAsyncTask extends AsyncTask<String, Void, Boolean> {
	WeakReference<Context> mContext;
	WeakReference<ForGotPasswordListener> mListener;
	ProgressDialog mDialog;

	public ForGotPasswordAsyncTask(Context context,
			ForGotPasswordListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<ForGotPasswordListener>(listener);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		try {
			JSONObject jObject = new JSONObject();
			jObject.put("email", params[0]);
			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			ResponseObject response = new Gson().fromJson(Server.getJSON(Server
					.requestPost(ServerURL.URL + ServerURL.getKeyForgot(),
							stringEntity)), ResponseObject.class);
			if (response.getStatus().equals("success")) {

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
		ForGotPasswordListener listener = mListener.get();
		if (result) {
			listener.onForGotPasswordListenerComplete();
		} else {
			listener.onForGotPasswordListenerFailed();
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
