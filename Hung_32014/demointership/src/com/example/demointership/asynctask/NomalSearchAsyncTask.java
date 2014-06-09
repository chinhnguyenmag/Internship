package com.example.demointership.asynctask;

import java.lang.ref.WeakReference;
import java.net.URL;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.Util.Temp;
import com.example.demointership.listener.NomalSearchListener;
import com.example.demointership.model.RestaurantsObject;
import com.google.gson.Gson;

public class NomalSearchAsyncTask extends
		AsyncTask<String, Void, RestaurantsObject[]> {
	WeakReference<Context> mContext;
	WeakReference<NomalSearchListener> mListener;
	ProgressDialog mDialog;

	public NomalSearchAsyncTask(Context context, NomalSearchListener listener) {
		this.mContext = new WeakReference<Context>(context);
		this.mListener = new WeakReference<NomalSearchListener>(listener);
	}

	@Override
	protected RestaurantsObject[] doInBackground(String... params) {
		RestaurantsObject[] response = null;
		try {
			JSONObject jObject = new JSONObject();
			jObject.put("access_token", params[0]);
			jObject.put("latitude", params[1]);
			jObject.put("longitude", params[2]);
			jObject.put("name", params[3]);
			jObject.put("current_city", params[4]);

			StringEntity stringEntity = new StringEntity(jObject.toString(),
					"UTF-8");
			response = new Gson().fromJson(Server.getJSON(Server.requestPost(
					ServerURL.URL + ServerURL.getKeyNormalsearch(),
					stringEntity)), RestaurantsObject[].class);
			for (RestaurantsObject item : response) {
				Bitmap image = null;
				if (item.getType() != null) {
					URL imageURL = new URL(ServerURL.URL + item.getLogo());
					image = BitmapFactory.decodeStream(imageURL
							.openConnection().getInputStream());
				} else {
					// if (item.getPhotos() == null) {
					URL imageURL = new URL(item.getLogo());
					image = BitmapFactory.decodeStream(imageURL
							.openConnection().getInputStream());
					// } else {
					//
					// String url =
					// "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
					// + item.getPhotos().get(0).photo_reference +
					// "&sensor=false&key="+ item.getPhotos().get(0).api_key;
					// URL imageURL = new URL(url);
					// image = BitmapFactory.decodeStream(imageURL
					// .openConnection().getInputStream());
					// }
				}
				item.setImagelogo(image);
			}
		} catch (Exception e) {
		}
		return response;
	}

	@Override
	protected void onPostExecute(RestaurantsObject[] result) {
		super.onPostExecute(result);
		NomalSearchListener listener = mListener.get();
		if (result != null) {
			if (Temp.listRestaurantObject != null) {
				Temp.listRestaurantObject = null;
			}
			Temp.listRestaurantObject = result;
			listener.onNomalSearchListenerComplete();

		} else {
			listener.onNomalSearchListenerFailed();
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
