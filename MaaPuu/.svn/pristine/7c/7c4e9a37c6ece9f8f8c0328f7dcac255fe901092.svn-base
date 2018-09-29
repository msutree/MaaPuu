package com.maapuu.mereca.background.employee.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.bigkoo.alertview.AlertView;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.employee.adapter.SortClientAdapter;
import com.maapuu.mereca.background.employee.bean.CustomBean;
import com.maapuu.mereca.background.employee.bean.CustomDataBean;
import com.maapuu.mereca.background.shop.dialog.ChooseShopFilter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.ShopBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.maapuu.mereca.view.CircleImgView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 工作狂 客户
 * Created by Jia on 2018/2/28.
 */

public class WorkClientFragment extends BaseFragment{

    @BindView(R.id.search_ic)
    TextView searchIc;
    @BindView(R.id.search)
    EditText searchEt;

    @BindView(R.id.ao_choose_shop_lt)
    LinearLayout chooseShopLt;// 工作狂不显示   我的店铺 显示
    @BindView(R.id.choose_shop_tv)
    TextView shopTv;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FullyLinearLayoutManager mLayoutManager;
    private BaseRecyclerAdapter<CustomBean> adapter;

    private SortClientAdapter adapter2;

    int is_admin = 0;//是否我的店铺。0为工作狂，1为我的店铺
    String shop_id = "0";//我的店铺里面需要店铺过滤，工作狂里面不需要店铺过滤。店铺id，全部店铺传0
    int page = 1;//第几页
    boolean isShowProgress = true;
    String keywords = "";
    List<ShopBean> shopList;

    public static WorkClientFragment newInstance(int is_admin){
        WorkClientFragment fragment = new WorkClientFragment();
        Bundle args = new Bundle();
        args.putInt("is_admin",is_admin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        if(bundle != null){
            is_admin = bundle.getInt("is_admin",0);
        }
    }

    @Override
    protected int setContentViewById() {
        return R.layout.em_fragment_work_client;
    }

    @Override
    protected void initView(final View v) {
        searchIc.setTypeface(StringUtils.getFont(mContext));
        chooseShopLt.setVisibility(is_admin==1?View.VISIBLE:View.GONE);

        mLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,
                UIUtils.dip2px(mContext,1),
                getResources().getColor(R.color.background)));

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getCustomList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                page++;
                getCustomList(true);
            }
        });

        adapter = new BaseRecyclerAdapter<CustomBean>(mContext, R.layout.em_item_client_new) {
            @Override
            public void convert(BaseRecyclerHolder holder, final CustomBean bean, int position, boolean isScrolling) {
                CircleImgView avatarIv = holder.getView(R.id.avatar);
                UIUtils.loadIcon(mContext,bean.getAvatar(),avatarIv);
                holder.setText(R.id.nick_name,bean.getNick_name());
                holder.setText(R.id.pay_time,"最后消费时间："+bean.getPay_time());
                //性别：1男；2女；0未知
                ImageView sexTv = holder.getView(R.id.sex);
                if(bean.getSex() == 1){
                    sexTv.setVisibility(View.VISIBLE);
                    sexTv.setImageResource(R.mipmap.nanbiao);//女 &#xe694;   男&#xe695;
                } else if(bean.getSex() == 2){
                    sexTv.setVisibility(View.VISIBLE);
                    sexTv.setImageResource(R.mipmap.nvbiaoji);//女 &#xe694;   男&#xe695;
                } else {
                    sexTv.setVisibility(View.GONE);
                }

                //设置背景  黑灰色  橙色
                LinearLayout tagLt = holder.getView(R.id.c_tag_lt);
                TextView tagImg = holder.getView(R.id.c_tag_img);
                TextView tagTxt = holder.getView(R.id.c_tag_txt);

                TextView tel = holder.getView(R.id.c_tel);
                TextView msg = holder.getView(R.id.c_msg);

                tagLt.setVisibility(View.GONE);//默认隐藏
                tel.setVisibility(View.GONE);//默认隐藏
                //生日
//                if(bean.getIs_birthday() == 1){
//                    tagLt.setVisibility(View.VISIBLE);
//                    tagLt.setBackgroundResource(R.drawable.bg_solid_black_grey_radius20);
//                    tagImg.setTypeface(StringUtils.getFont(mContext));
//                    tagImg.setText("\ue697");//预约 &#xe699;   生日 &#xe697;
//                    tagTxt.setText("生日");
//
//                    tel.setVisibility(View.VISIBLE);
//                    tel.setTypeface(StringUtils.getFont(mContext));
//                }

                // 预约
                if(bean.getIs_appoint() == 1){
                    tagLt.setVisibility(View.VISIBLE);
                    tagLt.setBackgroundResource(R.drawable.bg_solid_black_grey_radius20);
                    tagImg.setTypeface(StringUtils.getFont(mContext));
                    tagImg.setText("\ue699");//预约 &#xe699;   生日 &#xe697;
                    tagTxt.setText("预约");
                    tel.setVisibility(View.VISIBLE);
                    tel.setTypeface(StringUtils.getFont(mContext));
                }else {
                    if(bean.getIs_birthday() == 1){
                        tagLt.setVisibility(View.VISIBLE);
                        tagLt.setBackgroundResource(R.drawable.bg_solid_orange_radius20);
                        tagImg.setTypeface(StringUtils.getFont(mContext));
                        tagImg.setText("\ue697");//预约 &#xe699;   生日 &#xe697;
                        tagTxt.setText("生日");

                        tel.setText("\ue697");
                        tel.setVisibility(View.VISIBLE);
                        tel.setTypeface(StringUtils.getFont(mContext));
                    }else {
                        tagLt.setVisibility(View.GONE);
                    }
                }

                tel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(bean.getIs_appoint() == 1){
                            new AlertView(null, "TEL:"+bean.getPhone(), "取消", null, new String[]{"拨打"}, mContext,
                                    AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {
                                    if (position == 0) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:" + bean.getPhone()));
                                        startActivity(intent);
                                    }
                                }
                            }).show();
                        }else {
                            if(bean.getIs_birthday() == 1){
                                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.phone_birthday_set(LoginUtil.getInfo("token"),
                                        LoginUtil.getInfo("uid"),bean.getPhone()),true);
                            }
                        }
                    }
                });
                msg.setTypeface(StringUtils.getFont(mContext));
                msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(AppConfig.mIMKit == null){
                            AppConfig.mIMKit = YWAPI.getIMKitInstance(LoginUtil.getInfo("uid"), AppConfig.APP_KEY);
                        }
                        Intent chat = AppConfig.mIMKit.getChattingActivityIntent(bean.getUid());
                        chat.putExtra("uid",bean.getUid());
                        startActivity(chat);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    keywords = searchEt.getText().toString().trim();
                    page = 1;
                    getCustomList(true);

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        getCustomList(false);
    }

    private void getCustomList(boolean bool) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,
                UrlUtils.s_custom_list_get(LoginUtil.getInfo("token"), LoginUtil.getInfo("uid"),
                        is_admin+"",shop_id,keywords,page),bool);
    }

    private void setUI(CustomDataBean bean) {
        if(bean.getShop_list() != null && bean.getShop_list().size()>0) shopList = bean.getShop_list();

        List<CustomBean> list = bean.getCustom_data();
        if(page == 1) adapter.clear();
        adapter.addList(list);
        if(page > 1){
            if((list ==null || list.size()==0)){
                page--;
                ToastUtil.show(mContext,"暂无更多数据");
            } else {
                recyclerView.smoothScrollToPosition(adapter.getList().size()-list.size()-1);
            }
        }
    }

    @Override
    @OnClick({R.id.ao_choose_shop_lt})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ao_choose_shop_lt:
                //选择商铺
                if(shopList != null && shopList.size()>0){
                    ChooseShopFilter chooseShopFilter = new ChooseShopFilter(mContext, shopList, new ChooseShopFilter.ChooseCall() {
                        @Override
                        public void onChoose(ShopBean item) {
                            shopTv.setText(item.getShop_name());
                            shop_id = item.getShop_id();
                            page = 1;
                            getCustomList(true);
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
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    isShowProgress = false;
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            CustomDataBean bean = FastJsonTools.getPerson(object.optString("result"), CustomDataBean.class);
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
            case HttpModeBase.HTTP_REQUESTCODE_2:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext, "生日祝福已发送");
                    } else {
                        ToastUtil.show(mContext, object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpModeBase.HTTP_ERROR:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext, result_e);
                break;
        }
        return false;
    }

}
