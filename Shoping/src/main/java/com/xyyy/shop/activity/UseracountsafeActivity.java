package com.xyyy.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
/**
 * 用户账户安全
 */
public class UseracountsafeActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private RelativeLayout changephone;
	private RelativeLayout changepassword;
	private RelativeLayout changepaypassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_useraccontsafe);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("账户安全");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		changephone=(RelativeLayout)findViewById(R.id.changephone);
		changepassword=(RelativeLayout)findViewById(R.id.changepassword);
		changepaypassword=(RelativeLayout)findViewById(R.id.changepaypassword);
		changephone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(UseracountsafeActivity.this, UserchangephoneActivity.class);
				startActivity(intent);
			}
		});
		changepassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(UseracountsafeActivity.this, UserchangepasswordActivity.class);
				startActivity(intent);
			}
		});
		changepaypassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(UseracountsafeActivity.this, MembercardPayPasswordchangeActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onStop() {
		super.onStop();
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
