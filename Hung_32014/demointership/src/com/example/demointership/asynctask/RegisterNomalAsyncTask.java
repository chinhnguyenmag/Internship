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
import com.example.demointership.listener.RegisterNomalListener;
import com.example.demointership.model.UserProfileObject;
import com.google.gson.Gson;

public class RegisterNomalAsyncTask extends AsyncTask<String, Void, Boolean> {
	WeakReference<Context> mContext;
	WeakReference<RegisterNomalListener> mListener;
	ProgressDialog mDialog;

	public RegisterNomalAsyncTask(Context context,
			RegisterNomalListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<RegisterNomalListener>(listener);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		Context context = mContext.get();
		try {
			JSONObject jObject = new JSONObject();
			jObject.put("username", params[0]);
			jObject.put("email", params[1]);
			jObject.put("password", params[2]);
			jObject.put("first_name", params[3]);
			jObject.put("last_name", params[4]);
			jObject.put("zipcode", params[5]);
			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			UserProfileObject response = new Gson().fromJson(Server
					.getJSON(Server.requestPost(
							ServerURL.URL + ServerURL.getKeySignUp(),
							stringEntity)), UserProfileObject.class);
			if (response.getStatus().equals("success")) {
				SharedPreferences sp = context.getSharedPreferences(
						Constants.KEY_CURRENT_USER_XML, 0);
				Editor editor = sp.edit();
				editor.putString("access_token", response.getAccess_token());
				editor.putInt("id", response.getId());
				editor.putString("city", response.getCity());
				editor.putString("state", response.getState());
				editor.putInt("points", response.getPoints());
				editor.putString("dinner_status", response.getDinner_status());
				editor.commit();
			} else {
				result = false;
				SharedPreferences sp = context.getSharedPreferences(
						Constants.KEY_CURRENT_USER_XML, 0);
				Editor editor = sp.edit();
				String st = "email " + response.getErrors().email
						+ " username " + response.getErrors().username;
				editor.putString("errors", st);
				editor.commit();
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		RegisterNomalListener listener = mListener.get();
		if (result) {
			listener.onRegisterNomalListenerComplete();
		} else {
			listener.onRegisterNomalListenerFailed();
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
