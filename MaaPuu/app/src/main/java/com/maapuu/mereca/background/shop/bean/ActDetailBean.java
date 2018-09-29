package com.maapuu.mereca.background.shop.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.util.List;

/**
 * 营销活动详情
 * Created by Jia on 2018/4/16.
 */

public class ActDetailBean implements Serializable{

    /**
     * card_data : {"card_id":"1","card_img":"http://beauty.whhxrc.com/./public/upload/temp/card_page.png","card_img_url":"./public/upload/temp/card_page.png","card_name":"使用","card_desc":"说明","card_type":"1","recharge_amount":"6.00","give_amount":"10.00","times":"1","limit_months":"5","limit_months_text":"一年","deadline_begin":"2018-04-14 00:00:00","deadline_end":"2018-04-16 00:00:00","discount":"0.0","detail":[{"business_id":"1","content_type":"1","content":"尊享你的幸福","content_url":"","height":"0","width":"0"},{"business_id":"1","content_type":"1","content":"发型不错，很漂亮","content_url":"","height":"0","width":"0"},{"business_id":"1","content_type":"2","content":"http://beauty.whhxrc.com/./public/upload/temp/shop_cover.png","content_url":"./public/upload/temp/shop_cover.png","height":"290","width":"750"}]}
     * act_limit_options : {"1":"一个月","3":"三个月","6":"半年","12":"一年"}
     * shop_data : [{"shop_id":"1","shop_name":"渼树光谷店"},{"shop_id":"2","shop_name":"渼树时间广场店"},{"shop_id":"1","shop_name":"渼树光谷店"},{"shop_id":"2","shop_name":"渼树时间广场店"},{"shop_id":"2","shop_name":"渼树时间广场店"},{"shop_id":"1","shop_name":"渼树光谷店"},{"shop_id":"2","shop_name":"渼树时间广场店"},{"shop_id":"1","shop_name":"渼树光谷店"}]
     * shop_ids : 1,2,1,2,2,1,2,1
     * shop_names : 渼树光谷店,渼树时间广场店,渼树光谷店,渼树时间广场店,渼树时间广场店,渼树光谷店,渼树时间广场店,渼树光谷店
     * item_data : [{"item_id":"1","item_name":"时尚剪发"},{"item_id":"2","item_name":"时尚短发"},{"item_id":"1","item_name":"时尚剪发"},{"item_id":"2","item_name":"时尚短发"},{"item_id":"1","item_name":"时尚剪发"},{"item_id":"2","item_name":"时尚短发"},{"item_id":"1","item_name":"时尚剪发"},{"item_id":"2","item_name":"时尚短发"}]
     * item_ids : 1,2,1,2,1,2,1,2
     * item_names : 时尚剪发,时尚短发,时尚剪发,时尚短发,时尚剪发,时尚短发,时尚剪发,时尚短发
     */

    private CardDataBean card_data;
    private PackDataBean pack_data;
    private RedDataBean red_data;
    private List<ActLimitOptionsBean> act_limit_options;
    private List<ActDelOptionsBean> act_del_options;
    private String shop_ids;
    private String shop_names;
    private String item_ids;
    private String item_names;
    private List<ShopDataBean> shop_data;
    private List<ItemDataBean> item_data;

    public CardDataBean getCard_data() {
        return card_data;
    }

    public void setCard_data(CardDataBean card_data) {
        this.card_data = card_data;
    }

    public PackDataBean getPack_data() {
        return pack_data;
    }

    public void setPack_data(PackDataBean pack_data) {
        this.pack_data = pack_data;
    }

    public RedDataBean getRed_data() {
        return red_data;
    }

    public void setRed_data(RedDataBean red_data) {
        this.red_data = red_data;
    }

    public List<ActLimitOptionsBean> getAct_limit_options() {
        return act_limit_options;
    }

    public void setAct_limit_options(List<ActLimitOptionsBean> act_limit_options) {
        this.act_limit_options = act_limit_options;
    }

    public String getShop_ids() {
        return shop_ids;
    }

    public void setShop_ids(String shop_ids) {
        this.shop_ids = shop_ids;
    }

    public String getShop_names() {
        return shop_names;
    }

    public void setShop_names(String shop_names) {
        this.shop_names = shop_names;
    }

    public String getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(String item_ids) {
        this.item_ids = item_ids;
    }

    public String getItem_names() {
        return item_names;
    }

    public void setItem_names(String item_names) {
        this.item_names = item_names;
    }

    public List<ShopDataBean> getShop_data() {
        return shop_data;
    }

    public void setShop_data(List<ShopDataBean> shop_data) {
        this.shop_data = shop_data;
    }

    public List<ItemDataBean> getItem_data() {
        return item_data;
    }

    public void setItem_data(List<ItemDataBean> item_data) {
        this.item_data = item_data;
    }

    public List<ActDelOptionsBean> getAct_del_options() {
        return act_del_options;
    }

    public void setAct_del_options(List<ActDelOptionsBean> act_del_options) {
        this.act_del_options = act_del_options;
    }

    public static class ActLimitOptionsBean implements Serializable,IPickerViewData {

        /**
         * month : 1
         * month_text : 一个月
         */

        private String month;
        private String month_text;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getMonth_text() {
            return month_text;
        }

        public void setMonth_text(String month_text) {
            this.month_text = month_text;
        }

        @Override
        public String getPickerViewText() {
            return month_text;
        }
    }

    public static class ActDelOptionsBean implements Serializable,IPickerViewData {

        /**
         * option : 1
         * option_text : 所有关注我的用户
         */

        private String option;
        private String option_text;

        @Override
        public String getPickerViewText() {
            return option_text;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getOption_text() {
            return option_text;
        }

        public void setOption_text(String option_text) {
            this.option_text = option_text;
        }
    }
}
