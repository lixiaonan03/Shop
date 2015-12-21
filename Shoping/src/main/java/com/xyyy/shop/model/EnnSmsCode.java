package com.xyyy.shop.model;


public class EnnSmsCode {

	/**
	 * 验证码ID
	 **/
	private Integer codeId;
	/**
	 * 验证码类型 01-注册验证码 02-找回密码 03-绑定老会员卡 04-验证手机号 05-更换手机号
	 **/
	private String verifyType;
	/**
      * 
      **/
	private String userPhone;
	/**
	 * 验证码
	 **/
	private String verifyCode;
	/**
      * 
      **/
	private java.sql.Timestamp invaildTime;
	/**
	 * 状态 01-正常 02-失效
	 **/
	private String smsState;
	/**
	 * 验证结果 00-待验证 01-验证成功 02-验证失败
	 **/
	private String verifyRes;
	/**
	 * 创建时间
	 **/
	private java.sql.Timestamp creDate;
	/**
	 * 修改时间
	 **/
	private java.sql.Timestamp updDate;

	public EnnSmsCode() {
		super();
	}

	public Integer getCodeId() {
		return this.codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public String getVerifyType() {
		return this.verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getVerifyCode() {
		return this.verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public java.sql.Timestamp getInvaildTime() {
		return this.invaildTime;
	}

	public void setInvaildTime(java.sql.Timestamp invaildTime) {
		this.invaildTime = invaildTime;
	}

	public String getSmsState() {
		return this.smsState;
	}

	public void setSmsState(String smsState) {
		this.smsState = smsState;
	}

	public String getVerifyRes() {
		return this.verifyRes;
	}

	public void setVerifyRes(String verifyRes) {
		this.verifyRes = verifyRes;
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