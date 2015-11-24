package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.adapter.OrderPaydetailItemAdapter;
import com.xyyy.shop.model.EnnPayRecordVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.MyListView;
/**
 * 订单支付详情界面
 */
public class OrderPayDetailActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	
	private MyListView listview;
	private List<EnnPayRecordVO> list = new ArrayList<EnnPayRecordVO>();
	private OrderPaydetailItemAdapter adapter;
	private CustomProgressDialog customProgressDialog;
	private TextView yunfei;
	private TextView all;
	private String orderId;
	private String yunfeimoney;
	private String allmoney;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderpaydetail);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("支付明细");
		orderId=getIntent().getStringExtra("orderId");
		yunfeimoney=getIntent().getStringExtra("yunfeimoney");
		allmoney=getIntent().getStringExtra("allmoney");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		customProgressDialog=new CustomProgressDialog(OrderPayDetailActivity.this, "正在加载......");
		initView();
		initdata();
	}
	private void initView() {
		// TODO 初始化控件

		yunfei = (TextView) findViewById(R.id.yunfei);
		all = (TextView) findViewById(R.id.all);
		listview = (MyListView) findViewById(R.id.listview);
        
		yunfei.setText(yunfeimoney);
		all.setText(allmoney);
		
		adapter = new OrderPaydetailItemAdapter(OrderPayDetailActivity.this, list);
		listview.setAdapter(adapter);
	}

	private void initdata() {
		// TODO 加载数据
		customProgressDialog.show();
		Map<String, String> map=new HashMap<String, String>();
		map.put("orderId", orderId);
		VolleyUtil.sendObjectByPostToList(
				CommonVariable.GetOrderPayListURL, null, map, EnnPayRecordVO.class,
				new HttpBackListListener<EnnPayRecordVO>() {

					@Override
					public void onSuccess(List<EnnPayRecordVO> t) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						if (null != t && t.size() > 0) {
							listview.setVisibility(View.VISIBLE);
							list = t;
							adapter.set_list(list);
							adapter.notifyDataSetChanged();
						} else {
							listview.setVisibility(View.GONE);
						}
					}
					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						Toast.makeText(OrderPayDetailActivity.this, "加载数据失败！", 0).show();
						listview.setVisibility(View.GONE);
						customProgressDialog.dismiss();
					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(OrderPayDetailActivity.this, "加载数据失败！", 0).show();
						listview.setVisibility(View.GONE);
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
