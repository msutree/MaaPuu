package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.FixPagerAdapter;
import com.maapuu.mereca.background.shop.bean.ActBean;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.background.shop.fragment.MarketMealDoingFragment;
import com.maapuu.mereca.background.shop.fragment.MarketRedPacketsFragment;
import com.maapuu.mereca.background.shop.fragment.MarketVipCardFragment;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 营销活动
 * Created by Jia on 2018/3/8.
 */

public class MarketingDoingActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;

    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;
    @BindView(R.id.choose_shop_tv)
    TextView shopTv;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;

    private String[] titles = {"会员卡","套餐活动","红包"};
    private List<BaseFragment> fragments ;
    private Map<Integer, BaseFragment> mFragments = new HashMap();
    private FixPagerAdapter fixPagerAdapter ;

    int act_type = 1; //活动类型：1会员卡；2套餐活动；3红包
    String shop_id = "0";//店铺id，0表示全部店铺
    int page = 1;//第几页

    boolean isShowProgress = true;
    List<ShopBean> shopList;
    int tagPosition = 0;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_marketing_doing);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("营销活动");
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setTypeface(StringUtils.getFont(mContext));
        txtRight.setText("\ue69b");//&#xe69b;
        txtRight.setTextSize(17);

        initTab();
    }

    private void initTab() {
        fixPagerAdapter = new FixPagerAdapter(getSupportFragmentManager()) ;
        fragments = new ArrayList<>() ;
        for(int i = 0 ;i < titles.length ; i ++){
            fragments.add(createFragment(i)) ;
        }
        fixPagerAdapter.setTitles(titles);
        fixPagerAdapter.setFragments(fragments);
        vp.setAdapter(fixPagerAdapter);
        tabLayout.setupWithViewPager(vp);
        UIUtils.fitTab(tabLayout,titles.length,0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tagPosition = tab.getPosition();
                createFragment(tagPosition).loadData();
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
    public void initData() {
        getActList();
    }

    private void getActList() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_act_list_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        act_type,
                        shop_id,
                        page),isShowProgress);
    }

    private void setUI(ActBean bean) {
        if(bean.getShop_list() != null && bean.getShop_list().size()>0) shopList = bean.getShop_list();


    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.ao_choose_shop_lt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;

            case R.id.txt_right:
                //弹框添加活动
                ArrayList<String> cList = new ArrayList<>();
                cList.add("会员卡-会籍");
                cList.add("会员卡-项目卡");
                cList.add("会员卡-充值卡");
                cList.add("套餐活动");
                cList.add("红包-项目");
                cList.add("红包-商品");
                getAddDoingPicker(cList).show();

                break;

            case R.id.ao_choose_shop_lt:
                //选择商铺
                if(shopList != null && shopList.size()>0){
                    ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                        @Override
                        public void onChoose(ShopBean item) {
                            shopTv.setText(item.getShop_name());
                            shop_id = item.getShop_id();
                            for (BaseFragment ft:fragments) {
                                if(ft instanceof OnShopSelectListener){
                                    ((OnShopSelectListener)ft).onShopSelect(shop_id);
                                }
                            }
                        }
                    });
                    chooseShopFilter.createPopup();
                    chooseShopFilter.showAsDropDown(chooseShopLt);
                } else {
                    ToastUtil.show(mContext,"没有可供选择的商铺");
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                //活动类型：1会员卡；2套餐活动；3红包
                //刷新
                case 1:
                    createFragment(0).loadData();
                    break;

                case 2:
                    createFragment(1).loadData();
                    break;

                case 3:
                    createFragment(2).loadData();
                    break;
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    isShowProgress = false;
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            ActBean bean = FastJsonTools.getPerson(object.optString("result"), ActBean.class);
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
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }

    private OptionsPickerView getAddDoingPicker(final ArrayList<String> data) {

        OptionsPickerView pvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (data != null && data.size() > 0) {
                    String key = data.get(options1);
                    //活动类型：1会员卡；2套餐活动；3红包
                    switch (key){
                        case "会员卡-会籍":
                            it = new Intent(mContext,AddVipCardActivity.class);
                            it.putExtra("card_type",1);//card_type 会员类型：1会籍；2项目卡；3充值卡
                            startActivityForResult(it,1);
                            break;
                        case "会员卡-项目卡":
                            it = new Intent(mContext,AddVipCardActivity.class);
                            it.putExtra("card_type",2);//card_type 会员类型：1会籍；2项目卡；3充值卡
                            startActivityForResult(it,1);
                            break;
                        case "会员卡-充值卡":
                            it = new Intent(mContext,AddVipCardActivity.class);
                            it.putExtra("card_type",3);//card_type 会员类型：1会籍；2项目卡；3充值卡
                            startActivityForResult(it,1);
                            break;
                        case "套餐活动":
                            it = new Intent(mContext,AddTaoCanActivity.class);
                            startActivityForResult(it,2);
                            break;
                        case "红包-项目":
                            it = new Intent(mContext,AddHongBaoActivity.class);
                            it.putExtra("red_type","1");//红包类型:1项目红包；2商品红包
                            startActivityForResult(it,3);
                            break;
                        case "红包-商品":
                            it = new Intent(mContext,AddHongBaoActivity.class);
                            it.putExtra("red_type","2");//红包类型:1项目红包；2商品红包
                            startActivityForResult(it,3);
                            break;
                    }
                }
            }
        })
                .setSelectOptions(0)
                .setCancelColor(mContext.getResources().getColor(R.color.text_99))
                .setSubmitColor(mContext.getResources().getColor(R.color.main_color))
                //.setLabels("小时","分钟","")
                .isCenterLabel(true)
                .build();
        pvNoLinkOptions.setNPicker(data, null, null);

        return pvNoLinkOptions;
    }

    private BaseFragment createFragment(int index) {
        BaseFragment fragment = mFragments.get(index);
        // 如果之前没有创建, 创建新的Fragment
        if (fragment == null) {
            switch (index) {
                case 0:
                    fragment = new MarketVipCardFragment();
                    break;
                case 1:
                    fragment = new MarketMealDoingFragment();
                    break;
                case 2:
                    fragment = new MarketRedPacketsFragment();
                    break;
            }
            // 把创建的Fragment 存起来
            mFragments.put(index, fragment);
        }
        return fragment;
    }

    public interface OnShopSelectListener {
        void onShopSelect(String shop_id);
    }
}
