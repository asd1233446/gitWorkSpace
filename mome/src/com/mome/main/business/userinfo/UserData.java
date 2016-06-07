package com.mome.main.business.userinfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jessieray.api.model.CostStatistical;
import com.jessieray.api.model.MarkStatistical;
import com.jessieray.api.model.RecallStatistical;
import com.jessieray.api.model.TypeInfo;
import com.jessieray.api.model.TypeStatistical;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.CostStatisticalRequest;
import com.jessieray.api.service.MarkStatisticalRequest;
import com.jessieray.api.service.RecallStatisticalRequest;
import com.jessieray.api.service.TypeStatisticalRequest;
import com.mome.main.R;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListAdapter;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.business.module.ViewPagerAdapter;
import com.mome.main.business.widget.circleprogress.DonutProgress;
import com.mome.main.business.widget.digitalcloud.DigitalCloudView;
import com.mome.main.core.BaseFragment;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;
import com.mome.view.MyDatePicker;

@LayoutInject(layout = R.layout.user_data)
public class UserData extends BaseFragment implements OnCheckedChangeListener {

	/***
	 * 数据类型
	 */
	@ViewInject(id = R.id.user_data_type_name)
	private TextView dataType;

	/**
	 * 数据筛选
	 */
	@ViewInject(id = R.id.radioGroup)
	private RadioGroup rGroup;

	/**
	 * 中间滑动页面
	 */
	@ViewInject(id = R.id.user_data_viewpager)
	private ViewPager viewPager;

	private ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
	/**
	 * 页面容器
	 */
	private ArrayList<View> pageViewList = new ArrayList<View>();
	/**
	 * 页面布局id
	 */
	private int[] pageLayoutId = new int[] { R.layout.user_data_look_num,
			R.layout.user_data_grade, R.layout.user_data_digital_cloud,
			R.layout.user_data_expenditure_time };
	/**
	 * 页指示器
	 */
	@ViewInject(id = R.id.user_data_indicator_one)
	private ImageView indicatorOne;
	@ViewInject(id = R.id.user_data_indicator_two)
	private ImageView indicatorTwo;
	@ViewInject(id = R.id.user_data_indicator_three)
	private ImageView indicatorThree;
	@ViewInject(id = R.id.user_data_indicator_four)
	private ImageView indicatorFour;
	/**
	 * 数字云视图
	 */
	private DigitalCloudView digitalCloudView;

	/**
	 * 用户id
	 */
	private String userid;
	/**
	 * 筛选年份
	 */
	private String date;

	/**
	 * 观看类型
	 */
	private int lookedType=100;

	/**
	 * 筛选条件
	 * */
	private int filter;

	/**
	 * 日期控件
	 * */
	private DatePickerDialog datePickerDialog = null;

	/**
	 * 我的数据类型
	 * */
	private int ViewIndex;

	/**
	 * 年份筛选
	 * */
	@ViewInject(id = R.id.year)
	private RadioButton year_rb;

	@OnClick(id = R.id.year)
	public void showDataPickerClick(View view) {
		getDataPicker().show();
	}

	/**
	 * 全部
	 * */
	@ViewInject(id = R.id.all)
	private RadioButton all;

//	@OnClick(id = R.id.all)
//	public void showall(View view) {
//		
//	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		userid = bundle == null ? UserProperty.getInstance().getUid() : bundle
				.getString("userinfo");
		createPage();
		rGroup.setOnCheckedChangeListener(this);
		timeradioGroup.setOnCheckedChangeListener(this);
		setViewPager();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	/**
	 * 参数重置
	 * 
	 * */

	private void reset() {
		if(all.isChecked()==true)
		        DataType();
		all.setChecked(true);
		year_rb.setText("筛选年份");
		datePickerDialog = null;
		dataType.setText("观影数量");
		lookedType=0;
	
        
	}

	private void setViewPager() {
		reset();
		viewPager.setAdapter(viewPagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				Log.e("onPageSelected", arg0 + "");
				ViewIndex = arg0;
				reset();
				updateIndicator(arg0);
				// 初始化页面信息

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	/**
	 * 判断进入第4个页面观影模式“全部”，，不发交易
	 * */
	private boolean firstFlag=false;

	private void updateIndicator(int index) {
		indicatorOne.setImageResource(R.drawable.dot);
		indicatorTwo.setImageResource(R.drawable.dot);
		indicatorThree.setImageResource(R.drawable.dot);
		indicatorFour.setImageResource(R.drawable.dot);
		switch (index) {
		case 0:
			dataType.setText("观影数量");
			indicatorOne.setImageResource(R.drawable.dotselect);
			break;
		case 1:
			dataType.setText("评分统计");
			indicatorTwo.setImageResource(R.drawable.dotselect);
			break;
		case 2:
			dataType.setText("类型统计");
			indicatorThree
					.setImageResource(R.drawable.dotselect);
			break;
		case 3:
			firstFlag=true;
			all_rb.setChecked(true);
			dataType.setText("消费时间统计");
			indicatorFour
					.setImageResource(R.drawable.dotselect);
			break;
		}
	}

	private void createPage() {
		View view = null;
		for (int i = 0; i < pageLayoutId.length; i++) {
			view = AppConfig.INFLATER.inflate(pageLayoutId[i], null);
			if (i == 0) {
				createFirstPage(view);
			} else if (i == 1) {
				createSecondPage(view);
			} else if (i == 2) {
				createThirdPage(view);
			} else if (i == 3) {
				createFourthPage(view);
			}
			pageViewList.add(view);
		}

		viewPagerAdapter.setList(pageViewList);
	}

	/**
	 * 观看次数
	 * */
	private TextView totalLookedNum;
	/**
	 * 电影次数
	 * */
	private DonutProgress FilmNum;
	/**
	 * 网络次数
	 * */
	private DonutProgress InternetNum;
	/**
	 * 电视次数
	 * */
	private DonutProgress tvNum;

	/**
	 * 最常去的影院
	 * */
	private TextView oftenCinemeName;
	/**
	 * 最常去的影院次数
	 * */
	private TextView oftenCinemeNum;

	/**
	 * 最常的观影方式
	 * */
	private TextView oftenLookedType;

	/**
	 * 最常的观影方式次数
	 * */
	private TextView oftenLookedTypeNum;

	/**
	 * 创建第一页
	 * 
	 * @param view
	 */
	private void createFirstPage(View view) {
		if (view != null) {
			totalLookedNum = (TextView) view.findViewById(R.id.lookedNum_tv);
			FilmNum = (DonutProgress) view.findViewById(R.id.flimDount_pg);
			InternetNum = (DonutProgress) view
					.findViewById(R.id.internetDonut_pg);
			tvNum = (DonutProgress) view.findViewById(R.id.tvDount_pg);
			oftenCinemeName = (TextView) view
					.findViewById(R.id.oftenCinemaName_tv);
			oftenCinemeNum = (TextView) view
					.findViewById(R.id.oftenCinemaNum_tv);
			oftenLookedType = (TextView) view
					.findViewById(R.id.oftenLookedTypeName_tv);
			oftenLookedTypeNum = (TextView) view
					.findViewById(R.id.oftenLookedTypeNum_tv);
		}
	}

	private void setFirstPage(RecallStatistical recall) {
		totalLookedNum.setText(recall.getTotal() + "");
		FilmNum.setMax(recall.getTotal());
		FilmNum.setProgress(recall.getCinematotal());
		InternetNum.setMax(recall.getTotal());
		InternetNum.setProgress(recall.getInternettotal());
		tvNum.setMax(recall.getTotal());
		tvNum.setProgress(recall.getTvtotal());
		oftenCinemeName.setText(recall.getFavorcinema());
		oftenCinemeNum.setText(recall.getFavoercinematotal() + "");
		oftenLookedType.setText(recall.getFavorinternet());
		oftenLookedTypeNum.setText(recall.getFavorinternettotal() + "");

	}

	/**
	 * 星星平均分
	 * */
	private RatingBar markrating;
	/**
	 * 年份
	 * */
	private TextView markyear;

	/**
	 * 平均分
	 * */
	private TextView markvalue;

	/**
	 * 好评
	 * */
	private TextView markgood;
	/**
	 * 一般
	 * */
	private TextView markOrdinary;
	/**
	 * 较差
	 * */
	private TextView marklow;
	/**
	 * 极差
	 * */
	private TextView markLower;

	/**
	 * 创建第二页
	 * 
	 * @param view
	 */
	private void createSecondPage(View view) {
		if (view != null) {
			Log.e("view", "评分统计");
			markrating = (RatingBar) view.findViewById(R.id.markrating_rb);
			markyear = (TextView) view.findViewById(R.id.markyear_tv);
			markvalue = (TextView) view.findViewById(R.id.markValue_tv);
			markgood = (TextView) view.findViewById(R.id.markGood_tv);
			markOrdinary = (TextView) view.findViewById(R.id.markOrdinary_tv);
			marklow = (TextView) view.findViewById(R.id.marklow_tv);
			markLower = (TextView) view.findViewById(R.id.markLower_tv);			

		}
	}

	private void setSecondPage(MarkStatistical mark) {
		markrating.setRating((float) (mark.getAverage() * 0.5));
		markvalue.setText(mark.getAverage() + "");
		markgood.setText(mark.getGood() + "");
		markOrdinary.setText(mark.getCommon() + "");
		marklow.setText(mark.getBad() + "");
		markLower.setText(mark.getWorst() + "");
	}

	/**
	 * 电影类型的统计
	 */
	private ListView listview;

	private ArrayList<ListCellBase> datalist = new ArrayList<ListCellBase>();

	private ListAdapter adapter = new ListAdapter();

	/**
	 * 创建第三页
	 * 
	 * @param view
	 */
	private void createThirdPage(View view) {
		if (view != null) {
			digitalCloudView = (DigitalCloudView) view
					.findViewById(R.id.user_data_digital_cloud_view);
			listview = (ListView) view.findViewById(R.id.datatype_ls);
			adapter.setDataList(datalist);
			listview.setAdapter(adapter);
			ArrayList<String> data = new ArrayList<String>();
			data.add("剧情");
			data.add("爱情");
			data.add("喜剧");
			data.add("犯罪");
			data.add("悬疑");
			data.add("动作");
			data.add("传记");
			digitalCloudView.setData(data);
		}
	}

	private void setThirdPage(List<TypeInfo> list) {
		datalist.clear();
		for (TypeInfo type : list) {
			UserDataListCell cell = new UserDataListCell();
			cell.setTypeInfo(type);
			datalist.add(cell);
		}
		adapter.notifyDataSetChanged();
	}

	/**
	 * 观影类型
	 * */
	private RadioGroup timeradioGroup;
	/**
	 * 观影总时间
	 * */
	private TextView times_tv;
	/**
	 * 观影总小时
	 * */
	private TextView hours_tv;
	/**
	 * 观影总天数
	 * */
	private TextView day_tv;
	/**
	 * 观影总钱
	 * */
	private TextView monery_tv;
	/**
	 * 观影击败多少人
	 * */
	private TextView beat_tv;

	/**
	 * 观影击败多少人
	 * */
	private RadioButton all_rb;

	/**
	 * 创建第四页
	 * 
	 * @param view
	 */
	private void createFourthPage(View view) {
		if (view != null) {
			timeradioGroup = (RadioGroup) view
					.findViewById(R.id.timeradioGroup);
			all_rb = (RadioButton) view.findViewById(R.id.all_time);
			times_tv = (TextView) view.findViewById(R.id.times_tv);
			hours_tv = (TextView) view.findViewById(R.id.hours_tv);
			day_tv = (TextView) view.findViewById(R.id.day_tv);
			monery_tv = (TextView) view.findViewById(R.id.monery_tv);
			beat_tv = (TextView) view.findViewById(R.id.beat_tv);
		}
	}

	private void setFourPage(CostStatistical cost) {
		times_tv.setText(cost.getMinute());
		hours_tv.setText(Integer.valueOf(cost.getMinute()) / 60 + "");
		day_tv.setText(Integer.valueOf(cost.getMinute()) / 60 / 12 + "");
		monery_tv.setText(cost.getMoney());
		beat_tv.setText(cost.getPrecent());
	}

	/**
	 * 观影数据
	 * */

	private void getLookedRecord() {
		RecallStatisticalRequest.findRecallStatistical(userid, date, this);

	}

	/**
	 * 评分统计
	 * */
	private void getMarkRecord() {
		MarkStatisticalRequest.findMarkStatistical(userid, date, this);

	}

	/**
	 * 类型统计
	 * */
	private void getTypeRecord() {
		TypeStatisticalRequest.findTypeStatistical(userid, date, this);
	}

	/**
	 * 消费时间统计
	 * */
	private void getTimeRecord() {
		CostStatisticalRequest.findCostStatistical(userid, date, lookedType,
				this);

	}

	@Override
	public <T> void sucess(Type arg0, ResponseResult<T> arg1) {
		// TODO Auto-generated method stub
		super.sucess(arg0, arg1);
		if (arg1.getCode() == AppConfig.REQUEST_CODE_SUCCESS
				&& arg1.getModel() != null) {
			if (arg1.getModel().getClass().equals(RecallStatistical.class)) {
				// 观影次数统计
				setFirstPage((RecallStatistical) arg1.getModel());
			}
			if (arg1.getModel().getClass().equals(MarkStatistical.class)) {
				// 评分统计
				setSecondPage((MarkStatistical) arg1.getModel());
			}
			if (arg1.getModel().getClass().equals(TypeStatistical.class)) {
				// 类型筛选
				setThirdPage(((TypeStatistical) arg1.getModel()).getTypes());
			}
			if (arg1.getModel().getClass().equals(CostStatistical.class)) {
				// 消费统计
				setFourPage((CostStatistical) arg1.getModel());
			}
		}
	}

	@Override
	public void error(ResponseError arg0) {
		// TODO Auto-generated method stub
		Tools.toastShow(arg0.getMessage());
		super.error(arg0);
	}

	/**
	 * 调起时间控件
	 * */
	public DatePickerDialog getDataPicker() {
		if (datePickerDialog == null) {
			datePickerDialog = new MyDatePicker(getActivity(),
					AlertDialog.THEME_HOLO_LIGHT, new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							date = year + "";
							year_rb.setText(date);
							DataType();
						}
					}, Calendar.getInstance().get(Calendar.YEAR), Calendar
							.getInstance().get(Calendar.MARCH), Calendar
							.getInstance().get(Calendar.DAY_OF_MONTH));
			((ViewGroup) ((ViewGroup) datePickerDialog.getDatePicker()
					.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(
					View.GONE);
			((ViewGroup) ((ViewGroup) datePickerDialog.getDatePicker()
					.getChildAt(0)).getChildAt(0)).getChildAt(1).setVisibility(
					View.GONE);
		}

		return datePickerDialog;
	}

	/**
	 * 当前哪个页面，发不同的交易
	 * 
	 * */
	private void DataType() {
		switch (ViewIndex) {
		case 0:
			getLookedRecord();
			break;
		case 1:
			markyear.setVisibility(View.VISIBLE);
			markyear.setText(date);
			getMarkRecord();
			break;
		case 2:
			getTypeRecord();
			break;
		case 3:
			getTimeRecord();
			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
	
		// TODO Auto-generated method stub
		Log.e("sdfjosdfjs", "sdfjosdfjs");
		if (group.getId() == R.id.timeradioGroup) {
			switch (checkedId) {
			case R.id.all_time:
                   lookedType=0;
				break;
			case R.id.cinema:
				firstFlag=false;
                lookedType=1;
				break;
			case R.id.internet:
                lookedType=2;
				firstFlag=false;
				break;
			case R.id.tv:
                lookedType=3;
				firstFlag=false;
				break;
			default:
				break;
			}
			if(!firstFlag){
             getTimeRecord(); 
             firstFlag=false;
			}
		}
		else if(group.getId()==R.id.radioGroup){
			if(checkedId==R.id.all){
				date="";
				DataType();
			}
		}
	}
}
