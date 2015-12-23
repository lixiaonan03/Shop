package com.xyyy.shop.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.activity.ErweimaActivity;
import com.xyyy.shop.activity.GuangnongchangActivity;
import com.xyyy.shop.activity.HelpActivity;
import com.xyyy.shop.activity.LoginActivity;
import com.xyyy.shop.activity.MyInformationActivity;
import com.xyyy.shop.activity.MycouponActivity;
import com.xyyy.shop.activity.MydeliverynoticeActivity;
import com.xyyy.shop.activity.MygiftActivity;
import com.xyyy.shop.activity.MyorderActivity;
import com.xyyy.shop.activity.MytasteActivity;
import com.xyyy.shop.activity.MythisdeliveryActivity;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.RoundImageView;

/**
 * 我的新侬模块
 * 
 * @author lxn
 *
 */
public class MycenterFragment extends Fragment {
	private RelativeLayout myorder;// 我的订单
	private RelativeLayout mytaste;// 我的口味
	private RelativeLayout mydeliverynotice;// 配送预告
	private RelativeLayout mythisdelivery;// 近日配送清单
	private RelativeLayout mycoupon;// 我的优惠券
	private RelativeLayout giftcard;// 我的礼品卡
	private RelativeLayout guangnongchang;// 逛农场
	private RelativeLayout erweima;// 二维码
	private RelativeLayout help;//帮助中心
	
	private RelativeLayout user_information;
	private RoundImageView user_img;
	private TextView user_name;
	private String username;
	private String imgurl;
	private Button logout;// 退出登录
	private ChangeReceiver receiver;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_mycenter, container, false);
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = this.getArguments();
		if(bundle!=null){
			username = bundle.getString("name", "");
			imgurl = bundle.getString("imgurl", "");
		}
		initView();
		
		// 初始化广播
				receiver = new ChangeReceiver();
				LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
				IntentFilter intentFilter = new IntentFilter();
				intentFilter.addAction("changeuserinfo");
				broadcastManager.registerReceiver(receiver, intentFilter);

	}

	private void initView() {
		user_information = (RelativeLayout) getView().findViewById(
				R.id.user_information);
		user_img = (RoundImageView) getView().findViewById(R.id.user_image);
		user_name = (TextView) getView().findViewById(R.id.user_name);
		
		user_information.setOnClickListener(new ViewOnClickListener());

		myorder = (RelativeLayout) getView().findViewById(R.id.myorder);
		mytaste = (RelativeLayout) getView().findViewById(R.id.mytaste);
		mydeliverynotice = (RelativeLayout) getView().findViewById(
				R.id.mydeliverynotice);
		mythisdelivery = (RelativeLayout) getView().findViewById(
				R.id.mythisdelivery);
		mycoupon = (RelativeLayout) getView().findViewById(R.id.mycoupon);
		giftcard = (RelativeLayout) getView().findViewById(R.id.giftcard);
		guangnongchang = (RelativeLayout) getView().findViewById(
				R.id.guangnongchang);
		erweima = (RelativeLayout) getView().findViewById(R.id.erweima);
		help = (RelativeLayout) getView().findViewById(R.id.help);

		logout = (Button) getView().findViewById(R.id.logout);

		myorder.setOnClickListener(new ViewOnClickListener());
		mytaste.setOnClickListener(new ViewOnClickListener());
		mydeliverynotice.setOnClickListener(new ViewOnClickListener());
		mythisdelivery.setOnClickListener(new ViewOnClickListener());
		mycoupon.setOnClickListener(new ViewOnClickListener());
		giftcard.setOnClickListener(new ViewOnClickListener());
		guangnongchang.setOnClickListener(new ViewOnClickListener());
		erweima.setOnClickListener(new ViewOnClickListener());
		help.setOnClickListener(new ViewOnClickListener());

		logout.setOnClickListener(new ViewOnClickListener());
		
		changeuserinfo();
	}

	private class ViewOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			int id = arg0.getId();
			Intent intent = new Intent();

			switch (id) {
			case R.id.user_information:
				// 个人资料
				if (ShopApplication.isLogin) {
					intent.setClass(getActivity(), MyInformationActivity.class);
					getActivity().startActivity(intent);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 04);
					getActivity().startActivityForResult(intent, 04);
				}
				break;
			case R.id.myorder:
				// 我的订单
				if (ShopApplication.isLogin) {
					intent.setClass(getActivity(), MyorderActivity.class);
					getActivity().startActivity(intent);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 04);
					getActivity().startActivityForResult(intent, 04);
				}
				/*intent.setClass(getActivity(), MyorderActivity.class);
				getActivity().startActivity(intent);*/
				break;
			case R.id.mytaste:
				// 我的口味
				if (ShopApplication.isLogin) {
					intent.setClass(getActivity(), MytasteActivity.class);
					getActivity().startActivity(intent);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 04);
					getActivity().startActivityForResult(intent, 04);
				}
				/*intent.setClass(getActivity(), MytasteActivity.class);
				getActivity().startActivity(intent);*/
				break;
			case R.id.mydeliverynotice:
				// 配送预告
				if (ShopApplication.isLogin) {
					intent.setClass(getActivity(), MydeliverynoticeActivity.class);
					getActivity().startActivity(intent);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 04);
					getActivity().startActivityForResult(intent, 04);
				}
				/*intent.setClass(getActivity(), MydeliverynoticeActivity.class);
				getActivity().startActivity(intent);*/
				break;
			case R.id.mythisdelivery:
				// 近日配送清单
				if (ShopApplication.isLogin) {
					intent.setClass(getActivity(), MythisdeliveryActivity.class);
					getActivity().startActivity(intent);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 04);
					getActivity().startActivityForResult(intent, 04);
				}
				/*intent.setClass(getActivity(), MythisdeliveryActivity.class);
				getActivity().startActivity(intent);*/
				break;
			case R.id.mycoupon:
				// 我的优惠
				if (ShopApplication.isLogin) {
					intent.setClass(getActivity(), MycouponActivity.class);
					getActivity().startActivity(intent);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 04);
					getActivity().startActivityForResult(intent, 04);
				}
				break;
			case R.id.giftcard:
				// 我的礼品卡  
				if (ShopApplication.isLogin) {
					intent.setClass(getActivity(), MygiftActivity.class);
					getActivity().startActivity(intent);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 04);
					getActivity().startActivityForResult(intent, 04);
				}
				break;
			case R.id.guangnongchang:
				// 逛农场
				intent.setClass(getActivity(), GuangnongchangActivity.class);
				getActivity().startActivity(intent);
				break;
			case R.id.erweima:
				// 二维码
				intent.setClass(getActivity(), ErweimaActivity.class);
				getActivity().startActivity(intent);
				break;
			case R.id.help:
				// 帮助中心
				intent.setClass(getActivity(), HelpActivity.class);
				intent.putExtra("url",CommonVariable.HelpURL);
				getActivity().startActivity(intent);
				break;
			case R.id.logout:
				// 退出登录的
				StatService.onEvent(getActivity(),"logout" ,"退出登录");
				if (ShopApplication.isLogin) {
					ShopApplication.isLogin = false;
					logout.setVisibility(View.GONE);
					user_img.setImageResource(R.drawable.user_img);
					user_name.setText("请登录");
					if(ShopApplication.loginflag==1){
						ShopApplication.userid=0;
						ShopApplication.usernamelogin="";
						ShopApplication.userimgurlrlogin="";
						ShopApplication.userinfo=null;
					}
					if(ShopApplication.loginflag==2){
						ShopApplication.usernameOtherlogin=null;
						ShopApplication.userimgurlOtherlogin=null;
						ShopApplication.usersexOtherlogin=0;
						ShopApplication.userbirthother="";
						ShopApplication.api.unregisterApp();
					}
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 04);
					getActivity().startActivityForResult(intent, 04);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 04);
					getActivity().startActivityForResult(intent, 04);
				}
				break;

			default:
				break;
			}
		}
	}
	/**
	 * 当前fragment显示状态发生改变时执行的方法 隐藏是 hidden值为true
	 */
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden){
			StatService.onPageStart(getActivity(),	"个人中心");
			changeuserinfo();
		}else{
			StatService.onPageEnd(getActivity(),	"个人中心");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==04&&resultCode==04){
			changeuserinfo();
		}
	}
    /**
     * 更新用户信息的展示
     */
	private void changeuserinfo() {
		if (ShopApplication.isLogin) {
			logout.setVisibility(View.GONE);
			// 设置登录完成的信息
			if(ShopApplication.loginflag==1){
				//原生登录
				 // 使用DisplayImageOptions.Builder()创建DisplayImageOptions  
		        DisplayImageOptions options = new DisplayImageOptions.Builder()  
		            .showImageOnLoading(R.drawable.user_img)         // 设置图片下载期间显示的图片  
		            .showImageForEmptyUri(R.drawable.user_img)  // 设置图片Uri为空或是错误的时候显示的图片  
		            .showImageOnFail(R.drawable.user_img)       // 设置图片加载或解码过程中发生错误显示的图片      
		            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
		            .build();                                   // 创建配置过得DisplayImageOption对象  
				ImageLoader.getInstance().displayImage(ShopApplication.userimgurlrlogin, user_img,options);
				if(StringUtils.isNotBlank(ShopApplication.usernamelogin)){
					user_name.setText(ShopApplication.usernamelogin);
				}else{
					user_name.setText("请设置昵称");
				}
			}
			if(ShopApplication.loginflag==2){
				 // 使用DisplayImageOptions.Builder()创建DisplayImageOptions  
		        DisplayImageOptions options = new DisplayImageOptions.Builder()  
		            .showImageOnLoading(R.drawable.user_img)         // 设置图片下载期间显示的图片  
		            .showImageForEmptyUri(R.drawable.user_img)  // 设置图片Uri为空或是错误的时候显示的图片  
		            .showImageOnFail(R.drawable.user_img)       // 设置图片加载或解码过程中发生错误显示的图片      
		            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
		            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
		            .build();                                   // 创建配置过得DisplayImageOption对象  
				ImageLoader.getInstance().displayImage(ShopApplication.userimgurlOtherlogin, user_img,options);
				if(StringUtils.isNotBlank(ShopApplication.usernameOtherlogin)){
					user_name.setText(ShopApplication.usernameOtherlogin);
				}else{
					user_name.setText("");
				}
				
			}
		}else{
			logout.setVisibility(View.GONE);
		}
	}
	private class ChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if ("changeuserinfo".equals(action)) {
				changeuserinfo();
			}
		}
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(ShopApplication.mainflag==4)
		StatService.onPageEnd(getActivity(),"个人中心");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(ShopApplication.mainflag==4)
		StatService.onPageStart(getActivity(),	"个人中心");
	}
	
}
