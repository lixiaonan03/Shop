package com.xyyy.shop.model;

import java.math.BigDecimal;

public class GiftCardItem {
     //商品图片
	private String  goodsImg;
    //商品
	private EnnGoods ennGoods;
	//数量
	private Integer number;
	//价格
	private BigDecimal sumPrice;
	public String getGoodsImg() {
		return goodsImg;
	}
	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}
	public EnnGoods getEnnGoods() {
		return ennGoods;
	}
	public void setEnnGoods(EnnGoods ennGoods) {
		this.ennGoods = ennGoods;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public BigDecimal getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}
	
}
