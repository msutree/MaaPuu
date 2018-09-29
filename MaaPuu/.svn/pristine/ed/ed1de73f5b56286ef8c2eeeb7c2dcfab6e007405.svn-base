package com.maapuu.mereca.background.employee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.employee.bean.SalaryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 工资明细
 */
public class SalaryExpandAdapter extends BaseExpandableListAdapter {

    private List<SalaryBean> groupArray;
    private Context mContext;

    public SalaryExpandAdapter(Context context) {
        mContext = context;
        this.groupArray = new ArrayList<>();
    }

    public SalaryExpandAdapter(Context context, List<SalaryBean> groupArray) {
        mContext = context;
        this.groupArray = groupArray;
    }

    @Override
    public int getGroupCount() {
        return groupArray.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<SalaryBean.SrvListBean> list = groupArray.get(groupPosition).getSrv_list();
        if(list == null || list.size() == 0){
            return 0;
        }

        return list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupArray.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupArray.get(groupPosition).getSrv_list().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupHolder holder = null;
        final SalaryBean groupBean = groupArray.get(groupPosition);
        if (view == null) {
            holder = new GroupHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.em_item_salary_group, null);

            holder.wageDay =  view.findViewById(R.id.wage_day);
            holder.amount =  view.findViewById(R.id.amount);
            holder.indicator = view.findViewById(R.id.indicator);
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }

        holder.wageDay.setText(groupBean.getWage_day());
        holder.amount.setText("+"+groupBean.getAmount()+"元");

        if(isExpanded){
            holder.indicator.setImageResource(R.mipmap.sq);
        } else {
            holder.indicator.setImageResource(R.mipmap.zk);
        }

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        SalaryBean.SrvListBean childBean = groupArray.get(groupPosition).getSrv_list().get(childPosition);
        if (view == null) {
            holder = new ChildHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.s_em_item_salary_child, null);

            holder.time = view.findViewById(R.id.srv_begin_time_text);
            holder.srvName = view.findViewById(R.id.srv_name);
            holder.srvCharge = view.findViewById(R.id.srv_charge);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }

        holder.time.setText(childBean.getSrv_end_time_text());
        holder.srvName.setText(childBean.getSrv_name());
        holder.srvCharge.setText("+"+childBean.getSrv_charge());
        return view;
    }

    public void clear() {
        groupArray.clear();
    }

    public void addToList(List<SalaryBean> list) {
        if (groupArray == null) groupArray = new ArrayList<>();
        groupArray.addAll(list);
        notifyDataSetChanged();
    }

    public List<SalaryBean> getList(){
        return groupArray;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        TextView wageDay;
        TextView amount;
        ImageView indicator;
    }

    class ChildHolder {
        TextView time;
        TextView srvName;
        TextView srvCharge;
    }
}
