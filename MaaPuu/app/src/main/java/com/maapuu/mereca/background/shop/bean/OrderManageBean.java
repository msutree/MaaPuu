package com.maapuu.mereca.background.shop.bean;

import com.maapuu.mereca.bean.ShopBean;

import java.util.List;

/** 订单管理
 * Created by Jia on 2018/4/20.
 */

public class OrderManageBean {
    private List<ShopBean> shop_list;
    private List<OrderBean> order_list;//项目订单list
    private List<OrderBean> commodity;//商品订单list

    public List<ShopBean> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<ShopBean> shop_list) {
        this.shop_list = shop_list;
    }

    public List<OrderBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderBean> order_list) {
        this.order_list = order_list;
    }

    public List<OrderBean> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<OrderBean> commodity) {
        this.commodity = commodity;
    }
}
