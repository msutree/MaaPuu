package com.maapuu.mereca.fragment.homechild;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.GoodsListActivity;
import com.maapuu.mereca.adapter.help.BaseAdapterHelper;
import com.maapuu.mereca.adapter.help.QuickAdapter;
import com.maapuu.mereca.base.BaseFragment;
import com.maapuu.mereca.bean.CatalogBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dell on 2018/2/22.
 * 主页——商品
 */

public class HomeGoodsFragment extends BaseFragment {
    @BindView(R.id.list_view)
    MyListView listView;

    private List<CatalogBean> list;
    private QuickAdapter<CatalogBean> adapter;
    private String shop_id;

    @Override
    protected int setContentViewById() {
        Bundle bundle=getArguments();
        //判断需写
        if(bundle!=null) {
            shop_id = bundle.getString("shop_id");
        }
        return R.layout.fragment_home_goods;
    }

    public void refresh(String shop_id){
        this.shop_id = shop_id;
        initData();
    }

    @Override
    protected void initView(View v) {
        list = new ArrayList<>();
    }

    @Override
    protected void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.shop_catalog_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),shop_id,"2"),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(object.optJSONArray("result").length() > 0){
                            list = FastJsonTools.getPersons(object.optString("result"),CatalogBean.class);
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
        adapter = new QuickAdapter<CatalogBean>(mContext,R.layout.layout_home_project_item,list) {
            @Override
            protected void convert(BaseAdapterHelper helper, CatalogBean item) {
                helper.setSimpViewImageUri(R.id.image, Uri.parse(item.getCatalog_img()));
                helper.setText(R.id.txt_title,item.getCatalog_name());
                helper.setText(R.id.txt_region,item.getPrice_region());
                helper.setText(R.id.txt_item_num,"共"+item.getItem_num()+"项服务");
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                it = new Intent(mContext, GoodsListActivity.class);
                it.putExtra("shop_id",shop_id);
                it.putExtra("catalog_id",list.get(i).getCatalog_id());
                it.putExtra("pos",i);
                it.putExtra("flList", (Serializable) list);
                startActivity(it);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
