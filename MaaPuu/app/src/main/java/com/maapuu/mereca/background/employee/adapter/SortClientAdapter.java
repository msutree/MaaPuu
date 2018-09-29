package com.maapuu.mereca.background.employee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.maapuu.mereca.R;
import com.maapuu.mereca.background.employee.bean.ClientBean;
import com.maapuu.mereca.util.StringUtils;
import com.maapuu.mereca.view.CircleImgView;

import java.util.List;


/**
 * 客户列表Adapter  废弃
 */
public class SortClientAdapter extends BaseAdapter implements SectionIndexer{
	private List<ClientBean> list = null;
	private Context mContext;


	public SortClientAdapter(Context mContext, List<ClientBean> list) {
		this.mContext = mContext;
		this.list = list;
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder;
		final ClientBean bean = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.em_item_client, null);
			viewHolder.tvLetter =  view.findViewById(R.id.first_letter);
			viewHolder.ivIcon = view.findViewById(R.id.user_icon);
			viewHolder.tvTitle = view.findViewById(R.id.title);
			viewHolder.sexTv = view.findViewById(R.id.c_sex);

			viewHolder.tagLt = view.findViewById(R.id.c_tag_lt);
			viewHolder.tagImg = view.findViewById(R.id.c_tag_img);
			viewHolder.tagTxt = view.findViewById(R.id.c_tag_txt);

			viewHolder.tel = view.findViewById(R.id.c_tel);
			viewHolder.msg = view.findViewById(R.id.c_msg);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

//		CommonUtils.loadImg(mContext, UrlUtils.getImageUrl(bean.getPhoto()),viewHolder.ivIcon,R.mipmap.temp_user);

		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(bean.getFirstLetter());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		viewHolder.tvTitle.setText(bean.getName());
		viewHolder.sexTv.setTypeface(StringUtils.getFont(mContext));
		viewHolder.sexTv.setText("\ue694");//女 &#xe694;   男&#xe695;

		//设置背景  黑灰色  橙色
		viewHolder.tagLt.setBackgroundResource(R.drawable.bg_solid_black_grey_radius20);
		viewHolder.tagImg.setTypeface(StringUtils.getFont(mContext));
		viewHolder.tagImg.setText("\ue699");//预约 &#xe699;   生日 &#xe697;
		viewHolder.tagTxt.setText("预约");

		//TODO  测试
		if(position == 2 || position == 3){
			viewHolder.tagLt.setBackgroundResource(R.drawable.bg_solid_orange_radius20);
			viewHolder.tagImg.setText("\ue697");
			viewHolder.tagTxt.setText("生日");

			viewHolder.tel.setText("\ue697");
		}

		viewHolder.tel.setTypeface(StringUtils.getFont(mContext));
		viewHolder.msg.setTypeface(StringUtils.getFont(mContext));
		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;
		CircleImgView ivIcon;
		TextView tvTitle;
		TextView sexTv;

		LinearLayout tagLt;
		TextView tagImg;
		TextView tagTxt;

		TextView tel;
		TextView msg;
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getFirstLetter().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getFirstLetter();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateList(List<ClientBean> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public void addToList(List<ClientBean> list){
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	public void clear(){
		if(list != null){
			list.clear();
			notifyDataSetChanged();
		}
	}

	public List<ClientBean> getList(){
		return list;
	}
}