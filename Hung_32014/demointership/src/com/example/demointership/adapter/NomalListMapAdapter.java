package com.example.demointership.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demointership.R;
import com.example.demointership.model.RestaurantsObject;

public class NomalListMapAdapter extends ArrayAdapter<RestaurantsObject> {
	Activity mContext;
	RestaurantsObject[] mList;

	public NomalListMapAdapter(Activity context, int re,
			RestaurantsObject[] list) {
		super(context, re, list);
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			convertView = inflater.inflate(R.layout.item_list, parent, false);
		}
		RestaurantsObject item = mList[position];
		ImageView ivLogo = (ImageView) convertView
				.findViewById(R.id.itemlist_iv_logo);
		TextView tvName = (TextView) convertView
				.findViewById(R.id.itemlist_tv_name);
		TextView tvDistance = (TextView) convertView
				.findViewById(R.id.itemlist_tv_distance);
		tvName.setText(item.getName());

		tvDistance.setText(String.valueOf(item.getDistance()));
		ivLogo.setImageBitmap(item.getImagelogo());
		return convertView;
	}

}