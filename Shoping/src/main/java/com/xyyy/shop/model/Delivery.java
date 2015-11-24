package com.xyyy.shop.model;
/**
 * 自己创建的本次配送的实体
 * @author lxn
 *
 */
public class Delivery {
    String  imgurl;
    String  type;
    String  cardtype;
    String time;
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Delivery(String imgurl, String type, String cardtype, String time) {
		super();
		this.imgurl = imgurl;
		this.type = type;
		this.cardtype = cardtype;
		this.time = time;
	}
	public Delivery() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
