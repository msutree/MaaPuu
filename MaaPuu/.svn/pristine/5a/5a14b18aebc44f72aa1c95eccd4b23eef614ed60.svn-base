package com.maapuu.mereca.background.shop.dialog;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.util.StringUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zyyoona7.lib.BaseCustomPopup;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * 订单筛选
 * Created by Jia on 2018/3/8.
 */

public class OrderFilter extends BaseCustomPopup implements View.OnClickListener {
    View v;
    TagFlowLayout tagFlowLayout,tagFlowLayout1,tagFlowLayout2;
    TextView resetTv, confirmTv;

    Context context;
    TagAdapter adapter,adapter1,adapter2;

    List<String> dataList,list1,list2;
    ConfirmCall confirmCall;

    //String shop_id = "0"; //店铺id，全部店铺传0
    //String date = ""; //日期过滤，如：2018-04-18
    String status = ""; //订单状态：待使用,待评价,已完成，状态传中文
    String complaint = ""; //投诉：1投诉建议,2已处理
    String refund = ""; //退款：1退款申请,2退款成功,4商家拒绝

    String[] arr1 = {"投诉建议","已处理"};
    String[] arr2 = {"退款申请","退款成功","商家拒绝"};

    public OrderFilter(Context context, List<String> dataList,String status,String complaint,String refund, ConfirmCall confirmCall) {
        super(context);
        this.context = context;
        this.dataList = dataList;
        this.status = StringUtils.isEmpty(status)?"":status+",";
        this.complaint = StringUtils.isEmpty(complaint)?"":complaint+",";
        this.refund = StringUtils.isEmpty(refund)?"":refund+",";
        this.confirmCall = confirmCall;
        this.list1 = Arrays.asList(arr1);
        this.list2 = Arrays.asList(arr2);
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.pop_layout_order_filter, ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        //setFocusAndOutsideEnable(true).setBackgroundDimEnable(true).setDimValue(0.2f);
        setFocusAndOutsideEnable(true);
    }

    @Override
    protected void initViews(View view) {
        v = getView(R.id.view);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        resetTv = getView(R.id.pop_reset_tv);
        confirmTv = getView(R.id.pop_confirm_tv);

        tagFlowLayout = getView(R.id.pop_tag);
        tagFlowLayout1 = getView(R.id.pop_tag_1);
        tagFlowLayout2 = getView(R.id.pop_tag_2);
        adapter = new TagAdapter<String>(dataList) {
            @Override
            public View getView(FlowLayout parent, int position, String bean) {
                TextView textView = (TextView) LayoutInflater.from(context)
                        .inflate(R.layout.pop_tag_item, parent, false);
                textView.setText(bean);
                return textView;
            }
        };
        if(!StringUtils.isEmpty(status)){
            Set<Integer> set = new HashSet<>();
            for (int j = 0; j < dataList.size(); j++){
                for (int i = 0; i < status.split(",").length; i++){
                    if(dataList.get(j).equals(status.split(",")[i])){
                        set.add(j);
                    }
                }
            }
            adapter.setSelectedList(set);
        }
        tagFlowLayout.setAdapter(adapter);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(status.contains(dataList.get(position))){
                    status = status.replace(dataList.get(position) + ",","");
                }else {
                    status += dataList.get(position) + ",";
                }
                return false;
            }
        });

        adapter1 = new TagAdapter<String>(list1) {
            @Override
            public View getView(FlowLayout parent, int position, String bean) {
                TextView textView = (TextView) LayoutInflater.from(context)
                        .inflate(R.layout.pop_tag_item, parent, false);
                textView.setText(bean);
                return textView;
            }
        };

        if(!StringUtils.isEmpty(complaint)){
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < complaint.split(",").length; i++){
                set.add((Integer.parseInt(complaint.split(",")[i]) - 1));
            }
            adapter1.setSelectedList(set);
        }
        tagFlowLayout1.setAdapter(adapter1);
        tagFlowLayout1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(complaint.contains(String.valueOf(position+1))){
                    complaint = complaint.replace(String.valueOf(position+1) + ",","");
                }else {
                    complaint += String.valueOf(position+1) + ",";
                }
                return false;
            }
        });

        adapter2 = new TagAdapter<String>(list2) {
            @Override
            public View getView(FlowLayout parent, int position, String bean) {
                TextView textView = (TextView) LayoutInflater.from(context)
                        .inflate(R.layout.pop_tag_item, parent, false);
                textView.setText(bean);
                return textView;
            }
        };

        if(!StringUtils.isEmpty(refund)){
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < refund.split(",").length; i++){
                set.add((Integer.parseInt(refund.split(",")[i]) - 1));
            }
            adapter2.setSelectedList(set);
        }
        tagFlowLayout2.setAdapter(adapter2);
        tagFlowLayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(refund.contains(String.valueOf(position+1))){
                    refund = refund.replace(String.valueOf(position+1) + ",","");
                }else {
                    refund += String.valueOf(position+1) + ",";
                }
                return false;
            }
        });

        resetTv.setOnClickListener(this);
        confirmTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_reset_tv: //重置
                Set<Integer> set = new HashSet<>();
                adapter.setSelectedList(set);
                adapter1.setSelectedList(set);
                adapter2.setSelectedList(set);
                status = "";
                complaint = "";
                refund ="";
//                if (confirmCall != null) {
//                    confirmCall.onCall(status,complaint,refund);
//                }
//                dismiss();

                break;
            case R.id.pop_confirm_tv:  // 确定 筛选
                if(status.endsWith(",")){
                    status = status.substring(0,status.length()-1);
                }
                if(complaint.endsWith(",")){
                    complaint = complaint.substring(0,complaint.length()-1);
                }
                if(refund.endsWith(",")){
                    refund = refund.substring(0,refund.length()-1);
                }
                if (confirmCall != null) {
                    confirmCall.onCall(status,complaint,refund);
                }
                dismiss();

                break;
        }
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            showAtLocation(anchor, Gravity.NO_GRAVITY, 0, a[1] + anchor.getHeight() + 0);
        } else {
            super.showAsDropDown(anchor);
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            showAtLocation(anchor, Gravity.NO_GRAVITY, xoff, a[1] + anchor.getHeight() + yoff);
        } else {
            super.showAsDropDown(anchor, xoff, yoff);
        }
    }

    public interface ConfirmCall {
        void onCall(String status,String complaint,String refund);
    }

}
