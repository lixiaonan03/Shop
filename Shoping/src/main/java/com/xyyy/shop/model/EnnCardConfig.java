package com.xyyy.shop.model;


import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnCardConfig {
    
    /**
      * 会员配送配置ID
      **/
	private Integer configId;
    /**
      * 绑定会员卡ID
      **/
	private Integer bindVipcardId;
    /**
      * 会员
      **/
	private Integer membId;
    /**
      * 间隔周次
      **/
	private Integer interWeek;
    /**
      * 次/周
      **/
	private Integer deliveFreq;
    /**
      * 配送时间: 周几配送，以逗号分隔（1,2,3,4,5,6,7）
            
      **/
	private String deliveWeek;
    /**
      * 
      **/
	private String remark;
    /**
      * 
      **/
	private String receName;
    /**
      * 
      **/
	private String province;
    /**
      * 
      **/
	private String city;
    /**
      * 
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
      * 创建时间
      **/
	private java.sql.Timestamp creDate;
    /**
      * 修改时间
      **/
	private java.sql.Timestamp updDate;

	/**
	 * 下次配送日期
	 **/
	private java.sql.Timestamp deliveInitday;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getDeliveInitday() {
		return deliveInitday;
	}

	public void setDeliveInitday(java.sql.Timestamp deliveInitday) {
		this.deliveInitday = deliveInitday;
	}

	public EnnCardConfig() {
		super();
	}

	public Integer getConfigId() {
		return this.configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	public Integer getBindVipcardId() {
		return this.bindVipcardId;
	}

	public void setBindVipcardId(Integer bindVipcardId) {
		this.bindVipcardId = bindVipcardId;
	}
	public Integer getMembId() {
		return this.membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}
	public Integer getInterWeek() {
		return this.interWeek;
	}

	public void setInterWeek(Integer interWeek) {
		this.interWeek = interWeek;
	}
	public Integer getDeliveFreq() {
		return this.deliveFreq;
	}

	public void setDeliveFreq(Integer deliveFreq) {
		this.deliveFreq = deliveFreq;
	}
	public String getDeliveWeek() {
		return this.deliveWeek;
	}

	public void setDeliveWeek(String deliveWeek) {
		this.deliveWeek = deliveWeek;
	}
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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