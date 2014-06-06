package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.AsyncTask;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.Util.Temp;
import com.example.demointership.listener.GetItemTypeListener;
import com.example.demointership.model.ItemTypeObject;
import com.google.gson.Gson;

public class GetItemTypeAsyncTask extends
		AsyncTask<String, Void, ItemTypeObject[]> {
	WeakReference<Context> mContext;
	WeakReference<GetItemTypeListener> mListener;
//	ProgressDialog mDialog;

	public GetItemTypeAsyncTask(Context context, GetItemTypeListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<GetItemTypeListener>(listener);
	}

	@Override
	protected ItemTypeObject[] doInBackground(String... params) {
		ItemTypeObject[] response = null;
		try {

			response = new Gson().fromJson(
					Server.getJSON(Server.requestGet(ServerURL.URL
							+ ServerURL.getKeyGetAllItemtype()
							+ "access_token=" + params[0])),
					ItemTypeObject[].class);

		} catch (Exception e) {
			response = null;
		}
		return response;
	}

	@Override
	protected void onPostExecute(ItemTypeObject[] result) {
		super.onPostExecute(result);
		GetItemTypeListener listener = mListener.get();
		if (result != null) {
			Temp.listItemType = result;
			listener.onGetItemTypeListenerComplete();
		} else {
			listener.onGetItemTypeListenerFailed();
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
