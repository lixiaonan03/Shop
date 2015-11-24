package com.xyyy.shop.model;


public class EnnMemberInvoice {
    
    /**
      * 发票信息ID
      **/
	private Integer invoiceId;
    /**
      * 会员
      **/
	private Integer membId;
    /**
      * 发票类型
            01-普通发票
            02-增值税发票
      **/
	private String invoiceType;
    /**
      * 发票抬头
      **/
	private String invoiceTitle;
    /**
      * 发票内容
      **/
	private String invoiceCont;
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

	
	public EnnMemberInvoice() {
		super();
	}

	public Integer getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Integer getMembId() {
		return this.membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}
	public String getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceTitle() {
		return this.invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getInvoiceCont() {
		return this.invoiceCont;
	}

	public void setInvoiceCont(String invoiceCont) {
		this.invoiceCont = invoiceCont;
	}
	public String getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
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