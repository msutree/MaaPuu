package com.maapuu.mereca.background.shop.bean.shopreport;

import java.util.List;

/**
 * Created by dell on 2018/5/25.
 */

public class CardBean {
    private List<CardPointBean> list_income;
    private String total_income;

    public List<CardPointBean> getList_income() {
        return list_income;
    }

    public void setList_income(List<CardPointBean> list_income) {
        this.list_income = list_income;
    }

    public String getTotal_income() {
        return total_income;
    }

    public void setTotal_income(String total_income) {
        this.total_income = total_income;
    }
}
