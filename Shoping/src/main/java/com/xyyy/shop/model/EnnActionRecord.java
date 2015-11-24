package com.xyyy.shop.model;


import com.fasterxml.jackson.annotation.JsonFormat;

public class EnnActionRecord {
    
    /**
      * 操作记录ID
      **/
	private Integer actionId;
    /**
      * 01-订单管理
            02-会员卡管理
            
      **/
	private String bussType;
    /**
      * 操作表名称
      **/
	private String actionTableName;
    /**
      * 操作表主键ID
      **/
	private Integer actionKeyId;
    /**
      * 操作类型
            01-新增
            02-删除
            03-修改
            04-取消
            05-启用
            06-暂停
            07-绑定
            08-修改备注
      **/
	private String actionType;
    /**
      * 操作名称
      **/
	private String actionName;
    /**
      * 操作时间
      **/
	private java.sql.Timestamp actionTime;
    /**
      * 操作结果
            0-失败
            1-成功
      **/
	private String actionResult;
    /**
      * 处理前结果
      **/
	private String actionBefore;
    /**
      * 操作处理过程
      **/
	private String actionProcess;
    /**
      * 处理后结果
      **/
	private String actionAfter;
    /**
      * 操作人ID
      **/
	private Integer operUserId;
    /**
      * 操作人名称
      **/
	private String operUserName;
    /**
      * REMARK
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
      * 修改时间
      **/
	private java.sql.Timestamp updDate;


	public EnnActionRecord() {
		super();
	}

	public Integer getActionId() {
		return this.actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public String getBussType() {
		return this.bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}
	public String getActionTableName() {
		return this.actionTableName;
	}

	public void setActionTableName(String actionTableName) {
		this.actionTableName = actionTableName;
	}
	public Integer getActionKeyId() {
		return this.actionKeyId;
	}

	public void setActionKeyId(Integer actionKeyId) {
		this.actionKeyId = actionKeyId;
	}
	public String getActionType() {
		return this.actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getActionTime() {
		return this.actionTime;
	}

	public void setActionTime(java.sql.Timestamp actionTime) {
		this.actionTime = actionTime;
	}
	public String getActionResult() {
		return this.actionResult;
	}

	public void setActionResult(String actionResult) {
		this.actionResult = actionResult;
	}
	public String getActionBefore() {
		return this.actionBefore;
	}

	public void setActionBefore(String actionBefore) {
		this.actionBefore = actionBefore;
	}
	public String getActionProcess() {
		return this.actionProcess;
	}

	public void setActionProcess(String actionProcess) {
		this.actionProcess = actionProcess;
	}
	public String getActionAfter() {
		return this.actionAfter;
	}

	public void setActionAfter(String actionAfter) {
		this.actionAfter = actionAfter;
	}
	public Integer getOperUserId() {
		return this.operUserId;
	}

	public void setOperUserId(Integer operUserId) {
		this.operUserId = operUserId;
	}
	public String getOperUserName() {
		return this.operUserName;
	}

	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public java.sql.Timestamp getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(java.sql.Timestamp updDate) {
		this.updDate = updDate;
	}
}