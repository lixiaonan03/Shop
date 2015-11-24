package com.xyyy.shop.model;

import java.util.List;

public class GiftCardDetailVO {
	//会员卡
	private EnnCard ennCard;
	//会员卡类型
	private EnnCardType ennCardType;
	//会员卡配送
	private EnnCardConfig ennCardConfig;
	private List<GiftCardItem> giftCardItems;

	public EnnCard getEnnCard() {
		return ennCard;
	}

	public void setEnnCard(EnnCard ennCard) {
		this.ennCard = ennCard;
	}

	public EnnCardType getEnnCardType() {
		return ennCardType;
	}

	public void setEnnCardType(EnnCardType ennCardType) {
		this.ennCardType = ennCardType;
	}

	public EnnCardConfig getEnnCardConfig() {
		return ennCardConfig;
	}

	public void setEnnCardConfig(EnnCardConfig ennCardConfig) {
		this.ennCardConfig = ennCardConfig;
	}

	public List<GiftCardItem> getGiftCardItems() {
		return giftCardItems;
	}

	public void setGiftCardItems(List<GiftCardItem> giftCardItems) {
		this.giftCardItems = giftCardItems;
	}

}
