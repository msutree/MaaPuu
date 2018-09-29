package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.maapuu.mereca.R;
import com.maapuu.mereca.background.shop.adapter.SrvAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.SrvBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.FullyLinearLayoutManager;
import com.maapuu.mereca.recycleviewutil.ItemTouchHelperCallback;
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
 * 包含服务
 * Created by Jia on 2018/3/19.
 */

public class ProjectContainServiceActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.txt_1)
    TextView txt1;
    @BindView(R.id.txt_2)
    TextView txt2;
    @BindView(R.id.txt_3)
    TextView txt3;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private TextView[] tvs;
    private FullyLinearLayoutManager mLayoutManager;
    private SrvAdapter adapter;
    private List<SrvBean> mList;
    private List<SrvBean> tList;
    private List<SrvBean> gList;
    private List<SrvBean> list;
    private String srv_ids;
    private int srv_type = 1;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_project_contain_service);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("包含服务");
        srv_ids = getIntent().getStringExtra("srv_ids");
        tvs = new TextView[]{txt1,txt2,txt3};
        setHead(0);
        mLayoutManager = new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        //分割线
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, 2,
                getResources().getColor(R.color.background)));
        mList = new ArrayList<>();
        tList = new ArrayList<>();
        gList = new ArrayList<>();
        list = new ArrayList<>();
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
        if(!StringUtils.isEmpty(srv_ids)){
            for (int i = 0; i < list.size(); i++){
                for (int j = 0; j < srv_ids.split(",").length; j++){
                    if(list.get(i).getSrv_id().equals(srv_ids.split(",")[j])){
                        list.get(i).setChecked(true);
                    }
                }
            }
        }
        if (adapter == null) {
            adapter = new SrvAdapter(mContext,list);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        adapter.setOnItemClickListener(new SrvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                list.get(position).setChecked(!list.get(position).isChecked());
                adapter.notifyDataSetChanged();

//                UIUtils.startActivity(mContext,ServiceJobActivity.class);
            }
        });
        ItemTouchHelperCallback helperCallback = new ItemTouchHelperCallback(adapter);
        helperCallback.setSwipeEnable(false);
        helperCallback.setDragEnable(true);
        ItemTouchHelper helper = new ItemTouchHelper(helperCallback);
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_1,R.id.txt_2,R.id.txt_3,R.id.confirm_tv})
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.confirm_tv:
                if(StringUtils.isEmpty(getIds())){
                    ToastUtil.show(mContext,"请选择服务");
                    return;
                }
                it = new Intent();
                it.putExtra("srv_ids",getIds());
                it.putExtra("srv_names",getName());
                setResult(AppConfig.ACTIVITY_RESULTCODE_2,it);
                finish();
                break;
        }
    }
    private String getIds(){
        StringBuilder sb = new StringBuilder("");
        for (SrvBean bean:list) {
            if(bean.isChecked()){
                sb.append(bean.getSrv_id()+",");
            }
        }
        if(sb.toString().endsWith(",")){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    private String getName(){
        StringBuilder sb = new StringBuilder("");
        for (SrvBean bean:list) {
            if(bean.isChecked()){
                sb.append(bean.getSrv_name()+",");
            }
        }
        if(sb.toString().endsWith(",")){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

}
