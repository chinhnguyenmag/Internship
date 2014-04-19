package com.example.demointership.asyntask;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.model.UserDetail;
import com.google.gson.Gson;

public class asynctasksociallogin extends AsyncTask<String, Void, UserDetail> {
	Application mContext;
	Dialog mDialog;

	public asynctasksociallogin(Application context) {
		this.mContext = context;
	}

	@Override
	protected UserDetail doInBackground(String... params) {
		StringEntity stringEntity = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("uid", params[0]);
			obj.put("provider", params[1]);
			obj.put("email", params[2]);
			obj.put("username", params[3]);
//			obj.put("last_name", params[4]);
//			obj.put("zip", params[5]);
//			obj.put("address", "");
			stringEntity = new StringEntity(obj.toString(), "UTF-8");
		} catch (Exception e) {

		}

		UserDetail userDetail = new Gson().fromJson(Server.getJSON(Server
				.requestPost(ServerURL.URL + ServerURL.getKeyLoginSocial(),
						stringEntity)), UserDetail.class);
		//UserDetail userDetail = null;
		return userDetail;
	}

	@Override
	protected void onPostExecute(UserDetail result) {
		// TODO Auto-generated method stub
		mDialog.dismiss();
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		mDialog = ProgressDialog.show(mContext, "", "Loading...");
		super.onPreExecute();
	}

}
