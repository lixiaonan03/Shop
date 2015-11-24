package com.xyyy.shop.model;

import java.util.List;

public class OrderStatusDTO {
	private Integer membId;
	private List<String> orderStatuses;

	public Integer getMembId() {
		return membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}

	public List<String> getOrderStatuses() {
		return orderStatuses;
	}

	public void setOrderStatuses(List<String> orderStatuses) {
		this.orderStatuses = orderStatuses;
	}
}
