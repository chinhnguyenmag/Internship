package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.Util.Temp;
import com.example.demointership.listener.GetASearchProfileListener;
import com.example.demointership.model.SearchProfileObject;
import com.google.gson.Gson;

public class GetASearchProfileAsyncTask extends
		AsyncTask<String, Void, Boolean> {
	WeakReference<Context> mContext;
	WeakReference<GetASearchProfileListener> mListener;
	ProgressDialog mDialog;

	public GetASearchProfileAsyncTask(Context context,
			GetASearchProfileListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<GetASearchProfileListener>(listener);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		try {
			SearchProfileObject response = new Gson().fromJson(
					Server.getJSON(Server.requestGet(ServerURL.URL
							+ ServerURL.getKeyGetASearchprofile()
							+ "access_token=" + params[0]
							+ "&search_profile_id=" + params[1])),
					SearchProfileObject.class);
			if (!response.getStatus().equals("failed")) {
				Temp.defaultSearchProfileObject = response;
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
		GetASearchProfileListener listener = mListener.get();
		if (result) {
			listener.onGetASearchProfileListenerComplete();
		} else {
			listener.onGetASearchProfileListenerFailed();
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
