package com.maapuu.mereca.background.shop.bean.shopreport;

/**
 * Created by dell on 2018/5/25.
 */

public class ShopReportBean {
    private BusinessBean business_total;
    private ProjectBean project_income;
    private CommodityBean commodity_income;
    private CardBean card_income;
    private CommonBean common_amount;
    private CustomBean custom_project;
    private String custome_commodity_num;
    private String custome_member_num;
    private String custome_action_num;
    private String custome_action_amount;

    public BusinessBean getBusiness_total() {
        return business_total;
    }

    public void setBusiness_total(BusinessBean business_total) {
        this.business_total = business_total;
    }

    public ProjectBean getProject_income() {
        return project_income;
    }

    public void setProject_income(ProjectBean project_income) {
        this.project_income = project_income;
    }

    public CommodityBean getCommodity_income() {
        return commodity_income;
    }

    public void setCommodity_income(CommodityBean commodity_income) {
        this.commodity_income = commodity_income;
    }

    public CardBean getCard_income() {
        return card_income;
    }

    public void setCard_income(CardBean card_income) {
        this.card_income = card_income;
    }

    public CommonBean getCommon_amount() {
        return common_amount;
    }

    public void setCommon_amount(CommonBean common_amount) {
        this.common_amount = common_amount;
    }

    public CustomBean getCustom_project() {
        return custom_project;
    }

    public void setCustom_project(CustomBean custom_project) {
        this.custom_project = custom_project;
    }

    public String getCustome_commodity_num() {
        return custome_commodity_num;
    }

    public void setCustome_commodity_num(String custome_commodity_num) {
        this.custome_commodity_num = custome_commodity_num;
    }

    public String getCustome_member_num() {
        return custome_member_num;
    }

    public void setCustome_member_num(String custome_member_num) {
        this.custome_member_num = custome_member_num;
    }

    public String getCustome_action_num() {
        return custome_action_num;
    }

    public void setCustome_action_num(String custome_action_num) {
        this.custome_action_num = custome_action_num;
    }

    public String getCustome_action_amount() {
        return custome_action_amount;
    }

    public void setCustome_action_amount(String custome_action_amount) {
        this.custome_action_amount = custome_action_amount;
    }
}
