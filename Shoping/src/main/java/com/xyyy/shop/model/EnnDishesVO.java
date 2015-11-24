package com.xyyy.shop.model;


public class EnnDishesVO {
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

	private EnnDishes ennDishes;
	private EnnDishes parent_ennDishes;
	private int  flag;//01 喜欢 02忌口 3无所谓
	private String sortLetters;  //蔬菜名称拼音的首字母
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
    
}
