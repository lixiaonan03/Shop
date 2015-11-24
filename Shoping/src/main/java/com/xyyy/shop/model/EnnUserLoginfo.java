package com.xyyy.shop.model;


public class EnnUserLoginfo {
    
    /**
      * 用户ID
      **/
	private Integer userId;
    /**
      * 会员
      **/
	private Integer membId;
    /**
      * 
      **/
	private String userName;
    /**
      * 密码
      **/
	private String userPwd;
    /**
      * 
      **/
	private String userPhone;
    /**
      * 
      **/
	private String userEmail;
    /**
      * 01-正常
            02-锁定
            03-冻结
            04-注销
      **/
	private String state;
    /**
      * 
      **/
	private java.sql.Timestamp lockTime;
    /**
      * 
      **/
	private Integer errorTimes;
    /**
      * 
      **/
	private java.sql.Timestamp lastLogintime;
    /**
      * 创建时间
      **/
	private java.sql.Timestamp creDate;
    /**
      * 修改时间
      **/
	private java.sql.Timestamp updDate;


	public EnnUserLoginfo() {
		super();
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMembId() {
		return this.membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}
	public java.sql.Timestamp getLockTime() {
		return this.lockTime;
	}

	public void setLockTime(java.sql.Timestamp lockTime) {
		this.lockTime = lockTime;
	}
	public Integer getErrorTimes() {
		return this.errorTimes;
	}

	public void setErrorTimes(Integer errorTimes) {
		this.errorTimes = errorTimes;
	}
	public java.sql.Timestamp getLastLogintime() {
		return this.lastLogintime;
	}

	public void setLastLogintime(java.sql.Timestamp lastLogintime) {
		this.lastLogintime = lastLogintime;
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