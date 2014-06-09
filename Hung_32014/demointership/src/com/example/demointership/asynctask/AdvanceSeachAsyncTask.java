package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.Util.Temp;
import com.example.demointership.listener.AdvanceSearchListener;
import com.example.demointership.model.RestaurantsObject;
import com.google.gson.Gson;

public class AdvanceSeachAsyncTask extends AsyncTask<String, Void, Boolean> {
	WeakReference<Context> mContext;
	WeakReference<AdvanceSearchListener> mListener;
	ProgressDialog mDialog;

	public AdvanceSeachAsyncTask(Context context, AdvanceSearchListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<AdvanceSearchListener>(listener);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		boolean result = true;
		try {
			JSONObject jObject = new JSONObject();
			jObject.put("access_token", params[0]);
			jObject.put("latitude", Float.parseFloat(params[1]));
			jObject.put("longitude", Float.parseFloat(params[2]));
			String[] restaurant_rating = params[3].split(",");
			jObject.put("restaurant_rating_min",
					Float.parseFloat(restaurant_rating[0]));
			jObject.put("restaurant_rating_max",
					Float.parseFloat(restaurant_rating[1]));
			String[] item_price = params[4].split(",");
			jObject.put("item_price_min", Float.parseFloat(item_price[0]));
			jObject.put("item_price_max", Float.parseFloat(item_price[1]));
			String[] point_offered = params[5].split(",");
			jObject.put("point_offered_min", Float.parseFloat(point_offered[0]));
			jObject.put("point_offered_max", Float.parseFloat(point_offered[1]));
			String[] item_rating = params[6].split(",");
			jObject.put("item_rating_min", Float.parseFloat(item_rating[0]));
			jObject.put("item_rating_max", Float.parseFloat(item_rating[1]));
			jObject.put("radius", Integer.parseInt(params[7]));
			String[] item_type = params[8].split(",");
			JSONArray item_type_arr = new JSONArray();
			for (String item : item_type) {
				item_type_arr.put(Integer.parseInt(item));
			}
			jObject.put("item_type", item_type_arr);
			String[] menu_type = params[9].split(",");
			JSONArray menu_type_arr = new JSONArray();
			for (String item : menu_type) {
				menu_type_arr.put(Integer.parseInt(item));
			}
			jObject.put("menu_type", menu_type_arr);
			jObject.put("keyword", params[10]);
			String[] server_rating = params[11].split(",");
			jObject.put("server_rating_min", Float.parseFloat(server_rating[0]));
			jObject.put("server_rating_max", Float.parseFloat(server_rating[1]));
			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			RestaurantsObject[] response = new Gson().fromJson(Server
					.getJSON(Server.requestPost(
							ServerURL.URL + ServerURL.getKeyAdvancedsearch(),
							stringEntity)), RestaurantsObject[].class);
			if (response != null) {
				Temp.listRestaurantObject = response;
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
		AdvanceSearchListener listener = mListener.get();
		if (result) {
			listener.onAdvanceSearchListenerComplete();
		} else {
			listener.onAdvanceSearchListenerFailed();
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
