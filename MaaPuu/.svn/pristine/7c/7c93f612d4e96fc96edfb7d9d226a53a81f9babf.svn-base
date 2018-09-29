package com.maapuu.mereca.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.barlibrary.ImmersionBar;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.GoodsCommentRecyclerAdapter;
import com.maapuu.mereca.adapter.PersonalHomepageDtRecyclerAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.EvlBean;
import com.maapuu.mereca.bean.MoBean;
import com.maapuu.mereca.bean.MoCommentBean;
import com.maapuu.mereca.bean.PraiseBean;
import com.maapuu.mereca.bean.ShareBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.ShareUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhouwei.blurlibrary.EasyBlur;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class TeamDetailActivity extends BaseActivity {
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
    @BindView(R.id.txt_left_1)
    TextView txtLeft1;
    @BindView(R.id.txt_title_1)
    TextView txtTitle1;
    @BindView(R.id.txt_right_1)
    TextView txtRight1;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.txt_zuopin)
    TextView txtZuopin;
    @BindView(R.id.txt_koubei)
    TextView txtKouBei;

    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    @BindView(R.id.iv_hair_label)
    ImageView ivHairLabel;
    @BindView(R.id.txt_staff_intro)
    TextView txtStaffIntro;
    @BindView(R.id.txt_post_name)
    TextView txtPostName;
    @BindView(R.id.txt_fans_num)
    TextView txtFansNum;
    @BindView(R.id.txt_appoint_num)
    TextView txtAppointNum;
    @BindView(R.id.txt_attection)
    TextView txtAttection;
    @BindView(R.id.ll_has)
    LinearLayout llHas;
    @BindView(R.id.txt_empty_label)
    TextView txtEmptyLabel;

    private LinearLayoutManager mLayoutManager;
    private List<MoBean> list;
    private List<EvlBean> list1;
    private PersonalHomepageDtRecyclerAdapter adapter;
    private GoodsCommentRecyclerAdapter adapter1;
    private int page = 1;
    private int page1 = 1;
    private boolean isLeft = true;

    private String staff_id;
    private int zanPos = -1;
    private int comPos = -1;
    private int plPos = -1,plPos1 = -1;
    private boolean isFirst = true; //是否第一次点击口碑
    private String is_attention;
    private String uid;
    private Bitmap bitmap = null;
    private int zanPos1 = -1;

    private int mOffset = 0;
    private ShareBean shareBean;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_team_detail);
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar().titleBar(toolbar).init();
        txtLeft1.setTypeface(StringUtils.getFont(mContext));
        txtRight1.setVisibility(View.VISIBLE);
        txtRight1.setText("\ue616");
        txtRight1.setTextSize(16);
        txtRight1.setTypeface(StringUtils.getFont(mContext));
        txtZuopin.setSelected(true);
        staff_id = getIntent().getStringExtra("staff_id");
//        staff_id = "1";

        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,20
                ,getResources().getColor(R.color.background)));

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        classicsHeader.getLastUpdateText().setTextColor(getResources().getColor(R.color.white));
        classicsHeader.getTitleText().setTextColor(getResources().getColor(R.color.white));
        classicsHeader.setArrowResource(R.mipmap.refresh_arrow);//设置箭头资源
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(isLeft){
                    page = 1;
                    initData(page);
                }else {
                    page1 = 1;
                    initData1(page1);
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                if(isLeft){
                    initData(page);
                }else {
                    initData1(page1);
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
//                mOffset = offset / 2;
                mOffset = offset;
                ivBg.setTranslationY(mOffset);
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset != 0){
                    ivBg.setTranslationY(verticalOffset);
                }
                //200是appbar的高度
                if (Math.abs(verticalOffset) == DensityUtil.dp2px(300) - toolbar.getHeight()) {//关闭
                    toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    collapsingToolbarLayout.setContentScrimResource(R.color.white);
                    txtLeft1.setTextColor(getResources().getColor(R.color.text_33));
                    txtTitle1.setTextColor(getResources().getColor(R.color.text_33));
                    txtRight1.setTextColor(getResources().getColor(R.color.text_33));
                } else {  //展开
                    toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
                    collapsingToolbarLayout.setContentScrimResource(R.color.transparent);
                    txtLeft1.setTextColor(getResources().getColor(R.color.white));
                    txtTitle1.setTextColor(getResources().getColor(R.color.white));
                    txtRight1.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
    }

    @Override
    public void initData() {
        initData(page);
    }

    private void initData(int pageNum){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.staff_info_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),staff_id,pageNum),true);
    }

    private void initData1(int pageNum){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_4, UrlUtils.staff_koubei_list_get(LoginUtil.getInfo("token"),
                LoginUtil.getInfo("uid"),staff_id,pageNum),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadmore();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        if(resultObj.has("share_data") && !StringUtils.isEmpty(resultObj.optString("share_data"))){
                            shareBean = FastJsonTools.getPerson(resultObj.optString("share_data"),ShareBean.class);
                        }
                        if(resultObj.has("staff") && !StringUtils.isEmpty(resultObj.optString("staff"))){
                            JSONObject infoObject = resultObj.optJSONObject("staff");
                            uid = infoObject.optString("uid");
                            txtTitle1.setText(infoObject.optString("staff_name"));
                            txtStaffIntro.setText(infoObject.optString("staff_intro"));
                            txtPostName.setText(infoObject.optString("post_name")+"  "+infoObject.optString("staff_name"));
                            txtFansNum.setText("粉丝 "+infoObject.optString("fans_num"));
                            txtAppointNum.setText("被约数 "+infoObject.optString("appoint_num"));
                            is_attention = infoObject.optString("is_attention");
                            txtAttection.setText(infoObject.optString("is_attention").equals("1")?"已关注":"关注");
                            if(!StringUtils.isEmpty(infoObject.optString("staff_avatar"))){
                                ivIcon.setImageURI(Uri.parse(infoObject.optString("staff_avatar")));
                                UIUtils.loadImg(mContext,infoObject.optString("staff_avatar"),ivBg);
//                                new AsyncTask<String, Void, Bitmap>() {
//                                    @Override
//                                    protected Bitmap doInBackground(String... params) {
//                                        return returnBitMap(params[0]);
//                                    }
//                                    @Override
//                                    protected void onPostExecute(Bitmap bitmap) {
//                                        // 主线程处理view
//                                        if (bitmap != null) {
//                                            ivBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                                            ivBg.setImageBitmap(bitmap);
//                                        }else {
//                                            ivBg.setBackgroundColor(getResources().getColor(R.color.text_dd));
//                                        }
//                                    }
//                                }.execute(infoObject.optString("staff_avatar"));
//                                new DownloadImageTask().execute(infoObject.optString("staff_avatar")) ;
                            }
                        }
                        if(!StringUtils.isEmpty(resultObj.optString("works")) && resultObj.optJSONArray("works").length() > 0){
                            llHas.setVisibility(View.GONE);
                            List<MoBean> lsJson = FastJsonTools.getPersons(resultObj.optString("works"),MoBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        }else {
                            List<MoBean> lsJson = new ArrayList<>();
                            setAdapter(lsJson);
                            if(page != 1){
                                llHas.setVisibility(View.GONE);
                                ToastUtil.show(mContext,"暂无更多数据");
                            }else {
                                llHas.setVisibility(View.VISIBLE);
                                txtEmptyLabel.setText("还没有更新作品哦");
                            }
                        }
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
                        if(zanPos != -1){
                            if(list.get(zanPos).getIs_praise() == 1){
                                ToastUtil.show(mContext,"取消成功");
                                list.get(zanPos).setIs_praise(0);
                                if(!StringUtils.isEmpty(list.get(zanPos).getPraise_num()) && !list.get(zanPos).getPraise_num().equals("0")){
                                    if(Integer.parseInt(list.get(zanPos).getPraise_num())-1 == 0){
                                        list.get(zanPos).setPraise_num("0");
                                    }else {
                                        list.get(zanPos).setPraise_num(String.valueOf(Integer.parseInt(list.get(zanPos).getPraise_num())-1));
                                    }
                                }
                                for (int i = 0; i < list.get(zanPos).getPraise_users().size(); i++){
                                    if(list.get(zanPos).getPraise_users().get(i).getUid().equals(LoginUtil.getInfo("uid"))){
                                        list.get(zanPos).getPraise_users().remove(i);
                                    }
                                }
                            }else {
                                ToastUtil.show(mContext,"点赞成功");
                                list.get(zanPos).setIs_praise(1);
                                if(!StringUtils.isEmpty(list.get(zanPos).getPraise_num()) && !list.get(zanPos).getPraise_num().equals("0")){
                                    list.get(zanPos).setPraise_num(String.valueOf(Integer.parseInt(list.get(zanPos).getPraise_num())+1));
                                }else {
                                    list.get(zanPos).setPraise_num("1");
                                }
                                if(!StringUtils.isEmpty(object.optString("result"))){
                                    PraiseBean bean = FastJsonTools.getPerson(object.optString("result"),PraiseBean.class);
                                    list.get(zanPos).getPraise_users().add(bean);
                                }
                            }
                            adapter.notifyDataSetChanged();
                            zanPos = -1;
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_3:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        ToastUtil.show(mContext,"评论成功");
                        if(comPos != -1){
                            if(!StringUtils.isEmpty(object.optString("result"))){
                                MoCommentBean bean = FastJsonTools.getPerson(object.optString("result"),MoCommentBean.class);
                                list.get(comPos).getComments().add(bean);
                                adapter.notifyDataSetChanged();
                                comPos = -1;
                            }
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_4:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadmore();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result")) && object.optJSONArray("result").length() > 0){
                            llHas.setVisibility(View.GONE);
                            List<EvlBean> lsJson = FastJsonTools.getPersons(object.optString("result"),EvlBean.class);
                            setAdapter1(lsJson);
                            page1 ++ ;
                        }else {
                            List<EvlBean> lsJson = new ArrayList<>();
                            setAdapter1(lsJson);
                            if(page1 != 1){
                                llHas.setVisibility(View.GONE);
                                ToastUtil.show(mContext,"暂无更多数据");
                            }else {
                                llHas.setVisibility(View.VISIBLE);
                                txtEmptyLabel.setText("还没有更多评论");
                            }
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_5:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(is_attention.equals("1")){
                            is_attention = "0";
//                            ToastUtil.show(mContext,"取消关注成功");
                            txtAttection.setText("关注");
                        }else {
                            is_attention = "1";
//                            ToastUtil.show(mContext,"关注成功");
                            txtAttection.setText("已关注");
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_6:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(zanPos1 != -1){
                            if(list1.get(zanPos1).getIs_evl_praise().equals("1")){
//                                ToastUtil.show(mContext,"取消成功");
                                list1.get(zanPos1).setIs_evl_praise("0");
                                if(!StringUtils.isEmpty(list1.get(zanPos1).getPraise_num()) && !list1.get(zanPos1).getPraise_num().equals("0")){
                                    if(Integer.parseInt(list1.get(zanPos1).getPraise_num())-1 == 0){
                                        list1.get(zanPos1).setPraise_num("0");
                                    }else {
                                        list1.get(zanPos1).setPraise_num(String.valueOf(Integer.parseInt(list1.get(zanPos1).getPraise_num())-1));
                                    }
                                }
                            }else {
//                                ToastUtil.show(mContext,"点赞成功");
                                list1.get(zanPos1).setIs_evl_praise("1");
                                if(!StringUtils.isEmpty(list1.get(zanPos1).getPraise_num()) && !list1.get(zanPos1).getPraise_num().equals("0")){
                                    list1.get(zanPos1).setPraise_num(String.valueOf(Integer.parseInt(list1.get(zanPos1).getPraise_num())+1));
                                }else {
                                    list1.get(zanPos1).setPraise_num("1");
                                }
                            }
                            adapter1.notifyDataSetChanged();
                            zanPos1 = -1;
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_7:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
//                        ToastUtil.show(mContext,"删除成功");
                        if(plPos != -1){
                            list.get(plPos).getComments().remove(plPos1);
                            adapter.notifyDataSetChanged();
                            plPos = -1;
                            plPos1 = -1;
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
                refreshLayout.finishLoadmore();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    public Bitmap returnBitMap(final String url){
        try {
            URL path = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)path.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap != null){
            bitmap = EasyBlur.with(mContext)
                    .bitmap(bitmap) //要模糊的图片
                    .scale(1)
                    .radius(15)//模糊半径
                    .blur();
        }
        return bitmap;
    }

    //将网络图片转为Drawable
    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
        protected Drawable doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }
        protected void onPostExecute(Drawable result) {
            if(result == null){
                ivBg.setBackgroundColor(getResources().getColor(R.color.text_dd));
            }else {
                ivBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
    //作品适配器
    private void setAdapter(List<MoBean> lsJson) {
        if(page == 1)
            list.clear();
        list.addAll(lsJson);
        if(adapter == null){
            adapter = new PersonalHomepageDtRecyclerAdapter(mContext,list);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
        adapter.setOnItemClickListener(new PersonalHomepageDtRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {}

            @Override
            public void onZanClick(int position) {
                if(LoginUtil.getLoginState()){
                    zanPos = position;
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.mo_praise_set(LoginUtil.getInfo("token"),
                            LoginUtil.getInfo("uid"),list.get(position).getMo_id(),list.get(position).getIs_praise()==1?"0":"1"),true);
                }else {
                    it = new Intent(mContext, LoginActivity.class);
                    startActivity(it);
                }
            }

            @Override
            public void onDelClick(final int position) {}

            @Override
            public void onCommentClick(int position,String content) {
                if(LoginUtil.getLoginState()){
                    comPos = position;
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.mo_comment_set(LoginUtil.getInfo("token"),
                            LoginUtil.getInfo("uid"),list.get(position).getMo_id(),content,"0"),true);
                }else {
                    it = new Intent(mContext, LoginActivity.class);
                    startActivity(it);
                }
            }

            @Override
            public void onChildCommentDelClick(final int position, final int pos) {
                NiceDialog.init().setLayoutId(R.layout.pop_ewm_more)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                holder.setText(R.id.txt_share,"删除评论");
                                TextView txt = holder.getView(R.id.txt_save_pic);
                                txt.setVisibility(View.GONE);
                                holder.setOnClickListener(R.id.txt_cancel, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                holder.setOnClickListener(R.id.txt_share, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        plPos = position;plPos1 = pos;
                                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_7,
                                                UrlUtils.mo_comment_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                                                        list.get(position).getComments().get(pos).getMmt_comment_id()),true);
                                    }
                                });
                            }
                        })
                        .setOutCancel(true).setShowBottom(true)
                        .show(getSupportFragmentManager());
            }

            @Override
            public void onChildCommentClick(int position, int pos, String content) {
                if(LoginUtil.getLoginState()){
                    comPos = position;
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.mo_comment_set(LoginUtil.getInfo("token"),
                            LoginUtil.getInfo("uid"),list.get(position).getMo_id(),content,list.get(position).getComments().get(pos).getComment_uid()),true);
                }else {
                    it = new Intent(mContext, LoginActivity.class);
                    startActivity(it);
                }
            }
        });
    }
    //口碑适配器
    private void setAdapter1(List<EvlBean> lsJson) {
        if(page1 == 1)
            list1.clear();
        list1.addAll(lsJson);
        if(adapter1 == null){
            adapter1 = new GoodsCommentRecyclerAdapter(mContext,list1,false);
            recyclerView.setAdapter(adapter1);
        }else {
            adapter1.notifyDataSetChanged();
        }
        adapter1.setOnItemClickListener(new GoodsCommentRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onZanClick(int position) {
                if(LoginUtil.getLoginState()){
                    zanPos1 = position;
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_6,UrlUtils.evalution_praise_set(LoginUtil.getInfo("token"),
                            LoginUtil.getInfo("uid"),list1.get(position).getEvl_id(),list1.get(position).getIs_evl_praise().equals("1")?"0":"1"),true);
                }else {
                    it = new Intent(mContext, LoginActivity.class);
                    startActivity(it);
                }
            }

            @Override
            public void onReplyClick(int position,String content) {}
        });
    }

    @Override
    @OnClick({R.id.txt_left_1,R.id.txt_right_1,R.id.txt_zuopin,R.id.txt_koubei,R.id.ll_attection,R.id.ll_chat})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left_1:
                finish();
                break;
            case R.id.txt_right_1:
                if(shareBean != null){
                    share();
                }
                break;
            case R.id.txt_zuopin:
                isLeft = true;
                txtZuopin.setSelected(true);txtKouBei.setSelected(false);
                if(list.size() > 0){
                    llHas.setVisibility(View.GONE);
                }else {
                    llHas.setVisibility(View.VISIBLE);
                    txtEmptyLabel.setText("还没有更新作品哦");
                }
                adapter = new PersonalHomepageDtRecyclerAdapter(mContext,list);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.txt_koubei:
                isLeft = false;
                txtZuopin.setSelected(false);txtKouBei.setSelected(true);
                if(isFirst){
                    isFirst = false;
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_4, UrlUtils.staff_koubei_list_get(LoginUtil.getInfo("token"),
                            LoginUtil.getInfo("uid"),staff_id,1),true);
                }else {
                    if(list1.size() > 0){
                        llHas.setVisibility(View.GONE);
                    }else {
                        llHas.setVisibility(View.VISIBLE);
                        txtEmptyLabel.setText("还没有更多评论");
                    }
                    adapter1 = new GoodsCommentRecyclerAdapter(mContext,list1,false);
                    recyclerView.setAdapter(adapter1);
                }
                break;
            case R.id.ll_attection:
                if(LoginUtil.getLoginState()){
                    mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_5,UrlUtils.attention_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),"2",
                            staff_id,is_attention.equals("1")?"0":"1"),true);
                }else {
                    it = new Intent(mContext, LoginActivity.class);
                    startActivity(it);
                }
                break;
            case R.id.ll_chat:
                if(LoginUtil.getLoginState()){
                    if(AppConfig.mIMKit == null){
                        AppConfig.mIMKit = YWAPI.getIMKitInstance(LoginUtil.getInfo("uid"), AppConfig.APP_KEY);
                    }
                    Intent chat = AppConfig.mIMKit.getChattingActivityIntent(uid);
                    chat.putExtra("uid",uid);
                    startActivity(chat);
                }else {
                    it = new Intent(mContext, LoginActivity.class);
                    startActivity(it);
                }
                break;
        }
    }

    private void share() {
        NiceDialog.init().setLayoutId(R.layout.pop_share)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        holder.setOnClickListener(R.id.txt_pyq, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ShareUtil(TeamDetailActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE,shareBean);
                                dialog.dismiss();
                            }
                        });
                        holder.setOnClickListener(R.id.txt_wx, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ShareUtil(TeamDetailActivity.this, SHARE_MEDIA.WEIXIN,shareBean);
                                dialog.dismiss();
                            }
                        });
                        holder.setOnClickListener(R.id.txt_qq, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ShareUtil(TeamDetailActivity.this, SHARE_MEDIA.QQ,shareBean);
                                dialog.dismiss();
                            }
                        });
                        holder.setOnClickListener(R.id.txt_wb, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new ShareUtil(TeamDetailActivity.this, SHARE_MEDIA.SINA,shareBean);
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setOutCancel(true).setShowBottom(true)
                .show(getSupportFragmentManager());
    }
}
