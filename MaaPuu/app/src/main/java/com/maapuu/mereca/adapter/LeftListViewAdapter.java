package com.maapuu.mereca.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.bean.CatalogBean;
import com.maapuu.mereca.callback.LeftCallBack;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2016/6/14.
 */
public class LeftListViewAdapter extends RecyclerView.Adapter<LeftListViewAdapter.ViewHolder> {
    private Context mContext;
    private LeftCallBack leftCallBack;
    private List<CatalogBean> listData;
    private Map<Integer, Integer> map;

    public Map<Integer, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Integer> map) {
        this.map = map;
    }

    public LeftListViewAdapter(Context context, List<CatalogBean> listData, LeftCallBack leftCallBack) {
        this.mContext = context;
        this.listData = listData;
        this.leftCallBack = leftCallBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_project_item_v2,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(map.get(position) == 0){
            holder.txtTitle.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }else {
            holder.txtTitle.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        holder.txtTitle.setSelected(map.get(position) == 0 ? false : true);
        holder.txtTitle.setText(listData.get(position).getCatalog_name());
        holder.txtTitle.setOnClickListener(new MyListener(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class MyListener implements View.OnClickListener {
        private int position;
        public MyListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_title:
                    for (int i = 0; i < map.size(); i++){
                        if(i == position){
                            map.put(position, 1);
                        }else {
                            map.put(i, 0);
                        }
                    }
                    notifyDataSetChanged();
                    leftCallBack.changeLayout(position);
                    break;
            }
        }
    }
}
