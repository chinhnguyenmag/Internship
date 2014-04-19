package com.example.demointership.asyntask;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.model.UserDetail;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class asynctasksocialregister extends
		AsyncTask<String, Void, UserDetail> {

	private ProgressDialog mDialog;
	private Activity mContext;

	public asynctasksocialregister(Activity context) {
		this.mContext = context;
	}

	@Override
	protected void onPostExecute(UserDetail result) {
		mDialog.dismiss();
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		mDialog = ProgressDialog.show(mContext, "", "...");
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
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
			obj.put("password", params[4]);
			obj.put("first_name", params[5]);
			obj.put("last_name", params[6]);

			stringEntity = new StringEntity(obj.toString(), "UTF-8");
		} catch (Exception e) {

		}

		UserDetail responseReg = new Gson().fromJson(Server.getJSON(Server
				.requestPost(ServerURL.URL + ServerURL.getKeySignUpSocial(),
						stringEntity)), UserDetail.class);
		return responseReg;
	}

}
