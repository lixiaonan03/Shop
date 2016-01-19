package com.xyyy.shop.model;


import java.util.List;

public class GoodItemVO {
	private EnnGoods ennGoods;
	private String imgUrl;
	private List<EnnSalesAction> ennSalesActions;

	public List<EnnSalesAction> getEnnSalesActions() {
		return ennSalesActions;
	}

	public void setEnnSalesActions(List<EnnSalesAction> ennSalesActions) {
		this.ennSalesActions = ennSalesActions;
	}

	public EnnGoods getEnnGoods() {
		return ennGoods;
	}

	public void setEnnGoods(EnnGoods ennGoods) {
		this.ennGoods = ennGoods;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
