package com.xyyy.shop.model;

import java.util.List;

/**
 * 移动端商品详情页展示的VO
 * 
 * @author wl
 *
 */
public class GoodInfoVO2 {
    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 详情轮播图-图片地址
     */
    private List<EnnGoodsImg> imgUrls;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品Code
     */
    private String goodCode;
    /**
     * 优惠价
     */
    private java.math.BigDecimal youhuijia;
    /**
     * 价格
     */
    private java.math.BigDecimal price;
    /**
     * 规格
     */
    private String guige;

    /**
     * 配送范围
     */
    private List<EnnSysArea> ennSysAreas;
    /**
     * 配送范围2
     */
    private String peisongfanwei;

    /**
     * 促销信息
     */
    private String cuxiaoxinxi;
    /**
     * 商品介绍图片
     */
    private String goodInfo;
    /**
     * 评价
     */
    private List<EnnGoodsEvalVO> ennGoodsEvals;

    /**
     * 详情页推荐商品
     */
    private List<GoodItemVO> ennGoodsIntros;
    /**
     * 总评价人数
     */
    private String totalEval;

    /**
     * 五星率
     */
    private String fivePec;
    /**
     * 商品状态
     */
    private String goodsState;
    /**
     * 促销活动
     */
    private List<EnnSalesAction> ennSalesActions;

    public List<EnnSalesAction> getEnnSalesActions() {
        return ennSalesActions;
    }

    public void setEnnSalesActions(List<EnnSalesAction> ennSalesActions) {
        this.ennSalesActions = ennSalesActions;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public List<EnnGoodsImg> getImgUrls() {
        return imgUrls;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setImgUrls(List<EnnGoodsImg> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.math.BigDecimal getYouhuijia() {
        return youhuijia;
    }

    public void setYouhuijia(java.math.BigDecimal youhuijia) {
        this.youhuijia = youhuijia;
    }

    public java.math.BigDecimal getPrice() {
        return price;
    }

    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    public List<EnnSysArea> getEnnSysAreas() {
        return ennSysAreas;
    }

    public void setEnnSysAreas(List<EnnSysArea> ennSysAreas) {
        this.ennSysAreas = ennSysAreas;
    }

    public String getCuxiaoxinxi() {
        return cuxiaoxinxi;
    }

    public void setCuxiaoxinxi(String cuxiaoxinxi) {
        this.cuxiaoxinxi = cuxiaoxinxi;
    }

    public String getGoodInfo() {
        return goodInfo;
    }

    public void setGoodInfo(String goodInfo) {
        this.goodInfo = goodInfo;
    }

    public List<EnnGoodsEvalVO> getEnnGoodsEvals() {
        return ennGoodsEvals;
    }

    public void setEnnGoodsEvals(List<EnnGoodsEvalVO> ennGoodsEvals) {
        this.ennGoodsEvals = ennGoodsEvals;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public List<GoodItemVO> getEnnGoodsIntros() {
        return ennGoodsIntros;
    }

    public void setEnnGoodsIntros(List<GoodItemVO> ennGoodsIntros) {
        this.ennGoodsIntros = ennGoodsIntros;
    }

    public String getTotalEval() {
        return totalEval;
    }

    public void setTotalEval(String totalEval) {
        this.totalEval = totalEval;
    }

    public String getFivePec() {
        return fivePec;
    }

    public void setFivePec(String fivePec) {
        this.fivePec = fivePec;
    }

    public String getPeisongfanwei() {
        return peisongfanwei;
    }

    public void setPeisongfanwei(String peisongfanwei) {
        this.peisongfanwei = peisongfanwei;
    }

}
