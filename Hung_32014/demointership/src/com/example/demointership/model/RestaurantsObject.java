package com.example.demointership.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class RestaurantsObject {
	@SerializedName("status")
	private String status;
	@SerializedName("error")
	private String error;
	@SerializedName("id")
	private int id;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
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
		return distance;
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
}
