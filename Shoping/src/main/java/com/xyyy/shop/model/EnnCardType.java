package com.xyyy.shop.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnCardType implements Serializable{

	/**
      * 
      **/
	private Integer cardTypeId;
	/**
	 * 会员卡名称
	 **/
	private String cardName;
	/**
	 * 01-安全蔬菜 02-生态柴鸡蛋 03-生态猪肉 04-生态散养柴鸡 05-生态大米 06-生态杂粮 07-生态粮油类
	 * 
	 * 10-礼品卡 20-非服务型会员卡
	 **/
	private String cardKind;
	/**
	 * 01-年卡 02-半年卡 03-季卡 04-月卡 05-体验卡
	 * 10-礼品卡
	 **/
	private String cardType;
	/**
	 * 总金额
	 **/
	private String amount;
	/**
	 * 总配送次数
	 **/
	private Integer deliveTimes;
	/**
	 * 折扣
	 **/
	private java.math.BigDecimal discount;
	/**
	 * 会员卡描述
	 **/
	private String cardDec;
	/**
	 * 数字类，>=12斤/次 或 >=6斤/次等
	 **/
	private String deliveStand;
	/**
	 * 间隔周次
	 **/
	private Integer interWeek;
	/**
	 * 次/周
	 **/
	private Integer deliveFreq;
	/**
	 * 配送时间: 周几配送，以逗号分隔（1,2,3,4,5,6,7）
	 **/
	private String deliveWeek;
	/**
	 * 配送费用
	 **/
	private java.math.BigDecimal delivePrice;
	/**
	 * 配送方式
	 **/
	private String deliveType;
	/**
	 * 配送备注
	 **/
	private String remark;
	/**
	 * 创建时间
	 **/
	private java.sql.Timestamp creDate;
	/**
	 * 修改时间
	 **/
	private java.sql.Timestamp updDate;
	/**
	 * 是否电子会员卡 0-否 1-是
	 **/
	private String isElect;
	/**
	 * 关联某一菜箱商品ID
	 **/
	private Integer goodsId;
	
	 /**
     * 服务类型
           01-服务型
           02-非服务型
     **/
	private String serviceType;
	

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public EnnCardType() {
		super();
	}

	public Integer getCardTypeId() {
		return this.cardTypeId;
	}

	public void setCardTypeId(Integer cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

	public String getCardName() {
		return this.cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardKind() {
		return this.cardKind;
	}

	public void setCardKind(String cardKind) {
		this.cardKind = cardKind;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getDeliveTimes() {
		return this.deliveTimes;
	}

	public void setDeliveTimes(Integer deliveTimes) {
		this.deliveTimes = deliveTimes;
	}

	public java.math.BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(java.math.BigDecimal discount) {
		this.discount = discount;
	}

	public String getCardDec() {
		return this.cardDec;
	}

	public void setCardDec(String cardDec) {
		this.cardDec = cardDec;
	}

	public String getDeliveStand() {
		return this.deliveStand;
	}

	public void setDeliveStand(String deliveStand) {
		this.deliveStand = deliveStand;
	}

	public Integer getInterWeek() {
		return this.interWeek;
	}

	public void setInterWeek(Integer interWeek) {
		this.interWeek = interWeek;
	}

	public Integer getDeliveFreq() {
		return this.deliveFreq;
	}

	public void setDeliveFreq(Integer deliveFreq) {
		this.deliveFreq = deliveFreq;
	}

	public String getDeliveWeek() {
		return this.deliveWeek;
	}

	public void setDeliveWeek(String deliveWeek) {
		this.deliveWeek = deliveWeek;
	}

	public java.math.BigDecimal getDelivePrice() {
		return this.delivePrice;
	}

	public void setDelivePrice(java.math.BigDecimal delivePrice) {
		this.delivePrice = delivePrice;
	}

	public String getDeliveType() {
		return this.deliveType;
	}

	public void setDeliveType(String deliveType) {
		this.deliveType = deliveType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getCreDate() {
		return this.creDate;
	}

	public void setCreDate(java.sql.Timestamp creDate) {
		this.creDate = creDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(java.sql.Timestamp updDate) {
		this.updDate = updDate;
	}

	public String getIsElect() {
		return this.isElect;
	}

	public void setIsElect(String isElect) {
		this.isElect = isElect;
	}

	public Integer getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
}