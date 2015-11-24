package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.xyyy.shop.db.Cart;
import com.xyyy.shop.db.CartDao;
import com.xyyy.shop.model.EnnCart;
import com.xyyy.shop.model.EnnCartDTO;
import com.xyyy.shop.model.EnnMember;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.RegularExpression;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 用户注册的界面
 */
public class RegisterActivity extends BaseActivity {

	private EditText editTextLoginCode;
	private ImageView top_back;
	private EditText editTextPassword;
	private Button register_submit;
	private CustomProgressDialog customProgressDialog;
	private Button getcodebutton;
	private EditText edittextcode;
	private int yzm = 1;//1 验证码超时或无效  2 有效 
	private TextView top_text;
	private CheckBox checkBox;
	private TextView agreement;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ShopApplication.activityList.add(this);
		setContentView(R.layout.activity_register);
		customProgressDialog = new CustomProgressDialog(RegisterActivity.this,
				"正在注册......");
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("注册");
		editTextLoginCode = (EditText) findViewById(R.id.editTextLoginCode);
		getcodebutton = (Button) findViewById(R.id.getcodebutton);
		edittextcode = (EditText) findViewById(R.id.edittextcode);
		
		checkBox = (CheckBox) findViewById(R.id.checkBox);
		agreement = (TextView) findViewById(R.id.agreement);
		agreement.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(RegisterActivity.this, MemberAgreementActivity.class);
				startActivity(intent);
			}
		});
		getcodebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String phone = editTextLoginCode.getText().toString().trim();
				String check = RegularExpression.checkRegularExpression(phone,
						RegularExpression.MOBILE_PHONE, "请输入正确的手机号码！");
				
				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(RegisterActivity.this, check, 0).show();
					return;
				}
				
				getcodebutton.setClickable(false);
				VolleyUtil.sendStringRequestByGetToString(CommonVariable.GetCodeURL+phone+"/01", null, null, new HttpBackBaseListener() {
					
					@Override
					public void onSuccess(String string) {
						// TODO Auto-generated method stub
						getcodebutton.setBackgroundResource(R.drawable.getcode_onbutton);
						editTextLoginCode.setEnabled(false);
						yzm = 2;
						new MyCount(120000,1000).start();
					}
					
					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						Toast.makeText(RegisterActivity.this, "获取验证码失败！", 0).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}
					
					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(RegisterActivity.this, "获取验证码失败！", 0).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}
				}, false, null);
			}
		});
		;
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		register_submit = (Button) findViewById(R.id.register_submit);

		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		register_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 调用注册的接口方法
				final String username = editTextLoginCode.getText().toString();
				final String password = editTextPassword.getText().toString();
				String code = edittextcode.getText().toString();

				if (StringUtils.isBlank(username)
						|| StringUtils.isBlank(password)
						|| StringUtils.isBlank(code)) {
					Toast.makeText(ShopApplication.application, "请填写相关信息！",
							Toast.LENGTH_SHORT).show();
				} else {
					if(yzm==1){
						Toast.makeText(ShopApplication.application, "验证码无效！",
								Toast.LENGTH_SHORT).show();
						return;
					}
					if(!checkBox.isChecked()){
				    	Toast.makeText(RegisterActivity.this,"请同意协议！", 0).show();
						return;
				    }
					customProgressDialog.show();
					VolleyUtil.sendStringRequestByGetToString(
							CommonVariable.RegisterURL + username + "/"
									+ password + "/" + code, null, null,
							new HttpBackBaseListener() {

								@Override
								public void onSuccess(String string) {
									
									editTextLoginCode.setText("");
									editTextPassword.setText("");
									edittextcode.setText("");
									
									// TODO 注册成功
									/*Intent intent = new Intent(
												RegisterActivity.this,
												LoginActivity.class);
									startActivity(intent);*/
									String url = CommonVariable.LoginURL + username + "/" + password;
									VolleyUtil.sendStringRequestByGetToBean(url, null, null,
											EnnMember.class, new HttpBackListener<EnnMember>() {

												@Override
												public void onSuccess(EnnMember user) {
													customProgressDialog.dismiss();
													ShopApplication.isLogin = true;
													ShopApplication.loginflag = 1;
													if (null != user.getMembId()) {
														ShopApplication.userid = user.getMembId();
													} else {
														user.setMembId(0);
													}
													if (null != user.getMembNick()) {
														ShopApplication.usernamelogin = user
																.getMembNick();
													} else {
														ShopApplication.usernamelogin = "";
													}
													if (null != user.getMembImg()) {
														ShopApplication.userimgurlrlogin = user
																.getMembImg();
													}
													ShopApplication.userinfo = user;
													sendcart(user.getMembId());
													Intent intent=new Intent(getApplicationContext(), MainActivity.class);
													intent.putExtra("flag", 4);
													startActivity(intent);
													finish();
												}

												@Override
												public void onFail(String failstring) {
													// TODO Auto-generated method stub
													customProgressDialog.dismiss();
													Toast.makeText(getApplicationContext(), failstring,
															2).show();
												}

												@Override
												public void onError(VolleyError error) {
													// TODO Auto-generated method stub
													customProgressDialog.dismiss();
													Toast.makeText(getApplicationContext(), "登录失败！", 0)
															.show();
												}

											}, false, null);
								}

								@Override
								public void onFail(String failstring) {
									// TODO 注册失败
									customProgressDialog.dismiss();
									Toast.makeText(getApplicationContext(),
											"注册失败：" + failstring, 2).show();
								}

								@Override
								public void onError(VolleyError error) {
									// TODO 接口访问出错
									customProgressDialog.dismiss();
									Toast.makeText(RegisterActivity.this,
											"注册失败!", 0).show();
								}
							}, false, null);
				}
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
        	editTextLoginCode.setEnabled(true);
        	
        	
        	getcodebutton.setBackgroundResource(R.drawable.login_button);
        	getcodebutton.setText("获取验证码");
        	yzm = 1;
        }  
  
        @Override  
        public void onTick(long millisUntilFinished) {  
        	getcodebutton.setText(millisUntilFinished / 1000+"秒");  
        }  
  
    }  
	
	/**
	 * 把本地购物车的信息同步到服务器
	 * 
	 * @param userid
	 *            用户id
	 */
	void sendcart(int userid) {
		// TODO 把本地购物车的信息同步到服务器
		EnnCartDTO cartdao = new EnnCartDTO();
		List<EnnCart> list = new ArrayList<EnnCart>();
		List<Cart> listcurrent = CartDao.getInstance().queryAllGood();
		if (null != listcurrent && listcurrent.size() > 0) {
			for (Cart cart : listcurrent) {
				EnnCart enncart = new EnnCart();
				enncart.setMembId(userid);
				enncart.setCartNum(cart.getNum());
				enncart.setGoodsId(cart.getGoodid());
				list.add(enncart);
			}
			cartdao.setEnnCart(list);
			VolleyUtil.sendObjectByPostToString(
					CommonVariable.CartLoginUpdateAllGoodURL, null, cartdao,
					new HttpBackBaseListener() {

						@Override
						public void onSuccess(String string) {
							CartDao.getInstance().deleteAllCart();
						}

						@Override
						public void onFail(String failstring) {
						}

						@Override
						public void onError(VolleyError error) {
						}
					}, false, null);
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
