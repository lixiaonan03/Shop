package com.xyyy.shop.model;



/**
 * 完善资料dto
 * 
 * @Title
 * @Description
 * @Author wl
 * @Since 2015年12月15日
 * @Version 1.1.0
 */
public class PerfectDataDTO {
    private EnnMember ennMember;
    private EnnSmsCode ennSmsCode;
    private EnnMemberReceipt ennMemberReceipt;

    public EnnMember getEnnMember() {
        return ennMember;
    }

    public void setEnnMember(EnnMember ennMember) {
        this.ennMember = ennMember;
    }

    public EnnSmsCode getEnnSmsCode() {
        return ennSmsCode;
    }

    public void setEnnSmsCode(EnnSmsCode ennSmsCode) {
        this.ennSmsCode = ennSmsCode;
    }

    public EnnMemberReceipt getEnnMemberReceipt() {
        return ennMemberReceipt;
    }

    public void setEnnMemberReceipt(EnnMemberReceipt ennMemberReceipt) {
        this.ennMemberReceipt = ennMemberReceipt;
    }
}
