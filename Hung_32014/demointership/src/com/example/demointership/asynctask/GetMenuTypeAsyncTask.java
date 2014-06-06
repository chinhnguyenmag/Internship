package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.AsyncTask;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.Util.Temp;
import com.example.demointership.listener.GetMenuTypeListener;
import com.example.demointership.model.MenuTypeObject;
import com.google.gson.Gson;

public class GetMenuTypeAsyncTask extends
		AsyncTask<String, Void, MenuTypeObject[]> {
	WeakReference<Context> mContext;
	WeakReference<GetMenuTypeListener> mListener;
//	ProgressDialog mDialog;

	public GetMenuTypeAsyncTask(Context context, GetMenuTypeListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<GetMenuTypeListener>(listener);
	}

	@Override
	protected MenuTypeObject[] doInBackground(String... params) {
		MenuTypeObject[] response = null;
		try {

			response = new Gson().fromJson(
					Server.getJSON(Server.requestGet(ServerURL.URL
							+ ServerURL.getKeyGetAllMenutype()
							+ "access_token=" + params[0])),
					MenuTypeObject[].class);

		} catch (Exception e) {
			response = null;
		}
		return response;
	}

	@Override
	protected void onPostExecute(MenuTypeObject[] result) {
		super.onPostExecute(result);
		GetMenuTypeListener listener = mListener.get();
		if (result != null) {
			Temp.listMenuType = result;
			listener.onGetMenuTypeListenerComplete();
		} else {
			listener.onGetMenuTypeListenerFailed();
		}
//		mDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
//		mDialog = new ProgressDialog(mContext.get());
//		mDialog.setMessage("Loading");
//		mDialog.show();
	}

}
