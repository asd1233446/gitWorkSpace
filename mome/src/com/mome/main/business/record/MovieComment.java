package com.mome.main.business.record;

import java.lang.reflect.Type;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jessieray.api.request.base.ResponseCallback;
import com.jessieray.api.request.base.ResponseError;
import com.jessieray.api.request.base.ResponseResult;
import com.jessieray.api.service.addRecallArticleRequest;
import com.mome.main.R;
import com.mome.main.business.HeadRef;
import com.mome.main.business.HeadView;
import com.mome.main.business.access.WeiboLogin;
import com.mome.main.core.annotation.InjectProcessor;
import com.mome.main.core.annotation.LayoutInject;
import com.mome.main.core.annotation.OnClick;
import com.mome.main.core.annotation.ViewInject;
import com.mome.main.core.utils.Tools;
import com.mome.view.MySeekBar;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.constant.WBConstants;

/**
 * 写评论
 * 
 */
@LayoutInject(layout = R.layout.dynamic_comment)
public class MovieComment extends Activity implements IWeiboHandler.Response {
	/**
	 * 影评
	 * */
	@ViewInject(id = R.id.dynamic_comment_edit)
	private EditText comment;

	@ViewInject(id = R.id.movieIcon)
	private ImageView movieIcon;

	@OnClick(id = R.id.movieIcon)
	public void movieIconClick(View view) {
	}

	/**
	 * 评分
	 * */
	@ViewInject(id = R.id.seekBar)
	private MySeekBar seekBar;

	/**
	 * 关联观影同伴
	 * */
	@ViewInject(id = R.id.atFriend)
	private TextView atFriend;

	/**
	 * 返回
	 * */
	@OnClick(id = R.id.titlebar_left)
	public void backClick(View view) {
		this.finish();
	}

	/**
	 * 发布
	 * */
	@OnClick(id = R.id.titlebar_right)
	public void sendCommentClick(View view) {
		if(!TextUtils.isEmpty(comment.getText()))
		sendComment();
		else
			Toast.makeText(this, "添加影评", Toast.LENGTH_LONG).show();
	}

	/**
	 * 分享
	 * */
	@ViewInject(id = R.id.sina_btn)
	private Button sina;

	@OnClick(id = R.id.sina_btn)
	public void sinaClick(View view) {
		weiboLogin.sendText("我来自莫么");
		// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.ic_launcher);
		// weiboLogin.sendImage(bitmap);
		// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.ic_launcher);
		// weiboLogin.sendMedia("猫咪", "我是一个孤独的孩纸", bitmap,
		// "http//:www.baidu.com", "分享一个url");
		//

	}

	@OnClick(id = R.id.wechat_btn)
	public void wechatClick(View view) {

	}

	@OnClick(id = R.id.douban_btn)
	public void doubanClick(View view) {

	}

	private WeiboLogin weiboLogin;
	private IWeiboShareAPI mWeiboShareAPI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		InjectProcessor.activityInject(this);
		weiboLogin = WeiboLogin.getInstance(this);
		mWeiboShareAPI = weiboLogin.registerToSina(this);
		if (savedInstanceState != null) {
			mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		mWeiboShareAPI.handleWeiboResponse(intent, this); // 当前应用唤起微博分享后,返回当前应用
	}

	/**
	 * 接收微客户端博请求的数据。 当微博客户端唤起当前应用并进行分享时，该方法被调用。
	 * 
	 * @param baseRequest
	 *            微博请求数据对象
	 * @see {@link IWeiboShareAPI#handleWeiboRequest}
	 */
	@Override
	public void onResponse(BaseResponse baseResp) {
		switch (baseResp.errCode) {
		case WBConstants.ErrorCode.ERR_OK:
			Toast.makeText(this, "成功", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_CANCEL:
			Toast.makeText(this, "失败", Toast.LENGTH_LONG).show();
			break;
		case WBConstants.ErrorCode.ERR_FAIL:
			Toast.makeText(this, "失败", Toast.LENGTH_LONG).show();
			break;
		}
	}
	
	
	
	private void  sendComment(){
		float progress=(float) ((float)seekBar.getProgress()/2.0);
		addRecallArticleRequest.findaddRecallArticle("1",progress+"" , comment.getText().toString(), new ResponseCallback() {
			
			@Override
			public <T> void sucess(Type type, ResponseResult<T> claszz) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isRefreshNeeded() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void error(ResponseError error) {
				// TODO Auto-generated method stub
				Toast.makeText(MovieComment.this, "添加影评", Toast.LENGTH_LONG).show();
			}
		});
		
	}
}
