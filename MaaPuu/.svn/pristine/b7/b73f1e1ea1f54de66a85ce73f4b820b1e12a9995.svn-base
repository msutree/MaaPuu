package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.MyGridView;
import com.maapuu.mereca.view.MyListView;
import com.maapuu.mereca.view.SearchEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/2/26.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.et_search)
    SearchEditText etSearch;
    @BindView(R.id.grid_view)
    MyGridView gridView;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.list_view)
    MyListView listView;

    private List<String> hotList;
    private QuickAdapter<String> hotAdapter;
    private QuickAdapter<String> historyAdapter;
    private List<String> historyList;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
    }

    @Override
    public void initView() {
        hotList = new ArrayList<>();
        historyList = new ArrayList<>();

        if(!StringUtils.isEmpty(LoginUtil.getInfo("historylist"))){
            llHistory.setVisibility(View.VISIBLE);
            historyList = FastJsonTools.getPersons(LoginUtil.getInfo("historylist"),String.class);
        }else {
            llHistory.setVisibility(View.GONE);
        }
        historyAdapter = new QuickAdapter<String>(mContext,R.layout.layout_search_list_item,historyList) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.txt,item);
            }
        };
        listView.setAdapter(historyAdapter);
        initListener();
    }

    private void initListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(etSearch.getText().toString().isEmpty() || etSearch.getText().toString().equals("")){
                        ToastUtil.show(mContext, "请输入搜索关键字");
                    }else {
                        try {
                            JSONArray array = new JSONArray();
                            array.put(0,etSearch.getText().toString());
                            if(historyList != null && historyList.size()>0){
                                for (int i = 0 ;i < historyList.size(); i++){
                                    if(historyList.get(i).equals(etSearch.getText().toString())){
                                        historyList.remove(i);
                                    }
                                    array.put(i+1,historyList.get(i));
                                }
                            }
                            LoginUtil.setInfo("historylist",array.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        it = new Intent(mContext,SearchResultActivity.class);
                        it.putExtra("keyword",etSearch.getText().toString());
                        startActivityForResult(it,1000);
                        finish();
                    }
                }
                return false;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                etSearch.setText(hotList.get(position));
                etSearch.setSelection(hotList.get(position).length());

                try {
                    JSONArray array = new JSONArray();
                    array.put(0,etSearch.getText().toString());
                    if(historyList != null && historyList.size()>0){
                        for (int i = 0 ;i < historyList.size(); i++){
                            if(historyList.get(i).equals(etSearch.getText().toString())){
                                historyList.remove(i);
                            }
                            array.put(i+1,historyList.get(i));
                        }
                    }
                    LoginUtil.setInfo("historylist",array.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                it = new Intent(mContext,SearchResultActivity.class);
                it.putExtra("keyword",etSearch.getText().toString());
                startActivityForResult(it,1000);
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                etSearch.setText(historyList.get(position));
                etSearch.setSelection(historyList.get(position).length());

                try {
                    JSONArray array = new JSONArray();
                    array.put(0,etSearch.getText().toString());
                    if(historyList != null && historyList.size()>0){
                        for (int i = 0 ;i < historyList.size(); i++){
                            if(historyList.get(i).equals(etSearch.getText().toString())){
                                historyList.remove(i);
                            }
                            array.put(i+1,historyList.get(i));
                        }
                    }
                    LoginUtil.setInfo("historylist",array.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                it = new Intent(mContext,SearchResultActivity.class);
                it.putExtra("keyword",etSearch.getText().toString());
                startActivityForResult(it,1000);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.search_keyword_init_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid")),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(object.has("result") && !StringUtils.isEmpty(object.optString("result")) && !(object.opt("result") instanceof Boolean)){
                            hotList.clear();
                            for (int i = 0; i < object.optJSONArray("result").length(); i ++){
                                JSONObject jsonObject = object.optJSONArray("result").optJSONObject(i);
                                hotList.add(jsonObject.optString("keyword"));
                            }
                        }
                        setAdapter();
                    } else {
                        ToastUtil.show(mContext,object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case HttpModeBase.HTTP_ERROR:
                String result_e = (String) msg.obj;
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter() {
        hotAdapter = new QuickAdapter<String>(mContext,R.layout.layout_search_grid_item,hotList) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.txt,item);
            }
        };
        gridView.setAdapter(hotAdapter);
    }

    @Override
    @OnClick({R.id.txt_back,R.id.iv_clear})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_back:
                finish();
                break;
            case R.id.iv_clear:
                LoginUtil.setInfo("historylist","");
                historyList.clear();
                historyAdapter.notifyDataSetChanged();
                llHistory.setVisibility(View.GONE);
                break;
        }
    }
}
