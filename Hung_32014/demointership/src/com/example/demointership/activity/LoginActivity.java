package com.example.demointership.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.demointership.R;
import com.example.demointership.Util.Utils;
import com.example.demointership.asyntask.asynctasklogin;
import com.example.demointership.model.UserDetail;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.PlusClient;

public class LoginActivity extends Activity {
	Button mBtSubmit, mBtCreateAccount, mBtForgotPassword;
	ImageButton mIbFacebook, mIbTwitter, mIbGoogle;
	EditText mEtUsername, mEtPassword;
	public int REGISTER = 1, FORGOT = 2, FACEBOOK = 3, TWITTER = 4, GOOGLE = 5;
	private static final String APP_ID = "422350967898025";
	Facebook mFacebook;
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	// private GoogleApiClient mGoogleApiClient;
	// private boolean mIntentInProgress;
	final int RC_SIGN_IN = 0;
	Twitter mTwitter;
	private static final String CONSUMER_KEY = "sdOjEI2cOxzTLHMCCMmuQ";
	private static final String CONSUMER_SECRET = "biI3oxIBX2QMzUIVaW1wVAXygbynuS80pqSliSDTc";
	Dialog mDialogSocial;
	boolean isGooglePlus = false, isFacebook = false, isTwitter = false;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

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
		// mTwitter = Twitter
		ConfigurationBuilder confi = new ConfigurationBuilder();
		confi.setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(
				CONSUMER_SECRET);
		Configuration configuration = confi.build();
		mTwitter = new TwitterFactory(configuration).getInstance();

	}

	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.login_bt_submit: /* Button Submit */
			loginNomal();

			break;
		case R.id.login_bt_createaccount: /* Button Create A New Account */
			startActivityForResult(new Intent(this, RegisterActivity.class),
					REGISTER);
			break;
		case R.id.login_bt_forgotpassword: /* Button Forgot Password */
			forgotpassword();

			break;
		case R.id.login_ib_facebook: /* ImageButton Login with Facebook */
			loginFacebook();

			break;
		case R.id.login_ib_twitter: /* ImageButton Login with Twitter */
			loginTwitter();
			break;
		case R.id.login_ib_google: /* ImageButton Login with Google+ */
			loginGoogle();
			break;
		}
	}

	private void loginNomal() {
		String username = mEtUsername.getText().toString();
		String password = mEtPassword.getText().toString();
		if (username.length() != 0 && password.length() != 0) {
			asynctasklogin async = new asynctasklogin(LoginActivity.this);
			if (Utils.isValidEmail(getApplicationContext(), username)) {
				async.execute(username, null, password);
			} else {
				async.execute(null, username, password);
			}
			UserDetail userdetail = null;
			try {
				userdetail = async.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (userdetail.status.equals("success")) {
				startActivity(new Intent(LoginActivity.this,
						SuccessActivity.class));
			} else {
				Toast.makeText(getApplicationContext(), userdetail.error,
						Toast.LENGTH_SHORT).show();
			}
		} else
			Toast.makeText(getApplicationContext(), "Insert data",
					Toast.LENGTH_SHORT).show();
	}

	private void loginGoogle() {
		try {
			mConnectionResult.startResolutionForResult(this, GOOGLE);
			isGooglePlus = true;
		} catch (SendIntentException e) {
			mPlusClient.connect();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		// mPlusClient.connect();
	}

	@Override
	protected void onStop() {
		// mPlusClient.disconnect();
		super.onStop();
	}

	private void loginTwitter() {
		//ConfigurationBuilder confi = new ConfigurationBuilder();
		//confi.setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(
		//		CONSUMER_SECRET);
		//Configuration configuration = confi.build();
		//mTwitter = new TwitterFactory(configuration).getInstance();
		try {
			mTwitter.getOAuth2Token();
			isTwitter = true;
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}

	@SuppressWarnings("deprecation")
	private void loginFacebook() {

		mFacebook.authorize(LoginActivity.this, new DialogListener() {

			@Override
			public void onFacebookError(FacebookError e) {

			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onComplete(Bundle values) {
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
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (FacebookError e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				isFacebook = true;
			}

			@Override
			public void onCancel() {

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
				String email = emailEtDialog.getText().toString();
				Log.e("email", email);
			}
		});
		cancelBtnDialog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBackPressed() {

		if (mDialogSocial.isShowing()) {
			if (isFacebook)
				try {
					mFacebook.logout(getApplicationContext());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (isTwitter) {
				mTwitter.shutdown();
			}
			if (isGooglePlus) {
				mPlusClient.disconnect();
			}
		}
		super.onBackPressed();

	}

	public void showDialogSocialLogin() {
		mDialogSocial = new Dialog(LoginActivity.this);
		mDialogSocial.setContentView(R.layout.dialog_sociallogin);
		Button submitBtn = (Button) mDialogSocial
				.findViewById(R.id.sociallogin_bt_submit);
		final EditText EtUsername = (EditText) mDialogSocial
				.findViewById(R.id.sociallogin_et_username);
		final EditText EtZipcode = (EditText) mDialogSocial
				.findViewById(R.id.sociallogin_et_zipcode);
		submitBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = EtUsername.getText().toString();
				String zipcode = EtZipcode.getText().toString();
				// Thuc hien voi server

				// success => SusseccActivity

				// fail => LoginActivity +logout Social
			}
		});
	}
}
