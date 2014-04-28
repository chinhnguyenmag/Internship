package com.example.demointership.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demointership.R;

public class SuccessActivity extends Activity {
	Button mBtLogout;
	SharedPreferences mSpLogin;
	TextView mTvUsername, mTvUid;
	ImageView mImAvatar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success);
		mBtLogout = (Button) findViewById(R.id.success_bt_logout);
		mTvUsername = (TextView) findViewById(R.id.success_tv_username);
		mTvUid = (TextView) findViewById(R.id.success_tv_uid);
		mImAvatar = (ImageView) findViewById(R.id.success_im_avatar);
		mSpLogin = getSharedPreferences("CurrentUser", 0);
		String email = mSpLogin.getString("email", "");

		String uid = mSpLogin.getString("uid", "1");
		String url = mSpLogin.getString("userPhotoImageURL", null);
		if (url != null) {
			try {
				Bitmap bitmap = BitmapFactory
						.decodeStream((InputStream) new URL(url).getContent());
				mImAvatar.setImageBitmap(bitmap);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mTvUsername.setText(email);
		mTvUid.setText(uid);
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
