package com.xyyy.shop.model;

import java.util.List;

public class PayDetailDTO {
	private EnnOrder ennOrder;
	private List<EnnPayRecord> ennPayRecords;

	public EnnOrder getEnnOrder() {
		return ennOrder;
	}

	public void setEnnOrder(EnnOrder ennOrder) {
		this.ennOrder = ennOrder;
	}

	public List<EnnPayRecord> getEnnPayRecords() {
		return ennPayRecords;
	}

	public void setEnnPayRecords(List<EnnPayRecord> ennPayRecords) {
		this.ennPayRecords = ennPayRecords;
	}

}
