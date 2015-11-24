package com.xyyy.shop.model;

import java.math.BigDecimal;
public class MemcardRechargeDTO {
	
	private String cardCode;
	private BigDecimal money;
	private String orderForm;
	private Integer membId;
	private String isNeed;
	private String invoiceType;
	private String invoiceTitle;
	private String invoiceCont;
	private String trade_type;
	private  Integer receId;
	
	public Integer getReceId() {
		return receId;
	}

	public void setReceId(Integer receId) {
		this.receId = receId;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
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

	public String getInvoiceCont() {
		return invoiceCont;
	}

	public void setInvoiceCont(String invoiceCont) {
		this.invoiceCont = invoiceCont;
	}

	public String getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(String isNeed) {
		this.isNeed = isNeed;
	}

	public Integer getMembId() {
		return membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}

	public String getOrderForm() {
		return orderForm;
	}

	public void setOrderForm(String orderForm) {
		this.orderForm = orderForm;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}
