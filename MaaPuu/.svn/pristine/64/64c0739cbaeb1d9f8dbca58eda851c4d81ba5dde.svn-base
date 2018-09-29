package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeMenuRecyclerView;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.SrvManageAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.SrvBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.RecyclerViewDivider;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/8/4.
 */

public class ServerManageActivity extends BaseActivity{
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_1)
    TextView txt1;
    @BindView(R.id.txt_2)
    TextView txt2;
    @BindView(R.id.txt_3)
    TextView txt3;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView recyclerView;

    private TextView[] tvs;
    private List<SrvBean> mList;
    private List<SrvBean> tList;
    private List<SrvBean> gList;
    private List<SrvBean> list;
    private SrvManageAdapter adapter;
    private int srv_type = 1;
    private AlertView alertView;

    private int delPos = -1;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_server_manage);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("服务管理");
        mList = new ArrayList<>();
        tList = new ArrayList<>();
        gList = new ArrayList<>();
        list = new ArrayList<>();
        tvs = new TextView[]{txt1,txt2,txt3};
        setHead(0);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, 1,false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
                ,getResources().getColor(R.color.background)));
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
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_srv_sel_new_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid")),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if (!StringUtils.isEmpty(object.optString("result"))) {
                            if(object.optJSONObject("result").has("meifa_srv") && object.optJSONObject("result").optJSONArray("meifa_srv").length() > 0){
                                mList = FastJsonTools.getPersons(object.optJSONObject("result").optString("meifa_srv"),SrvBean.class);
                            }
                            if(object.optJSONObject("result").has("toupi_srv") && object.optJSONObject("result").optJSONArray("toupi_srv").length() > 0){
                                tList = FastJsonTools.getPersons(object.optJSONObject("result").optString("toupi_srv"),SrvBean.class);
                            }
                            if(object.optJSONObject("result").has("gouke_srv") && object.optJSONObject("result").optJSONArray("gouke_srv").length() > 0){
                                gList = FastJsonTools.getPersons(object.optJSONObject("result").optString("gouke_srv"),SrvBean.class);
                            }
                            setAdapter(srv_type);
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
                        ToastUtil.show(mContext,"删除成功");
                        if(delPos != -1){
                            list.remove(delPos);
                            switch (srv_type){
                                case 1:
                                    mList.remove(delPos);
                                    break;
                                case 2:
                                    tList.remove(delPos);
                                    break;
                                case 3:
                                    gList.remove(delPos);
                                    break;
                            }
                            adapter.notifyDataSetChanged();
                            delPos = -1;
                        }
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
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

    private void setAdapter(int type) {
        list.clear();
        switch (type){
            case 1:
                list.addAll(mList);
                break;
            case 2:
                list.addAll(tList);
                break;
            case 3:
                list.addAll(gList);
                break;
        }
        if(adapter == null){
            adapter = new SrvManageAdapter(mContext,list);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
        adapter.setOnItemClickListener(new SrvManageAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                it = new Intent(mContext,AddServerActivity.class);
                it.putExtra("pos",position);
                it.putExtra("srv_type",srv_type+"");
                it.putExtra("srvBean",list.get(position));
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
            }

            @Override
            public void onRightItemClick(View v, final int position) {
                alertView = new AlertView(null, "确定删除吗？", "取消", null, new String[]{"确定"}, mContext,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int pos) {
                        if (pos == 0) {
                            delPos = position;
                            mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_service_delete_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                                    list.get(position).getSrv_id()),true);
                        }
                    }
                });
                alertView.show();
            }
        });
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_1,R.id.txt_2,R.id.txt_3,R.id.txt_add})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_1:
                setHead(0);srv_type = 1; setAdapter(srv_type);
                break;
            case R.id.txt_2:
                setHead(1);srv_type = 2; setAdapter(srv_type);
                break;
            case R.id.txt_3:
                setHead(2);srv_type = 3; setAdapter(srv_type);
                break;
            case R.id.txt_add:
                it = new Intent(mContext,AddServerActivity.class);
                it.putExtra("pos",-1);
                it.putExtra("srv_type",srv_type+"");
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                if(data.getIntExtra("pos",-1) == -1){
                    list.add((SrvBean) data.getSerializableExtra("srvBean"));
                    switch (srv_type){
                        case 1:
                            mList.add((SrvBean) data.getSerializableExtra("srvBean"));
                            break;
                        case 2:
                            tList.add((SrvBean) data.getSerializableExtra("srvBean"));
                            break;
                        case 3:
                            gList.add((SrvBean) data.getSerializableExtra("srvBean"));
                            break;
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    list.set(data.getIntExtra("pos",-1),(SrvBean) data.getSerializableExtra("srvBean"));
                    switch (srv_type){
                        case 1:
                            mList.set(data.getIntExtra("pos",-1),(SrvBean) data.getSerializableExtra("srvBean"));
                            break;
                        case 2:
                            tList.set(data.getIntExtra("pos",-1),(SrvBean) data.getSerializableExtra("srvBean"));
                            break;
                        case 3:
                            gList.set(data.getIntExtra("pos",-1),(SrvBean) data.getSerializableExtra("srvBean"));
                            break;
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(alertView != null && alertView.isShowing()){
            alertView.dismiss();
        }else {
            super.onBackPressed();
        }
    }
}
