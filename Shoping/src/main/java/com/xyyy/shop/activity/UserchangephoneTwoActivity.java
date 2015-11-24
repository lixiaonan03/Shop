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
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.RegularExpression;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 用户更换手机号 第二步
 */
public class UserchangephoneTwoActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	private EditText edittextphone;
	private EditText edittextcode;
	private Button getcodebutton;
	private int yzm = 1;//1 验证码超时或无效  2 有效 
	private CustomProgressDialog customProgressDialog;
	private CountDownTimer count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userchangephone);

		customProgressDialog = new CustomProgressDialog(
				UserchangephoneTwoActivity.this, "正在更改......");
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("更换手机号");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		edittextphone = (EditText) findViewById(R.id.edittextphone);
		edittextphone.setHint("请输入新的手机号");
		edittextcode = (EditText) findViewById(R.id.edittextcode);

		getcodebutton = (Button) findViewById(R.id.getcodebutton);
		getcodebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				String phone = edittextphone.getText().toString().trim();
				String check = RegularExpression.checkRegularExpression(phone,
						RegularExpression.MOBILE_PHONE, "请输入正确的手机号码！");
				
				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(UserchangephoneTwoActivity.this, check, 0).show();
					return;
				}
				getcodebutton.setBackgroundResource(R.drawable.getcode_onbutton);
				getcodebutton.setClickable(false);
				String codefalg="";
			     codefalg=CommonVariable.SMSCodeForChangePhone;
				VolleyUtil.sendStringRequestByGetToString(CommonVariable.GetCodeURL+phone+"/"+codefalg, null, null, new HttpBackBaseListener() {
					
					

					@Override
					public void onSuccess(String string) {
						edittextphone.setEnabled(false);
						yzm = 2;
						count=new MyCount(120000,1000);
						count.start();
					}
					
					@Override
					public void onFail(String failstring) {
						Toast.makeText(UserchangephoneTwoActivity.this, "获取验证码失败！", 0).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}
					
					@Override
					public void onError(VolleyError error) {
						Toast.makeText(UserchangephoneTwoActivity.this, "获取验证码失败！", 0).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}
				}, false, null);
			}
		});
		submit = (Button) findViewById(R.id.submit);
		submit.setText("更换");
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
			
					final String phone = edittextphone.getText().toString().trim();
					String code = edittextcode.getText().toString().trim();
					if(StringUtils.isBlank(phone)||StringUtils.isBlank(code)){
						Toast.makeText(UserchangephoneTwoActivity.this,"请输入相关信息！", 0).show();
						return;
					}
					if(yzm==1){
						Toast.makeText(UserchangephoneTwoActivity.this,"验证码失效！", 0).show();
						return;
					}
					customProgressDialog.show();
					int userid = 0;
					if (ShopApplication.isLogin) {
						if (ShopApplication.loginflag == 1) {
							userid = ShopApplication.userid;
						}
						if (ShopApplication.loginflag == 2) {
							userid = ShopApplication.useridother;
						}
					}
					VolleyUtil.sendStringRequestByGetToString(CommonVariable.ChangeUserphoneTwoURL+phone+"/"+code+"/"+userid,null, null,new HttpBackBaseListener() {
						
						@Override
						public void onSuccess(String string) {
							// TODO Auto-generated method stub
							customProgressDialog.dismiss();
							if (null != ShopApplication.userinfo) {
								ShopApplication.userinfo.setMembPhone(phone);
							}
							getIntent().putExtra("memberphone", phone);
							setResult(1, getIntent());
							finish();
						}
						
						@Override
						public void onFail(String failstring) {
							// TODO Auto-generated method stub
							customProgressDialog.dismiss();
							Toast.makeText(UserchangephoneTwoActivity.this, "更换失败！", 0).show();
						}
						
						@Override
						public void onError(VolleyError error) {
							// TODO Auto-generated method stub
							customProgressDialog.dismiss();
							Toast.makeText(UserchangephoneTwoActivity.this, "更换失败！", 0).show();
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
