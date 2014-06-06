package com.example.demointership.activity;

import java.io.File;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.demointership.R;
import com.example.demointership.asynctask.RegisterNomalAsyncTask;
import com.example.demointership.listener.RegisterNomalListener;

public class RegisterActivity extends Activity implements RegisterNomalListener {
	EditText mEtFirstName, mEtLastName, mEtZipcode, mEtEmail, mEtUsername,
			mEtPassword, mEtConfirmPassword;
	Button mBtSubmit;
	ImageButton mIbAvatar;

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();
	}

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

	public boolean check(String... params) {
		for (String item : params)
			if (item.trim().length() == 0)
				return false;
		return true;

	}

	public void showToast(String st) {
		Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
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

			if (check(firstname, lastname, zipcode, email, username, password,
					confirmpassword)) {
				showToast("Please fill all value !");
			} else {
				if (!password.equals(confirmpassword)) {
					showToast("Password and Confirm Password are not correct !");
				} else {
					RegisterNomalAsyncTask async = new RegisterNomalAsyncTask(
							this, this);
					async.execute(username, email, password, firstname,
							lastname, zipcode);
				}
			}

			break;
		case R.id.register_ib_avatar: // image Button avatar

			setAvatar();
			break;
		}
	}

	private void setAvatar() {
		final Dialog dialog = new Dialog(RegisterActivity.this,
				R.style.CustomDialogThemeNoTitle);
		dialog.setContentView(R.layout.dialog_getavatar);
		Button btCancel = (Button) dialog.findViewById(R.id.avatar_cancel);
		Button btChoose = (Button) dialog.findViewById(R.id.avatar_chooseimage);
		Button btTake = (Button) dialog.findViewById(R.id.avatar_takecamera);
		btCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		btChoose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ChooseOnLibrary();
				dialog.dismiss();
			}

		});
		btTake.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TakeAPhoto();
				dialog.dismiss();
			}

		});
		dialog.show();
	}

	private void TakeAPhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1);
	}

	private void ChooseOnLibrary() {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, 2);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				Bitmap imageBitmap = (Bitmap) extras.get("data");
				mIbAvatar.setImageBitmap(imageBitmap);

				String root = Environment.getExternalStorageDirectory()
						.toString();
				File myDir = new File(root + "/mymenu/avatar");
				myDir.mkdirs();
				String fname = "avatar.jpg";
				Toast.makeText(this,
						"Image saved to:\n" + myDir.toString() + fname,
						Toast.LENGTH_SHORT).show();
				File file = new File(myDir, fname);
				if (file.exists())
					file.delete();
				try {
					FileOutputStream out = new FileOutputStream(file);
					imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
					out.flush();
					out.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (resultCode == RESULT_CANCELED) {
			} else {
			}
		}
		if (requestCode == 2) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String filePath = cursor.getString(columnIndex);
			cursor.close();

			Bitmap imageBitmap = BitmapFactory.decodeFile(filePath);
			mIbAvatar.setImageBitmap(imageBitmap);

			String root = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(root + "/mymenu/avatar");
			myDir.mkdirs();
			String fname = "avatar.jpg";
			Toast.makeText(this,
					"Image saved to:\n" + myDir.toString() + fname,
					Toast.LENGTH_SHORT).show();
			File file = new File(myDir, fname);
			if (file.exists())
				file.delete();
			try {
				FileOutputStream out = new FileOutputStream(file);
				imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.flush();
				out.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void onRegisterNomalListenerComplete() {
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public void onRegisterNomalListenerFailed() {
		SharedPreferences SpLogin = getSharedPreferences("CurrentUser", 0);
		String st = SpLogin.getString("errors", "");
		showToast(st);
		Editor editor = SpLogin.edit();
		editor.remove("id");
		editor.remove("username");
		editor.remove("email");
		editor.remove("first_name");
		editor.remove("last_name");
		editor.remove("zip");
		editor.remove("access_token");
		editor.remove("city");
		editor.remove("state");
		editor.remove("point");
		editor.remove("dinner_status");
		editor.commit();

	}

}
