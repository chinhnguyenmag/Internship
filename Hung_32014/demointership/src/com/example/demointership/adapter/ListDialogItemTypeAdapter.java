package com.example.demointership.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demointership.R;
import com.example.demointership.model.ItemTypeObject;

public class ListDialogItemTypeAdapter extends ArrayAdapter<ItemTypeObject>
		implements OnClickListener {
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
			holder.cbChoose.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.tvID.setText("" + getItem(position).getId());
		holder.tvName.setText("" + getItem(position).getName());
		holder.cbChoose.setTag(position);
		holder.cbChoose.setChecked(mList[position].isCheck());
		return convertView;
	}

	public ItemTypeObject[] getList() {
		return this.mList;
	}

	private class Holder {
		public TextView tvName;
		public CheckBox cbChoose;
		public TextView tvID;
	}

	@Override
	public void onClick(View view) {
		Integer index = (Integer) view.getTag();
		boolean state = mList[index].isCheck();
		mList[index].setCheck(!state);
	}
}
