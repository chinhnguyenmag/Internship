package com.example.demointership.asyntask;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.model.UserDetail;
import com.google.gson.Gson;

public class asynctasklogin extends AsyncTask<String, Void, UserDetail> {
	private ProgressDialog mDialog;
	private Activity mContext;

	public asynctasklogin(Activity context) {
		this.mContext = context;
	}

	@Override
	protected void onPostExecute(UserDetail result) {
		mDialog.dismiss();
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();

		//mDialog = ProgressDialog.show(mContext, " ", "Loading...");
		mDialog = new ProgressDialog(mContext);
		mDialog.setMessage("Loading...");
		mDialog.show();
	}

	@Override
	protected void onProgressUpdate(Void... values) {

		super.onProgressUpdate(values);
	}

	@Override
	protected UserDetail doInBackground(String... params) {
		String email = params[0];
		String username = params[1];
		String password = params[2];
		JSONObject jObject = new JSONObject();
		StringEntity stringEntity = null;
		if (email != null) {
			try {
				jObject.put("email", email);
				jObject.put("password", password);
				stringEntity = new StringEntity(jObject.toString(), "UTF-8");
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {
			try {
				jObject.put("username", username);
				jObject.put("password", password);
				stringEntity = new StringEntity(jObject.toString(), "UTF-8");
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		try {

			UserDetail userdetail = new Gson().fromJson(Server.getJSON(Server
					.requestPost(ServerURL.URL + ServerURL.getKeyLoginNormal(),
							stringEntity)), UserDetail.class);
			return userdetail;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
