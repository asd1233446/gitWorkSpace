package com.mome.main.business.module;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class BaseAdapter<T> extends android.widget.BaseAdapter{
	protected Context context;
	protected ArrayList<T> models;
	
	public BaseAdapter(Context context, ArrayList<T> models) {
		this.context = context;
		if (models == null)
			this.models = new ArrayList<T>();
		else
			this.models = models;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return models.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return models.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update(T model) {
		if (models == null)
			return;
			this.models.add(1,model);
		
		notifyDataSetChanged();
	}

}
