package com.example.demointership.activity;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.demointership.R;

public class TwitterLoginActivity extends Activity {

	public static final String CONSUMER_KEY = "sdOjEI2cOxzTLHMCCMmuQ";
	public static final String CONSUMER_SECRET = "biI3oxIBX2QMzUIVaW1wVAXygbynuS80pqSliSDTc";
	public static final String CALLBACK_URL = "myapp://LoginActivity";

	public static final String IEXTRA_AUTH_URL = "auth_url";
	public static final String IEXTRA_OAUTH_VERIFIER = "oauth_verifier";
	public static final String IEXTRA_OAUTH_TOKEN = "oauth_token";

	public static final String PREF_NAME = "twitterLog";
	public static final String PREF_KEY_ACCESS_TOKEN = "access_token";
	public static final String PREF_KEY_ACCESS_TOKEN_SECRET = "access_token_secret";

	@SuppressLint("SetJavaScriptEnabled")
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_logintwitter);

		WebView webView = (WebView) findViewById(R.id.logintwitter_wv_twitter);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				boolean result = true;
				if (url != null && url.startsWith(CALLBACK_URL)) {
					Uri uri = Uri.parse(url);
					Log.v("", url);
					if (uri.getQueryParameter("denied") != null) {
						setResult(RESULT_CANCELED);
						finish();
					} else {
						String oauthToken = uri
								.getQueryParameter("oauth_token");
						String oauthVerifier = uri
								.getQueryParameter("oauth_verifier");

						Intent intent = getIntent();
						intent.putExtra(IEXTRA_OAUTH_TOKEN, oauthToken);
						intent.putExtra(IEXTRA_OAUTH_VERIFIER, oauthVerifier);
						setResult(RESULT_OK, intent);
						finish();
					}
				} else {
					result = super.shouldOverrideUrlLoading(view, url);
				}
				return result;
			}
		});
		webView.loadUrl(this.getIntent().getExtras().getString("auth_url"));
	}
}