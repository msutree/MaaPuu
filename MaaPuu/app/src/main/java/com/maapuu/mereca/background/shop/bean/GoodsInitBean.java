package com.maapuu.mereca.background.shop.bean;

import com.maapuu.mereca.bean.ShopBean;

import java.util.List;

/**
 * 商品管理
 * Created by Jia on 2018/4/14.
 */

public class GoodsInitBean {

    private List<ShopBean> shop_list;
    private List<GoodsBean> item_list;

    public List<ShopBean> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<ShopBean> shop_list) {
        this.shop_list = shop_list;
    }

    public List<GoodsBean> getItem_list() {
        return item_list;
    }

    public void setItem_list(List<GoodsBean> item_list) {
        this.item_list = item_list;
    }
}
