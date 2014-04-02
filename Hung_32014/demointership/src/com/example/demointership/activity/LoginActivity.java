package com.example.demointership.activity;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.demointership.R;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public class LoginActivity extends Activity {
	Button mBtSubmit, mBtCreateAccount, mBtForgotPassword;
	ImageButton mIbFacebook, mIbTwitter, mIbGoogle;
	EditText mEtUsername, mEtPassword;
	public int REGISTER = 1, FORGOT = 2, FACEBOOK = 3, TWITTER = 4, GOOGLE = 5;
	private static final String APP_ID = "422350967898025";
	Facebook mFacebook;

	@SuppressWarnings("deprecation")
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

		mFacebook = new Facebook(APP_ID);
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
			forgotpassword();

			break;
		case R.id.login_ib_facebook: /* ImageButton Login with Facebook */
			loginFacebook();

			break;
		case R.id.login_ib_twitter: /* ImageButton Login with Twitter */

			break;
		case R.id.login_ib_google: /* ImageButton Login with Google+ */

			break;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}

	@SuppressWarnings("deprecation")
	private void loginFacebook() {
		// TODO Auto-generated method stub
		mFacebook.authorize(LoginActivity.this, new DialogListener() {

			@Override
			public void onFacebookError(FacebookError e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(DialogError e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onComplete(Bundle values) {
				// TODO Auto-generated method stub
				SharedPreferences fbSp = getSharedPreferences(
						"facebookUserDetail", 0);
				try {
					String json = mFacebook.request("me");
					JSONObject obj = Util.parseJson(json);
					String name = obj.getString("name");
					String email = obj.getString("email");
					String access_token = mFacebook.getAccessToken();
					long access_expires = mFacebook.getAccessExpires();
					String id = obj.getString("id");
					String firstname = obj.getString("firstname");
					String lastname = obj.getString("lastname");

					Editor edit = fbSp.edit();
					edit.putString("access_token", access_token);
					edit.putLong("access_expires", access_expires);
					edit.putString("name", name);
					edit.putString("email", email);
					edit.putString("id", id);
					edit.putString("firstname", firstname);
					edit.putString("lastname", lastname);
					edit.commit();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FacebookError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Gson GsonObject = Util.parseJson();

				// edit.putString("name", value)
			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub

			}
		});
	}

	@SuppressLint("NewApi")
	public void forgotpassword() {
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
				String email = emailEtDialog.getText().toString();
				Log.e("email", email);
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
