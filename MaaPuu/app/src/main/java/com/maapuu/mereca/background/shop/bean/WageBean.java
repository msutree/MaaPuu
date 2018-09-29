package com.maapuu.mereca.background.shop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 岗位设置 工资
 * Created by Jia on 2018/4/13.
 */

public class WageBean implements Serializable{
    /**
     * wage_id : 1
     * srv_id : 1
     * srv_name : 洗发
     * is_num : 	计费方式，1按次数计费，2按比例计费
     * calc_type : 1
     * wage_detail : [{"step":"1","condition":"5","commission":"5"},{"step":"2","condition":"10","commission":"8"},{"step":"3","condition":"15","commission":"9"},{"step":"5","condition":"20","commission":"12"}]
     */

    private String wage_id;
    private String srv_id;
    private String srv_name;
    private int is_num; //计费方式，1按次数计费，2按比例计费
    private int calc_type; //计算方式：1累进计算；2累计计算
    private List<WageDetailBean> wage_detail;

    public String getWage_id() {
        return wage_id;
    }

    public void setWage_id(String wage_id) {
        this.wage_id = wage_id;
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

    public int getIs_num() {
        return is_num;
    }

    public void setIs_num(int is_num) {
        this.is_num = is_num;
    }

    public int getCalc_type() {
        return calc_type;
    }

    public void setCalc_type(int calc_type) {
        this.calc_type = calc_type;
    }

    public List<WageDetailBean> getWage_detail() {
        return wage_detail;
    }

    public void setWage_detail(List<WageDetailBean> wage_detail) {
        this.wage_detail = wage_detail;
    }

    public static class WageDetailBean implements Serializable {
        /**
         * step : 1
         * condition : 5
         * commission : 5
         * condition_text: "5次以上10元",
           commission_text: "10元"
         */

        private String step;
        private String condition;
        private String commission;
        private String condition_text;
        private String commission_text;


        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getCondition_text() {
            return condition_text;
        }

        public void setCondition_text(String condition_text) {
            this.condition_text = condition_text;
        }

        public String getCommission_text() {
            return commission_text;
        }

        public void setCommission_text(String commission_text) {
            this.commission_text = commission_text;
        }
    }
}
