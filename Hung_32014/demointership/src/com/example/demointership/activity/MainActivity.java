package com.example.demointership.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.demointership.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		int i = 1000;
		while (true) {

			try {
				Thread.sleep(100);
				Log.e("test", "ping");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i = i - 100;
			if (i == 0) {

				startActivity(new Intent("android.intent.action.LOGIN"));
				finish();
				break;
			}
		}
	}

}
