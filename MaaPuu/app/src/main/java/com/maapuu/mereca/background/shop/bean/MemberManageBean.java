package com.maapuu.mereca.background.shop.bean;

import com.maapuu.mereca.bean.ShopBean;

import java.util.List;

/**
 * 会员管理
 * Created by Jia on 2018/4/18.
 */

public class MemberManageBean {
    private List<ShopBean> shop_list;
    private List<MemberBean> member_info;

    public List<ShopBean> getShop_list() {
        return shop_list;
    }

    public void setShop_list(List<ShopBean> shop_list) {
        this.shop_list = shop_list;
    }

    public List<MemberBean> getMember_info() {
        return member_info;
    }

    public void setMember_info(List<MemberBean> member_info) {
        this.member_info = member_info;
    }
}
