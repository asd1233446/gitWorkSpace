package com.mome.main.business.record;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jessieray.api.model.MovieInfo;
import com.mome.main.R;
import com.mome.main.business.module.BaseAdapter;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.net.HttpRequest;
import com.mome.main.netframe.volley.toolbox.NetworkImageView;

public class MovieSearchAdapter extends BaseAdapter<MovieInfo> {
	private MovieInfo minfo;

	public MovieSearchAdapter(Context context, ArrayList<MovieInfo> models) {
		super(context, models);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = InjectProcessor.injectListViewHolder(viewHolder);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		minfo = models.get(position);
		viewHolder.image.setImageUrl(minfo.getImagesrc(),
				HttpRequest.getInstance().imageLoader);
		viewHolder.name.setText(minfo.getTitle());
		return convertView;
	}

	@LayoutInject(layout = R.layout.movie_search_item)
	public class ViewHolder {
		@ViewInject(id = R.id.head)
		private NetworkImageView image;

		@ViewInject(id = R.id.name)
		private TextView name;

	}

}

