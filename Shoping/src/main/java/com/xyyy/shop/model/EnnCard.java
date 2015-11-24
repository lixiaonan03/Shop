package com.xyyy.shop.model;



import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnCard implements Comparable<EnnCard>,Serializable{

	/**
	 * 会员卡ID
	 **/
	private Integer cardId;
	/**
	 * 会员卡类型ID
	 **/
	private Integer cardTypeId;
	/**
	 * 会员卡编号
	 **/
	private String cardCode;
	/**
	 * 会员卡密码
	 **/
	private String cardPwd;
	/**
	 * 会员卡状态 00-待启用 01-启用 02-暂停 03-注销
	 **/
	private String cardState;
	/**
	 * 总金额
	 **/
	private String amount;
	/**
	 * 总次数
	 **/
	private Integer times;
	/**
	 * 卡内余额
	 **/
	private java.math.BigDecimal cardRemain;
	/**
	 * 剩余次数
	 **/
	private Integer cardRenum;
	/**
	 * 是否已开发票
	 **/
	private String isInvoiced;
	/**
	 * 0-未激活 1-已激活
	 **/
	private String isActive;
	/**
	 * 激活验证码
	 **/
	private String activeCode;
	/**
	 * 激活时间
	 **/
	private java.sql.Timestamp activeTime;
	/**
	 * 创建人ID
	 **/
	private Integer creUserId;
	/**
	 * 创建人名称
	 **/
	private String creUserName;
	/**
	 * 创建时间
	 **/
	private java.sql.Timestamp creDate;
	/**
	 * 修改人ID
	 **/
	private Integer updUserId;
	/**
	 * 修改人名称
	 **/
	private String updUserName;
	/**
	 * 修改时间
	 **/
	private java.sql.Timestamp updDate;
	/**
	 * 该会员卡关联的商品ID-（因会员卡关联了固定某一菜箱商品）
	 **/
	private Integer goodsId;
	/**
	 * 暂停时间
	 **/
	private java.sql.Timestamp pauseTime;
	/**
	 * 启用时间
	 **/
	private java.sql.Timestamp startTime;
	/**
	 * 激活手机号码
	 **/
	private String activePhone;
	/**
	 * 有效期截止时间 2015/01/17 23:59:59
	 **/
	private java.sql.Timestamp vaildEndtime;
	/**
	 * 有效期开始时间 2015/01/01 00:00:00
	 **/
	private java.sql.Timestamp vaildBegtime;
	/**
	 * 是否冻结： 默认为0-未冻结 0-未冻结 1-已冻结
	 **/
	private String isFreeze;
	private String isOldcard;
	/**
	 * 暂停终止时间
	 **/
	private java.sql.Timestamp pauseTimeEnd;
	 /**
     * 实际金额-具体充值金额
     **/
	private java.math.BigDecimal realAmount;
   /**
     * 赠送金额
     **/
	private java.math.BigDecimal giveAmount;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getPauseTimeEnd() {
		return pauseTimeEnd;
	}

	public void setPauseTimeEnd(java.sql.Timestamp pauseTimeEnd) {
		this.pauseTimeEnd = pauseTimeEnd;
	}
   
	public java.math.BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(java.math.BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public java.math.BigDecimal getGiveAmount() {
		return giveAmount;
	}

	public void setGiveAmount(java.math.BigDecimal giveAmount) {
		this.giveAmount = giveAmount;
	}

	public String getIsOldcard() {
		return isOldcard;
	}

	public void setIsOldcard(String isOldcard) {
		this.isOldcard = isOldcard;
	}

	public String getIsFreeze() {
		return isFreeze;
	}

	public void setIsFreeze(String isFreeze) {
		this.isFreeze = isFreeze;
	}

	public EnnCard() {
		super();
	}

	public Integer getCardId() {
		return this.cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public Integer getCardTypeId() {
		return this.cardTypeId;
	}

	public void setCardTypeId(Integer cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

	public String getCardCode() {
		return this.cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardPwd() {
		return this.cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	public String getCardState() {
		return this.cardState;
	}

	public void setCardState(String cardState) {
		this.cardState = cardState;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getTimes() {
		return this.times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public java.math.BigDecimal getCardRemain() {
		return this.cardRemain;
	}

	public void setCardRemain(java.math.BigDecimal cardRemain) {
		this.cardRemain = cardRemain;
	}

	public Integer getCardRenum() {
		return this.cardRenum;
	}

	public void setCardRenum(Integer cardRenum) {
		this.cardRenum = cardRenum;
	}

	public String getIsInvoiced() {
		return this.isInvoiced;
	}

	public void setIsInvoiced(String isInvoiced) {
		this.isInvoiced = isInvoiced;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getActiveCode() {
		return this.activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getActiveTime() {
		return this.activeTime;
	}

	public void setActiveTime(java.sql.Timestamp activeTime) {
		this.activeTime = activeTime;
	}

	public Integer getCreUserId() {
		return this.creUserId;
	}

	public void setCreUserId(Integer creUserId) {
		this.creUserId = creUserId;
	}

	public String getCreUserName() {
		return this.creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getCreDate() {
		return this.creDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getVaildEndtime() {
		return vaildEndtime;
	}

	public void setVaildEndtime(java.sql.Timestamp vaildEndtime) {
		this.vaildEndtime = vaildEndtime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getVaildBegtime() {
		return vaildBegtime;
	}

	public void setVaildBegtime(java.sql.Timestamp vaildBegtime) {
		this.vaildBegtime = vaildBegtime;
	}

	public void setCreDate(java.sql.Timestamp creDate) {
		this.creDate = creDate;
	}

	public Integer getUpdUserId() {
		return this.updUserId;
	}

	public void setUpdUserId(Integer updUserId) {
		this.updUserId = updUserId;
	}

	public String getUpdUserName() {
		return this.updUserName;
	}

	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(java.sql.Timestamp updDate) {
		this.updDate = updDate;
	}

	public Integer getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getPauseTime() {
		return this.pauseTime;
	}

	public void setPauseTime(java.sql.Timestamp pauseTime) {
		this.pauseTime = pauseTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(java.sql.Timestamp startTime) {
		this.startTime = startTime;
	}

	public String getActivePhone() {
		return this.activePhone;
	}

	public void setActivePhone(String activePhone) {
		this.activePhone = activePhone;
	}


	@Override
	public int compareTo(EnnCard o) {
		return o.getCardRemain().compareTo(this.getCardRemain());
	}
}