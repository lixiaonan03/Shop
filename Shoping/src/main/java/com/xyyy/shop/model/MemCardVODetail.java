package com.xyyy.shop.model;

/**
 * 接口使用的我的会员卡的详情的实体
 * @author lxn
 *
 */
public class MemCardVODetail {
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	// 会员卡信息
	private EnnCard ennCard;
	// 会员卡类型
	private EnnCardType ennCardType;
	//会员卡配送信息
	private EnnCardConfig ennCardConfig;
	 /**
     * 0-否
           1-是
     **/
	private String isDefault;
	
	public EnnCardConfig getEnnCardConfig() {
		return ennCardConfig;
	}

	public void setEnnCardConfig(EnnCardConfig ennCardConfig) {
		this.ennCardConfig = ennCardConfig;
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
