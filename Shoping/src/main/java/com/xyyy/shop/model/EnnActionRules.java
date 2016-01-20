package com.xyyy.shop.model;

public class EnnActionRules {
    
    /**
      * 
      **/
	private Integer rulesId;
    /**
      * 商品描述ID
      **/
	private Integer actionId;
    /**
      * 
      **/
	private String ruleName;
    /**
      * 是否互斥
            1-是
            0-否
            默认是
      **/
	private String isMutex;
    /**
      * 满减（赠）金额
      **/
	private java.math.BigDecimal actionAmount;
    /**
      * 减免金额
      **/
	private java.math.BigDecimal cutAmount;
    /**
      * 促销折扣：
            默认10，只有是打折促销活动时，才进行修改
      **/
	private java.math.BigDecimal promDiscount;
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


	public EnnActionRules() {
		super();
	}

	public Integer getRulesId() {
		return this.rulesId;
	}

	public void setRulesId(Integer rulesId) {
		this.rulesId = rulesId;
	}
	public Integer getActionId() {
		return this.actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getIsMutex() {
		return this.isMutex;
	}

	public void setIsMutex(String isMutex) {
		this.isMutex = isMutex;
	}
	public java.math.BigDecimal getActionAmount() {
		return this.actionAmount;
	}

	public void setActionAmount(java.math.BigDecimal actionAmount) {
		this.actionAmount = actionAmount;
	}
	public java.math.BigDecimal getCutAmount() {
		return this.cutAmount;
	}

	public void setCutAmount(java.math.BigDecimal cutAmount) {
		this.cutAmount = cutAmount;
	}
	public java.math.BigDecimal getPromDiscount() {
		return this.promDiscount;
	}

	public void setPromDiscount(java.math.BigDecimal promDiscount) {
		this.promDiscount = promDiscount;
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