package com.xyyy.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnMemberPaypwd {
    
    /**
      * 支付密码表ID
      **/
	private Integer paypwdId;
    /**
      * 会员
      **/
	private Integer membId;
    /**
      * 支付密码
      **/
	private String payPwd;
    /**
      * 密码设置时间
      **/
	private java.sql.Timestamp setTime;
    /**
      * 密码锁定时间
      **/
	private java.sql.Timestamp lockTime;
    /**
      * 支付密码状态
            01-正常
            02-锁定
      **/
	private String pwdState;
    /**
      * 密码错误次数
            密码输入错误超过3次，锁定不可用
      **/
	private Integer errorTimes;
    /**
      * 
      **/
	private String remark;
    /**
      * 创建时间
      **/
	private java.sql.Timestamp creDate;
    /**
      * 修改时间
      **/
	private java.sql.Timestamp updDate;


	public EnnMemberPaypwd() {
		super();
	}

	public Integer getPaypwdId() {
		return this.paypwdId;
	}

	public void setPaypwdId(Integer paypwdId) {
		this.paypwdId = paypwdId;
	}
	public Integer getMembId() {
		return this.membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}
	public String getPayPwd() {
		return this.payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getSetTime() {
		return this.setTime;
	}

	public void setSetTime(java.sql.Timestamp setTime) {
		this.setTime = setTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getLockTime() {
		return this.lockTime;
	}

	public void setLockTime(java.sql.Timestamp lockTime) {
		this.lockTime = lockTime;
	}
	public String getPwdState() {
		return this.pwdState;
	}

	public void setPwdState(String pwdState) {
		this.pwdState = pwdState;
	}
	public Integer getErrorTimes() {
		return this.errorTimes;
	}

	public void setErrorTimes(Integer errorTimes) {
		this.errorTimes = errorTimes;
	}
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
}