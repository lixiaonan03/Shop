package com.xyyy.shop.model;


public class OrderItemVO2 {
	private String goodId;
	private String imgURL;
	private String goodName;
	private String price;
	private String num;
	private String Evaled;
	
	public String getEvaled() {
		return Evaled;
	}
	public void setEvaled(String evaled) {
		Evaled = evaled;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	
}
