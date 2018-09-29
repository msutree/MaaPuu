package com.maapuu.mereca.background.shop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.DividerGridItemDecoration;
import com.maapuu.mereca.recycleviewutil.FullyGridLayoutManager;
import com.maapuu.mereca.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/22. 废弃
 */

public class ShopProjectOrderDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.rl_contact_phone)
    RelativeLayout rlContactPhone;
    @BindView(R.id.ll_tk)
    LinearLayout llTk;
    @BindView(R.id.ll_pj)
    LinearLayout llPj;
    @BindView(R.id.txt_label_1)
    TextView txtLabel1;
    @BindView(R.id.txt_label_2)
    TextView txtLabel2;
    @BindView(R.id.txt_btn_1)
    TextView txtBtn1;
    @BindView(R.id.txt_btn_2)
    TextView txtBtn2;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private int type; // 0退款   1投诉   2评价

    private List<String> list;
    private BaseRecyclerAdapter<String> adapter;
    private AlertView alertView;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_project_order_detail);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        type = getIntent().getIntExtra("type",2);

        switch (type){
            case 0:
                txtTitle.setText("退款订单详情");
                rlContactPhone.setVisibility(View.GONE);
                llTk.setVisibility(View.VISIBLE);
                llPj.setVisibility(View.GONE);
                txtLabel1.setText("退款详情");txtLabel2.setText("退款说明");
                llBottom.setVisibility(View.VISIBLE);
                txtBtn1.setText("拒绝退款");txtBtn2.setText("同意退款");
                break;
            case 1:
                txtTitle.setText("投诉订单详情");
                rlContactPhone.setVisibility(View.VISIBLE);
                llTk.setVisibility(View.GONE);
                llPj.setVisibility(View.GONE);
                txtLabel1.setText("投诉详情");txtLabel2.setText("投诉建议");
                llBottom.setVisibility(View.VISIBLE);
                txtBtn1.setText("处理完成");txtBtn2.setText("拨打电话");
                break;
            case 2:
                txtTitle.setText("订单详情");
                rlContactPhone.setVisibility(View.GONE);
                llTk.setVisibility(View.GONE);
                llPj.setVisibility(View.VISIBLE);
                txtLabel1.setText("评价详情");txtLabel2.setText("评价详情");
                llBottom.setVisibility(View.GONE);
                break;
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new FullyGridLayoutManager(mContext,3, LinearLayoutManager.VERTICAL,false));
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext,20
                ,mContext.getResources().getColor(R.color.white)));
        list = new ArrayList<>();
        list.add("");list.add("");list.add("");
        adapter = new BaseRecyclerAdapter<String>(mContext,list,R.layout.layout_goods_comment_image_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, String item, int position, boolean isScrolling) {

            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_btn_1,R.id.txt_btn_2})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_btn_1:
                if(type == 0){
                    alertView = new AlertView(null, "您确定拒绝此退款申请吗？", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                            }
                        }
                    });
                    alertView.show();
                }else if(type == 1){
                    alertView = new AlertView(null, "您确定同意此退款申请吗？", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                            }
                        }
                    });
                    alertView.show();
                }
                break;
            case R.id.txt_btn_2:
                if(type == 0){
                    alertView = new AlertView(null, "您确定此投诉已处理完成吗？", "取消", null, new String[]{"确定"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                            }
                        }
                    });
                    alertView.show();
                }else if(type == 1){
                    alertView = new AlertView(null, "TEL:18562453695", "取消", null, new String[]{"拨打"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                            }
                        }
                    });
                    alertView.show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            super.onBackPressed();
        }
    }
}
