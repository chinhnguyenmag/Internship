package com.example.demointership.model;

import com.google.gson.annotations.SerializedName;

public class MenuTypeObject {
	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	private boolean isCheck;
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
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
	
}
