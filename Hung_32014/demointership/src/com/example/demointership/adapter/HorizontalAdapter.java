package com.example.demointership.adapter;

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

public class HorizontalAdapter extends ArrayAdapter<RestaurantsObject> {
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;

	public HorizontalAdapter(Context context, RestaurantsObject[] values) {
		super(context, R.layout.item_horizontallistview, values);
		mInflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		mImageLoader = new ImageLoader(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_horizontallistview,
					parent, false);

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
		if (getItem(position).getType().equals("mymenu"))
			mImageLoader.DisplayImage(ServerURL.URL
					+ getItem(position).getLogo(), holder.ivLogo);
		else if (getItem(position).getPhotos() != null) {
			RestaurantsObject item = getItem(position);
			String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
					+ item.getPhotos().get(0).photo_reference
					+ "&sensor=false&key=" + item.getPhotos().get(0).api_key;
			mImageLoader.DisplayImage(url, holder.ivLogo);
		} else {
			mImageLoader.DisplayImage(getItem(position).getLogo(),
					holder.ivLogo);
		}
		// holder.tvLink.setText(getItem(position).getLogo());

		return convertView;
	}

	private class Holder {
		public TextView tvDistance;
		public ImageView ivLogo;
	}

}