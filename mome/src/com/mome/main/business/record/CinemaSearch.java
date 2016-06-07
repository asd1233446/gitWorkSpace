package com.mome.main.business.record;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.jessieray.api.model.CinemaInfo;
import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;

@LayoutInject(layout = R.layout.cinema_search)
public class CinemaSearch extends BaseFragment implements OnItemClickListener{
	@ViewInject(id = R.id.searchList)
	private ListView searchList;

	private List<CinemaInfo> mList;
	private List<String> filterList=new ArrayList<String>();
	private List<String> nameList=new ArrayList<String>();
	private ArrayAdapter<String> adapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		Bundle bundle = getArguments();
		mList = (List<CinemaInfo>) bundle.get("list");
		setUpList();
	}

	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		this.headView.titlebar_bg_layout.setBackgroundResource(R.color.gary_line);
		super.onStart();
	}

	public void setUpList(){
		adapter=new ArrayAdapter<String>(getActivity(),R.layout.city_list_item,R.id.name,filterList);
		searchList.setAdapter(adapter);
		searchList.setOnItemClickListener(this);
		for (CinemaInfo info:mList) {
			nameList.add(info.getTitle());
		}
	}
	@Override
	public void editTextChange(String text) {
		// TODO Auto-generated method stub
		filterList.clear();
		filterList.addAll(Tools.fuzzySearch(text, nameList));
		adapter.notifyDataSetChanged();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String name=(String) parent.getItemAtPosition(position);
		int index=nameList.indexOf(name);
		CinemaInfo info=mList.get(index);
		Tools.getCallListener().Back(info);
		Tools.pullScreen(SelectCinema.class.getName());
		
	}
}
