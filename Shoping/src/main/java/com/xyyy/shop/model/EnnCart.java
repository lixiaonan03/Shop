package com.xyyy.shop.model;

import java.util.Date;

public class EnnCart {

	/**
	 * 购物车信息ID
	 **/
	private Integer cartId;
	/**
	 * 会员
	 **/
	
	private Integer membId;
	/**
      * 
      **/
	
	private Integer goodsId;
	/**
      * 
      **/
	
	private Integer supplierId;
	/**
      * 
      **/
	
	private Integer cartNum;
	/**
	 * 01-正常 02-已过期 03-待删除
	 **/
	
	private String cartState;
	/**
	 * 01-购买 02-再买点
	 **/
	
	private String cartType;
	/**
	 * 创建时间
	 **/
	
	private Date creDate;
	/**
	 * 修改时间
	 **/
	
	private Date updDate;

	public EnnCart() {
		super();
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getMembId() {
		return membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getCartNum() {
		return cartNum;
	}

	public void setCartNum(Integer cartNum) {
		this.cartNum = cartNum;
	}

	public String getCartState() {
		return cartState;
	}

	public void setCartState(String cartState) {
		this.cartState = cartState;
	}

	public String getCartType() {
		return cartType;
	}

	public void setCartType(String cartType) {
		this.cartType = cartType;
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