package com.maapuu.mereca.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * Created by dell on 2018/3/24.
 */

public class ShopBean implements Serializable,IPickerViewData{
    private String shop_id;
    private String shop_name;
    private String shop_logo;
    private String shop_cover;
    private String evl_level;
    private String address_detail;
    private String distance;

    private boolean isChecked;//本地标记是否选中

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public String getShop_cover() {
        return shop_cover;
    }

    public void setShop_cover(String shop_cover) {
        this.shop_cover = shop_cover;
    }

    public String getEvl_level() {
        return evl_level;
    }

    public void setEvl_level(String evl_level) {
        this.evl_level = evl_level;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String getPickerViewText() {
        return shop_name;
    }

}
