package com.xyyy.shop.model;


import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnDishes {

	/**
	 * 菜品ID
	 **/
	private Integer dishesId;
	/**
      * 
      **/
	private String dishesCode;
	/**
      * 
      **/
	private String dishesName;
	/**
      * 
      **/
	private Integer pDishesId;
	/**
	 * 创建时间
	 **/
	private java.sql.Timestamp creDate;
	/**
	 * 修改时间
	 **/
	private java.sql.Timestamp updDate;
	/**
	 * 1-是 0-否
	 **/
	private String isLeaf;
	/**
      * 
      **/
	private Integer dishesLevel;

	private String dishsImg;
	/**
	 * 菜谱id
	 */
	private String menuId;
	/**
	 * 菜谱url
	 */
	private String menuUrl;

	public String getDishsImg() {
		return dishsImg;
	}

	public void setDishsImg(String dishsImg) {
		this.dishsImg = dishsImg;
	}

	public EnnDishes() {
		super();
	}

	public Integer getDishesId() {
		return this.dishesId;
	}

	public void setDishesId(Integer dishesId) {
		this.dishesId = dishesId;
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

	public Integer getpDishesId() {
		return this.pDishesId;
	}

	public void setpDishesId(Integer pDishesId) {
		this.pDishesId = pDishesId;
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

	public String getIsLeaf() {
		return this.isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getDishesLevel() {
		return this.dishesLevel;
	}

	public void setDishesLevel(Integer dishesLevel) {
		this.dishesLevel = dishesLevel;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

}