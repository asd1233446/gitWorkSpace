package com.mome.main.business.userinfo;

import java.lang.reflect.Type;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jessieray.api.model.AddArticleGood;
import com.jessieray.api.model.DynamicInfo;
import com.jessieray.api.model.TypeInfo;
import com.jessieray.api.model.UndoArticleGood;
import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.AddArticleGoodRequest;
import com.jessieray.api.service.UndoArticleGoodRequest;
import com.mome.main.R;
import com.mome.main.business.dynamic.DynamicDetail;
import com.mome.main.business.model.UserProperty;
import com.mome.main.business.module.ListCellBase;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.core.utils.AppConfig;
import com.mome.main.core.utils.Tools;

public class UserDataListCell implements ListCellBase {
	private TypeInfo info;

	public void setTypeInfo( TypeInfo info) {
		this.info = info;
	}

	@Override
	public View getView(View convertView) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = InjectProcessor.injectListViewHolder(viewHolder);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

	
		viewHolder.name.setText(info.getMovietype());
		viewHolder.value.setText(info.getTotal()+"");
		viewHolder.mark.setText(info.getAverage()+"");
		
		return convertView;
	}

	@LayoutInject(layout = R.layout.user_data_type_list)
	public class ViewHolder {
		@ViewInject(id = R.id.dataTypeName_tv)
		private TextView name;

		@ViewInject(id = R.id.dataTypeValue_tv)
		private TextView value;

		@ViewInject(id = R.id.dataTypeMark_tv)
		private TextView mark;
	}
}
