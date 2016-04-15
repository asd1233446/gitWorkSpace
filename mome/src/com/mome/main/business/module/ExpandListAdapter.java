package com.mome.main.business.module;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class ExpandListAdapter extends BaseExpandableListAdapter {
	
	private List<List<ExpandListCellBase>> childDataList;
	private List<ExpandListCellBase> groupDataList;

	public void setDataList(List<List<ExpandListCellBase>> paramList) {
		this.childDataList = paramList;
	}
	
	public void setGroupDataList(List<ExpandListCellBase> paramList) {
		this.groupDataList = paramList;
	}
	
	@Override
	public int getGroupCount() {
		if(groupDataList != null && !groupDataList.isEmpty()) {
			return groupDataList.size();
		}
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(childDataList != null && !childDataList.isEmpty()) {
			return childDataList.get(groupPosition).size();
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		if(groupDataList != null && !groupDataList.isEmpty()) {
			return groupDataList.get(groupPosition);
		}
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if(childDataList != null && !childDataList.isEmpty()) {
			return childDataList.get(groupPosition).get(childPosition);
		}
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View view = convertView;
		if(groupDataList != null && !groupDataList.isEmpty()) {
			view = groupDataList.get(groupPosition).getGroupView(groupPosition,convertView,isExpanded);
		}
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		if(childDataList != null && !childDataList.isEmpty()) {
			view = childDataList.get(groupPosition).get(childPosition).getChildView(groupPosition,childPosition,convertView);
		}
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
