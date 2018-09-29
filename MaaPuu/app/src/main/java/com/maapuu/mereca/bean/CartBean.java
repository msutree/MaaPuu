package com.maapuu.mereca.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2018/3/9.
 */

public class CartBean implements Serializable {
    private String shop_id;
    private String shop_name;
    private boolean bool;
    private List<CartGoodsBean> detail;

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

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public List<CartGoodsBean> getDetail() {
        return detail;
    }

    public void setDetail(List<CartGoodsBean> detail) {
        this.detail = detail;
    }
}
