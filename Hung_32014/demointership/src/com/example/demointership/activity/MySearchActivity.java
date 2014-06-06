package com.example.demointership.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demointership.R;
import com.example.demointership.Util.Constants;
import com.example.demointership.Util.Temp;
import com.example.demointership.adapter.SearchProfileAdapter;
import com.example.demointership.asynctask.GetAllSearchProfileAsyncTask;
import com.example.demointership.asynctask.GetItemTypeAsyncTask;
import com.example.demointership.asynctask.GetMenuTypeAsyncTask;
import com.example.demointership.listener.DeleteUserSearchProfileListener;
import com.example.demointership.listener.GetAllSearchProfileListener;
import com.example.demointership.listener.GetItemTypeListener;
import com.example.demointership.listener.GetMenuTypeListener;
import com.example.demointership.model.SearchProfileObject;

public class MySearchActivity extends Activity implements
		GetAllSearchProfileListener, DeleteUserSearchProfileListener, GetItemTypeListener, GetMenuTypeListener {
	Button mBtSavedProfile, mBtCreateProfile;
	ListView mLvList;
	LinearLayout mLlContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mysearch);
		mBtSavedProfile = (Button) findViewById(R.id.mysearch_bt_savedprofile);
		mBtCreateProfile = (Button) findViewById(R.id.mysearch_bt_create);
		mLvList = (ListView) findViewById(R.id.mysearch_lv_list);
		mLlContainer = (LinearLayout) findViewById(R.id.mysearch_ll_container);
		SharedPreferences sp = getSharedPreferences(Constants.KEY_CURRENT_USER_XML, 0);
		String access_token = sp.getString("access_token", "");
		GetItemTypeAsyncTask asyncGetItem = new GetItemTypeAsyncTask(this,this);
		GetMenuTypeAsyncTask asyncGetMenu = new GetMenuTypeAsyncTask(this, this);
		asyncGetItem.execute(access_token);
		asyncGetMenu.execute(access_token);
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences sp = getSharedPreferences(
				Constants.KEY_CURRENT_USER_XML, 0);
		String access_token = sp.getString("access_token", "");
		GetAllSearchProfileAsyncTask async = new GetAllSearchProfileAsyncTask(
				MySearchActivity.this, MySearchActivity.this);
		async.execute(access_token);
	}

	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.mysearch_bt_savedprofile:

			break;
		case R.id.mysearch_bt_create:
			startActivity(new Intent(MySearchActivity.this,
					CreateMySearchActivity.class));
			break;
		}
	}

	@Override
	public void onGetAllSearchProfileListenerComplete() {
		SearchProfileAdapter adapter = new SearchProfileAdapter(
				 MySearchActivity.this, R.layout.item_mysearch,
				Temp.listSearchProfileObject, this);
		mLvList.setAdapter(adapter);
		// for (final SearchProfileObject item : Temp.listSearchProfileObject) {
		// LayoutInflater layoutInflater = (LayoutInflater)
		// getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// final View addView = layoutInflater.inflate(R.layout.item_mysearch,
		// null);
		// TextView textOut =
		// (TextView)addView.findViewById(R.id.itemmysearch_tv_string);
		// textOut.setText(item.getName());
		// ImageButton ibDelete =
		// (ImageButton)addView.findViewById(R.id.itemmysearch_ib_edit);
		// ibDelete.setOnClickListener(new OnClickListener(){
		// @Override
		// public void onClick(View v) {
		// showToast(""+ item.getId() + "" + item.getName());
		// }
		// });
		// mLlContainer.addView(addView);
		// }
	}

	@Override
	public void onGetAllSearchProfileListenerFailed() {
		showToast("Can't connect to server !");
	}

	private void showToast(String st) {
		Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDeleteUserSearchProfileListenerComplete() {
		SharedPreferences sp = getSharedPreferences(
				Constants.KEY_CURRENT_USER_XML, 0);
		String access_token = sp.getString("access_token", "");
		GetAllSearchProfileAsyncTask async = new GetAllSearchProfileAsyncTask(
				MySearchActivity.this, MySearchActivity.this);
		async.execute(access_token);
	}

	@Override
	public void onDeleteUserSearchProfileListenerFailed() {

	}

	@Override
	public void onGetItemTypeListenerComplete() {
		
	}

	@Override
	public void onGetItemTypeListenerFailed() {
		
	}

	@Override
	public void onGetMenuTypeListenerComplete() {
		
	}

	@Override
	public void onGetMenuTypeListenerFailed() {
		
	}
}
