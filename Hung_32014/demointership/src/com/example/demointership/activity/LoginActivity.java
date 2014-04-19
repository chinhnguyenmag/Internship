package com.example.demointership.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
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
import com.example.demointership.asyntask.asynctasksociallogin;
import com.example.demointership.asyntask.asynctasksocialregister;
import com.example.demointership.model.UserDetail;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class LoginActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener {
	Button mBtSubmit, mBtCreateAccount, mBtForgotPassword;
	ImageButton mIbFacebook, mIbTwitter, mIbGoogle;
	EditText mEtUsername, mEtPassword;
	public int REGISTER = 1, FORGOT = 2, FACEBOOK = 3, TWITTER = 4, GOOGLE = 5;
	private static final String APP_ID = "422350967898025";
	Facebook mFacebook;
	private ConnectionResult mConnectionResult;
	private GoogleApiClient mGoogleApiClient;
	// private boolean mIntentInProgress;

	private static final String CONSUMER_KEY = "sdOjEI2cOxzTLHMCCMmuQ";
	private static final String CONSUMER_SECRET = "biI3oxIBX2QMzUIVaW1wVAXygbynuS80pqSliSDTc";
	private static final String CALLBACK_URL = "myapp://LoginActivity";

	private static final String IEXTRA_AUTH_URL = "auth_url";
	private static final String IEXTRA_OAUTH_VERIFIER = "oauth_verifier";
	private static final String IEXTRA_OAUTH_TOKEN = "oauth_token";
	public static final String PROVIDER_FACEBOOK = "facebook";
	public static final String PROVIDER_TWITTER = "twitter";
	public static final String PROVIDER_GOOGLE = "google_oauth2";
	private static final String TWITTER_LOG = "twitterLog";
	private static final String PREF_KEY_ACCESS_TOKEN = "access_token";
	private static final String PREF_KEY_ACCESS_TOKEN_SECRET = "access_token_secret";
	private Twitter mTwitter;
	private RequestToken mRequestToken;
	Dialog mDialogSocial;
	boolean mIsDialogshowing = false;
	boolean mIsGooglePlus = false, mIsFacebook = false, mIsTwitter = false;
	SharedPreferences mSpLogin;

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
		mSpLogin = getSharedPreferences("CurrentUser", 0);
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
			Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT)
					.show();
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

				e.printStackTrace();
			} catch (ExecutionException e) {

				e.printStackTrace();
			}
			if (userdetail.getStatus().equals("success")) {
				startActivity(new Intent(LoginActivity.this,
						SuccessActivity.class));
			} else {
				Toast.makeText(getApplicationContext(), userdetail.getError(),
						Toast.LENGTH_SHORT).show();
			}
		} else
			Toast.makeText(getApplicationContext(), "Insert data",
					Toast.LENGTH_SHORT).show();
	}

	private void loginGoogle() {
		mIsGooglePlus = true;
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API, null)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();
		if (!mGoogleApiClient.isConnecting()) {
			resolveSignInError();
		}

	}

	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mConnectionResult.startResolutionForResult(this, GOOGLE);
			} catch (SendIntentException e) {
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

	private void loginTwitter() {
		mIsTwitter = true;
		try {
			ConfigurationBuilder confbuilder = new ConfigurationBuilder();
			Configuration conf = confbuilder.setOAuthConsumerKey(CONSUMER_KEY)
					.setOAuthConsumerSecret(CONSUMER_SECRET).build();
			mTwitter = new TwitterFactory(conf).getInstance();
			mTwitter.setOAuthAccessToken(null);
			try {
				mRequestToken = mTwitter.getOAuthRequestToken(CALLBACK_URL);
				// startActivity(new Intent(Intent.ACTION_VIEW,
				// Uri.parse(mRequestToken.getAuthenticationURL())));
				Intent intent = new Intent(LoginActivity.this,
						TwitterLoginActivity.class);
				intent.putExtra(IEXTRA_AUTH_URL,
						mRequestToken.getAuthorizationURL());
				startActivityForResult(intent, TWITTER);

			} catch (TwitterException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void logoutFromTwitter() {
		// Clear the shared preferences
		SharedPreferences pref = getSharedPreferences(TWITTER_LOG, MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(PREF_KEY_ACCESS_TOKEN);
		editor.remove(PREF_KEY_ACCESS_TOKEN_SECRET);
		editor.commit();

		if (mTwitter != null) {
			mTwitter.shutdown();
		}

		Toast.makeText(LoginActivity.this, "unauthorized", Toast.LENGTH_SHORT)
				.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		mFacebook.authorizeCallback(requestCode, resultCode, intent);
		if (requestCode == REGISTER) {
			if (resultCode == RESULT_OK) {
				startActivity(new Intent(LoginActivity.this,
						SuccessActivity.class));
			}
		}
		if (requestCode == TWITTER) {
			if (resultCode == RESULT_OK) {
				try {
					String oauthVerifier = intent.getExtras().getString(
							IEXTRA_OAUTH_VERIFIER);
					AccessToken accessToken = mTwitter.getOAuthAccessToken(
							mRequestToken, oauthVerifier);
					SharedPreferences pref = getSharedPreferences(TWITTER_LOG,
							MODE_PRIVATE);
					SharedPreferences.Editor editor = pref.edit();
					editor.putString(PREF_KEY_ACCESS_TOKEN,
							accessToken.getToken());
					editor.putString(PREF_KEY_ACCESS_TOKEN_SECRET,
							accessToken.getTokenSecret());
					editor.commit();
					Toast.makeText(this, "authorized", Toast.LENGTH_SHORT)
							.show();
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			} else if (resultCode == RESULT_CANCELED) {
			}
		}
		if (requestCode == GOOGLE) {
			if (resultCode != RESULT_OK) {

			}
			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
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

				try {
					String json = mFacebook.request("me");
					JSONObject obj = Util.parseJson(json);
					// String name = obj.getString("name");
					String email = obj.getString("email");
					// String access_token = mFacebook.getAccessToken();
					// long access_expires = mFacebook.getAccessExpires();
					String id = obj.getString("id");
					String firstname = obj.getString("firstname");
					String lastname = obj.getString("lastname");
					Editor editor = mSpLogin.edit();
					editor.putString("first_name", firstname);
					editor.putString("last_name", lastname);
					editor.putString("uid", id);
					editor.putString("email", email);
					editor.commit();
					asynctasksociallogin async = new asynctasksociallogin(
							getApplication());
					async.execute(id, PROVIDER_FACEBOOK, email, "");
					UserDetail userDetai = null;
					try {
						userDetai = async.get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}

					if (userDetai.getStatus().equals("success")) {
						editor = mSpLogin.edit();
						editor.putString("access_token",
								userDetai.getAccess_token());
						editor.putString("userPhotoImageURL",
								userDetai.getUserPhotoImageURL());
						editor.putString("username", userDetai.getUsername());

						// editor.putString(arg0, arg1)
						// some info
						editor.commit();
						startActivity(new Intent(LoginActivity.this,
								SuccessActivity.class));
						finish();

					} else {
						mDialogSocial.show();
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (FacebookError e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}

				Toast.makeText(getApplicationContext(), "Successfull !",
						Toast.LENGTH_SHORT).show();

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
				// mPlusClient.disconnect();
				mIsGooglePlus = false;
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

		// SharedPreferences SpLogin = getSharedPreferences("CurrentUser",
		// 0);

		submitBtn.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				String username = EtUsername.getText().toString();
				String zipcode = EtZipcode.getText().toString();
				Editor editor = mSpLogin.edit();
				editor.putString("zip", zipcode);
				editor.commit();

				asynctasksocialregister async = new asynctasksocialregister(
						LoginActivity.this);
				String uid = mSpLogin.getString("uid", null);
				String first_name = mSpLogin.getString("first_name", null);
				String last_name = mSpLogin.getString("last_name", null);
				String email = mSpLogin.getString("email", null);
				String provider = "";
				if (mIsFacebook)
					provider = PROVIDER_FACEBOOK;
				if (mIsGooglePlus)
					provider = PROVIDER_GOOGLE;
				if (mIsTwitter)
					provider = PROVIDER_TWITTER;
				async.execute(uid, provider, email, username, "123456",
						first_name, last_name);
				UserDetail userDetail = null;
				try {
					userDetail = async.get();
				} catch (InterruptedException e) {

					e.printStackTrace();
				} catch (ExecutionException e) {

					e.printStackTrace();
				}
				if (userDetail.getStatus().equals("success")) {

					startActivity(new Intent(LoginActivity.this,
							SuccessActivity.class));
				} else {
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
						// mPlusClient.disconnect();
						mIsGooglePlus = false;
					}
				}

			}
		});
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
				Editor editor = mSpLogin.edit();
				editor.putString("username", personName);
				editor.putString("email", email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}
	}
}
