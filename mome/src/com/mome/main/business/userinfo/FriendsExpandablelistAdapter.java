package com.mome.main.business.userinfo;

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

public class FriendsExpandablelistAdapter extends BaseAdapter implements
StickyListHeadersAdapter, SectionIndexer {

	private ArrayList<Friend> childList;
	private AssortPinyinList assort = new AssortPinyinList();
	private Map<String , Friend> map=new HashMap<String , Friend>();
	private Friend friend;
	public ArrayList<Friend> getChildList() {
		return childList;
	}

	public void setChildList(ArrayList<Friend> childList) {
		this.childList = childList;
		sort() ;
	}
	
	/*
	 * 1代表关注 2，代表好友
	 * */
	private String relationType="0";	
	public void setInfo(String relationType ) {
		this.relationType = relationType;
	}

	private LanguageComparator_CN cnSort = new LanguageComparator_CN();
		
	private void sort() {
		assort.getHashList().getKeyArr().clear();
		for (Friend friend :childList) {
			map.put(friend.getNickname(), friend);
			assort.getHashList().add(friend.getNickname());
		}
	
		assort.getHashList().sortKeyComparator(cnSort);
		for(String i:assort.getHashList().getKeyArr()){
			assort.getHashList().setFristCharList(assort.getFirstChar(i));
		}

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
		public CheckBox realtion_rb;    
	}
	 class lvButtonListener implements OnClickListener {
	        private int position;

	        lvButtonListener(int pos) {
	            position = pos;
	        }
	        
	        @Override
	        public void onClick(View v) {
	        	CheckBox a=(CheckBox) v;
	        	Log.e(" realtion_rb.isChecked()", a.isChecked()+"");
				if(!a.isChecked())
					cancelAddttention(a,position);
				else
					addAddttention(a,position);
			}
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
		friend=map.get(assort.getHashList().getKeyIndex(position));
		viewHolder.headIcon.setImageUrl(friend.getAvatar(), HttpRequest.getInstance().imageLoader);
		viewHolder.name.setText(friend.getNickname());
		viewHolder.realtion_rb.setVisibility(relationType.equals("2")?View.GONE:View.VISIBLE);
		Log.e("好友的标准", friend.getRelationtype());
		viewHolder.realtion_rb.setChecked(friend.getRelationtype().equals("1")?false:true);
		selectorStyle(viewHolder.realtion_rb,viewHolder.realtion_rb.isChecked());
		viewHolder.realtion_rb.setOnClickListener(new lvButtonListener(position) );
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
	public void updateListView(ArrayList<Friend> list) {
		if (list == null) {
			this.childList = new ArrayList<Friend>();
		} else {
			setChildList(list);
		}
		notifyDataSetChanged();
	}

	/**
	 * 
	 *添加关注
	 */
	private void addAddttention(final CheckBox button,int postion){
		AddttentionRequest.findAddttention(UserProperty.getInstance().getUid(),map.get(assort.getHashList().getKeyIndex(postion)).getUserid(), new ResponseCallback() {
			
			@Override
			public <T> void sucess(Type type, ResponseResult<T> claszz) {
				// TODO Auto-generated method stub
				 selectorStyle(button,true);
			}
			
			@Override
			public boolean isRefreshNeeded() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void error(ResponseError error) {
				// TODO Auto-generated method stub
				Tools.toastShow(error.getMessage());
			}
		});
		}
	/**
	 * 
	 *取消关注
	 */
		private void cancelAddttention(final CheckBox button,int position){
		CancelttentionRequest.findCancelAddttention(UserProperty.getInstance().getUid(),map.get(assort.getHashList().getKeyIndex(position)).getUserid(), new ResponseCallback() {
			
			@Override
			public <T> void sucess(Type type, ResponseResult<T> claszz) {
				// TODO Auto-generated method stub
				 selectorStyle(button,false);

			}
			
			@Override
			public boolean isRefreshNeeded() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void error(ResponseError error) {
				// TODO Auto-generated method stub
				Tools.toastShow(error.getMessage());
			}
		});
		}

		private void selectorStyle(CheckBox attention, boolean isAttention){
			if(isAttention){
				//双向关注
				attention.setChecked(isAttention);
				attention.setBackgroundResource(R.drawable.checkbox_checked);
			}else{
				//单向关注
				attention.setChecked(isAttention);
				attention.setBackgroundResource(R.drawable.checkbox_unchecked);
			}
		}
	
}
