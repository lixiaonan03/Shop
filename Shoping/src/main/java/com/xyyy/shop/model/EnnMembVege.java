package com.xyyy.shop.model;


public class EnnMembVege {
    
    /**
      * 菜箱指定ID
      **/
	private Integer vegeId;
    /**
      * 配送预告
      **/
	private Integer noticeId;
    /**
      * 会员
      **/
	private Integer membId;
    /**
      * 菜品ID
      **/
	private Integer dishesId;
    /**
      * 菜品编号
      **/
	private String dishesCode;
    /**
      * 菜品名称
      **/
	private String dishesName;
    /**
      * 口味类型
            01-想要
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


	public EnnMembVege() {
		super();
	}

	public Integer getVegeId() {
		return this.vegeId;
	}

	public void setVegeId(Integer vegeId) {
		this.vegeId = vegeId;
	}
	public Integer getNoticeId() {
		return this.noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
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
	public String getDishesName() {
		return this.dishesName;
	}

	public void setDishesName(String dishesName) {
		this.dishesName = dishesName;
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