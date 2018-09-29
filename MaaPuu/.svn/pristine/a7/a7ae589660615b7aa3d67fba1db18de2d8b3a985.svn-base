package com.maapuu.mereca.fragment.goodschild;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.GoodsCommentRecyclerAdapter;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.EvlBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.DividerGridItemDecoration;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/5.
 */

public class GoodsCommentFragment extends BaseFragment{
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.txt_all)
    TextView txtAll;
    @BindView(R.id.txt_all_num)
    TextView txtAllNum;
    @BindView(R.id.line_all)
    View lineAll;
    @BindView(R.id.ll_tu)
    LinearLayout llTu;
    @BindView(R.id.txt_tu)
    TextView txtTu;
    @BindView(R.id.txt_tu_num)
    TextView txtTuNum;
    @BindView(R.id.line_tu)
    View lineTu;
    @BindView(R.id.ll_hao)
    LinearLayout llHao;
    @BindView(R.id.txt_hao)
    TextView txtHao;
    @BindView(R.id.txt_hao_num)
    TextView txtHaoNum;
    @BindView(R.id.line_hao)
    View lineHao;
    @BindView(R.id.ll_cha)
    LinearLayout llCha;
    @BindView(R.id.txt_cha)
    TextView txtCha;
    @BindView(R.id.txt_cha_num)
    TextView txtChaNum;
    @BindView(R.id.line_cha)
    View lineCha;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_has)
    LinearLayout llHas;

    private LinearLayout[] lls;
    private TextView[] tvs;
    private TextView[] tvNums;
    private View[] lines;
    private static GoodsCommentFragment fragment = null;
    private LinearLayoutManager mLayoutManager;
    private List<EvlBean> list;
    private GoodsCommentRecyclerAdapter adapter;
    private int page = 1;
    private String item_id;
    private String shop_id;
    private String list_type = "1";//列表类型：1全部；2有图；3好评；4差评
    private int pos;
    private int zanPos = -1;

    public static GoodsCommentFragment newInstance() {
        if (fragment == null) {
            fragment = new GoodsCommentFragment();
        }
        return fragment;
    }

    @Override
    protected int setContentViewById() {
        Bundle bundle=getArguments();
        //判断需写
        if(bundle!=null) {
            item_id = bundle.getString("item_id");
            shop_id = bundle.getString("shop_id");
        }
        return R.layout.fragment_goods_comment;
    }

    public void refresh(int pos){
        setHead(pos);
        if(!String.valueOf(pos+1).equals(list_type)){
            list_type = String.valueOf(pos+1);
            page = 1;
            initData(page,true);
        }
    }

    @Override
    protected void initView(View v) {
        lls = new LinearLayout[]{llAll,llTu,llHao,llCha};
        tvs = new TextView[]{txtAll,txtTu,txtHao,txtCha};
        tvNums = new TextView[]{txtAllNum,txtTuNum,txtHaoNum,txtChaNum};
        lines = new View[]{lineAll,lineTu,lineHao,lineCha};
        setHead(0);

        list = new ArrayList<>();
        mLayoutManager=new FullyLinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext, 10
                ,getResources().getColor(R.color.background)));

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setEnableAutoLoadMore(false);//是否启用自动加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                initData(page,true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                initData(page,true);
            }
        });

    }

    private void setHead(int postion) {
        for (int i = 0; i < lls.length; i++){
            if(i == postion){
                lls[postion].setSelected(true);
                tvs[postion].setSelected(true);
                tvNums[postion].setSelected(true);
                lines[postion].setSelected(true);
            }else {
                lls[i].setSelected(false);
                tvs[i].setSelected(false);
                tvNums[i].setSelected(false);
                lines[i].setSelected(false);
            }
        }
    }

    @Override
    protected void initData() {
        initData(page,false);
    }

    private void initData(int pageNum,boolean bool){
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.evalution_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                shop_id,item_id,list_type,pageNum),bool);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        if(resultObj.has("evl_total") && !StringUtils.isEmpty(resultObj.optString("evl_total"))){
                            JSONObject totalObj = resultObj.optJSONObject("evl_total");
                            txtAllNum.setText(totalObj.optString("evl_num"));
                            txtTuNum.setText(totalObj.optString("evl_share_num"));
                            txtHaoNum.setText(totalObj.optString("evl_good_num"));
                            txtChaNum.setText(totalObj.optString("evl_bad_num"));
                        }
                        if(!StringUtils.isEmpty(resultObj.optString("evl_data")) && resultObj.optJSONArray("evl_data").length() > 0){
                            llHas.setVisibility(View.GONE);
                            List<EvlBean> lsJson = FastJsonTools.getPersons(resultObj.optString("evl_data"),EvlBean.class);
                            setAdapter(lsJson);
                            page ++ ;
                        }else {
                            List<EvlBean> lsJson = new ArrayList<>();
                            setAdapter(lsJson);
                            if(page != 1){
                                llHas.setVisibility(View.GONE);
                                ToastUtil.show(mContext,"暂无更多数据");
                            }else {
                                llHas.setVisibility(View.VISIBLE);
                            }
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
                        if(zanPos != -1){
                            if(list.get(zanPos).getIs_evl_praise().equals("1")){
                                ToastUtil.show(mContext,"取消成功");
                                list.get(zanPos).setIs_evl_praise("0");
                                if(!StringUtils.isEmpty(list.get(zanPos).getPraise_num()) && !list.get(zanPos).getPraise_num().equals("0")){
                                    if(Integer.parseInt(list.get(zanPos).getPraise_num())-1 == 0){
                                        list.get(zanPos).setPraise_num("0");
                                    }else {
                                        list.get(zanPos).setPraise_num(String.valueOf(Integer.parseInt(list.get(zanPos).getPraise_num())-1));
                                    }
                                }
                            }else {
                                ToastUtil.show(mContext,"点赞成功");
                                list.get(zanPos).setIs_evl_praise("1");
                                if(!StringUtils.isEmpty(list.get(zanPos).getPraise_num()) && !list.get(zanPos).getPraise_num().equals("0")){
                                    list.get(zanPos).setPraise_num(String.valueOf(Integer.parseInt(list.get(zanPos).getPraise_num())+1));
                                }else {
                                    list.get(zanPos).setPraise_num("1");
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
            case HttpModeBase.HTTP_ERROR:
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter(List<EvlBean> lsJson) {
        if(page == 1)
            list.clear();
        list.addAll(lsJson);
        if(adapter == null){
            adapter = new GoodsCommentRecyclerAdapter(mContext,list,false);
            recyclerView.setAdapter(adapter);
        }else {
            if(page > 1){
                adapter.notifyItemRangeInserted(list.size()-lsJson.size(),lsJson.size());
            }else {
                adapter.notifyDataSetChanged();
            }
        }
        adapter.setOnItemClickListener(new GoodsCommentRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onZanClick(int position) {
                zanPos = position;
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_6,UrlUtils.evalution_praise_set(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),list.get(position).getEvl_id(),list.get(position).getIs_evl_praise().equals("1")?"0":"1"),true);
            }

            @Override
            public void onReplyClick(int position,String content) {}
        });
    }

    @Override
    @OnClick({R.id.ll_all,R.id.ll_tu,R.id.ll_hao,R.id.ll_cha})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_all:
                setHead(0);list_type = "1";
                page = 1;initData(page,true);
                break;
            case R.id.ll_tu:
                setHead(1);list_type = "2";
                page = 1;initData(page,true);
                break;
            case R.id.ll_hao:
                setHead(2);list_type = "3";
                page = 1;initData(page,true);
                break;
            case R.id.ll_cha:
                setHead(3);list_type = "4";
                page = 1;initData(page,true);
                break;
        }
    }
}
