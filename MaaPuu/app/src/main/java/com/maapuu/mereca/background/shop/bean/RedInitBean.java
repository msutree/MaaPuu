package com.maapuu.mereca.background.shop.bean;

import com.maapuu.mereca.bean.ShopBean;

import java.util.List;

/**
 * 套餐活动订单
 * Created by Jia on 2018/4/26.
 */

public class RedInitBean {

    private List<RedBean> red_data;
    private List<ShopBean> shop_list;

    public List<RedBean> getRed_data() {
        return red_data;
    }

    public void setRed_data(List<RedBean> red_data) {
        this.red_data = red_data;
    }

    public List<ShopBean> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<ShopBean> shop_list) {
        this.shop_list = shop_list;
    }

}
