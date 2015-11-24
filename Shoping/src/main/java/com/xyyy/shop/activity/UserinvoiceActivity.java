package com.xyyy.shop.activity;

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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.UserInvoiceItemAdapter;
import com.xyyy.shop.model.EnnMemberInvoice;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 用户发票信息
 */
public class UserinvoiceActivity extends BaseActivity {

	private ImageView top_back;
	private Button submit;
	private ImageView top_add;
	private ListView listview;
	private List<EnnMemberInvoice> addresss = new ArrayList<EnnMemberInvoice>();
	private UserInvoiceItemAdapter adapter;
	private CustomProgressDialog customProgressDialog;

	private int flag = 0;// 从其他地方跳转过来的标记 2 表示从购物车的订单详请跳转进来的

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinvoice);
		customProgressDialog = new CustomProgressDialog(
				UserinvoiceActivity.this, "正在加载......");
		top_back = (ImageView) findViewById(R.id.top_back);
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		flag = getIntent().getIntExtra("flag", 0);
		top_add = (ImageView) findViewById(R.id.top_add);
		top_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(UserinvoiceActivity.this,
						AdduserinvoiceActivity.class);
				startActivity(intent);
			}
		});
		listview = (ListView) findViewById(R.id.listview);
		adapter = new UserInvoiceItemAdapter(UserinvoiceActivity.this, addresss);
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
						// 从购物车订单详情的地方跳转过来
						EnnMemberInvoice seletestr = adapter.get_list().get(
								adapter.flagposition);
						getIntent().putExtra("text",
								seletestr.getInvoiceTitle());
						setResult(02, getIntent());
						finish();
					} else if (flag == 0) {
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
		// TODO 人员id
		int userid=0;
		if (ShopApplication.isLogin) {
			if (ShopApplication.loginflag == 1) {
				userid = ShopApplication.userid;
			}
			if (ShopApplication.loginflag == 2) {
				userid = ShopApplication.useridother;
			}
		}
		VolleyUtil.sendStringRequestByGetToList(
				CommonVariable.CartGetInvociesURL + userid, null, null,
				EnnMemberInvoice.class,
				new HttpBackListListener<EnnMemberInvoice>() {

					@Override
					public void onSuccess(List<EnnMemberInvoice> t) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						addresss = t;
						adapter.set_list(addresss);
						adapter.notifyDataSetChanged();
					}

					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						Toast.makeText(UserinvoiceActivity.this,"获取发票信息失败！", 0).show();
					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						Toast.makeText(UserinvoiceActivity.this,"获取发票信息失败！", 0).show();
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
