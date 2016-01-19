package com.xyyy.shop.model;

public class EnnActionGift {
    /**
      * 
      **/
	private Integer actionGiftId;
    /**
      * 促销规则ID
      **/
	private Integer rulesId;
    /**
      * 商品ID
      **/
	private Integer goodsId;
    /**
      * 
      **/
	private java.math.BigDecimal mallPrice;
    /**
      * 商品协议供货价
      **/
	private java.math.BigDecimal promPrice;
    /**
      * 赠送数量
      **/
	private Integer giftNum;
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


	public EnnActionGift() {
		super();
	}

	public Integer getActionGiftId() {
		return this.actionGiftId;
	}

	public void setActionGiftId(Integer actionGiftId) {
		this.actionGiftId = actionGiftId;
	}
	public Integer getRulesId() {
		return this.rulesId;
	}

	public void setRulesId(Integer rulesId) {
		this.rulesId = rulesId;
	}
	public Integer getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public java.math.BigDecimal getMallPrice() {
		return this.mallPrice;
	}

	public void setMallPrice(java.math.BigDecimal mallPrice) {
		this.mallPrice = mallPrice;
	}
	public java.math.BigDecimal getPromPrice() {
		return this.promPrice;
	}

	public void setPromPrice(java.math.BigDecimal promPrice) {
		this.promPrice = promPrice;
	}
	public Integer getGiftNum() {
		return this.giftNum;
	}

	public void setGiftNum(Integer giftNum) {
		this.giftNum = giftNum;
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
	public java.sql.Timestamp getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(java.sql.Timestamp updDate) {
		this.updDate = updDate;
	}
}