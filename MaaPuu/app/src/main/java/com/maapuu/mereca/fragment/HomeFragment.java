package com.maapuu.mereca.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.barlibrary.ImmersionBar;
import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.ChooseCityActivity;
import com.maapuu.mereca.activity.LoginActivity;
import com.maapuu.mereca.activity.SearchActivity;
import com.maapuu.mereca.activity.ShopListActivity;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.camera.CaptureActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.fragment.homechild.HomeCampaignsFragment;
import com.maapuu.mereca.fragment.homechild.HomeGoodsFragment_v2;
import com.maapuu.mereca.fragment.homechild.HomeIndexFragment;
import com.maapuu.mereca.fragment.homechild.HomeProjectFragment_v2;
import com.maapuu.mereca.fragment.homechild.HomeTeamFragment;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.JumpPermissionManagement;
import com.maapuu.mereca.util.LocationUtils;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dell on 2018/1/11.
 */

public class HomeFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks{
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.classics_header)
    ClassicsHeader classicsHeader;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_sanjiao)
    TextView txtSanjiao;
    @BindView(R.id.txt_sousuo)
    TextView txtSousuo;
    @BindView(R.id.txt_saoyisao)
    TextView txtSaoyisao;
    @BindView(R.id.txt_city)
    TextView txtCity;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.txt_index)
    TextView txtIndex;
    @BindView(R.id.txt_project)
    TextView txtProject;
    @BindView(R.id.txt_team)
    TextView txtTeam;
    @BindView(R.id.txt_campaigns)
    TextView txtCampaigns;
    @BindView(R.id.txt_goods)
    TextView txtGoods;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_shop)
    SimpleDraweeView ivShop;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.txt_shop_locate)
    TextView txtShopLocate;
    @BindView(R.id.txt_fans)
    TextView txtFans;
    @BindView(R.id.txt_attection)
    TextView txtAttection;
    @BindView(R.id.iv_shop_phone)
    ImageView ivShopPhone;

    private TextView[] tvs;

    public static HomeFragment homeFragment;
    private FragmentManager fragmentManager;
    private HomeIndexFragment indexFragment;
    private HomeProjectFragment_v2 projectFragment;
    private HomeTeamFragment teamFragment;
    private HomeCampaignsFragment campaignsFragment;
    private HomeGoodsFragment_v2 goodsFragment;

    private String locCity;
    private boolean isFirst = true;
    private boolean isRefresh = false;
    private String shop_phone;
    private String shop_id = "0";
    private String index_info;
    private String is_attention;
    private String attention_num;

    private int mTab = 0;
    private int mOffset = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar()
                .statusBarDarkFont(true,0f).init();
        homeFragment = this;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true,0f).init();
        }
    }

    @Override
    protected int setContentViewById() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View v) {
        txtSanjiao.setTypeface(StringUtils.getFont(mContext));
        txtSousuo.setTypeface(StringUtils.getFont(mContext));
        txtSaoyisao.setTypeface(StringUtils.getFont(mContext));
        ImmersionBar.with(this).titleBar(toolbar).init();
        classicsHeader.getLastUpdateText().setTextColor(getActivity().getResources().getColor(R.color.white));
        classicsHeader.getTitleText().setTextColor(getActivity().getResources().getColor(R.color.white));
        classicsHeader.setArrowResource(R.mipmap.refresh_arrow);//设置箭头资源

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivShopPhone.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setOval(0,0,view.getWidth(),view.getHeight());
                }
            });
        }

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                switch (mTab){
                    case 0:
                        initData();
                        break;
                    case 1:
                        projectFragment.refresh(shop_id);
                        break;
                    case 2:
                        teamFragment.refresh(shop_id);
                        break;
                    case 3:
                        campaignsFragment.refresh(shop_id);
                        break;
                    case 4:
                        goodsFragment.refresh(shop_id);
                        break;
                }
            }
        });
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset;
                ivBg.setTranslationY(mOffset);
            }
            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset;
                ivBg.setTranslationY(mOffset);
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
//                }
                if(verticalOffset != 0 ){
                    ivBg.setTranslationY(verticalOffset);
                }
                //200是appbar的高度
                if (Math.abs(verticalOffset) == DensityUtil.dp2px(180) - toolbar.getHeight()) {//关闭
                    toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                    llSearch.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_search_home_1));
                    collapsingToolbarLayout.setContentScrimResource(R.color.white);
                    txtCity.setTextColor(getActivity().getResources().getColor(R.color.text_33));
                    txtSanjiao.setTextColor(getActivity().getResources().getColor(R.color.text_33));
                    txtSousuo.setTextColor(getActivity().getResources().getColor(R.color.text_33));
                    txtSaoyisao.setTextColor(getActivity().getResources().getColor(R.color.text_33));
                } else {  //展开
                    toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
                    llSearch.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_search_home));
                    collapsingToolbarLayout.setContentScrimResource(R.color.transparent);
                    txtCity.setTextColor(getActivity().getResources().getColor(R.color.white));
                    txtSanjiao.setTextColor(getActivity().getResources().getColor(R.color.white));
                    txtSousuo.setTextColor(getActivity().getResources().getColor(R.color.white));
                    txtSaoyisao.setTextColor(getActivity().getResources().getColor(R.color.white));
                }
            }
        });
        tvs = new TextView[]{txtIndex,txtProject,txtTeam,txtCampaigns,txtGoods};
        setHead(0);
        fragmentManager = getChildFragmentManager();

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
        if (indexFragment != null) {
            transaction.hide(indexFragment);
        }
        if (projectFragment != null) {
            transaction.hide(projectFragment);
        }
        if (teamFragment != null) {
            transaction.hide(teamFragment);
        }
        if (campaignsFragment != null) {
            transaction.hide(campaignsFragment);
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
                if (indexFragment == null) {
                    indexFragment = new HomeIndexFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("index_info",index_info);
                    bundle.putString("shop_id",shop_id);
                    indexFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_container, indexFragment);
                } else {
                    indexFragment.refresh(index_info);
                    fragmentTransaction.show(indexFragment);
                }
                break;
            case 1:
                if (projectFragment == null) {
                    projectFragment = new HomeProjectFragment_v2();
                    Bundle bundle=new Bundle();
                    bundle.putString("shop_id",shop_id);
                    projectFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_container, projectFragment);
                } else {
                    projectFragment.refresh(shop_id);
                    fragmentTransaction.show(projectFragment);
                }
                break;
            case 2:
                if (teamFragment == null) {
                    teamFragment = new HomeTeamFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("shop_id",shop_id);
                    teamFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_container, teamFragment);
                } else {
                    teamFragment.refresh(shop_id);
                    fragmentTransaction.show(teamFragment);
                }
                break;
            case 3:
                if (campaignsFragment == null) {
                    campaignsFragment = new HomeCampaignsFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("shop_id",shop_id);
                    campaignsFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_container, campaignsFragment);
                } else {
                    campaignsFragment.refresh(shop_id);
                    fragmentTransaction.show(campaignsFragment);
                }
                break;
            case 4:
                if (goodsFragment == null) {
                    goodsFragment = new HomeGoodsFragment_v2();
                    Bundle bundle=new Bundle();
                    bundle.putString("shop_id",shop_id);
                    goodsFragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.fl_container, goodsFragment);
                } else {
                    goodsFragment.refresh(shop_id);
                    fragmentTransaction.show(goodsFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void initData() {
//        initLocation();
        init();
    }

    /**
     * 定位
     */
    private void initLocation() {
        new LocationUtils(getActivity(), new LocationUtils.Callback() {
            @Override
            public void click(AMapLocation aMapLocation) {
                if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                    AppConfig.LAT = aMapLocation.getLatitude()+"";
                    AppConfig.LNG = aMapLocation.getLongitude()+"";
                    locCity = aMapLocation.getCity();
                }else {
                    locCity = "未开启";
                    AppConfig.LAT = "0";
                    AppConfig.LNG = "0";
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:" + aMapLocation.getErrorCode()
                            + ", errInfo:" + aMapLocation.getErrorInfo());
                }
                init();
            }
        });
    }

    private void init() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.mem_home_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),LoginUtil.getCityID(mContext),
                AppConfig.CITY,AppConfig.LAT,AppConfig.LNG,LoginUtil.getInfo("unlogin_shop_id")),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                refreshLayout.finishRefresh();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        if(resultObj.has("city_data") && !StringUtils.isEmpty(resultObj.optString("city_data"))){
                            JSONObject cityObj = resultObj.optJSONObject("city_data");
                            LoginUtil.putCityID(mContext,cityObj.optString("id"));
                            LoginUtil.putCityName(mContext,cityObj.optString("name"));
                            txtCity.setText(LoginUtil.getCityName(mContext));
                        }
                        JSONObject shopObject = resultObj.optJSONObject("shop_data");
                        index_info = resultObj.optString("shop_data");
                        if(shopObject.has("info") && !StringUtils.isEmpty(shopObject.optString("info")) && !(shopObject.opt("info") instanceof Boolean)){
                            JSONObject infoObject = shopObject.optJSONObject("info");
                            LoginUtil.setInfo("shop_id",infoObject.optString("shop_id"));
                            shop_id = infoObject.optString("shop_id");
                            txtShopName.setText(infoObject.optString("shop_name"));
                            txtShopLocate.setText(infoObject.optString("city_name"));
                            attention_num = StringUtils.isEmpty(infoObject.optString("attention_num"))?"0":infoObject.optString("attention_num");
                            txtFans.setText("粉丝"+infoObject.optString("attention_num"));
                            shop_phone = infoObject.optString("shop_tel");
                            is_attention = infoObject.optString("is_attention");
                            txtAttection.setText(infoObject.optString("is_attention").equals("1")?"已关注":"关注");
                            if(!StringUtils.isEmpty(infoObject.optString("shop_cover"))){
                                new DownloadImageTask().execute(infoObject.optString("shop_cover")) ;
                            }
                            if(!StringUtils.isEmpty(infoObject.optString("shop_logo"))){
                                ivShop.setImageURI(Uri.parse(infoObject.optString("shop_logo")));
                            }
                        }
                        if(isFirst){
                            isFirst = false;
                            if(resultObj.optInt("is_open") == 1 && !AppConfig.CITY.contains(LoginUtil.getCityName(mContext))){
                                new AlertView(null, "定位到您在"+AppConfig.CITY+"\n是否切换至该城市？", "取消",null, new String[]{"切换"}, getActivity(),
                                        AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Object o, int position) {
                                        if (position == 0) {
                                            LoginUtil.putCityID(mContext,"");
                                            LoginUtil.putCityName(mContext,"");
                                            init();
                                        }
                                    }
                                }).show();
                            }
                        }
                        addFragment(mTab);
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(is_attention.equals("1")){
                            is_attention = "0";
//                            ToastUtil.show(mContext,"取消关注成功");
                            txtAttection.setText("关注");
                            attention_num = String.valueOf(Integer.parseInt(attention_num)-1);
                            txtFans.setText("粉丝"+attention_num);
                        }else {
                            is_attention = "1";
//                            ToastUtil.show(mContext,"关注成功");
                            txtAttection.setText("已关注");
                            attention_num = String.valueOf(Integer.parseInt(attention_num)+1);
                            txtFans.setText("粉丝"+attention_num);
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                refreshLayout.finishRefresh();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
            case 888:
                refreshLayout.finishRefresh();
                break;
            case 999:
                isRefresh = true;
                setHead(0);mTab = 0;addFragment(mTab);
                initData();
                break;
        }
        return false;
    }

    //将网络图片转为Drawable
    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
        protected Drawable doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }
        protected void onPostExecute(Drawable result) {
            if(result == null){
                ivBg.setBackgroundColor(getActivity().getResources().getColor(R.color.text_dd));
            }else {
                ivBg.setBackground(result);
            }
        }
    }

    private Drawable loadImageFromNetwork(String imageUrl) {
        Drawable drawable = null;
        try {// 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        return drawable ;
    }

    @Override
    @OnClick({R.id.ll_city,R.id.ll_search,R.id.txt_saoyisao,R.id.txt_index,R.id.txt_project,
            R.id.txt_team, R.id.txt_campaigns,R.id.txt_goods,R.id.iv_shop,R.id.iv_shop_phone,R.id.txt_attection})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_city:
                it = new Intent(mContext, ChooseCityActivity.class);
                startActivityForResult(it,AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.ll_search:
                it = new Intent(mContext, SearchActivity.class);
                startActivity(it);
                break;
            case R.id.txt_saoyisao:
                if(LoginUtil.getLoginState()){
                    requestLocationPermission();
                }else {
                    it = new Intent(mContext, LoginActivity.class);
                    startActivity(it);
                }
                break;
            case R.id.txt_index:
                setHead(0);mTab = 0;addFragment(mTab);
                break;
            case R.id.txt_project:
                setHead(1);mTab = 1;addFragment(mTab);
                break;
            case R.id.txt_team:
                setHead(2);mTab = 2;addFragment(mTab);
                break;
            case R.id.txt_campaigns:
                setHead(3);mTab = 3;addFragment(mTab);
                break;
            case R.id.txt_goods:
                setHead(4);mTab = 4;addFragment(mTab);
                break;
            case R.id.iv_shop:
                it = new Intent(mContext, ShopListActivity.class);
                startActivity(it);
                break;
            case R.id.txt_attection:
                if(LoginUtil.getLoginState()){
                    if(StringUtils.isEmpty(is_attention)){
                        return;
                    }
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.attention_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),"1",
                            shop_id,is_attention.equals("1")?"0":"1"),true);
                }else {
                    it = new Intent(mContext, LoginActivity.class);
                    startActivity(it);
                }
                break;
            case R.id.iv_shop_phone:
                if(!StringUtils.isEmpty(shop_phone)){
                    new AlertView(null, "TEL:"+shop_phone, "取消", null, new String[]{"拨打"}, mContext,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + shop_phone));
                                startActivity(intent);
                            }
                        }
                    }).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                setHead(0);mTab = 0;addFragment(mTab);
                initData();
                break;
        }
    }

    @AfterPermissionGranted(AppConfig.PERMISSIONCODE)
    private void requestLocationPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.CAMERA)) {
            it = new Intent(mContext, CaptureActivity.class);
            it.putExtra("isAddFriends",false);
            startActivity(it);
        }else {
            EasyPermissions.requestPermissions(this,"您需要开启相机权限", AppConfig.PERMISSIONCODE,
                    Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        new AlertView(null, "使用扫一扫功能，需打开相机权限？", "取消", null, new String[]{"确定"}, mContext,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    JumpPermissionManagement.GoToSetting(getActivity());
                }
            }
        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,HomeFragment.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeFragment = null;
    }
}
