package com.xyyy.shop.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.toolUtil.StringUtils;

/**
 * 购物车订单里添加用户备注信息的
 */
public class AdduserremarkActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	private EditText remark_content;
    
	private int flag=0;//从其他地方跳转过来的标记2 表示从购物车的订单详请跳转进来的 3 礼品卡详情进入的
	private String remark;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adduserremark);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("备注");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		flag=getIntent().getIntExtra("flag", 0);
		remark=getIntent().getStringExtra("remark");
		remark_content = (EditText) findViewById(R.id.remark_content);
		if(StringUtils.isNotBlank(remark)){
        	remark_content.setText(remark);
        	remark_content.setSelection(remark.length());
        } 
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				String remarkstr=remark_content.getText().toString().trim();
				if(StringUtils.isBlank(remarkstr)){
					Toast.makeText(AdduserremarkActivity.this,"请输入备注信息！", 0).show();
					return;
				}
				if(remarkstr.length()>50){
					 Toast.makeText(AdduserremarkActivity.this, "备注信息过长！", Toast.LENGTH_SHORT).show();
		             return;
				}
				if(flag==2){
					getIntent().putExtra("remarkstr", remarkstr);
					setResult(02, getIntent());
					finish();
				}
				if(flag==3){
					getIntent().putExtra("remarkstr", remarkstr);
					setResult(03, getIntent());
					finish();
				}
			    
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		  /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
         */
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
