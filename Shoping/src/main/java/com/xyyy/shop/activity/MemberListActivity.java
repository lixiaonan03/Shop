package com.xyyy.shop.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
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
 * 点击购买会员卡进入的购买会员卡的商品列表
 */
public class MemberListActivity extends BaseActivity {
	
	
	private ListView listview; 
	
	private List<GoodItemVO> list=new ArrayList<GoodItemVO>();//全部订单的数据
	private SearchItemAdapter adapter;
	private int flag=1;//那个选项卡被选中 1年卡 2半年卡 3季卡 4月卡 5体验卡
	private CustomProgressDialog customProgressDialog;
	private RelativeLayout nocard;

	private ImageView top_back;

	private TextView top_text;

	private TextView card_year;

	private TextView card_halfyear;

	private TextView card_season;

	private TextView card_month;

	private TextView card_experience;

	private View lineyear;

	private View linehalfyear;

	private View lineseason;

	private View linemonth;

	private View lineexperience;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memberlist);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("新侬会员卡");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		customProgressDialog=new CustomProgressDialog(MemberListActivity.this, "正在加载......");
		initView();
		initdata();
	}
	
	/**
	 * 初始化界面组件
	 */
	private void initView() {
		card_year=(TextView)findViewById(R.id.card_year);
		card_halfyear=(TextView)findViewById(R.id.card_halfyear);
		card_season=(TextView)findViewById(R.id.card_season);
		card_month=(TextView)findViewById(R.id.card_month);
		card_experience=(TextView)findViewById(R.id.card_experience);
		
		nocard=(RelativeLayout)findViewById(R.id.nocard);
	    
		card_year.setOnClickListener(viewclick);
		card_halfyear.setOnClickListener(viewclick);
		card_season.setOnClickListener(viewclick);
		card_month.setOnClickListener(viewclick);
		card_experience.setOnClickListener(viewclick);
		
		lineyear=(View)findViewById(R.id.lineyear);
		linehalfyear=(View)findViewById(R.id.linehalfyear);
		lineseason=(View)findViewById(R.id.lineseason);
		linemonth=(View)findViewById(R.id.linemonth);
		lineexperience=(View)findViewById(R.id.lineexperience);
	
		listview=(ListView)findViewById(R.id.listview);
		
		adapter=new SearchItemAdapter(MemberListActivity.this, list);
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
				intent.setClass(MemberListActivity.this, ProductdeatilActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("name", name);
				intent.putExtra("imgurl", imgurl);
				intent.putExtra("goodprice", goodprice);
				startActivity(intent);
			}
		});
	}
    /**
     * 顶部tab页改变之
     */
	private void changetab(){
		card_year.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
		card_halfyear.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
		card_season.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
		card_month.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
		card_experience.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
		
		card_year.setBackgroundResource(R.color.order_background_nochecked);
		card_halfyear.setBackgroundResource(R.color.order_background_nochecked);
		card_season.setBackgroundResource(R.color.order_background_nochecked);
		card_month.setBackgroundResource(R.color.order_background_nochecked);
		card_experience.setBackgroundResource(R.color.order_background_nochecked);
		
		lineyear.setBackgroundResource(R.color.order_linecolor_nochecked);
		linehalfyear.setBackgroundResource(R.color.order_linecolor_nochecked);
		lineseason.setBackgroundResource(R.color.order_linecolor_nochecked);
		linemonth.setBackgroundResource(R.color.order_linecolor_nochecked);
		lineexperience.setBackgroundResource(R.color.order_linecolor_nochecked);
		
		switch (flag) {
		case 1:
			card_year.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
			card_year.setBackgroundResource(R.color.order_background_checked);
			lineyear.setBackgroundResource(R.color.order_linecolor_checked);
			break;
		case 2:
			card_halfyear.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
			card_halfyear.setBackgroundResource(R.color.order_background_checked);
			linehalfyear.setBackgroundResource(R.color.order_linecolor_checked);
			break;
		case 3:
			card_season.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
			card_season.setBackgroundResource(R.color.order_background_checked);
			lineseason.setBackgroundResource(R.color.order_linecolor_checked);
			break;
		case 4:
			card_month.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
			card_month.setBackgroundResource(R.color.order_background_checked);
			linemonth.setBackgroundResource(R.color.order_linecolor_checked);
			break;
		case 5:
			card_experience.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
			card_experience.setBackgroundResource(R.color.order_background_checked);
			lineexperience.setBackgroundResource(R.color.order_linecolor_checked);
			break;

		default:
			break;
		}
	}
	OnClickListener viewclick=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.card_year:
				flag=1;
				changetab();
				initdata();
				break;
			case R.id.card_halfyear:
				flag=2;
				changetab();
				initdata();
				
				break;
			case R.id.card_season:
				flag=3;
				changetab();
				initdata();
				break;
			case R.id.card_month:
				flag=4;
				changetab();
				initdata();
				break;
			case R.id.card_experience:
				flag=5;
				changetab();
				initdata();
				break;

			default:
				break;
			}
		}
	};
	private void initdata() {
		/*if (!customProgressDialog.isShowing()) {
			customProgressDialog.show();
		}*/
		customProgressDialog.show();
		VolleyUtil.sendStringRequestByGetToList(CommonVariable.GetMembercardBytypeURL+"0"+flag, null, null, GoodItemVO.class, new HttpBackListListener<GoodItemVO>() {

			@Override
			public void onSuccess(List<GoodItemVO> t) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				if (t!=null&&t.size()>0) {
					nocard.setVisibility(View.GONE);
					listview.setVisibility(View.VISIBLE);
					
					list=t;
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
