package com.example.demointership.activity;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demointership.R;
import com.example.demointership.Util.Constants;
import com.example.demointership.Util.GMapV2Direction;
import com.example.demointership.Util.Temp;
import com.example.demointership.adapter.HorizontalAdapter;
import com.example.demointership.adapter.NomalListMapAdapter;
import com.example.demointership.asynctask.LogOutAsyncTask;
import com.example.demointership.asynctask.NomalSearchAsyncTask;
import com.example.demointership.asynctask.RunMyDefaultSearchProfileAsyncTask;
import com.example.demointership.listener.LogOutListener;
import com.example.demointership.listener.NomalSearchListener;
import com.example.demointership.listener.RunMyDefaultSearchProfileListener;
import com.example.demointership.model.RestaurantsObject;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.meetme.android.horizontallistview.HorizontalListView;

@SuppressLint("NewApi")
public class MapActivity extends FragmentActivity implements
		ConnectionCallbacks, OnConnectionFailedListener, LogOutListener,
		NomalSearchListener, OnMarkerClickListener,
		RunMyDefaultSearchProfileListener, OnItemClickListener {
	Button mBtMap, mBtList;
	ImageButton mIbMysearch;
	EditText mEtSearch;
	HorizontalListView mHlvItem;
	LinearLayout mLlList;
	RelativeLayout mRlMap;
	GoogleMap mGoogleMap;
	ListView mLvListItem;
	LocationClient mLocationClient;
	SharedPreferences mSpLogin;
	RestaurantsObject[] mRestaurants;
	Location mCurrentLocation;
	final int MYSEARCH = 1;

	// LruCache<String, Bitmap> mMemoryCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		// final int maxMemory = (int) (Runtime.getRuntime().maxMemory() /
		// 1024);

		// Use 1/8th of the available memory for this memory cache.
		// final int cacheSize = maxMemory / 8;

		// mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
		// @Override
		// protected int sizeOf(String key, Bitmap bitmap) {
		// // The cache size will be measured in kilobytes rather than
		// // number of items.
		// return bitmap.getByteCount() / 1024;
		// }
		// };
		mSpLogin = getSharedPreferences(Constants.KEY_CURRENT_USER_XML, 0);
		mBtMap = (Button) findViewById(R.id.map_bt_map);
		mBtList = (Button) findViewById(R.id.map_bt_list);
		mEtSearch = (EditText) findViewById(R.id.map_et_search);
		mHlvItem = (HorizontalListView) findViewById(R.id.map_hlv_list);
		mLlList = (LinearLayout) findViewById(R.id.map_ll_viewlist);
		mLvListItem = (ListView) findViewById(R.id.map_lv_listitem);
		mRlMap = (RelativeLayout) findViewById(R.id.map_ll_viewmap);
		if (mGoogleMap == null) {
			try {
				mGoogleMap = ((SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.map_gm_map)).getMap();
				mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				mGoogleMap.setMyLocationEnabled(true);
			} catch (Exception e) {
			}
		}

		mLocationClient = new LocationClient(getApplicationContext(), this,
				this);
		mGoogleMap.setOnMarkerClickListener(this);
		mBtList.requestFocus();
		mEtSearch.clearFocus();
		mEtSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(MapActivity.this,
						R.style.CustomDialogThemeNoTitle);
				dialog.setContentView(R.layout.dialog_nomalsearch);
				final CheckBox cbCheck = (CheckBox) dialog
						.findViewById(R.id.nomalsearch_cb_check);
				Button btnSubmit = (Button) dialog
						.findViewById(R.id.nomalsearch_bt_submit);
				Button btnCancel = (Button) dialog
						.findViewById(R.id.nomalsearch_bt_cancel);
				EditText etSearch = (EditText) dialog
						.findViewById(R.id.nomalsearch_et_search);
				final String key = etSearch.getText().toString();
				btnCancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});
				btnSubmit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (cbCheck.isChecked()) {
							RunMyDefaultSearchProfileAsyncTask async = new RunMyDefaultSearchProfileAsyncTask(
									MapActivity.this, MapActivity.this);
							String access_token = mSpLogin.getString(
									"access_token", "");
							int id = mSpLogin.getInt("search_profile_id", 0);
							if (id == 0)
								showToast("....");
							else
								async.execute(access_token,
										String.valueOf(mCurrentLocation
												.getLatitude()), String
												.valueOf(mCurrentLocation
														.getLongitude()),
										String.valueOf(id));
							dialog.dismiss();
						} else {
							// ....
							if (mLocationClient.isConnected()) {
								Location location = mLocationClient
										.getLastLocation();
								NomalSearchAsyncTask async = new NomalSearchAsyncTask(
										MapActivity.this, MapActivity.this);
								String access_token = mSpLogin.getString(
										"access_token", "");
								async.execute(
										access_token,
										String.valueOf(location.getLatitude()),
										String.valueOf(location.getLongitude()),
										key, "");

							}
						}
						dialog.dismiss();
					}
				});

				dialog.show();

			}
		});
		mHlvItem.setOnItemClickListener(this);
		mLvListItem.setOnItemClickListener(this);

	}

	// public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	// if (getBitmapFromMemCache(key) == null) {
	// mMemoryCache.put(key, bitmap);
	// }
	// }
	//
	// public Bitmap getBitmapFromMemCache(String key) {
	// return mMemoryCache.get(key);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_logout:
			String access_token = mSpLogin.getString("access_token", "");
			LogOutAsyncTask async = new LogOutAsyncTask(this, this);
			async.execute(access_token);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onClicks(View v) {
		switch (v.getId()) {
		case R.id.map_bt_list:
			mLlList.setVisibility(View.VISIBLE);
			mRlMap.setVisibility(View.GONE);
			break;
		case R.id.map_bt_map:
			mLlList.setVisibility(View.GONE);
			mRlMap.setVisibility(View.VISIBLE);
			break;

		case R.id.map_ib_mysearch:
			startActivityForResult(new Intent(MapActivity.this,
					MySearchActivity.class), MYSEARCH);

			break;
		}
	}

	@Override
	protected void onDestroy() {
		mLvListItem.setAdapter(null);
		mHlvItem.setAdapter(null);
		mRestaurants = null;
		super.onDestroy();
	}

	private void FillMap(/* RestaurantsObject[] Restaurants */) {
		if (mLocationClient.isConnected()) {
			Location location = mLocationClient.getLastLocation();
			String access_token = mSpLogin.getString("access_token", "");
			NomalSearchAsyncTask async = new NomalSearchAsyncTask(this, this);
			async.execute(access_token, String.valueOf(location.getLatitude()),
					String.valueOf(location.getLongitude()), "", "");

		}
	}

	private void showToast(String st) {
		Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {

	}

	@Override
	protected void onStart() {
		super.onStart();
		mLocationClient.connect();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == MYSEARCH) {
			/**
			 * RESULT_OK AdvanceSearch complete, fill result to list.
			 */
			if (resultCode == RESULT_OK) {
				mRestaurants = Temp.listRestaurantObject;
				// if (mRestaurants != null)
				for (RestaurantsObject restaurant : mRestaurants) {
					LatLng ll = new LatLng(restaurant.getLat(),
							restaurant.getLong());
					// mGoogleMap.clear();
					mGoogleMap.addMarker(new MarkerOptions()
							.title(restaurant.getName())
							.snippet(restaurant.getAddress()).position(ll));
				}
				HorizontalAdapter horizontaladapter = new HorizontalAdapter(
						this, mRestaurants);
				mHlvItem.setAdapter(horizontaladapter);

				NomalListMapAdapter listadapter = new NomalListMapAdapter(this,
						R.layout.item_list, mRestaurants);
				mLvListItem.setAdapter(listadapter);
			} else if (resultCode == RESULT_CANCELED) {
				/**
				 * RESULT_CANCELED just create
				 */

			}
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mCurrentLocation = mLocationClient.getLastLocation();
		Temp.currentLocation = mCurrentLocation;
		LatLng currenlocation = new LatLng(mCurrentLocation.getLatitude(),
				mCurrentLocation.getLongitude());

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currenlocation,
				10));
		FillMap();
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this,
				R.style.CustomDialogThemeNoTitle);
		builder.setMessage("Exit Application ?");
		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onDisconnected() {
		mLocationClient.connect();
	}

	@Override
	public void onLogOutListenerComplete() {
		{
			Editor editor = mSpLogin.edit();
			editor.remove("error");
			editor.remove("uid");
			editor.remove("first_name");
			editor.remove("last_name");
			editor.remove("email");
			editor.remove("username");
			editor.remove("provider");
			editor.remove("access_token");
			editor.commit();
			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onLogOutListenerFailed() {
		showToast("Invalid Auth key");
	}

	@Override
	public void onNomalSearchListenerComplete() {
		mRestaurants = Temp.listRestaurantObject;
		// if (mRestaurants != null)
		for (RestaurantsObject restaurant : mRestaurants) {
			LatLng ll = new LatLng(restaurant.getLat(), restaurant.getLong());
			mGoogleMap.addMarker(new MarkerOptions()
					.title(restaurant.getName())
					.snippet(restaurant.getAddress()).position(ll));
		}
		HorizontalAdapter horizontaladapter = new HorizontalAdapter(this,
				mRestaurants);
		mHlvItem.setAdapter(horizontaladapter);
		
		NomalListMapAdapter listadapter = new NomalListMapAdapter(this,
				R.layout.item_list, mRestaurants);
		mLvListItem.setAdapter(listadapter);
	}

	@Override
	public void onNomalSearchListenerFailed() {
		showToast("Can't connect to server !");
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		GMapV2Direction rd = new GMapV2Direction();
		LatLng from = new LatLng(mCurrentLocation.getLatitude(),
				mCurrentLocation.getLongitude());
		LatLng to = marker.getPosition();
		Document doc = rd.getDocument(from, to, GMapV2Direction.MODE_DRIVING);
		ArrayList<LatLng> directionPoint = rd.getDirection(doc);
		PolylineOptions rectLine = new PolylineOptions().width(3).color(
				Color.RED);
		for (LatLng item : directionPoint) {
			rectLine.add(item);
		}
		mGoogleMap.clear();
		for (RestaurantsObject restaurant : mRestaurants) {
			LatLng ll = new LatLng(restaurant.getLat(), restaurant.getLong());
			mGoogleMap.addMarker(new MarkerOptions()
					.title(restaurant.getName())
					.snippet(restaurant.getAddress()).position(ll));
		}
		mGoogleMap.addPolyline(rectLine);
		marker.showInfoWindow();
		return false;
	}

	// public void onGetASearchProfileListenerComplete() {
	// AdvanceSeachAsyncTask async = new AdvanceSeachAsyncTask(this, this);
	// String access_token = mSpLogin.getString("access_token", "");
	// SearchProfileObject SearchProfile = Temp.defaultSearchProfileObject;
	// async.execute(access_token,
	// String.valueOf(mCurrentLocation.getLatitude()),
	// String.valueOf(mCurrentLocation.getLongitude()),
	// SearchProfile.getLocation_rating(),
	// SearchProfile.getItem_price(),
	// SearchProfile.getPoint_offered(),
	// String.valueOf(SearchProfile.getRadius()),
	// SearchProfile.getItem_type(), SearchProfile.getMenu_type(),
	// SearchProfile.getKeyword(), SearchProfile.getServer_rating());
	// }

	// public void onAdvanceSearchListenerComplete() {
	// mRestaurants = Temp.listRestaurantObject;
	// if (mRestaurants != null)
	// for (RestaurantsObject restaurant : mRestaurants) {
	// LatLng ll = new LatLng(restaurant.getLat(),
	// restaurant.getLong());
	// mGoogleMap.addMarker(new MarkerOptions()
	// .title(restaurant.getName())
	// .snippet(restaurant.getAddress()).position(ll));
	// HorizontalAdapter horizontaladapter = new HorizontalAdapter(
	// this, mRestaurants);
	// mHlvItem.setAdapter(horizontaladapter);
	//
	// NomalListMapAdapter listadapter = new NomalListMapAdapter(this,
	// R.layout.item_list, mRestaurants);
	// mLvListItem.setAdapter(listadapter);
	// }
	// }

	@Override
	public void onRunMyDefaultSearchProfileListenerComplete() {
		mRestaurants = Temp.listRestaurantObject;
		if (mRestaurants != null)
			for (RestaurantsObject restaurant : mRestaurants) {
				LatLng ll = new LatLng(restaurant.getLat(),
						restaurant.getLong());
				mGoogleMap.addMarker(new MarkerOptions()
						.title(restaurant.getName())
						.snippet(restaurant.getAddress()).position(ll));
				HorizontalAdapter horizontaladapter = new HorizontalAdapter(
						this, mRestaurants);
				mHlvItem.setAdapter(horizontaladapter);

				NomalListMapAdapter listadapter = new NomalListMapAdapter(this,
						R.layout.item_list, mRestaurants);
				mLvListItem.setAdapter(listadapter);
			}
	}

	@Override
	public void onRunMyDefaultSearchProfileListenerFailed() {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {
		final Dialog dialog = new Dialog(MapActivity.this,
				R.style.CustomDialogThemeNoTitle);
		dialog.setContentView(R.layout.dialog_restaurant_detail);
		TextView tvName = (TextView) dialog
				.findViewById(R.id.dialog_restaurant_detail_tv_name);
		ImageView ivLogo = (ImageView) dialog
				.findViewById(R.id.dialog_restaurant_detail_iv_logo);
		Button btCancel = (Button) dialog
				.findViewById(R.id.dialog_restaurant_detail_bt_cancel);
		tvName.setText("" + mRestaurants[position].getName());
		ivLogo.setImageBitmap(mRestaurants[position].getImagelogo());
		btCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
		showToast("" + mRestaurants[position].getId());
	}
}
