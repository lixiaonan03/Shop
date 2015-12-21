package com.xyyy.shop.fragment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.activity.CartActivity;
import com.xyyy.shop.activity.LoginActivity;
import com.xyyy.shop.activity.MydeliverynoticeActivity;
import com.xyyy.shop.activity.MyorderActivity;
import com.xyyy.shop.activity.MytasteActivity;
import com.xyyy.shop.activity.OtherWebviewActivity;
import com.xyyy.shop.activity.ProductdeatilActivity;
import com.xyyy.shop.activity.SearchActivity;
import com.xyyy.shop.db.Cart;
import com.xyyy.shop.db.CartDao;
import com.xyyy.shop.model.GoodItemVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.MySwipeRefreshLayout;
import com.zbar.lib.CaptureActivity;

/**
 * 首页模块
 * 
 * @author lxn
 */
public class HomeFragment extends Fragment {

	private WebView webview;
	private ProgressBar progressbar;
	private String url;
	private RelativeLayout gosearch;
	private MySwipeRefreshLayout swipeLayout;
	private ImageView erweima;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		url = CommonVariable.HomeURL;

		gosearch = (RelativeLayout) getView().findViewById(R.id.search);
		gosearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SearchActivity.class);
				startActivity(intent);
			}
		});
		//扫描二维码
		erweima=(ImageView)getView().findViewById(R.id.erweima);
		erweima.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 跳转到二维码扫描界面
				Intent intent = new Intent();
				intent.setClass(getActivity(), CaptureActivity.class);
				startActivity(intent);
			}
		});
		
		
		swipeLayout = (MySwipeRefreshLayout) getView().findViewById(
				R.id.swipe_container);
		
		swipeLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

					@Override
					public void onRefresh() {
						// 重新刷新页面
						if (webview.getProgress() == 100) {
							webview.loadUrl(url);
						}

					}
				});
		swipeLayout.setColorSchemeResources(R.color.holo_blue_bright,
				R.color.holo_green_light, R.color.holo_orange_light,
				R.color.holo_red_light);

		webview = (WebView) getView().findViewById(R.id.webview);
		webview.setHorizontalScrollBarEnabled(false);// 水平不显示
		webview.setVerticalScrollBarEnabled(false); // 垂直不显示
        
		
		progressbar = (ProgressBar) getView().findViewById(R.id.progressbar);
		initwebview();
	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	private void initwebview() {
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new JavaScriptInterface(), "shop");
		WebSettings settings = webview.getSettings();
		String useragent = settings.getUserAgentString() + "micromessenger";
		settings.setUserAgentString(useragent);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webview.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				progressbar.setVisibility(View.GONE);
				webview.setVisibility(View.GONE);
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				progressbar.setVisibility(View.VISIBLE);
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				progressbar.setVisibility(View.GONE);
			}
		});
		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				progressbar.setProgress(newProgress);
				if (newProgress == 100) {
					// 隐藏进度条
					swipeLayout.setRefreshing(false);
				} else {
					if (!swipeLayout.isRefreshing())
						swipeLayout.setRefreshing(true);
				}
				super.onProgressChanged(view, newProgress);
			}
		});

		webview.loadUrl(url);
	}

	private Handler mHandler = new Handler();

	/**
	 * 与webview 交互使用的
	 * 
	 * @author lxn
	 *
	 */
	final class JavaScriptInterface {
		int goodid = 0;
		String name;
		BigDecimal goodprice;
		Integer iselectron;

		JavaScriptInterface() {
		}

		/**
		 * This is not called on the UI thread. Post a runnable to invoke
		 * loadUrl on the UI thread.
		 */
		@JavascriptInterface
		public void clickOnAndroid() {
			mHandler.post(new Runnable() {
				public void run() {
					webview.loadUrl("javascript:wave()");
				}
			});
		}

		@JavascriptInterface
		public void gooddetail(String goodid) {
			// 跳转商品详情的
			Intent intent = new Intent();
			intent.setClass(getActivity(), ProductdeatilActivity.class);
			intent.putExtra("flag", 2);
			intent.putExtra("id", StrToNumber.strTolong(goodid));
			startActivity(intent);
		}
		@JavascriptInterface
		public void gooddetailRecommend(String goodid) {
			// 跳转推荐商品详情的
			StatService.onEvent(getActivity(),"home_recommend" , "首页推荐商品详情");
			Intent intent = new Intent();
			intent.setClass(getActivity(), ProductdeatilActivity.class);
			intent.putExtra("flag", 2);
			intent.putExtra("id", StrToNumber.strTolong(goodid));
			startActivity(intent);
		}

		@JavascriptInterface
		public void getShortcutNav(String gowhere) {
			// 中间快捷菜单的链接 订单 order 预告 advance 口味 taste
			Intent intent = new Intent();
			if (gowhere.equals("order")) {
				StatService.onEvent(getActivity(),"home_order" , "首页快捷订单");
				if (ShopApplication.isLogin) {
					intent.setClass(getActivity(), MyorderActivity.class);
					intent.putExtra("inflag", 1);
					getActivity().startActivity(intent);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 01);
					getActivity().startActivityForResult(intent, 01);
				}
			}
			if (gowhere.equals("advance")) {
				StatService.onEvent(getActivity(),"home_advance" , "首页快捷预告");
				if (ShopApplication.isLogin) {
					intent.setClass(getActivity(),
							MydeliverynoticeActivity.class);
					intent.putExtra("inflag", 1);
					getActivity().startActivity(intent);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 01);
					getActivity().startActivityForResult(intent, 01);
				}
			}
			if (gowhere.equals("taste")) {
				StatService.onEvent(getActivity(),"home_taste" , "首页快捷口味");
				if (ShopApplication.isLogin) {
					intent.setClass(getActivity(), MytasteActivity.class);
					intent.putExtra("inflag", 1);
					getActivity().startActivity(intent);
				} else {
					intent.setClass(getActivity(), LoginActivity.class);
					intent.putExtra("flag", 01);
					getActivity().startActivityForResult(intent, 01);
				}
			}
		}

		@JavascriptInterface
		public void goodlist(String id,String name) {
			if(StringUtils.isBlank(name)){
				name="";
			}
			StatService.onEvent(getActivity(),"home_classify" , name);
			// 跳转分类详情的
			int classifyid = 0;
			if (StringUtils.isBlank(id)) {
				classifyid = 0;
			} else {
				classifyid = Integer.parseInt(id);
			}
			Intent intent = new Intent();
			intent.setClass(getActivity(), SearchActivity.class);
			intent.putExtra("id", classifyid);
			startActivity(intent);
		}

		@JavascriptInterface
		public void gobuy(String id) {
			StatService.onEvent(getActivity(),"home_gobuy" , "首页推荐立即购买");
			// 立即购买
			if (StringUtils.isBlank(id)) {
				goodid = 0;
			} else {
				goodid = Integer.parseInt(id);
			}
			VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GoodInfoURL
					+ goodid, null, null, GoodItemVO.class,
					new HttpBackListener<GoodItemVO>() {
						@Override
						public void onSuccess(GoodItemVO t) {
							if (null != t) {
								if (null != t.getEnnGoods()) {
									name = t.getEnnGoods().getGoodsName();
									goodprice = t.getEnnGoods().getMallPrice();
									iselectron = t.getEnnGoods().getIsdianzi();
								}
								String imgurl = t.getImgUrl();
								// 加入购物车
								if (ShopApplication.isLogin) {
									// 登录情况下
									Integer userid = null;
									Map<String, String> map = new HashMap<String, String>();
									if (ShopApplication.loginflag == 1) {
										userid = ShopApplication.userid;
									}
									if (ShopApplication.loginflag == 2) {
										userid = ShopApplication.useridother;
									}
									map.put("membId", userid + "");
									map.put("cartNum", 1 + "");
									map.put("goodsId", goodid + "");
									VolleyUtil.sendJsonRequestByPost(
											CommonVariable.CartAddGoodURL,
											null, map,
											new HttpBackBaseListener() {

												@Override
												public void onSuccess(
														String string) {
													Toast.makeText(
															getActivity(),
															"加入购物车成功！", 0)
															.show();
													Intent intent = new Intent(
															getActivity(),
															CartActivity.class);
													startActivity(intent);
												}

												@Override
												public void onFail(
														String failstring) {
													Toast.makeText(
															getActivity(),
															"加入购物车失败！", 0)
															.show();
												}

												@Override
												public void onError(
														VolleyError error) {
													Toast.makeText(
															getActivity(),
															"加入购物车失败！", 0)
															.show();
												}
											}, false, null);
								} else {
									// 未登录
									int goodnum = CartDao.getInstance()
											.queryNumbygoodid((int) goodid);
									if (goodnum > 0) {
										Cart cartupdate = CartDao
												.getInstance()
												.queryCartbygoodid((int) goodid);
										cartupdate.setNum(goodnum + 1);
										CartDao.getInstance().updateoneGood(
												cartupdate);
									} else {
										// 操作本地数据
										Cart cartinsert = new Cart();
										cartinsert.setGoodid(goodid);
										cartinsert.setGoodname(name);
										cartinsert.setGoodimgurl(imgurl);
										// cartinsert.setIselectron(iselectron);
										cartinsert.setGoodprice(goodprice);
										cartinsert.setNum(1);
										CartDao.getInstance().insertGood(
												cartinsert);
									}
									Toast.makeText(getActivity(), "加入购物车成功！", 0)
											.show();
									Intent intent = new Intent(getActivity(),
											CartActivity.class);
									startActivity(intent);
								}
							}
						}

						@Override
						public void onFail(String failstring) {
						}

						@Override
						public void onError(VolleyError error) {

						}
					}, false, null);
		}

		@JavascriptInterface
		public void imgtop(String valname, String valfrom) {
			StatService.onEvent(getActivity(),"home_topimg" , "首页轮播图点击");
			if (valname.equals("playimage--")) {
				// 商品详情
				Intent intent = new Intent();
				intent.setClass(getActivity(), ProductdeatilActivity.class);
				intent.putExtra("flag", 2);
				intent.putExtra("id", StrToNumber.strTolong(valfrom));
				startActivity(intent);
			}
			if (valname.equals("orde--")) {
				// 跳转顶部图片链接
				Intent intent = new Intent();
				intent.setClass(getActivity(), OtherWebviewActivity.class);
				intent.putExtra("url", valfrom + "?device=android");
				startActivity(intent);
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 01 && resultCode == 01) {

		}
	}
	
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		if (!hidden) {
			StatService.onPageStart(getActivity(),	"首页模块");
		}else{
			StatService.onPageEnd(getActivity(),"首页模块");
		}
		super.onHiddenChanged(hidden);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(ShopApplication.mainflag==0)
		StatService.onPageStart(getActivity(),	"首页模块");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(ShopApplication.mainflag==0)
		StatService.onPageEnd(getActivity(),"首页模块");
	}
}
