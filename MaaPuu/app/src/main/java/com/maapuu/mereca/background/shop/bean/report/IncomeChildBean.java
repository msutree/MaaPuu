package com.maapuu.mereca.background.shop.bean.report;

import java.util.List;

/**
 * Created by dell on 2018/5/24.
 */

public class IncomeChildBean {
    private List<IncomePointBean> catalog;
    private List<IncomeStaffBean> staff;

    public List<IncomePointBean> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<IncomePointBean> catalog) {
        this.catalog = catalog;
    }

    public List<IncomeStaffBean> getStaff() {
        return staff;
    }

    public void setStaff(List<IncomeStaffBean> staff) {
        this.staff = staff;
    }
}
