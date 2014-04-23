package com.example.demointership.asyntask;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.demointership.Util.Constants;
import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.model.UserDetail;
import com.google.gson.Gson;

public class asynctasksociallogin extends AsyncTask<String, Void, UserDetail> {
	Activity mContext;
	Dialog mDialog;

	public asynctasksociallogin(Activity context) {
		this.mContext = context;
	}

	@Override
	protected UserDetail doInBackground(String... params) {
		StringEntity stringEntity = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("uid", params[0]);
			String typeLog = params[1];
			if (typeLog.equals(Constants.PROVIDER_FACEBOOK)) {
				obj.put("provider", Constants.PROVIDER_FACEBOOK);
			}
			if (typeLog.equals(Constants.PROVIDER_TWITTER)) {
				obj.put("provider", Constants.PROVIDER_TWITTER);
				obj.put("username", params[2]);
			}
			if (typeLog.equals(Constants.PROVIDER_GOOGLE)) {
				obj.put("provider", Constants.PROVIDER_GOOGLE);
			}
			stringEntity = new StringEntity(obj.toString(), "UTF-8");
		} catch (Exception e) {

		}

		UserDetail userDetail = new Gson().fromJson(Server.getJSON(Server
				.requestPost(ServerURL.URL + ServerURL.getKeyLoginSocial(),
						stringEntity)), UserDetail.class);
		return userDetail;
	}

	@Override
	protected void onPostExecute(UserDetail result) {
		mDialog.dismiss();
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		mDialog = ProgressDialog.show(mContext, "", "Loading...");
		super.onPreExecute();
	}

}
