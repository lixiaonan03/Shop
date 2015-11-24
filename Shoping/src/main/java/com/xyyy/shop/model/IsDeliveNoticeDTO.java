package com.xyyy.shop.model;

public class IsDeliveNoticeDTO {
	private Boolean isEnable;
	private String state;

	/*
	 * 00:请购买会员卡：无会员卡或者会员卡次数已经用尽 01:有新购买会员卡未启动 02：卡已暂停，请启用 03：有会员卡并有剩余次数
	 */
	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
