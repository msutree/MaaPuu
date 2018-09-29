package com.maapuu.mereca.background.shop.bean;

/**
 * 红包
 * Created by Jia on 2018/4/26.
 */

public class RedBean {

    /**
     * red_id : 199
     * nick_name : 趣味儿童与青少年
     * avatar : http://beauty.whhxrc.com/./public/upload/appphotos/2018/03/29/889d0b8066c118a65392a83f93af2b78Bo7ojB.jpg
     * valid_end : 2018-04-29 00:00:00
     * order_no :
     * red_amount : 50.00
     * fullcut_amount : 100
     * status : 1
     * shop_name : 美沭中南店
     */

    private String red_id;
    private String nick_name;
    private String avatar;
    private String valid_end;
    private String order_no;
    private String red_amount;
    private String fullcut_amount;
    private String status; //status红包状态1未使用；2已使用；3已过期
    private String shop_name;

    public String getRed_id() {
        return red_id;
    }

    public void setRed_id(String red_id) {
        this.red_id = red_id;
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

    public String getValid_end() {
        return valid_end;
    }

    public void setValid_end(String valid_end) {
        this.valid_end = valid_end;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getRed_amount() {
        return red_amount;
    }

    public void setRed_amount(String red_amount) {
        this.red_amount = red_amount;
    }

    public String getFullcut_amount() {
        return fullcut_amount;
    }

    public void setFullcut_amount(String fullcut_amount) {
        this.fullcut_amount = fullcut_amount;
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
}
