package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.xyyy.shop.adapter.MydeliverynoticeItemAdapter;
import com.xyyy.shop.model.DeliveNoticeDTO2;
import com.xyyy.shop.model.DeliveNoticeItemDTO;
import com.xyyy.shop.model.EnnMembVege;
import com.xyyy.shop.model.IsDeliveNoticeDTO;
import com.xyyy.shop.model.Membfavor;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.DateUtil;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 我的配送预告
 */
public class MydeliverynoticeActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;
	private TextView deliverytime;// 预告时间
	private Button submit;// 提交按钮
	private RelativeLayout buysomemore;// 再买点
	private ExpandableListView expand_listview;
	private List<DeliveNoticeItemDTO> list = new ArrayList<DeliveNoticeItemDTO>();
	private MydeliverynoticeItemAdapter adapter;
	private CustomProgressDialog customProgressDialog;

	private int noticeid;// 接口返回的回来的noticeid
	private int userid;

	private LinearLayout nodata;

	private TextView msgTop;

	private View line1;
	private LinearLayout yesdata;

	private int submitflag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mydeliverynotice);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("配送预告");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		customProgressDialog = new CustomProgressDialog(
				MydeliverynoticeActivity.this, "正在加载......");
		initview();
		initdata();
	}

	private void initview() {
		// TODO 初始化控件
		deliverytime = (TextView) findViewById(R.id.deliverytime);
		submit = (Button) findViewById(R.id.submit);
		buysomemore = (RelativeLayout) findViewById(R.id.buysomemore);
		yesdata = (LinearLayout) findViewById(R.id.yesdata);
		nodata = (LinearLayout) findViewById(R.id.nodata);
		msgTop = (TextView) findViewById(R.id.msgTop);
		line1 = findViewById(R.id.line1);

		expand_listview = (ExpandableListView) findViewById(R.id.expand_listview);
		// 初始化可扩展的listview
		expand_listview.setGroupIndicator(null);// 去掉自带的图标
		adapter = new MydeliverynoticeItemAdapter(this, list);
		expand_listview.setAdapter(adapter);

		int groupCount = expand_listview.getCount();
		for (int i = 0; i < groupCount; i++) {
			expand_listview.expandGroup(i);
		}

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				if (submitflag == 1) {
					// 购买会员卡
					Intent intent = new Intent(MydeliverynoticeActivity.this,
							MemberListActivity.class);
					startActivity(intent);
					finish();
				} else if (submitflag == 2) {
					Intent intent = new Intent(MydeliverynoticeActivity.this,
							MainActivity.class);
					intent.putExtra("flag", 3);
					startActivity(intent);
					finish();
				} else {
					// TODO 更改完配送预告的确定按钮
					List<EnnMembVege> updatelist = new ArrayList<EnnMembVege>();
					for (DeliveNoticeItemDTO deliveNoticeItemDTO : adapter
							.get_list()) {
						if (null != deliveNoticeItemDTO.getMembfavors()
								&& deliveNoticeItemDTO.getMembfavors().size() > 0) {
							for (Membfavor membfavor : deliveNoticeItemDTO
									.getMembfavors()) {
								EnnMembVege one = new EnnMembVege();
								one.setNoticeId(noticeid);
								if (null != membfavor.getEnnDishes()
										&& null != membfavor.getEnnDishes()
												.getDishesId()) {
									one.setDishesId(membfavor.getEnnDishes()
											.getDishesId());
								} else {
									one.setDishesId(0);
								}
								if (null != membfavor.getFlavorType())
									one.setFlavorType(membfavor.getFlavorType());
								// 人员id
								one.setMembId(userid);
								updatelist.add(one);
							}
						}
					}
					customProgressDialog.show();
					VolleyUtil.sendObjectByPostToString(
							CommonVariable.UpdateDelivenNoticeURL, null,
							updatelist, new HttpBackBaseListener() {

								@Override
								public void onSuccess(String string) {
									customProgressDialog.dismiss();
									Toast.makeText(
											MydeliverynoticeActivity.this,
											"提交成功！", 0).show();
								}

								@Override
								public void onFail(String failstring) {
									customProgressDialog.dismiss();
									Toast.makeText(
											MydeliverynoticeActivity.this,
											"提交失败！", 0).show();
								}

								@Override
								public void onError(VolleyError error) {
									customProgressDialog.dismiss();
									Toast.makeText(
											MydeliverynoticeActivity.this,
											"提交失败！", 0).show();
								}
							}, false, null);
				}
			}

		});
		buysomemore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				// TODO 再买点功能
				Intent intent = new Intent(MydeliverynoticeActivity.this,
						ClassifyActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initdata() {
		// TODO 加载数据
		customProgressDialog.show();

		userid = 0;
		if (ShopApplication.isLogin) {
			if (ShopApplication.loginflag == 1) {
				userid = ShopApplication.userid;
			}
			if (ShopApplication.loginflag == 2) {
				userid = ShopApplication.useridother;
			}
		}
		VolleyUtil.sendStringRequestByGetToBean(
				CommonVariable.GetDelivenNoticeURL + userid, null, null,
				DeliveNoticeDTO2.class,
				new HttpBackListener<DeliveNoticeDTO2>() {

					@Override
					public void onSuccess(DeliveNoticeDTO2 t) {

						if (null != t) {
							initIs(t);
						} else {
							customProgressDialog.dismiss();
							nodata.setVisibility(View.VISIBLE);
							yesdata.setVisibility(View.GONE);
							submit.setVisibility(View.GONE);
						}
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						nodata.setVisibility(View.VISIBLE);
						yesdata.setVisibility(View.GONE);
						Toast.makeText(MydeliverynoticeActivity.this, "加载失败！",
								0).show();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						nodata.setVisibility(View.VISIBLE);
						yesdata.setVisibility(View.GONE);
						Toast.makeText(MydeliverynoticeActivity.this, "加载失败！",
								0).show();

					}
				}, false, null);

	}

	/**
	 * 判断当前用户是否能进行选菜
	 */
	private void initIs(final DeliveNoticeDTO2 noticedto) {
		userid = 0;
		if (ShopApplication.isLogin) {
			if (ShopApplication.loginflag == 1) {
				userid = ShopApplication.userid;
			}
			if (ShopApplication.loginflag == 2) {
				userid = ShopApplication.useridother;
			}
		}
		VolleyUtil.sendStringRequestByGetToBean(
				CommonVariable.IsDelivenNoticeURL + userid, null, null,
				IsDeliveNoticeDTO.class,
				new HttpBackListener<IsDeliveNoticeDTO>() {

					@Override
					public void onSuccess(IsDeliveNoticeDTO t) {
						customProgressDialog.dismiss();
						if (null != noticedto.getNoticeId()) {
							noticeid = StrToNumber.strToint(noticedto
									.getNoticeId());
							if (null != noticedto.getStartDate()
									&& null != noticedto.getEndDate()) {
								deliverytime.setText(DateUtil.dateToStr(
										noticedto.getStartDate(),
										"yyyy-MM-dd")
										+ " — "
										+ DateUtil.dateToStr(
												noticedto.getEndDate(),
												"yyyy-MM-dd"));
							}
							if (null != noticedto.getDeliveNoticeItems()) {
								list = noticedto.getDeliveNoticeItems();
								adapter.set_list(list);
								adapter.notifyDataSetChanged();
								int groupCount = expand_listview.getCount();
								for (int i = 0; i < groupCount; i++) {
									expand_listview.expandGroup(i);
								}
							}
						
						} else {
							nodata.setVisibility(View.VISIBLE);
							yesdata.setVisibility(View.GONE);
							submit.setVisibility(View.GONE);
						}
						
						if (t.getIsEnable()&&(StrToNumber.strToint(t.getState())==3)) {
							if (true != noticedto.isFrist()) {
								submit.setText("变更");
							}
						} else {
							// 当前用户不能选菜
							switch (StrToNumber.strToint(t.getState())) {
							case 0:
								submitflag = 1;
								submit.setText("购买会员卡");
								break;
							case 1:
								submitflag = 2;
								submit.setText("请启用新会员卡");
							case 2:
								submitflag = 2;
								submit.setText("卡已暂定，请启用");
								break;

							default:
								break;
							}

						}
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
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
