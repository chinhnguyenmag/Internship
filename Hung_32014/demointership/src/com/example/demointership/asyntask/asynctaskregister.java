package com.example.demointership.asyntask;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.model.UserDetail;
import com.google.gson.Gson;

public class asynctaskregister extends AsyncTask<String, Void, UserDetail> {
	private ProgressDialog mDialog;
	private Activity mContext;

	public asynctaskregister(Activity context) {
		this.mContext = context;
	}

	@Override
	protected UserDetail doInBackground(String... params) {
		StringEntity stringEntity = null;
		JSONObject obj = new JSONObject();
		try {
			obj.put("username", params[0]);
			obj.put("email", params[1]);
			obj.put("password", params[2]);
			obj.put("firstname", params[3]);
			obj.put("lastname", params[4]);
			obj.put("zipcode", params[5]);
			stringEntity = new StringEntity(obj.toString(), "UTF-8");
		} catch (Exception e) {

		}

		UserDetail responseReg = new Gson().fromJson(Server.getJSON(Server
				.requestPost(ServerURL.URL + ServerURL.getKeySignUp(),
						stringEntity)), UserDetail.class);
		return responseReg;
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

	@Override
	protected void onProgressUpdate(Void... values) {

		super.onProgressUpdate(values);
	}

}
