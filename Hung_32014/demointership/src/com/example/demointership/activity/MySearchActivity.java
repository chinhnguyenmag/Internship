package com.example.demointership.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.demointership.R;

public class MySearchActivity extends Activity {
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

	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.mysearch_bt_savedprofile:

			break;
		case R.id.mysearch_bt_create:

			break;
		}
	}
}
