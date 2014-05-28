package com.example.demointership.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demointership.R;
import com.example.demointership.model.RestaurantsObject;

public class HorizontalAdapter extends ArrayAdapter<RestaurantsObject> {
	private LayoutInflater mInflater;

	public HorizontalAdapter(Context context, RestaurantsObject[] values) {
		super(context, R.layout.item_horizontallistview, values);
		mInflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.item_horizontallistview, parent, false);

			holder = new Holder();
			holder.tvDistance = (TextView) convertView
					.findViewById(R.id.itemhlv_tv_distance);
			holder.ivLogo = (ImageView) convertView
					.findViewById(R.id.itemhlv_iv_logo);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.tvDistance.setText(String.valueOf(getItem(position)
				.getDistance()));
		holder.ivLogo.setImageBitmap(getItem(position).getImagelogo());
		return convertView;
	}

	private class Holder {
		public TextView tvDistance;
		public ImageView ivLogo;
	}

}