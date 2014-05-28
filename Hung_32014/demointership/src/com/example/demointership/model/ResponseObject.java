package com.example.demointership.model;

import com.google.gson.annotations.SerializedName;

public class ResponseObject {
	@SerializedName("status")
	private String status;
	@SerializedName("access_token")
	private String access_token;
	@SerializedName("error")
	private String error;
	@SerializedName("user")
	private UserProfileObject user;
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
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public UserProfileObject getUser() {
		return user;
	}
	public void setUser(UserProfileObject user) {
		this.user = user;
	}
	
}
