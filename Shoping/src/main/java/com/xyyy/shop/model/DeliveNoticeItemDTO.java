package com.xyyy.shop.model;

import java.util.List;

public class DeliveNoticeItemDTO {
    private EnnDishes parentDish;
    private List<Membfavor> membfavors;
	public EnnDishes getParentDish() {
		return parentDish;
	}
	public void setParentDish(EnnDishes parentDish) {
		this.parentDish = parentDish;
	}
	public List<Membfavor> getMembfavors() {
		return membfavors;
	}
	public void setMembfavors(List<Membfavor> membfavors) {
		this.membfavors = membfavors;
	}
    
}
