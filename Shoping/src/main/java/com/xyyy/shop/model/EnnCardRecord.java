package com.xyyy.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnCardRecord {
    
    /**
      * 会员卡消费记录ID
      **/
	private Integer cardRecordId;
    /**
      * 会员卡ID
      **/
	private Integer cardId;
    /**
      * 收支类型
            01-收入
            02-支出
      **/
	private String blanceType;
    /**
      * 消费记录类型
            01-充值
            02-赠送
            03-消费
            04-退款
            
            99-其他
      **/
	private String paymentsType;
    /**
      * 订单ID
      **/
	private Integer orderId;
    /**
      * 订单编号
      **/
	private String orderSeq;
    /**
      * 金额
            （加+ - 符号）
      **/
	private java.math.BigDecimal amount;
    /**
      * 卡内余额
      **/
	private java.math.BigDecimal cardRemain;
    /**
      * 剩余次数
      **/
	private Integer cardRenum;
    /**
      * 时分秒
      **/
	private java.sql.Timestamp paymentsTime;
    /**
      * 备注
      **/
	private String remark;
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


	public EnnCardRecord() {
		super();
	}

	public Integer getCardRecordId() {
		return this.cardRecordId;
	}

	public void setCardRecordId(Integer cardRecordId) {
		this.cardRecordId = cardRecordId;
	}
	public Integer getCardId() {
		return this.cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public String getBlanceType() {
		return this.blanceType;
	}

	public void setBlanceType(String blanceType) {
		this.blanceType = blanceType;
	}
	public String getPaymentsType() {
		return this.paymentsType;
	}

	public void setPaymentsType(String paymentsType) {
		this.paymentsType = paymentsType;
	}
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderSeq() {
		return this.orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	public java.math.BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getPaymentsTime() {
		return this.paymentsTime;
	}

	public void setPaymentsTime(java.sql.Timestamp paymentsTime) {
		this.paymentsTime = paymentsTime;
	}
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
}