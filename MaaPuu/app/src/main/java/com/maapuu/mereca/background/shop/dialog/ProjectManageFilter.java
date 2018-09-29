package com.maapuu.mereca.background.shop.dialog;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * 项目管理筛选
 * Created by Jia on 2018/3/20.
 */

public class ProjectManageFilter extends BaseCustomPopup implements View.OnClickListener {
    View v;
    TextView resetTv, confirmTv;
    TagFlowLayout categoryTfl;

    TagFlowLayout otherTfl;

    Context context;
    TagAdapter categoryAdapter;
    List<String> categoryList;

    TagAdapter otherAdapter;
    List<String> otherList;

    ConfirmCall confirmCall;

    String[] arr1 = {"促销项目","单次项目","单项套餐","多项套餐"};
    String[] arr2 = {"上架","下架","置顶项目","非置顶项目"};

    private String project_type ;
    private String other_type;

    public ProjectManageFilter(Context context, List<String> dataList1, List<String> dataList2,ConfirmCall confirmCall) {
        super(context);
        this.context = context;
        this.categoryList = dataList1;
        this.otherList = dataList2;
        this.confirmCall = confirmCall;
    }

    public ProjectManageFilter(Context context,String project_type,String other_type, ConfirmCall confirmCall) {
        super(context);
        this.context = context;
        this.confirmCall = confirmCall;
        this.project_type = StringUtils.isEmpty(project_type)?"":project_type+",";
        this.other_type = StringUtils.isEmpty(other_type)?"":other_type+",";
        this.categoryList = Arrays.asList(arr1);
        this.otherList = Arrays.asList(arr2);
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.pop_layout_project_manage_filter, ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
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
        resetTv.setOnClickListener(this);
        confirmTv.setOnClickListener(this);

        //项目类别
        categoryTfl = getView(R.id.pop_tag_project_category);
        categoryAdapter = new TagAdapter<String>(categoryList) {
            @Override
            public View getView(FlowLayout parent, int position, String bean) {
                TextView textView = (TextView) LayoutInflater.from(context)
                        .inflate(R.layout.pop_tag_item, parent, false);
                textView.setText(bean);
                return textView;
            }
        };
        if(!StringUtils.isEmpty(project_type)){
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < project_type.split(",").length; i++){
                set.add((Integer.parseInt(project_type.split(",")[i]) - 1));
            }
            categoryAdapter.setSelectedList(set);
        }
        categoryTfl.setAdapter(categoryAdapter);
        categoryTfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(project_type.contains(String.valueOf(position+1))){
                    project_type = project_type.replace(String.valueOf(position+1) + ",","");
                }else {
                    project_type += String.valueOf(position+1) + ",";
                }
                return false;
            }
        });

        //其他
        otherTfl = getView(R.id.pop_tag_other);
        otherAdapter = new TagAdapter<String>(otherList) {
            @Override
            public View getView(FlowLayout parent, int position, String bean) {
                TextView textView = (TextView) LayoutInflater.from(context)
                        .inflate(R.layout.pop_tag_item, parent, false);
                textView.setText(bean);
                return textView;
            }
        };

        if(!StringUtils.isEmpty(other_type)){
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < other_type.split(",").length; i++){
                set.add((Integer.parseInt(other_type.split(",")[i]) - 1));
            }
            otherAdapter.setSelectedList(set);
        }
//        if(!StringUtils.isEmpty(other_type)){
//            Set<Integer> set = new HashSet<>();
//            set.add((Integer.parseInt(other_type) - 1));
//            otherAdapter.setSelectedList(set);
//        }
        otherTfl.setAdapter(otherAdapter);
        otherTfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(other_type.contains(String.valueOf(position+1))){
                    other_type = other_type.replace(String.valueOf(position+1) + ",","");
                }else {
                    other_type += String.valueOf(position+1) + ",";
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_reset_tv: //重置
                other_type = "";
                project_type = "";
                Set<Integer> set = new HashSet<>();
                categoryAdapter.setSelectedList(set);
                otherAdapter.setSelectedList(set);
                break;
            case R.id.pop_confirm_tv:  // 确定 筛选
                if(other_type.endsWith(",")){
                    other_type = other_type.substring(0,other_type.length()-1);
                }
                if(project_type.endsWith(",")){
                    project_type = project_type.substring(0,project_type.length()-1);
                }
                if (confirmCall != null) {
                    confirmCall.onCall(project_type, other_type);
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
//        if(Build.VERSION.SDK_INT >= 24) {
//            Rect rect = new Rect();
//            anchor.getGlobalVisibleRect(rect);
//            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
//            setHeight(h);
//        }
//        super.showAsDropDown(anchor);
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
//        if(Build.VERSION.SDK_INT >= 24) {
//            Rect rect = new Rect();
//            anchor.getGlobalVisibleRect(rect);
//            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
//            setHeight(h);
//        }
//        super.showAsDropDown(anchor, xoff, yoff);
    }


    public interface ConfirmCall {
        void onCall(String project, String other);
    }

}
