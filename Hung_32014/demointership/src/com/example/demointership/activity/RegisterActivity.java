package com.example.demointership.activity;

import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.demointership.R;
import com.example.demointership.asyntask.asynctaskregister;
import com.example.demointership.model.UserDetail;

public class RegisterActivity extends Activity {
	EditText mEtFirstName, mEtLastName, mEtZipcode, mEtEmail, mEtUsername,
			mEtPassword, mEtConfirmPassword;
	Button mBtSubmit;
	ImageButton mIbAvatar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mEtFirstName = (EditText) findViewById(R.id.register_et_firstname);
		mEtLastName = (EditText) findViewById(R.id.register_et_lastname);
		mEtZipcode = (EditText) findViewById(R.id.register_et_zipcode);
		mEtEmail = (EditText) findViewById(R.id.register_et_email);
		mEtUsername = (EditText) findViewById(R.id.register_et_username);
		mEtPassword = (EditText) findViewById(R.id.register_et_password);
		mEtConfirmPassword = (EditText) findViewById(R.id.register_et_confirmpassword);
		mIbAvatar = (ImageButton) findViewById(R.id.register_ib_avatar);

	}

	@SuppressLint("ShowToast")
	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.register_bt_submit:
			String firstname = mEtFirstName.getText().toString();
			String lastname = mEtLastName.getText().toString();
			String zipcode = mEtZipcode.getText().toString();
			String email = mEtEmail.getText().toString();
			String username = mEtUsername.getText().toString();
			String password = mEtPassword.getText().toString();
			String confirmpassword = mEtConfirmPassword.getText().toString();

			if (firstname.length() == 0 || lastname.length() == 0
					|| zipcode.length() == 0 || email.length() == 0
					|| username.length() == 0 || password.length() == 0
					|| confirmpassword.length() == 0) {
				Toast.makeText(getApplicationContext(), "fill all value",
						Toast.LENGTH_LONG);
			} else {
				if (!password.equals(confirmpassword)) {
					Toast.makeText(getApplicationContext(),
							"Password and Confirm Password are not correct !",
							Toast.LENGTH_LONG);
				} else {
					asynctaskregister async = new asynctaskregister(
							RegisterActivity.this);
					async.execute();

					UserDetail responseReg = null;
					try {
						responseReg = async.get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (responseReg != null) {
						if (responseReg.status.equals("success")) {

						} else {

						}

					}
				}

			}
			break;
		}
	}

}
