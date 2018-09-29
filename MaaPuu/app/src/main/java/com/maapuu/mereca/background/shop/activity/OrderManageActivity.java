package com.maapuu.mereca.background.shop.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.bean.OrderManageBean;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.background.shop.dialog.OrderFilter;
import com.maapuu.mereca.background.shop.fragment.OrderCommodityFragment;
import com.maapuu.mereca.background.shop.fragment.OrderProjectFragment;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.DateUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单管理
 * Created by Jia on 2018/3/10.
 */

public class OrderManageActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;
    @BindView(R.id.choose_shop_tv)
    TextView shopTv;

    @BindView(R.id.om_menu_project_lt)
    LinearLayout menuProjectLt;//项目
    @BindView(R.id.om_menu_project_tv)
    TextView menuProjectTv;
    @BindView(R.id.om_menu_project_arrow)
    ImageView menuProjectArrow;

    @BindView(R.id.om_menu_goods_lt)
    LinearLayout menuGoodsLt;//商品
    @BindView(R.id.om_menu_goods_tv)
    TextView menuGoodsTv;
    @BindView(R.id.om_menu_goods_arrow)
    ImageView menuGoodsArrow;

    private int page = 1;
    boolean isShowProgress = true;

    private TextView[] tvs;
    private int currentMenu = 0;

    String shop_id = "0"; //店铺id，全部店铺传0
    String date = ""; //日期过滤，如：2018-04-18
    String status = ""; //订单状态：待使用,待评价,已完成，状态传中文
    String complaint = ""; //投诉：1投诉建议,2已处理
    String refund = ""; //退款：1退款申请,2退款成功,4商家拒绝

    List<ShopBean> shopList;

    private FragmentManager fragmentManager;
    OrderProjectFragment projectFragment;
    OrderCommodityFragment commodityFragment;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_order_manage);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("订单管理");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight.setText("\ue69a");
        txtRight.setTextSize(18);

        tvs = new TextView[]{menuProjectTv,menuGoodsTv};
        setMenuTxt(0);
        fragmentManager = getSupportFragmentManager();
        addFragment(0);
    }

    private void setMenuTxt(int position) {
        for (int i = 0; i < tvs.length; i++){
            if(i == position){
                tvs[position].setSelected(true);
            }else {
                tvs[i].setSelected(false);
            }
        }
    }

    @Override
    public void initData() {
        getOrderList();
    }

    private void setUI(OrderManageBean bean) {
        if(bean.getShop_list() != null && bean.getShop_list().size()>0) shopList = bean.getShop_list();
    }

    private void getOrderList() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_project_list_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        shop_id,
                        date,
                        status,
                        complaint,
                        refund,
                        page),isShowProgress);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_clear,R.id.ao_choose_shop_lt,R.id.om_menu_project_lt,R.id.om_menu_goods_lt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_clear:
                date = "";
                if(currentMenu == 0 && projectFragment != null){
                    projectFragment.onSelect(shop_id,date,status,complaint,refund);
                } else if(currentMenu == 1 && commodityFragment != null){
                    commodityFragment.onSelect(shop_id,date,status,complaint,refund);
                }
                break;
            case R.id.ao_choose_shop_lt:
                //选择商铺
                if(shopList != null && shopList.size()>0){
                    ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                        @Override
                        public void onChoose(ShopBean item) {
                            shopTv.setText(item.getShop_name());
                            shop_id = item.getShop_id();
                            page = 1;

                            if(projectFragment != null){
                                projectFragment.onSelect(shop_id,date,status,complaint,refund);
                            }
                            if(commodityFragment != null){
                                commodityFragment.onSelect(shop_id,date,status,complaint,refund);
                            }
                        }
                    });
                    chooseShopFilter.createPopup();
                    chooseShopFilter.showAsDropDown(chooseShopLt);
                } else {
                    ToastUtil.show(mContext,"没有可供选择的商铺");
                }

                break;

            case R.id.txt_right:
                //弹出时间滚轮
                showTimePv();
                break;

            case R.id.om_menu_project_lt:
                addFragment(0);
                if(currentMenu != 0){
                    setMenuTxt(0);
                    currentMenu = 0;
                } else {
                    menuProjectArrow.setImageResource(R.mipmap.sq);
                    String[] menuArr1 = new String[]{"待使用","待评价","已完成"};
                    OrderFilter orderFilter = new OrderFilter(mContext, Arrays.asList(menuArr1),status,complaint,refund, new OrderFilter.ConfirmCall() {

                        @Override
                        public void onCall(String status1,String complaint1,String refund1) {
                            status = status1;
                            complaint = complaint1;
                            refund = refund1;

                            if(currentMenu == 0 && projectFragment != null){
                                projectFragment.onSelect(shop_id,date,status,complaint,refund);
                            }
                        }
                    });
                    orderFilter.createPopup();
                    orderFilter.showAsDropDown(menuProjectLt);
                    orderFilter.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            menuProjectArrow.setImageResource(R.mipmap.zk);
                        }
                    });
                }

                break;

            case R.id.om_menu_goods_lt:
                addFragment(1);
                if(currentMenu != 1){
                    setMenuTxt(1);
                    currentMenu = 1;
                } else {
                    menuGoodsArrow.setImageResource(R.mipmap.sq);
                    String[] menuArr2 = new String[]{"待发货","已发货","待评价","已完成"};
                    OrderFilter orderFilter2 = new OrderFilter(mContext,Arrays.asList(menuArr2),status,complaint,refund, new OrderFilter.ConfirmCall() {

                        @Override
                        public void onCall(String status1,String complaint1,String refund1) {
                            status = status1;
                            complaint = complaint1;
                            refund = refund1;

                            if(currentMenu == 1 && commodityFragment != null){
                                commodityFragment.onSelect(shop_id,date,status,complaint,refund);
                            }
                        }
                    });
                    orderFilter2.createPopup();
                    orderFilter2.showAsDropDown(menuProjectLt);
                    orderFilter2.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            menuGoodsArrow.setImageResource(R.mipmap.zk);
                        }
                    });
                }

                break;

        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    isShowProgress = true;
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            OrderManageBean bean = FastJsonTools.getPerson(object.optString("result"), OrderManageBean.class);
                            if(bean != null){
                                setUI(bean);
                            }
                        }
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }

    TimePickerView pvCustomTime;
    private void showTimePv() {

        /**
         * 注意事项：
         * 月份是0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        final Calendar selectedDate = Calendar.getInstance();//系统当前时间
        final Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 0, 1);
        Calendar endDate = Calendar.getInstance();
        //Calendar endDate = selectedDate;
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date dates, View v) {//选中事件回调
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dates);

                date = DateUtil.calendar2String(calendar, DateUtil.FORMAT_DATE);
                if(currentMenu == 0 && projectFragment != null){
                    projectFragment.onSelect(shop_id,date,status,complaint,refund);
                } else if(currentMenu == 1 && commodityFragment != null){
                    commodityFragment.onSelect(shop_id,date,status,complaint,refund);
                }

            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.my_pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(pvCustomTime != null){
                                    pvCustomTime.returnData();
                                    pvCustomTime.dismiss();
                                }

                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                .setDividerColor(Color.parseColor("#EEEEEE"))
                .build();

        pvCustomTime.show();
    }

    private void addFragment(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (projectFragment == null) {
                    projectFragment = new OrderProjectFragment();
                    fragmentTransaction.add(R.id.om_container, projectFragment);
                } else {
                    fragmentTransaction.show(projectFragment);
                }
                break;

            case 1:
                if (commodityFragment == null) {
                    commodityFragment = new OrderCommodityFragment();
                    fragmentTransaction.add(R.id.om_container, commodityFragment);
                } else {
                    fragmentTransaction.show(commodityFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    // 隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {
        if (projectFragment != null) {
            transaction.hide(projectFragment);
        }
        if (commodityFragment != null) {
            transaction.hide(commodityFragment);
        }
    }

    public interface OnSelectListener{
        /*  shop_id;//店铺id，全部店铺传0
            date;//日期过滤，如：2018-04-18
            status;//订单状态：待使用,待评价,已完成，状态传中文
            complaint;//投诉：1投诉建议,2已处理
            refund;//退款：1退款申请,2退款成功,4商家拒绝*/
        void onSelect(String shop_id,String date,String status,String complaint,String refund);
    }

}
