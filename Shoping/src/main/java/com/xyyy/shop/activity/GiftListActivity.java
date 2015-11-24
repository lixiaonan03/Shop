package com.xyyy.shop.activity;

import java.math.BigDecimal;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.adapter.SearchItemAdapter;
import com.xyyy.shop.model.GoodItemVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 点击购买礼品卡进入的购买礼品卡的商品列表
 */
public class GiftListActivity extends BaseActivity {
	
	
	private ListView listview; 
	
	private List<GoodItemVO> list=new ArrayList<GoodItemVO>();//礼品卡数据
	private SearchItemAdapter adapter;
	private CustomProgressDialog customProgressDialog;
	private RelativeLayout nocard;

	private ImageView top_back;

	private TextView top_text;

	private TextView sort_default;
	private RelativeLayout sort_price;
	private TextView sort_price_text;
	private ImageView sort_price_img;
	private TextView sort_salesvolume;
	private TextView sort_goodevaluate;
	
	private int PRICE_UP=1;//价格升序排列
	private int PRICE_DOWN=2;//价格降序排列
	
	private View linedefault;
	private View lineprice;
	private View linesales;
	private View lineevaluate;
	
	private int sort_price_current=0;//当前价格的排序规则
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_giftlist);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("新侬礼品卡");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		customProgressDialog=new CustomProgressDialog(GiftListActivity.this, "正在加载......");
		initView();
		initdata();
	}
	
	/**
	 * 初始化界面组件
	 */
	private void initView() {
		//排序的
		sort_default=(TextView)findViewById(R.id.sort_default);
		sort_price=(RelativeLayout)findViewById(R.id.sort_price);
		sort_price_text=(TextView)findViewById(R.id.sort_price_text);
		sort_price_img=(ImageView)findViewById(R.id.sort_price_img);
		sort_salesvolume=(TextView)findViewById(R.id.sort_salesvolume);
		sort_goodevaluate=(TextView)findViewById(R.id.sort_goodevaluate);
		
		linedefault=(View)findViewById(R.id.linedefault);
		lineprice=(View)findViewById(R.id.lineprice);
		linesales=(View)findViewById(R.id.linesales);
		lineevaluate=(View)findViewById(R.id.lineevaluate);
		
		nocard=(RelativeLayout)findViewById(R.id.nocard);
	    
		
sort_default.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//默认的被点击 选中默认的排序
				sort_price_current=0;
				sort_price_img.setImageResource(R.drawable.sort_price);
				
				sort_default.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
				sort_default.setBackgroundResource(R.color.order_background_checked);
				linedefault.setBackgroundResource(R.color.order_linecolor_checked);
				
				sort_price_text.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_price.setBackgroundResource(R.color.order_background_nochecked);
				lineprice.setBackgroundResource(R.color.order_linecolor_nochecked);
				
				sort_salesvolume.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_salesvolume.setBackgroundResource(R.color.order_background_nochecked);
				linesales.setBackgroundResource(R.color.order_linecolor_nochecked);
				
				sort_goodevaluate.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_goodevaluate.setBackgroundResource(R.color.order_background_nochecked);
				lineevaluate.setBackgroundResource(R.color.order_linecolor_nochecked);
				//TODO 默认排序数据变化  
				Collections.sort(list,new Comparator<GoodItemVO>(){

					@Override
					public int compare(GoodItemVO paramT1, GoodItemVO paramT2) {
						if(paramT1.getEnnGoods().getGoodsName()==null||paramT2.getEnnGoods().getGoodsName()==null){
							return -1;
						}
						return paramT1.getEnnGoods().getGoodsName().compareTo(paramT2.getEnnGoods().getGoodsName());
					}
					
				});
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
			}
		});
		sort_price.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//价格排序
				sort_price_text.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
				sort_price.setBackgroundResource(R.color.order_background_checked);
				lineprice.setBackgroundResource(R.color.order_linecolor_checked);
				
				
				sort_default.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_default.setBackgroundResource(R.color.order_background_nochecked);
				linedefault.setBackgroundResource(R.color.order_linecolor_nochecked);
				
				sort_salesvolume.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_salesvolume.setBackgroundResource(R.color.order_background_nochecked);
				linesales.setBackgroundResource(R.color.order_linecolor_nochecked);
				
				sort_goodevaluate.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_goodevaluate.setBackgroundResource(R.color.order_background_nochecked);
				lineevaluate.setBackgroundResource(R.color.order_linecolor_nochecked);
				if(sort_price_current==PRICE_UP){
					//当前是升序的 图片箭头和数据变成降序的
					sort_price_img.setImageResource(R.drawable.sort_price_down);
					sort_price_current=PRICE_DOWN;
					Collections.sort(list, new Comparator<GoodItemVO>(){

						@Override
						public int compare(GoodItemVO paramT1, GoodItemVO paramT2) {
							if(paramT1.getEnnGoods().getMallPrice()==null||paramT2.getEnnGoods().getMallPrice()==null){
								return -1;
							}
							return paramT2.getEnnGoods().getMallPrice().compareTo(paramT1.getEnnGoods().getMallPrice());
						}
						
					});
				}else{
					sort_price_current=PRICE_UP;
					sort_price_img.setImageResource(R.drawable.sort_price_up);
					Collections.sort(list, new Comparator<GoodItemVO>(){

						@Override
						public int compare(GoodItemVO paramT1, GoodItemVO paramT2) {
							if(paramT1.getEnnGoods().getMallPrice()==null||paramT2.getEnnGoods().getMallPrice()==null){
								return 1;
							}
							return paramT1.getEnnGoods().getMallPrice().compareTo(paramT2.getEnnGoods().getMallPrice());
						}
						
					});
				}
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
			}
		});
		sort_salesvolume.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO 销量排序
				sort_price_current=0;
				sort_price_img.setImageResource(R.drawable.sort_price);
				
				sort_salesvolume.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
				sort_salesvolume.setBackgroundResource(R.color.order_background_checked);
				linesales.setBackgroundResource(R.color.order_linecolor_checked);
				
				sort_default.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_default.setBackgroundResource(R.color.order_background_nochecked);
				linedefault.setBackgroundResource(R.color.order_linecolor_nochecked);
				
				sort_price_text.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_price.setBackgroundResource(R.color.order_background_nochecked);
				lineprice.setBackgroundResource(R.color.order_linecolor_nochecked);
				
				sort_goodevaluate.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_goodevaluate.setBackgroundResource(R.color.order_background_nochecked);
				lineevaluate.setBackgroundResource(R.color.order_linecolor_nochecked);
				
				Collections.sort(list, new Comparator<GoodItemVO>(){

					@Override
					public int compare(GoodItemVO paramT1, GoodItemVO paramT2) {
						if(paramT1.getEnnGoods().getGoodsSales()==null||paramT2.getEnnGoods().getGoodsSales()==null){
							return -1;
						}
						return paramT2.getEnnGoods().getGoodsSales().compareTo(paramT1.getEnnGoods().getGoodsSales());
					}
					
				});
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
			}
		});
		sort_goodevaluate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO 好评排序
				sort_price_current=0;
				sort_price_img.setImageResource(R.drawable.sort_price);
				
				sort_goodevaluate.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
				sort_goodevaluate.setBackgroundResource(R.color.order_background_checked);
				lineevaluate.setBackgroundResource(R.color.order_linecolor_checked);
				
				sort_default.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_default.setBackgroundResource(R.color.order_background_nochecked);
				linedefault.setBackgroundResource(R.color.order_linecolor_nochecked);
				
				sort_price_text.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_price.setBackgroundResource(R.color.order_background_nochecked);
				lineprice.setBackgroundResource(R.color.order_linecolor_nochecked);
				
				sort_salesvolume.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				sort_salesvolume.setBackgroundResource(R.color.order_background_nochecked);
				linesales.setBackgroundResource(R.color.order_linecolor_nochecked);
				
				Collections.sort(list, new Comparator<GoodItemVO>(){

					@Override
					public int compare(GoodItemVO paramT1, GoodItemVO paramT2) {
						if(paramT1.getEnnGoods().getGoodsEvals()==null||paramT2.getEnnGoods().getGoodsEvals()==null){
							return -1;
						}
						return paramT2.getEnnGoods().getGoodsEvals().compareTo(paramT1.getEnnGoods().getGoodsEvals());
					}
					
				});
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
			}
		});
		
		listview=(ListView)findViewById(R.id.listview);
		
		
		
		adapter=new SearchItemAdapter(GiftListActivity.this, list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 条目点击的事件 进入会员卡详情的界面
				long id=list.get(arg2).getEnnGoods().getGoodsId();
				String name=list.get(arg2).getEnnGoods().getGoodsName();
				String imgurl=list.get(arg2).getImgUrl();
				BigDecimal goodprice = list.get(arg2).getEnnGoods().getMallPrice();
				Intent intent = new Intent();
				intent.setClass(GiftListActivity.this, ProductdeatilActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("name", name);
				intent.putExtra("imgurl", imgurl);
				intent.putExtra("goodprice", goodprice);
				startActivity(intent);
			}
		});
	}
   
	private void initdata() {
		
		customProgressDialog.show();
		VolleyUtil.sendStringRequestByGetToList(CommonVariable.GetMembercardBytypeURL+"10", null, null, GoodItemVO.class, new HttpBackListListener<GoodItemVO>() {

			@Override
			public void onSuccess(List<GoodItemVO> t) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				if (t!=null&&t.size()>0) {
					nocard.setVisibility(View.GONE);
					listview.setVisibility(View.VISIBLE);
					
					list=t;
					Collections.sort(list,new Comparator<GoodItemVO>(){

						@Override
						public int compare(GoodItemVO paramT1, GoodItemVO paramT2) {
							if(paramT1.getEnnGoods().getGoodsName()==null||paramT2.getEnnGoods().getGoodsName()==null){
								return -1;
							}
							return paramT1.getEnnGoods().getGoodsName().compareTo(paramT2.getEnnGoods().getGoodsName());
						}
						
					});
					adapter.set_list(list);
					adapter.notifyDataSetChanged();
				}else{
					nocard.setVisibility(View.VISIBLE);
					listview.setVisibility(View.GONE);
				}
			}

			@Override
			public void onFail(String failstring) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				nocard.setVisibility(View.VISIBLE);
				listview.setVisibility(View.GONE);
			}

			@Override
			public void onError(VolleyError error) {
				// TODO Auto-generated method stub
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
