package com.xyyy.shop.model;


import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnPayRecord {
    
    /**
      * 订单支付记录ID
      **/
	private Integer payRecordId;
    /**
      * 订单ID
      **/
	private Integer orderId;
    /**
      * 订单编号
      **/
	private String orderSeq;
    /**
      * 支付方式
            01-货到付款
            02-在线支付
            03-公司转账
            04-邮局汇款
            05-会员卡支付
            06-礼品卡支付
            后台下单---------------------------
            07-货到付款【含阳光卡】
            08-销售回款
            09-样品赠品
            10-月结
            
      **/
	private String payMethod;
    /**
      * 支付金额
      **/
	private java.math.BigDecimal payAmount;
    /**
      * 支付卡号
      **/
	private String payCardnm;
    /**
      * 支付单流水号
      **/
	private String paySeq;
    /**
      * 支付状态
            01-待支付
            02-支付中
            03-支付完成
      **/
	private String payState;
    /**
      * 支付时间：时分秒
      **/
	private java.sql.Timestamp payTime;
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


	public EnnPayRecord() {
		super();
	}

	public Integer getPayRecordId() {
		return this.payRecordId;
	}

	public void setPayRecordId(Integer payRecordId) {
		this.payRecordId = payRecordId;
	}
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderSeq() {
		return this.orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public java.math.BigDecimal getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(java.math.BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayCardnm() {
		return this.payCardnm;
	}

	public void setPayCardnm(String payCardnm) {
		this.payCardnm = payCardnm;
	}
	public String getPaySeq() {
		return this.paySeq;
	}

	public void setPaySeq(String paySeq) {
		this.paySeq = paySeq;
	}
	public String getPayState() {
		return this.payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getPayTime() {
		return this.payTime;
	}

	public void setPayTime(java.sql.Timestamp payTime) {
		this.payTime = payTime;
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