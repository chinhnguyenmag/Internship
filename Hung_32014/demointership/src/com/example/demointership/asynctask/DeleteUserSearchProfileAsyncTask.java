package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.listener.DeleteUserSearchProfileListener;
import com.example.demointership.model.ResponseObject;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class DeleteUserSearchProfileAsyncTask extends
		AsyncTask<String, Void, Boolean> {
	WeakReference<Context> mContext;
	WeakReference<DeleteUserSearchProfileListener> mListener;
	ProgressDialog mDialog;

	public DeleteUserSearchProfileAsyncTask(Context context,
			DeleteUserSearchProfileListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<DeleteUserSearchProfileListener>(
				listener);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		try {
			JSONObject jObject = new JSONObject();
			jObject.put("access_token", params[0]);
			jObject.put("search_profile_id", Integer.parseInt(params[1]));
			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			ResponseObject response = new Gson().fromJson(Server.getJSON(Server
					.requestPost(ServerURL.URL + ServerURL.getKeyGetDeletesearchprofile(),
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
		DeleteUserSearchProfileListener listener = mListener.get();
		if (result) {
			listener.onDeleteUserSearchProfileListenerComplete();
		} else {
			listener.onDeleteUserSearchProfileListenerFailed();
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
