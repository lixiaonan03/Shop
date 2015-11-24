package com.xyyy.shop.model;


import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnGoodsEval {
    
    /**
      * 商品评价ID
      **/
	private Integer evalId;
    /**
      * 商品ID
      **/
	private Integer goodsId;
    /**
      * 
      **/
	private Integer evalQualScore;
    /**
      * 配送速度分
      **/
	private Integer evalSpdScore;
    /**
      * 
      **/
	private Integer evalServScore;
    /**
      * 商品评价描述
      **/
	private String evalDesc;
    /**
      * 
      **/
	private java.sql.Timestamp evalTime;
    /**
      * 
      **/
	private Integer evalUserId;
    /**
      * 
      **/
	private String evalUserName;
    /**
      * 会员
      **/
	private Integer membId;
    /**
      * 01-正常
            02-屏蔽
      **/
	private String evalState;
    /**
      * 订单ID
      **/
	private Integer orderId;
    /**
      * 屏蔽操作人ID
      **/
	private Integer shieldUserId;
    /**
      * 屏蔽操作人名称
      **/
	private String shieldUserName;
    /**
      * 屏蔽时间
      **/
	private java.sql.Timestamp shieldTime;
    /**
      * 屏蔽理由
      **/
	private String shieldReason;


	public EnnGoodsEval() {
		super();
	}

	public Integer getEvalId() {
		return this.evalId;
	}

	public void setEvalId(Integer evalId) {
		this.evalId = evalId;
	}
	public Integer getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getEvalQualScore() {
		return this.evalQualScore;
	}

	public void setEvalQualScore(Integer evalQualScore) {
		this.evalQualScore = evalQualScore;
	}
	public Integer getEvalSpdScore() {
		return this.evalSpdScore;
	}

	public void setEvalSpdScore(Integer evalSpdScore) {
		this.evalSpdScore = evalSpdScore;
	}
	public Integer getEvalServScore() {
		return this.evalServScore;
	}

	public void setEvalServScore(Integer evalServScore) {
		this.evalServScore = evalServScore;
	}
	public String getEvalDesc() {
		return this.evalDesc;
	}

	public void setEvalDesc(String evalDesc) {
		this.evalDesc = evalDesc;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getEvalTime() {
		return this.evalTime;
	}

	public void setEvalTime(java.sql.Timestamp evalTime) {
		this.evalTime = evalTime;
	}
	public Integer getEvalUserId() {
		return this.evalUserId;
	}

	public void setEvalUserId(Integer evalUserId) {
		this.evalUserId = evalUserId;
	}
	public String getEvalUserName() {
		return this.evalUserName;
	}

	public void setEvalUserName(String evalUserName) {
		this.evalUserName = evalUserName;
	}
	public Integer getMembId() {
		return this.membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}
	public String getEvalState() {
		return this.evalState;
	}

	public void setEvalState(String evalState) {
		this.evalState = evalState;
	}
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getShieldUserId() {
		return this.shieldUserId;
	}

	public void setShieldUserId(Integer shieldUserId) {
		this.shieldUserId = shieldUserId;
	}
	public String getShieldUserName() {
		return this.shieldUserName;
	}

	public void setShieldUserName(String shieldUserName) {
		this.shieldUserName = shieldUserName;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getShieldTime() {
		return this.shieldTime;
	}

	public void setShieldTime(java.sql.Timestamp shieldTime) {
		this.shieldTime = shieldTime;
	}
	public String getShieldReason() {
		return this.shieldReason;
	}

	public void setShieldReason(String shieldReason) {
		this.shieldReason = shieldReason;
	}
}