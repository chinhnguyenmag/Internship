package com.example.demointership.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.demointership.R;

public class LoginActivity extends Activity {
	Button mBtSubmit, mBtCreateAccount, mBtForgotPassword;
	ImageButton mIbFacebook, mIbTwitter, mIbGoogle;
	EditText mEtUsername, mEtPassword;
	public int REGISTER = 1, FORGOT = 2, FACEBOOK = 3, TWITTER = 4, GOOGLE = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mBtSubmit = (Button) findViewById(R.id.login_bt_submit);
		mBtCreateAccount = (Button) findViewById(R.id.login_bt_createaccount);
		mBtForgotPassword = (Button) findViewById(R.id.login_bt_forgotpassword);
		mIbFacebook = (ImageButton) findViewById(R.id.login_ib_facebook);
		mIbTwitter = (ImageButton) findViewById(R.id.login_ib_twitter);
		mIbGoogle = (ImageButton) findViewById(R.id.login_ib_google);
		mEtUsername = (EditText) findViewById(R.id.login_et_username);
		mEtPassword = (EditText) findViewById(R.id.login_et_password);

	}

	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.login_bt_submit: /* Button Submit */

			break;
		case R.id.login_bt_createaccount: /* Button Create A New Account */
			startActivityForResult(
					new Intent("android.intent.action.REGISTER"), REGISTER);
			break;
		case R.id.login_bt_forgotpassword: /* Button Forgot Password */
			forgot();

			break;
		case R.id.login_ib_facebook: /* ImageButton Login with Facebook */

			
			break;
		case R.id.login_ib_twitter: /* ImageButton Login with Twitter */
			break;
		case R.id.login_ib_google: /* ImageButton Login with Google+ */
			break;
		}
	}

	@SuppressLint("NewApi")
	public void forgot() {
		final Dialog dialog = new Dialog(LoginActivity.this);
		dialog.setContentView(R.layout.dialog_forgotpassword);
		Button submitBtnDialog = (Button) dialog
				.findViewById(R.id.forgot_bt_submit);
		Button cancelBtnDialog = (Button) dialog
				.findViewById(R.id.forgot_bt_cancel);
		final EditText emailEtDialog = (EditText) dialog
				.findViewById(R.id.forgot_et_email);
		submitBtnDialog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		cancelBtnDialog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

	}
}
