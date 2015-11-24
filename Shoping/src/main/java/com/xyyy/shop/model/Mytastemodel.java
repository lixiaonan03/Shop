package com.xyyy.shop.model;
/**
 * 自己模拟的我的口味使用的实体
 * @author lxn
 *
 */
public class Mytastemodel {
	private int  flag;//0 未选中 1 选中
	private String sortLetters;  //蔬菜名称拼音的首字母
	private String name;
	private String  type;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Mytastemodel(int flag, String sortLetters, String name, String type) {
		super();
		this.flag = flag;
		this.sortLetters = sortLetters;
		this.name = name;
		this.type = type;
	}
	public Mytastemodel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
