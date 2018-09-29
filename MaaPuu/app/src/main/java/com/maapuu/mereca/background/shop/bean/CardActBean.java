package com.maapuu.mereca.background.shop.bean;

/**
 * 会员卡 营销活动
 * Created by Jia on 2018/4/16.
 */

public class CardActBean {

    /**
     * card_id : 1
     * card_img : http://beauty.whhxrc.com/./public/upload/temp/card_page.png
     * card_name : 使用
     * card_desc : 说明
     * card_type : 1
     */

    private String card_id;
    private String card_img;
    private String card_name;
    private String card_desc;
    private int card_type;

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_img() {
        return card_img;
    }

    public void setCard_img(String card_img) {
        this.card_img = card_img;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getCard_desc() {
        return card_desc;
    }

    public void setCard_desc(String card_desc) {
        this.card_desc = card_desc;
    }

    public int getCard_type() {
        return card_type;
    }

    public void setCard_type(int card_type) {
        this.card_type = card_type;
    }
}
