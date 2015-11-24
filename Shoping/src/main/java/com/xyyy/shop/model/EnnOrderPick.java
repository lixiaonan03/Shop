package com.xyyy.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnOrderPick {

	/**
	 * 配货记录ID
	 **/
	private Integer pickId;
	/**
	 * 订单商品信息ID
	 **/
	private Integer orderGoodsId;
	/**
	 * 01-配方推荐菜品 02-装配菜品
	 **/
	private String pickType;
	/**
      * 
      **/
	private String dishesCode;
	/**
      * 
      **/
	private String dishesName;
	/**
	 * 配送数量
	 **/
	private Integer pickNum;
	/**
	 * 标准重量(KG/千克)
	 **/
	private java.math.BigDecimal pickWeight;
	/**
      * 
      **/
	private java.sql.Timestamp pickDate;
	 /**
     * 
     **/
	private Integer seqNum;
   /**
     * 追溯码
     **/
	private String traceCode;
   /**
     * 称重码
     **/
	private String weightCode;
   
	public Integer getSeqNum() {
	return seqNum;
}

public void setSeqNum(Integer seqNum) {
	this.seqNum = seqNum;
}

public String getTraceCode() {
	return traceCode;
}

public void setTraceCode(String traceCode) {
	this.traceCode = traceCode;
}

public String getWeightCode() {
	return weightCode;
}

public void setWeightCode(String weightCode) {
	this.weightCode = weightCode;
}

	public EnnOrderPick() {
		super();
	}

	public Integer getPickId() {
		return this.pickId;
	}

	public void setPickId(Integer pickId) {
		this.pickId = pickId;
	}

	public Integer getOrderGoodsId() {
		return this.orderGoodsId;
	}

	public void setOrderGoodsId(Integer orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}

	public String getPickType() {
		return this.pickType;
	}

	public void setPickType(String pickType) {
		this.pickType = pickType;
	}

	public String getDishesCode() {
		return this.dishesCode;
	}

	public void setDishesCode(String dishesCode) {
		this.dishesCode = dishesCode;
	}

	public String getDishesName() {
		return this.dishesName;
	}

	public void setDishesName(String dishesName) {
		this.dishesName = dishesName;
	}

	public Integer getPickNum() {
		return this.pickNum;
	}

	public void setPickNum(Integer pickNum) {
		this.pickNum = pickNum;
	}

	public java.math.BigDecimal getPickWeight() {
		return this.pickWeight;
	}

	public void setPickWeight(java.math.BigDecimal pickWeight) {
		this.pickWeight = pickWeight;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getPickDate() {
		return this.pickDate;
	}

	public void setPickDate(java.sql.Timestamp pickDate) {
		this.pickDate = pickDate;
	}
}