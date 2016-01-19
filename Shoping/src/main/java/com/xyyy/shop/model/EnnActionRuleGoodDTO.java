package com.xyyy.shop.model;

import java.util.List;

public class EnnActionRuleGoodDTO {
    private EnnActionRules ennActionRules;
    private List<EnnActionGift> EnnActionGifts;

    public EnnActionRules getEnnActionRules() {
        return ennActionRules;
    }

    public void setEnnActionRules(EnnActionRules ennActionRules) {
        this.ennActionRules = ennActionRules;
    }

    public List<EnnActionGift> getEnnActionGifts() {
        return EnnActionGifts;
    }

    public void setEnnActionGifts(List<EnnActionGift> ennActionGifts) {
        EnnActionGifts = ennActionGifts;
    }
}
