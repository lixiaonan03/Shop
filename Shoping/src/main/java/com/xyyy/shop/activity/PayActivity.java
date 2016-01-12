package com.xyyy.shop.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.model.EnnMemberPaypwd;
import com.xyyy.shop.model.EnnPayRecord;
import com.xyyy.shop.model.InsertPayDTO;
import com.xyyy.shop.model.PayDTO;
import com.xyyy.shop.model.PayDetailDTO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.wxapi.WXPayEntryActivity;

/**
 * 支付界面
 */
public class PayActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	private int orderid;
	private TextView receviceaddress;
	private TextView receviceperson;
	private TextView invoice;
	private RadioGroup radiogroup;
	private String orderseq;
	private CustomProgressDialog customProgressDialog;

	private LinearLayout otherpay_lin;// 其他支付的布局界面

	private TextView ordermoney, membercardpaymoney, otherpaymoney;
	private EditText membercardpassword;
	private TextView membercardpaypasswordset;
	private TextView membercardpaypasswordfind;

	private int payflag;// 0未选择 1微信支付 2支付宝
	private int otherpayIsgone = 0;// 其他支付的界面是否需要展示 0表示2个都需要展示 1表示其他支付不需要展示2
									// 会员卡支付不需要展示
	private LinearLayout membercard_payLin;
	private String membId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		customProgressDialog = new CustomProgressDialog(PayActivity.this,
				"正在加载订单信息......");
		orderseq = getIntent().getStringExtra("orderseq");
		orderid = getIntent().getIntExtra("orderid", 0);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("支付详情");
		top_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		// 订单金额
		ordermoney = (TextView) findViewById(R.id.ordermoney);
		membercardpaymoney = (TextView) findViewById(R.id.membercardpaymoney);// 会员卡支付
		otherpaymoney = (TextView) findViewById(R.id.otherpaymoney);// 其他支付

		otherpay_lin = (LinearLayout) findViewById(R.id.otherpay_lin);// 其他支付
		membercard_payLin = (LinearLayout) findViewById(R.id.membercard_payLin);

		membercardpassword = (EditText) findViewById(R.id.membercardpassword);
		membercardpaypasswordset = (TextView) findViewById(R.id.membercardpaypasswordset);
		membercardpaypasswordfind = (TextView) findViewById(R.id.membercardpaypasswordfind);
		membercardpaypasswordset.setOnClickListener(viewclick);
		membercardpaypasswordfind.setOnClickListener(viewclick);

		receviceaddress = (TextView) findViewById(R.id.receviceaddress);
		receviceperson = (TextView) findViewById(R.id.receviceperson);
		invoice = (TextView) findViewById(R.id.invoice);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.pay_weixin:
					// 微信支付
					payflag = 1;
					break;
				case R.id.pay_zhifubao:
					// 支付宝支付
					payflag = 2;
					break;
				default:
					break;
				}
			}
		});

		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				gopayweixin();
			}
		});
		initdata();
		initdata1();
	}

	OnClickListener viewclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			switch (arg0.getId()) {
			case R.id.membercardpaypasswordset:
				// 设置会员卡支付密码
				intent.setClass(PayActivity.this,
						MembercardPayPasswordSetActivity.class);
				break;
			case R.id.membercardpaypasswordfind:
				// 找回会员卡支付密码
				intent.setClass(PayActivity.this,
						MembercardPayPasswordFindActivity.class);
				break;

			default:
				break;
			}
			startActivity(intent);
		}
	};

	/**
	 * 判断用户是否有会员卡支付密码
	 */
	private void initdata1() {
		customProgressDialog.show();
		int userid = 0;
		if (ShopApplication.loginflag == 1) {
			userid = ShopApplication.userid;
		}
		if (ShopApplication.loginflag == 2) {
			userid = ShopApplication.useridother;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("membId", userid);
		VolleyUtil.sendObjectByPostToList(
				CommonVariable.GetUserMembercardPayPasswordURL, null, map,
				EnnMemberPaypwd.class,
				new HttpBackListListener<EnnMemberPaypwd>() {

					@Override
					public void onSuccess(List<EnnMemberPaypwd> t) {
						if (null != t && t.size() > 0) {
							membercardpaypasswordset.setVisibility(View.GONE);
						} else {
							membercardpaypasswordfind.setVisibility(View.GONE);
						}
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(PayActivity.this, "加载数据失败！",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(PayActivity.this, "加载数据失败！",
								Toast.LENGTH_SHORT).show();
					}

				}, false, null);
	}

	/**
	 * 根据订单id 获取相关信息
	 */
	private void initdata() {
		customProgressDialog.show();
		VolleyUtil.sendStringRequestByGetToBean(
				CommonVariable.GetOrderPayDetailURL + orderid, null, null,
				PayDetailDTO.class, new HttpBackListener<PayDetailDTO>() {
					@Override
					public void onSuccess(PayDetailDTO t) {
						customProgressDialog.dismiss();
						if (null != t.getEnnOrder()
								&& null != t.getEnnOrder().getOrderSeq()) {
							orderseq = t.getEnnOrder().getOrderSeq();
						}
						changeview(t);
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(PayActivity.this, "加载订单信息失败！",Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(PayActivity.this, "加载订单信息失败！", Toast.LENGTH_SHORT).show();
					}

				}, false, null);

	}

	/**
	 * 根据结果更新界面数据
	 * 
	 * @param t
	 */
	private void changeview(PayDetailDTO t) {
		// TODO 根据得到的结果 跟新页面上的内容
		/*
		 * if (null == t.getReceivePerson()) t.setReceivePerson(""); if (null ==
		 * t.getReceivePersonPhone()) t.setReceivePersonPhone("");
		 * receviceperson.setText(t.getReceivePerson() + "  " +
		 * t.getReceivePersonPhone()); if (null == t.getReceiveAddress())
		 * t.setReceiveAddress("");
		 * receviceaddress.setText(t.getReceiveAddress());
		 */

		/*
		 * if (null == t.getInvoiceTitle() || "".equals(t.getInvoiceTitle()))
		 * t.setInvoiceTitle("无"); invoice.setText("发票信息：" +
		 * t.getInvoiceTitle());
		 */
		if (null != t.getEnnOrder()) {
			if (null != t.getEnnOrder().getTotalPrice()) {
				ordermoney.setText("￥"
						+ t.getEnnOrder().getTotalPrice()
								.setScale(2, BigDecimal.ROUND_HALF_UP));
				if (null != t.getEnnPayRecords()
						&& t.getEnnPayRecords().size() > 0) {
					BigDecimal memcardpay = new BigDecimal(0);
					BigDecimal otherpay = new BigDecimal(0);
					for (EnnPayRecord i : t.getEnnPayRecords()) {
						if (i.getPayMethod().equals("05")) {
							memcardpay = memcardpay.add(i.getPayAmount());
						}
						if (i.getPayMethod().equals("02")) {
							otherpay = otherpay.add(i.getPayAmount());
						}

					}
					if (memcardpay.compareTo(t.getEnnOrder().getTotalPrice()) == -1) {
						if (memcardpay.compareTo(new BigDecimal(0)) == -1) {
							// 会员卡支付不需要展示
							otherpayIsgone = 2;
							membercardpaymoney.setVisibility(View.GONE);
							otherpay_lin.setVisibility(View.VISIBLE);
							radiogroup.setVisibility(View.VISIBLE);
							membercard_payLin.setVisibility(View.GONE);

						} else {
							// 2个都需要展示
							otherpayIsgone = 0;
							membercardpaymoney.setVisibility(View.VISIBLE);
							otherpay_lin.setVisibility(View.VISIBLE);
							radiogroup.setVisibility(View.VISIBLE);
							membercardpaymoney.setText("会员卡支付：￥"
									+ memcardpay.setScale(2,
											BigDecimal.ROUND_HALF_UP));
							otherpaymoney.setText("其他支付：￥"
									+ otherpay.setScale(2,
											BigDecimal.ROUND_HALF_UP));
						}

					} else {
						// 其他支付不需要展示
						otherpayIsgone = 1;
						membercardpaymoney.setVisibility(View.VISIBLE);
						otherpay_lin.setVisibility(View.GONE);
						radiogroup.setVisibility(View.GONE);
						membercardpaymoney.setText("会员卡支付：￥"
								+ t.getEnnOrder().getTotalPrice()
										.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				} else {
					otherpayIsgone = 2;
					membercardpaymoney.setVisibility(View.GONE);
					otherpay_lin.setVisibility(View.GONE);
					radiogroup.setVisibility(View.VISIBLE);
					membercard_payLin.setVisibility(View.GONE);
				}
			}
		}
	}

	/**
	 * 微信统一下单的url
	 */
	private void gopayweixin() {
		if (otherpayIsgone != 1) {
			if (!(ShopApplication.api.isWXAppInstalled() && ShopApplication.api
					.isWXAppSupportAPI())) {
				Toast.makeText(PayActivity.this, "请安装最新版本的微信客户端！",Toast.LENGTH_SHORT).show();
				return;
			}
		}
		InsertPayDTO insertpaydto = new InsertPayDTO();
		insertpaydto.setOut_trade_no(orderseq);
		insertpaydto.setTrade_type("APP");
		if (otherpayIsgone != 2) {
			if(StringUtils.isBlank(membercardpassword.getText().toString()
					.trim())){
				Toast.makeText(PayActivity.this, "请输入会员卡支付密码！", Toast.LENGTH_SHORT).show();
				return;
			}
			insertpaydto.setPayPass(membercardpassword.getText().toString()
					.trim());
		}
		customProgressDialog.setContent("正在加载支付信息......");
		customProgressDialog.show();
		VolleyUtil.sendObjectByPostToBean(CommonVariable.WXtopayURL, null,
				insertpaydto, PayDTO.class, new HttpBackListener<PayDTO>() {

					@Override
					public void onSuccess(PayDTO t) {
						customProgressDialog.dismiss();
						if (null != t && StringUtils.isNotBlank(t.getPaySign())) {
							ShopApplication.payflag = 1;
							PayReq request = new PayReq();
							request.appId = ShopApplication.APP_ID;
							request.partnerId = ShopApplication.MCH_ID;
							request.prepayId = t.getPackages();
							request.packageValue = "Sign=WXPay";
							request.nonceStr = t.getNonceStr();
							request.timeStamp = t.getTimeStamp();
							request.sign = t.getPaySign();
							ShopApplication.orderid = "" + orderid;
							ShopApplication.api.sendReq(request);
						} else {
							// 只用会员卡支付支付成功了
							Intent intent = new Intent(PayActivity.this,
									OrderDetailActivity.class);
							intent.putExtra("id", orderid + "");
							startActivity(intent);
							finish();
						}
					}

					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						Toast.makeText(PayActivity.this, "支付失败！" + failstring,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						Toast.makeText(PayActivity.this, "支付失败！",Toast.LENGTH_SHORT).show();
					}
				}, false, null);

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
