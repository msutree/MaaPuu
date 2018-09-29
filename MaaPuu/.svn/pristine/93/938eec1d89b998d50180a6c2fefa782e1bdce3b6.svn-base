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

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.barlibrary.ImmersionBar;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.PersonalHomepageDtRecyclerAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.MoBean;
import com.maapuu.mereca.bean.MoCommentBean;
import com.maapuu.mereca.bean.PraiseBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PictureSelectUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.util.UIUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
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
import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import info.wangchen.simplehud.SimpleHUD;

/**
 * Created by dell on 2018/3/5.
 */

public class PersonalHomepageActivity extends BaseActivity {
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
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_signture)
    TextView txtSignture;
    @BindView(R.id.segment)
    SegmentedButtonGroup group;
    @BindView(R.id.ll_has)
    LinearLayout llHas;
    @BindView(R.id.txt_empty_label)
    TextView txtEmptyLabel;

    private boolean isFirst = true; //是否第一次点击动态
    private LinearLayoutManager mLayoutManager;
    private List<MoBean> list;
    private PersonalHomepageDtRecyclerAdapter adapter;
    private List<MoBean> list1;
    private PersonalHomepageDtRecyclerAdapter adapter1;
    private int page = 1;
    private int page1 = 1;
    private String is_staff;
    private String userpage_uid;
    private int delPos = -1;
    private int zanPos = -1;
    private int comPos = -1;
    private int delPos1 = -1;
    private int zanPos1 = -1;
    private int comPos1 = -1;
    private int plPos = -1,plPos1 = -1;
    private int plPos2 = -1,plPos3 = -1;
    private Bitmap bitmap = null;

    private int mOffset = 0;
    private String upload_path;
    private String edit_path;
    private String mo_type = "0";
    private boolean isFirstEnter = true;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal_homepage);
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar().titleBar(toolbar).init();
        txtLeft1.setTypeface(StringUtils.getFont(mContext));
        txtTitle1.setText("个人主页");
        userpage_uid = getIntent().getStringExtra("userpage_uid");

        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,20
                ,getResources().getColor(R.color.background)));

        group.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
                if(position == 0){
                    mo_type = "2";
                    if(list.size() > 0){
                        llHas.setVisibility(View.GONE);
                    }else {
                        llHas.setVisibility(View.VISIBLE);
                        txtEmptyLabel.setText("还没有更新作品哦");
                    }
                    List<MoBean> tempList = new ArrayList<>();
                    tempList.addAll(list);
                    setAdapter(tempList,true);
                }else {
                    mo_type = "1";
                    if(isFirst){
                        isFirst = false;
                        initData(page1);
                    }else {
                        if(list1.size() > 0){
                            llHas.setVisibility(View.GONE);
                        }else {
                            llHas.setVisibility(View.VISIBLE);
                            txtEmptyLabel.setText("还没有更新动态哦");
                        }
                        List<MoBean> tempList = new ArrayList<>();
                        tempList.addAll(list1);
                        setAdapter1(tempList,true);
                    }
                }
            }
        });
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        classicsHeader.getLastUpdateText().setTextColor(getResources().getColor(R.color.white));
        classicsHeader.getTitleText().setTextColor(getResources().getColor(R.color.white));
        classicsHeader.setArrowResource(R.mipmap.refresh_arrow);//设置箭头资源
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(mo_type.equals("2")){
                    page = 1;
                    initData(page);
                }else {
                    page1 = 1;
                    initData(page1);
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                if(mo_type.equals("2")){
                    initData(page);
                }else {
                    initData(page1);
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
                if(verticalOffset != 0 ){
                    ivBg.setTranslationY(verticalOffset);
                }
                //200是appbar的高度
                if (Math.abs(verticalOffset) == DensityUtil.dp2px(245) - toolbar.getHeight()) {//关闭
                    toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                    collapsingToolbarLayout.setContentScrimResource(R.color.white);
                    txtLeft1.setTextColor(getResources().getColor(R.color.text_33));
                    txtTitle1.setTextColor(getResources().getColor(R.color.text_33));
                } else {  //展开
                    toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
                    collapsingToolbarLayout.setContentScrimResource(R.color.transparent);
                    txtLeft1.setTextColor(getResources().getColor(R.color.white));
                    txtTitle1.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
    }

    @Override
    public void initData() {
        initData(1);
    }

    private void initData(int pageNum){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.userpage_info_get(LoginUtil.getInfo("token"),
                LoginUtil.getInfo("uid"),userpage_uid,mo_type,pageNum),true);
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
                        if(resultObj.has("userpage_info") && !StringUtils.isEmpty(resultObj.optString("userpage_info"))){
                            JSONObject infoObject = resultObj.optJSONObject("userpage_info");
                            txtName.setText(infoObject.optString("nick_name"));
                            txtSignture.setText(infoObject.optString("signature"));
                            is_staff = infoObject.optString("is_staff");
                            if(isFirstEnter){
                                if("1".equals(is_staff)){
                                    mo_type = "2";
                                    group.setVisibility(View.VISIBLE);
                                }else {
                                    mo_type = "1";
                                    group.setVisibility(View.INVISIBLE);
                                }
                                isFirstEnter = false;
                            }
                            if(infoObject.has("mo_head_img")){
                                UIUtils.loadImg(mContext,infoObject.optString("mo_head_img"),ivBg);
                            }
                            if(!StringUtils.isEmpty(infoObject.optString("avatar"))){
                                ivIcon.setImageURI(Uri.parse(infoObject.optString("avatar")));
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
//                                }.execute(infoObject.optString("avatar"));
//                                new DownloadImageTask().execute(infoObject.optString("avatar")) ;
                            }
                        }
                        if(!StringUtils.isEmpty(resultObj.optString("mo_data")) && resultObj.optJSONArray("mo_data").length() > 0){
                            llHas.setVisibility(View.GONE);
                            List<MoBean> lsJson = FastJsonTools.getPersons(resultObj.optString("mo_data"),MoBean.class);
                            if("2".equals(mo_type)){
                                setAdapter(lsJson,false);
                                page ++ ;
                            }else {
                                setAdapter1(lsJson,false);
                                page1 ++ ;
                            }
                        }else {
                            List<MoBean> lsJson = new ArrayList<>();
                            if("2".equals(mo_type)){
                                setAdapter(lsJson,false);
                                if(page != 1){
                                    llHas.setVisibility(View.GONE);
                                    ToastUtil.show(mContext,"暂无更多数据");
                                }else {
                                    llHas.setVisibility(View.VISIBLE);
                                    txtEmptyLabel.setText("还没有更新作品哦");
                                }
                            }else {
                                setAdapter1(lsJson,false);
                                if(page1 != 1){
                                    llHas.setVisibility(View.GONE);
                                    ToastUtil.show(mContext,"暂无更多数据");
                                }else {
                                    llHas.setVisibility(View.VISIBLE);
                                    txtEmptyLabel.setText("还没有更新动态哦");
                                }
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
                        if("2".equals(mo_type)){
                            if(zanPos != -1){
                                if(list.get(zanPos).getIs_praise() == 1){
//                                    ToastUtil.show(mContext,"取消成功");
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
//                                    ToastUtil.show(mContext,"点赞成功");
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
                        }else {
                            if(zanPos1 != -1){
                                if(list1.get(zanPos1).getIs_praise() == 1){
//                                    ToastUtil.show(mContext,"取消成功");
                                    list1.get(zanPos1).setIs_praise(0);
                                    if(!StringUtils.isEmpty(list1.get(zanPos1).getPraise_num()) && !list1.get(zanPos1).getPraise_num().equals("0")){
                                        if(Integer.parseInt(list1.get(zanPos1).getPraise_num())-1 == 0){
                                            list1.get(zanPos1).setPraise_num("0");
                                        }else {
                                            list1.get(zanPos1).setPraise_num(String.valueOf(Integer.parseInt(list1.get(zanPos1).getPraise_num())-1));
                                        }
                                    }
                                    for (int i = 0; i < list1.get(zanPos1).getPraise_users().size(); i++){
                                        if(list1.get(zanPos1).getPraise_users().get(i).getUid().equals(LoginUtil.getInfo("uid"))){
                                            list1.get(zanPos1).getPraise_users().remove(i);
                                        }
                                    }
                                }else {
//                                    ToastUtil.show(mContext,"点赞成功");
                                    list1.get(zanPos1).setIs_praise(1);
                                    if(!StringUtils.isEmpty(list1.get(zanPos1).getPraise_num()) && !list1.get(zanPos1).getPraise_num().equals("0")){
                                        list1.get(zanPos1).setPraise_num(String.valueOf(Integer.parseInt(list1.get(zanPos1).getPraise_num())+1));
                                    }else {
                                        list1.get(zanPos1).setPraise_num("1");
                                    }
                                    if(!StringUtils.isEmpty(object.optString("result"))){
                                        PraiseBean bean = FastJsonTools.getPerson(object.optString("result"),PraiseBean.class);
                                        list1.get(zanPos1).getPraise_users().add(bean);
                                    }
                                }
                                adapter1.notifyDataSetChanged();
                                zanPos1 = -1;
                            }
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
//                        ToastUtil.show(mContext,"评论成功");
                        if("2".equals(mo_type)){
                            if(comPos != -1){
                                if(!StringUtils.isEmpty(object.optString("result"))){
                                    MoCommentBean bean = FastJsonTools.getPerson(object.optString("result"),MoCommentBean.class);
                                    list.get(comPos).getComments().add(bean);
                                    adapter.notifyDataSetChanged();
                                    comPos = -1;
                                }
                            }
                        }else {
                            if(comPos1 != -1){
                                if(!StringUtils.isEmpty(object.optString("result"))){
                                    MoCommentBean bean = FastJsonTools.getPerson(object.optString("result"),MoCommentBean.class);
                                    list1.get(comPos1).getComments().add(bean);
                                    adapter1.notifyDataSetChanged();
                                    comPos1 = -1;
                                }
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
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
//                        ToastUtil.show(mContext,"删除成功");
                        if("2".equals(mo_type)){
                            if(delPos != -1){
                                list.remove(delPos);
                                adapter.notifyDataSetChanged();
                                delPos = -1;
                                if(list.size() == 0){
                                    txtEmptyLabel.setText("还没有更新作品哦");
                                    llHas.setVisibility(View.VISIBLE);
                                }
                            }
                        }else {
                            if(delPos1 != -1){
                                list1.remove(delPos1);
                                adapter1.notifyDataSetChanged();
                                delPos1 = -1;
                                if(list1.size() == 0){
                                    txtEmptyLabel.setText("还没有更新动态哦");
                                    llHas.setVisibility(View.VISIBLE);
                                }
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
//                        ToastUtil.show(mContext,"修改成功");
                        edit_path = object.optString("result");
                        UIUtils.loadImg(mContext,object.optString("result"),ivBg);
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
                        if("2".equals(mo_type)){
                            if(plPos != -1){
                                list.get(plPos).getComments().remove(plPos1);
                                adapter.notifyDataSetChanged();
                                plPos = -1;
                                plPos1 = -1;
                            }
                        }else {
                            if(plPos2 != -1){
                                list1.get(plPos2).getComments().remove(plPos3);
                                adapter1.notifyDataSetChanged();
                                plPos2 = -1;
                                plPos3 = -1;
                            }
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_REQUESTCODE_IMG:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (object.has("src") && !object.optString("src").equals("")) {
                            save(object.optString("src"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SimpleHUD.dismiss();
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

    private void save(String src) {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_5, UrlUtils.friend_moment_head_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),src),true);
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

    private void setAdapter(List<MoBean> lsJson,boolean bool) {
        if(page == 1 || bool)
            list.clear();
        list.addAll(lsJson);
        if(adapter == null || bool){
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
                zanPos = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.mo_praise_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),list.get(position).getMo_id(),list.get(position).getIs_praise()==1?"0":"1"),true);
            }

            @Override
            public void onDelClick(final int position) {
                new AlertView(null, "确定删除吗？", "取消", null, new String[]{"确定"}, mContext,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int pos) {
                        if (pos == 0) {
                            delPos = position;
                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_4,UrlUtils.mo_delete_set(LoginUtil.getInfo("token"),
                                    LoginUtil.getInfo("uid"),list.get(position).getMo_id()),true);
                        }
                    }
                }).show();
            }

            @Override
            public void onCommentClick(int position,String content) {
                comPos = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.mo_comment_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),list.get(position).getMo_id(),content,"0"),true);
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
                comPos = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.mo_comment_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),list.get(position).getMo_id(),content,list.get(position).getComments().get(pos).getComment_uid()),true);
            }
        });
    }

    private void setAdapter1(List<MoBean> lsJson,boolean bool) {
        if(page1 == 1 || bool)
            list1.clear();
        list1.addAll(lsJson);
        if(adapter1 == null || bool){
            adapter1 = new PersonalHomepageDtRecyclerAdapter(mContext,list1);
            recyclerView.setAdapter(adapter1);
        }else {
            adapter1.notifyDataSetChanged();
        }
        adapter1.setOnItemClickListener(new PersonalHomepageDtRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {}

            @Override
            public void onZanClick(int position) {
                zanPos1 = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.mo_praise_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),list1.get(position).getMo_id(),list1.get(position).getIs_praise()==1?"0":"1"),true);
            }

            @Override
            public void onDelClick(final int position) {
                new AlertView(null, "确定删除吗？", "取消", null, new String[]{"确定"}, mContext,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int pos) {
                        if (pos == 0) {
                            delPos1 = position;
                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_4,UrlUtils.mo_delete_set(LoginUtil.getInfo("token"),
                                    LoginUtil.getInfo("uid"),list1.get(position).getMo_id()),true);
                        }
                    }
                }).show();
            }

            @Override
            public void onCommentClick(int position,String content) {
                comPos1 = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.mo_comment_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),list1.get(position).getMo_id(),content,"0"),true);
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
                                        plPos2 = position;plPos3 = pos;
                                        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_7,
                                                UrlUtils.mo_comment_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                                                        list1.get(position).getComments().get(pos).getMmt_comment_id()),true);
                                    }
                                });
                            }
                        })
                        .setOutCancel(true).setShowBottom(true)
                        .show(getSupportFragmentManager());
            }

            @Override
            public void onChildCommentClick(int position, int pos, String content) {
                comPos1 = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.mo_comment_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),list1.get(position).getMo_id(),content,list1.get(position).getComments().get(pos).getComment_uid()),true);
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left_1,R.id.iv_icon,R.id.view})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left_1:
                if(!StringUtils.isEmpty(edit_path)){
                    it = new Intent();
                    it.putExtra("edit_path",edit_path);
                    setResult(AppConfig.ACTIVITY_RESULTCODE_4,it);
                }
                finish();
                break;
            case R.id.iv_icon:
                if(!LoginUtil.getLoginState()){
                    ToastUtil.show(mContext,"请先登录");
                    return;
                }
                if(LoginUtil.getInfo("uid").equals(userpage_uid)){
                    return;
                }
                it = new Intent(mContext,ChatDetailActivity.class);
                it.putExtra("friend_uid",userpage_uid);
                it.putExtra("msg_id","");
                it.putExtra("share_code","");
                startActivity(it);
                break;
            case R.id.view:
                if(!LoginUtil.getInfo("uid").equals(userpage_uid)){
                    return;
                }
                NiceDialog.init().setLayoutId(R.layout.pop_ewm_more)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                                holder.setText(R.id.txt_share,"更换相册封面");
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
                                        PictureSelector.create(PersonalHomepageActivity.this)
                                                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                                .maxSelectNum(1)// 最大图片选择数量
                                                .minSelectNum(1)// 最小选择数量
                                                .imageSpanCount(3)// 每行显示个数
                                                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                                                .previewImage(true)// 是否可预览图片
                                                .isCamera(true)// 是否显示拍照按钮
                                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                                .circleDimmedLayer(false) // 是否圆形裁剪 true or false
                                                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                                                .enableCrop(true)// 是否裁剪
                                                .compress(true)// 是否压缩
                                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                                    }
                                });
                            }
                        })
                        .setOutCancel(true).setShowBottom(true)
                        .show(getSupportFragmentManager());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PictureConfig.CHOOSE_REQUEST:
                if(resultCode == RESULT_OK){
                    if(PictureSelector.obtainMultipleResult(data) != null && PictureSelector.obtainMultipleResult(data).size() > 0){
                        upload_path = PictureSelector.obtainMultipleResult(data).get(0).getCutPath();
                        mHttpModeBase.uploadImage(HttpModeBase.HTTP_REQUESTCODE_IMG, upload_path,true);
                        // 显示在图片控件上
//                        UIUtils.loadImg(mContext,upload_path,bgImage);
                    }else {
                        ToastUtil.show(mContext,"选择图片出错");
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(!StringUtils.isEmpty(edit_path)){
            it = new Intent();
            it.putExtra("edit_path",edit_path);
            setResult(AppConfig.ACTIVITY_RESULTCODE_4,it);
            finish();
        }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PictureSelectUtil.clearCache(PersonalHomepageActivity.this);
    }
}
