package com.maapuu.mereca.background.shop.bean;

import java.util.List;

/**
 * 订单详情bean
 * Created by Jia on 2018/4/24.
 */

public class OrderCommentDetailBean {

    /**
     * evl_id : 7
     * evl_level : 5
     * create_time : 2018-04-07 11:41:47
     * nick_name : 趣味儿童与青少年
     * shop_desc_level : 5
     * srv_level : 4
     * staff_logist_level : 5
     * evl_content : aa1
     */

    private String order_no;
    private String evl_id;
    private float evl_level;
    private String create_time;
    private String nick_name;
    private float shop_desc_level;
    private float srv_level;
    private float staff_logist_level;
    private String evl_content;
    private List<DetailBean> detail;

    public String getEvl_id() {
        return evl_id;
    }

    public void setEvl_id(String evl_id) {
        this.evl_id = evl_id;
    }

    public float getEvl_level() {
        return evl_level;
    }

    public void setEvl_level(float evl_level) {
        this.evl_level = evl_level;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public float getShop_desc_level() {
        return shop_desc_level;
    }

    public void setShop_desc_level(float shop_desc_level) {
        this.shop_desc_level = shop_desc_level;
    }

    public float getSrv_level() {
        return srv_level;
    }

    public void setSrv_level(float srv_level) {
        this.srv_level = srv_level;
    }

    public float getStaff_logist_level() {
        return staff_logist_level;
    }

    public void setStaff_logist_level(float staff_logist_level) {
        this.staff_logist_level = staff_logist_level;
    }

    public String getEvl_content() {
        return evl_content;
    }

    public void setEvl_content(String evl_content) {
        this.evl_content = evl_content;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public static class DetailBean {
        /**
         * business_id : 3
         * content_type : 2
         * content : http://beauty.whhxrc.com/./public/upload/temp/shop_cover.png
         * content_url : ./public/upload/temp/shop_cover.png
         * height : 290
         * width : 750
         */

        private String business_id;
        private String content_type;
        private String content;
        private String content_url;
        private int height;
        private int width;

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent_url() {
            return content_url;
        }

        public void setContent_url(String content_url) {
            this.content_url = content_url;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }

}
