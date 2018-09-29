package com.maapuu.mereca.bean;

import java.io.Serializable;

/**
 * Created by dell on 2018/3/9.
 */

public class CartGoodsBean implements Serializable {
    private String shop_id;
    private String item_id;
    private String shop_name;
    private String item_shop_id;
    private String item_name;
    private String price;
    private String item_img;
    private String item_specification;
    private int is_check;
    private int num;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public String getItem_specification() {
        return item_specification;
    }

    public void setItem_specification(String item_specification) {
        this.item_specification = item_specification;
    }

    public int getIs_check() {
        return is_check;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
