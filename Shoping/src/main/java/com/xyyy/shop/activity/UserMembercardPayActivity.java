package com.xyyy.shop.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.UserMembercardPayItemAdapter;
import com.xyyy.shop.model.GoodItemVO;
import com.xyyy.shop.model.MemCardVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 用户可支付的会员卡列表
 */
public class UserMembercardPayActivity extends BaseActivity {

	private ImageView top_back;
	private Button submit;
	private ListView listview;
	private List<MemCardVO> membercardpaylist = new ArrayList<MemCardVO>();
	private UserMembercardPayItemAdapter adapter;
	private CustomProgressDialog customProgressDialog;

	private int flag = 0;// 1 从确认订单界面跳转的
	private TextView top_text;
	private List<MemCardVO> membercardpaylistgo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usermembercardpaylist);
		customProgressDialog = new CustomProgressDialog(
				UserMembercardPayActivity.this, "正在加载......");
		top_back = (ImageView) findViewById(R.id.top_back);
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		flag = getIntent().getIntExtra("flag", 0);
		membercardpaylistgo =(List<MemCardVO>)getIntent().getSerializableExtra("list");
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("会员卡支付");
		listview = (ListView) findViewById(R.id.listview);
		adapter = new UserMembercardPayItemAdapter(
				UserMembercardPayActivity.this, membercardpaylist);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (membercardpaylist.get(arg2).getFlag() == 0) {
					membercardpaylist.get(arg2).setFlag(1);
				} else {
					membercardpaylist.get(arg2).setFlag(0);
				}
				adapter.notifyDataSetChanged();
			}
		});
		;
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				List<MemCardVO> membercardpaylistgo = new ArrayList<MemCardVO>();
                for (MemCardVO i : membercardpaylist) {
					if(i.getFlag()==1){
						membercardpaylistgo.add(i);
					}
				}
                if(null!=membercardpaylistgo){
                	getIntent().putExtra("list",(Serializable)membercardpaylistgo);
                	setResult(2, getIntent());
                	finish();
                }else{
                	/*Toast.makeText(UserMembercardPayActivity.this,
							"请选择要支付的会员卡！", 0).show();*/
                }
			}
		});
	}

	/**
	 * 加载数据
	 */
	private void initdata() {
		customProgressDialog.show();
		int userid = 0;
		if (ShopApplication.isLogin) {
			if (ShopApplication.loginflag == 1) {
				userid = ShopApplication.userid;
			}
			if (ShopApplication.loginflag == 2) {
				userid = ShopApplication.useridother;
			}
		}
		VolleyUtil.sendStringRequestByGetToList(CommonVariable.GetMyMemberURL
				+ userid, null, null, MemCardVO.class,
				new HttpBackListListener<MemCardVO>() {

					@Override
					public void onSuccess(List<MemCardVO> t) {
						customProgressDialog.dismiss();
						membercardpaylist.clear();
						if (null != t && t.size() > 0) {
							for (MemCardVO memCardVO : t) {
								if (!(null != memCardVO.getEnnCardType()
										.getCardType() && memCardVO
										.getEnnCardType().getCardType()
										.equals("10"))) {
									// 不是礼品卡
									if (!memCardVO.getEnnCard().getIsFreeze()
											.equals("1")) {
										// 没有冻结
										if(null!=memCardVO.getEnnCard()
												.getCardRemain()){
											if (memCardVO.getEnnCard()
													.getCardRemain()
													.compareTo(new BigDecimal(0)) == 1) {
												// 余额大于0
												membercardpaylist.add(memCardVO);
											}
										}
									}
								}
							}
							if(null!=membercardpaylistgo&&membercardpaylistgo.size()>0){
								for (MemCardVO memCardVO2 : membercardpaylist) {
									for (MemCardVO memCardVO : membercardpaylistgo) {
										if(memCardVO2.getEnnCard().getCardId().equals(memCardVO.getEnnCard().getCardId())){
											memCardVO2.setFlag(1);
											break;
										}
									}
								}
							}
							if (membercardpaylist.size() > 0) {
								// TODO 排序数据变化
								Collections.sort(membercardpaylist, new Comparator<MemCardVO>() {

									@Override
									public int compare(MemCardVO paramT1, MemCardVO paramT2) {
										return paramT2
												.getEnnCard()
												.getCardRemain()
												.compareTo(paramT1.getEnnCard()
														.getCardRemain());
									}

								});
								adapter.set_list(membercardpaylist);
								adapter.notifyDataSetChanged();
							} else {
								listview.setVisibility(View.GONE);
							}

						} else {
							listview.setVisibility(View.GONE);
						}
					}

					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						Toast.makeText(UserMembercardPayActivity.this,
								"加载数据失败！", Toast.LENGTH_SHORT).show();
						listview.setVisibility(View.GONE);
						customProgressDialog.dismiss();
					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(UserMembercardPayActivity.this,
								"加载数据失败！", Toast.LENGTH_SHORT).show();
						listview.setVisibility(View.GONE);
						customProgressDialog.dismiss();
					}
				}, false, null);
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
