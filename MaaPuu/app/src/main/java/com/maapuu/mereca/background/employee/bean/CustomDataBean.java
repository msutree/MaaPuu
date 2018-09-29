package com.maapuu.mereca.background.employee.bean;

import com.maapuu.mereca.bean.ShopBean;

import java.util.List;

/**
 * 客户列表 我的店铺和工作狂里面共用
 * Created by Jia on 2018/4/26.
 */

public class CustomDataBean {
    private List<ShopBean> shop_list;
    private List<CustomBean> custom_data;

    public List<ShopBean> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<ShopBean> shop_list) {
        this.shop_list = shop_list;
    }

    public List<CustomBean> getCustom_data() {
        return custom_data;
    }

    public void setCustom_data(List<CustomBean> custom_data) {
        this.custom_data = custom_data;
    }
}
