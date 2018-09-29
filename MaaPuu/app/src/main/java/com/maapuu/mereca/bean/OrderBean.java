package com.maapuu.mereca.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2018/4/16.
 */

public class OrderBean implements Serializable {
    private String oid;
    private String order_no;
    private String status;
    private String shop_name;
    private String item_num;
    private String pay_amount;
    private String is_delete;
    private String is_pay;
    private String is_evl;
    private String order_title;
    private int logistics_type;
    private List<OrderChildBean> items;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getItem_num() {
        return item_num;
    }

    public void setItem_num(String item_num) {
        this.item_num = item_num;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }

    public String getIs_evl() {
        return is_evl;
    }

    public void setIs_evl(String is_evl) {
        this.is_evl = is_evl;
    }

    public String getOrder_title() {
        return order_title;
    }

    public void setOrder_title(String order_title) {
        this.order_title = order_title;
    }

    public List<OrderChildBean> getItems() {
        return items;
    }

    public void setItems(List<OrderChildBean> items) {
        this.items = items;
    }

    public int getLogistics_type() {
        return logistics_type;
    }

    public void setLogistics_type(int logistics_type) {
        this.logistics_type = logistics_type;
    }
}
