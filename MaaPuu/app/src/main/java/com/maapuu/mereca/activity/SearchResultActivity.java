package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.fragment.searchchild.SearchGoodsFragment;
import com.maapuu.mereca.fragment.searchchild.SearchHairStylistFragment;
import com.maapuu.mereca.fragment.searchchild.SearchProjectFragment;
import com.maapuu.mereca.fragment.searchchild.SearchShopFragment;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.SearchEditText;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2017/8/2.
 * 搜索结果
 */

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.et_search)
    SearchEditText etSearch;
//    @BindView(R.id.txt_doctor)
    public static TextView txtShop;
//    @BindView(R.id.txt_taocan)
    public static TextView txtHairStylist;
//    @BindView(R.id.txt_danxiang)
    public static TextView txtProject;
//    @BindView(R.id.txt_cart_num)
    public static TextView txtGoods;

    private TextView[] tvs;

    private FragmentManager fragmentManager;
    private SearchShopFragment shopFragment;
    private SearchHairStylistFragment hairStylistFragment;
    private SearchProjectFragment projectFragment;
    private SearchGoodsFragment goodsFragment;
    private String keyword = "";
    private int mId = 0;
    public static SearchResultActivity activity;

    private List<String> historyList;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_result);
        activity = this;
    }

    @Override
    public void initView() {
        fragmentManager = getSupportFragmentManager();
        etSearch = (SearchEditText)findViewById(R.id.et_search);
        txtShop = (TextView) findViewById(R.id.txt_shop);
        txtHairStylist = (TextView) findViewById(R.id.txt_hair_stylist);
        txtProject = (TextView) findViewById(R.id.txt_project);
        txtGoods = (TextView) findViewById(R.id.txt_goods);
        keyword = getIntent().getStringExtra("keyword");
        etSearch.setText(keyword);
        etSearch.setSelection(keyword.length());
        tvs = new TextView[]{txtShop,txtHairStylist,txtProject,txtGoods};
        setHead(mId);
        addFragment(mId);

        historyList = new ArrayList<>();

        if(!StringUtils.isEmpty(LoginUtil.getInfo("historylist"))){
            historyList = FastJsonTools.getPersons(LoginUtil.getInfo("historylist"),String.class);
        }

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(etSearch.getText().toString().isEmpty() || etSearch.getText().toString().equals("")){
                        ToastUtil.show(mContext, "请输入搜索关键字");
                    }else {
                        try {
                            JSONArray array = new JSONArray();
                            array.put(0,etSearch.getText().toString());
                            if(historyList != null && historyList.size()>0){
                                for (int i = 0 ;i < historyList.size(); i++){
                                    if(historyList.get(i).equals(etSearch.getText().toString())){
                                        historyList.remove(i);
                                    }
                                    array.put(i+1,historyList.get(i));
                                }
                            }
                            LoginUtil.setInfo("historylist",array.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        switch (mId){
                            case 0:
                                if(shopFragment != null){
                                    shopFragment.refresh(StringUtils.isEmpty(etSearch.getText().toString())?"":etSearch.getText().toString());
                                }
                                break;
                            case 1:
                                if(hairStylistFragment != null){
                                    hairStylistFragment.refresh(StringUtils.isEmpty(etSearch.getText().toString())?"":etSearch.getText().toString());
                                }
                                break;
                            case 2:
                                if(projectFragment != null){
                                    projectFragment.refresh(StringUtils.isEmpty(etSearch.getText().toString())?"":etSearch.getText().toString());
                                }
                                break;
                            case 3:
                                if(goodsFragment != null){
                                    goodsFragment.refresh(StringUtils.isEmpty(etSearch.getText().toString())?"":etSearch.getText().toString());
                                }
                                break;
                        }
                    }
                }
                return false;
            }
        });
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

    public String getKeyword(){
        return keyword;
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_back,R.id.txt_shop,R.id.txt_hair_stylist,R.id.txt_project,R.id.txt_goods})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_back:
                finish();
                break;
            case R.id.txt_shop:
                mId = 0;setHead(mId);addFragment(mId);
                break;
            case R.id.txt_hair_stylist:
                mId = 1;setHead(mId);addFragment(mId);
                break;
            case R.id.txt_project:
                mId = 2;setHead(mId);addFragment(mId);
                break;
            case R.id.txt_goods:
                mId = 3;setHead(mId);addFragment(mId);
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
        if (projectFragment != null) {
            transaction.hide(projectFragment);
        }
        if (goodsFragment != null) {
            transaction.hide(goodsFragment);
        }
    }

    private void addFragment(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (shopFragment == null) {
                    shopFragment = new SearchShopFragment();
                    fragmentTransaction.add(R.id.fl_container, shopFragment);
                } else {
                    fragmentTransaction.show(shopFragment);
                    shopFragment.refresh(StringUtils.isEmpty(etSearch.getText().toString())?"":etSearch.getText().toString());
                }
                break;
            case 1:
                if (hairStylistFragment == null) {
                    hairStylistFragment = new SearchHairStylistFragment();
                    fragmentTransaction.add(R.id.fl_container, hairStylistFragment);
                } else {
                    fragmentTransaction.show(hairStylistFragment);
                    hairStylistFragment.refresh(StringUtils.isEmpty(etSearch.getText().toString())?"":etSearch.getText().toString());
                }
                break;
            case 2:
                if (projectFragment == null) {
                    projectFragment = new SearchProjectFragment();
                    fragmentTransaction.add(R.id.fl_container, projectFragment);
                } else {
                    fragmentTransaction.show(projectFragment);
                    projectFragment.refresh(StringUtils.isEmpty(etSearch.getText().toString())?"":etSearch.getText().toString());
                }
                break;
            case 3:
                if (goodsFragment == null) {
                    goodsFragment = new SearchGoodsFragment();
                    fragmentTransaction.add(R.id.fl_container, goodsFragment);
                } else {
                    fragmentTransaction.show(goodsFragment);
                    goodsFragment.refresh(StringUtils.isEmpty(etSearch.getText().toString())?"":etSearch.getText().toString());
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
