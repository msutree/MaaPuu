package com.maapuu.mereca.background.shop.bean;

import com.maapuu.mereca.bean.ShopBean;

import java.util.List;

/**
 * 会员订单
 * Created by Jia on 2018/4/26.
 */

public class MemberOrderInitBean {

    private List<MemberOrderBean> member_order;
    private List<ShopBean> shop_list;

    public List<MemberOrderBean> getMember_order() {
        return member_order;
    }

    public void setMember_order(List<MemberOrderBean> member_order) {
        this.member_order = member_order;
    }

    public List<ShopBean> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<ShopBean> shop_list) {
        this.shop_list = shop_list;
    }

}
