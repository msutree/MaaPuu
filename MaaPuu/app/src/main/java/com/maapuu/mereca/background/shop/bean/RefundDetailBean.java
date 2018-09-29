package com.maapuu.mereca.background.shop.bean;

import java.util.List;

/**
 * 投诉详情
 * Created by Jia on 2018/4/24.
 */

public class RefundDetailBean {
    /**
     * order_no : PJ2018042116475309723
     * refund_status : 1
     * refund_no : 2018042117014177244RF
     * refund_amount : 58.00
     * refund_type : 仅退款
     * refund_reason : 无合适预约时间
     * refund_desc :
     * apply_time : 2018-04-21 17:01:41
     * nick_name : 趣味儿童与青少年
     * phone : 15972093060
     * detail : []
     */

    private String order_no;
    private String refund_status;
    private String refund_no;
    private String refund_amount;
    private String refund_type;
    private String refund_reason;
    private String refund_desc;
    private String apply_time;
    private String nick_name;
    private String phone;
    private List<DetailBean> detail;


    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public String getRefund_no() {
        return refund_no;
    }

    public void setRefund_no(String refund_no) {
        this.refund_no = refund_no;
    }

    public String getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(String refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(String refund_type) {
        this.refund_type = refund_type;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(String refund_reason) {
        this.refund_reason = refund_reason;
    }

    public String getRefund_desc() {
        return refund_desc;
    }

    public void setRefund_desc(String refund_desc) {
        this.refund_desc = refund_desc;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
