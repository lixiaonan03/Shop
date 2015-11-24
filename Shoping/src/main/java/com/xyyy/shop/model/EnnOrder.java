package com.xyyy.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;


public class EnnOrder {

	/**
	 * 订单ID
	 **/
	private Integer orderId;
	/**
	 * 父订单编号
	 **/
	private String parentorderId;
	/**
	 * 01-微商城 02-WEB商城03-京东 04-天猫 99-其他
	 **/
	private String orderFrom;
	/**
	 * 会员
	 **/
	private Integer membId;
	/**
	 * 订单日期 yyyy/mm/dd hh:mm:ss
	 **/
	private java.sql.Timestamp orderTime;
	/**
	 * 订单总金额=商品总金额+实际运费金额-积分抵消金额
	 **/
	private java.math.BigDecimal totalPrice;
	/**
	 * 01待付款 02已付款 03已配货 04已发货 05已收货-（待评价） 07已完成（评价完成） 06-已取消i(作废)
	 * 
	 * 11申请退货 12退货审核中 13退货被拒绝 14退货申请通过 15退货中 16退货完成
	 * 
	 * 21申请换货 22换货审核中 23换货被拒绝 24换货申请通过 25换货中 26换货完成
	 **/
	private String orderStatus;
	/**
	 * 订单类型 01-普通单 02-日客单 03-再买点订单
	 **/
	private String orderType;
	/**
	 * 默认否 0-否 1-是
	 **/
	private String isNeed;
	/**
	 * 0-否 1-是
	 **/
	private String isInvoiced;
	/**
	 * 01-普通发票 02-增值税发票
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
	 * 01-货到付款 02-在线支付 03-公司转账04-邮局汇款05-会员卡支付
	 **/
	private String payMethod;
	/**
	 * 0-未支付1-已支付
	 **/
	private String payFlag;
	/**
	 * 支付卡号
	 **/
	private String payCardnm;
	/**
	 * 支付日期
	 **/
	private java.sql.Timestamp payTime;
	/**
	 * 支付返回码
	 **/
	private String payReturnCode;
	/**
	 * 积分消费额
	 **/
	private Integer payScore;
	/**
	 * 积分抵消金额
	 **/
	private java.math.BigDecimal payScoreMoney;
	/**
	 * 失效时间
	 **/
	private java.sql.Timestamp invalidTime;
	/**
      * 
      **/
	private String remark;
	/**
	 * 原订单号
	 **/
	private String oldOrderId;
	/**
	 * 收货人姓名
	 **/
	private String receName;
	/**
	 * 省份
	 **/
	private String province;
	/**
	 * 城市
	 **/
	private String city;
	/**
	 * 区县
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
	 * 收货人联系方式
	 **/
	private String receTel;
	/**
	 * 收货人邮箱
	 **/
	private String receEmail;
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
	 * 订单关联会员口味
	 **/
	private String orderFavour;
	/**
	 * 客户自定义收货日期
	 **/
	private java.sql.Timestamp memReceDate;
	/**
	 * 任务期号（获取配方时使用）
	 **/
	private String taskCode;
	/**
	 * 针对再买点，日客单为父订单，再买点为子订单
	 **/
	private Integer parentOrderId;
	/**
	 * 商品总金额
	 **/
	private java.math.BigDecimal goodsAmount;
	/**
	 * 商品优惠金额
	 **/
	private java.math.BigDecimal goodsDisAmount;
	/**
	 * 订单运费
	 **/
	private java.math.BigDecimal orderFare;
	/**
	 * 实际运费金额
	 **/
	private java.math.BigDecimal realOrderFare;
	/**
	 * 订单编号
	 **/
	private String orderSeq;
	/**
	 * 下单方式 01-微商城 02-IOS 03-Andriod 04-Web网站
	 **/
	private String orderPlace;
	/**
	 * 0-否 1-是
	 **/
	private String isBind;
	/**
	 * 绑定会员卡号
	 **/
	private Integer bindCard;

	private String viewMemReceDate;
	/**
	 * 配送方式： 01-快递配送 02-客户自提
	 **/
	private String deliverType;
	/**
	 * 支付凭证路径 提供下载路径即可
	 **/
	private String payCertifiPath;
	/**
	 * 未支付理由
	 **/
	private String nopayReason;
	/**
	 * 支付单流水号
	 **/
	private String paySeq;

	public String getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}

	public String getPayCertifiPath() {
		return payCertifiPath;
	}

	public void setPayCertifiPath(String payCertifiPath) {
		this.payCertifiPath = payCertifiPath;
	}

	public String getNopayReason() {
		return nopayReason;
	}

	public void setNopayReason(String nopayReason) {
		this.nopayReason = nopayReason;
	}

	public String getPaySeq() {
		return paySeq;
	}

	public void setPaySeq(String paySeq) {
		this.paySeq = paySeq;
	}

	public String getViewMemReceDate() {
		return viewMemReceDate;
	}

	public void setViewMemReceDate(String viewMemReceDate) {
		this.viewMemReceDate = viewMemReceDate;
	}

	public EnnOrder() {
		super();
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getParentorderId() {
		return this.parentorderId;
	}

	public void setParentorderId(String parentorderId) {
		this.parentorderId = parentorderId;
	}

	public String getOrderFrom() {
		return this.orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public Integer getMembId() {
		return this.membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(java.sql.Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public java.math.BigDecimal getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(java.math.BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getIsNeed() {
		return this.isNeed;
	}

	public void setIsNeed(String isNeed) {
		this.isNeed = isNeed;
	}

	public String getIsInvoiced() {
		return this.isInvoiced;
	}

	public void setIsInvoiced(String isInvoiced) {
		this.isInvoiced = isInvoiced;
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

	public String getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getPayFlag() {
		return this.payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public String getPayCardnm() {
		return this.payCardnm;
	}

	public void setPayCardnm(String payCardnm) {
		this.payCardnm = payCardnm;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getPayTime() {
		return this.payTime;
	}

	public void setPayTime(java.sql.Timestamp payTime) {
		this.payTime = payTime;
	}

	public String getPayReturnCode() {
		return this.payReturnCode;
	}

	public void setPayReturnCode(String payReturnCode) {
		this.payReturnCode = payReturnCode;
	}

	public Integer getPayScore() {
		return this.payScore;
	}

	public void setPayScore(Integer payScore) {
		this.payScore = payScore;
	}

	public java.math.BigDecimal getPayScoreMoney() {
		return this.payScoreMoney;
	}

	public void setPayScoreMoney(java.math.BigDecimal payScoreMoney) {
		this.payScoreMoney = payScoreMoney;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(java.sql.Timestamp invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOldOrderId() {
		return this.oldOrderId;
	}

	public void setOldOrderId(String oldOrderId) {
		this.oldOrderId = oldOrderId;
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

	public String getOrderFavour() {
		return this.orderFavour;
	}

	public void setOrderFavour(String orderFavour) {
		this.orderFavour = orderFavour;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getMemReceDate() {
		return this.memReceDate;
	}

	public void setMemReceDate(java.sql.Timestamp memReceDate) {
		this.memReceDate = memReceDate;
	}

	public String getTaskCode() {
		return this.taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Integer getParentOrderId() {
		return this.parentOrderId;
	}

	public void setParentOrderId(Integer parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public java.math.BigDecimal getGoodsAmount() {
		return this.goodsAmount;
	}

	public void setGoodsAmount(java.math.BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public java.math.BigDecimal getGoodsDisAmount() {
		return this.goodsDisAmount;
	}

	public void setGoodsDisAmount(java.math.BigDecimal goodsDisAmount) {
		this.goodsDisAmount = goodsDisAmount;
	}

	public java.math.BigDecimal getOrderFare() {
		return this.orderFare;
	}

	public void setOrderFare(java.math.BigDecimal orderFare) {
		this.orderFare = orderFare;
	}

	public java.math.BigDecimal getRealOrderFare() {
		return this.realOrderFare;
	}

	public void setRealOrderFare(java.math.BigDecimal realOrderFare) {
		this.realOrderFare = realOrderFare;
	}

	public String getOrderSeq() {
		return this.orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

	public String getOrderPlace() {
		return this.orderPlace;
	}

	public void setOrderPlace(String orderPlace) {
		this.orderPlace = orderPlace;
	}

	public String getIsBind() {
		return this.isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	public Integer getBindCard() {
		return this.bindCard;
	}

	public void setBindCard(Integer bindCard) {
		this.bindCard = bindCard;
	}
}