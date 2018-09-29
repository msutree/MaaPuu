package com.maapuu.mereca.background.shop.bean;

/**
 * 消费记录 会员管理
 * Created by Jia on 2018/4/18.
 */

public class ConsumeBean {

    /**
     * oid : 3
     * item_name : 时尚剪发
     * pay_amount : 1.00
     * create_time_text : 2018-03-09 22:33
     * shop_name : 渼树光谷店
     */

    private String oid;
    private String item_name;
    private String pay_amount;
    private String create_time_text;
    private String shop_name;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getCreate_time_text() {
        return create_time_text;
    }

    public void setCreate_time_text(String create_time_text) {
        this.create_time_text = create_time_text;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}
