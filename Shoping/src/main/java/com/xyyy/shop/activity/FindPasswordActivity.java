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
 * 找回密码
 */
public class FindPasswordActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private EditText edittextphone;
	private EditText edittextcode;
	private Button getcodebutton;
	private Button submit;
	private EditText edittextpassword;
	private int yzm = 1;//1 验证码超时或无效  2 有效 
	private CustomProgressDialog customProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userfindpassword);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("找回密码");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		customProgressDialog=new CustomProgressDialog(FindPasswordActivity.this, "正在重置......");
		edittextphone = (EditText) findViewById(R.id.edittextphone);
		edittextcode = (EditText) findViewById(R.id.edittextcode);
		edittextpassword = (EditText) findViewById(R.id.edittextpassword);

		getcodebutton = (Button) findViewById(R.id.getcodebutton);
		getcodebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				String phone = edittextphone.getText().toString().trim();
				String check = RegularExpression.checkRegularExpression(phone,
						RegularExpression.MOBILE_PHONE, "请输入正确的手机号码！");
				
				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(FindPasswordActivity.this, check, Toast.LENGTH_SHORT).show();
					return;
				}
				getcodebutton.setBackgroundResource(R.drawable.getcode_onbutton);
				getcodebutton.setClickable(false);
				VolleyUtil.sendStringRequestByGetToString(CommonVariable.GetCodeURL+phone+"/"+CommonVariable.SMSCodeForFindpassword, null, null, new HttpBackBaseListener() {
					
					@Override
					public void onSuccess(String string) {
						edittextphone.setEnabled(false);
						yzm = 2;
						new MyCount(120000,1000).start();
					}
					
					@Override
					public void onFail(String failstring) {
						Toast.makeText(FindPasswordActivity.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}
					
					@Override
					public void onError(VolleyError error) {
						Toast.makeText(FindPasswordActivity.this, "获取验证码失败！",Toast.LENGTH_SHORT).show();
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
				String password = edittextpassword.getText().toString().trim();
				if(StringUtils.isBlank(phone)||StringUtils.isBlank(code)||StringUtils.isBlank(password)){
					Toast.makeText(FindPasswordActivity.this, "请输入相关信息！",Toast.LENGTH_SHORT).show();
					return;
				}
				if(yzm ==1){
					Toast.makeText(FindPasswordActivity.this, "验证码已失效！",Toast.LENGTH_SHORT).show();
					return;
				}
				String check = RegularExpression.checkRegularExpression(password,
						RegularExpression.userpass, "请输入6-12位字母或数字！");
				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(FindPasswordActivity.this, check,Toast.LENGTH_SHORT).show();
					return;
				}
				customProgressDialog.show();
				VolleyUtil.sendStringRequestByGetToString(CommonVariable.FindMemberPasswordURL+phone+"/"+code+"/"+password, null, null, new HttpBackBaseListener() {
					
					@Override
					public void onSuccess(String string) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						edittextphone.setText("");
						edittextcode.setText("");
						edittextpassword.setText("");
						getcodebutton.setBackgroundResource(R.drawable.login_button);
			        	getcodebutton.setText("获取验证码");
			        	yzm = 1;
			        	Toast.makeText(FindPasswordActivity.this, "重置成功！", Toast.LENGTH_SHORT).show();
			        	finish();
					}
					
					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(FindPasswordActivity.this, "重置失败！"+failstring,Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(FindPasswordActivity.this,  "重置失败！", Toast.LENGTH_SHORT).show();
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
