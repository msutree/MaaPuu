package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * Created by dell on 2016/12/5.
 */
public class BannerBean implements Serializable{
    private String shop_adv_id;
    private String adv_img;
    private int adv_type;
    private String adv_value;
    private String adv_text;

    public String getShop_adv_id() {
        return shop_adv_id;
    }

    public void setShop_adv_id(String shop_adv_id) {
        this.shop_adv_id = shop_adv_id;
    }

    public String getAdv_img() {
        return adv_img;
    }

    public void setAdv_img(String adv_img) {
        this.adv_img = adv_img;
    }

    public int getAdv_type() {
        return adv_type;
    }

    public void setAdv_type(int adv_type) {
        this.adv_type = adv_type;
    }

    public String getAdv_value() {
        return adv_value;
    }

    public void setAdv_value(String adv_value) {
        this.adv_value = adv_value;
    }

    public String getAdv_text() {
        return adv_text;
    }

    public void setAdv_text(String adv_text) {
        this.adv_text = adv_text;
    }
}
