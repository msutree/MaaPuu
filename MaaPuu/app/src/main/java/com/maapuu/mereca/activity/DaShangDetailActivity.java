package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class DaShangDetailActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_left_1)
    TextView txtLeft1;
    @BindView(R.id.txt_title_1)
    TextView txtTitle1;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar1)
    View mToolbar1;
    @BindView(R.id.toolbar2)
    View mToolbar2;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager mLayoutManager;
    private List<String> list;
    private BaseRecyclerAdapter<String> adapter;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dashang_detail);
        ImmersionBar.with(DaShangDetailActivity.this).fitsSystemWindows(false).transparentStatusBar()
                .statusBarDarkFont(true,0f).init();
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtLeft1.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("打赏详情");txtTitle1.setText("打赏详情");
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            list.add("");
        }

        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new BaseRecyclerAdapter<String>(mContext,list,R.layout.layout_dashang_detail_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, String bean, int position, boolean isScrolling) {
                if(position == list.size() - 1){
                    holder.setVisible(R.id.line,false);
                    holder.setVisible(R.id.txt_is_zuijia,true);
                }else {
                    holder.setVisible(R.id.line,true);
                    holder.setVisible(R.id.txt_is_zuijia,false);
                }
            }
        };
        recyclerView.setAdapter(adapter);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0){//张开
                    ImmersionBar.with(DaShangDetailActivity.this).fitsSystemWindows(false).transparentStatusBar()
                            .statusBarDarkFont(true,0f).init();
                    mToolbar1.setVisibility(View.VISIBLE);
                    mToolbar2.setVisibility(View.GONE);
                    mToolbar2.setBackgroundColor(getResources().getColor(R.color.transparent));
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {//收缩
                    ImmersionBar.with(DaShangDetailActivity.this).fitsSystemWindows(false).transparentStatusBar()
                            .statusBarDarkFont(true,0f).init();
                    mToolbar1.setVisibility(View.GONE);
                    mToolbar2.setVisibility(View.VISIBLE);
                    mToolbar2.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    int alpha=255-Math.abs(verticalOffset);//Math.abs返回绝对值
                    Log.e("alpha",alpha+"");
//                    if(alpha<0){//收缩toolbar
//                        mToolbar1.setVisibility(View.GONE);
//                        mToolbar2.setVisibility(View.VISIBLE);
//                    }else{//张开toolbar
//                        mToolbar1.setVisibility(View.VISIBLE);
//                        mToolbar2.setVisibility(View.GONE);
//                    }
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_left_1})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
            case R.id.txt_left_1:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(DaShangDetailActivity.this).destroy();
    }
}
