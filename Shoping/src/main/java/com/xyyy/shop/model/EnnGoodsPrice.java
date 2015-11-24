package com.xyyy.shop.model;

import java.util.Date;


public class EnnGoodsPrice {

	/**
	 * 商品行情ID
	 **/
	private Integer marketId;
	/**
	 * 商品ID
	 **/
	
	private Integer goodsId;
	/**
	 * 商品市场价
	 **/
	
	private java.math.BigDecimal marketPri;
	/**
	 * 商品协议供货价
	 **/
	
	private java.math.BigDecimal memPrice;
	/**
	 * 商品协议供货价
	 **/
	
	private java.math.BigDecimal promPrice;
	/**
      * 
      **/
	
	private java.math.BigDecimal mallPrice;
	/**
	 * 供货地区ID
	 **/
	
	private String regionId;
	/**
	 * 供应商ID
	 **/
	
	private Integer supplierId;
	/**
	 * 状态 enable:启用 disable:禁用 invalid:作废 valid:取消作废
	 **/
	
	private String state;
	/**
	 * 创建人ID
	 **/
	
	private Integer creUserId;
	/**
	 * 创建人名称
	 **/
	
	private String creUserName;
	/**
	 * 创建人机构ID
	 **/
	
	private Integer creOrgId;
	/**
	 * 创建时间
	 **/
	
	private Date creDate;
	/**
	 * 修改人ID
	 **/
	
	private Integer updUserId;
	/**
	 * 修改人名称
	 **/
	
	private String updUserName;
	/**
	 * 修改人机构ID
	 **/
	
	private Integer updOrgId;
	/**
	 * 修改时间
	 **/
	
	private Date updDate;

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public java.math.BigDecimal getMarketPri() {
		return marketPri;
	}

	public void setMarketPri(java.math.BigDecimal marketPri) {
		this.marketPri = marketPri;
	}

	public java.math.BigDecimal getMemPrice() {
		return memPrice;
	}

	public void setMemPrice(java.math.BigDecimal memPrice) {
		this.memPrice = memPrice;
	}

	public java.math.BigDecimal getPromPrice() {
		return promPrice;
	}

	public void setPromPrice(java.math.BigDecimal promPrice) {
		this.promPrice = promPrice;
	}

	public java.math.BigDecimal getMallPrice() {
		return mallPrice;
	}

	public void setMallPrice(java.math.BigDecimal mallPrice) {
		this.mallPrice = mallPrice;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(Integer creUserId) {
		this.creUserId = creUserId;
	}

	public String getCreUserName() {
		return creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}

	public Integer getCreOrgId() {
		return creOrgId;
	}

	public void setCreOrgId(Integer creOrgId) {
		this.creOrgId = creOrgId;
	}


	public Integer getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(Integer updUserId) {
		this.updUserId = updUserId;
	}

	public String getUpdUserName() {
		return updUserName;
	}

	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}

	public Integer getUpdOrgId() {
		return updOrgId;
	}

	public void setUpdOrgId(Integer updOrgId) {
		this.updOrgId = updOrgId;
	}

	public Date getCreDate() {
		return creDate;
	}

	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	

}