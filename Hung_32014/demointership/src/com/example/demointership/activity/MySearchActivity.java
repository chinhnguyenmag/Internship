package com.example.demointership.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demointership.R;
import com.example.demointership.Util.Constants;
import com.example.demointership.Util.Temp;
import com.example.demointership.adapter.SearchProfileAdapter;
import com.example.demointership.asynctask.GetAllSearchProfileAsyncTask;
import com.example.demointership.listener.DeleteUserSearchProfileListener;
import com.example.demointership.listener.GetAllSearchProfileListener;

public class MySearchActivity extends Activity implements
		GetAllSearchProfileListener, DeleteUserSearchProfileListener {
	Button mBtSavedProfile, mBtCreateProfile;
	ListView mLvList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mysearch);
		mBtSavedProfile = (Button) findViewById(R.id.mysearch_bt_savedprofile);
		mBtCreateProfile = (Button) findViewById(R.id.mysearch_bt_create);
		mLvList = (ListView) findViewById(R.id.mysearch_lv_list);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences sp = getSharedPreferences(Constants.KEY_CURRENT_USER_XML, 0);
		String access_token = sp.getString("access_token", "");
		GetAllSearchProfileAsyncTask async = new GetAllSearchProfileAsyncTask(MySearchActivity.this,MySearchActivity.this);
		async.execute(access_token);
	}

	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.mysearch_bt_savedprofile:

			break;
		case R.id.mysearch_bt_create:

			break;
		}
	}

	@Override
	public void onGetAllSearchProfileListenerComplete() {
		SearchProfileAdapter adapter = new SearchProfileAdapter(
				getApplicationContext(), R.layout.item_mysearch,
				Temp.listSearchProfileObject, this);
		mLvList.setAdapter(adapter);
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
		SharedPreferences sp = getSharedPreferences(Constants.KEY_CURRENT_USER_XML, 0);
		String access_token = sp.getString("access_token", "");
		GetAllSearchProfileAsyncTask async = new GetAllSearchProfileAsyncTask(MySearchActivity.this,MySearchActivity.this);
		async.execute(access_token);
	}

	@Override
	public void onDeleteUserSearchProfileListenerFailed() {
		
	}
}
