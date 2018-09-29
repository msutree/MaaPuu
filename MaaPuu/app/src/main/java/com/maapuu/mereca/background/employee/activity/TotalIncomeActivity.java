package com.maapuu.mereca.background.employee.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.gyf.barlibrary.ImmersionBar;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.employee.bean.TotalIncomeBean;
import com.maapuu.mereca.base.BaseActivity;
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
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 总收入
 * Created by Jia on 2018/3/5.
 */

public class TotalIncomeActivity extends BaseActivity{
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.balance)
    TextView balanceTv;
    @BindView(R.id.last_day_amount)
    TextView lastDayAmountTv;
    @BindView(R.id.last_week_amount)
    TextView lastWeekAmount;
    @BindView(R.id.wage_total)
    TextView wageTotal;
    @BindView(R.id.ll_has)
    LinearLayout llHas;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView rv;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<TotalIncomeBean.BalanceListBean> adapter;
    private int page = 1;
    String date = "";//月份过滤，如：2018-04
    boolean isShowProgress = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .fitsSystemWindows(false)
                .transparentStatusBar()
                .init();
    }

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.em_activity_total_income);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtRight.setTypeface(StringUtils.getFont(mContext));
        date = StringUtils.getTime(new Date()).substring(0,7);

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getTotalIncome();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                page++;
                getTotalIncome();
            }
        });

        mLayoutManager=new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(mLayoutManager);
        rv.setHasFixedSize(true);

        adapter = new BaseRecyclerAdapter<TotalIncomeBean.BalanceListBean>(mContext,R.layout.em_item_income) {
            @Override
            public void convert(BaseRecyclerHolder holder, TotalIncomeBean.BalanceListBean bean, int position, boolean isScrolling) {
                    holder.setText(R.id.create_time_text,bean.getCreate_time_text());
                    holder.setText(R.id.amount,bean.getAmount());
                    holder.setText(R.id.balance,bean.getBalance());
            }
        };
        rv.setAdapter(adapter);
    }

    @Override
    public void initData() {
        getTotalIncome();
    }

    private void getTotalIncome() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_wage_total_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        date,page),isShowProgress);
    }

    private void setUI(TotalIncomeBean bean) {
        balanceTv.setText(StringUtils.isEmpty(bean.getBalance())?"0.00":bean.getBalance());

        TotalIncomeBean.WageMonthBean wage = bean.getWage_month();
        if(wage != null){
            lastDayAmountTv.setText(StringUtils.isEmpty(wage.getLast_day_amount())?"0.00":wage.getLast_day_amount());
            lastWeekAmount.setText(StringUtils.isEmpty(wage.getLast_week_amount())?"0.00":wage.getLast_week_amount());
            wageTotal.setText(StringUtils.isEmpty(wage.getWage_total())?"0.00":wage.getWage_total());
        }

        List<TotalIncomeBean.BalanceListBean> list = bean.getBalance_list();
        if(page == 1 && list.size() == 0){
            llHas.setVisibility(View.VISIBLE);
        }else {
            llHas.setVisibility(View.GONE);
        }
        if(page == 1) adapter.clear();
        adapter.addList(list);
        if(page > 1){
            if((list ==null || list.size()==0)){
                page--;
                ToastUtil.show(mContext,"暂无更多数据");
            } else {
                rv.smoothScrollToPosition(adapter.getList().size()-list.size()-1);
            }
        }
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_clear,R.id.txt_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_clear:
                date ="";
                page = 1;
                getTotalIncome();
                break;
            case R.id.txt_right:
                //弹出时间滚轮
                showTimePv();
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
                            TotalIncomeBean bean = FastJsonTools.getPerson(object.optString("result"), TotalIncomeBean.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
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
            public void onTimeSelect(Date date1, View v) {//选中事件回调
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);

                date =DateUtil.calendar2String(calendar, DateUtil.FORMAT_YEAR_MONTH);
                page = 1;
                getTotalIncome();
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
