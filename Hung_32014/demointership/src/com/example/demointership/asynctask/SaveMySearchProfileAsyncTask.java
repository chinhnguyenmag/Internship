package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

import com.example.demointership.Util.Constants;
import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.listener.SaveMySearchProfileListener;
import com.example.demointership.model.ResponseObject;
import com.google.gson.Gson;

public class SaveMySearchProfileAsyncTask extends
		AsyncTask<String, Void, Boolean> {
	WeakReference<Context> mContext;
	WeakReference<SaveMySearchProfileListener> mListener;
	ProgressDialog mDialog;

	public SaveMySearchProfileAsyncTask(Context context,
			SaveMySearchProfileListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<SaveMySearchProfileListener>(
				listener);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		try {
			JSONObject jObject = new JSONObject();
			jObject.put("access_token", params[0]);
			jObject.put("restaurant_rating_min", Integer.parseInt(params[1]));
			jObject.put("restaurant_rating_max", Integer.parseInt(params[2]));
			jObject.put("item_price_min", Integer.parseInt(params[3]));
			jObject.put("item_price_max", Integer.parseInt(params[4]));
			jObject.put("point_offered_min", Integer.parseInt(params[5]));
			jObject.put("point_offered_max", Integer.parseInt(params[6]));
			jObject.put("item_rating_min", Integer.parseInt(params[7]));
			jObject.put("item_rating_max", Integer.parseInt(params[8]));
			jObject.put("radius", Integer.parseInt(params[9]));
			String[] item_type = params[10].split(",");
			JSONArray item_type_arr = new JSONArray();
			for (String item : item_type)
				item_type_arr.put(item);
			jObject.put("item_type", item_type_arr);
			String[] menu_type = params[11].split(",");
			JSONArray menu_type_arr = new JSONArray();
			for (String item : menu_type)
				menu_type_arr.put(item);
			jObject.put("menu_type", item_type_arr);
			jObject.put("keyword", params[12]);
			jObject.put("server_rating_min", Integer.parseInt(params[13]));
			jObject.put("server_rating_max", Integer.parseInt(params[14]));
			jObject.put("isdefault", Integer.parseInt(params[15]));
			jObject.put("name", params[16]);
			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			ResponseObject response = new Gson().fromJson(Server.getJSON(Server
					.requestPost(
							ServerURL.URL + ServerURL.getKeyAddSearchprofile(),
							stringEntity)), ResponseObject.class);
			if (response.getStatus().equals("success")) {

			} else {
				result = false;
				SharedPreferences sp = mContext.get().getSharedPreferences(
						Constants.KEY_CURRENT_USER_XML, 0);
				Editor editor = sp.edit();
				editor.putString("error", response.getError());
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
		SaveMySearchProfileListener listener = mListener.get();
		if (result) {
			listener.onSaveMySearchProfileListenerComplete();
		} else {
			listener.onSaveMySearchProfileListenerFailed();
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
