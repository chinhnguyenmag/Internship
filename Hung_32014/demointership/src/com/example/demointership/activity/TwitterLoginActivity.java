package com.example.demointership.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.demointership.R;
import com.example.demointership.Util.Constants;

public class TwitterLoginActivity extends BaseActivity {

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
				if (url != null && url.startsWith(Constants.CALLBACK_URL)) {
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
						intent.putExtra(Constants.IEXTRA_OAUTH_TOKEN,
								oauthToken);
						intent.putExtra(Constants.IEXTRA_OAUTH_VERIFIER,
								oauthVerifier);
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