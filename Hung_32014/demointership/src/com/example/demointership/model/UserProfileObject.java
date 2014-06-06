package com.example.demointership.model;

import com.google.gson.annotations.SerializedName;

public class UserProfileObject {
	@SerializedName("status")
	private String status;
	@SerializedName("access_token")
	private String access_token;
	@SerializedName("errors")
	private ErrorsObject errors;
	@SerializedName("id")
	private int id;
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
	@SerializedName("points")
	private int points;
	@SerializedName("dinner_status")
	private String dinner_status;
	@SerializedName("userPhotoImageURL")
	private String userPhotoImageURL;
	@SerializedName("defaultsearchprofile")
	private int defaultsearchprofile;
	@SerializedName("default_search_profile")
	private SearchProfileObject[] listSearchProfileObject;
	@SerializedName("is_register_levelup")
	private int is_register_levelup;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
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

	public int getDefaultsearchprofile() {
		return defaultsearchprofile;
	}

	public void setDefaultsearchprofile(int defaultsearchprofile) {
		this.defaultsearchprofile = defaultsearchprofile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public ErrorsObject getErrors() {
		return errors;
	}

	public void setErrors(ErrorsObject errors) {
		this.errors = errors;
	}

	public SearchProfileObject[] getListSearchProfileObject() {
		return listSearchProfileObject;
	}

	public void setListSearchProfileObject(
			SearchProfileObject[] listSearchProfileObject) {
		this.listSearchProfileObject = listSearchProfileObject;
	}

	public int getIs_register_levelup() {
		return is_register_levelup;
	}

	public void setIs_register_levelup(int is_register_levelup) {
		this.is_register_levelup = is_register_levelup;
	}

}
