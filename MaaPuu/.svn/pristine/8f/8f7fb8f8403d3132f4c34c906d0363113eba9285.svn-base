package com.maapuu.mereca.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.adapter.SortAdapter;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.OpenCityBean;
import com.maapuu.mereca.bean.SortModel;
import com.maapuu.mereca.constant.UrlUtils;
import com.maapuu.mereca.util.CharacterParser;
import com.maapuu.mereca.util.FastJsonTools;
import com.maapuu.mereca.util.HttpModeBase;
import com.maapuu.mereca.util.LoginUtil;
import com.maapuu.mereca.util.PinyinComparator;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.util.ToastUtil;
import com.maapuu.mereca.view.SideBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择城市
 * Created by Ji on 2017/12/8.
 */

public class ChooseCityActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.sidrbar)
    SideBar sideBar;
    @BindView(R.id.dialog)
    TextView txtDialog;
    @BindView(R.id.city_list_view)
    ListView cityListView;

    private List<OpenCityBean> citys;

    private SortAdapter adapter;

    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private List<SortModel> SourceDateList;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choose_city);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("选择城市");
        citys = new ArrayList<>();

        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        addListener();
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.cmm_open_city_get(),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        if(!StringUtils.isEmpty(object.optString("result")) && object.optJSONArray("result").length() > 0){
                            citys = FastJsonTools.getPersons(object.optString("result"),OpenCityBean.class);
                            SourceDateList = filledData(citys);
                            // 根据a-z进行排序源数据
                            Collections.sort(SourceDateList, pinyinComparator);
                            adapter = new SortAdapter(mContext, SourceDateList);
                            cityListView.setAdapter(adapter);
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
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                if(citys.size() > 0){
                    int position = adapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        cityListView.setSelection(position);
                    }
                }
            }
        });
        sideBar.setTextView(txtDialog);
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoginUtil.putCityID(mContext,SourceDateList.get(position).getId());
//                it = new Intent();
//                it.putExtra("city_name", SourceDateList.get(position).getName());
//                it.putExtra("filter_city", SourceDateList.get(position).getId());
                setResult(AppConfig.ACTIVITY_RESULTCODE, it);
                finish();
            }
        });
    }

    /**
     * 为ListView填充数据
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<OpenCityBean> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getName());
            sortModel.setId(date.get(i).getId());
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getName());
            String sortString ;
            if(date.get(i).getName().startsWith("重")){
                sortString = "C";
            }else if(date.get(i).getName().startsWith("毫")){
                sortString = "H";
            }else if(date.get(i).getName().startsWith("漯")){
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
    @OnClick({R.id.txt_left})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_left:
                finish();
                break;
        }
    }
}
