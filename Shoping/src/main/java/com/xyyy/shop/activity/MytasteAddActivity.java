package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
import com.xyyy.shop.adapter.MytasteAddItemAdapter;
import com.xyyy.shop.model.EnnDishesVO2;
import com.xyyy.shop.model.EnnMemberFlavor;
import com.xyyy.shop.model.EnnMemberFlavorDTO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.SideBar;
import com.xyyy.shop.view.SideBar.OnTouchingLetterChangedListener;

/**
 * 添加我的口味
 */
public class MytasteAddActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;
	private ListView listview;
	private TextView dialog;
	private SideBar sidebar;
	private MytasteAddItemAdapter adapter;
	private List<EnnDishesVO2> list = new ArrayList<EnnDishesVO2>();
	private RelativeLayout yesdata;
	private RelativeLayout nodata;
	private CustomProgressDialog customProgressDialog;
	private Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mytaste_add);

		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("新侬菜品");
		top_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		customProgressDialog=new CustomProgressDialog(MytasteAddActivity.this, "正在加载......");
		initview();
		initdata();
	}

	private void initview() {
		// TODO 初始化控件
		yesdata=(RelativeLayout)findViewById(R.id.yesdata);
		nodata=(RelativeLayout)findViewById(R.id.nodata);
		
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(viewclick);
		listview = (ListView) findViewById(R.id.listview);
		dialog = (TextView) findViewById(R.id.dialog);
		sidebar = (SideBar) findViewById(R.id.sidebar);
		sidebar.setTextView(dialog);
		
		adapter = new MytasteAddItemAdapter(MytasteAddActivity.this, list);
		listview.setAdapter(adapter);

		// 设置右侧触摸监听
		sidebar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					listview.setSelection(position);
				}
			}
		});
	}
    
	private void initdata() {
		int userid = 0;
		if(ShopApplication.isLogin){
			if(ShopApplication.loginflag==1){
			     userid = ShopApplication.userid;
			}
			if(ShopApplication.loginflag==2){
				userid = ShopApplication.useridother;
			}
		}
		// TODO 加载数据
		customProgressDialog.show();
		VolleyUtil.sendStringRequestByGetToList(CommonVariable.GetAllDishesURL+userid+"/all", null, null, EnnDishesVO2.class, new HttpBackListListener<EnnDishesVO2>(){

			@Override
			public void onSuccess(List<EnnDishesVO2> t) {
				customProgressDialog.dismiss();
				list=t;
				if(t!=null&&t.size()>0){
					// 根据a-z进行排序源数据
					Collections.sort(list, new Comparator<EnnDishesVO2>() {

						@Override
						public int compare(EnnDishesVO2 arg0, EnnDishesVO2 arg1) {
							if (arg0.getSortLetters().equals("@")
									|| arg1.getSortLetters().equals("#")) {
								return -1;
							} else if (arg0.getSortLetters().equals("#")
									|| arg1.getSortLetters().equals("@")) {
								return 1;
							} else {
								return arg0.getSortLetters().compareTo(
										arg1.getSortLetters());
							}
						}

					});
					yesdata.setVisibility(View.VISIBLE);
					nodata.setVisibility(View.GONE);
					adapter.set_list(list);
					adapter.notifyDataSetChanged();
				}else{
					yesdata.setVisibility(View.VISIBLE);
					nodata.setVisibility(View.GONE);
				}
			}

			@Override
			public void onFail(String failstring) {
				customProgressDialog.dismiss();
				yesdata.setVisibility(View.VISIBLE);
				nodata.setVisibility(View.GONE);
				Toast.makeText(MytasteAddActivity.this, "加载失败！",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(VolleyError error) {
				customProgressDialog.dismiss();
				yesdata.setVisibility(View.VISIBLE);
				nodata.setVisibility(View.GONE);
				Toast.makeText(MytasteAddActivity.this, "加载失败！",Toast.LENGTH_SHORT).show();
			}}, false, null);
		
		
	
	}

	OnClickListener viewclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
		
			case R.id.submit:
				// 添加
				goadd();
				break;
			
			default:
				break;
			}
		}
	};
	/**
	 * 添加我的口味
	 */
	private void goadd() {
		
		EnnMemberFlavorDTO dto=new EnnMemberFlavorDTO();
		List<EnnMemberFlavor> ennMemberFlavors=new ArrayList<EnnMemberFlavor>();
		for (EnnDishesVO2 one : adapter.get_list()) {
			    if(StringUtils.isBlank(one.getFlavorType())){
			    	
			    }else{
			    	EnnMemberFlavor oneMemberFlavor=new EnnMemberFlavor();
					oneMemberFlavor.setDishesId(one.getEnnDishes().getDishesId());
					oneMemberFlavor.setDishesCode(one.getEnnDishes().getDishesCode());
				    oneMemberFlavor.setFlavorType(one.getFlavorType());
					int userid = 0;
					if(ShopApplication.isLogin){
						if(ShopApplication.loginflag==1){
						     userid = ShopApplication.userid;
						}
						if(ShopApplication.loginflag==2){
							userid = ShopApplication.useridother;
						}
					}
					oneMemberFlavor.setMembId(userid);
					ennMemberFlavors.add(oneMemberFlavor);
			    }
			}
		if(ennMemberFlavors.size()==0){
			Toast.makeText(MytasteAddActivity.this, "请选择相关口味",Toast.LENGTH_SHORT).show();
			return;
		}
		dto.setEnnMemberFlavors(ennMemberFlavors);
		customProgressDialog.show();
		VolleyUtil.sendObjectByPostToString(CommonVariable.AddMemberTasteURL,null, dto, new HttpBackBaseListener() {
			
			@Override
			public void onSuccess(String string) {
				customProgressDialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(MytasteAddActivity.this);
		        builder.setTitle("提示");
		        builder.setMessage("添加成功");
		        builder.setNegativeButton("确定", null);
		        if (!isFinishing()) {
		            builder.create().show();
		        }
			}
			
			@Override
			public void onFail(String failstring) {
				customProgressDialog.dismiss();
				Toast.makeText(MytasteAddActivity.this, "添加失败！",Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onError(VolleyError error) {
				customProgressDialog.dismiss();
				Toast.makeText(MytasteAddActivity.this, "添加失败！",Toast.LENGTH_SHORT).show();
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
