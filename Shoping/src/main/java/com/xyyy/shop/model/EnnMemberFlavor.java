package com.xyyy.shop.model;


public class EnnMemberFlavor {
    
    /**
      * 会员等级ID
      **/
	private Integer flavorId;
    /**
      * 会员
      **/
	private Integer membId;
    /**
      * 
      **/
	private Integer dishesId;
    /**
      * 
      **/
	private String dishesCode;
    /**
      * 01-想要
            02-最近想要
            03-喜欢
            04-无所谓（不显示）
            05-不想要
            06-忌口
      **/
	private String flavorType;
    /**
      * 创建时间
      **/
	private java.sql.Timestamp creDate;
    /**
      * 修改时间
      **/
	private java.sql.Timestamp updDate;


	public EnnMemberFlavor() {
		super();
	}

	public Integer getFlavorId() {
		return this.flavorId;
	}

	public void setFlavorId(Integer flavorId) {
		this.flavorId = flavorId;
	}
	public Integer getMembId() {
		return this.membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
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
	public String getFlavorType() {
		return this.flavorType;
	}

	public void setFlavorType(String flavorType) {
		this.flavorType = flavorType;
	}
	public java.sql.Timestamp getCreDate() {
		return this.creDate;
	}

	public void setCreDate(java.sql.Timestamp creDate) {
		this.creDate = creDate;
	}
	public java.sql.Timestamp getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(java.sql.Timestamp updDate) {
		this.updDate = updDate;
	}
}