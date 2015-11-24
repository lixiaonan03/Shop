package com.xyyy.shop.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.UserAddressItemAdapter;
import com.xyyy.shop.model.EnnMemberReceipt;
import com.xyyy.shop.model.EnnMemberReceiptDTO;
import com.xyyy.shop.model.EnnSysArea;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 用户收货地址
 */
public class UseradressActivity extends BaseActivity {

	private ImageView top_back;
	private Button submit;
	private ImageView top_add;
	private ListView listview;
	private List<EnnMemberReceipt> addresss = new ArrayList<EnnMemberReceipt>();
	private UserAddressItemAdapter adapter;
	private CustomProgressDialog customProgressDialog;

	private int flag = 0;// 从其他地方跳转过来的标记 2表示 从我的会员卡详情进来的 3 表示从购物车的订单详请跳转进来的 4
							// 礼品卡详情进来的 5 新建发票过来的
	private List<EnnSysArea> canuserlist;
	private TextView prompt;

	private LinearLayout nodata;
	private RelativeLayout yesdata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_useradress);
		nodata = (LinearLayout) findViewById(R.id.nodata);
		yesdata = (RelativeLayout) findViewById(R.id.yesdata);
		customProgressDialog = new CustomProgressDialog(
				UseradressActivity.this, "正在加载......");
		top_back = (ImageView) findViewById(R.id.top_back);
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		flag = getIntent().getIntExtra("flag", 0);
		prompt = (TextView) findViewById(R.id.prompt);
		if (flag == 2 || flag == 4) {
			canuserlist = (List<EnnSysArea>) getIntent().getSerializableExtra(
					"addresslist");
			prompt.setVisibility(View.GONE);
			StringBuilder prompttextstr = new StringBuilder("该卡支持的配送范围为：");
			for (EnnSysArea i : canuserlist) {
				prompttextstr.append(i.getAreaAllName() + "、");
			}
			prompt.setText(prompttextstr.substring(0,
					prompttextstr.length() - 1));
		} else {
			prompt.setVisibility(View.GONE);
		}
		top_add = (ImageView) findViewById(R.id.top_add);
		top_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(UseradressActivity.this,
						AdduseradressActivity.class);
				if(null!=canuserlist&&canuserlist.size()>0){
					intent.putExtra("addresslist",
							(Serializable)canuserlist);
				}
				startActivity(intent);
			}
		});
		listview = (ListView) findViewById(R.id.listview);
		adapter = new UserAddressItemAdapter(UseradressActivity.this, addresss);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (adapter.flagposition == -1) {
					addresss.get(arg2).setIsDefault("1");
				} else {
					addresss.get(adapter.flagposition).setIsDefault("0");
					addresss.get(arg2).setIsDefault("1");
				}
				// TODO 单击条目选择默认
				adapter.notifyDataSetChanged();
			}
		});
		;
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				if (adapter.flagposition != -1) {
					if (flag == 2) {
						// 从我的会员卡详解跳过来的
						EnnMemberReceipt seletestr = adapter.get_list().get(
								adapter.flagposition);
						getIntent().putExtra("province",
								seletestr.getProvince());
						getIntent().putExtra("city", seletestr.getCity());
						getIntent().putExtra("country", seletestr.getCountry());
						getIntent().putExtra("receAddress",
								seletestr.getReceAddress());
						getIntent().putExtra("recename",
								seletestr.getReceName());
						getIntent().putExtra("recephone",
								seletestr.getRecePhone());
						setResult(02, getIntent());
						finish();
					} else if (flag == 3) {
						// 从购物车订单详情的地方跳转过来
						EnnMemberReceipt seletestr = adapter.get_list().get(
								adapter.flagposition);
						getIntent()
								.putExtra("addressid", seletestr.getReceId());
						getIntent().putExtra("province",
								seletestr.getProvince());
						getIntent().putExtra("city", seletestr.getCity());
						getIntent().putExtra("country", seletestr.getCountry());
						getIntent().putExtra("receAddress",
								seletestr.getReceAddress());

						getIntent().putExtra("recename",
								seletestr.getReceName());
						getIntent().putExtra("recephone",
								seletestr.getRecePhone());
						setResult(03, getIntent());
						finish();

					} else if (flag == 4) {
						// 从购物车订单详情的地方跳转过来
						EnnMemberReceipt seletestr = adapter.get_list().get(
								adapter.flagposition);
						getIntent()
								.putExtra("addressid", seletestr.getReceId());
						getIntent().putExtra("province",
								seletestr.getProvince());
						getIntent().putExtra("city", seletestr.getCity());
						getIntent().putExtra("country", seletestr.getCountry());
						getIntent().putExtra("receAddress",
								seletestr.getReceAddress());

						getIntent().putExtra("recename",
								seletestr.getReceName());
						getIntent().putExtra("recephone",
								seletestr.getRecePhone());
						setResult(04, getIntent());
						finish();
					} else if (flag == 5) {
						// 新建发票地址
						EnnMemberReceipt seletestr = adapter.get_list().get(
								adapter.flagposition);
						getIntent()
								.putExtra("addressid", seletestr.getReceId());
						getIntent().putExtra("province",
								seletestr.getProvince());
						getIntent().putExtra("city", seletestr.getCity());
						getIntent().putExtra("country", seletestr.getCountry());
						getIntent().putExtra("receAddress",
								seletestr.getReceAddress());

						getIntent().putExtra("recename",
								seletestr.getReceName());
						getIntent().putExtra("recephone",
								seletestr.getRecePhone());
						setResult(05, getIntent());
						finish();
					} else if (flag == 0) {
						customProgressDialog.setContent("正在提交......");
						customProgressDialog.show();
						EnnMemberReceiptDTO dto = new EnnMemberReceiptDTO();
						List<EnnMemberReceipt> ennMemberReceipts = new ArrayList<EnnMemberReceipt>();
						ennMemberReceipts.addAll(addresss);
						dto.setEnnMemberReceipts(ennMemberReceipts);

						VolleyUtil.sendObjectByPostToString(
								CommonVariable.UpdateMyAddressListURL, null,
								dto, new HttpBackBaseListener() {

									@Override
									public void onSuccess(String string) {
										customProgressDialog.dismiss();
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
				}
			}
		});
	}

		

	/**
	 * 加载数据
	 */
	private void initdata() {
		customProgressDialog.show();
		adapter.flagposition = -1;
		int userid = 0;
		if (ShopApplication.isLogin) {
			if (ShopApplication.loginflag == 1) {
				userid = ShopApplication.userid;
			}
			if (ShopApplication.loginflag == 2) {
				userid = ShopApplication.useridother;
			}
		}
		VolleyUtil.sendStringRequestByGetToList(CommonVariable.GetMyAddressURL
				+ userid, null, null, EnnMemberReceipt.class,
				new HttpBackListListener<EnnMemberReceipt>() {

					@Override
					public void onSuccess(List<EnnMemberReceipt> t) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						addresss = new ArrayList<EnnMemberReceipt>();
						if (flag == 2 || flag == 4) {
							if(null!=canuserlist&&canuserlist.size()>0){
								for (EnnMemberReceipt ennMemberReceipt : t) {
									for (EnnSysArea can : canuserlist) {
										if (ennMemberReceipt.getCountry().equals(
												can.getAreaCode())) {
											addresss.add(ennMemberReceipt);
											break;
										}
									}
								}
							}else{
								addresss = t;
							}
						} else {
							addresss = t;
						}
						
						if(addresss.size()>0){
							yesdata.setVisibility(View.VISIBLE);
							nodata.setVisibility(View.GONE);
							adapter.set_list(addresss);
							adapter.notifyDataSetChanged();
						}else{
							yesdata.setVisibility(View.GONE);
							nodata.setVisibility(View.VISIBLE);
						}
						
					}

					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						Toast.makeText(UseradressActivity.this, "加载数据失败！",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						Toast.makeText(UseradressActivity.this, "加载数据失败！",
								Toast.LENGTH_SHORT).show();
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
