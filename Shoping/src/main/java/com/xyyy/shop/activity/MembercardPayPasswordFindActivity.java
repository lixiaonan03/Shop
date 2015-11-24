package com.xyyy.shop.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.RegularExpression;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 会员卡支付密码找回
 */
public class MembercardPayPasswordFindActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private EditText edittextphone;
	private EditText edittextcode;
	private Button getcodebutton;
	private Button submit;
	private EditText edittextnew,edittextnewagain;
	private int yzm = 1;//1 验证码超时或无效  2 有效 
	private CustomProgressDialog customProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_membercardpaypasswordfind);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("找回支付密码");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		customProgressDialog=new CustomProgressDialog(MembercardPayPasswordFindActivity.this, "正在重置......");
		edittextphone = (EditText) findViewById(R.id.edittextphone);
		edittextcode = (EditText) findViewById(R.id.edittextcode);
		edittextnew = (EditText) findViewById(R.id.edittextnew);
		edittextnewagain = (EditText) findViewById(R.id.edittextnewagain);

		getcodebutton = (Button) findViewById(R.id.getcodebutton);
		getcodebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				String phone = edittextphone.getText().toString().trim();
				String check = RegularExpression.checkRegularExpression(phone,
						RegularExpression.MOBILE_PHONE, "请输入正确的手机号码！");
				
				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(MembercardPayPasswordFindActivity.this, check, 0).show();
					return;
				}
				getcodebutton.setBackgroundResource(R.drawable.getcode_onbutton);
				getcodebutton.setClickable(false);
				VolleyUtil.sendStringRequestByGetToString(CommonVariable.GetCodeURL+phone+"/06", null, null, new HttpBackBaseListener() {
					
					@Override
					public void onSuccess(String string) {
						edittextphone.setEnabled(false);
						yzm = 2;
						new MyCount(120000,1000).start();
					}
					
					@Override
					public void onFail(String failstring) {
						Toast.makeText(MembercardPayPasswordFindActivity.this, "获取验证码失败！", 0).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}
					
					@Override
					public void onError(VolleyError error) {
						Toast.makeText(MembercardPayPasswordFindActivity.this, "获取验证码失败！", 0).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}
				}, false, null);
			}
		});
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				//提交按钮
				String phone = edittextphone.getText().toString().trim();
				String code = edittextcode.getText().toString().trim();
				String password = edittextnew.getText().toString().trim();
				String passwordagain = edittextnewagain.getText().toString().trim();
				if(StringUtils.isBlank(phone)||StringUtils.isBlank(code)||StringUtils.isBlank(password)||StringUtils.isBlank(passwordagain)){
					Toast.makeText(MembercardPayPasswordFindActivity.this, "请输入相关信息！", 0).show();
					return;
				}
				if(yzm ==1){
					Toast.makeText(MembercardPayPasswordFindActivity.this, "验证码已失效！", 0).show();
					return;
				}
				String check = RegularExpression.checkRegularExpression(password,
						RegularExpression.paypassword, "请输入6位数字！");
				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(MembercardPayPasswordFindActivity.this, check, 0).show();
					return;
				}
				if(!password.equals(passwordagain)){
					Toast.makeText(MembercardPayPasswordFindActivity.this, "两次输入的密码不一致！", 0).show();
					return;
				}
				customProgressDialog.show();
				VolleyUtil.sendStringRequestByGetToString(CommonVariable.MembercardPayPasswordFindURL+phone+"/"+code+"/"+password, null, null, new HttpBackBaseListener() {
					
					@Override
					public void onSuccess(String string) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						edittextphone.setText("");
						edittextcode.setText("");
						edittextnew.setText("");
						edittextnewagain.setText("");
						getcodebutton.setBackgroundResource(R.drawable.login_button);
			        	getcodebutton.setText("获取验证码");
			        	yzm = 1;
			        	Toast.makeText(MembercardPayPasswordFindActivity.this, "重置成功！", 0).show();
			        	finish();
					}
					
					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(MembercardPayPasswordFindActivity.this, "重置失败！"+failstring, 0).show();
					}
					
					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(MembercardPayPasswordFindActivity.this,  "重置失败！", 0).show();
					}
				}, false, null);
			}
		});
	}
	class MyCount extends CountDownTimer {  
		  
        public MyCount(long millisInFuture, long countDownInterval) {  
            super(millisInFuture, countDownInterval);  
        }  
  
        @Override  
        public void onFinish() {  
        	//倒计时完要做的事情
        	getcodebutton.setClickable(true);
        	edittextphone.setEnabled(true);
        	
        	
        	getcodebutton.setBackgroundResource(R.drawable.login_button);
        	getcodebutton.setText("获取验证码");
        	yzm = 1;
        }  
  
        @Override  
        public void onTick(long millisUntilFinished) {  
        	getcodebutton.setText(millisUntilFinished / 1000+"秒");  
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
