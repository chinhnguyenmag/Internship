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
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.demointership.R;
import com.example.demointership.Util.Constants;
import com.example.demointership.Util.Utils;
import com.example.demointership.asyntask.asynctaskforgotpassword;
import com.example.demointership.asyntask.asynctasklogin;
import com.example.demointership.asyntask.asynctasksociallogin;
import com.example.demointership.asyntask.asynctasksocialregister;
import com.example.demointership.model.UserDetail;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.PlusClient;

@SuppressLint("NewApi")
public class LoginActivity extends Activity
// implements
// ConnectionCallbacks,
// OnConnectionFailedListener,
// com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks
{
	Button mBtSubmit, mBtCreateAccount, mBtForgotPassword;
	ImageButton mIbFacebook, mIbTwitter, mIbGoogle;
	EditText mEtUsername, mEtPassword;
	Facebook mFacebook;
	private ConnectionResult mConnectionResult;
	private Twitter mTwitter;
	private RequestToken mRequestToken;
	Dialog mDialogSocial;
	boolean mIsDialogshowing = false;
	boolean mIsGooglePlus = false, mIsFacebook = false, mIsTwitter = false;
	SharedPreferences mSpLogin;
	PlusClient mPlusClient;
	ProgressDialog mProgressLoading;

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
		mSpLogin = getSharedPreferences("CurrentUser", 0);
		// mPlusClient = new PlusClient.Builder(this, this, this).setActions(
		// "http://schemas.google.com/AddActivity",
		// "http://schemas.google.com/BuyActivity")
		// /* .setScopes("https://www.googleapis.com/auth/userinfo.email") */
		// .build();
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(Constants.CONSUMER_KEY);
		builder.setOAuthConsumerSecret(Constants.CONSUMER_SECRET);
		Configuration configuration = builder.build();

		TwitterFactory factory = new TwitterFactory(configuration);
		mTwitter = factory.getInstance();
		showDialogSocialLogin();
		if (android.os.Build.VERSION.SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		Toast.makeText(getApplicationContext(), "oncreate", Toast.LENGTH_SHORT)
				.show();
		// mProgressLoading.setTitle("loading...");
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

		Intent intent = AccountPicker.newChooseAccountIntent(null, null,
				new String[] { "com.google" }, false, null, null, null, null);
		startActivityForResult(intent, Constants.ACCOUNT_PICKER);
		// mIsGooglePlus = true;
		// if (!mPlusClient.isConnected() && mConnectionResult.hasResolution())
		// {
		// try {
		// mConnectionResult.startResolutionForResult(LoginActivity.this,
		// Constants.LOGIN_VIA_GOOGLE);
		// } catch (Exception e) {
		// mConnectionResult = null;
		// mPlusClient.connect();
		// }
		// } else if (mPlusClient.isConnected()) {
		// mPlusClient.disconnect();
		// }

	}

	@Override
	protected void onStart() {
		super.onStart();
		// mPlusClient.connect();

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT)
				.show();
		String name = mSpLogin.getString("username", null);
		if (name != null) {
			startActivity(new Intent(LoginActivity.this, SuccessActivity.class));
		}
		// facebook();
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

				asynctasksociallogin async = new asynctasksociallogin(
						LoginActivity.this);
				async.execute(uid, Constants.PROVIDER_TWITTER, username);
				UserDetail userDetail = null;
				userDetail = async.get();
				if (userDetail.getStatus().equals("success")) {
					editor = mSpLogin.edit();
					editor.putString("username", userDetail.getUsername());
					editor.putString("userPhotoImageURL",
							userDetail.getUserPhotoImageURL());
					editor.commit();
					startActivity(new Intent(LoginActivity.this,
							SuccessActivity.class));
				} else {
					mDialogSocial.show();
					mIsDialogshowing = true;
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (TwitterException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		super.onResume();
	}

	private void facebook() {
		if (mFacebook.isSessionValid()) {

		}

	}

	@Override
	protected void onStop() {
		super.onStop();
		// mPlusClient.disconnect();
		// if (mPlusClient.isConnecting()) {
		// mPlusClient
		// .revokeAccessAndDisconnect(new OnAccessRevokedListener() {
		//
		// @Override
		// public void onAccessRevoked(ConnectionResult status) {
		// Toast.makeText(getApplicationContext(),
		// "onAccessRevoked", Toast.LENGTH_SHORT)
		// .show();
		// }
		// });
		// }
	}

	@Override
	protected void onDestroy() {
		// mPlusClient.disconnect();
		// mFacebook = null;
		// mTwitter = null;
		super.onDestroy();
	}

	private void loginTwitter() {

		mIsTwitter = true;
		try {

			 mTwitter.setOAuthAccessToken(null);
			 try {
					mRequestToken = mTwitter.getOAuthRequestToken(Constants.CALLBACK_URL);
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

		Toast.makeText(LoginActivity.this, "unauthorized", Toast.LENGTH_SHORT)
				.show();
	}
	private void check_token()  {
		boolean done = mSpLogin.getBoolean("do", false);
		if (done) {
			String uid = mSpLogin.getString("uid", "");
			String provider = mSpLogin.getString("provider", "");
			if (provider.equals(Constants.PROVIDER_FACEBOOK)) {
				asynctasksociallogin async = new asynctasksociallogin(LoginActivity.this);
				UserDetail userDetail = null;
				async.execute(uid,provider);
				try {
					userDetail = async.get();
					if (userDetail.getStatus().equals("success")){
						Editor editor = mSpLogin.edit();
						editor.putString("username", userDetail.getUsername());
						editor.commit();
						startActivity(new Intent(LoginActivity.this, SuccessActivity.class));
						finish();
					} else {
						mDialogSocial.show();
					}
				} catch (Exception e ){
					
				}
			}
			if (provider.equals(Constants.PROVIDER_TWITTER)) {
				String username = mSpLogin.getString("username","");
				asynctasksociallogin async = new asynctasksociallogin(LoginActivity.this);
				UserDetail userDetail = null;
				async.execute(uid,provider,username);
				try {
					userDetail = async.get();
					if (userDetail.getStatus().equals("success")){
						Editor editor = mSpLogin.edit();
						editor.putString("username", userDetail.getUsername());
						editor.commit();
						startActivity(new Intent(LoginActivity.this, SuccessActivity.class));
						finish();
					} else {
						mDialogSocial.show();
					}
				} catch (Exception e ){
					
				}
			}
			if (provider.equals(Constants.PROVIDER_GOOGLE)) {
				asynctasksociallogin async = new asynctasksociallogin(LoginActivity.this);
				UserDetail userDetail = null;
				async.execute(uid,provider);
				try {
					userDetail = async.get();
					if (userDetail.getStatus().equals("success")){
						Editor editor = mSpLogin.edit();
						editor.putString("username", userDetail.getUsername());
						editor.commit();
						startActivity(new Intent(LoginActivity.this, SuccessActivity.class));
						finish();
					} else {
						mDialogSocial.show();
					}
				} catch (Exception e ){
					
				}
			}

		}
	}
	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		// mFacebook.authorizeCallback(requestCode, resultCode, intent);
		// facebook();
		check_token();
		if (requestCode == Constants.REGISTER) {
			if (resultCode == RESULT_OK) {
				startActivity(new Intent(LoginActivity.this,
						SuccessActivity.class));
			}
		}
		if (requestCode == Constants.LOGIN_VIA_TWITTER) {
			if (resultCode == RESULT_OK) {
				try {
					String oauthVerifier = intent.getExtras().getString(
							Constants.IEXTRA_OAUTH_VERIFIER);
					AccessToken accessToken = mTwitter.getOAuthAccessToken(
							mRequestToken, oauthVerifier);
					SharedPreferences pref = getSharedPreferences(
							Constants.TWITTER_LOG, MODE_PRIVATE);
					SharedPreferences.Editor editor = pref.edit();
					editor.putString(Constants.PREF_KEY_ACCESS_TOKEN,
							accessToken.getToken());
					editor.putString(Constants.PREF_KEY_ACCESS_TOKEN_SECRET,
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
		if (requestCode == Constants.LOGIN_VIA_GOOGLE) {
			if (resultCode != RESULT_OK) {
				Toast.makeText(getApplicationContext(),
						"onActivityResult isGoogle", Toast.LENGTH_SHORT).show();
				mConnectionResult = null;
				mPlusClient.connect();
			} else {
				mIsGooglePlus = false;
			}
		}
		if (requestCode == Constants.ACCOUNT_PICKER && resultCode == RESULT_OK) {
			// AccountManager manager = AccountManager.get(this);
			String accountName = intent
					.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
			String uid = intent.getStringExtra(AccountManager.KEY_ACCOUNTS);
			Toast.makeText(getApplicationContext(), accountName + " " + uid,
					Toast.LENGTH_SHORT).show();
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
					Log.d("facebook", "try");
					String json = mFacebook.request("me");
					JSONObject obj = Util.parseJson(json);
					String uid = obj.getString("id");
					String firstname = obj.getString("first_name");
					String lastname = obj.getString("last_name");
					String email = obj.getString("email");
					String username = obj.getString("username");
					// Editor editor = mSpLogin.edit();
					editor.putString("uid", uid);
					editor.putString("first_name", firstname);
					editor.putString("last_name", lastname);
					editor.putString("email", email);
					editor.putString("username", username);
					editor.putString("provider", Constants.PROVIDER_FACEBOOK);
					editor.commit();
					asynctasksociallogin async = new asynctasksociallogin(
							LoginActivity.this);
					async.execute(uid, Constants.PROVIDER_FACEBOOK);
					UserDetail userDetai = null;
					Log.d("facebook", "userdetail");
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
						editor.commit();
						startActivity(new Intent(LoginActivity.this,
								SuccessActivity.class));
						finish();

					} else {
						mDialogSocial.show();
						mIsDialogshowing = true;
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
				facebook();
				Toast.makeText(getApplicationContext(), "Successfull !",
						Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onCancel() {
				mIsFacebook = false;
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
				asynctaskforgotpassword async = new asynctaskforgotpassword(
						LoginActivity.this);
				UserDetail userDetail = null;

				try {
					async.execute(email);
					userDetail = async.get();

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (userDetail.getStatus().equals("success")) {
					Toast.makeText(getApplicationContext(), "OK",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "FAILED",
							Toast.LENGTH_SHORT).show();
				}
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
				String email = mSpLogin.getString("email", null);
				String provider = mSpLogin.getString("provider", null);

				async.execute(uid, provider, email, username, "123456", "", "");
				UserDetail userDetail = null;
				try {
					userDetail = async.get();
				} catch (InterruptedException e) {

					e.printStackTrace();
				} catch (ExecutionException e) {

					e.printStackTrace();
				}
				if (userDetail.getStatus().equals("success")) {
					mDialogSocial.dismiss();
					startActivity(new Intent(LoginActivity.this,
							SuccessActivity.class));
				} else {
					mDialogSocial.dismiss();
					Toast.makeText(getApplicationContext(),
							"register social failed ! ", Toast.LENGTH_SHORT)
							.show();
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

			}
		});

	}

	// @Override
	// public void onConnected(Bundle connectionHint) {
	//
	// Person user = mPlusClient.getCurrentPerson();
	// if (user != null) {
	// String uid = user.getId();
	// String username = user.getNickname();
	// // GoogleApiClient googleApi;
	// String email = mPlusClient.getAccountName();
	// Editor editor = mSpLogin.edit();
	// editor.putString("uid", uid);
	// editor.putString("first_name", null);
	// editor.putString("last_name", null);
	// editor.putString("email", email);
	// editor.putString("username", username);
	// editor.putString("provider", Constants.PROVIDER_GOOGLE);
	// editor.commit();
	// asynctasksociallogin async = new asynctasksociallogin(
	// LoginActivity.this);
	// async.execute(uid, Constants.PROVIDER_GOOGLE);
	// UserDetail userDetail = null;
	// try {
	// userDetail = async.get();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// Log.d("", "async.execute");
	// if (userDetail.getStatus().equals("success")) {
	// editor = mSpLogin.edit();
	// editor.putString("username", userDetail.getUsername());
	// editor.putString("userPhotoImageURL",
	// userDetail.getUserPhotoImageURL());
	// editor.commit();
	// Log.d("", "startActivity");
	// startActivity(new Intent(LoginActivity.this,
	// SuccessActivity.class));
	// } else {
	// mDialogSocial.show();
	// mIsDialogshowing = true;
	// }
	// }
	//
	// // }
	// }
	//
	// @Override
	// public void onConnectionFailed(ConnectionResult result) {
	// //
	// Toast.makeText(getApplicationContext(), "onConnectionFailed",
	// Toast.LENGTH_SHORT).show();
	// mConnectionResult = result;
	// }
	//
	// @Override
	// public void onDisconnected() {
	// Toast.makeText(getApplicationContext(), "onDisconnected",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// @Override
	// public void onConnectionSuspended(int arg0) {

	// }
}
