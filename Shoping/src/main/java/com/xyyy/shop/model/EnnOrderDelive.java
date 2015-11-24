package com.xyyy.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnOrderDelive {

	/**
      * 
      **/
	private Integer orderDeliveId;
	/**
	 * 订单ID
	 **/
	private Integer orderId;
	/**
      * 
      **/
	private String deliverVendor;
	/**
      * 
      **/
	private String deliverSeq;
	/**
      * 
      **/
	private String deliverState;
	/**
      * 
      **/
	private java.sql.Timestamp deliverDate;
	/**
      * 
      **/
	private java.sql.Timestamp receiveDate;
	/**
      * 
      **/
	private String receName;
	/**
	 * 创建人ID
	 **/
	private Integer creUserId;
	/**
	 * 创建人名称
	 **/
	private String creUserName;
	/**
	 * 创建人机构ID
	 **/
	private Integer creOrgId;
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
	 * 修改人机构ID
	 **/
	private Integer updOrgId;
	/**
	 * 修改时间
	 **/
	private java.sql.Timestamp updDate;
	/**
	 * 备注
	 **/
	private String remark;
	/**
	 * 总件数
	 **/
	private Integer totalNum;
	/**
	 * 妥投件数
	 **/
	private Integer postOk;
	/**
	 * 异常件数
	 **/
	private Integer postNo;

	public EnnOrderDelive() {
		super();
	}

	public Integer getOrderDeliveId() {
		return this.orderDeliveId;
	}

	public void setOrderDeliveId(Integer orderDeliveId) {
		this.orderDeliveId = orderDeliveId;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getDeliverVendor() {
		return this.deliverVendor;
	}

	public void setDeliverVendor(String deliverVendor) {
		this.deliverVendor = deliverVendor;
	}

	public String getDeliverSeq() {
		return this.deliverSeq;
	}

	public void setDeliverSeq(String deliverSeq) {
		this.deliverSeq = deliverSeq;
	}

	public String getDeliverState() {
		return this.deliverState;
	}

	public void setDeliverState(String deliverState) {
		this.deliverState = deliverState;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getDeliverDate() {
		return this.deliverDate;
	}

	public void setDeliverDate(java.sql.Timestamp deliverDate) {
		this.deliverDate = deliverDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(java.sql.Timestamp receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getReceName() {
		return this.receName;
	}

	public void setReceName(String receName) {
		this.receName = receName;
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

	public Integer getCreOrgId() {
		return this.creOrgId;
	}

	public void setCreOrgId(Integer creOrgId) {
		this.creOrgId = creOrgId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

	public Integer getUpdOrgId() {
		return this.updOrgId;
	}

	public void setUpdOrgId(Integer updOrgId) {
		this.updOrgId = updOrgId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(java.sql.Timestamp updDate) {
		this.updDate = updDate;
	}
     
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getPostOk() {
		return postOk;
	}

	public void setPostOk(Integer postOk) {
		this.postOk = postOk;
	}

	public Integer getPostNo() {
		return postNo;
	}

	public void setPostNo(Integer postNo) {
		this.postNo = postNo;
	}
}