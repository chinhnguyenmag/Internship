package com.example.demointership.model;

import java.util.List;

import android.graphics.Bitmap;

import com.example.demointership.Util.Utils;
import com.google.gson.annotations.SerializedName;

public class RestaurantsObject {
	@SerializedName("status")
	private String status;
	@SerializedName("error")
	private String error;
	@SerializedName("id")
	private String id;
	@SerializedName("name")
	private String name;
	@SerializedName("address")
	private String address;
	@SerializedName("city")
	private String city;
	@SerializedName("state")
	private String state;
	@SerializedName("country")
	private String country;
	@SerializedName("lat")
	private float lat;
	@SerializedName("long")
	private float Long;
	@SerializedName("distance")
	private float distance;
	@SerializedName("logo")
	private String logo;
	@SerializedName("type")
	private String type;
	@SerializedName("photos")
	private List<photoObject> photos;

	public String getType() {
		return type;
	}

	public List<photoObject> getPhotos() {
		if (this.photos.size() == 0)
			return null;
		return photos;
	}

	public void setPhotos(List<photoObject> photos) {
		this.photos = photos;
	}

	public void setType(String type) {
		this.type = type;
	}

	private Bitmap imagelogo;

	public Bitmap getImagelogo() {
		return imagelogo;
	}

	public void setImagelogo(Bitmap imagelogo) {
		this.imagelogo = imagelogo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLong() {
		return Long;
	}

	public void setLong(float l) {
		Long = l;
	}

	public float getDistance() {

		return Utils.round(distance, 2);
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public class photoObject {
		@SerializedName("width")
		public int width;
		@SerializedName("height")
		public int height;
		@SerializedName("photo_reference")
		public String photo_reference;
		@SerializedName("api_key")
		public String api_key;
	}
}
