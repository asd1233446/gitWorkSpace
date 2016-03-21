package com.mome.main.core.utils;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.mome.main.core.BaseFragment;
import com.mome.main.core.MainActivity;
import com.mome.main.netframe.volley.RequestQueue;

public class AppConfig {

	/**
	 * 调试开关
	 */
	public static final boolean DEBUG = true;
	/**
	 * 屏幕宽高
	 */
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	/**
	 * 接口请求成功
	 */
	public static final long REQUEST_CODE_SUCCESS = 0;
	/**
	 * 应用环境
	 */
	public static Context context = null;
	/**
	 * 应用主类实例
	 */
	public static MainActivity mainActivity;
	/**
	 * fragment页面管理类实例
	 */
	public static FragmentManager fragmentManager;
	/**
	 * 当前的根屏幕
	 */
	public static String CurrentRootScreenName = "";
	/**
	 * 当前屏幕
	 */
	public static BaseFragment currentScreen;
	/**
	 * 新浪微博登录用授权信息
	 */
    public static final String WEIBO_APP_KEY = "790000745";
    public static final String WEIBO_APP_SECRET = "78d38035f62c92ea1fd493617d2a249c";
    /** 
     * 应用的回调页，第三方应用可以使用自己的回调页。
     * 
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String WEIBO_REDIRECT_URL = "http://mome.mobi";
    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * 
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * 
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * 
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String WEIBO_SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
	/**
	 * 用户相关服务器传回的签名key
	 */
	public static String SCRET_KEY = "";
	/**
	 * 签名key
	 */
	public static String SIGN = "sign";
	/**
	 * 用户UID
	 */
	public static String UID_VALUE = "";
	/**
	 * 当前版本号
	 */
	public static String CLIENT_VERSION_VALUE = "1.0.0";
	/**
	 * 渠道号
	 */
	public static String SOURCE_ID = "0000";
	/**
	 * 时间戳
	 */
	public static String TIMESTAMP_STRING="timestamp";
	/**
	 * 服务器和本地时间差
	 */
	public static long updateTimestamp;
	/**
	 * 平台号
	 */
	public static String PLATFORM_VALUE = "13022";
	/**
	 * 设备相关信息
	 */
	public static String DEVICE_TOKEN = "0";//设备唯一标识
    public static String DEVICE_DS = ""; //手机型号
    public static String DEVICE_OS = ""; //手机操作系统
	public static String DEV_APPKEY_VALUE = "";//渠道号
	public static final String DEVICE_TYPE = "android";//平台类型
	public static String strPushClientId = "";//push用设备唯一标识
	/**
	 * http请求方式
	 */
	public static final int HTTP_GET = 0;
	public static final int HTTP_POST = 1;
	public static final int HTTP_PUT = 2;
	/**
	 * 请求url
	 */
	public static String url;
	public static final String[] HTTP_URL = new String[]{
		"http://182.92.79.73:8080/hm/mome",
		"http://release"
	};
	/**
	 * 列表一页请求的数据条数
	 */
	public static final int PAGE_SIZE = 20;
	/**
	 * 用来通过xml创建view实例
	 */
	public static LayoutInflater INFLATER = null;
	/**
	 * 首次安装启动应用程序
	 */
	public static boolean isFirstInstall;
	/**
	 * 首次启动应用程序
	 */
	public static boolean isFirstBoot;
	
	/*************************************************************************
	 * 存储用key值
	 *************************************************************************/
	/**
	 * 存储版本号的key
	 */
	public static final String SAVE_KEY_VERSION = "version";
}
