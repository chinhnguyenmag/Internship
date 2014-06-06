package com.example.demointership.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.demointership.R;
import com.example.demointership.Util.Constants;
import com.example.demointership.asynctask.DeleteUserSearchProfileAsyncTask;
import com.example.demointership.listener.DeleteUserSearchProfileListener;
import com.example.demointership.model.SearchProfileObject;

public class SearchProfileAdapter extends ArrayAdapter<SearchProfileObject> {
	LayoutInflater mInflater;
	DeleteUserSearchProfileListener mListener;
	Activity mActivity;

	public SearchProfileAdapter(Activity activity, int resource,
			SearchProfileObject[] list, DeleteUserSearchProfileListener listener) {
		super(activity, R.layout.item_mysearch, list);
		mInflater = LayoutInflater.from(activity);
		this.mListener = listener;
		this.mActivity = activity;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_mysearch, null);
			holder = new Holder();
			holder.ibEdit = (ImageButton) convertView
					.findViewById(R.id.itemmysearch_ib_edit);
			holder.ibDelete = (ImageButton) convertView
					.findViewById(R.id.itemmysearch_ib_del);
			holder.tvName = (TextView) convertView
					.findViewById(R.id.itemmysearch_tv_string);
			holder.cbChoose = (CheckBox) convertView
					.findViewById(R.id.itemmysearch_cb_choose);
			holder.ibDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							mActivity);
					builder.setMessage("are you sure to delete this profile? ");
					builder.setPositiveButton("YES",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									DeleteUserSearchProfileAsyncTask async = new DeleteUserSearchProfileAsyncTask(
											mActivity, mListener);
									SharedPreferences sp = mActivity
											.getSharedPreferences(
													Constants.KEY_CURRENT_USER_XML,
													0);
									String access_token = sp.getString(
											"access_token", "");
									int id = getItem(position).getId();
									async.execute(access_token,
											String.valueOf(id));
									dialog.dismiss();
								}
							});
					builder.setNegativeButton("NO",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					AlertDialog alert = builder.create();
					alert.show();
				}
			});
			holder.ibEdit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.tvName.setText(getItem(position).getName());
		if (getItem(position).getIsdefault() == 1)
			holder.cbChoose.setChecked(true);
		else
			holder.cbChoose.setChecked(false);
		return convertView;
	}

	private class Holder {
		public ImageButton ibEdit;
		public TextView tvName;
		public CheckBox cbChoose;
		public ImageButton ibDelete;
	}
}
