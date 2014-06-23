package com.example.demointership.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.demointership.R;

public class MainActivity extends BaseActivity {
	final int TIME_DELAY = 1000; // miliseconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
				finish();
			}
		}, TIME_DELAY);

	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
	@Override
	protected void onPause() {

		super.onPause();
	}

	@Override
	protected void onRestart() {

		super.onRestart();
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	@Override
	protected void onStop() {

		super.onStop();
	}

}
