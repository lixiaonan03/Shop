package com.xyyy.shop.model;

import java.util.List;


/**
 * 我的订单中使用
 * 
 * @author Administrator
 * 
 */
public class OrderVO {
	private String orderId;
	private String orderSeq;
	private String totalGoodsNum;
	private String totalPrice;
	private String orderStatus;
	private List<OrderItemVO> orderItems;
	private String orderType;
	private String payFlag;

	 
	public String getPayFlag() {
		return payFlag;
	}
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTotalGoodsNum() {
		return totalGoodsNum;
	}
	public void setTotalGoodsNum(String totalGoodsNum) {
		this.totalGoodsNum = totalGoodsNum;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<OrderItemVO> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemVO> orderItems) {
		this.orderItems = orderItems;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
