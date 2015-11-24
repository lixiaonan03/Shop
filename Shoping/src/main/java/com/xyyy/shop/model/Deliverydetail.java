package com.xyyy.shop.model;
/**
 * 自己创建的本次配送详情的实体
 * @author lxn
 *
 */
public class Deliverydetail {
    String  imgurl;
    String  name;
    String  weight;
    String menu;
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	public Deliverydetail(String imgurl, String name, String weight, String menu) {
		super();
		this.imgurl = imgurl;
		this.name = name;
		this.weight = weight;
		this.menu = menu;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public Deliverydetail() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
