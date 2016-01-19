package com.xyyy.shop.model;

public class EnnSalesAction {
    
    /**
      * 
      **/
	private Integer actionId;
    /**
      * 
      **/
	private String actionName;
    /**
      * 促销简名:
            显示给微商城商品使用： 
            买二赠一、满100增XX等
      **/
	private String actionNameSimp;
    /**
      * 促销类型
            01-满减
            02-满赠
            03-优惠券
            04-换购
      **/
	private String salesType;
    /**
      * 是否互斥
            1-是
            0-否
            默认是
      **/
	private String isMutex;
    /**
      * 00-草稿
            01-待审核
            02-审核通过
            03-审核不通过
            04-退回重新审核
            
            
      **/
	private String auditState;
    /**
      * 活动状态
            01-未开始
            02-进行中
            03-已结束
            
      **/
	private String actionState;
    /**
      * 活动开始时间
      **/
	private java.sql.Timestamp startTime;
    /**
      * 活动结束时间
      **/
	private java.sql.Timestamp endTime;
    /**
      * 会员等级要求
      **/
	private String membLevel;
    /**
      * 参与方式
            01-全部商品
            02-部分商品
      **/
	private String joinStyle;
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
    /**
      * 活动优先级（数值越大，优先级越高）
      **/
	private Integer actionPriority;


	public EnnSalesAction() {
		super();
	}

	public Integer getActionId() {
		return this.actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionNameSimp() {
		return this.actionNameSimp;
	}

	public void setActionNameSimp(String actionNameSimp) {
		this.actionNameSimp = actionNameSimp;
	}
	public String getSalesType() {
		return this.salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	public String getIsMutex() {
		return this.isMutex;
	}

	public void setIsMutex(String isMutex) {
		this.isMutex = isMutex;
	}
	public String getAuditState() {
		return this.auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getActionState() {
		return this.actionState;
	}

	public void setActionState(String actionState) {
		this.actionState = actionState;
	}
	public java.sql.Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(java.sql.Timestamp startTime) {
		this.startTime = startTime;
	}
	public java.sql.Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(java.sql.Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getMembLevel() {
		return this.membLevel;
	}

	public void setMembLevel(String membLevel) {
		this.membLevel = membLevel;
	}
	public String getJoinStyle() {
		return this.joinStyle;
	}

	public void setJoinStyle(String joinStyle) {
		this.joinStyle = joinStyle;
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
	public java.sql.Timestamp getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(java.sql.Timestamp updDate) {
		this.updDate = updDate;
	}
	public Integer getActionPriority() {
		return this.actionPriority;
	}

	public void setActionPriority(Integer actionPriority) {
		this.actionPriority = actionPriority;
	}
}