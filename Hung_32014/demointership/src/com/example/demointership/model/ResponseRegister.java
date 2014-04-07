package com.example.demointership.model;

import com.google.gson.annotations.SerializedName;

public class ResponseRegister {
	@SerializedName("status")
	public String status;
	@SerializedName("id")
	public String id;
	@SerializedName("access_token")
	public String access_token;
	@SerializedName("city")
	public String city;
	@SerializedName("state")
	public String state;
	@SerializedName("points")
	public String points;
	@SerializedName("dinner_status")
	public String dinner_status;
	@SerializedName("errors")
	public String errors;
	@SerializedName("email")
	public String email;
	@SerializedName("username")
	public String username;

}
