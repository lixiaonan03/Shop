package com.xyyy.shop.model;

import java.math.BigDecimal;
import java.util.List;

public class ShowGoodsDTO {
	// 收货地址id
	private Integer receId;
	// 区域(随会员卡一起配送)
	private Integer zone;
	//订单总金额
	private BigDecimal totalPrice;
	//运费
	private BigDecimal yunfei;
	// 收货地址id
	private List<ShowGoodsItemsDTO> showGoodsItemsDTOs;
    
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getYunfei() {
		return yunfei;
	}

	public void setYunfei(BigDecimal yunfei) {
		this.yunfei = yunfei;
	}

	public Integer getReceId() {
		return receId;
	}
    
	public Integer getZone() {
		return zone;
	}

	public void setZone(Integer zone) {
		this.zone = zone;
	}

	public void setReceId(Integer receId) {
		this.receId = receId;
	}

	public List<ShowGoodsItemsDTO> getShowGoodsItemsDTOs() {
		return showGoodsItemsDTOs;
	}

	public void setShowGoodsItemsDTOs(List<ShowGoodsItemsDTO> showGoodsItemsDTOs) {
		this.showGoodsItemsDTOs = showGoodsItemsDTOs;
	}

}
