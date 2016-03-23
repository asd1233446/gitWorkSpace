package com.mome.main.business.userinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jessieray.api.model.Friend;
import com.mome.main.R;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.pinyin.AssortPinyinList;
import com.mome.pinyin.LanguageComparator_CN;

import net.sourceforge.pinyin4j.PinyinHelper;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsExpandablelistAdapter extends BaseExpandableListAdapter {

	// �ַ�
	private ArrayList<Friend> childList;

	private AssortPinyinList assort = new AssortPinyinList();
	public ArrayList<Friend> getChildList() {
		return childList;
	}

	public void setChildList(ArrayList<Friend> childList) {
		this.childList = childList;
		sort() ;
	}

	// ��������
	private LanguageComparator_CN cnSort = new LanguageComparator_CN();

//	public FriendsExpandablelistAdapter(Context context, List<String> strList) {
//		this.strList = strList;
//		if (strList == null) {
//			strList = new ArrayList<String>();
//		}
//		sort();

//	}
		
	private void sort() {
		for (Friend friend :childList) {
			assort.getHashList().add(friend.getNickname());
		}
		assort.getHashList().sortKeyComparator(cnSort);
		for (int i = 0, length = assort.getHashList().size(); i < length; i++) {
			Collections.sort((assort.getHashList().getValueListIndex(i)),
					cnSort);
		}

	}

	public Object getChild(int group, int child) {
		// TODO Auto-generated method stub
		return assort.getHashList().getValueIndex(group, child);
	}

	public long getChildId(int group, int child) {
		// TODO Auto-generated method stub
		return child;
	}

	public View getChildView(int group, int child, boolean arg2,
			View contentView, ViewGroup arg4) {
		// TODO Auto-generated method stub
		View view = contentView;
		ChildViewHolder viewHolder;
		if (view == null) {
			viewHolder = new ChildViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) view.getTag();
		}
		//viewHolder.headIcon.seti
		viewHolder.name.setText(assort.getHashList().getValueIndex(group, child));
		//viewHolder.realtion_rb
		return view;
	}

	public int getChildrenCount(int group) {
		// TODO Auto-generated method stub
		return assort.getHashList().getValueListIndex(group).size();
	}

	public Object getGroup(int group) {
		// TODO Auto-generated method stub
		return assort.getHashList().getValueListIndex(group);
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return assort.getHashList().size();
	}

	public long getGroupId(int group) {
		// TODO Auto-generated method stub
		return group;
	}

	public View getGroupView(int group, boolean arg1, View contentView,
			ViewGroup arg3) {
		View view = contentView;
		GroupViewHolder viewHolder;
		if (view == null) {
			viewHolder = new GroupViewHolder();
			view = InjectProcessor.injectListViewHolder(viewHolder);
			view.setTag(viewHolder);
		} else {
			viewHolder = (GroupViewHolder) view.getTag();
		}
		viewHolder.name.setText(assort.getFirstChar(assort.getHashList()
		.getValueIndex(group, 0)));
		return contentView;
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

	@LayoutInject(layout = R.layout.list_group_item)
	class GroupViewHolder {
		@ViewInject(id = R.id.name)
		public TextView name;
	}

	@LayoutInject(layout = R.layout.friend_list_item)
	class ChildViewHolder {
		@ViewInject(id = R.id.head)
		public com.mome.main.netframe.volley.toolbox.NetworkImageView headIcon;
		@ViewInject(id = R.id.name)
		public TextView name;
		@ViewInject(id = R.id.realtion_rb)
		public RadioButton realtion_rb;
	}

}
