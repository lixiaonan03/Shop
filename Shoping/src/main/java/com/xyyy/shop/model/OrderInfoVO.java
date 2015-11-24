package com.xyyy.shop.model;

import java.util.List;
/**
 * 去支付使用
 * @author Administrator
 *
 */
public class OrderInfoVO {
	// 收货信息
	private EnnMemberReceipt emr;
	// 支付及配送
	// 发票信息
	// 备注
	private EnnOrder eo;
	// 折扣信息
	// 新豆
	// 商品信息
	private List<GoodVO> goods;
	//商品价格
	private String goodsPrice;
	//运费
	private String yunfei;
	//优惠
	private String youhui;
	//新豆
	private String xindou;

	public EnnMemberReceipt getEmr() {
		return emr;
	}

	public void setEmr(EnnMemberReceipt emr) {
		this.emr = emr;
	}

	public EnnOrder getEo() {
		return eo;
	}

	public void setEo(EnnOrder eo) {
		this.eo = eo;
	}

	public List<GoodVO> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodVO> goods) {
		this.goods = goods;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
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

	public String getXindou() {
		return xindou;
	}

	public void setXindou(String xindou) {
		this.xindou = xindou;
	}

}
