package com.xyyy.shop.model;

import java.math.BigDecimal;



public class EnnCardRule {
    
    /**
      * 充值规则ID
      **/
	private Integer ruleId;
    /**
      * 充值金额下限
      **/
	private java.math.BigDecimal lowAmount;
    /**
      * 充值金额上限
      **/
	private java.math.BigDecimal highAmount;
    /**
      * 赠送金额
      **/
	private java.math.BigDecimal giveAmount;
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


	public EnnCardRule() {
		super();
	}

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public java.math.BigDecimal getLowAmount() {
		if(null==lowAmount)lowAmount=new BigDecimal(0);
		return this.lowAmount;
	}

	public void setLowAmount(java.math.BigDecimal lowAmount) {
		this.lowAmount = lowAmount;
	}
	public java.math.BigDecimal getHighAmount() {
		if(null==highAmount)highAmount=new BigDecimal(0);
		return this.highAmount;
	}

	public void setHighAmount(java.math.BigDecimal highAmount) {
		this.highAmount = highAmount;
	}
	public java.math.BigDecimal getGiveAmount() {
		return this.giveAmount;
	}

	public void setGiveAmount(java.math.BigDecimal giveAmount) {
		this.giveAmount = giveAmount;
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