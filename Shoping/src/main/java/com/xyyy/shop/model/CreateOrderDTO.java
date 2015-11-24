package com.xyyy.shop.model;

import java.util.List;

public class CreateOrderDTO {
	/**
	 * 会员id
	 */
	private String ennMemId;
    
	
	/**
	 * 收货信息
	 */
	private EnnMemberReceipt ennMemberReceipt;

     
	/**
	 * 订单
	 */
	private EnnOrder ennOrder;

	/**
	 * 新豆
	 */

	// 商品价格
	private String goodsPrice;
	// 配送费用
	private String peisongfeiyong;
	// 优惠折扣
	private String youhiuzhekou;
	// 新豆
	private String xindou;
	// 应付总金额
	private String yinfuzongjine;

	/**
	 * 商品列表
	 * 
	 * @return
	 */
	private List<OrderItemVO> orderItemVOs;
	
	private String canBuyGoodIds;
	/**
	 * 会员卡支付时会员卡的id
	 */
	private List<Integer> payCardIds; 

	public List<Integer> getPayCardIds() {
		return payCardIds;
	}

	public void setPayCardIds(List<Integer> payCardIds) {
		this.payCardIds = payCardIds;
	}

	public String getCanBuyGoodIds() {
		return canBuyGoodIds;
	}

	public void setCanBuyGoodIds(String canBuyGoodIds) {
		this.canBuyGoodIds = canBuyGoodIds;
	}

	public List<OrderItemVO> getOrderItemVOs() {
		return orderItemVOs;
	}

	public void setOrderItemVOs(List<OrderItemVO> orderItemVOs) {
		this.orderItemVOs = orderItemVOs;
	}

	public EnnMemberReceipt getEnnMemberReceipt() {
		return ennMemberReceipt;
	}

	public void setEnnMemberReceipt(EnnMemberReceipt ennMemberReceipt) {
		this.ennMemberReceipt = ennMemberReceipt;
	}

	public EnnOrder getEnnOrder() {
		return ennOrder;
	}

	public void setEnnOrder(EnnOrder ennOrder) {
		this.ennOrder = ennOrder;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getPeisongfeiyong() {
		return peisongfeiyong;
	}

	public void setPeisongfeiyong(String peisongfeiyong) {
		this.peisongfeiyong = peisongfeiyong;
	}

	public String getYouhiuzhekou() {
		return youhiuzhekou;
	}

	public void setYouhiuzhekou(String youhiuzhekou) {
		this.youhiuzhekou = youhiuzhekou;
	}

	public String getXindou() {
		return xindou;
	}

	public void setXindou(String xindou) {
		this.xindou = xindou;
	}

	public String getYinfuzongjine() {
		return yinfuzongjine;
	}

	public void setYinfuzongjine(String yinfuzongjine) {
		this.yinfuzongjine = yinfuzongjine;
	}

	public String getEnnMemId() {
		return ennMemId;
	}

	public void setEnnMemId(String ennMemId) {
		this.ennMemId = ennMemId;
	}

}
