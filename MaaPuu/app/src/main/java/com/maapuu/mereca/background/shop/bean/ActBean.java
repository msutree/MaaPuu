package com.maapuu.mereca.background.shop.bean;

import com.maapuu.mereca.bean.ShopBean;

import java.util.List;

/**
 * 营销活动
 * Created by Jia on 2018/4/16.
 */

public class ActBean {
    private List<ShopBean> shop_list; // 商铺列表
    private List<CardActBean> card_act_list;// 会员卡活动 套餐活动 红包
    private List<PackActBean> pack_act_list;
    private List<RedActBean> red_act_list;

    public List<ShopBean> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<ShopBean> shop_list) {
        this.shop_list = shop_list;
    }

    public List<CardActBean> getCard_act_list() {
        return card_act_list;
    }

    public void setCard_act_list(List<CardActBean> card_act_list) {
        this.card_act_list = card_act_list;
    }

    public List<PackActBean> getPack_act_list() {
        return pack_act_list;
    }

    public void setPack_act_list(List<PackActBean> pack_act_list) {
        this.pack_act_list = pack_act_list;
    }

    public List<RedActBean> getRed_act_list() {
        return red_act_list;
    }

    public void setRed_act_list(List<RedActBean> red_act_list) {
        this.red_act_list = red_act_list;
    }
}
