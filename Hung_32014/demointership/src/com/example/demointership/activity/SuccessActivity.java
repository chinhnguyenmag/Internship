package com.example.demointership.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demointership.R;

public class SuccessActivity extends Activity {
	Button mBtLogout;
	SharedPreferences mSpLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success);
		mBtLogout = (Button) findViewById(R.id.success_bt_logout);
		mSpLogin = getSharedPreferences("CurrentUser", 0);
	}

	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.success_bt_logout:
			Editor editor = mSpLogin.edit();
			editor.remove("uid");
			editor.remove("first_name");
			editor.remove("last_name");
			editor.remove("email");
			editor.remove("username");
			editor.remove("provider");
			editor.commit();
			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;

		}
	}
}
