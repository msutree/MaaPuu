package com.maapuu.mereca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.MyFriendsSortAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.FriendBean;
import com.maapuu.mereca.bean.SortModel;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.CharacterParser;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.KeyBoardUtils;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PinyinComparator;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.SearchEditText;
import com.maapuu.mereca.view.SideBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 好友
 * Created by Ji on 2017/12/8.
 */

public class MyFriendsActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.et_search)
    SearchEditText etSearch;
    @BindView(R.id.sidrbar)
    SideBar sideBar;
    @BindView(R.id.dialog)
    TextView txtDialog;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.ll_has)
    LinearLayout llHas;

    private List<FriendBean> list;

    private MyFriendsSortAdapter adapter;

    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private List<SortModel> SourceDateList;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_friends);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("好友");
        txtRight.setTypeface(StringUtils.getFont(mContext));
        list = new ArrayList<>();

        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        addListener();
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1,UrlUtils.friend_list_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid")),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        list = FastJsonTools.getPersons(object.optString("result"),FriendBean.class);
                        if(list != null && list.size() > 0){
                            llHas.setVisibility(View.GONE);
                            SourceDateList = filledData(list);
                            // 根据a-z进行排序源数据
                            Collections.sort(SourceDateList, pinyinComparator);
                            adapter = new MyFriendsSortAdapter(mContext, SourceDateList);
                            listView.setAdapter(adapter);
                        }else {
                            llHas.setVisibility(View.VISIBLE);
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

    public void addListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    KeyBoardUtils.closeKeybord(v,mContext);
                    List<SortModel> showList = new ArrayList<>();
                    if(etSearch.getText().toString().length()>0){
                        for(int i=0;i<SourceDateList.size();i++){
                            if(SourceDateList.get(i).getName().contains(etSearch.getText().toString())){
                                showList.add(SourceDateList.get(i));
                            }
                        }
                    }else{
                        showList.addAll(SourceDateList);
                    }
                    adapter = new MyFriendsSortAdapter(mContext, showList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                if(list != null && list.size() > 0){
                    int position = adapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        listView.setSelection(position);
                    }
                }
            }
        });
        sideBar.setTextView(txtDialog);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(AppConfig.mIMKit == null){
                    AppConfig.mIMKit = YWAPI.getIMKitInstance(LoginUtil.getInfo("uid"), AppConfig.APP_KEY);
                }
                Intent chat = AppConfig.mIMKit.getChattingActivityIntent(SourceDateList.get(position).getId());
                chat.putExtra("uid",SourceDateList.get(position).getId());
                startActivityForResult(chat,AppConfig.ACTIVITY_REQUESTCODE);
            }
        });
    }

    /**
     * 为ListView填充数据
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<FriendBean> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setId(date.get(i).getFriend_uid());
            sortModel.setName(date.get(i).getNick_name());
            sortModel.setBack(date.get(i).getSignature());
            sortModel.setBack1(date.get(i).getAvatar());
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getNick_name());
            String sortString ;
            if(date.get(i).getNick_name().startsWith("重")){
                sortString = "C";
            }else if(date.get(i).getNick_name().startsWith("毫")){
                sortString = "H";
            }else if(date.get(i).getNick_name().startsWith("漯")){
                sortString = "L";
            }else {
                sortString = pinyin.substring(0, 1).toUpperCase();
            }

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_right,R.id.txt_empty_btn})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_right:
            case R.id.txt_empty_btn:
                startActivity(new Intent(mContext,AddFriendsActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                list.clear();
                SourceDateList.clear();
                initData();
                break;
        }
    }
}
