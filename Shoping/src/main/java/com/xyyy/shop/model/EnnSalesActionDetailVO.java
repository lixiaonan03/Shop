package com.xyyy.shop.model;

import java.util.List;

public class EnnSalesActionDetailVO {
    private EnnSalesAction ennSalesAction;
    private List<EnnActionRuleGoodDTO> ennActionRuleGoodDTOs;

    public List<EnnActionRuleGoodDTO> getEnnActionRuleGoodDTOs() {
        return ennActionRuleGoodDTOs;
    }

    public void setEnnActionRuleGoodDTOs(List<EnnActionRuleGoodDTO> ennActionRuleGoodDTOs) {
        this.ennActionRuleGoodDTOs = ennActionRuleGoodDTOs;
    }

    public EnnSalesAction getEnnSalesAction() {
        return ennSalesAction;
    }

    public void setEnnSalesAction(EnnSalesAction ennSalesAction) {
        this.ennSalesAction = ennSalesAction;
    }
}
