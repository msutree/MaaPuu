package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/3/24.
 */

public class PromotionBean implements Serializable {
    private String item_id;
    private String item_name;
    private String sale_num;
    private String price;
    private String market_price;
    private String diff_seconds;
    private String promotion_price;
    private String item_img;
    private int catalog_type;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getSale_num() {
        return sale_num;
    }

    public void setSale_num(String sale_num) {
        this.sale_num = sale_num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getDiff_seconds() {
        return diff_seconds;
    }

    public void setDiff_seconds(String diff_seconds) {
        this.diff_seconds = diff_seconds;
    }

    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public int getCatalog_type() {
        return catalog_type;
    }

    public void setCatalog_type(int catalog_type) {
        this.catalog_type = catalog_type;
    }

    public String getPromotion_price() {
        return promotion_price;
    }

    public void setPromotion_price(String promotion_price) {
        this.promotion_price = promotion_price;
    }
}
