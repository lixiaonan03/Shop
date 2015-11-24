package com.xyyy.shop.model;

import java.util.List;

public class DeliveListDetailDTO {
	/**
	 * 商品信息
	 */
	private List<DeliveDetailItemDTO> deliveDetailItemDTOs;
	/**
	 * 再买点 商品信息
	 */
	private List<DeliveDetailItemDTO> buyTooDeliveDetailItemDTOs;
	
	/**
	 * 物流信息
	 */
	private EnnPostRec ennPostRec;
	
	public List<DeliveDetailItemDTO> getBuyTooDeliveDetailItemDTOs() {
		return buyTooDeliveDetailItemDTOs;
	}

	public void setBuyTooDeliveDetailItemDTOs(
			List<DeliveDetailItemDTO> buyTooDeliveDetailItemDTOs) {
		this.buyTooDeliveDetailItemDTOs = buyTooDeliveDetailItemDTOs;
	}

	public EnnPostRec getEnnPostRec() {
		return ennPostRec;
	}

	public void setEnnPostRec(EnnPostRec ennPostRec) {
		this.ennPostRec = ennPostRec;
	}

	public List<DeliveDetailItemDTO> getDeliveDetailItemDTOs() {
		return deliveDetailItemDTOs;
	}

	public void setDeliveDetailItemDTOs(
			List<DeliveDetailItemDTO> deliveDetailItemDTOs) {
		this.deliveDetailItemDTOs = deliveDetailItemDTOs;
	}

}
