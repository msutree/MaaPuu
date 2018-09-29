package com.maapuu.mereca.background.shop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.maapuu.mereca.R;
import com.maapuu.mereca.activity.ChooseTaocanActivity;
import com.maapuu.mereca.background.shop.adapter.SelectItemAdapter;
import com.maapuu.mereca.background.shop.bean.ContainItemsBean;
import com.maapuu.mereca.base.AppConfig;
import com.maapuu.mereca.base.BaseActivity;
import com.maapuu.mereca.bean.ShopBean;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dell on 2018/8/6.
 */

public class EntryTaocanActivity extends BaseActivity {
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.txt_birth)
    TextView txtBirth;
    @BindView(R.id.txt_shop)
    TextView txtShop;
    @BindView(R.id.txt_taocan_type)
    TextView txtTaocanType;
    @BindView(R.id.txt_taocan_name)
    TextView txtTaocanName;
    @BindView(R.id.txt_emploee_name)
    TextView txtEmploeeName;

    private String custom_uid;
    private List<ShopBean> shopList;
    private String shop_id = "";
    private int pack_type = -1;
    private List<String> typeList = new ArrayList<>();
    private List<ContainItemsBean> list;
    private SelectItemAdapter adapter;
    private String sel_item_id;

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shop_activity_entry_taocan);
    }

    @Override
    public void initView() {
        txtLeft.setTypeface(StringUtils.getFont(mContext));
        txtTitle.setText("录入套餐");
        custom_uid = getIntent().getStringExtra("custom_uid");
        shopList = new ArrayList<>();
        list = new ArrayList<>();
        typeList.add("单项套餐");
        typeList.add("多项套餐");
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext,1,false));
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL,2
                ,getResources().getColor(R.color.background)));
        adapter = new SelectItemAdapter(mContext,list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SelectItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onMinusClick(int position) {
                if(list.get(position).getNum() == 0){
                    return;
                }
                list.get(position).setNum(list.get(position).getNum()-1);
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onPlusClick(int position) {
                list.get(position).setNum(list.get(position).getNum()+1);
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onTextChange(int position) {

            }
        });
    }

    @Override
    public void initData() {
        mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_1, UrlUtils.s_input_member_init_get(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),custom_uid),true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HttpModeBase.HTTP_REQUESTCODE_1:
                try {
                    JSONObject object = new JSONObject((String) msg.obj);
                    if (object.optInt("status") == 1) {
                        JSONObject resultObj = object.optJSONObject("result");
                        if(resultObj.has("shop_list") && resultObj.optJSONArray("shop_list").length() > 0){
                            shopList = FastJsonTools.getPersons(resultObj.optString("shop_list"),ShopBean.class);
                        }
                        txtEmploeeName.setText(resultObj.optString("jbr"));
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
                        ToastUtil.show(mContext,"录入成功");
                        setResult(AppConfig.ACTIVITY_RESULTCODE);
                        finish();
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
                        if(!(object.opt("result") instanceof Boolean) && object.optJSONArray("result").length() > 0){
                            list.clear();
                            list.addAll(FastJsonTools.getPersons(object.optString("result"),ContainItemsBean.class));
                            adapter.notifyDataSetChanged();
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

    private String itemDatas(){
        JSONArray array = new JSONArray();
        int pos = 0;
        try {
            for (int i = 0; i < list.size(); i++){
                if(list.get(i).getNum() != 0){
                    JSONObject object = new JSONObject();
                    object.put("item_id",list.get(i).getItem_id());
                    object.put("num",list.get(i).getNum());
                    array.put(pos,object);
                    pos++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array.toString();
    }

    private int getNums(){
        int nums = 0;
        for (int i = 0; i < list.size(); i++){
            nums += list.get(i).getNum();
        }
        return nums;
    }

    @Override
    @OnClick({R.id.txt_left,R.id.txt_save,R.id.txt_add,R.id.txt_birth,R.id.txt_shop,R.id.txt_taocan_type,R.id.txt_taocan_name})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_left:
                finish();
                break;
            case R.id.txt_save:
                if(StringUtils.isEmpty(etName.getText().toString())){
                    ToastUtil.show(mContext,"请输入姓名");
                    return;
                }
                if(StringUtils.isEmpty(etPhone.getText().toString())){
                    ToastUtil.show(mContext,"请输入联系方式");
                    return;
                }
                if(StringUtils.isEmpty(txtBirth.getText().toString())){
                    ToastUtil.show(mContext,"请选择生日");
                    return;
                }
                if(StringUtils.isEmpty(shop_id)){
                    ToastUtil.show(mContext,"请选择选择店铺");
                    return;
                }
                if(StringUtils.isEmpty(txtTaocanType.getText().toString())){
                    ToastUtil.show(mContext,"请选择套餐类型");
                    return;
                }
                if(StringUtils.isEmpty(txtTaocanName.getText().toString())){
                    ToastUtil.show(mContext,"请选择套餐");
                    return;
                }
                if(getNums() == 0){
                    ToastUtil.show(mContext,"套餐项目数量不能为0");
                    return;
                }
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_2,UrlUtils.s_input_pack_set(LoginUtil.getInfo("token"),LoginUtil.getInfo("uid"),
                        custom_uid,shop_id,etName.getText().toString(),etPhone.getText().toString(),txtBirth.getText().toString(),sel_item_id,itemDatas()),true);
                break;
            case R.id.txt_add:
                if(StringUtils.isEmpty(shop_id)){
                    ToastUtil.show(mContext,"请选择选择店铺");
                    return;
                }
                it = new Intent(mContext,SelectItemsActivity.class);
                it.putExtra("shop_id",shop_id);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
            case R.id.txt_birth:
                StringUtils.closeKeyBorder(mContext,view);
                showTimePv();
                break;
            case R.id.txt_shop:
                if(shopList != null && shopList.size() > 0){
                    StringUtils.closeKeyBorder(mContext,view);
                    showPv();
                }
                break;
            case R.id.txt_taocan_type:
                StringUtils.closeKeyBorder(mContext,view);
                showTypePv();
                break;
            case R.id.txt_taocan_name:
                if(StringUtils.isEmpty(shop_id)){
                    ToastUtil.show(mContext,"请选择选择店铺");
                    return;
                }
                if(StringUtils.isEmpty(txtTaocanType.getText().toString())){
                    ToastUtil.show(mContext,"请选择套餐类型");
                    return;
                }
                it = new Intent(mContext,ChooseTaocanActivity.class);
                it.putExtra("shop_id",shop_id);
                it.putExtra("pack_type",pack_type);
                startActivityForResult(it, AppConfig.ACTIVITY_REQUESTCODE);
                break;
        }
    }

    TimePickerView pvCustomTime;
    private void showTimePv() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        pvCustomTime = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                txtBirth.setText(StringUtils.getTime(date));
            }
        })
                .setCancelColor(getResources().getColor(R.color.text_99))
                .setSubmitColor(getResources().getColor(R.color.main_color))
                .setDate(selectedDate)
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTextColorCenter(Color.parseColor("#333333"))//设置选中项的颜色
                .setDividerColor(Color.parseColor("#EEEEEE"))
                .build();
        pvCustomTime.show();
    }

    OptionsPickerView pickerView;
    private void showPv(){
        pickerView = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                if(!shop_id.equals(shopList.get(options1).getShop_id())){
                    shop_id = shopList.get(options1).getShop_id();
                    txtShop.setText(shopList.get(options1).getShop_name());
                    pack_type = -1;
                    txtTaocanType.setText("");
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        })
                .setSubCalSize(16)//确定和取消文字大小
                .setSubmitColor(getResources().getColor(R.color.main_color))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.text_99))//取消按钮文字颜色
                .setContentTextSize(16)//滚轮文字大小
                .setLinkage(true)//设置是否联动，默认true
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();
        pickerView.setPicker(shopList,null,null);//添加数据源
        pickerView.show();
    }

    private void showTypePv(){
        pickerView = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                if(pack_type != options1 + 2){
                    pack_type = options1+2;
                    txtTaocanType.setText(typeList.get(options1));
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        })
                .setSubCalSize(16)//确定和取消文字大小
                .setSubmitColor(getResources().getColor(R.color.main_color))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.text_99))//取消按钮文字颜色
                .setContentTextSize(16)//滚轮文字大小
                .setLinkage(true)//设置是否联动，默认true
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .build();
        pickerView.setPicker(typeList,null,null);//添加数据源
        pickerView.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case AppConfig.ACTIVITY_RESULTCODE:
                List<ContainItemsBean> tempList = (List<ContainItemsBean>) data.getSerializableExtra("templist");
                if(tempList != null && tempList.size() > 0){
                    list.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }
                break;
            case AppConfig.ACTIVITY_RESULTCODE_1:
                sel_item_id = data.getStringExtra("item_id");
                txtTaocanName.setText(data.getStringExtra("item_name"));
                mHttpModeBase.xPost(HttpModeBase.HTTP_REQUESTCODE_3,UrlUtils.s_input_item_sub_list_get(LoginUtil.getInfo("token"),
                        LoginUtil.getInfo("uid"),shop_id,sel_item_id),true);
                break;
        }
    }
}
