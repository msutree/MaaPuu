package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.TiXianActivity;
import com.maapuu.mereca.background.shop.bean.FinanceManageBean;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.DateUtil;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 财务管理
 * Created by Jia on 2018/3/12.
 */

public class FinanceManageActivity extends BaseActivity {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right2)
    TextView txtRight2;

    @BindView(R.id.choose_shop_lt)
    LinearLayout chooseShopLt;
    @BindView(R.id.choose_shop_tv)
    TextView shopTv;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.txt_yue)
    TextView txtYue;
    @BindView(R.id.fm_account_title_tv)
    TextView accountTitle_tv;


    @BindView(R.id.rv)
    RecyclerView rv;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter adapter;
    boolean isShowProgress = true;
    String shop_id = "0";//店铺id，全部店铺传0
    String date = "";//查询日期，格式：2018-04

    List<ShopBean> shopList;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_finance_manage);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("财务管理");
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight2.setTypeface(StringUtils.getFont(mContext));
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getAccountInfo();
            }
        });
        mLayoutManager=new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(mLayoutManager);
        rv.setNestedScrollingEnabled(false);
        rv.setHasFixedSize(true);
        adapter = new BaseRecyclerAdapter<FinanceManageBean.BalanceLogBean>(mContext,R.layout.item_shop_finance_manage) {
            @Override
            public void convert(BaseRecyclerHolder holder, FinanceManageBean.BalanceLogBean bean, int position, boolean isScrolling) {
                holder.setText(R.id.fm_name,bean.getBusiness_text());
                holder.setText(R.id.fm_time,bean.getCreate_time());
                holder.setText(R.id.fm_amount,bean.getAmount()+"元");
            }
        };
        rv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        date = DateUtil.calendar2String(Calendar.getInstance(), DateUtil.FORMAT_YEAR_MONTH);
        getAccountInfo();
    }

    private void getAccountInfo() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_account_info_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        shop_id,
                        date),isShowProgress);
    }

    private void setUI(FinanceManageBean bean) {
        FinanceManageBean.AccountInfoBean accountInfoBean = bean.getAccount_info();
        if(accountInfoBean != null){
            txtYue.setText(StringUtils.isEmpty(accountInfoBean.getBalance())?"0.00":accountInfoBean.getBalance());
        }
        shopList = bean.getShop_list();
        List<FinanceManageBean.BalanceLogBean> balanceList = bean.getBalance_log();
        adapter.clear();
        adapter.addList(balanceList);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_clear,R.id.txt_right,R.id.txt_right2,R.id.fm_withdraw_tv,R.id.choose_shop_lt})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_clear:
                date = "";
                accountTitle_tv.setText("全部明细");
                getAccountInfo();
                break;
            case R.id.txt_right2:
                //弹出时间滚轮
                showTimePv();
                break;

            case R.id.txt_right:
                it = new Intent(mContext, FmShopAccountActivity.class);
                it.putExtra("shop_ip",shop_id);
                it.putExtra("date",date);
                startActivity(it);
                break;

            case R.id.fm_withdraw_tv:
                if(!TextUtils.isEmpty(shop_id) && !"0".equals(shop_id)){
                    it = new Intent(mContext, TiXianActivity.class);
                    it.putExtra("shop_id",shop_id);
                    startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                } else {
                    ToastUtil.show(mContext,"请选择店铺");
                }

                break;

            case R.id.choose_shop_lt:
                //选择商铺
                if(shopList != null && shopList.size()>0){
                    ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                        @Override
                        public void onChoose(ShopBean item) {
                            shopTv.setText(item.getShop_name());
                            shop_id = item.getShop_id();
                            getAccountInfo();
                        }
                    });
                    chooseShopFilter.createPopup();
                    chooseShopFilter.showAsDropDown(chooseShopLt);
                } else {
                    ToastUtil.show(mContext,"没有可供选择的商铺");
                }

                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    isShowProgress = false;
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            FinanceManageBean bean = FastJsonTools.getPerson(object.optString("result"), FinanceManageBean.class);
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
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
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
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 0, 1);
        Calendar endDate = Calendar.getInstance();
        //Calendar endDate = selectedDate;
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date dates, View v) {//选中事件回调
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dates);

                date = DateUtil.calendar2String(calendar, DateUtil.FORMAT_YEAR_MONTH);
                accountTitle_tv.setText(date+"明细");
                getAccountInfo();
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
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                .setDividerColor(Color.parseColor("#EEEEEE"))
                .build();

        pvCustomTime.show();
    }
}
