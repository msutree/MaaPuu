package com.maapuu.mereca.background.shop.bean;

/**
 * 会员订单bean
 * Created by Jia on 2018/4/26.
 */

public class MemberOrderBean {
    /**
     * member_id : 18
     * order_no : MB2018040714515033924
     * mk_status : 2
     * shop_name : 渼树光谷店
     * card_type : 1
     * card_name : 尊享会员
     * recharge_amount : 1000.00
     * member_no : CN2018040714521849452
     * pay_time : 2018-04-07 14:52:18
     * member_name : 宝宝
     * member_phone : 18666666666
     */

    private String member_id;
    private String order_no;
    private String mk_status;
    private String shop_name;
    private int card_type;
    private String card_name;
    private String recharge_amount;
    private String member_no;
    private String pay_time;
    private String member_name;
    private String member_phone;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getMk_status() {
        return mk_status;
    }

    public void setMk_status(String mk_status) {
        this.mk_status = mk_status;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getCard_type() {
        return card_type;
    }

    public void setCard_type(int card_type) {
        this.card_type = card_type;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(String recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    public String getMember_no() {
        return member_no;
    }

    public void setMember_no(String member_no) {
        this.member_no = member_no;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_phone() {
        return member_phone;
    }

    public void setMember_phone(String member_phone) {
        this.member_phone = member_phone;
    }
}
