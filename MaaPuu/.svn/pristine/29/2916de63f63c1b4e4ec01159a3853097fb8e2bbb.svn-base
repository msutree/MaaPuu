package com.maapuu.mereca.background.employee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.FixPagerAdapter;
import com.maapuu.mereca.background.employee.fragment.MyServiceFragment;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的服务
 * Created by Jia on 2018/3/05.
 */

public class MyServiceActivity extends BaseActivity {

    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;

    private String[] titles = {"正在服务", "已完成"};
    private List<BaseFragment> fragments;
    private Map<Integer, BaseFragment> mFragments = new HashMap();
    private FixPagerAdapter fixPagerAdapter;
    int selectedPosition = 0;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.em_activity_my_service);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("我的服务");
        selectedPosition = getIntent().getIntExtra("selectedPosition",0);

        initTab();
    }

    @Override
    public void initData() {
    }

    private void initTab() {
        selectedPosition =getIntent().getIntExtra("selectedPosition",0);
        fixPagerAdapter = new FixPagerAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(createFragment(i));
        }
        fixPagerAdapter.setTitles(titles);
        fixPagerAdapter.setFragments(fragments);
        vp.setAdapter(fixPagerAdapter);
        tabLayout.setupWithViewPager(vp);
        if(selectedPosition != 0 && selectedPosition < titles.length){
            tabLayout.getTabAt(selectedPosition).isSelected();
            vp.setCurrentItem(selectedPosition);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                createFragment(tab.getPosition()).loadData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        selectedPosition =intent.getIntExtra("selectedPosition",0);
        if(selectedPosition != 0 && selectedPosition < titles.length){
            tabLayout.getTabAt(selectedPosition).isSelected();
            vp.setCurrentItem(selectedPosition);
        }
    }

    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
        }
    }

    private BaseFragment createFragment(int index) {
        BaseFragment fragment = mFragments.get(index);
        // 如果之前没有创建, 创建新的Fragment
        if (fragment == null) {
            switch (index) {
                case 0:
                    fragment = MyServiceFragment.newInstance(0);
                    break;
                case 1:
                    fragment = MyServiceFragment.newInstance(1);
                    break;
            }
            // 把创建的Fragment 存起来
            mFragments.put(index, fragment);
        }
        return fragment;
    }

}
