package com.xyyy.shop.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 我的订单使用
 * 
 * @author Administrator
 * 
 */
public class OrderInfoDetailVO2 {
	// 订单状态
	private String orderStatus;
	// 运费
	private String yunfei;
	// 优惠
	private String youhui;
	// 应付总金额
	private String totalPrice;
	// 收货人
	private String receivePerson;
	// 收货人手机号码
	private String receivePersonPhone;
	// 收货地址
	private String receiveAddress;
	// 物流信息
	private String wuliu;
	// 商品信息
	private List<OrderItemVO2> orderItemVOs;
	// 订单Id
	private String orderId;
	// 订单编号
	private String orderSeq;
	// 支付单号
	private String paySeq;
	// 下单时间
	private java.sql.Timestamp insertOrderTime;
	// 收货时间
	private java.sql.Timestamp receiveGoodTime;
	// 发票类型
	private String invoiceType;
	// 发票抬头
	private String invoiceTitle;
	// 支付时间
	private java.sql.Timestamp payTime;
	//支付方式
	private String payMethod;
    //支付卡号
	private String payedCardNum;
	//支付标志
	private String payFlag;
	//业务记录信息
	private List<EnnOrderRecord>  ennOrderRecords;
	
	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public List<EnnOrderRecord> getEnnOrderRecords() {
		return ennOrderRecords;
	}

	public void setEnnOrderRecords(List<EnnOrderRecord> ennOrderRecords) {
		this.ennOrderRecords = ennOrderRecords;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
    
	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getYunfei() {
		return yunfei;
	}

	public void setYunfei(String yunfei) {
		this.yunfei = yunfei;
	}

	public String getYouhui() {
		return youhui;
	}

	public void setYouhui(String youhui) {
		this.youhui = youhui;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getReceivePersonPhone() {
		return receivePersonPhone;
	}

	public void setReceivePersonPhone(String receivePersonPhone) {
		this.receivePersonPhone = receivePersonPhone;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getWuliu() {
		return wuliu;
	}

	public void setWuliu(String wuliu) {
		this.wuliu = wuliu;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayedCardNum() {
		return payedCardNum;
	}

	public void setPayedCardNum(String payedCardNum) {
		this.payedCardNum = payedCardNum;
	}

	public String getPaySeq() {
		return paySeq;
	}

	public void setPaySeq(String paySeq) {
		this.paySeq = paySeq;
	}

	public java.sql.Timestamp getInsertOrderTime() {
		return insertOrderTime;
	}

	public void setInsertOrderTime(java.sql.Timestamp insertOrderTime) {
		this.insertOrderTime = insertOrderTime;
	}

	public java.sql.Timestamp getReceiveGoodTime() {
		return receiveGoodTime;
	}

	public void setReceiveGoodTime(java.sql.Timestamp receiveGoodTime) {
		this.receiveGoodTime = receiveGoodTime;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public List<OrderItemVO2> getOrderItemVOs() {
		return orderItemVOs;
	}

	public void setOrderItemVOs(List<OrderItemVO2> orderItemVOs) {
		this.orderItemVOs = orderItemVOs;
	}
}
