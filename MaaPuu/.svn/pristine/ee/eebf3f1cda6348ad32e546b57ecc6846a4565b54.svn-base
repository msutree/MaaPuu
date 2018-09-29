package com.maapuu.mereca.background.shop.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.employee.fragment.WorkClientFragment;
import com.maapuu.mereca.background.shop.fragment.ShopDataFragment;
import com.maapuu.mereca.background.shop.fragment.ShopHomeFragment;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的店面
 * Created by Jia on 2018/2/27.
 */

public class MyShopActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_all)
    TextView txtAll;
    @BindView(R.id.txt_daifk)
    TextView txtDaifk;
    @BindView(R.id.txt_daisy)
    TextView txtDaisy;

    @BindView(R.id.ms_set_goal_tv)
    TextView setGoalTv;

    private TextView[] tvs;
    private FragmentManager fragmentManager;
    private ShopHomeFragment shopHomeFragment;
    private ShopDataFragment shopDataFragment;
    private WorkClientFragment workClientFragment;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_my_shop);
    }

    @Override
    public void initView() {
        initTab();
    }

    private void initTab() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        setGoalTv.setVisibility(View.GONE);
        tvs = new TextView[]{txtAll,txtDaifk,txtDaisy};
        setHead(0);
        fragmentManager = getSupportFragmentManager();
        addFragment(0);
    }

    private void setHead(int postion) {
        for (int i = 0; i < tvs.length; i++){
            if(i == postion){
                tvs[postion].setSelected(true);
            }else {
                tvs[i].setSelected(false);
            }
        }
    }

    // 隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {
        if (shopHomeFragment != null) {
            transaction.hide(shopHomeFragment);
        }
        if (shopDataFragment != null) {
            transaction.hide(shopDataFragment);
        }
        if (workClientFragment != null) {
            transaction.hide(workClientFragment);
        }
    }

    private void addFragment(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (shopHomeFragment == null) {
                    shopHomeFragment = new ShopHomeFragment();
                    fragmentTransaction.add(R.id.fl_container, shopHomeFragment);
                } else {
                    fragmentTransaction.show(shopHomeFragment);
                }
                break;
            case 1:
                if (shopDataFragment == null) {
                    shopDataFragment = new ShopDataFragment();
                    fragmentTransaction.add(R.id.fl_container, shopDataFragment);
                } else {
                    fragmentTransaction.show(shopDataFragment);
                }
                break;
            case 2:
                if (workClientFragment == null) {
                    workClientFragment = WorkClientFragment.newInstance(1);//是否我的店铺。0为工作狂，1为我的店铺
                    fragmentTransaction.add(R.id.fl_container, workClientFragment);
                } else {
                    fragmentTransaction.show(workClientFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"设置成功");
                        shopDataFragment.refresh();
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

    @Override
    @OnClick({R.id.txt_left,R.id.txt_all,R.id.txt_daifk,R.id.txt_daisy,R.id.ms_set_goal_tv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_all:
                setGoalTv.setVisibility(View.GONE);
                setHead(0);
                addFragment(0);
                break;
            case R.id.txt_daifk:
                setGoalTv.setVisibility(View.VISIBLE);
                setHead(1);
                addFragment(1);
                break;
            case R.id.txt_daisy:
                setGoalTv.setVisibility(View.GONE);
                setHead(2);
                addFragment(2);
                break;
            case R.id.ms_set_goal_tv:
                if(StringUtils.isEmpty(shopDataFragment.getShop_id())){
                    ToastUtil.show(mContext,"请选择店铺");
                    return;
                }
                NiceDialog.init()
                        .setLayoutId(R.layout.pop_set_target)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            public void convertView(com.othershe.nicedialog.ViewHolder holder, final BaseNiceDialog dialog) {
                                final EditText priceEt = holder.getView(R.id.et_price);
                                holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String raise_price = priceEt.getText().toString().trim();
                                        if(TextUtils.isEmpty(raise_price)){
                                            ToastUtil.show(mContext,"输入金额");
                                            return;
                                        }
                                        dialog.dismiss();
                                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.shop_report_target_set(LoginUtil.getInfo("token"),
                                                LoginUtil.getInfo("uid"),priceEt.getText().toString(),shopDataFragment.getShop_id()),true);
                                    }
                                });
                            }
                        })
                        .setWidth(270)
                        .setOutCancel(false)
                        .show(getSupportFragmentManager());
                break;
        }
    }

}
