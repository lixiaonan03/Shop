package com.xyyy.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;


public class EnnMemberReceipt {
    
    /**
      * 收货地址ID
      **/
	private Integer receId;
    /**
      * 会员
      **/
	private Integer membId;
    /**
      * 
      **/
	private String receName;
    /**
      * 省
      **/
	private String province;
    /**
      *市 
      **/
	private String city;
    /**
      * 区
      **/
	private String country;
    /**
      * 收货人详细地址
      **/
	private String receAddress;
    /**
      * 
      **/
	private String recePhone;
    /**
      * 
      **/
	private String receTel;
    /**
      * 
      **/
	private String receEmail;
    /**
      * 0-否
            1-是
      **/
	private String isDefault;
    /**
      * 创建时间
      **/
	private java.sql.Timestamp creDate;
    /**
      * 修改时间
      **/
	private java.sql.Timestamp updDate;


	public EnnMemberReceipt() {
		super();
	}

	public Integer getReceId() {
		return this.receId;
	}

	public void setReceId(Integer receId) {
		this.receId = receId;
	}
	public Integer getMembId() {
		return this.membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}
	public String getReceName() {
		return this.receName;
	}

	public void setReceName(String receName) {
		this.receName = receName;
	}
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	public String getReceAddress() {
		return this.receAddress;
	}

	public void setReceAddress(String receAddress) {
		this.receAddress = receAddress;
	}
	public String getRecePhone() {
		return this.recePhone;
	}

	public void setRecePhone(String recePhone) {
		this.recePhone = recePhone;
	}
	public String getReceTel() {
		return this.receTel;
	}

	public void setReceTel(String receTel) {
		this.receTel = receTel;
	}
	public String getReceEmail() {
		return this.receEmail;
	}

	public void setReceEmail(String receEmail) {
		this.receEmail = receEmail;
	}
	public String getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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