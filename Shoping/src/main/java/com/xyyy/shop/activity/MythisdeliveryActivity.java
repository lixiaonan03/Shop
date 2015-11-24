package com.xyyy.shop.activity;

import java.util.ArrayList;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.MythisdeliveryItemAdapter;
import com.xyyy.shop.model.OrderDeliveItemDTO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.MyListView;
/**
 * 会员卡配送清单的界面
 */
public class MythisdeliveryActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private MyListView listthis;//本次配送
	private MyListView listhistory;//历史配送
	private MythisdeliveryItemAdapter adapter1;//本次配送的适配器
	private MythisdeliveryItemAdapter adapter2;//历史配送的适配器
	private List<OrderDeliveItemDTO> list1=new ArrayList<OrderDeliveItemDTO>();
	private List<OrderDeliveItemDTO> list2=new ArrayList<OrderDeliveItemDTO>();
	private CustomProgressDialog customProgressDialog;
	private ScrollView yesdata;
	private RelativeLayout nodata;
	private LinearLayout nodatalin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mythisdelivery);
		customProgressDialog=new CustomProgressDialog(MythisdeliveryActivity.this, "正在加载......");
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("会员卡配送清单");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		yesdata=(ScrollView)findViewById(R.id.yesdata);
		nodata=(RelativeLayout)findViewById(R.id.nodata);
		nodatalin=(LinearLayout)findViewById(R.id.nodatalin);
		nodatalin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// 购买会员卡
				Intent intent = new Intent(MythisdeliveryActivity.this,
						MemberListActivity.class);
				startActivity(intent);
			}
		});
		
		listthis=(MyListView)findViewById(R.id.listthis);
		adapter1=new MythisdeliveryItemAdapter(MythisdeliveryActivity.this, list1);
		listthis.setAdapter(adapter1);
		listthis.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MythisdeliveryActivity.this, DeliverydetatilActivity.class);
				intent.putExtra("id", list1.get(paramInt).getEnnOrder().getOrderId());
				startActivity(intent);
			}
		});
		listhistory=(MyListView)findViewById(R.id.listhistory);
		adapter2=new MythisdeliveryItemAdapter(MythisdeliveryActivity.this, list2);
		listhistory.setAdapter(adapter2);
		listhistory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MythisdeliveryActivity.this, DeliverydetatilActivity.class);
				intent.putExtra("id", list2.get(paramInt).getEnnOrder().getOrderId());
				startActivity(intent);
			}
		});
		initdata();
	}
	private void initdata() {
		// TODO 加载数据
		customProgressDialog.show();
		int userid = 0;
		if(ShopApplication.isLogin){
			if(ShopApplication.loginflag==1){
			    userid = ShopApplication.userid;
			}
			if(ShopApplication.loginflag==2){
				userid = ShopApplication.useridother;
			}
		}
		VolleyUtil.sendStringRequestByGetToList(CommonVariable.GetDeliveRecodeURL+userid, null,null, OrderDeliveItemDTO.class, new HttpBackListListener<OrderDeliveItemDTO>() {

			@Override
			public void onSuccess(List<OrderDeliveItemDTO> t) {
				customProgressDialog.dismiss();
				if(null!=t&&t.size()>0){
					yesdata.setVisibility(View.VISIBLE);
					nodata.setVisibility(View.GONE);
					for (OrderDeliveItemDTO orderDeliveItemDTO : t) {
						if(null!=orderDeliveItemDTO.getStatus()&&orderDeliveItemDTO.getStatus().equals("01")){
							// 01 本次配送
							list1.add(orderDeliveItemDTO);
						}
						if(null!=orderDeliveItemDTO.getStatus()&&orderDeliveItemDTO.getStatus().equals("02")){
							// 02 历史配送
							list2.add(orderDeliveItemDTO);
						}
					}
					adapter1.set_list(list1);
					adapter1.notifyDataSetChanged();
					adapter2.set_list(list2);
					adapter2.notifyDataSetChanged();
				}else{
					yesdata.setVisibility(View.GONE);
					nodata.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onFail(String failstring) {
				yesdata.setVisibility(View.GONE);
				nodata.setVisibility(View.VISIBLE);
				customProgressDialog.dismiss();
				Toast.makeText(MythisdeliveryActivity.this, "加载失败！", 0).show();
			}

			@Override
			public void onError(VolleyError error) {
				yesdata.setVisibility(View.GONE);
				nodata.setVisibility(View.VISIBLE);
				customProgressDialog.dismiss();
				Toast.makeText(MythisdeliveryActivity.this, "加载失败！", 0).show();
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
