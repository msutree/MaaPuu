package com.maapuu.mereca.background.shop.bean;

import java.util.List;

/**
 * 会员管理
 * Created by Jia on 2018/4/18.
 */

public class MemberDetailBean {
    MemberBean member_info;
    List<ConsumeBean> order_list;

    public MemberBean getMember_info() {
        return member_info;
    }

    public void setMember_info(MemberBean member_info) {
        this.member_info = member_info;
    }

    public List<ConsumeBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<ConsumeBean> order_list) {
        this.order_list = order_list;
    }
}
