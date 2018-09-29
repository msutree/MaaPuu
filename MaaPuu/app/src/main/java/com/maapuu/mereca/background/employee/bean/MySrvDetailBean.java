package com.maapuu.mereca.background.employee.bean;

import java.util.List;

/**
 * 我的服务详情
 * Created by Jia on 2018/4/10.
 */

public class MySrvDetailBean {

    /**
     * reasons : ["顾客太多忙不过来","有其他服务人员为TA服务","顾客自己选择不做"]
     * order_detail : {"oid":"108","code2d_id":"58","nick_name":"超级 账号","avatar":"http://beauty.whhxrc.com/./public/upload/appphotos/2018/05/19/c8e17ed3249134dd9bf4ed4ead7f46feYawAcV.jpg","item_name":"中式洗护造型","pay_amount":"0.00","order_no":"PJ2018052510515231389","pay_type":"3","create_time_text":"2018-05-25 10:51","code_status":"3"}
     * cur_srv : {"appoint_srv_id":"185","srv_id":"1","srv_name":"洗发","staff_id":"27","staff_name":"杀马特造型发型师","staff_avatar":"http://beauty.whhxrc.com/./public/upload/appphotos/2018/05/22/0b6869bfcea58a964ce561c4f78a3e73OOAxst.jpg","srv_duration":"10","srv_img":"http://beauty.whhxrc.com/./public/upload/temp/xifafuwu.png","srv_begin_time":"2018-05-25 13:59:39","used_time":"2","reward":"10.00"}
     */

    private OrderDetailBean order_detail;
    private CurSrvBean cur_srv;
    private List<String> reasons;

    public OrderDetailBean getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(OrderDetailBean order_detail) {
        this.order_detail = order_detail;
    }

    public CurSrvBean getCur_srv() {
        return cur_srv;
    }

    public void setCur_srv(CurSrvBean cur_srv) {
        this.cur_srv = cur_srv;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public static class OrderDetailBean {
        /**
         * oid : 108
         * code2d_id : 58
         * nick_name : 超级 账号
         * avatar : http://beauty.whhxrc.com/./public/upload/appphotos/2018/05/19/c8e17ed3249134dd9bf4ed4ead7f46feYawAcV.jpg
         * item_name : 中式洗护造型
         * pay_amount : 0.00
         * order_no : PJ2018052510515231389
         * pay_type : 3
         * create_time_text : 2018-05-25 10:51
         * code_status : 3
         */

        private String oid;
        private String code2d_id;
        private String nick_name;
        private String avatar;
        private String item_name;
        private String pay_amount;
        private String order_no;
        private int pay_type;
        private String create_time_text;
        private String code_status;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getCode2d_id() {
            return code2d_id;
        }

        public void setCode2d_id(String code2d_id) {
            this.code2d_id = code2d_id;
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

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public String getCreate_time_text() {
            return create_time_text;
        }

        public void setCreate_time_text(String create_time_text) {
            this.create_time_text = create_time_text;
        }

        public String getCode_status() {
            return code_status;
        }

        public void setCode_status(String code_status) {
            this.code_status = code_status;
        }
    }

    public static class CurSrvBean {
        /**
         * appoint_srv_id : 185
         * srv_id : 1
         * srv_name : 洗发
         * staff_id : 27
         * staff_name : 杀马特造型发型师
         * staff_avatar : http://beauty.whhxrc.com/./public/upload/appphotos/2018/05/22/0b6869bfcea58a964ce561c4f78a3e73OOAxst.jpg
         * srv_duration : 10
         * srv_img : http://beauty.whhxrc.com/./public/upload/temp/xifafuwu.png
         * srv_begin_time : 2018-05-25 13:59:39
         * used_time : 2
         * reward : 10.00
         */

        private String appoint_srv_id;
        private String srv_id;
        private String srv_name;
        private String staff_id;
        private String staff_name;
        private String staff_avatar;
        private String srv_duration;
        private String srv_img;
        private String srv_begin_time;
        private String srv_end_time;
        private int used_time;
        private String reward;

        public String getAppoint_srv_id() {
            return appoint_srv_id;
        }

        public void setAppoint_srv_id(String appoint_srv_id) {
            this.appoint_srv_id = appoint_srv_id;
        }

        public String getSrv_id() {
            return srv_id;
        }

        public void setSrv_id(String srv_id) {
            this.srv_id = srv_id;
        }

        public String getSrv_name() {
            return srv_name;
        }

        public void setSrv_name(String srv_name) {
            this.srv_name = srv_name;
        }

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
        }

        public String getStaff_name() {
            return staff_name;
        }

        public void setStaff_name(String staff_name) {
            this.staff_name = staff_name;
        }

        public String getStaff_avatar() {
            return staff_avatar;
        }

        public void setStaff_avatar(String staff_avatar) {
            this.staff_avatar = staff_avatar;
        }

        public String getSrv_duration() {
            return srv_duration;
        }

        public void setSrv_duration(String srv_duration) {
            this.srv_duration = srv_duration;
        }

        public String getSrv_img() {
            return srv_img;
        }

        public void setSrv_img(String srv_img) {
            this.srv_img = srv_img;
        }

        public String getSrv_begin_time() {
            return srv_begin_time;
        }

        public void setSrv_begin_time(String srv_begin_time) {
            this.srv_begin_time = srv_begin_time;
        }

        public String getSrv_end_time() {
            return srv_end_time;
        }

        public void setSrv_end_time(String srv_end_time) {
            this.srv_end_time = srv_end_time;
        }

        public int getUsed_time() {
            return used_time;
        }

        public void setUsed_time(int used_time) {
            this.used_time = used_time;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }
    }
}
