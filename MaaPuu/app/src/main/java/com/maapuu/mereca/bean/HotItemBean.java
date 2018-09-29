package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/3/24.
 */

public class HotItemBean implements Serializable {
    private String shop_hot_item_id;
    private String item_id;
    private String hot_item_img;
    private String item_name;
    private String price;
    private String market_price;
    private String sale_num;

    public String getShop_hot_item_id() {
        return shop_hot_item_id;
    }

    public void setShop_hot_item_id(String shop_hot_item_id) {
        this.shop_hot_item_id = shop_hot_item_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getHot_item_img() {
        return hot_item_img;
    }

    public void setHot_item_img(String hot_item_img) {
        this.hot_item_img = hot_item_img;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSale_num() {
        return sale_num;
    }

    public void setSale_num(String sale_num) {
        this.sale_num = sale_num;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }
}
