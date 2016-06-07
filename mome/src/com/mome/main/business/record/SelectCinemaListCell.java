package com.mome.main.business.record;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jessieray.api.model.CinemaInfo;
import com.jessieray.api.model.DistrictsInfo;
import com.mome.main.R;
import com.mome.main.business.module.ExpandListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;

public class SelectCinemaListCell extends ExpandListCellBase{
	/**
	 * 地区名称
	 * */
	private DistrictsInfo distance;
	
	private CinemaInfo info;

	public DistrictsInfo getDistance() {
		return distance;
	}

	public void setDistance(DistrictsInfo distance) {
		this.distance = distance;
	}

	public CinemaInfo getInfo() {
		return info;
	}

	public void setInfo(CinemaInfo info) {
		this.info = info;
	}

	@Override
	public View getGroupView(int groupIndex,View convertView,boolean isExpanded) {
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
		
		viewHolder.area.setText(distance.getDistrict()+"("+distance.getTotal()+")");
		  if(isExpanded){
              viewHolder.arrow.setImageResource(R.drawable.openarrow);
          }else{
        	  viewHolder.arrow.setImageResource(R.drawable.closearrow);
          }
		return view;
	}

	@Override
	public View getChildView(int groupIndex,int childIndex,View convertView) {
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
	    viewHolder.cinema_name.setText(info.getTitle());
		viewHolder.cinema_address.setText(info.getAddress());
		viewHolder.cinema_distance.setText(info.getDistrict());
		return view;
	}

	
	
	@LayoutInject(layout=R.layout.select_cinema_child)
	 class ChildViewHolder{
		
		/**
		 * 电影地址
		 * 
		 * */
		@ViewInject(id=R.id.select_cinema_child_address)
		private TextView cinema_address;
		
		/**
		 * 影院名称
		 * 
		 * */
		@ViewInject(id=R.id.select_cinema_child_name)
		private TextView cinema_name;
		
		/**
		 * 距离
		 * 
		 * */
		@ViewInject(id=R.id.select_cinema_child_distance)
		private TextView cinema_distance;
		
	
		
	}
	
	@LayoutInject(layout=R.layout.select_cinema_group)
	class GroupViewHolder{
		/**
		 *  地区
		 * 
		 * */
		@ViewInject(id=R.id.select_cinema_group_title)
		private TextView area;
	
		
		/**
		 * 箭头
		 * 
		 * */
		@ViewInject(id=R.id.select_cinema_group_arrow)
		private ImageView arrow;
		
	}
}
