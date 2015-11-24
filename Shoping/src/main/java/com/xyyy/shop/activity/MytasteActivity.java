package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
import com.xyyy.shop.adapter.MytasteItemAdapter;
import com.xyyy.shop.model.EnnDishesVO;
import com.xyyy.shop.model.EnnMemberFlavor;
import com.xyyy.shop.model.EnnMemberFlavorDTO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.SideBar;
import com.xyyy.shop.view.SideBar.OnTouchingLetterChangedListener;

/**
 * 我的口味
 */
public class MytasteActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private TextView yestaste;
	private TextView notaste;
	private View lineyes;
	private View lineno;
	private CheckBox checkBoxall;
	private ImageView del;
	private ListView listview;
	private TextView dialog;
	private SideBar sidebar;
	private MytasteItemAdapter adapter;
	private List<EnnDishesVO> list = new ArrayList<EnnDishesVO>();
	private RelativeLayout yesdata;
	private RelativeLayout nodata;
	private LinearLayout nodata_lin;
	private TextView nodata_text;
	private CustomProgressDialog customProgressDialog;
    private int flag=1;//1表示喜欢被选中 2表示忌口被选中
	private ImageView top_add;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mytaste);

		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("我的口味");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		customProgressDialog=new CustomProgressDialog(MytasteActivity.this, "正在加载......");
		initview();
	}
    
	private void initview() {
		yestaste = (TextView) findViewById(R.id.yestaste);
		notaste = (TextView) findViewById(R.id.notaste);
		yestaste.setOnClickListener(viewclick);
		notaste.setOnClickListener(viewclick);

		lineyes = (View) findViewById(R.id.lineyes);
		lineno = (View) findViewById(R.id.lineno);
        
		
		yesdata=(RelativeLayout)findViewById(R.id.yesdata);
		nodata=(RelativeLayout)findViewById(R.id.nodata);
		nodata_lin=(LinearLayout)findViewById(R.id.nodata_lin);
		nodata_lin.setOnClickListener(viewclick);
		nodata_text=(TextView)findViewById(R.id.nodata_text);
		
		
		checkBoxall = (CheckBox) findViewById(R.id.checkBoxall);
		top_add = (ImageView) findViewById(R.id.top_add);
		del = (ImageView) findViewById(R.id.del);
		top_add.setOnClickListener(viewclick);
		del.setOnClickListener(viewclick);
		checkBoxall.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO radiobuttonall选择改变
				if(arg1){
					//全选
					for (EnnDishesVO item : list) {
						item.setFlag(1);
					}
				}else{
					//全不选
					for (EnnDishesVO item : list) {
						item.setFlag(0);
					}
				}
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
			}
		});
		
		
		listview = (ListView) findViewById(R.id.listview);
		dialog = (TextView) findViewById(R.id.dialog);
		sidebar = (SideBar) findViewById(R.id.sidebar);
		sidebar.setTextView(dialog);
		
		adapter = new MytasteItemAdapter(MytasteActivity.this, list);
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
		// TODO 加载数据
		int userid = 0;
		if (ShopApplication.isLogin) {
			if(ShopApplication.loginflag==1){
				userid=ShopApplication.userid;
			}
			if(ShopApplication.loginflag==2){
				userid=ShopApplication.useridother;
			}
		}
		//String url=CommonVariable.GetMemberTasteURL+"1/";
		String url=CommonVariable.GetMemberTasteURL+userid+"/";
		if(flag==1){
			url=url+"01";
		}
		if(flag==2){
			url=url+"06";
		}
		customProgressDialog.show();
		VolleyUtil.sendStringRequestByGetToList(url, null, null, EnnDishesVO.class, new HttpBackListListener<EnnDishesVO>(){

			@Override
			public void onSuccess(List<EnnDishesVO> t) {
				customProgressDialog.dismiss();
				if(t!=null&&t.size()>0){
					list=t;
					// 根据a-z进行排序源数据
					Collections.sort(list, new Comparator<EnnDishesVO>() {

						@Override
						public int compare(EnnDishesVO arg0, EnnDishesVO arg1) {
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
					yesdata.setVisibility(View.GONE);
					nodata.setVisibility(View.VISIBLE);
					if(flag==1){
						nodata_text.setText("点击添加我喜欢的菜品");
					}
					if(flag==2){
						nodata_text.setText("点击添加我忌口的菜品");
					}
				}
			}

			@Override
			public void onFail(String failstring) {
				customProgressDialog.dismiss();
				yesdata.setVisibility(View.GONE);
				nodata.setVisibility(View.VISIBLE);
				if(flag==1){
					nodata_text.setText("点击添加我喜欢的菜品");
				}
				if(flag==2){
					nodata_text.setText("点击添加我忌口的菜品");
				}
				Toast.makeText(MytasteActivity.this, "加载失败！", 0).show();
			}

			@Override
			public void onError(VolleyError error) {
				customProgressDialog.dismiss();
				yesdata.setVisibility(View.GONE);
				nodata.setVisibility(View.VISIBLE);
				if(flag==1){
					nodata_text.setText("点击添加我喜欢的菜品");
				}
				if(flag==2){
					nodata_text.setText("点击添加我忌口的菜品");
				}
				Toast.makeText(MytasteActivity.this, "加载失败！", 0).show();
			}}, false, null);
	}

	OnClickListener viewclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.yestaste:
				// 喜欢
				yestaste.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
				notaste.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				yestaste.setBackgroundResource(R.color.order_background_checked);
				notaste.setBackgroundResource(R.color.order_background_nochecked);
				lineyes.setBackgroundResource(R.color.order_linecolor_checked);
				lineno.setBackgroundResource(R.color.order_linecolor_nochecked);
			    flag=1;
			    adapter.setFlag(flag);
			    initdata();
				break;
			case R.id.notaste:
				// 忌口
				notaste.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
				yestaste.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
				notaste.setBackgroundResource(R.color.order_background_checked);
				yestaste.setBackgroundResource(R.color.order_background_nochecked);
				lineno.setBackgroundResource(R.color.order_linecolor_checked);
				lineyes.setBackgroundResource(R.color.order_linecolor_nochecked);
				 flag=2;
				 adapter.setFlag(flag);
				 initdata();
				break;
			case R.id.top_add:
				// 添加
                Intent intent=new Intent(MytasteActivity.this,MytasteAddActivity.class);
                startActivity(intent);
				break;
			case R.id.nodata_lin:
				// 添加
				Intent intentadd=new Intent(MytasteActivity.this,MytasteAddActivity.class);
				startActivity(intentadd);
				break;
			case R.id.del:
				// 删除
				 godelete();
				
				
				break;

			default:
				break;
			}
		}
	};
	/**
	 * 删除我的口味
	 */
	private void godelete() {
		final EnnMemberFlavorDTO dto=new EnnMemberFlavorDTO();
		List<EnnMemberFlavor> ennMemberFlavors=new ArrayList<EnnMemberFlavor>();
		for (EnnDishesVO one : list) {
			if(one.getFlag()==1){
				EnnMemberFlavor oneMemberFlavor=new EnnMemberFlavor();
				oneMemberFlavor.setDishesId(one.getEnnDishes().getDishesId());
				oneMemberFlavor.setDishesCode(one.getEnnDishes().getDishesCode());
				if(flag==1){
					oneMemberFlavor.setFlavorType("01");
				}
				if(flag==2){
					oneMemberFlavor.setFlavorType("06");
				}
				//TODO 设置进去人员id 目前这先用 1代替
				int userid = 0;
				if (ShopApplication.isLogin) {
					if(ShopApplication.loginflag==1){
						userid=ShopApplication.userid;
					}
					if(ShopApplication.loginflag==2){
						userid=ShopApplication.useridother;
					}
				}
				oneMemberFlavor.setMembId(userid);
				ennMemberFlavors.add(oneMemberFlavor);
			}
		}
		if(ennMemberFlavors.size()==0){
			Toast.makeText(MytasteActivity.this, "请选择要删除的菜品！", 0).show();
			return;
		}
		dto.setEnnMemberFlavors(ennMemberFlavors);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(MytasteActivity.this);
        builder.setTitle("提示");
        builder.setMessage("您确定要删除这些喜欢么？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               
               dialog.dismiss();//取消dialog，或dismiss掉
               customProgressDialog.show();
               VolleyUtil.sendObjectByPostToString(CommonVariable.DeleteMemberTasteURL,null, dto, new HttpBackBaseListener() {
       			
       			@Override
       			public void onSuccess(String string) {
       				customProgressDialog.dismiss();
       				initdata();
       			}
       			
       			@Override
       			public void onFail(String failstring) {
       				customProgressDialog.dismiss();
       				Toast.makeText(MytasteActivity.this, "删除失败！",Toast.LENGTH_SHORT).show();
       			}
       			
       			@Override
       			public void onError(VolleyError error) {
       				customProgressDialog.dismiss();
       				Toast.makeText(MytasteActivity.this, "删除失败！",Toast.LENGTH_SHORT).show();
       			}
       		}, false, null);
            }
        });
        if (!isFinishing()) {
            builder.create().show();
        }
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
