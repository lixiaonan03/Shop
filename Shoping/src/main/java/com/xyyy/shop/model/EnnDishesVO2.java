package com.xyyy.shop.model;



public class EnnDishesVO2 {
	private String flavorType;
	private EnnDishes ennDishes;
	private EnnDishes parent_ennDishes;
	private int  flag;//0 未选中 1 选中
	private String sortLetters;  //蔬菜名称拼音的首字母
	
	public String getFlavorType() {
		return flavorType;
	}

	public void setFlavorType(String flavorType) {
		this.flavorType = flavorType;
	}

	public EnnDishes getEnnDishes() {
		return ennDishes;
	}

	public void setEnnDishes(EnnDishes ennDishes) {
		this.ennDishes = ennDishes;
	}

	public EnnDishes getParent_ennDishes() {
		return parent_ennDishes;
	}

	public void setParent_ennDishes(EnnDishes parent_ennDishes) {
		this.parent_ennDishes = parent_ennDishes;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
   
}
