package com.example.demointership.model;

import com.google.gson.annotations.SerializedName;

public class SearchProfileObject {
	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	@SerializedName("location_rating")
	private String location_rating;
	@SerializedName("item_price")
	private String item_price;
	@SerializedName("point_offered")
	private String point_offered;
	@SerializedName("item_rating")
	private String item_rating;
	@SerializedName("radius")
	private int radius;
	@SerializedName("item_type")
	private String item_type;
	@SerializedName("menu_type")
	private String menu_type;
	@SerializedName("keyword")
	private String keyword;
	@SerializedName("server_rating")
	private String server_rating;
	@SerializedName("isdefault")
	private int isdefault;
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
	public String getLocation_rating() {
		return location_rating;
	}
	public void setLocation_rating(String location_rating) {
		this.location_rating = location_rating;
	}
	public String getItem_price() {
		return item_price;
	}
	public void setItem_price(String item_price) {
		this.item_price = item_price;
	}
	public String getPoint_offered() {
		return point_offered;
	}
	public void setPoint_offered(String point_offered) {
		this.point_offered = point_offered;
	}
	public String getItem_rating() {
		return item_rating;
	}
	public void setItem_rating(String item_rating) {
		this.item_rating = item_rating;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public String getItem_type() {
		return item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	public String getMenu_type() {
		return menu_type;
	}
	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getServer_rating() {
		return server_rating;
	}
	public void setServer_rating(String server_rating) {
		this.server_rating = server_rating;
	}
	public int getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}
}
