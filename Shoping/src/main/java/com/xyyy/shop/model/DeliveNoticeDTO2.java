package com.xyyy.shop.model;


import java.util.Date;
import java.util.List;

public class DeliveNoticeDTO2 {
	private boolean frist;
	private Date startDate;
	private Date endDate;
	private String noticeId;
	private List<DeliveNoticeItemDTO> deliveNoticeItems;

	
	public boolean isFrist() {
		return frist;
	}

	public void setFrist(boolean frist) {
		this.frist = frist;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<DeliveNoticeItemDTO> getDeliveNoticeItems() {
		return deliveNoticeItems;
	}

	public void setDeliveNoticeItems(List<DeliveNoticeItemDTO> deliveNoticeItems) {
		this.deliveNoticeItems = deliveNoticeItems;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
}