package com.example.demointership.activity;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.demointership.R;
import com.example.demointership.Util.Constants;
import com.example.demointership.Util.Utils;
import com.example.demointership.asynctask.ForGotPasswordAsyncTask;
import com.example.demointership.asynctask.LoginNomalAsyncTask;
import com.example.demointership.asynctask.LoginSocialAsyncTask;
import com.example.demointership.asynctask.RegisterSocialAsyncTask;
import com.example.demointership.listener.ForGotPasswordListener;
import com.example.demointership.listener.LoginNomalListener;
import com.example.demointership.listener.LoginSocialListener;
import com.example.demointership.listener.RegisterSocialListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;

@SuppressLint("NewApi")
public class LoginActivity extends Activity
		implements
		ConnectionCallbacks,
		OnConnectionFailedListener,
		com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks,
		LoginNomalListener, ForGotPasswordListener, LoginSocialListener,
		RegisterSocialListener {
	Button mBtSubmit, mBtCreateAccount, mBtForgotPassword;
	ImageButton mIbFacebook, mIbTwitter, mIbGoogle;
	EditText mEtUsername, mEtPassword;
	Facebook mFacebook;
	ConnectionResult mConnectionResult;
	Twitter mTwitter;
	RequestToken mRequestToken;
	boolean mIsDialogshowing = false;
	boolean mIsGooglePlus = false, mIsFacebook = false, mIsTwitter = false;
	SharedPreferences mSpLogin;
	PlusClient mPlusClient;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
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

		mFacebook = new Facebook(Constants.APP_ID);
		mSpLogin = getSharedPreferences(Constants.KEY_CURRENT_USER_XML, 0);
		mPlusClient = new PlusClient.Builder(this, this, this).setActions(
				"http://schemas.google.com/AddActivity",
				"http://schemas.google.com/BuyActivity")
		/* .setScopes("https://www.googleapis.com/auth/userinfo.email") */
		.build();
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(Constants.CONSUMER_KEY);
		builder.setOAuthConsumerSecret(Constants.CONSUMER_SECRET);
		twitter4j.conf.Configuration configuration = builder.build();

		TwitterFactory factory = new TwitterFactory(configuration);
		mTwitter = factory.getInstance();
		if (android.os.Build.VERSION.SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.login_bt_submit: /* Button Submit */
			loginNomal();

			break;
		case R.id.login_bt_createaccount: /* Button Create A New Account */
			startActivityForResult(new Intent(this, RegisterActivity.class),
					Constants.REGISTER);
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
			LoginNomalAsyncTask async = new LoginNomalAsyncTask(this, this);
			if (Utils.isValidEmail(getApplicationContext(), username)) {
				async.execute(username, null, password);
			} else {
				async.execute(null, username, password);
			}
		} else
			showToast("Please Insert Data !");
	}

	private void loginGoogle() {

		mIsGooglePlus = true;
		try {
			if (!mPlusClient.isConnected()/* && mConnectionResult.hasResolution() */) {
				try {

					mConnectionResult.startResolutionForResult(
							LoginActivity.this, Constants.LOGIN_VIA_GOOGLE);
				} catch (Exception e) {
					mConnectionResult = null;

					mPlusClient.connect();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {
		if (mTwitter != null) {
			String uid = "";
			String username = "";
			try {
				uid = String.valueOf(mTwitter.getId());
				username = mTwitter.getScreenName();
				Editor editor = mSpLogin.edit();
				editor.putString("uid", uid);
				editor.putString("first_name", null);
				editor.putString("last_name", null);
				editor.putString("email", null);
				editor.putString("provider", Constants.PROVIDER_TWITTER);
				editor.commit();

				LoginSocialAsyncTask async = new LoginSocialAsyncTask(this,
						this);
				async.execute(uid, Constants.PROVIDER_TWITTER, username);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String access_token = mSpLogin.getString("access_token", null);
		String username = mSpLogin.getString("username", null);
		if (access_token != null && username != null) {
			startActivity(new Intent(this, MapActivity.class));
			finish();
		}
		super.onResume();
	}

	private void showToast(String st) {
		Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mPlusClient.isConnected()) {
			mPlusClient.clearDefaultAccount();
			mPlusClient.disconnect();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void loginTwitter() {

		mIsTwitter = true;
		try {

			mTwitter.setOAuthAccessToken(null);
			try {
				mRequestToken = mTwitter
						.getOAuthRequestToken(Constants.CALLBACK_URL);
				Intent intent = new Intent(LoginActivity.this,
						TwitterLoginActivity.class);
				intent.putExtra(Constants.IEXTRA_AUTH_URL,
						mRequestToken.getAuthorizationURL());
				startActivityForResult(intent, Constants.LOGIN_VIA_TWITTER);

			} catch (TwitterException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void logoutFromTwitter() {
		SharedPreferences pref = getSharedPreferences(Constants.TWITTER_LOG,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(Constants.PREF_KEY_ACCESS_TOKEN);
		editor.remove(Constants.PREF_KEY_ACCESS_TOKEN_SECRET);
		editor.commit();

		if (mTwitter != null) {
			mTwitter.shutdown();
		}
	}

	private void check_token() {
		boolean done = mSpLogin.getBoolean("do", false);
		if (done) {
			String uid = mSpLogin.getString("uid", "");
			String provider = mSpLogin.getString("provider", "");
			if (provider.equals(Constants.PROVIDER_TWITTER)) {
				String username = mSpLogin.getString("username", "");
				LoginSocialAsyncTask async = new LoginSocialAsyncTask(this,
						this);
				async.execute(uid, provider, username);
			}
			if (provider.equals(Constants.PROVIDER_GOOGLE)) {
				LoginSocialAsyncTask async = new LoginSocialAsyncTask(this,
						this);
				async.execute(uid, provider);
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		check_token();
		if (requestCode == Constants.REGISTER) {
			if (resultCode == RESULT_OK) {
				startActivity(new Intent(LoginActivity.this, MapActivity.class));
			}
		}
		if (requestCode == Constants.LOGIN_VIA_TWITTER) {
			if (resultCode == RESULT_OK) {
				try {
					String oauthVerifier = intent.getExtras().getString(
							Constants.IEXTRA_OAUTH_VERIFIER);
					twitter4j.auth.AccessToken accessToken = mTwitter
							.getOAuthAccessToken(mRequestToken, oauthVerifier);
					// SharedPreferences pref = getSharedPreferences(
					// Constants.TWITTER_LOG, MODE_PRIVATE);
					// SharedPreferences.Editor editor = pref.edit();
					// editor.putString(Constants.PREF_KEY_ACCESS_TOKEN,
					// accessToken.getToken());
					// editor.putString(Constants.PREF_KEY_ACCESS_TOKEN_SECRET,
					// accessToken.getTokenSecret());
					// editor.commit();
				} catch (TwitterException e) {
					e.printStackTrace();
				}

			} else if (resultCode == RESULT_CANCELED) {
			}
		}
		if (requestCode == Constants.LOGIN_VIA_GOOGLE
				&& resultCode == RESULT_OK) {
			mPlusClient.connect();

		}
	}

	@SuppressWarnings("deprecation")
	private void loginFacebook() {
		mIsFacebook = true;
		mFacebook.authorize(LoginActivity.this, new DialogListener() {

			@Override
			public void onFacebookError(FacebookError e) {

			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onComplete(Bundle values) {
				Editor editor = mSpLogin.edit();
				editor.putBoolean("do", true);
				try {
					String json = mFacebook.request("me");
					JSONObject obj = Util.parseJson(json);
					String uid = obj.getString("id");
					String firstname = obj.getString("first_name");
					String lastname = obj.getString("last_name");
					String email = obj.getString("email");
					String username = obj.getString("username");
					editor.putString("uid", uid);
					editor.putString("first_name", firstname);
					editor.putString("last_name", lastname);
					editor.putString("email", email);
					editor.putString("username", username);
					editor.putString("provider", Constants.PROVIDER_FACEBOOK);
					editor.commit();
					LoginSocialAsyncTask async = new LoginSocialAsyncTask(
							LoginActivity.this, LoginActivity.this);
					async.execute(uid, Constants.PROVIDER_FACEBOOK);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onCancel() {
				mIsFacebook = false;
			}
		});
	}

	@SuppressLint("NewApi")
	public void forgotpassword() {
		final Dialog dialog = new Dialog(LoginActivity.this,
				R.style.CustomDialogThemeNoTitle);
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
				ForGotPasswordAsyncTask async = new ForGotPasswordAsyncTask(
						LoginActivity.this, LoginActivity.this);
				async.execute(email);
				dialog.dismiss();
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

		if (mIsDialogshowing) {
			if (mIsFacebook)
				try {
					mFacebook.logout(getApplicationContext());
					mIsFacebook = false;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (mIsTwitter) {
				logoutFromTwitter();
				mIsTwitter = false;
			}
			if (mIsGooglePlus) {
				mPlusClient.disconnect();
				mIsGooglePlus = false;
			}
		}
		super.onBackPressed();

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Person user = mPlusClient.getCurrentPerson();
		if (user != null) {
			String uid = user.getId();
			String username = user.getNickname();
			String email = mPlusClient.getAccountName();
			Editor editor = mSpLogin.edit();
			editor.putString("uid", uid);
			editor.putString("first_name", null);
			editor.putString("last_name", null);
			editor.putString("email", email);
			editor.putString("username", username);
			editor.putString("provider", Constants.PROVIDER_GOOGLE);
			editor.commit();
			LoginSocialAsyncTask async = new LoginSocialAsyncTask(
					LoginActivity.this, LoginActivity.this);
			async.execute(uid, Constants.PROVIDER_GOOGLE);
		}

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {

		mConnectionResult = result;
		loginGoogle();
	}

	@Override
	public void onDisconnected() {
	}

	@Override
	public void onConnectionSuspended(int arg0) {

	}

	@Override
	public void onLoginNomalListenerComplete() {
		startActivity(new Intent(this, MapActivity.class));
		finish();
	}

	@Override
	public void onLoginNomalListenerFailed() {
		String st = mSpLogin.getString("error", "");
		Editor editor = mSpLogin.edit();
		editor.remove("error");
		editor.commit();
		showToast(st);
	}

	@Override
	public void onForGotPasswordListenerComplete() {
		showToast("A email was sent to your mail !");
	}

	@Override
	public void onForGotPasswordListenerFailed() {
		showToast("Failed ! ");
	}

	@Override
	public void onLoginSocialListenerComplete() {
		startActivity(new Intent(LoginActivity.this, MapActivity.class));
		finish();
	}

	@Override
	public void onLoginSocialListenerFailed() {
		String username = mSpLogin.getString("username", "");
		final Dialog dialog = new Dialog(LoginActivity.this,
				R.style.CustomDialogThemeNoTitle);
		dialog.setContentView(R.layout.dialog_sociallogin);

		Button submitBtn = (Button) dialog
				.findViewById(R.id.sociallogin_bt_submit);
		final EditText EtUsername = (EditText) dialog
				.findViewById(R.id.sociallogin_et_username);
		final EditText EtZipcode = (EditText) dialog
				.findViewById(R.id.sociallogin_et_zipcode);
		EtUsername.setText(username);
		submitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = EtUsername.getText().toString();
				String zipcode = EtZipcode.getText().toString();
				Editor editor = mSpLogin.edit();
				editor.putString("zip", zipcode);
				editor.commit();

				RegisterSocialAsyncTask async = new RegisterSocialAsyncTask(
						LoginActivity.this, LoginActivity.this);
				String uid = mSpLogin.getString("uid", null);
				String email = mSpLogin.getString("email", null);
				String provider = mSpLogin.getString("provider", null);
				async.execute(uid, provider, email, username, "123456", "", "");
			}
		});
		dialog.show();
	}

	@Override
	public void onRegisterSocialListenerComplete() {
		startActivity(new Intent(LoginActivity.this, MapActivity.class));
		finish();
	}

	@Override
	public void onRegisterSocialListenerFailed() {
		Editor editor = mSpLogin.edit();
		editor.remove("username");
		editor.remove("access_token");
		editor.commit();
		showToast("Failed to connect by social account");
	}

}
