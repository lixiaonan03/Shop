package com.xyyy.shop.model;


public class OrderDeliveItemDTO {
	private String status;// 01 本次配送 02 历史配送
	private String imgUrl;
	private EnnCard ennCard;
	private EnnCardType ennCardType;
	private EnnOrder ennOrder;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public EnnCard getEnnCard() {
		return ennCard;
	}

	public void setEnnCard(EnnCard ennCard) {
		this.ennCard = ennCard;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EnnOrder getEnnOrder() {
		return ennOrder;
	}

	public void setEnnOrder(EnnOrder ennOrder) {
		this.ennOrder = ennOrder;
	}

	public EnnCardType getEnnCardType() {
		return ennCardType;
	}

	public void setEnnCardType(EnnCardType ennCardType) {
		this.ennCardType = ennCardType;
	}
}
