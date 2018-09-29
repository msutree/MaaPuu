package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.fragment.DecorateBannerFragment;
import com.maapuu.mereca.background.shop.fragment.DecorateHairstylistFragment;
import com.maapuu.mereca.background.shop.fragment.DecorateHotProjectFragment;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.util.StringUtils;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 店铺装饰
 * Created by Jia on 2018/3/13.
 */

public class ShopDecorateActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.txt_tab_1)
    TextView txtTab1;
    @BindView(R.id.txt_tab_2)
    TextView txtTab2;
    @BindView(R.id.txt_tab_3)
    TextView txtTab3;

    private TextView[] tvs;

    private FragmentManager fragmentManager;
    DecorateBannerFragment bannerFragment;
    DecorateHairstylistFragment hairstylistFragment;
    DecorateHotProjectFragment hotProjectFragment;

    private int tabPosition = 0;
    private int type = -1;
    private boolean refresh1 = false;
    private boolean refresh2 = false;
    private boolean refresh3 = false;

    private String shop_id;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_shop_decorate);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("店铺装饰");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight.setText("\ue69b");//&#xe69b;
        txtRight.setTextSize(17);
        shop_id = getIntent().getStringExtra("shop_id");

        fragmentManager = getSupportFragmentManager();
        tvs = new TextView[]{txtTab1, txtTab2, txtTab3};
        setHead(tabPosition);
        addFragment(tabPosition);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tabPosition = intent.getIntExtra("tabPosition",0);
        setHead(tabPosition);
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_tab_1,R.id.txt_tab_2,R.id.txt_tab_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right://弹框添加
                showAddDialog();
                break;
            case R.id.txt_tab_1:
                tabPosition = 0;setHead(tabPosition);addFragment(tabPosition);
                break;
            case R.id.txt_tab_2:
                tabPosition = 1;setHead(tabPosition);addFragment(tabPosition);
                break;
            case R.id.txt_tab_3:
                tabPosition = 2;setHead(tabPosition);addFragment(tabPosition);
                break;
        }
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
        if (bannerFragment != null) {
            transaction.hide(bannerFragment);
        }
        if (hairstylistFragment != null) {
            transaction.hide(hairstylistFragment);
        }
        if (hotProjectFragment != null) {
            transaction.hide(hotProjectFragment);
        }
    }

    private void addFragment(int i) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (i) {
            case 0:
                if (bannerFragment == null) {
                    bannerFragment = new DecorateBannerFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("shop_id",shop_id);
                    bannerFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.sd_fl_container, bannerFragment);
                } else {
                    if(refresh1){
                        bannerFragment.refresh();
                    }
                    fragmentTransaction.show(bannerFragment);
                }
                break;
            case 1:
                if (hairstylistFragment == null) {
                    hairstylistFragment = new DecorateHairstylistFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("shop_id",shop_id);
                    hairstylistFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.sd_fl_container, hairstylistFragment);
                } else {
                    if(refresh2){
                        hairstylistFragment.refresh();
                    }
                    fragmentTransaction.show(hairstylistFragment);
                }
                break;
            case 2:
                if (hotProjectFragment == null) {
                    hotProjectFragment = new DecorateHotProjectFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("shop_id",shop_id);
                    hotProjectFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.sd_fl_container, hotProjectFragment);
                } else {
                    if(refresh3){
                        hotProjectFragment.refresh();
                    }
                    fragmentTransaction.show(hotProjectFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void showAddDialog() {
        final List<String> list = new ArrayList<>();
        list.add("轮播图");
        list.add("发型师");
        list.add("热门项目");

        NiceDialog.init().setLayoutId(R.layout.pop_bottom_menu)
                .setConvertListener(new ViewConvertListener() {

                    @Override
                    public void convertView(final ViewHolder holder, final BaseNiceDialog dialog) {
                        TextView cancelTv = holder.getView(R.id.pop_cancel_tv);
                        cancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        RecyclerView rv = holder.getView(R.id.pop_rv);
                        BaseRecyclerAdapter<String> adapter = new BaseRecyclerAdapter<String>(mContext,list,R.layout.pop_item_bottom_menu) {
                            @Override
                            public void convert(BaseRecyclerHolder baseHolder, final String item, int position, boolean isScrolling) {
                                final TextView menuTv = baseHolder.getView(R.id.bm_title);
                                menuTv.setText(item);
                                menuTv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                       switch (item){
                                           case "轮播图":
                                               type = 0;
                                               it = new Intent(mContext,AddBannerActivity.class);
                                               it.putExtra("shop_id",shop_id);
                                               startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                                               break;
                                           case "发型师":
                                               type = 1;
                                               it = new Intent(mContext,AddHairstylistActivity.class);
                                               it.putExtra("isAdd",true);
                                               it.putExtra("shop_id",shop_id);
                                               startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                                               break;
                                           case "热门项目":
                                               type = 2;
                                               it = new Intent(mContext,DecorateAddProjectActivity.class);
                                               it.putExtra("shop_id",shop_id);
                                               startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                                               break;
                                       }
                                       dialog.dismiss();
                                    }
                                });
                            }
                        };
                        rv.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,2,
                                getResources().getColor(R.color.background)));
                        rv.setLayoutManager(new LinearLayoutManager(mContext));
                        rv.setAdapter(adapter);
                    }
                })
                .setOutCancel(true).setShowBottom(true)
                //.setHeight(270)
                .show(getSupportFragmentManager());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == AppConfig.ACTIVITY_RESULTCODE){
            switch (tabPosition){
                case 0:
                    if(type == 0){
                        bannerFragment.refresh();
                    }else if(type == 1){
                        refresh2 = true;
                    }else if(type == 2){
                        refresh3 = true;
                    }
                    break;
                case 1:
                    if(type == 0){
                        refresh1 = true;
                    }else if(type == 1){
                        hairstylistFragment.refresh();
                    }else if(type == 2){
                        refresh3 = true;
                    }
                    break;
                case 2:
                    if(type == 0){
                        refresh1 = true;
                    }else if(type == 1){
                        refresh2 = true;
                    }else if(type == 2){
                        hotProjectFragment.refresh();
                    }
                    break;
            }
        }
    }
}
