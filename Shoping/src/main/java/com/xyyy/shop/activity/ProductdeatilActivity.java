package com.xyyy.shop.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.db.Cart;
import com.xyyy.shop.db.CartDao;
import com.xyyy.shop.model.GoodItemVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 加载商品详情的webview类
 * 
 * @author lxn
 */
public class ProductdeatilActivity extends BaseActivity {

	private WebView webview;
	private ProgressBar progressbar;
	private ImageView top_back;
	String url;
	private long id;

	private TextView item_count;
	private Button item_addcart;
	private CustomProgressDialog customProgressDialog;
	private String name;
	private String imgurl;
	private BigDecimal goodprice;
	Integer iselectron=0;
	private RelativeLayout cartnum_framelayout;
	private RelativeLayout num_rel;
    private int flag=1;//进入是标记 1商品分类进入的 2首界面进入的
    private String goodstate;//商品状态 01  待发布    02上架  03下架
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ShopApplication.activityList.add(this);
		setContentView(R.layout.activity_productdetail);

		flag = getIntent().getExtras().getInt("flag",1);
		id = getIntent().getExtras().getLong("id", 0);
		if(flag==1){
			//iselectron = getIntent().getExtras().getInt("iseletron");
			name = getIntent().getExtras().getString("name");
			imgurl = getIntent().getExtras().getString("imgurl");
			goodstate = getIntent().getExtras().getString("goodstate");
			goodprice = (BigDecimal) getIntent().getExtras().getSerializable(
					"goodprice");
		}else{
			VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GoodInfoURL+id, null, null, GoodItemVO.class, new HttpBackListener<GoodItemVO>() {

				@Override
				public void onSuccess(GoodItemVO t) {
					if(null!=t){
						if(null!=t.getEnnGoods()){
							name = t.getEnnGoods().getGoodsName();
							goodprice = t.getEnnGoods().getMallPrice();
							iselectron = t.getEnnGoods().getIsdianzi();
							goodstate=t.getEnnGoods().getGoodsState();
						}
						imgurl = t.getImgUrl();
					}
				}
				@Override
				public void onFail(String failstring) {
					Toast.makeText(ProductdeatilActivity.this,
							"获取商品信息失败！", 0).show();
				}

				@Override
				public void onError(VolleyError error) {
					Toast.makeText(ProductdeatilActivity.this,
							"获取商品信息失败！", 0).show();
				}
			}, false, null);
		}
		

		url = CommonVariable.GoodDeatilURL +id+"&device=android";
		top_back = (ImageView) findViewById(R.id.top_back);
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		customProgressDialog = new CustomProgressDialog(
				ProductdeatilActivity.this, "正在加入......");
		webview = (WebView) findViewById(R.id.webview);
		progressbar = (ProgressBar) findViewById(R.id.progressbar);

		cartnum_framelayout = (RelativeLayout) findViewById(R.id.cartnum_framelayout);
		num_rel = (RelativeLayout) findViewById(R.id.num_rel);
		item_count = (TextView) findViewById(R.id.item_count);
		item_addcart = (Button) findViewById(R.id.item_addcart);
		cartnum_framelayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ProductdeatilActivity.this,
						CartActivity.class);
				startActivity(intent);
			}
		});
		item_addcart.setOnClickListener(viewclick);
		initwebview();
		
	}
    
	
					
	@SuppressLint("SetJavaScriptEnabled")
	private void initwebview() {
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new JavaScriptInterface(), "shop");
		
		WebSettings settings = webview.getSettings();  
		String useragent = settings.getUserAgentString()+"micromessenger";  
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
				super.onProgressChanged(view, newProgress);
			}
		});
		webview.loadUrl(url);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 按下的如果是BACK，同时没有重复
			if (webview.canGoBack()) {
				webview.goBack();
			} else {
				finish();
			}
			// true 不传递 false 传递
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 根据登录状态 查询当前购物车的数量
	 */
	private void initdata() {
		// TODO 根据登录状态 查询当前购物车的数量
		if (ShopApplication.isLogin) {
			// 登录情况下 走接口调用查询当前购物车内商品的数量
			getAllcartnum();
		} else {
			// 未登录 查询本地数据库 查询出所有商品数量的 总数
			int goodnum = CartDao.getInstance().queryNumAll();
			if (goodnum > 0) {
				num_rel.setVisibility(View.VISIBLE);
				item_count.setText("" + goodnum);
			} else {
				num_rel.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 登录情况下查询用户购物车商品总数量
	 */
	private void getAllcartnum() {
		int userid = 0;
		if (ShopApplication.loginflag == 1) {
			userid = ShopApplication.userid;
		}
		if (ShopApplication.loginflag == 2) {
			userid = ShopApplication.useridother;
		}
		VolleyUtil.sendStringRequestByGetToString(
				CommonVariable.CartGetGoodNumURL + userid, null, null,
				new HttpBackBaseListener() {

					@Override
					public void onSuccess(String string) {
						int goodnum = StrToNumber.strToint(string);
						if (goodnum > 0) {
							num_rel.setVisibility(View.VISIBLE);
							item_count.setText("" + goodnum);
						} else {
							num_rel.setVisibility(View.GONE);
						}
					}

					@Override
					public void onFail(String failstring) {
						num_rel.setVisibility(View.GONE);
					}

					@Override
					public void onError(VolleyError error) {
						num_rel.setVisibility(View.GONE);
					}
				}, false, null);
	}

	OnClickListener viewclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO 点击事件
			switch (arg0.getId()) {

			case R.id.item_addcart:
				if(null!=goodstate&&goodstate.equals("03")){
					Toast.makeText(ProductdeatilActivity.this,
							"该商品已下架，无法加入购物车！", 0).show();
					return;
				}
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
					map.put("goodsId", id + "");
					customProgressDialog.show();
					VolleyUtil.sendJsonRequestByPost(
							CommonVariable.CartAddGoodURL, null, map,
							new HttpBackBaseListener() {

								@Override
								public void onSuccess(String string) {
									customProgressDialog.dismiss();
									/*AlertDialog.Builder builder = new AlertDialog.Builder(
											ProductdeatilActivity.this);
									builder.setTitle("提示");
									builder.setMessage("加入购物车成功");
									builder.setNegativeButton("确定", null);
									if (!isFinishing()) {
										builder.create().show();
									}*/
									Toast.makeText(ProductdeatilActivity.this, "加入购物车成功", 0).show();
									if (num_rel.getVisibility() == View.GONE) {
										num_rel.setVisibility(View.VISIBLE);
										item_count.setText(1 + "");
										return;
									}
									if (num_rel.getVisibility() == View.VISIBLE) {
										item_count.setText(StrToNumber
												.strToint(item_count.getText()
														.toString().trim())
												+ 1 + "");
									}
								}

								@Override
								public void onFail(String failstring) {
									customProgressDialog.dismiss();
									Toast.makeText(ProductdeatilActivity.this,
											"加入购物车失败！", 0).show();
								}

								@Override
								public void onError(VolleyError error) {
									customProgressDialog.dismiss();
									Toast.makeText(ProductdeatilActivity.this,
											"加入购物车失败！", 0).show();
								}
							}, false, null);
				} else {
					// 未登录
					/*
					 * Intent intent=new Intent();
					 * intent.setClass(ProductdeatilActivity.this,
					 * LoginActivity.class); intent.putExtra("flag", 06);
					 * startActivityForResult(intent, 06);
					 */
					int goodnum = CartDao.getInstance().queryNumbygoodid(
							(int) id);
					if (goodnum > 0) {
						Cart cartupdate = CartDao.getInstance()
								.queryCartbygoodid((int) id);
						/*
						 * cartupdate.setGoodid((int)id);
						 * cartupdate.setGoodname(name);
						 * cartupdate.setGoodimgurl(imgurl);
						 * cartupdate.setGoodprice(goodprice);
						 */
						cartupdate.setNum(goodnum + 1);
						CartDao.getInstance().updateoneGood(cartupdate);
					} else {
						// 操作本地数据
						Cart cartinsert = new Cart();
						cartinsert.setGoodid((int) id);
						cartinsert.setGoodname(name);
						cartinsert.setGoodimgurl(imgurl);
						cartinsert.setGoodprice(goodprice);
						//cartinsert.setIselectron(iselectron);
						// cartinsert.setNum(StrToNumber.strToint(item_count.getText().toString().trim()));
						cartinsert.setNum(1);
						CartDao.getInstance().insertGood(cartinsert);
					}
					/*AlertDialog.Builder builder = new AlertDialog.Builder(
							ProductdeatilActivity.this);
					builder.setTitle("提示");
					builder.setMessage("加入购物车成功");
					builder.setNegativeButton("确定", null);
					if (!isFinishing()) {
						builder.create().show();
					}*/
					Toast.makeText(ProductdeatilActivity.this, "加入购物车成功", 0).show();
					if (num_rel.getVisibility() == View.GONE) {
						num_rel.setVisibility(View.VISIBLE);
						item_count.setText(1 + "");
						return;
					}
					if (num_rel.getVisibility() == View.VISIBLE) {
						item_count.setText(StrToNumber.strToint(item_count
								.getText().toString().trim())
								+ 1 + "");
					}

				}
				break;

			default:
				break;
			}
		}
	};
	 /**
	  * 与webview 交互使用的
	  * @author lxn
	  *
	  */
	 class JavaScriptInterface {

		JavaScriptInterface() {
       }
       @JavascriptInterface
       public void gooddetail(String goodid) {
       	//跳转商品详情的
    	   Intent intent = new Intent();
			intent.setClass(ProductdeatilActivity.this, ProductdeatilActivity.class);
			intent.putExtra("flag",2);
			intent.putExtra("id",StrToNumber.strTolong(goodid));
			startActivity(intent);
			finish();
       }
       @JavascriptInterface
       public void gooddetailintroduce(String goodid) {
    	   //跳转商品详情图文介绍的
    	   Intent intent = new Intent();
    	   intent.setClass(ProductdeatilActivity.this, ProductdetailIntroduceActivity.class);
    	   intent.putExtra("url",CommonVariable.GoodDeatilIntroduceURL+goodid+"&device=android");
    	   startActivity(intent);
       }
       @JavascriptInterface
       public void goodevaluate(String goodid,String evaluate) {
    	   //跳转商品详情图文介绍的
    	   Intent intent = new Intent();
    	   intent.setClass(ProductdeatilActivity.this, ProductdetailEvaluateActivity.class);
    	   intent.putExtra("url",CommonVariable.GoodDeatilEvaluateURL+goodid+"&fivePec="+evaluate+"&device=android");
    	   startActivity(intent);
       }
   }
	 @Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			initdata();
			StatService.onResume(this);
		}

		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			/**
			 * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
			 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
			 */
			StatService.onPause(this);
		}
}
