package com.mome.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

public class MyDatePicker extends DatePickerDialog{

	public MyDatePicker(Context context, OnDateSetListener callBack,  
            int year, int monthOfYear, int dayOfMonth) {  
        super(context, callBack, year, monthOfYear, dayOfMonth);  
    }  
  
    public MyDatePicker(Context context, int theme,  
            OnDateSetListener callBack, int year, int monthOfYear,  
            int dayOfMonth) {  
        super(context, theme, callBack, year, monthOfYear, dayOfMonth);  
    }  
  
    @Override  
    protected void onStop() {  
        // TODO Auto-generated method stub  
        // super.onStop();//注释掉  
    }  
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

}
