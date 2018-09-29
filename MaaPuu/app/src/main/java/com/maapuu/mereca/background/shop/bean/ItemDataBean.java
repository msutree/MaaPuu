package com.maapuu.mereca.background.shop.bean;

import java.io.Serializable;

/**
 *
 * Created by Jia on 2018/4/17.
 */

public class ItemDataBean implements Serializable {
    /**
     * item_id : 1
     * item_name : 时尚剪发
     */

    private String item_id;
    private String item_name;

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
}
