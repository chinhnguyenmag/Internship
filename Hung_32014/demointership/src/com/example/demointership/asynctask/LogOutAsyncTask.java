package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

import com.example.demointership.Util.Constants;
import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.listener.LogOutListener;
import com.example.demointership.model.ResponseObject;
import com.google.gson.Gson;

public class LogOutAsyncTask extends AsyncTask<String, Void, Boolean>{
	WeakReference<Context> mContext;
	WeakReference<LogOutListener> mListener;
	ProgressDialog mDialog;
	
	public LogOutAsyncTask(Context context, LogOutListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<LogOutListener>(listener);
	}
	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		try {
			JSONObject jObject = new JSONObject();
			jObject.put("access_token"	, params[0]);
			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			ResponseObject response = new Gson().fromJson(Server.getJSON(Server
					.requestPost(ServerURL.URL + ServerURL.getKeyLogout(),
							stringEntity)), ResponseObject.class);
			if (response.getStatus().equals("success")) {
				SharedPreferences sp = mContext.get().getSharedPreferences(Constants.KEY_CURRENT_USER_XML, 0);
				Editor editor = sp.edit();
				editor.remove("access_token");
				editor.remove("username");
				
				editor.commit();
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
		LogOutListener listener = mListener.get();
		if (result) {
			listener.onLogOutListenerComplete();
			
		} else {
			listener.onLogOutListenerFailed();
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
