package com.xyyy.shop.model;

import java.math.BigDecimal;

public class GoodVO {
	private EnnGoods ennGoods;
	private EnnGoodsImg ennGoodsImg;
	private int number;
	private BigDecimal sumMoney;
	private String ennGoodsPrice;
	public EnnGoods getEnnGoods() {
		return ennGoods;
	}
	public void setEnnGoods(EnnGoods ennGoods) {
		this.ennGoods = ennGoods;
	}
	public EnnGoodsImg getEnnGoodsImg() {
		return ennGoodsImg;
	}
	public void setEnnGoodsImg(EnnGoodsImg ennGoodsImg) {
		this.ennGoodsImg = ennGoodsImg;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public BigDecimal getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}
	public String getEnnGoodsPrice() {
		return ennGoodsPrice;
	}
	public void setEnnGoodsPrice(String ennGoodsPrice) {
		this.ennGoodsPrice = ennGoodsPrice;
	}

}
