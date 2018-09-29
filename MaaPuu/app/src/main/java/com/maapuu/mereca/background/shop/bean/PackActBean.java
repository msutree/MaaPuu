package com.maapuu.mereca.background.shop.bean;

/**
 * 套餐活动
 * Created by Jia on 2018/4/16.
 */

public class PackActBean {

    /**
     * pack_id : 21
     * pack_name : 1热1212
     */

    private String pack_id;
    private String pack_name;
    private String is_valid;

    public String getPack_id() {
        return pack_id;
    }

    public void setPack_id(String pack_id) {
        this.pack_id = pack_id;
    }

    public String getPack_name() {
        return pack_name;
    }

    public void setPack_name(String pack_name) {
        this.pack_name = pack_name;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }
}
