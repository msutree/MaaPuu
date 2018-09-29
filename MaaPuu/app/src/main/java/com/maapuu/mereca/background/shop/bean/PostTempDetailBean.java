package com.maapuu.mereca.background.shop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 岗位模板详情
 * Created by Jia on 2018/4/11.
 */

public class PostTempDetailBean implements Serializable {

    /**
     * func : [{"func_id":"1","func_name":"预约单"},{"func_id":"2","func_name":"店铺管理"},{"func_id":"3","func_name":"岗位设置"},{"func_id":"6","func_name":"员工管理"},{"func_id":"7","func_name":"项目管理"},{"func_id":"8","func_name":"商品管理"},{"func_id":"9","func_name":"营销活动"},{"func_id":"10","func_name":"会员管理"},{"func_id":"11","func_name":"订单管理"},{"func_id":"12","func_name":"会员订单"}]
     * srv : [{"srv_id":"1","srv_name":"洗发"},{"srv_id":"2","srv_name":"吹发"},{"srv_id":"3","srv_name":"剪发"},{"srv_id":"4","srv_name":"染发"},{"srv_id":"5","srv_name":"烫发"},{"srv_id":"6","srv_name":"护理"}]
     * wage : [{"wage_id":"1","srv_id":"1","srv_name":"洗发","is_num":"1","calc_type":"1","wage_detail":[{"step":"1","condition":"5","commission":"5"},{"step":"2","condition":"10","commission":"8"},{"step":"3","condition":"15","commission":"9"},{"step":"5","condition":"20","commission":"12"}]},{"wage_id":"2","srv_id":"2","srv_name":"吹发","is_num":"2","calc_type":"1","wage_detail":[{"step":"1","condition":"8","commission":"8"},{"step":"2","condition":"12","commission":"12"},{"step":"3","condition":"15","commission":"15"},{"step":"5","condition":"20","commission":"20"}]},{"wage_id":"3","srv_id":"3","srv_name":"剪发","is_num":"1","calc_type":"2","wage_detail":[{"step":"1","condition":"8","commission":"8"},{"step":"2","condition":"12","commission":"12"},{"step":"3","condition":"15","commission":"15"},{"step":"5","condition":"20","commission":"20"}]},{"wage_id":"4","srv_id":"4","srv_name":"染发","is_num":"2","calc_type":"2","wage_detail":[{"step":"1","condition":"8","commission":"8"},{"step":"2","condition":"12","commission":"12"},{"step":"3","condition":"15","commission":"15"},{"step":"5","condition":"20","commission":"20"}]},{"wage_id":"0","srv_id":"5","srv_name":"烫发","is_num":"","calc_type":"","wage_detail":[{"step":"","condition":"","commission":""},{"step":"","condition":"","commission":""}]}]
     * wage_base : 2000.00
     */

    private String wage_base;
    private List<FuncBean> func;
    private List<PostSrvBean> srv;
    private List<WageBean> wage;
    private List<PostBean> post;

    public String getWage_base() {
        return wage_base;
    }

    public void setWage_base(String wage_base) {
        this.wage_base = wage_base;
    }

    public List<FuncBean> getFunc() {
        return func;
    }

    public void setFunc(List<FuncBean> func) {
        this.func = func;
    }

    public List<PostSrvBean> getSrv() {
        return srv;
    }

    public void setSrv(List<PostSrvBean> srv) {
        this.srv = srv;
    }

    public List<WageBean> getWage() {
        return wage;
    }

    public void setWage(List<WageBean> wage) {
        this.wage = wage;
    }

    public List<PostBean> getPost() {
        return post;
    }

    public void setPost(List<PostBean> post) {
        this.post = post;
    }

}
