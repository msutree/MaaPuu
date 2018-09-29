package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.BalanceLogBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class MyAccountActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_left_1)
    TextView txtLeft1;
    @BindView(R.id.txt_title_1)
    TextView txtTitle1;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_right_1)
    TextView txtRight1;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar1)
    View mToolbar1;
    @BindView(R.id.toolbar2)
    View mToolbar2;
    @BindView(R.id.txt_yue)
    TextView txtYue;
    @BindView(R.id.txt_detail_label)
    TextView txtDetailLabel;
    @BindView(R.id.ll_has)
    LinearLayout llHas;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager mLayoutManager;
    private List<BalanceLogBean> list;
    private BaseRecyclerAdapter<BalanceLogBean> adapter;
    private String balance_id;
    private String balance;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_account);
        ImmersionBar.with(MyAccountActivity.this).fitsSystemWindows(false).transparentStatusBar()
                .statusBarDarkFont(true,0f).init();
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtLeft1.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("我的账户");txtTitle1.setText("我的账户");
        txtRight.setVisibility(View.VISIBLE);txtRight1.setVisibility(View.VISIBLE);
        txtRight.setText("\ue67d");txtRight1.setText("\ue67d");
        txtRight.setTextSize(16);txtRight1.setTextSize(16);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight1.setTypeface(StringUtils.getFont(mContext));
        list = new ArrayList<>();

        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0){//张开
                    ImmersionBar.with(MyAccountActivity.this).fitsSystemWindows(false).transparentStatusBar()
                            .statusBarDarkFont(true,0f).init();
                    mToolbar1.setVisibility(View.VISIBLE);
                    mToolbar2.setVisibility(View.GONE);
                    mToolbar2.setBackgroundColor(getResources().getColor(R.color.transparent));
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {//收缩
                    ImmersionBar.with(MyAccountActivity.this).fitsSystemWindows(false).transparentStatusBar()
                            .statusBarDarkFont(true,0f).init();
                    mToolbar1.setVisibility(View.GONE);
                    mToolbar2.setVisibility(View.VISIBLE);
                    mToolbar2.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    int alpha=255-Math.abs(verticalOffset);//Math.abs返回绝对值
                    Log.e("alpha",alpha+"");
                    if(alpha<0){//收缩toolbar
                        mToolbar1.setVisibility(View.GONE);
                        mToolbar2.setVisibility(View.VISIBLE);
                    }else{//张开toolbar
                        mToolbar1.setVisibility(View.VISIBLE);
                        mToolbar2.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.account_info_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid")),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        balance_id = resultObj.optJSONObject("account_info").optString("balance_id");
                        balance = resultObj.optJSONObject("account_info").optString("balance");
                        txtYue.setText(StringUtils.fmtMicrometer(balance));
                        if(resultObj.has("balance_log") && !StringUtils.isEmpty(resultObj.optString("balance_log"))
                                && resultObj.optJSONArray("balance_log").length() > 0){
                            llHas.setVisibility(View.GONE);
                            txtDetailLabel.setVisibility(View.VISIBLE);
                            list = FastJsonTools.getPersons(resultObj.optString("balance_log"),BalanceLogBean.class);
                            setAdapter();
                        }else {
                            llHas.setVisibility(View.VISIBLE);
                            txtDetailLabel.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter() {
        adapter = new BaseRecyclerAdapter<BalanceLogBean>(mContext,list,R.layout.layout_my_account_detail) {
            @Override
            public void convert(BaseRecyclerHolder holder, BalanceLogBean bean, int position, boolean isScrolling) {
                holder.setText(R.id.txt_title,bean.getBusiness_text());
                holder.setText(R.id.txt_time,bean.getCreate_time());
                holder.setText(R.id.txt_amount,bean.getAmount()+"元");
                if(position == list.size() - 1){
                    holder.setVisible(R.id.line,false);
                }else {
                    holder.setVisible(R.id.line,true);
                }
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_left_1,R.id.txt_right,R.id.txt_right_1,R.id.txt_tixian,R.id.txt_charge})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
            case R.id.txt_left_1:
                finish();
                break;
            case R.id.txt_right:
            case R.id.txt_right_1:
                it = new Intent(mContext,AccountDetailActivity.class);
                it.putExtra("balance_id",balance_id);
                startActivity(it);
                break;
            case R.id.txt_tixian:
                it = new Intent(mContext,TiXianActivity.class);
                it.putExtra("balance_id",balance_id);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.txt_charge:
                it = new Intent(mContext,ChargeActivity.class);
                it.putExtra("balance_id",balance_id);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                initData();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(MyAccountActivity.this).destroy();
    }
}
