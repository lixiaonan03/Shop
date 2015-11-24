package com.xyyy.shop.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnPostRec {
    
    /**
      * 
      **/
	private Integer postRecId;
    /**
      * 订单配送信息ID
      **/
	private Integer orderDeliveId;
    /**
      * 快递员名称
      **/
	private String postMan;
    /**
      * 快递员手机号
      **/
	private String postPhone;
    /**
      * 快递状态
      **/
	private String postStatus;
    /**
      * 到达时间
      **/
	private java.sql.Timestamp postTime;
    /**
      * 到达站点
      **/
	private String postStation;
    /**
      * 实时温度
      **/
	private String postDegrees;
    /**
      * 跟踪记录描述:
            记录当前操作描述:
            xxxx年xx月xx日 xx时xx分xx秒 快递收件成功；
            
      **/
	private String postDesc;
    /**
      * 备注
      **/
	private String remark;
    /**
      * 创建人ID
      **/
	private Integer creUserId;
    /**
      * 创建人名称
      **/
	private String creUserName;
    /**
      * 创建人机构ID
      **/
	private Integer creOrgId;
    /**
      * 创建时间
      **/
	private java.sql.Timestamp creDate;
    /**
      * 修改人ID
      **/
	private Integer updUserId;
    /**
      * 修改人名称
      **/
	private String updUserName;
    /**
      * 修改人机构ID
      **/
	private Integer updOrgId;
    /**
      * 修改时间
      **/
	private java.sql.Timestamp updDate;
	  /**
     * 总件数
     **/
	private Integer totalNum;
   /**
     * 妥投件数
     **/
	private Integer postOk;
   /**
     * 异常件数
     **/
	private Integer postNo;

	public EnnPostRec() {
		super();
	}

	public Integer getPostRecId() {
		return this.postRecId;
	}

	public void setPostRecId(Integer postRecId) {
		this.postRecId = postRecId;
	}
	public Integer getOrderDeliveId() {
		return this.orderDeliveId;
	}

	public void setOrderDeliveId(Integer orderDeliveId) {
		this.orderDeliveId = orderDeliveId;
	}
	public String getPostMan() {
		return this.postMan;
	}

	public void setPostMan(String postMan) {
		this.postMan = postMan;
	}
	public String getPostPhone() {
		return this.postPhone;
	}

	public void setPostPhone(String postPhone) {
		this.postPhone = postPhone;
	}
	public String getPostStatus() {
		return this.postStatus;
	}

	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getPostTime() {
		return this.postTime;
	}

	public void setPostTime(java.sql.Timestamp postTime) {
		this.postTime = postTime;
	}
	public String getPostStation() {
		return this.postStation;
	}

	public void setPostStation(String postStation) {
		this.postStation = postStation;
	}
	public String getPostDegrees() {
		return this.postDegrees;
	}

	public void setPostDegrees(String postDegrees) {
		this.postDegrees = postDegrees;
	}
	public String getPostDesc() {
		return this.postDesc;
	}

	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getCreUserId() {
		return this.creUserId;
	}

	public void setCreUserId(Integer creUserId) {
		this.creUserId = creUserId;
	}
	public String getCreUserName() {
		return this.creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}
	public Integer getCreOrgId() {
		return this.creOrgId;
	}

	public void setCreOrgId(Integer creOrgId) {
		this.creOrgId = creOrgId;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getCreDate() {
		return this.creDate;
	}

	public void setCreDate(java.sql.Timestamp creDate) {
		this.creDate = creDate;
	}
	public Integer getUpdUserId() {
		return this.updUserId;
	}

	public void setUpdUserId(Integer updUserId) {
		this.updUserId = updUserId;
	}
	public String getUpdUserName() {
		return this.updUserName;
	}

	public void setUpdUserName(String updUserName) {
		this.updUserName = updUserName;
	}
	public Integer getUpdOrgId() {
		return this.updOrgId;
	}

	public void setUpdOrgId(Integer updOrgId) {
		this.updOrgId = updOrgId;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(java.sql.Timestamp updDate) {
		this.updDate = updDate;
	}
	
	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getPostOk() {
		return postOk;
	}

	public void setPostOk(Integer postOk) {
		this.postOk = postOk;
	}

	public Integer getPostNo() {
		return postNo;
	}

	public void setPostNo(Integer postNo) {
		this.postNo = postNo;
	}
}