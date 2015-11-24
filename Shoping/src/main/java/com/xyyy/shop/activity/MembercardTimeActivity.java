package com.xyyy.shop.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
/**
 * 修改我的会员卡的配送时间
 */
public class MembercardTimeActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	
	private CheckBox two;
	private CheckBox three;
	private CheckBox four;
	private CheckBox five;
	private int size;
	private int currentsize;//当前选中的个数
	
	private CheckBox one;
	private CheckBox six;
	private CheckBox seven;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_membercard_time);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("配送时间");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		size=getIntent().getIntExtra("size", 0);
		
		two=(CheckBox)findViewById(R.id.two);
		three=(CheckBox)findViewById(R.id.three);
		four=(CheckBox)findViewById(R.id.four);
		five=(CheckBox)findViewById(R.id.five);
		
		one=(CheckBox)findViewById(R.id.one);
		six=(CheckBox)findViewById(R.id.six);
		seven=(CheckBox)findViewById(R.id.seven);
		
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				gosubmit();
			}
		});
	}

	private void gosubmit() {
		//TODO 确定按钮
		currentsize=0;
		StringBuilder strs=new StringBuilder();
		if(one.isChecked()){
			currentsize++;
			strs.append("1,");
		}
		if(two.isChecked()){
			currentsize++;
			strs.append("2,");
		}
		if(three.isChecked()){
			currentsize++;
			strs.append("3,");
		}
		if(four.isChecked()){
			currentsize++;
			strs.append("4,");
		}
		if(five.isChecked()){
			currentsize++;
			strs.append("5,");
		}
		
		if(six.isChecked()){
			currentsize++;
			strs.append("6,");
		}
		if(seven.isChecked()){
			currentsize++;
			strs.append("7,");
		}
		if(currentsize!=size){
			AlertDialog.Builder builder = new AlertDialog.Builder(MembercardTimeActivity.this);
	        builder.setTitle("提示");
	        builder.setMessage("选择的配送时间不符合要求,您只能选择"+size+"天。");
	        builder.setNegativeButton("确定", null);
	        if (!isFinishing()) {
	            builder.create().show();
	        }
		}else{
			setResult(01, getIntent().putExtra("timestrs", strs.toString()));
			finish();
		}
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
