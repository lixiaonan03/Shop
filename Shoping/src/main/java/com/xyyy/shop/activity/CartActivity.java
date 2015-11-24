package com.xyyy.shop.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.MycartItemAdapter;
import com.xyyy.shop.db.Cart;
import com.xyyy.shop.db.CartDao;
import com.xyyy.shop.model.GoodCarVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 购物车activity的界面
 */
public class CartActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
    
	private TextView cart_edit;// 编辑按钮
	private CheckBox checkBoxall;
	private TextView allmoneytextview;
	private Button pay;
	private ListView listview;
	private MycartItemAdapter adapter;
	private List<Cart> list = new ArrayList<Cart>();
	private double allmoney;
	private int flag = 1;// 1 代表支付状态 2 代表 编辑状态
	private CustomProgressDialog customProgressDialog;
	private RelativeLayout yeslogin;
	private LinearLayout nodata;
	private TextView nodatatext;
	private ImageView nodataimg;
	private int cartgoodflag=0;//购物车中的商品标记   0是无电子类商品 1有电子类商品
	private ChangeReceiver receiver;
	
	private boolean isclick=false;//底部全选按钮 是否是通过广播接收处理的 false不是  true 是
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("购物车");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		initview();
		customProgressDialog = new CustomProgressDialog(CartActivity.this,
				"正在加载......");
		
		// 初始化广播
		receiver = new ChangeReceiver();
		LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(CartActivity.this);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("changeallmoney");
		intentFilter.addAction("changeallmoneyand");
		broadcastManager.registerReceiver(receiver, intentFilter);
				
		
	}
	

	private void initview() {
		yeslogin = (RelativeLayout)findViewById(R.id.yeslogin);
		nodata = (LinearLayout)findViewById(R.id.nodata);
		nodata.setOnClickListener(viewclick);
		nodatatext=(TextView)findViewById(R.id.nodatatext);
		nodataimg=(ImageView)findViewById(R.id.nodataimg);
		
		cart_edit = (TextView)findViewById(R.id.cart_edit);
		checkBoxall = (CheckBox)findViewById(R.id.checkBoxall);
		checkBoxall.setChecked(true);
		allmoneytextview = (TextView)findViewById(
				R.id.allmoneytextview);
		pay = (Button)findViewById(R.id.pay);
		listview = (ListView)findViewById(R.id.listview);
		cart_edit.setOnClickListener(viewclick);
		pay.setOnClickListener(viewclick);

		checkBoxall.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(isclick){
					isclick=false;
					return;
				}else{
					isclick=false;
					for (Cart mycart : adapter.get_list()) {
						if (arg1) {
							mycart.setFlag(0);
						} else {
							mycart.setFlag(1);
						}
					}
					adapter.notifyDataSetChanged();
				}
			}
		});
		adapter = new MycartItemAdapter(CartActivity.this, list);
		listview.setAdapter(adapter);

		changeallmoney();
	}
	
	/**
	 * 根据登录状态改变view
	 */
	void changeviewbylogin() {
		if (ShopApplication.isLogin) {
			yeslogin.setVisibility(View.VISIBLE);
			nodata.setVisibility(View.GONE);
			initdata();
		} else {
			//去数据库查询 未登录的购物车的数据
			list=CartDao.getInstance().queryAllGood();
			if(null!=list&&list.size()>0){
				cart_edit.setVisibility(View.VISIBLE);
				yeslogin.setVisibility(View.VISIBLE);
				nodata.setVisibility(View.GONE);
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
				changeallmoney();
			}else{
				cart_edit.setVisibility(View.GONE);
				nodatatext.setText("您的购物车当前没有任何商品。");
				yeslogin.setVisibility(View.GONE);
				nodata.setVisibility(View.VISIBLE);
			}
			/*yeslogin.setVisibility(View.GONE);
			nodata.setVisibility(View.VISIBLE);
			nodatatext.setText("立即登录，选择自己喜欢的菜品");*/
		}
	}

	OnClickListener viewclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.cart_edit:
				if(null!=list&&list.size()>0){
					//编辑按钮
					if (flag == 1) {
						// 当前是支付状态 点击后变成编辑状态
						cart_edit.setText("完成");
						flag = 2;
						adapter.setFlag(flag);
						allmoneytextview.setVisibility(View.GONE);
						pay.setText("删除");
						
						checkBoxall.setChecked(false);
						for (Cart mycart : adapter.get_list()) {
							mycart.setFlag(1);
						}
						adapter.notifyDataSetChanged();
						changeallmoney();
					} else {
						flag = 1;
						cart_edit.setText("编辑");
						adapter.setFlag(flag);
						allmoneytextview.setVisibility(View.VISIBLE);
						pay.setText("去结算");
						checkBoxall.setChecked(true);
						for (Cart mycart : adapter.get_list()) {
							mycart.setFlag(0);
						}
						adapter.notifyDataSetChanged();
						changeallmoney();
					}
				}
				break;
			case R.id.pay:
				if (flag == 1) {
					//TODO  当前是支付状态 根据登录状态判断点击事件 订单详情界面
					if (ShopApplication.isLogin) {
						//登录状态下
						if(null!=adapter.get_list()&&adapter.get_list().size()>0){
							List<Cart> listgo=new ArrayList<Cart>();
							for (Cart cart : adapter.get_list()) {
								if(cart.getFlag()==0){
									listgo.add(cart);
								}
							}
							if(listgo.size()>0){
								for (Cart cart : listgo) {
									if(cart.getIselectron()==1){
										cartgoodflag=1;//有电子类商品了
									}
								}
								for (Cart cart : listgo) {
									if(cartgoodflag==1){
										//如果有电子卡的情况下 再有实体类商品就提示
										if(cart.getIselectron()==0){
											Toast.makeText(CartActivity.this, "亲，您的购物车里包含电子商品,电子商品需和实体物品分开结算!", 1).show();
											cartgoodflag=0;
											return;
										}
									}
								}
								Intent intentgo=new Intent(CartActivity.this,CartOrderDetailActivity.class);
								intentgo.putExtra("list",(Serializable)listgo);
								startActivity(intentgo);
							}else{
								Toast.makeText(CartActivity.this, "请选择要结算的商品！", 0).show();
							}
						}
					}else{
						Toast.makeText(CartActivity.this, "请先登录！", 0).show();
						Intent intent = new Intent(CartActivity.this, LoginActivity.class);
						intent.putExtra("flag", 02);
						startActivityForResult(intent, 03);
					}
					
				} else {
					final StringBuilder url=new StringBuilder("/");
					for (Cart goodCarVO : adapter.get_list()) {
						if(goodCarVO.getFlag()==0){
							url.append("---");
							url.append(goodCarVO.getGoodid());
						}
					}
					if(url.length()>=5){
						
					}else{
						Toast.makeText(ShopApplication.application, "请选择要删除的商品！", 2).show();
						return;
					}
					
					
					// 购物车删除
					if (ShopApplication.isLogin) {
						//登录状态下
						AlertDialog.Builder builder = new AlertDialog.Builder(
								CartActivity.this);
						builder.setTitle("提示");
						builder.setMessage("您确定要删除么？");
						builder.setNegativeButton("取消", null);
						builder.setPositiveButton("删除",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										//走提交接口
									
										int memberid=0;
										if(ShopApplication.loginflag==1){
											memberid=ShopApplication.userid;
										}
										if(ShopApplication.loginflag==2){
											memberid=ShopApplication.useridother;
										}
										String delurl=CommonVariable.CartDeleteGoodURL+memberid+url.toString();
										customProgressDialog.show();
										VolleyUtil.sendStringRequestByGetToList(delurl, null, null, GoodCarVO.class,new HttpBackListListener<GoodCarVO>(){

											@Override
											public void onSuccess(List<GoodCarVO> t) {
												customProgressDialog.dismiss();
												Intent intent = new Intent("changeallmoney");
												LocalBroadcastManager.getInstance(CartActivity.this).sendBroadcast(intent);
												if(null!=t&&t.size()>0){
													yeslogin.setVisibility(View.VISIBLE);
													nodata.setVisibility(View.GONE);
													list=changeGoodCarVOToCart(t);
													for (Cart mycart : list) {
														mycart.setFlag(1);
													}
													adapter.set_list(list);
													adapter.notifyDataSetChanged();
													changeallmoney();
												}else{
													nodatatext.setText("您的购物车当前没有任何商品。");
													yeslogin.setVisibility(View.GONE);
													nodata.setVisibility(View.VISIBLE);
												}
											}

											@Override
											public void onFail(String failstring) {
												customProgressDialog.dismiss();
												yeslogin.setVisibility(View.GONE);
												nodata.setVisibility(View.VISIBLE);
											}

											@Override
											public void onError(VolleyError error) {
												customProgressDialog.dismiss();
												yeslogin.setVisibility(View.GONE);
												nodata.setVisibility(View.VISIBLE);
											}}, false, null);
										dialog.dismiss();// 取消dialog，或dismiss掉
									}
								});
						builder.create().show();
					}else{
						//TODO 在未登录的情况下删除 本地数据库中数据
						List<Cart> listdel=new ArrayList<Cart>();
						for (Cart cart : adapter.get_list()) {
							if(cart.getFlag()==0){
								listdel.add(cart);
							}
						}
						if(listdel.size()==0){
							Toast.makeText(ShopApplication.application, "请选择要删除的商品！", 2).show();
							return;
						}else{
							CartDao.getInstance().deleteCarts(listdel);
							list=CartDao.getInstance().queryAllGood();
							Intent intent = new Intent("changeallmoney");
							LocalBroadcastManager.getInstance(CartActivity.this).sendBroadcast(intent);
							if(null!=list&&list.size()>0){
								yeslogin.setVisibility(View.VISIBLE);
								nodata.setVisibility(View.GONE);
								for (Cart mycart : list) {
									mycart.setFlag(1);
								}
								adapter.set_list(list);
								adapter.notifyDataSetChanged();
								changeallmoney();
								
								
							}else{
								nodatatext.setText("您的购物车当前没有任何商品。");
								yeslogin.setVisibility(View.GONE);
								nodata.setVisibility(View.VISIBLE);
								adapter.notifyDataSetChanged();
								
							}
						}
					}
				}
				break;
			case R.id.nodata:
				
				break;
			default:
				break;
			}
		}
	};
    /**
     * 登录情况下 查询购物车的数据
     */
	private void initdata() {
		customProgressDialog.show();
		int memberid = 0;
		if(ShopApplication.loginflag==1){
			memberid=ShopApplication.userid;
		}
		if(ShopApplication.loginflag==2){
			memberid=ShopApplication.useridother;
		}
		VolleyUtil.sendStringRequestByGetToList(CommonVariable.CartGetGoodURL+memberid,
				null, null, GoodCarVO.class,
				new HttpBackListListener<GoodCarVO>() {

					@Override
					public void onSuccess(List<GoodCarVO> t) {
						customProgressDialog.dismiss();
						if(null!=t&&t.size()>0){
							cart_edit.setVisibility(View.VISIBLE);
							yeslogin.setVisibility(View.VISIBLE);
							nodata.setVisibility(View.GONE);
							list=changeGoodCarVOToCart(t);
							adapter.set_list(list);
							adapter.notifyDataSetChanged();
							changeallmoney();
						}else{
							cart_edit.setVisibility(View.GONE);
							nodatatext.setText("您的购物车当前没有任何商品。");
							yeslogin.setVisibility(View.GONE);
							nodata.setVisibility(View.VISIBLE);
						}
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						cart_edit.setVisibility(View.GONE);
						Toast.makeText(CartActivity.this,"加载购物车数据失败！",Toast.LENGTH_SHORT).show();
						nodatatext.setText("您的购物车当前没有任何商品。");
						yeslogin.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						cart_edit.setVisibility(View.GONE);
						Toast.makeText(CartActivity.this,"加载购物车数据失败！",Toast.LENGTH_SHORT).show();
						nodatatext.setText("您的购物车当前没有任何商品。");
						yeslogin.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}

				}, false, null);
	}

	/**
	 * 改变总结金额
	 */
	private void changeallmoney() {
		allmoney = 0.00;
		for (Cart mycart : adapter.get_list()) {
			if(mycart.getFlag()==0){
				allmoney += (mycart.getGoodprice().multiply(new BigDecimal(mycart.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue();
			}
		}
		BigDecimal   f1   =   new BigDecimal(allmoney).setScale(2,   BigDecimal.ROUND_HALF_UP); 
		allmoneytextview.setText("合计：￥" + f1);
	}
	/**
	 * 把服务端接口使用的GoodCarVO 转化成 自己使用的Cart 实体
	 * @param t
	 * @return
	 */
	private List<Cart> changeGoodCarVOToCart(List<GoodCarVO> t) {
		List<Cart> cartlist=new ArrayList<Cart>();
		for (GoodCarVO goodcarvo : t) {
			Cart cart=new Cart();
			cart.setNum(StrToNumber.strToint(goodcarvo.getNum()));
			if(null!=goodcarvo&&null!=goodcarvo.getGood().getGoodsId()){
				cart.setGoodid(goodcarvo.getGood().getGoodsId());
			}else{
				cart.setGoodid(0);
			}
			if(null!=goodcarvo&&null!=goodcarvo.getGood().getGoodsName()){
				cart.setGoodname(goodcarvo.getGood().getGoodsName());
			}else{
				cart.setGoodname("");
			}
			if(null!=goodcarvo&&null!=goodcarvo.getGood().getMallPrice()){
				cart.setGoodprice(goodcarvo.getGood().getMallPrice());
			}else{
				cart.setGoodprice(new BigDecimal(0.00));
			}
			if(null!=goodcarvo&&null!=goodcarvo.getGoodImgs()&&goodcarvo.getGoodImgs().size()>0){
				if(null!=goodcarvo.getGoodImgs().get(0).getImgPath())
				cart.setGoodimgurl(goodcarvo.getGoodImgs().get(0).getImgPath());
			}
			//TODO  是否是电子卡
			if(null!=goodcarvo&&null!=goodcarvo.getIsElect()){
				if(goodcarvo.getIsElect().equals("0")){
					cart.setIselectron(0);
				}
				if(goodcarvo.getIsElect().equals("1")){
					cart.setIselectron(1);
				}
			}else{
				cart.setIselectron(0);
			}
			cartlist.add(cart);
		}
		return cartlist;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 03 && resultCode == 02) {
		    changeviewbylogin();
		}
	}
    
	private class ChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if ("changeallmoney".equals(action)) {
                changeallmoney();
			}
			if ("changeallmoneyand".equals(action)) {
				changeallmoney();
				 if(checkBoxall.isChecked()){
				    	isclick=true;
				    }
				checkBoxall.setChecked(false);
			}
		}
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		changeviewbylogin();
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
