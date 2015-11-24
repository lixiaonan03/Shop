package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.MygiftItemAdapter;
import com.xyyy.shop.model.MemCardVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.MyListView;

/**
 * 我的礼品卡界面
 */
public class MygiftActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;

	private RelativeLayout gobuygiftLin;// 购买礼品卡
	private RelativeLayout gobindgiftLin;// 绑定礼品卡
	private MyListView listview;
	private List<MemCardVO> list = new ArrayList<MemCardVO>();
	private MygiftItemAdapter adapter;
	private CustomProgressDialog customProgressDialog;
	private LinearLayout nodata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mygift);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("我的礼品卡");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		customProgressDialog = new CustomProgressDialog(MygiftActivity.this,
				"正在加载......");
		initView();

	}

	private void initView() {
		// TODO 初始化控件
		nodata = (LinearLayout) findViewById(R.id.nodata);

		gobuygiftLin = (RelativeLayout) findViewById(R.id.gobuygiftLin);
		gobindgiftLin = (RelativeLayout) findViewById(R.id.gobindgiftLin);
		gobuygiftLin.setOnClickListener(viewclick);
		gobindgiftLin.setOnClickListener(viewclick);

		listview = (MyListView) findViewById(R.id.listview);
		listview.setEmptyView(nodata);

		adapter = new MygiftItemAdapter(MygiftActivity.this, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 点击进入我的礼品卡详情界面
				Intent intent = new Intent(MygiftActivity.this,
						GiftDetailActivity.class);
				int carttype = 0;
				int cardflag = 0;
				int cardid = 0;
				if (null != adapter.get_list().get(arg2).getEnnCardType()) {
					carttype = StrToNumber.strToint(adapter.get_list()
							.get(arg2).getEnnCardType().getCardType());
				}
				if (null != adapter.get_list().get(arg2).getEnnCard()) {
					cardflag = StrToNumber.strToint(adapter.get_list()
							.get(arg2).getEnnCard().getCardState());
					cardid = adapter.get_list().get(arg2).getEnnCard()
							.getCardId();
				}
				intent.putExtra("cardtype", carttype);
				intent.putExtra("cardflag", cardflag);
				intent.putExtra("cradid", cardid);
				startActivity(intent);
			}
		});
	}

	private void initdata() {
		// TODO 加载数据
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
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						if (null != t && t.size() > 0) {
							nodata.setVisibility(View.GONE);
							listview.setVisibility(View.VISIBLE);
							List<MemCardVO> listcurrent = new ArrayList<MemCardVO>();
							for (MemCardVO memCardVO : t) {
								if (null != memCardVO.getEnnCardType()
										.getCardType()
										&& memCardVO.getEnnCardType()
												.getCardType().equals("10")) {
									listcurrent.add(memCardVO);
								}
							}
							list = listcurrent;
							Collections.sort(list, new Comparator<MemCardVO>() {
								@Override
								public int compare(MemCardVO o1, MemCardVO o2) {
									String sales1 = o1.getEnnCard().getCardState();
									String sales2 = o2.getEnnCard().getCardState();
									return sales1.compareTo(sales2);
									// return result == 1 ? -1 : 1;
								}
							});

							Collections.sort(list, new Comparator<MemCardVO>() {
								@Override
								public int compare(MemCardVO o1, MemCardVO o2) {
									String sales1 = o1.getEnnCard().getIsFreeze();
									String sales2 = o2.getEnnCard().getIsFreeze();
									return sales1.compareTo(sales2);
								}
							});
							adapter.set_list(list);
							adapter.notifyDataSetChanged();
						} else {
							nodata.setVisibility(View.VISIBLE);
							listview.setVisibility(View.GONE);
						}
					}

					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						Toast.makeText(MygiftActivity.this, "加载数据失败！", 0)
								.show();
						nodata.setVisibility(View.VISIBLE);
						listview.setVisibility(View.GONE);
						customProgressDialog.dismiss();
					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(MygiftActivity.this, "加载数据失败！", 0)
								.show();
						nodata.setVisibility(View.VISIBLE);
						listview.setVisibility(View.GONE);
						customProgressDialog.dismiss();
					}
				}, false, null);
	}

	OnClickListener viewclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.gobuygiftLin:
				// 购买礼品卡
				Intent intent = new Intent(MygiftActivity.this,
						GiftListActivity.class);
				MygiftActivity.this.startActivity(intent);
				break;
			case R.id.gobindgiftLin:
				// 绑定礼品卡
				Intent intentgobind = new Intent(MygiftActivity.this,
						BindGiftActivity.class);
				MygiftActivity.this.startActivity(intentgobind);
				break;
			default:
				break;
			}
		}
	};

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
