package com.mome.main.business.movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import com.jessieray.api.model.Friend;
import com.jessieray.api.model.PhotoInfo;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

public class SelectedFriendsListCell extends BaseAdapter implements
		StickyListHeadersAdapter, SectionIndexer {

	private ArrayList<Friend> childList;
	private AssortPinyinList assort = new AssortPinyinList();
	private Map<String, Friend> map = new HashMap<String, Friend>();
	private Map<Integer, Boolean> selected = new HashMap<Integer, Boolean>();
	private onFriendsItemCheckedListener listener;
	private Friend friend;
	private int MaxCount;
	private int type;

	public void setMaxCount(int count, int type) {
		this.MaxCount = count;
		this.type = type;
	}

	public void setChildList(ArrayList<Friend> childList) {
		this.childList = childList;
		sort();
	}

	private void sort() {
		assort.getHashList().getKeyArr().clear();
		for (Friend friend : childList) {
			map.put(friend.getNickname(), friend);
			assort.getHashList().add(friend.getNickname());
		}

		for (String i : assort.getHashList().getKeyArr()) {
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
		public CheckBox mCheckBox;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
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
		friend = map.get(assort.getHashList().getKeyIndex(position));
		viewHolder.headIcon.setImageUrl(friend.getAvatar(),
				HttpRequest.getInstance().imageLoader);
		viewHolder.name.setText(friend.getNickname());
		viewHolder.mCheckBox
				.setBackgroundResource(R.drawable.attention_selector);
		viewHolder.mCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							selected.put(position, true);
						} else {
							selected.remove(position);
						}
						listener.onCheckedChanged(friend, buttonView,
								isChecked, selected);
					}
				});
		if (type == 0) {
			viewHolder.mCheckBox.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					boolean isTouch = selected.size() == MaxCount
							&& !((CheckBox) v).isChecked();
					if (isTouch) {
						Tools.toastShow("观影同伴最多6位");
					}
					return isTouch;
				}
			});
		}
		viewHolder.mCheckBox.setChecked(selected.get(position) == null ? false
				: true);

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

		viewHolder.name.setText(assort.getFirstChar(assort.getHashList()
				.getKeyIndex(position)));
		return view;
	}

	@Override
	public long getHeaderId(int position) {
		// TODO Auto-generated method stub
		String headId = assort.getFirstChar(assort.getHashList().getKeyIndex(
				position));
		return headId.charAt(0);
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
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

	public void setListener(onFriendsItemCheckedListener listener) {
		this.listener = listener;
	}

	public interface onFriendsItemCheckedListener {
		public void onCheckedChanged(Friend friendModel,
				CompoundButton buttonView, boolean isChecked,
				Map<Integer, Boolean> selected);
	}

}
