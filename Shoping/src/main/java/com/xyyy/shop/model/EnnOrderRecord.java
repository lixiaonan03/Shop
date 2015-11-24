package com.xyyy.shop.model;


import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnOrderRecord {

	/**
	 * 订单记录ID
	 **/
	private Integer recordId;
	/**
	 * 订单ID
	 **/
	private Integer orderId;
	/**
	 * 变更前状态 01待付款 02已付款 03已配货 04已发货 05完成 06作废
	 * 
	 * 11申请退货 12退货审核中 13退货被拒绝 14退货申请通过 15退货中 16退货完成
	 * 
	 * 21申请换货 22换货审核中 23换货被拒绝 24换货申请通过 25换货中 26换货完成
	 **/
	private String preState;
	/**
	 * 01待付款 02已付款 03已配货 04已发货 05完成 06作废
	 * 
	 * 11申请退货 12退货审核中 13退货被拒绝 14退货申请通过 15退货中 16退货完成
	 * 
	 * 21申请换货 22换货审核中 23换货被拒绝 24换货申请通过 25换货中 26换货完成
	 **/
	private String curState;
	/**
	 * 跟踪记录描述: 记录当前操作描述: xxxx年xx月xx日 xx时xx分xx秒 系统下单成功； xxxx年xx月xx日 xx时xx分xx秒
	 * 支付成功；等
	 **/
	private String recDesc;
	/**
	 * 记录时间
	 **/
	private java.sql.Timestamp recTime;
	/**
	 * 记录人ID
	 **/
	private Integer recUserId;
	/**
	 * 记录人名称
	 **/
	private String recName;
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

	/**
	 * 记录类型 01-新增 02-删除 03-修改 04-取消 05-退换货
	 **/
	private String recType;

	public String getRecType() {
		return recType;
	}

	public void setRecType(String recType) {
		this.recType = recType;
	}

	public EnnOrderRecord() {
		super();
	}

	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getPreState() {
		return this.preState;
	}

	public void setPreState(String preState) {
		this.preState = preState;
	}

	public String getCurState() {
		return this.curState;
	}

	public void setCurState(String curState) {
		this.curState = curState;
	}

	public String getRecDesc() {
		return this.recDesc;
	}

	public void setRecDesc(String recDesc) {
		this.recDesc = recDesc;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getRecTime() {
		return this.recTime;
	}

	public void setRecTime(java.sql.Timestamp recTime) {
		this.recTime = recTime;
	}

	public Integer getRecUserId() {
		return this.recUserId;
	}

	public void setRecUserId(Integer recUserId) {
		this.recUserId = recUserId;
	}

	public String getRecName() {
		return this.recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(java.sql.Timestamp updDate) {
		this.updDate = updDate;
	}
}