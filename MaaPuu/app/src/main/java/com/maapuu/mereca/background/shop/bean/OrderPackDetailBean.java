package com.maapuu.mereca.background.shop.bean;

import java.util.List;

/**
 * 套餐活动订单详情
 * Created by Jia on 2018/4/26.
 */

public class OrderPackDetailBean {

    /**
     * order_no : AC2018040401155568121
     * recharge_amount : 100.00
     * nick_name : 趣味儿童与青少年
     * avatar : http://beauty.whhxrc.com/./public/upload/appphotos/2018/03/29/889d0b8066c118a65392a83f93af2b78Bo7ojB.jpg
     * pay_time : 2018-04-06 01:01:01
     * pack_name : 5包一起买
     * items : [{"item_name":"时尚剪发","order_no":"PJ2018040601010171426"},{"item_name":"时尚短发","order_no":"PJ2018040601010176239"},{"item_name":"BOB头","order_no":"PJ2018040601010179546"}]
     */

    private String order_no;
    private String recharge_amount;
    private String nick_name;
    private String avatar;
    private String pay_time;
    private String pack_name;
    private List<ItemsBean> items;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(String recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPack_name() {
        return pack_name;
    }

    public void setPack_name(String pack_name) {
        this.pack_name = pack_name;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * item_name : 时尚剪发
         * order_no : PJ2018040601010171426
         */

        private String item_name;
        private String order_no;

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }
    }
}
