package com.example.demointership.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

/**
 * This class provides common variables and methods that are used for all
 * activities.
 * 
 * @author Chinhnd
 * 
 */
public class BaseActivity extends Activity {
	/**
	 * A dialog showing a progress indicator and an optional text message or
	 * view.
	 */
	protected ProgressDialog mProgressDialog;
	/** the flag to know that this activity has been destroyed. */
	protected boolean mIsDestroy = false;
	/** the flag to know that this activity has been stopped. */
	protected boolean mIsStop = false;
	/** the flag to know that this activity has been paused. */
	protected boolean mIsPause = false;
	/**
	 * this flag is used to avoid execute an action many times when user click
	 * quickly.
	 */
	private boolean mIsCanClick = true;
	/**
	 * Showing a quick little message for the user. It's will be cancel when
	 * finish activity.
	 */
	private Toast mToast;
	/**
	 * true: start other activity to update current data. Otherwise, don't
	 * update current data.
	 */
	protected boolean mIsStartedOtherActivity = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		System.gc();
		System.runFinalization();
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.gc();
		System.runFinalization();
		mIsPause = false;
		mIsStop = false;
		mIsCanClick = true;
		mIsStartedOtherActivity = false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		dismissProgress();

		System.gc();
		System.runFinalization();

		mIsPause = true;
		mIsCanClick = false;
	}

	@Override
	protected void onStop() {
		mIsStop = true;
		if (mToast != null) {
			cancelToast();
		}
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mIsDestroy = true;
		mToast = null;
		mProgressDialog = null;

		System.gc();
		System.runFinalization();
	}

	@Override
	public void startActivity(Intent intent) {
		mIsStartedOtherActivity = true;
		super.startActivity(intent);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		mIsStartedOtherActivity = true;
		super.startActivityForResult(intent, requestCode);
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
	}

	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
	}

	/**
	 * show progress dialog.
	 * 
	 * @param msgResId
	 */
	public void showProgress(int msgResId) {
		showProgress(msgResId, null);
	}

	/**
	 * mIsDestroy = true;
	 * 
	 * @param msgResId
	 * @param keyListener
	 */
	public void showProgress(int msgResId,
			DialogInterface.OnKeyListener keyListener) {

		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			return;
		}
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setIndeterminate(true);
		if (msgResId != 0) {
			mProgressDialog.setMessage(getResources().getString(msgResId));
		}

		if (keyListener != null) {
			mProgressDialog.setOnKeyListener(keyListener);
		} else {
			mProgressDialog.setCancelable(false);
		}
		mProgressDialog.show();
	}

	/**
	 * cancel progress dialog.
	 */
	public void dismissProgress() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	/**
	 * Show message by the Toast object, it will be canceled when finish this
	 * activity.
	 * 
	 * @param msg
	 *            message muốn hiển thị
	 */
	public final void showToastMessage(final CharSequence msg) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), "",
					Toast.LENGTH_SHORT);
		}

		if (mToast != null) {
			// Cancel last message toast
			if (mToast.getView().isShown()) {
				mToast.cancel();
			}
			mToast.setText(msg);
			mToast.show();
		}
	}

	/**
	 * Show message by the Toast object, it will be canceled when finish this
	 * activity.
	 * 
	 * @param resId
	 *            id of message wants to show
	 */
	public final void showToastMessage(final int resId) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), "",
					Toast.LENGTH_SHORT);
		}

		if (mToast != null) {
			if (!mToast.getView().isShown()) {
				mToast.cancel();
			}
			mToast.setText(getText(resId));
			mToast.show();
		}
	}

	/**
	 * Cancel toast.
	 */
	public final void cancelToast() {
		if (mToast != null && mToast.getView().isShown()) {
			mToast.cancel();
		}
	}

	/**
	 * Hiển thị Toast Message và sẽ KHÔNG cancel khi finish activity
	 * 
	 * @param msg
	 *            message muốn hiển thị
	 */
	public final void showToastMessageNotRelease(final CharSequence msg) {
		Toast toast = Toast.makeText(getApplicationContext(), "",
				Toast.LENGTH_LONG);
		if (toast != null) {
			// Cancel last message toast
			if (toast.getView().isShown()) {
				toast.cancel();
			}
			toast.setText(msg);
			toast.show();
		}
	}

	/**
	 * Show toast message, the message is not canceled when finish this
	 * activity.
	 * 
	 * @param msg
	 *            message wants to show
	 */
	public final void showToastMessageNotRelease(final int resId) {
		Toast toast = Toast.makeText(getApplicationContext(), "",
				Toast.LENGTH_LONG);
		if (toast != null) {
			if (!toast.getView().isShown()) {
				toast.cancel();
			}
			toast.setText(getText(resId));
			toast.show();
		}
	}

	/**
	 * used to check a click action will be execute or not.
	 * 
	 * @return true: can click a. false: can not click.
	 */
	public boolean isCanClick() {
		return mIsCanClick;
	}

	// @Override
	// public void onBackPressed() {
	// // do nothing
	// }

}
