package com.xyyy.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xyyy.shop.db.DataBaseHelper;
import com.xyyy.shop.model.EnnMember;
import com.xyyy.shop.toolUtil.BadHandler;
import com.xyyy.shop.toolUtil.PreferencesUtil;

public class ShopApplication extends Application {
	
	//微信的APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wxcab09fe2226ee85f";
    public static final String MCH_ID = "1251862501";
    //IWXAPI是第三方app和微信通信的openapi接口
    public static IWXAPI api;
    
	public static ShopApplication application = null;
	public static final Logger logger = LoggerFactory.getLogger(ShopApplication.class);
	public static List<Activity> activityList = new ArrayList<Activity>();
	public static RequestQueue requestQueue;
	
	public static boolean isLogin =false;
	public static int loginflag =1;//登录方式 1原生登录 2第三方登录
	public static int usersexOtherlogin;// 微信登录性别 1男 2女 0未知
	public static String usernameOtherlogin;// 第三方登录用户名
	public static String userimgurlOtherlogin;//第三方登录 用户头像地址
	public static String userbirthother;//第三方登录 出生日期
	
	public static String usernamelogin;// 原生用户名
	public static String userimgurlrlogin;//原生登录 用户头像地址
	public static int userid;//原生登录 的人员id
	public static int useridother;//第三方登录人员id
	public static EnnMember userinfo =new EnnMember();// 原生登录个人信息
	
	public static String orderid;
	public static int payflag=1;//支付用途标记  用于处理支付完成之后的跳转   1.表示普通订单的支付 2 会员卡支付的支付 
	
	public static int mainflag=0;//主界面模块的标记 0首页模块 1分类模块 2购物车模块 3会员卡模块 4个人中心

	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		PreferencesUtil.init(application);
		BadHandler.getInstance().init(application);
		StatService.setDebugOn(true);
		//初始化数据库
		DataBaseHelper.getHelper(application).getWritableDatabase();
		
		initImageLoader(application);
		//网路请求的请求队列
		requestQueue=Volley.newRequestQueue(application);
		//注册微信Api
		regToWx();
	}
    
	public static ShopApplication getInstance() {
		return application;
	}
	/**
	 * 退出应用
	 */
	public static void doEdit(){
		isLogin =false;
		DataBaseHelper.getHelper(application).close();
		api.unregisterApp();
		for(Activity oneactivity :activityList){
			oneactivity.finish();
		}
		System.gc();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	/**
	 * 初始化Imageloader 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		// 这个是你希望的缓存文件的目录：imageloader/Cache
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"/xyyy/shop/imageloader/Cache");

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
		        // 设置线程优先级
				.threadPriority(Thread.NORM_PRIORITY + 2)
				// 设置图片缓存路劲
				.discCache(new UnlimitedDiscCache(cacheDir))
				/*
				 * //调用该方法会禁止在内存中缓存同一张图片的多个尺寸。当把本地图片加载到内存中时，
				 * 首先会把图片缩减至要显示的ImageView的大小，
				 * 因此可能会出现一种状况，就是会首先显示一张图的小图，然后再显示这张图的大图。这种情况下，
				 * 同一张图片的两种尺寸的Bitmap会被存储在内存中，这是默认的操作
				 * 该方法会确保删除已加载图片缓存在内存中的其他尺寸的缓存。
				 */
				.denyCacheImageMultipleSizesInMemory()
				/*
				 * ImageLoaderConfiguration
				 * 配置中的.discCacheFileNameGenerator()方法是将缓存下来的文件以什么方式命名
				 * 里面可以调用的方法有 1.new Md5FileNameGenerator() //使用MD5对UIL进行加密命名
				 * 2.new HashCodeFileNameGenerator()//使用HASHCODE对UIL进行加密命名
				 */
				.discCacheFileNameGenerator(new Md5FileNameGenerator())

				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		 ImageLoader.getInstance().init(config);
	}
	/**
	 * 注册微信Api
	 */
	private void regToWx(){
		//通过WXAPIFactory工厂，获取IWXAPI的实例
		api=WXAPIFactory.createWXAPI(this, APP_ID,true);
		//将应用的appid 注册到微信
		api.registerApp(APP_ID);
	}
}
