package com.example.demointership.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demointership.R;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.model.RestaurantsObject;
import com.fedorvlasov.lazylist.ImageLoader;

public class NomalListMapAdapter extends ArrayAdapter<RestaurantsObject> {
	Activity mContext;
	RestaurantsObject[] mList;
	ImageLoader mImageLoader;
	static LayoutInflater mInflater = null;

	public NomalListMapAdapter(Activity context, int re,
			RestaurantsObject[] list) {
		super(context, re, list);
		this.mContext = context;
		this.mList = list;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mImageLoader = new ImageLoader(context.getApplicationContext());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) {
			vi = mInflater.inflate(R.layout.item_list, null);
		}
		RestaurantsObject item = mList[position];
		ImageView ivLogo = (ImageView) vi.findViewById(R.id.itemlist_iv_logo);
		TextView tvName = (TextView) vi.findViewById(R.id.itemlist_tv_name);
		TextView tvDistance = (TextView) vi
				.findViewById(R.id.itemlist_tv_distance);
		// TextView tvLink = (TextView) convertView
		// .findViewById(R.id.itemlist_tv_link);
		tvName.setText(item.getName());
		// tvLink.setText(item.getLogo());

		tvDistance.setText(String.valueOf(item.getDistance()));
		if (item.getType().equals("mymenu"))
			mImageLoader.DisplayImage(ServerURL.URL + item.getLogo(), ivLogo);
		else if (item.getPhotos() != null) {
			String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
					+ item.getPhotos().get(0).photo_reference
					+ "&sensor=false&key=" + item.getPhotos().get(0).api_key;
			mImageLoader.DisplayImage(url, ivLogo);
		} else {
			mImageLoader.DisplayImage(item.getLogo(), ivLogo);
		}
		return vi;
	}
}