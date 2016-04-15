package com.mome.main.business.record;

import java.util.ArrayList;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.main.core.utils.Tools.callBack;
import com.mome.pinyin.AssortView;
import com.mome.pinyin.AssortView.OnTouchAssortListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

@LayoutInject(layout = R.layout.select_city)
public class SelectCity extends BaseFragment implements OnTouchAssortListener ,OnItemClickListener{
	@ViewInject(id = R.id.seachCity)
	private EditText seachCity;

	/** 城市列表 */
	@ViewInject(id = R.id.expandableList)
	private ExpandableStickyListHeadersListView listView;

	/** 城市列表对应的Adapter **/
	private CityExpandablelistAdapter adapter;
	/** 字母索引 **/
	@ViewInject(id = R.id.letter_view)
	/** 分组下集合 */
	private ArrayList<String> childList = new ArrayList<String>();

	/** 字母 **/
	@ViewInject(id = R.id.letter_view)
	private AssortView assortView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	//	AppConfig.fragmentManager.popBackStack(SelectCinema.class.getName(), 0);//0表示弹出的屏幕不包括当前屏幕
		setUpListView();
		fastSearch();
		getCityList() ;
	}

	private void setUpListView() {
		adapter = new CityExpandablelistAdapter();
		assortView.setOnTouchAssortListener(this);
		listView.setOnItemClickListener(this);
		listView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
			@Override
			public void onHeaderClick(StickyListHeadersListView l, View header,
					int itemPosition, long headerId, boolean currentlySticky) {
				if (listView.isHeaderCollapsed(headerId)) {
					listView.expand(headerId);
				} else {
					listView.collapse(headerId);
				}
			}
		});
	}

	/**
	 * 快速搜索
	 * */
	private void fastSearch() {

		seachCity.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String content = seachCity.getText().toString();
				if (!TextUtils.isEmpty(content)) {

					Log.e("模糊查询", search(content).size() + "  ");
					adapter.updateListView(search(content));
				} else {
					adapter.updateListView(childList);
				}
			}
		});

	}

	/**
	 * 模糊查询
	 * 
	 * @param str
	 * @return
	 */
	private ArrayList<String> search(String str) {
		ArrayList<String> filterList = new ArrayList<String>();// 过滤后的list

		for (String string : childList) {
			if (!string.isEmpty()) {
				// 姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
				if (string.toLowerCase(Locale.CHINESE).contains(
						str.toLowerCase(Locale.CHINESE))
						|| adapter.getAssort().getFirstChar(string)
								.toLowerCase(Locale.CHINESE).replace(" ", "")
								.contains(str.toLowerCase(Locale.CHINESE))) {
					if (!filterList.contains(string)) {
						filterList.add(string);
					}
				}
			}
		}

		return filterList;
	}

	private void getCityList() {
		for (int i = 0; i < 3; i++) {
			childList.add("北京" + i);
			childList.add("北安" + i);
		}
		for (int i = 0; i < 3; i++) {
			childList.add("北防" + i);

		}
		for (int i = 0; i < 3; i++) {
			childList.add("赤峰" + i);

		}
		for (int i = 0; i < 3; i++) {
			childList.add("天津" + i);
			childList.add("成都" + i);
		}

		adapter.setChildList(childList);
		listView.setAdapter(adapter);
	}

	@Override
	public void onTouchAssortListener(String str) {
		// TODO Auto-generated method stub
		int index = adapter.getAssort().getHashList().getFristCharIndex(str);
		// Tools.toastShow("==="+index);
		if (index != -1) {
			listView.setSelection(index);
		}
	}

	@Override
	public void onTouchAssortUP() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
     String cityName=adapter.getAssort().getHashList().getKeyIndex(position);
		
		Tools.getCallListener().Back(cityName);
		Tools.pullScreen();
	}


}
