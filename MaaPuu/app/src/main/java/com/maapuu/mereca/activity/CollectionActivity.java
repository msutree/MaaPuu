package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.fragment.collectionchild.CollectionArticleFragment;
import com.maapuu.mereca.fragment.collectionchild.CollectionGoodsFragment;
import com.maapuu.mereca.fragment.collectionchild.CollectionProjectFragment;
import com.maapuu.mereca.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class CollectionActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_project)
    TextView txtProject;
    @BindView(R.id.txt_goods)
    TextView txtGoods;
    @BindView(R.id.txt_article)
    TextView txtArticle;

    private TextView[] tvs;

    private FragmentManager fragmentManager;
    private CollectionProjectFragment projectFragment;
    private CollectionGoodsFragment goodsFragment;
    private CollectionArticleFragment articleFragment;
    private int mId = 0;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collection);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("收藏");
        fragmentManager = getSupportFragmentManager();
        tvs = new TextView[]{txtProject,txtGoods,txtArticle};
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
    @OnClick({R.id.txt_left,R.id.rl_cart,R.id.txt_project,R.id.txt_goods,R.id.txt_article})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.rl_cart:
                it = new Intent(mContext,ShoppingCartActivity.class);
                startActivity(it);
                break;
            case R.id.txt_project:
                mId = 0;setHead(mId);addFragment(mId);
                break;
            case R.id.txt_goods:
                mId = 1;setHead(mId);addFragment(mId);
                break;
            case R.id.txt_article:
                mId = 2;setHead(mId);addFragment(mId);
                break;
        }
    }

    // 隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {
        if (projectFragment != null) {
            transaction.hide(projectFragment);
        }
        if (goodsFragment != null) {
            transaction.hide(goodsFragment);
        }
        if (articleFragment != null) {
            transaction.hide(articleFragment);
        }
    }

    private void addFragment(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (projectFragment == null) {
                    projectFragment = new CollectionProjectFragment();
                    fragmentTransaction.add(R.id.fl_container, projectFragment);
                } else {
                    fragmentTransaction.show(projectFragment);
                }
                break;
            case 1:
                if (goodsFragment == null) {
                    goodsFragment = new CollectionGoodsFragment();
                    fragmentTransaction.add(R.id.fl_container, goodsFragment);
                } else {
                    fragmentTransaction.show(goodsFragment);
                }
                break;
            case 2:
                if (articleFragment == null) {
                    articleFragment = new CollectionArticleFragment();
                    fragmentTransaction.add(R.id.fl_container, articleFragment);
                } else {
                    fragmentTransaction.show(articleFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
