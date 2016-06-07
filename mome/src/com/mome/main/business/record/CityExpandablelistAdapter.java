package com.mome.main.business.record;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import com.jessieray.api.model.Friend;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.AddttentionRequest;
import com.jessieray.api.service.CancelttentionRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.Tools;
import com.mome.pinyin.AssortPinyinList;
import com.mome.pinyin.LanguageComparator_CN;

import net.sourceforge.pinyin4j.PinyinHelper;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

public class CityExpandablelistAdapter extends BaseAdapter implements
StickyListHeadersAdapter, SectionIndexer {

	private List<String> childList;
	private AssortPinyinList assort = new AssortPinyinList();
	private Map<String , String> map=new HashMap<String , String>();
	public List<String> getChildList() {
		return childList;
	}

	public void setChildList(List<String> childList) {
		this.childList = childList;
		sort();
	}
	
		
	public  List<String> sort() {
		assort.getHashList().getKeyArr().clear();
		for (String str :childList) {
			assort.getHashList().add(str);
		}
		for(String i:assort.getHashList().getKeyArr()){
			assort.getHashList().setFristCharList(assort.getFirstChar(i));
		}
          return assort.getHashList().getKeyArr();
	}


	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	public AssortPinyinList getAssort() {
		return assort;
	}

	@LayoutInject(layout = R.layout.city_group_item)
	class GroupViewHolder {
		@ViewInject(id = R.id.name)
		public TextView name;
	}

	@LayoutInject(layout = R.layout.city_list_item)
	class ChildViewHolder {
		@ViewInject(id = R.id.name)
		public TextView name;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return childList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return childList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		ChildViewHolder viewHolder;
		if (view == null) {
			viewHolder = new ChildViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) view.getTag();
		}
		viewHolder.name.setText(assort.getHashList().getKeyIndex(position));
		
		return view;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		// TODO Auto-generated method stub
		return sectionIndex;
	}

	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		GroupViewHolder viewHolder;
		if (view == null) {
			viewHolder = new GroupViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (GroupViewHolder) view.getTag();
		}

	viewHolder.name.setText(assort.getFirstChar(assort.getHashList().getKeyIndex(position)));
		return view;
	}

	@Override
	public long getHeaderId(int position) {
		// TODO Auto-generated method stub	
		String headId=assort.getFirstChar(assort.getHashList().getKeyIndex(position));
				return headId.charAt(0);
	}
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<String> list) {
		if (list == null) {
			this.childList = new ArrayList<String>();
		} else {
			setChildList(list);
		}
		notifyDataSetChanged();
	}

	

	
}
