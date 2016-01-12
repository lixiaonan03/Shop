package com.xyyy.shop.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.model.EnnMember;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.RegularExpression;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 用户更换手机号
 */
public class UserchangephoneActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	private EditText edittextphone;
	private EditText edittextcode;
	private Button getcodebutton;
	private SmsObserver smsObserver;
	private int yzm = 1;//1 验证码超时或无效  2 有效 
    private Integer id;
	private CustomProgressDialog customProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userchangephone);

		/*smsObserver = new SmsObserver(this, smsHandler);
		getContentResolver().registerContentObserver(SMS_INBOX, true,
				smsObserver);*/
		customProgressDialog = new CustomProgressDialog(
				UserchangephoneActivity.this, "正在验证......");
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
		edittextcode = (EditText) findViewById(R.id.edittextcode);

		getcodebutton = (Button) findViewById(R.id.getcodebutton);
		getcodebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				String phone = edittextphone.getText().toString().trim();
				String check = RegularExpression.checkRegularExpression(phone,
						RegularExpression.MOBILE_PHONE, "请输入正确的手机号码！");
				
				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(UserchangephoneActivity.this, check, Toast.LENGTH_SHORT).show();
					return;
				}
			
				getcodebutton.setClickable(false);
				String codefalg="";
					codefalg=CommonVariable.SMSCodeForCheckPhone;
				VolleyUtil.sendStringRequestByGetToString(CommonVariable.GetCodeURL+phone+"/"+codefalg, null, null, new HttpBackBaseListener() {
					
					@Override
					public void onSuccess(String string) {
						getcodebutton.setBackgroundResource(R.drawable.getcode_onbutton);
						edittextphone.setEnabled(false);
						yzm = 2;
						new MyCount(120000,1000).start();
					}
					
					@Override
					public void onFail(String failstring) {
						Toast.makeText(UserchangephoneActivity.this, "获取验证码失败！",Toast.LENGTH_SHORT).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}
					
					@Override
					public void onError(VolleyError error) {
						Toast.makeText(UserchangephoneActivity.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();
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
					//第一步
					String phone = edittextphone.getText().toString().trim();
					String code = edittextcode.getText().toString().trim();
					if(StringUtils.isBlank(phone)||StringUtils.isBlank(code)){
						Toast.makeText(UserchangephoneActivity.this,"请输入相关信息！",Toast.LENGTH_SHORT).show();
						return;
					}
					if(yzm==1){
						Toast.makeText(UserchangephoneActivity.this,"验证码失效！", Toast.LENGTH_SHORT).show();
						return;
					}
					customProgressDialog.show();
					int memberid = 0;
					if(ShopApplication.isLogin){
						if(ShopApplication.loginflag==1){
							memberid = ShopApplication.userid;
						}
						if(ShopApplication.loginflag==2){
							memberid = ShopApplication.useridother;
						}
					}
					VolleyUtil.sendStringRequestByGetToBean(CommonVariable.ChangeUserphoneOneURL+phone+"/"+code+"/"+memberid,null, null, EnnMember.class, new HttpBackListener<EnnMember>(){

						@Override
						public void onSuccess(EnnMember t) {
							customProgressDialog.dismiss();
							if(null!=t){
								yzm = 1;
							/*	edittextphone.setText("");
								edittextphone.setEnabled(true);
								getcodebutton.setText("获取验证码");
								getcodebutton.setBackgroundResource(R.drawable.login_button);*/
								id=t.getMembId();
								Intent  intent=new Intent(UserchangephoneActivity.this,UserchangephoneTwoActivity.class);
								intent.putExtra("memberid", id);
								startActivity(intent);
								finish();
							}
						}

						@Override
						public void onFail(String failstring) {
							customProgressDialog.dismiss();
							Toast.makeText(UserchangephoneActivity.this, "当前的手机号验证失败！",Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onError(VolleyError error) {
							customProgressDialog.dismiss();
							Toast.makeText(UserchangephoneActivity.this, "当前的手机号验证失败！",Toast.LENGTH_SHORT).show();
						}
						
					}, false, null);
				}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
    
	@Override
	protected void onDestroy() {
		//getContentResolver().unregisterContentObserver(smsObserver);
		super.onDestroy();
	}

	/**
	 * 读取手机短信验证码
	 */
	private Uri SMS_INBOX = Uri.parse("content://sms/");

	public void getSmsFromPhone() {
		ContentResolver cr = getContentResolver();
		String[] projection = new String[] { "body" };// "_id", "address",
														// "person",, "date",
														// "type
		String where = " address = '1065860113' AND date >  "
				+ (System.currentTimeMillis() - 10 * 60 * 1000);
		Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
		if (null == cur)
			return;
		if (cur.moveToNext()) {
			String body = cur.getString(cur.getColumnIndex("body"));
			int i = body.indexOf(":", 0);
			String code = body.substring(i + 1, i + 1 + 6);
			edittextcode.setText(code);
		}
	}

	public Handler smsHandler = new Handler() {
		// 这里可以进行回调的操作
		// TODO

	};

	class SmsObserver extends ContentObserver {

		public SmsObserver(Context context, Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			// 每当有新短信到来时，使用我们获取短消息的方法
			getSmsFromPhone();
		}
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
