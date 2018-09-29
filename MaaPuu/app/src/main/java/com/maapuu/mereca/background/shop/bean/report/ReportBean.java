package com.maapuu.mereca.background.shop.bean.report;

/**
 * Created by dell on 2018/5/24.
 */

public class ReportBean {
    private MonthBean month;
    private IncomeBean income;
    private AchieveBean achieve;
    private CustomBean custom;

    public MonthBean getMonth() {
        return month;
    }

    public void setMonth(MonthBean month) {
        this.month = month;
    }

    public IncomeBean getIncome() {
        return income;
    }

    public void setIncome(IncomeBean income) {
        this.income = income;
    }

    public AchieveBean getAchieve() {
        return achieve;
    }

    public void setAchieve(AchieveBean achieve) {
        this.achieve = achieve;
    }

    public CustomBean getCustom() {
        return custom;
    }

    public void setCustom(CustomBean custom) {
        this.custom = custom;
    }
}
