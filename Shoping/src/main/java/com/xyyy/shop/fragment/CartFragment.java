package com.xyyy.shop.fragment;

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
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.xyyy.shop.activity.CartOrderDetailActivity;
import com.xyyy.shop.activity.LoginActivity;
import com.xyyy.shop.adapter.MycartItemAdapter;
import com.xyyy.shop.db.Cart;
import com.xyyy.shop.db.CartDao;
import com.xyyy.shop.model.GoodCarVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 购物车模块
 * 
 * @author lxn
 */
public class CartFragment extends Fragment {

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_cart, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initview();
		customProgressDialog = new CustomProgressDialog(getActivity(),
				"正在加载......");
		
		// 初始化广播
		receiver = new ChangeReceiver();
		LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("changeallmoney");
		intentFilter.addAction("changeallmoneyand");
		broadcastManager.registerReceiver(receiver, intentFilter);
				
		changeviewbylogin();
	}

	private void initview() {
		yeslogin = (RelativeLayout) getView().findViewById(R.id.yeslogin);
		nodata = (LinearLayout) getView().findViewById(R.id.nodata);
		nodata.setOnClickListener(viewclick);
		nodatatext=(TextView)getView().findViewById(R.id.nodatatext);
		nodataimg=(ImageView)getView().findViewById(R.id.nodataimg);
		
		cart_edit = (TextView) getView().findViewById(R.id.cart_edit);
		checkBoxall = (CheckBox) getView().findViewById(R.id.checkBoxall);
		checkBoxall.setChecked(true);
		allmoneytextview = (TextView) getView().findViewById(
				R.id.allmoneytextview);
		pay = (Button) getView().findViewById(R.id.pay);
		listview = (ListView) getView().findViewById(R.id.listview);
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
		adapter = new MycartItemAdapter(getActivity(), list);
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
						cart_edit.setText("编辑");
						flag = 1;
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
											Toast.makeText(getActivity(), "亲，您的购物车里包含电子商品,电子商品需和实体物品分开结算!", 1).show();
											cartgoodflag=0;
											return;
										}
									}
								}
								Intent intentgo=new Intent(getActivity(),CartOrderDetailActivity.class);
								intentgo.putExtra("list",(Serializable)listgo);
								startActivity(intentgo);
							}else{
								Toast.makeText(getActivity(), "请选择要结算的商品！", 0).show();
							}
						}
						
						
					}else{
						Toast.makeText(getActivity(), "请先登录！", 0).show();
						Intent intent = new Intent(getActivity(), LoginActivity.class);
						intent.putExtra("flag", 02);
						startActivityForResult(intent, 02);
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
								getActivity());
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
												LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
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
						//TODO 在为登录的情况下删除 本地数据库中数据
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
							LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
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
			//微信登录方式
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
						Toast.makeText(getActivity(),"加载购物车数据失败！",Toast.LENGTH_SHORT).show();
						nodatatext.setText("您的购物车当前没有任何商品。");
						yeslogin.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						cart_edit.setVisibility(View.GONE);
						Toast.makeText(getActivity(),"加载购物车数据失败！",Toast.LENGTH_SHORT).show();
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
	public void onHiddenChanged(boolean hidden) {
		if (!hidden) {
			Intent intent = new Intent("changeallmoney");
			LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
			changeviewbylogin();
			StatService.onPageStart(getActivity(),	"购物车模块");
		}else{
			StatService.onPageEnd(getActivity(),"购物车模块");
		}
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 02 && resultCode == 02) {
		    changeviewbylogin();
		}
	}
    
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if(!isHidden()){
			changeviewbylogin();
		}
		if(ShopApplication.mainflag==2)
		StatService.onPageStart(getActivity(),	"购物车模块");
		super.onResume();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(ShopApplication.mainflag==2)
		StatService.onPageEnd(getActivity(),"购物车模块");
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
}
