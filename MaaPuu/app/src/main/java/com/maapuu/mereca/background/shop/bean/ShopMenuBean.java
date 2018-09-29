package com.maapuu.mereca.background.shop.bean;

/**
 * 我的店铺 菜单
 * Created by Jia on 2018/4/11.
 */

public class ShopMenuBean {

    /**
     * func_name : 预约单
     * func_code : 10101
     * flag : 0   为1 表示高，0表示1像素

     */

    private String func_name;
    private String func_code;
    private int flag;

    public String getFunc_name() {
        return func_name;
    }

    public void setFunc_name(String func_name) {
        this.func_name = func_name;
    }

    public String getFunc_code() {
        return func_code;
    }

    public void setFunc_code(String func_code) {
        this.func_code = func_code;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
