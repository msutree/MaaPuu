package com.maapuu.mereca.background.shop.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/4/12.
 */

public class ItemBean implements Serializable {
    private String item_shop_id;
    private String item_id;
    private String item_name;
    private String is_sel;
    private boolean bool;

    public String getItem_shop_id() {
        return item_shop_id;
    }

    public void setItem_shop_id(String item_shop_id) {
        this.item_shop_id = item_shop_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getIs_sel() {
        return is_sel;
    }

    public void setIs_sel(String is_sel) {
        this.is_sel = is_sel;
    }
}
