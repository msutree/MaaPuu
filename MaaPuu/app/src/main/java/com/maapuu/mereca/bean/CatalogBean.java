package com.maapuu.mereca.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2018/3/24.
 * 店铺项目、商品分类
 */

public class CatalogBean implements Serializable {
    private String catalog_id;
    private String catalog_img;
    private String catalog_name;
    private String price_region;
    private String item_num;
    private boolean bool;
    private List<GoodsBean> items;

    public String getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(String catalog_id) {
        this.catalog_id = catalog_id;
    }

    public String getCatalog_img() {
        return catalog_img;
    }

    public void setCatalog_img(String catalog_img) {
        this.catalog_img = catalog_img;
    }

    public String getCatalog_name() {
        return catalog_name;
    }

    public void setCatalog_name(String catalog_name) {
        this.catalog_name = catalog_name;
    }

    public String getPrice_region() {
        return price_region;
    }

    public void setPrice_region(String price_region) {
        this.price_region = price_region;
    }

    public String getItem_num() {
        return item_num;
    }

    public void setItem_num(String item_num) {
        this.item_num = item_num;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public List<GoodsBean> getItems() {
        return items;
    }

    public void setItems(List<GoodsBean> items) {
        this.items = items;
    }
}
