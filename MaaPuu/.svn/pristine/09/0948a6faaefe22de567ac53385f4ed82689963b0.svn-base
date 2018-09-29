package com.maapuu.mereca.background.shop.bean;

import com.maapuu.mereca.bean.OrderChildBean;

import java.util.List;

/**
 * 项目订单详情
 * Created by Jia on 2018/4/23.
 */

public class ProjectOrderDetailBean {

    /**
     * shop_data : {"shop_id":"1","shop_name":"渼树光谷店","shop_logo":"http://beauty.whhxrc.com/./public/upload/appphotos/2018/04/11/2ba51c31c538b07e444023e25e701f36Srgb20.jpg","address_detail":"湖北省武汉市光谷广场店  你知道我在哪吗。你不知道你怎么知道我在这里呢。 哈哈 我又来了。"}
     * item_detail : {"order_detail_id":"54","item_id":"57","item_name":"中式洗护造型","item_img":"http://beauty.whhxrc.com/./public/upload/appphotos/2018/04/21/4d58ef0eb4a52a4554d21071c1b9be48gsfZ5M.jpg","item_desc":"","price":"58.00","num":"1"}
     * order_detail : {"oid":"53","status":"待使用","order_no":"PJ2018042116475309723","pay_type":"3","order_amount":"58.00","dis_amount":"0.00","pay_amount":"58.00","create_time_text":"2018-04-21 16:47"}
     */

    private ShopDataBean shop_data;
    private ItemDetailBean item_detail;//项目订单时使用
    private OrderDetailBean order_detail;
    private List<OrderChildBean> items;//商品时才使用
    private AddressInfo address_info;//商品时才使用

    public ShopDataBean getShop_data() {
        return shop_data;
    }

    public void setShop_data(ShopDataBean shop_data) {
        this.shop_data = shop_data;
    }

    public ItemDetailBean getItem_detail() {
        return item_detail;
    }

    public void setItem_detail(ItemDetailBean item_detail) {
        this.item_detail = item_detail;
    }

    public OrderDetailBean getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(OrderDetailBean order_detail) {
        this.order_detail = order_detail;
    }

    public List<OrderChildBean> getItems() {
        return items;
    }

    public void setItems(List<OrderChildBean> items) {
        this.items = items;
    }

    public AddressInfo getAddress_info() {
        return address_info;
    }

    public void setAddress_info(AddressInfo address_info) {
        this.address_info = address_info;
    }

    public static class ItemDetailBean {
        /**
         * order_detail_id : 54
         * item_id : 57
         * item_name : 中式洗护造型
         * item_img : http://beauty.whhxrc.com/./public/upload/appphotos/2018/04/21/4d58ef0eb4a52a4554d21071c1b9be48gsfZ5M.jpg
         * item_desc :
         * price : 58.00
         * num : 1
         */

        private String order_detail_id;
        private String item_id;
        private String item_name;
        private String item_img;
        private String item_desc;
        private String price;
        private String num;

        public String getOrder_detail_id() {
            return order_detail_id;
        }

        public void setOrder_detail_id(String order_detail_id) {
            this.order_detail_id = order_detail_id;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getItem_img() {
            return item_img;
        }

        public void setItem_img(String item_img) {
            this.item_img = item_img;
        }

        public String getItem_desc() {
            return item_desc;
        }

        public void setItem_desc(String item_desc) {
            this.item_desc = item_desc;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }

    public static class OrderDetailBean {
        /**
         * oid : 53
         * status : 待使用
         * order_no : PJ2018042116475309723
         * pay_type : 3
         * order_amount : 58.00
         * dis_amount : 0.00
         * pay_amount : 58.00
         * create_time_text : 2018-04-21 16:47
         */

        private String oid;
        private String status;
        private String order_no;
        private int pay_type;
        private String order_amount;
        private String dis_amount;
        private String pay_amount;
        private String create_time_text;
        private String nick_name;
        private String phone;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getDis_amount() {
            return dis_amount;
        }

        public void setDis_amount(String dis_amount) {
            this.dis_amount = dis_amount;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        public String getCreate_time_text() {
            return create_time_text;
        }

        public void setCreate_time_text(String create_time_text) {
            this.create_time_text = create_time_text;
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
    }

    public static class AddressInfo{

        /**
         * receiver :
         * receiver_phone :
         * address_detail :
         * logistics_no :
         * company_name :
         */

        private String receiver;
        private String receiver_phone;
        private String address_detail;
        private String logistics_no;
        private String company_name;

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getReceiver_phone() {
            return receiver_phone;
        }

        public void setReceiver_phone(String receiver_phone) {
            this.receiver_phone = receiver_phone;
        }

        public String getAddress_detail() {
            return address_detail;
        }

        public void setAddress_detail(String address_detail) {
            this.address_detail = address_detail;
        }

        public String getLogistics_no() {
            return logistics_no;
        }

        public void setLogistics_no(String logistics_no) {
            this.logistics_no = logistics_no;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }
    }
}
