package com.maapuu.mereca.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.LatestMoBean;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerAdapter;
import com.maapuu.mereca.recycleviewutil.BaseRecyclerHolder;
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
 * Created by dell on 2018/3/5.
 */

public class LatestCommentActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<LatestMoBean> list;
    private BaseRecyclerAdapter<LatestMoBean> adapter;
    private String praise_ids;
    private String comment_ids;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_latest_comment);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("最新评论");
        praise_ids = getIntent().getStringExtra("praise_ids");
        comment_ids = getIntent().getStringExtra("comment_ids");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

        list = new ArrayList<>();
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.mo_news_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),praise_ids,comment_ids),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result")) && object.optJSONArray("result").length() > 0){
                            list = FastJsonTools.getPersons(object.optString("result"),LatestMoBean.class);
                            setAdapter();
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
                ToastUtil.show(mContext,result_e);
                break;
        }
        return false;
    }

    private void setAdapter() {
        adapter = new BaseRecyclerAdapter<LatestMoBean>(mContext,list,R.layout.layout_latest_comment_item) {
            @Override
            public void convert(BaseRecyclerHolder holder, LatestMoBean item, int position, boolean isScrolling) {
                if(list.get(position).getDetail() == null || list.get(position).getDetail().size() == 0){
                    holder.setVisible(R.id.txt_content,true);
                    holder.setVisible(R.id.image,false);
                    holder.setVisible(R.id.iv_is_video,false);
                    holder.setText(R.id.txt_content,item.getMoment_content());
                }else {
                    if(list.get(position).getDetail().get(0).getContent_type().equals("2")){
                        holder.setVisible(R.id.txt_content,false);
                        holder.setVisible(R.id.image,true);
                        holder.setVisible(R.id.iv_is_video,false);
                    }else {
                        holder.setVisible(R.id.txt_content,false);
                        holder.setVisible(R.id.image,true);
                        holder.setVisible(R.id.iv_is_video,true);
                    }
                    holder.setSimpViewImageUri(R.id.image,Uri.parse(item.getDetail().get(0).getContent()));
                }
                if(item.getIs_praise() == 1){
                    holder.setVisible(R.id.txt_comment,false);
                    holder.setVisible(R.id.iv_zan,true);
                }else {
                    holder.setVisible(R.id.txt_comment,true);
                    holder.setVisible(R.id.iv_zan,false);
                }
                holder.setSimpViewImageUri(R.id.iv_icon,Uri.parse(item.getAvatar()));
                holder.setText(R.id.txt_name,item.getNick_name());
                holder.setText(R.id.txt_comment,item.getContent());
                holder.setText(R.id.txt_time,item.getTime_text());

                if(position == list.size() - 1){
                    holder.setVisible(R.id.line,false);
                }else {
                    holder.setVisible(R.id.line,true);
                }
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    @OnClick({R.id.txt_left})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
        }
    }
}
