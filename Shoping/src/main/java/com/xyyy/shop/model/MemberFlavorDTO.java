package com.xyyy.shop.model;

import java.util.List;

public class MemberFlavorDTO {
	private Integer membId;
	private List<String> flavorTypes;

	public Integer getMembId() {
		return membId;
	}

	public void setMembId(Integer membId) {
		this.membId = membId;
	}

	public List<String> getFlavorTypes() {
		return flavorTypes;
	}

	public void setFlavorTypes(List<String> flavorTypes) {
		this.flavorTypes = flavorTypes;
	}

}
