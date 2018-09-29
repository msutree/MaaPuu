package com.maapuu.mereca.background.shop.bean.report;

import java.util.List;

/**
 * Created by dell on 2018/5/24.
 */

public class AchieveChildBean {
    private List<AchievePointBean> data;
    private String ld_total_amount;
    private String xj_total_amount;

    public List<AchievePointBean> getData() {
        return data;
    }

    public void setData(List<AchievePointBean> data) {
        this.data = data;
    }

    public String getLd_total_amount() {
        return ld_total_amount;
    }

    public void setLd_total_amount(String ld_total_amount) {
        this.ld_total_amount = ld_total_amount;
    }

    public String getXj_total_amount() {
        return xj_total_amount;
    }

    public void setXj_total_amount(String xj_total_amount) {
        this.xj_total_amount = xj_total_amount;
    }
}
