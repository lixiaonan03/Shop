package com.xyyy.shop.model;

import java.io.Serializable;




public class MemCardVO implements Serializable{
	// 会员卡信息
	private EnnCard ennCard;
	// 会员卡类型
	private EnnCardType ennCardType;
    // 是否选中
	private int flag ;//0 未选 1选中
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

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

}
