package com.example.demointership.model;

import com.example.demointership.R;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

	@SerializedName("status")
	private String status;
	@SerializedName("access_token")
	private String access_token;
	@SerializedName("id")
	private String id;
	@SerializedName("email")
	private String email;
	@SerializedName("username")
	private String username;
	@SerializedName("first_name")
	private String first_name;
	@SerializedName("last_name")
	private String last_name;
	@SerializedName("address")
	private String address;
	@SerializedName("city")
	private String city;
	@SerializedName("state")
	private String state;
	@SerializedName("zip")
	private String zip;
	@SerializedName("point")
	private String point;
	@SerializedName("dinner_status")
	private String dinner_status;
	@SerializedName("userPhotoImageURL")
	private String userPhotoImageURL;
	@SerializedName("defaultsearchprofile")
	private String defaultsearchprofile;
	@SerializedName("error")
	private String error;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		if (email == null) {
			return "";
		} else {
			return email;
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		if (username == null) {
			return "";
		} else {
			return username;
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getDinner_status() {
		return dinner_status;
	}

	public void setDinner_status(String dinner_status) {
		this.dinner_status = dinner_status;
	}

	public String getUserPhotoImageURL() {
		return userPhotoImageURL;
	}

	public void setUserPhotoImageURL(String userPhotoImageURL) {
		this.userPhotoImageURL = userPhotoImageURL;
	}

	public String getDefaultsearchprofile() {
		return defaultsearchprofile;
	}

	public void setDefaultsearchprofile(String defaultsearchprofile) {
		this.defaultsearchprofile = defaultsearchprofile;
	}

	public String getError() {
		if (error == null) {
			return "";
		} else {
			return error;
		}
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatus() {
		if (status == null) {
			return "";
		} else {
			return status;
		}
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
