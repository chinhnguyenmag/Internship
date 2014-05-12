package com.example.demointership.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.w3c.dom.Document;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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
import com.example.demointership.Util.GMapV2Direction;
import com.example.demointership.Util.Server;
import com.example.demointership.Util.ServerURL;
import com.example.demointership.model.RestaurantsObject;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.meetme.android.horizontallistview.HorizontalListView;

public class MapActivity extends FragmentActivity implements
		ConnectionCallbacks, OnConnectionFailedListener {
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
	RestaurantsObject[] mRestaurantsSearch;
	Location mCurrentLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		mSpLogin = getSharedPreferences("CurrentUser", 0);
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
		mHlvItem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				GMapV2Direction rd = new GMapV2Direction();
				LatLng from = new LatLng(mCurrentLocation.getLatitude(),
						mCurrentLocation.getLongitude());
				LatLng to = new LatLng(mRestaurants[pos].getLat(),
						mRestaurants[pos].getLong());
				Document doc = rd.getDocument(from, to,
						GMapV2Direction.MODE_DRIVING);
				ArrayList<LatLng> directionPoint = rd.getDirection(doc);
				PolylineOptions rectLine = new PolylineOptions().width(3)
						.color(Color.RED);
				for (LatLng item : directionPoint) {
					rectLine.add(item);
				}
				mGoogleMap.clear();
				for (RestaurantsObject restaurant : mRestaurants) {
					LatLng ll = new LatLng(restaurant.getLat(), restaurant
							.getLong());
					mGoogleMap.addMarker(new MarkerOptions()
							.title(restaurant.getName())
							.snippet(restaurant.getAddress()).position(ll));
				}
				mGoogleMap.addPolyline(rectLine);
			}
		});
		mBtList.requestFocus();
		mEtSearch.clearFocus();
		mEtSearch.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					final Dialog dialog = new Dialog(MapActivity.this);
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
								dialog.dismiss();
							} else {
								// ....
								if (mLocationClient.isConnected()) {
									Location location = mLocationClient
											.getLastLocation();
									asynctask async = new asynctask(
											MapActivity.this);
									String access_token = mSpLogin.getString(
											"access_token", "");
									try {
										async.execute(access_token,
												String.valueOf(location
														.getLatitude()),
												String.valueOf(location
														.getLongitude()), key);

										mRestaurantsSearch = async.get();
									} catch (Exception e) {
										mRestaurantsSearch = null;
									}
									if (mRestaurantsSearch != null)
										for (RestaurantsObject restaurant : mRestaurantsSearch) {
											LatLng ll = new LatLng(restaurant
													.getLat(), restaurant
													.getLong());
											mGoogleMap
													.addMarker(new MarkerOptions()
															.title(restaurant
																	.getName())
															.snippet(
																	restaurant
																			.getAddress())
															.position(ll));
											// FillMap(mRestaurantsSearch);
											// FillList(mRestaurantsSearch);
										}
									else
										showToast("Can't connect to server !");
								}
							}
						}
					});

					dialog.show();

				}
			}
		});

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
		// startActivity(new Intent(MapActivity.this, MySearchActivity.class));
		// finish();
		{
			Editor editor = mSpLogin.edit();
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
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void FillMap(/* RestaurantsObject[] Restaurants */) {
		if (mLocationClient.isConnected()) {
			Location location = mLocationClient.getLastLocation();
			asynctask async = new asynctask(MapActivity.this);
			String access_token = mSpLogin.getString("access_token", "");
			try {
				async.execute(access_token,
						String.valueOf(location.getLatitude()),
						String.valueOf(location.getLongitude()), "");

				mRestaurants = async.get();
			} catch (Exception e) {
				mRestaurants = null;
			}
			if (mRestaurants != null)
				for (RestaurantsObject restaurant : mRestaurants) {
					LatLng ll = new LatLng(restaurant.getLat(),
							restaurant.getLong());
					mGoogleMap.addMarker(new MarkerOptions()
							.title(restaurant.getName())
							.snippet(restaurant.getAddress()).position(ll));
				}
			else
				showToast("Can't connect to server !");
		}
	}

	private void showToast(String st) {
		Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT).show();
	}

	private void FillList(/* RestaurantsObject[] Restaurants */) {

		if (mRestaurants != null) {
			HorizontalAdapter horizontaladapter = new HorizontalAdapter(this,
					mRestaurants);
			mHlvItem.setAdapter(horizontaladapter);

			NomalListAdapter listadapter = new NomalListAdapter(this,
					R.layout.item_list, mRestaurants);
			mLvListItem.setAdapter(listadapter);
		} else
			showToast(" it's null");
	}

	private class HorizontalAdapter extends ArrayAdapter<RestaurantsObject> {
		private LayoutInflater mInflater;

		public HorizontalAdapter(Context context, RestaurantsObject[] values) {
			super(context, R.layout.item_horizontallistview, values);
			mInflater = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.item_horizontallistview, parent, false);

				holder = new Holder();
				holder.tvDistance = (TextView) convertView
						.findViewById(R.id.itemhlv_tv_distance);
				holder.ivLogo = (ImageView) convertView
						.findViewById(R.id.itemhlv_iv_logo);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			holder.tvDistance.setText(String.valueOf(getItem(position)
					.getDistance()));
			String url = ServerURL.URL + getItem(position).getLogo();
			AsyncTask<String, Void, Bitmap> async = new AsyncTask<String, Void, Bitmap>() {

				@Override
				protected Bitmap doInBackground(String... params) {
					String url = params[0];
					URL imageURL;
					Bitmap image = null;
					try {
						imageURL = new URL(url);
						image = BitmapFactory.decodeStream(imageURL
								.openStream());
					} catch (MalformedURLException e) {
					} catch (IOException e) {
						e.printStackTrace();
					}
					return image;
				}

			};
			Bitmap image = null;
			async.execute(url);
			try {
				image = async.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			holder.ivLogo.setImageBitmap(image);
			return convertView;
		}

		private class Holder {
			public TextView tvDistance;
			public ImageView ivLogo;
		}

	}

	private class asynctask extends
			AsyncTask<String, Void, RestaurantsObject[]> {
		private ProgressDialog mDialog;
		private Activity mContext;

		public asynctask(Activity context) {
			this.mContext = context;
		}

		@Override
		protected RestaurantsObject[] doInBackground(String... params) {
			StringEntity stringEntity = null;
			JSONObject obj = new JSONObject();
			RestaurantsObject[] response = null;
			try {
				obj.put("access_token", params[0]);
				obj.put("latitude", Double.parseDouble(params[1]));
				obj.put("longitude", Double.parseDouble(params[2]));
				obj.put("name", params[3]);
				stringEntity = new StringEntity(obj.toString(), "UTF-8");

				response = new Gson().fromJson(Server.getJSON(Server
						.requestPost(
								ServerURL.URL + ServerURL.getKeyNormalsearch(),
								stringEntity)), RestaurantsObject[].class);
			} catch (Exception e) {
			}
			return response;
		}

		@Override
		protected void onPostExecute(RestaurantsObject[] result) {
			super.onPostExecute(result);
			mDialog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(mContext);
			mDialog.setMessage("Loading...");
			mDialog.show();
		}

	}

	private class NomalListAdapter extends ArrayAdapter<RestaurantsObject> {
		Activity mContext;
		RestaurantsObject[] list;

		public NomalListAdapter(Activity context, int re,
				RestaurantsObject[] list) {
			super(context, re, list);
			this.mContext = context;
			this.list = list;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = mContext.getLayoutInflater();
				convertView = inflater.inflate(R.layout.item_list, parent,
						false);
			}
			RestaurantsObject item = list[position];
			// ImageView ivLogo =(ImageView)
			// convertView.findViewById(R.id.itemlist_iv_logo);
			TextView tvName = (TextView) convertView
					.findViewById(R.id.itemlist_tv_name);
			TextView tvDistance = (TextView) convertView
					.findViewById(R.id.itemlist_tv_distance);
			tvName.setText(item.getName());
			tvDistance.setText(String.valueOf(item.getDistance()));

			return convertView;
		}

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
	public void onConnected(Bundle connectionHint) {
		mCurrentLocation = mLocationClient.getLastLocation();
		LatLng currenlocation = new LatLng(mCurrentLocation.getLatitude(),
				mCurrentLocation.getLongitude());

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currenlocation,
				10));
		FillMap();
		FillList();
	}

	@Override
	public void onDisconnected() {
		mLocationClient.connect();
	}
}
