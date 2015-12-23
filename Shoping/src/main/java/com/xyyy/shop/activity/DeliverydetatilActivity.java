package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.adapter.DeliverydetailItemAdapter;
import com.xyyy.shop.model.DeliveDetailItemDTO;
import com.xyyy.shop.model.DeliveListDetailDTO;
import com.xyyy.shop.model.Deliverydetail;
import com.xyyy.shop.model.OrderDeliveItemDTO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.DateUtil;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.MyListView;
/**
 * 配送清单详情的界面
 */
public class DeliverydetatilActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private MyListView listview;
	private TextView count;
	private RelativeLayout logistics;//物流信息
	private DeliverydetailItemAdapter adapter;
	private DeliverydetailItemAdapter buymemberadapter;
	private List<DeliveDetailItemDTO> list=new ArrayList<DeliveDetailItemDTO>();
	private List<DeliveDetailItemDTO> buymemberlist=new ArrayList<DeliveDetailItemDTO>();
	
	private int orderid;
	private CustomProgressDialog customProgressDialog;
	private LinearLayout buymember_lin;
	private MyListView buymemberlistview;
	private TextView logisticstext;
	private TextView logisticstime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deliverydetatil);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("清单详情");
		orderid=getIntent().getIntExtra("id",0);
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		customProgressDialog=new CustomProgressDialog(DeliverydetatilActivity.this, "正在加载......");
		initview();
		initdata();
	}
	private void initview() {
		// TODO 初始化控件
		listview=(MyListView)findViewById(R.id.listview);
		//再买点随会员卡配送的
		buymember_lin=(LinearLayout)findViewById(R.id.buymember_lin);
		buymemberlistview=(MyListView)findViewById(R.id.buymemberlistview);
		
		count=(TextView)findViewById(R.id.count);
		//物流信息
		logistics=(RelativeLayout)findViewById(R.id.logistics);
		logistics.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(DeliverydetatilActivity.this, LogisticsActivity.class);
				intent.putExtra("url",CommonVariable.OrderGetLogisticsURL+orderid+"&device=android");
				startActivity(intent);
			}
		});
		logisticstext=(TextView)findViewById(R.id.logisticstext);
		logisticstime=(TextView)findViewById(R.id.logisticstime);
		
		adapter=new DeliverydetailItemAdapter(DeliverydetatilActivity.this, list);
		buymemberadapter=new DeliverydetailItemAdapter(DeliverydetatilActivity.this, buymemberlist);
		
		listview.setAdapter(adapter);
		buymemberlistview.setAdapter(buymemberadapter);
		
		listview.setOnItemClickListener(itemcilck);
		buymemberlistview.setOnItemClickListener(itemcilck);
		
	}
	OnItemClickListener  itemcilck=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO 条目点击进入菜谱
			DeliveDetailItemDTO one = (DeliveDetailItemDTO)arg0.getAdapter().getItem(arg2);
			// 跳转菜谱页
			Intent intent = new Intent();
			intent.setClass(DeliverydetatilActivity.this, CookbookActivity.class);
			StringBuilder url=new StringBuilder(CommonVariable.GetCookbookURL);
			if(null!=one){
				if(null!=one.getEnnDishes()){
					if(null!=one.getEnnDishes().getMenuId()){
						url.append(one.getEnnDishes().getMenuId());
					}
					if(null!=one.getEnnDishes().getDishesName()){
						url.append("&dishesName="+one.getEnnDishes().getDishesName());
					}
				}
				if(null!=one.getEnnOrderPick()&&null!=one.getEnnOrderPick().getTraceCode()){
					url.append("&pickCode="+one.getEnnOrderPick().getTraceCode());
					url.append("&device=android");
				}
			}
			intent.putExtra("url", url.toString());
			if(null!=one&&null!=one.getEnnDishes()&&null!=one.getEnnDishes().getDishesName()){
				intent.putExtra("cookname", one.getEnnDishes().getDishesName());
			}else{
				intent.putExtra("cookname", "菜谱");
			}
			
			startActivity(intent);
		}
	};
	private void initdata() {
		// TODO 加载数据
		adapter.set_list(list);
		adapter.notifyDataSetChanged();
		customProgressDialog.show();
		VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetDeliveDetailURL+orderid, null, null, DeliveListDetailDTO.class, new HttpBackListener<DeliveListDetailDTO>() {

			@Override
			public void onSuccess(DeliveListDetailDTO t) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				int countsize=0;
				if(null!=t.getDeliveDetailItemDTOs()){
					if(null!=t.getDeliveDetailItemDTOs()&&t.getDeliveDetailItemDTOs().size()>0){
						list=t.getDeliveDetailItemDTOs();
						adapter.set_list(list);
						adapter.notifyDataSetChanged();
						countsize=t.getDeliveDetailItemDTOs().size();
					}
				}
				if(null!=t.getBuyTooDeliveDetailItemDTOs()){
					buymember_lin.setVisibility(View.VISIBLE);
					buymemberlist=t.getBuyTooDeliveDetailItemDTOs();
					buymemberadapter.set_list(buymemberlist);
					buymemberadapter.notifyDataSetChanged();
					countsize+=t.getBuyTooDeliveDetailItemDTOs().size();
				}else{
					buymember_lin.setVisibility(View.GONE);
				}
				count.setText(Html.fromHtml("合计" + "<font color=\"#45A63A\">"+countsize+"</font>"+"件商品"));
				if(null!=t.getEnnPostRec()){
					if(null!=t.getEnnPostRec().getPostDesc()){
						logisticstext.setText(t.getEnnPostRec().getPostDesc());
					}
					if(null!=t.getEnnPostRec().getPostTime()){
						logisticstime.setText(DateUtil.getDateTime(t.getEnnPostRec().getPostTime()));
					}
				}
			}

			@Override
			public void onFail(String failstring) {
				customProgressDialog.dismiss();
				Toast.makeText(DeliverydetatilActivity.this, "加载失败！",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(VolleyError error) {
				customProgressDialog.dismiss();
				Toast.makeText(DeliverydetatilActivity.this, "加载失败！",Toast.LENGTH_SHORT).show();
			}
		}, false, null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}
}
