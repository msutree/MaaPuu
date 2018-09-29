package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.fragment.attectionchild.AttectionArticlerFragment;
import com.maapuu.mereca.fragment.attectionchild.AttectionHairStylistFragment;
import com.maapuu.mereca.fragment.attectionchild.AttectionShopFragment;
import com.maapuu.mereca.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2017/8/2.
 * 关注
 */

public class AttectionActivity extends BaseActivity {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_shop)
    TextView txtShop;
    @BindView(R.id.txt_hair_stylist)
    TextView txtHairStylist;
    @BindView(R.id.txt_articler)
    TextView txtArticler;

    private TextView[] tvs;

    private FragmentManager fragmentManager;
    private AttectionShopFragment shopFragment;
    private AttectionHairStylistFragment hairStylistFragment;
    private AttectionArticlerFragment articlerFragment;
    private int mId = 0;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_attection);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("关注");
        fragmentManager = getSupportFragmentManager();
        tvs = new TextView[]{txtShop,txtHairStylist,txtArticler};
        setHead(mId);
        addFragment(mId);
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

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_shop,R.id.txt_hair_stylist,R.id.txt_articler})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_shop:
                mId = 0;setHead(mId);addFragment(mId);
                break;
            case R.id.txt_hair_stylist:
                mId = 1;setHead(mId);addFragment(mId);
                break;
            case R.id.txt_articler:
                mId = 2;setHead(mId);addFragment(mId);
                break;
        }
    }

    // 隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }
        if (hairStylistFragment != null) {
            transaction.hide(hairStylistFragment);
        }
        if (articlerFragment != null) {
            transaction.hide(articlerFragment);
        }
    }

    private void addFragment(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (shopFragment == null) {
                    shopFragment = new AttectionShopFragment();
                    fragmentTransaction.add(R.id.fl_container, shopFragment);
                } else {
                    fragmentTransaction.show(shopFragment);
                }
                break;
            case 1:
                if (hairStylistFragment == null) {
                    hairStylistFragment = new AttectionHairStylistFragment();
                    fragmentTransaction.add(R.id.fl_container, hairStylistFragment);
                } else {
                    fragmentTransaction.show(hairStylistFragment);
                }
                break;
            case 2:
                if (articlerFragment == null) {
                    articlerFragment = new AttectionArticlerFragment();
                    fragmentTransaction.add(R.id.fl_container, articlerFragment);
                } else {
                    fragmentTransaction.show(articlerFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
