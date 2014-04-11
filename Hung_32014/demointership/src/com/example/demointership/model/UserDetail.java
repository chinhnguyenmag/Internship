package com.example.demointership.model;

import com.example.demointership.R;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

	@SerializedName("status")
	public String status;
	@SerializedName("access_token")
	public String access_token;
	@SerializedName("id")
	public String id;
	@SerializedName("email")
	public String email;
	@SerializedName("username")
	public String username;
	@SerializedName("first_name")
	public String first_name;
	@SerializedName("last_name")
	public String last_name;
	@SerializedName("address")
	public String address;
	@SerializedName("city")
	public String city;
	@SerializedName("state")
	public String state;
	@SerializedName("zip")
	public String zip;
	@SerializedName("point")
	public String point;
	@SerializedName("dinner_status")
	public String dinner_status;
	@SerializedName("userPhotoImageURL")
	public String userPhotoImageURL;
	@SerializedName("defaultsearchprofile")
	public String defaultsearchprofile;
	@SerializedName("error")
	public String error;
	
}
