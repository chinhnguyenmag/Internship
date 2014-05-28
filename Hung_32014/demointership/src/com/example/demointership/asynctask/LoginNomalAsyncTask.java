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
import com.example.demointership.listener.LoginNomalListener;
import com.example.demointership.model.ResponseObject;
import com.google.gson.Gson;

public class LoginNomalAsyncTask extends AsyncTask<String, Void, Boolean> {
	WeakReference<Context> mContext;
	WeakReference<LoginNomalListener> mListener;
	ProgressDialog mDialog;

	public LoginNomalAsyncTask(Context context, LoginNomalListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<LoginNomalListener>(listener);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		try {

			JSONObject jObject = new JSONObject();
			String email = params[0];
			if (email != null) {
				jObject.put("email", email);

			} else {
				jObject.put("username", params[1]);
			}
			jObject.put("password", params[2]);
			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			ResponseObject response = new Gson().fromJson(Server.getJSON(Server
					.requestPost(ServerURL.URL + ServerURL.getKeyLoginNormal(),
							stringEntity)), ResponseObject.class);
			if (response.getStatus().equals("success")) {
				SharedPreferences sp = mContext.get().getSharedPreferences(
						Constants.KEY_CURRENT_USER_XML, 0);
				Editor editor = sp.edit();
				editor.putString("access_token", response.getAccess_token());
				editor.putInt("id", response.getUser().getId());
				editor.putString("email", response.getUser().getEmail());
				editor.putString("username", response.getUser().getUsername());
				editor.putString("first_name", response.getUser()
						.getFirst_name());
				editor.putString("last_name", response.getUser().getLast_name());
				editor.putString("address", response.getUser().getAddress());
				editor.putString("city", response.getUser().getCity());
				editor.putString("state", response.getUser().getState());
				editor.putString("zip", response.getUser().getZip());
				editor.putInt("points", response.getUser().getPoints());
				editor.putString("dinner_status", response.getUser()
						.getDinner_status());
				editor.putString("userPhotoImageURL", response.getUser()
						.getUserPhotoImageURL());
				editor.putInt("defaultsearchprofile", response.getUser()
						.getDefaultsearchprofile());
				editor.commit();
			} else {
				result = false;
				SharedPreferences sp = mContext.get().getSharedPreferences(
						Constants.KEY_CURRENT_USER_XML, 0);
				Editor editor = sp.edit();
				editor.putString("error",response.getError());
				editor.commit();
			}

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		LoginNomalListener listener = mListener.get();
		if (result) {
			listener.onLoginNomalListenerComplete();
		} else {
			listener.onLoginNomalListenerFailed();
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
