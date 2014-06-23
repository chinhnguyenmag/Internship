package com.example.demointership.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edmodo.rangebar.RangeBar;
import com.edmodo.rangebar.RangeBar.OnRangeBarChangeListener;
import com.example.demointership.R;
import com.example.demointership.Util.Constants;
import com.example.demointership.Util.Temp;
import com.example.demointership.adapter.ListDialogItemTypeAdapter;
import com.example.demointership.adapter.ListDialogMenuTypeAdapter;
import com.example.demointership.asynctask.AdvanceSeachAsyncTask;
import com.example.demointership.asynctask.SaveMySearchProfileAsyncTask;
import com.example.demointership.listener.AdvanceSearchListener;
import com.example.demointership.listener.SaveMySearchProfileListener;
import com.example.demointership.model.ItemTypeObject;
import com.example.demointership.model.MenuTypeObject;

@SuppressLint("NewApi")
public class CreateMySearchActivity extends BaseActivity implements
		SaveMySearchProfileListener, AdvanceSearchListener {
	Spinner mSnMenuType, mSnFoodType;
	EditText mEtSearch;
	RangeBar mRbItemPrice, mRbItemRating, mRbServerRating, mRbDistance,
			mRbPointsOffered, mRbRestaurantRating;
	Button mBtSave, mBtSearch, mBtCancel, mBtMenuType, mBtFoodType;
	TextView mTvItemPriceLeft, mTvItemPriceRight, mTvItemRatingLeft,
			mTvItemRatingRight, mTvServerRatingLeft, mTvServerRatingRight,
			mTvDistanceLeft, mTvDistanceRight, mTvPointOfferedLeft,
			mTvPointOfferedRight, mTvRestaurantRatingLeft,
			mTvRestaurantRatingRight;
	int mWidth;

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_createmysearch);
		mBtMenuType = (Button) findViewById(R.id.createmysearch_bt_menu_type);
		mBtFoodType = (Button) findViewById(R.id.createmysearch_bt_food_type);
		mEtSearch = (EditText) findViewById(R.id.createmysearch_et_key);
		mRbItemPrice = (RangeBar) findViewById(R.id.createmysearch_rb_item_price);
		mRbItemRating = (RangeBar) findViewById(R.id.createmysearch_rb_item_rating);
		mRbServerRating = (RangeBar) findViewById(R.id.createmysearch_rb_server_rating);
		mRbDistance = (RangeBar) findViewById(R.id.createmysearch_rb_distance);
		mRbPointsOffered = (RangeBar) findViewById(R.id.createmysearch_rb_point_offred);
		mRbRestaurantRating = (RangeBar) findViewById(R.id.createmysearch_rb_restaurant_rating);
		mBtSave = (Button) findViewById(R.id.createmysearch_bt_save);
		mBtSearch = (Button) findViewById(R.id.createmysearch_bt_search);
		mBtCancel = (Button) findViewById(R.id.createmysearch_bt_cancel);
		mTvItemPriceLeft = (TextView) findViewById(R.id.createmysearch_tv_item_price_left);
		mTvItemPriceRight = (TextView) findViewById(R.id.createmysearch_tv_item_price_right);
		mTvItemRatingLeft = (TextView) findViewById(R.id.createmysearch_tv_item_rating_left);
		mTvItemRatingRight = (TextView) findViewById(R.id.createmysearch_tv_item_rating_right);
		mTvServerRatingLeft = (TextView) findViewById(R.id.createmysearch_tv_server_rating_left);
		mTvServerRatingRight = (TextView) findViewById(R.id.createmysearch_tv_server_rating_right);
		mTvDistanceLeft = (TextView) findViewById(R.id.createmysearch_tv_distance_left);
		mTvDistanceRight = (TextView) findViewById(R.id.createmysearch_tv_distance_right);
		mTvPointOfferedLeft = (TextView) findViewById(R.id.createmysearch_tv_point_offred_left);
		mTvPointOfferedRight = (TextView) findViewById(R.id.createmysearch_tv_point_offred_right);
		mTvRestaurantRatingLeft = (TextView) findViewById(R.id.createmysearch_tv_restaurant_rating_left);
		mTvRestaurantRatingRight = (TextView) findViewById(R.id.createmysearch_tv_restaurant_rating_right);
		mRbItemPrice.setTickHeight(0);
		mRbItemRating.setTickHeight(0);
		mRbServerRating.setTickHeight(0);
		mRbDistance.setTickHeight(0);
		mRbPointsOffered.setTickHeight(0);
		mRbRestaurantRating.setTickHeight(0);
		Display display = getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int width = display.getWidth(); // deprecated
		mWidth = width;
		mRbItemPrice
				.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {

					@Override
					public void onIndexChangeListener(RangeBar rangeBar,
							int leftThumbIndex, int rightThumbIndex) {
						if (leftThumbIndex < 0) {
							mRbItemPrice.setThumbIndices(0, rightThumbIndex);
							leftThumbIndex = 0;
						}
						if (rightThumbIndex > 99) {
							mRbItemPrice.setThumbIndices(leftThumbIndex, 99);
							rightThumbIndex = 99;
						}
						int tem = mWidth / 100;
						RelativeLayout.LayoutParams llpl = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT,
								RelativeLayout.LayoutParams.WRAP_CONTENT);
						llpl.setMargins(tem * leftThumbIndex + 10, 0, 0, 0);
						mTvItemPriceLeft.setLayoutParams(llpl);
						mTvItemPriceLeft.setText(String
								.valueOf(leftThumbIndex + 1) + "$");
						RelativeLayout.LayoutParams llpr = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT,
								RelativeLayout.LayoutParams.WRAP_CONTENT);
						llpr.setMargins(tem * rightThumbIndex + 10, 0, 0, 0);
						mTvItemPriceRight.setLayoutParams(llpr);
						mTvItemPriceRight.setText(String
								.valueOf(rightThumbIndex + 1) + "$");
					}
				});
		mRbItemPrice.setThumbIndices(0, 99);
		mRbItemRating
				.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {

					@Override
					public void onIndexChangeListener(RangeBar rangeBar,
							int leftThumbIndex, int rightThumbIndex) {
						if (leftThumbIndex < 0) {
							mRbItemRating.setThumbIndices(0, rightThumbIndex);
							leftThumbIndex = 0;
						}
						if (rightThumbIndex > 12) {
							mRbItemRating.setThumbIndices(leftThumbIndex, 12);
							rightThumbIndex = 12;
						}
						int tem = mWidth / 13;
						RelativeLayout.LayoutParams llpl = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT,
								RelativeLayout.LayoutParams.WRAP_CONTENT);
						llpl.setMargins(tem * leftThumbIndex + 10, 0, 0, 0);
						mTvItemRatingLeft.setLayoutParams(llpl);
						mTvItemRatingLeft
								.setText(ChangeToTextPoint(leftThumbIndex + 1));
						RelativeLayout.LayoutParams llpr = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT,
								RelativeLayout.LayoutParams.WRAP_CONTENT);
						llpr.setMargins(tem * rightThumbIndex + 10, 0, 0, 0);
						mTvItemRatingRight.setLayoutParams(llpr);
						mTvItemRatingRight
								.setText(ChangeToTextPoint(rightThumbIndex + 1));

					}
				});
		mRbItemRating.setThumbIndices(0, 12);
		mRbServerRating
				.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {

					@Override
					public void onIndexChangeListener(RangeBar rangeBar,
							int leftThumbIndex, int rightThumbIndex) {
						if (leftThumbIndex < 0) {
							mRbServerRating.setThumbIndices(0, rightThumbIndex);
							leftThumbIndex = 0;
						}
						if (rightThumbIndex > 12) {
							mRbServerRating.setThumbIndices(leftThumbIndex, 12);
							rightThumbIndex = 12;
						}
						int tem = mWidth / 13;
						RelativeLayout.LayoutParams llpl = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT,
								RelativeLayout.LayoutParams.WRAP_CONTENT);
						llpl.setMargins(tem * leftThumbIndex + 10, 0, 0, 0);
						mTvServerRatingLeft.setLayoutParams(llpl);
						mTvServerRatingLeft
								.setText(ChangeToTextPoint(leftThumbIndex + 1));
						RelativeLayout.LayoutParams llpr = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT,
								RelativeLayout.LayoutParams.WRAP_CONTENT);
						llpr.setMargins(tem * rightThumbIndex + 10, 0, 0, 0);
						mTvServerRatingRight.setLayoutParams(llpr);
						mTvServerRatingRight
								.setText(ChangeToTextPoint(rightThumbIndex + 1));

					}
				});
		mRbServerRating.setThumbIndices(0, 12);
		mRbDistance.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {

			@Override
			public void onIndexChangeListener(RangeBar rangeBar,
					int leftThumbIndex, int rightThumbIndex) {
				if (leftThumbIndex < 0) {
					mRbDistance.setThumbIndices(0, rightThumbIndex);
					leftThumbIndex = 0;
				}
				if (rightThumbIndex > 99) {
					mRbDistance.setThumbIndices(leftThumbIndex, 99);
					rightThumbIndex = 99;
				}
				int tem = mWidth / 100;
				RelativeLayout.LayoutParams llpl = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				llpl.setMargins(tem * leftThumbIndex + 10, 0, 0, 0);
				mTvDistanceLeft.setLayoutParams(llpl);
				mTvDistanceLeft.setText(String.valueOf(leftThumbIndex + 1));
				RelativeLayout.LayoutParams llpr = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				llpr.setMargins(tem * rightThumbIndex + 10, 0, 0, 0);
				mTvDistanceRight.setLayoutParams(llpr);
				mTvDistanceRight.setText(String.valueOf(rightThumbIndex + 1));
			}
		});
		mRbDistance.setThumbIndices(0, 99);
		mRbPointsOffered
				.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {

					@Override
					public void onIndexChangeListener(RangeBar rangeBar,
							int leftThumbIndex, int rightThumbIndex) {
						if (leftThumbIndex < 0) {
							mRbPointsOffered
									.setThumbIndices(0, rightThumbIndex);
							leftThumbIndex = 0;
						}
						if (rightThumbIndex > 99) {
							mRbPointsOffered
									.setThumbIndices(leftThumbIndex, 99);
							rightThumbIndex = 99;
						}
						int tem = mWidth / 100;
						RelativeLayout.LayoutParams llpl = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT,
								RelativeLayout.LayoutParams.WRAP_CONTENT);
						llpl.setMargins(tem * leftThumbIndex + 10, 0, 0, 0);
						mTvPointOfferedLeft.setLayoutParams(llpl);
						mTvPointOfferedLeft.setText(String
								.valueOf(leftThumbIndex + 1) + "pts");
						RelativeLayout.LayoutParams llpr = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT,
								RelativeLayout.LayoutParams.WRAP_CONTENT);
						llpr.setMargins(tem * rightThumbIndex + 10, 0, 0, 0);
						mTvPointOfferedRight.setLayoutParams(llpr);
						mTvPointOfferedRight.setText(String
								.valueOf(rightThumbIndex + 1) + "pts");
					}
				});
		mRbPointsOffered.setThumbIndices(0, 99);
		mRbRestaurantRating
				.setOnRangeBarChangeListener(new OnRangeBarChangeListener() {

					@Override
					public void onIndexChangeListener(RangeBar rangeBar,
							int leftThumbIndex, int rightThumbIndex) {
						if (leftThumbIndex < 0) {
							mRbRestaurantRating.setThumbIndices(0,
									rightThumbIndex);
							leftThumbIndex = 0;
						}
						if (rightThumbIndex > 12) {
							mRbRestaurantRating.setThumbIndices(leftThumbIndex,
									12);
							rightThumbIndex = 12;
						}
						int tem = mWidth / 13;
						RelativeLayout.LayoutParams llpl = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT,
								RelativeLayout.LayoutParams.WRAP_CONTENT);
						llpl.setMargins(tem * leftThumbIndex + 10, 0, 0, 0);
						mTvRestaurantRatingLeft.setLayoutParams(llpl);
						mTvRestaurantRatingLeft
								.setText(ChangeToTextPoint(leftThumbIndex + 1));
						RelativeLayout.LayoutParams llpr = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT,
								RelativeLayout.LayoutParams.WRAP_CONTENT);
						llpr.setMargins(tem * rightThumbIndex + 10, 0, 0, 0);
						mTvRestaurantRatingRight.setLayoutParams(llpr);
						mTvRestaurantRatingRight
								.setText(ChangeToTextPoint(rightThumbIndex + 1));

					}
				});
		mRbRestaurantRating.setThumbIndices(0, 12);
	}

	private String ChangeToTextPoint(int i) {
		switch (i) {
		case 1:
			return "F";
		case 2:
			return "D-";
		case 3:
			return "D";
		case 4:
			return "D+";
		case 5:
			return "C-";
		case 6:
			return "C";
		case 7:
			return "C+";
		case 8:
			return "B-";
		case 9:
			return "B";
		case 10:
			return "B+";
		case 11:
			return "A-";
		case 12:
			return "A";
		case 13:
			return "A+";
		default:
			return "";
		}
	}

	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.createmysearch_bt_save:
			showDialogSave();
			break;
		case R.id.createmysearch_bt_search:
			searchAdvance();
			break;
		case R.id.createmysearch_bt_cancel:
			setResult(RESULT_CANCELED);
			finish();
			break;
		case R.id.createmysearch_bt_food_type:
			showDialogFoodType();
			break;
		case R.id.createmysearch_bt_menu_type:
			showDialogMenuType();
			break;
		}
	}

	private void searchAdvance() {
		AdvanceSeachAsyncTask async = new AdvanceSeachAsyncTask(
				CreateMySearchActivity.this, CreateMySearchActivity.this);
		SharedPreferences sp = CreateMySearchActivity.this
				.getSharedPreferences(Constants.KEY_CURRENT_USER_XML, 0);

		int restaurant_rating_min = mRbRestaurantRating.getLeftIndex() + 1;
		int restaurant_rating_max = mRbRestaurantRating.getRightIndex() + 1;
		int item_price_min = mRbItemPrice.getLeftIndex() + 1;
		int item_price_max = mRbItemPrice.getRightIndex() + 1;
		int points_offered_min = mRbPointsOffered.getLeftIndex() + 1;
		int points_offered_max = mRbPointsOffered.getRightIndex() + 1;
		int item_rating_min = mRbItemRating.getLeftIndex() + 1;
		int item_rating_max = mRbItemRating.getRightIndex() + 1;
		int radius = mRbDistance.getRightIndex() + 1;
		String item_type = "";
		ItemTypeObject[] listItemType = Temp.listItemType;
		int sizeItemType = listItemType.length;
		for (int i = 0; i < sizeItemType - 1; i++) {
			if (listItemType[i].isCheck()) {
				item_type += listItemType[i].getId() + ",";
			}
		}
		if (listItemType[sizeItemType - 1].isCheck()) {
			item_type += listItemType[sizeItemType - 1].getId();
		} else {
			if (item_type.length() > 1)
				item_type = item_type.substring(0, item_type.length() - 2);
		}

		//
		String menu_type = "";
		MenuTypeObject[] listMenuType = Temp.listMenuType;
		int sizeMenuType = listMenuType.length;
		for (int i = 0; i < sizeMenuType - 1; i++) {
			if (listMenuType[i].isCheck()) {
				menu_type += listMenuType[i].getId() + ",";
			}
		}
		if (listMenuType[sizeMenuType - 1].isCheck()) {
			menu_type += listMenuType[sizeMenuType - 1].getId();
		} else {
			if (menu_type.length() > 1)
				menu_type = menu_type.substring(0, menu_type.length() - 2);
		}
		int server_rating_min = mRbServerRating.getLeftIndex() + 1;
		int server_rating_max = mRbServerRating.getRightIndex() + 1;
		String keyword = "" + mEtSearch.getText().toString();
		String access_token = sp.getString("access_token", "");
		async.execute(access_token,
				convertToString(Temp.currentLocation.getLatitude()),
				convertToString(Temp.currentLocation.getLongitude()),
				convertToString(restaurant_rating_min),
				convertToString(restaurant_rating_max),
				convertToString(item_price_min),
				convertToString(item_price_max),
				convertToString(points_offered_min),
				convertToString(points_offered_max),
				convertToString(item_rating_min),
				convertToString(item_rating_max), convertToString(radius),
				item_type, menu_type, keyword,
				convertToString(server_rating_min),
				convertToString(server_rating_max));
	}

	private void showDialogMenuType() {
		final Dialog dialog = new Dialog(this, R.style.CustomDialogThemeNoTitle);
		dialog.setContentView(R.layout.dialog_choose_type);
		TextView tvTitle = (TextView) dialog
				.findViewById(R.id.dialog_choose_tv_title);
		tvTitle.setText(getResources().getString(
				R.string.dialog_choose_tv_title_menu_type));
		ImageButton ibCancel = (ImageButton) dialog
				.findViewById(R.id.dialog_choose_ib_close);
		ibCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		MenuTypeObject[] list = Temp.listMenuType;
		final ListDialogMenuTypeAdapter adapter = new ListDialogMenuTypeAdapter(
				this, R.layout.item_createmysearch, list);
		ListView lvList = (ListView) dialog
				.findViewById(R.id.dialog_choose_lv_list);
		lvList.setAdapter(adapter);
		Button btSave = (Button) dialog
				.findViewById(R.id.dialog_choose_bt_save);
		btSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						CreateMySearchActivity.this,
						R.style.CustomDialogThemeNoTitle);
				builder.setMessage("Save ?");
				builder.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Temp.listMenuType = adapter.getList();
								dialog.dismiss();
							}
						});
				builder.setNegativeButton("NO",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
				if (!alert.isShowing()) {
					dialog.dismiss();
				}
			}
		});
		dialog.show();
	}

	private void showDialogFoodType() {
		final Dialog dialog = new Dialog(this, R.style.CustomDialogThemeNoTitle);
		dialog.setContentView(R.layout.dialog_choose_type);
		TextView tvTitle = (TextView) dialog
				.findViewById(R.id.dialog_choose_tv_title);
		tvTitle.setText(getResources().getString(
				R.string.dialog_choose_tv_title_food_type));
		ImageButton ibCancel = (ImageButton) dialog
				.findViewById(R.id.dialog_choose_ib_close);
		Button btSave = (Button) dialog
				.findViewById(R.id.dialog_choose_bt_save);
		ItemTypeObject[] list = Temp.listItemType;
		final ListDialogItemTypeAdapter adapter = new ListDialogItemTypeAdapter(
				this, R.layout.item_createmysearch, list);
		ListView lvList = (ListView) dialog
				.findViewById(R.id.dialog_choose_lv_list);
		lvList.setAdapter(adapter);
		btSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						CreateMySearchActivity.this,
						R.style.CustomDialogThemeNoTitle);
				builder.setMessage("Save ?");
				builder.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Temp.listItemType = adapter.getList();
								dialog.dismiss();
							}
						});
				builder.setNegativeButton("NO",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
				if (!alert.isShowing()) {
					dialog.dismiss();
				}
			}
		});
		ibCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private void showDialogSave() {
		final Dialog dialog = new Dialog(CreateMySearchActivity.this,
				R.style.CustomDialogThemeNoTitle);
		dialog.setContentView(R.layout.dialog_createmysearch);
		Button btSave = (Button) dialog
				.findViewById(R.id.dialog_createmysearch_bt_save);
		Button btCancel = (Button) dialog
				.findViewById(R.id.dialog_createmysearch_bt_cancel);
		final EditText etName = (EditText) dialog
				.findViewById(R.id.dialog_createmysearch_et_name);
		btCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		btSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String st = etName.getText().toString().trim();
				if (st.length() == 0) {
					showToast("Name can not null");
				} else {
					SaveMySearchProfileAsyncTask async = new SaveMySearchProfileAsyncTask(
							CreateMySearchActivity.this,
							CreateMySearchActivity.this);
					SharedPreferences sp = CreateMySearchActivity.this
							.getSharedPreferences(
									Constants.KEY_CURRENT_USER_XML, 0);
					int restaurant_rating_min = mRbRestaurantRating
							.getLeftIndex() + 1;
					int restaurant_rating_max = mRbRestaurantRating
							.getRightIndex() + 1;
					int item_price_min = mRbItemPrice.getLeftIndex() + 1;
					int item_price_max = mRbItemPrice.getRightIndex() + 1;
					int points_offered_min = mRbPointsOffered.getLeftIndex() + 1;
					int points_offered_max = mRbPointsOffered.getRightIndex() + 1;
					int item_rating_min = mRbItemRating.getLeftIndex() + 1;
					int item_rating_max = mRbItemRating.getRightIndex() + 1;
					int radius = mRbDistance.getRightIndex() + 1;
					String item_type = "";
					ItemTypeObject[] listItemType = Temp.listItemType;
					int sizeItemType = listItemType.length;
					for (int i = 0; i < sizeItemType - 1; i++) {
						if (listItemType[i].isCheck()) {
							item_type += listItemType[i].getId() + ",";
						}
					}
					if (listItemType[sizeItemType - 1].isCheck()) {
						item_type += listItemType[sizeItemType - 1].getId();
					} else {
						if (item_type.length() > 1)
							item_type = item_type.substring(0,
									item_type.length() - 2);
					}

					//
					String menu_type = "";
					MenuTypeObject[] listMenuType = Temp.listMenuType;
					int sizeMenuType = listMenuType.length;
					for (int i = 0; i < sizeMenuType - 1; i++) {
						if (listMenuType[i].isCheck()) {
							menu_type += listMenuType[i].getId() + ",";
						}
					}
					if (listMenuType[sizeMenuType - 1].isCheck()) {
						menu_type += listMenuType[sizeMenuType - 1].getId();
					} else {
						if (menu_type.length() > 1)
							menu_type = menu_type.substring(0,
									menu_type.length() - 2);
					}
					int server_rating_min = mRbServerRating.getLeftIndex() + 1;
					int server_rating_max = mRbServerRating.getRightIndex() + 1;
					String keyword = "" + mEtSearch.getText().toString();
					String name = "" + etName.getText().toString();
					String access_token = sp.getString("access_token", "");
					async.execute(access_token,
							convertToString(restaurant_rating_min),
							convertToString(restaurant_rating_max),
							convertToString(item_price_min),
							convertToString(item_price_max),
							convertToString(points_offered_min),
							convertToString(points_offered_max),
							convertToString(item_rating_min),
							convertToString(item_rating_max),
							convertToString(radius), item_type, menu_type,
							keyword, convertToString(server_rating_min),
							convertToString(server_rating_max),
							convertToString(0), name);
				}
			}

		});
		dialog.show();
	}

	private String convertToString(double n) {
		return String.valueOf(n);
	}

	private String convertToString(int n) {
		return String.valueOf(n);
	}

	private void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	/**
	 * RESULT_CANCELED Save complete, finish this with resultCode =
	 * RESULT_CANCELED
	 * 
	 */
	@Override
	public void onSaveMySearchProfileListenerComplete() {
		showToastMessage("save");
		setResult(RESULT_CANCELED);
		finish();
	}

	@Override
	public void onSaveMySearchProfileListenerFailed() {

	}

	/**
	 * RESULT_OK Search complete, resultCode = RESULT_OK
	 */
	@Override
	public void onAdvanceSearchListenerComplete() {
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public void onAdvanceSearchListenerFailed() {

	}

}
