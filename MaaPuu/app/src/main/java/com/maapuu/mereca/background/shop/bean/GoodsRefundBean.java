package com.maapuu.mereca.background.shop.bean;

/**
 * 商品订单 退款
 * Created by Jia on 2018/4/25.
 */

public class GoodsRefundBean {

    /**
     * order_no : CT2018040915174487363
     * refund_status : 1
     * refund_no : 2018042516065798738RF
     * refund_amount : 700.00
     * refund_type : 仅退款
     * apply_time : 2018-04-25 16:06:57
     * nick_name : 趣味儿童与青少年
     * phone : 15972093060
     */

    private String order_no;
    private String item_name;
    private int refund_status;
    private String refund_no;
    private String refund_amount;
    private String refund_type;
    private String apply_time;
    private String nick_name;
    private String phone;
    private String refund_id;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public String getRefund_no() {
        return refund_no;
    }

    public void setRefund_no(String refund_no) {
        this.refund_no = refund_no;
    }

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(String refund_type) {
        this.refund_type = refund_type;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
