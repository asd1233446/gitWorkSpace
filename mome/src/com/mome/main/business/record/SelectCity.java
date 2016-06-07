package com.mome.main.business.record;

import java.util.ArrayList;
import java.util.Locale;

import net.sourceforge.pinyin4j.PinyinHelper;

import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import com.mome.main.R;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.MainActivity;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.pinyin.AssortView;
import com.mome.pinyin.AssortView.OnTouchAssortListener;
import com.sina.weibo.sdk.register.mobile.PinyinUtils;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

@LayoutInject(layout = R.layout.select_city)
public class SelectCity extends BaseFragment implements OnTouchAssortListener,
		OnItemClickListener {
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

	// 定位城市
	@ViewInject(id = R.id.gpsCity)
	private TextView locationCity;

	// 热门城市
	@OnClick(id = R.id.bj_tv)
	public void bjClick(View view){
		getCityNmae("北京");
	}
	
	// 热门城市
	@OnClick(id = R.id.sh_tv)
	public void shClick(View view){
		getCityNmae("上海");
	};
	
	// 热门城市
	@OnClick(id = R.id.gz_tv)
	public void gzClick(View view){
		getCityNmae("广州");
	};
	
	// 热门城市
	@OnClick(id = R.id.tj_tv)
	public void tjClick(View view){
		getCityNmae("天津");
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		locationCity.setText(MainActivity.cityName + "GPS");
		setUpListView();
		fastSearch();
		getCityList();
	}

	private void setUpListView() {
		adapter = new CityExpandablelistAdapter();
		assortView.setOnTouchAssortListener(this);
		listView.setOnItemClickListener(this);
		 
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
								.contains(str.toLowerCase(Locale.CHINESE))||PinyinUtils.getInstance(getActivity()).getPinyin(string).contains(PinyinUtils.getInstance(getActivity()).getPinyin(str))) {
					if (!filterList.contains(string)) {
						filterList.add(string);
					}
				}
			}
		}

		return filterList;
	}

	private void getCityList() {
		childList = Tools.getCityList();
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
		String cityName = adapter.getAssort().getHashList()
				.getKeyIndex(position);
		getCityNmae(cityName);
	}

	public void getCityNmae(String cityName) {
		Tools.getCallInnerListener().Back(cityName);
		Tools.pullScreen();
	}

}
