package com.xyyy.shop.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.RegularExpression;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 会员卡支付密码设置
 */
public class MembercardPayPasswordSetActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	private EditText edittextnew,edittextnewagain;
	private CustomProgressDialog customProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_membercardpaypasswordset);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("设置支付密码");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		customProgressDialog=new CustomProgressDialog(MembercardPayPasswordSetActivity.this, "正在设置......");
		edittextnew = (EditText) findViewById(R.id.edittextnew);
		edittextnewagain = (EditText) findViewById(R.id.edittextnewagain);

		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				//提交按钮
				String password = edittextnew.getText().toString().trim();
				String passwordagain = edittextnewagain.getText().toString().trim();
				if(StringUtils.isBlank(password)||StringUtils.isBlank(passwordagain)){
					Toast.makeText(MembercardPayPasswordSetActivity.this, "请输入相关信息！",Toast.LENGTH_SHORT).show();
					return;
				}
				String check = RegularExpression.checkRegularExpression(password,
						RegularExpression.paypassword, "请输入6位数字！");
				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(MembercardPayPasswordSetActivity.this, check,Toast.LENGTH_SHORT).show();
					return;
				}
				if(!password.equals(passwordagain)){
					Toast.makeText(MembercardPayPasswordSetActivity.this, "两次输入的密码不一致！",Toast.LENGTH_SHORT).show();
					return;
				}
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
				VolleyUtil.sendStringRequestByGetToString(CommonVariable.MembercardPayPasswordSetURL+userid+"/"+password, null, null, new HttpBackBaseListener() {
					
					@Override
					public void onSuccess(String string) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						edittextnew.setText("");
						edittextnewagain.setText("");
						AlertDialog.Builder builder = new AlertDialog.Builder(MembercardPayPasswordSetActivity.this);
				        builder.setTitle("提示");
				        builder.setMessage("亲，您的支付密码已设置成功。");
				        builder.setNegativeButton("确定", null);
				        if (!isFinishing()) {
				            builder.create().show();
				        }
					}
					
					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(MembercardPayPasswordSetActivity.this, "设置失败！"+failstring,Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(MembercardPayPasswordSetActivity.this,  "设置失败！",Toast.LENGTH_SHORT).show();
					}
				}, false, null);
			}
		});
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
