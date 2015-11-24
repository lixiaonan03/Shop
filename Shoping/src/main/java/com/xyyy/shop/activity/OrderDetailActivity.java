package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.MyOrderItemItemAdapter;
import com.xyyy.shop.model.OrderInfoDetailVO2;
import com.xyyy.shop.model.OrderItemVO2;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.DateUtil;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.MyListView;
/**
 * 订单详情的界面
 */
public class OrderDetailActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private TextView order_status;
	private TextView order_yunfei;
	private TextView order_allmoney;
	private ImageView order_statusimg;
	private TextView order_consignee;
	private TextView order_consigneeaddress;
	private RelativeLayout order_logistics;
	private TextView order_logistics_textview;
	private TextView order_logistics_time;
	private MyListView listview;
	private TextView order_yunfei_textview;
	private TextView order_allmoney_textview;
	private RelativeLayout money_rel;
	private LinearLayout moneylin;
	private TextView moneyalltextview;
	
	
	private TextView order_id;
	private TextView orderpay_id;
	private TextView ordergiftpay;
	private TextView ordergiftcardcode;
	private TextView order_time;
	private TextView order_invoicetype;
	private TextView order_invoicecontent;
	private Button order_button;
	//退换货提示
	private LinearLayout hintLin;
	private TextView hintstr;
	
	private CustomProgressDialog customProgressDialog;
	private String cardcode;//礼品卡进入的cardcode
	private int flag;//进入标记 1表示 礼品卡进入 0原来的默认的
	//跳转支付明细所携带的
	private String allmoneystr;
	private String yunfeimoneystr;
	private String orderid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdetail);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("订单详情");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(flag==0){
					Intent intent = new Intent();
					intent.setClass(OrderDetailActivity.this, MyorderActivity.class);
					startActivity(intent);
					finish();
				}
				if(flag==1){
					finish();
				}
				
			}
		});
		flag=getIntent().getIntExtra("flag", 0);
		if(flag==1){
			cardcode=getIntent().getStringExtra("cardcode");
		}else{
			orderid=getIntent().getStringExtra("id");
		}
		customProgressDialog=new CustomProgressDialog(OrderDetailActivity.this, "正在加载......");
		initview();
		initdata();
	}

	private void initdata() {
		customProgressDialog.show();
		if(flag==1){
			VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetOrderByCardcodeURL+cardcode, null, null, OrderInfoDetailVO2.class, new HttpBackListener<OrderInfoDetailVO2>(){

				@Override
				public void onSuccess(OrderInfoDetailVO2 t) {
					customProgressDialog.dismiss();
					changeview(t);
				}

				@Override
				public void onFail(String failstring) {
					customProgressDialog.dismiss();
					Toast.makeText(OrderDetailActivity.this, "加载失败！", 0).show();
				}

				@Override
				public void onError(VolleyError error) {
					customProgressDialog.dismiss();
					Toast.makeText(OrderDetailActivity.this, "加载失败！", 0).show();
				}
				
			}, false, null);
		}else{
			VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetOrderDetailURL+orderid, null, null, OrderInfoDetailVO2.class, new HttpBackListener<OrderInfoDetailVO2>(){

				@Override
				public void onSuccess(OrderInfoDetailVO2 t) {
					customProgressDialog.dismiss();
					changeview(t);
				}

				@Override
				public void onFail(String failstring) {
					customProgressDialog.dismiss();
					Toast.makeText(OrderDetailActivity.this, "加载失败！", 0).show();
				}

				@Override
				public void onError(VolleyError error) {
					customProgressDialog.dismiss();
					Toast.makeText(OrderDetailActivity.this, "加载失败！", 0).show();
				}
				
			}, false, null);
		}
	}
    /**
     * 更加结果更新界面数据
     * @param t
     */
	private void changeview(OrderInfoDetailVO2 t) {
		orderid=t.getOrderId();
		// TODO 根据得到的结果 跟新页面上的内容
		if(t.getOrderStatus().equals("06")){
			if(t.getPayFlag().equals("1")){
				t.setOrderStatus("07");
			}
		}
		//01 去付款 02 03 04 查看物 05 去评价 07 再次购买
		//判断是否需要提示
		if(null!=t&&null!=t.getEnnOrderRecords()&&t.getEnnOrderRecords().size()>0){
			if(null!=t.getEnnOrderRecords().get(0)&&StringUtils.isNotBlank(t.getEnnOrderRecords().get(0).getRecDesc())){
				hintLin.setVisibility(View.VISIBLE);
				hintstr.setText(t.getEnnOrderRecords().get(0).getRecDesc());
			}
		}
		if(t.getOrderStatus().equals("01")){
			//待付款
			order_status.setText("待付款");
			order_statusimg.setImageResource(R.drawable.order_status01);
			order_logistics.setVisibility(View.GONE);
			order_button.setText("去付款");
			order_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent payintent=new Intent(OrderDetailActivity.this,PayActivity.class);
					payintent.putExtra("orderid",StrToNumber.strToint(orderid));
					startActivity(payintent);
					finish();
				}
			});
		}
		if(t.getOrderStatus().equals("02")||t.getOrderStatus().equals("03")||t.getOrderStatus().equals("04")){
			//未收货
			order_status.setText("待收货");
			order_statusimg.setImageResource(R.drawable.order_status02);
			order_logistics.setVisibility(View.GONE);
			order_button.setText("查看物流");
			order_button.setBackgroundResource(R.drawable.login_buttonap);
			order_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		if(t.getOrderStatus().equals("05")){
			//未评价
			order_status.setText("待评价");
			order_logistics.setVisibility(View.GONE);
			order_statusimg.setImageResource(R.drawable.order_status03);
			order_button.setText("去评价");
			order_button.setBackgroundResource(R.drawable.login_button);
			order_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(OrderDetailActivity.this, OrderEvaluateActivity.class);
					int userid = 0;
					if (ShopApplication.isLogin) {
						if(ShopApplication.loginflag==1){
							userid=ShopApplication.userid;
						}
						if(ShopApplication.loginflag==2){
							userid=ShopApplication.useridother;
						}
					}
					intent.putExtra("url", CommonVariable.OrderEvaluateURL+orderid+"&userId="+userid);
					startActivity(intent);
				}
			});
		}
		if(t.getOrderStatus().equals("07")){
			//已完成
			order_status.setText("已完成");
			order_logistics.setVisibility(View.GONE);
			order_statusimg.setImageResource(R.drawable.order_status04);
			order_button.setBackgroundResource(R.drawable.login_button);
			order_button.setText("交易完成");
		}
		if(null==t.getYunfei())t.setYunfei("0.00");
		if(null==t.getYouhui())t.setYouhui("0.00");
		order_yunfei.setText("运费金额：￥ "+t.getYunfei()+"   优惠：￥ "+t.getYouhui());
		
		if(null==t.getTotalPrice())t.setTotalPrice("0.00");
		order_allmoney.setText("应付总价：￥ "+t.getTotalPrice());
		
		if(null==t.getReceivePerson())t.setReceivePerson("");
		if(null==t.getReceivePersonPhone())t.setReceivePersonPhone("");
		order_consignee.setText("收货人： "+t.getReceivePerson()+"  "+t.getReceivePersonPhone());
		if(null==t.getReceiveAddress())t.setReceiveAddress("");
		order_consigneeaddress.setText("收货地址： "+t.getReceiveAddress());
		
		if(null==t.getWuliu())t.setWuliu("");
		order_logistics_textview.setText(t.getWuliu());
		order_logistics_time.setText(""+t.getReceiveGoodTime());
		
		List<OrderItemVO2> ordergoodlist;
		if(null!=t.getOrderItemVOs()){
			ordergoodlist = t.getOrderItemVOs();
		}else{
			ordergoodlist =new ArrayList<OrderItemVO2>();
		}
			
		MyOrderItemItemAdapter adapter = new MyOrderItemItemAdapter(OrderDetailActivity.this, ordergoodlist);
		listview.setAdapter(adapter);
		
		allmoneystr=t.getTotalPrice();
		yunfeimoneystr=t.getYunfei();
		moneyalltextview.setText("￥ "+t.getTotalPrice());
		order_yunfei_textview.setText("￥ "+t.getYunfei());
		order_allmoney_textview.setText("￥ "+t.getTotalPrice());
		
		if(null==t.getOrderSeq())t.setOrderSeq("");
		order_id.setText("订单编号："+t.getOrderSeq());
		
		if(flag==1){
			//礼品卡支付
			moneylin.setVisibility(View.GONE);
			ordergiftpay.setVisibility(View.VISIBLE);
			ordergiftcardcode.setVisibility(View.VISIBLE);
			orderpay_id.setVisibility(View.GONE);
			order_time.setVisibility(View.GONE);
			order_invoicetype.setVisibility(View.GONE);
			order_invoicecontent.setVisibility(View.GONE);
			ordergiftcardcode.setText("礼品卡号："+cardcode);
		}else{
			if(null!=t.getPayMethod()&&t.getPayMethod().equals("06")){
				moneylin.setVisibility(View.GONE);
				ordergiftpay.setVisibility(View.VISIBLE);
				ordergiftcardcode.setVisibility(View.VISIBLE);
				orderpay_id.setVisibility(View.GONE);
				order_time.setVisibility(View.GONE);
				order_invoicetype.setVisibility(View.GONE);
				order_invoicecontent.setVisibility(View.GONE);
				if(null==t.getPayedCardNum())t.setPayedCardNum("");
				ordergiftcardcode.setText("礼品卡号："+t.getPayedCardNum());
			}else{
				ordergiftpay.setVisibility(View.GONE);
				ordergiftcardcode.setVisibility(View.GONE);
				if(null==t.getPaySeq())t.setPaySeq("");
				orderpay_id.setText("支付单号："+t.getPaySeq());
				order_time.setText("下单时间："+DateUtil.getDateTime(t.getInsertOrderTime()));
				if(null==t.getInvoiceType())t.setInvoiceType("0");
				int invoicetype=StrToNumber.strToint(t.getInvoiceType());
				switch (invoicetype) {
				case 1:
					order_invoicetype.setText("发票类型：普通发票");
					break;

				default:
					break;
				}
				if(null==t.getInvoiceTitle()||"".equals(t.getInvoiceTitle()))t.setInvoiceTitle("无");
				order_invoicecontent.setText("发票抬头："+t.getInvoiceTitle());
			}
			
		}
		
	}

	private void initview() {
		//顶部订单状态
		order_status=(TextView)findViewById(R.id.order_status);
		order_yunfei=(TextView)findViewById(R.id.order_yunfei);
		order_allmoney=(TextView)findViewById(R.id.order_allmoney);
		order_statusimg=(ImageView)findViewById(R.id.order_statusimg);
		//退换货提示
		hintLin=(LinearLayout)findViewById(R.id.hintLin);
		hintstr=(TextView)findViewById(R.id.hintstr);
		
		//收货人 姓名电话  收货地址
		order_consignee=(TextView)findViewById(R.id.order_consignee);
		order_consigneeaddress=(TextView)findViewById(R.id.order_consigneeaddress);
		
		//物流信息
		order_logistics=(RelativeLayout)findViewById(R.id.order_logistics);
		order_logistics_textview=(TextView)findViewById(R.id.order_logistics_textview);
		order_logistics_time=(TextView)findViewById(R.id.order_logistics_time);
		//商品列表
		listview=(MyListView)findViewById(R.id.listview);
		listview.setFocusable(false);
		//运费和 总共所付费用
		order_yunfei_textview=(TextView)findViewById(R.id.order_yunfei_textview);
		order_allmoney_textview=(TextView)findViewById(R.id.order_allmoney_textview);
		moneylin=(LinearLayout)findViewById(R.id.moneylin);
		money_rel=(RelativeLayout)findViewById(R.id.money_rel);
		money_rel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				Intent intent=new Intent(OrderDetailActivity.this, OrderPayDetailActivity.class);
				intent.putExtra("orderId", orderid);
				intent.putExtra("allmoney", allmoneystr);
				intent.putExtra("yunfeimoney", yunfeimoneystr);
				startActivity(intent);
			}
		});
		moneyalltextview=(TextView)findViewById(R.id.moneyalltextview);
		
		
		//订单id
		order_id=(TextView)findViewById(R.id.order_id);
		orderpay_id=(TextView)findViewById(R.id.orderpay_id);
		ordergiftpay=(TextView)findViewById(R.id.ordergiftpay);
		ordergiftcardcode=(TextView)findViewById(R.id.ordergiftcardcode);
		order_time=(TextView)findViewById(R.id.order_time);
		order_invoicetype=(TextView)findViewById(R.id.order_invoicetype);
		order_invoicecontent=(TextView)findViewById(R.id.order_invoicecontent);
		
		//底部按钮
		order_button=(Button)findViewById(R.id.order_button);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(flag==0){
			Intent intent = new Intent();
			intent.setClass(OrderDetailActivity.this, MyorderActivity.class);
			startActivity(intent);
			finish();
		}
		if(flag==1){
			finish();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
