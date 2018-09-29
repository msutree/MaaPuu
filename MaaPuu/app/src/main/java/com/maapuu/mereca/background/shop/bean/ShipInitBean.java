package com.maapuu.mereca.background.shop.bean;

import java.util.List;

/**
 * 未发货订单 初始信息
 * Created by Jia on 2018/4/25.
 */

public class ShipInitBean {
    private String order_no;
    private List<LogisticsCompanyBean> company;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public List<LogisticsCompanyBean> getCompany() {
        return company;
    }

    public void setCompany(List<LogisticsCompanyBean> company) {
        this.company = company;
    }
}
