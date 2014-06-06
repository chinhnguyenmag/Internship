package com.example.demointership.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demointership.R;
import com.example.demointership.model.ItemTypeObject;

public class ListDialogItemTypeAdapter extends ArrayAdapter<ItemTypeObject>{
	Activity mContext;
	ItemTypeObject[] mList;
	LayoutInflater mInflater;

	public ListDialogItemTypeAdapter(Activity context, int resource,
			ItemTypeObject[] list) {
		super(context, resource, list);
		mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_createmysearch, null);
			holder = new Holder();
			holder.tvID = (TextView) convertView
					.findViewById(R.id.item_createmysearch_id);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.item_createmysearch_name);
			holder.cbChoose = (CheckBox) convertView
					.findViewById(R.id.item_createmysearch_check);
			holder.cbChoose
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								Toast.makeText(mContext,
										"" + getItem(position).getName(),
										Toast.LENGTH_SHORT).show();
								getItem(position).setCheck(true);
							} else {

							}
						}
					});
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.tvID.setText("" + getItem(position).getId());
		holder.tvName.setText("" + getItem(position).getName());
		return convertView;
	}

	private class Holder {
		public TextView tvName;
		public CheckBox cbChoose;
		public TextView tvID;
	}
}
