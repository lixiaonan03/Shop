package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.db.Cart;
import com.xyyy.shop.db.CartDao;
import com.xyyy.shop.model.EnnCart;
import com.xyyy.shop.model.EnnCartDTO;
import com.xyyy.shop.model.EnnMember;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.MD5Util;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {

	private EditText editTextLoginCode;
	private EditText editTextPassword;
	private TextView loginSubmit;
	private TextView goregister;
	private TextView forgetpassword;
	private ImageView top_back;
	private CustomProgressDialog customProgressDialog;
	private ImageView login_wx;
	private TextView top_text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		customProgressDialog = new CustomProgressDialog(LoginActivity.this,
				"正在登录......");
		initView();
	}

	private void initView() {
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("登录");

		editTextLoginCode = (EditText) findViewById(R.id.editTextLoginCode);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		loginSubmit = (TextView) findViewById(R.id.login_submit);
		goregister = (TextView) findViewById(R.id.goregister);
		forgetpassword = (TextView) findViewById(R.id.forgetpassword);

		login_wx = (ImageView) findViewById(R.id.login_wx);
		// 设置监听
		top_back.setOnClickListener(new ViewOnClickListener());
		loginSubmit.setOnClickListener(new ViewOnClickListener());
		goregister.setOnClickListener(new ViewOnClickListener());
		forgetpassword.setOnClickListener(new ViewOnClickListener());
		login_wx.setOnClickListener(new ViewOnClickListener());
	}

	private class ViewOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int viewid = v.getId();
			switch (viewid) {
				case R.id.top_back:
					// 回退
					finish();
					break;
				case R.id.login_submit:
					// 登录
					goLogin();

					break;
				case R.id.goregister:
					// 去注册
					Intent intent = new Intent(LoginActivity.this,
							RegisterActivity.class);
					startActivity(intent);
					break;
				case R.id.forgetpassword:
					// 忘记密码
					Intent intentfind = new Intent(LoginActivity.this,
							FindPasswordActivity.class);
					startActivity(intentfind);
					break;
				case R.id.login_wx:
					// 微信登录的方式
					if(!(ShopApplication.api.isWXAppInstalled()&&ShopApplication.api.isWXAppSupportAPI())){
						Toast.makeText(LoginActivity.this, "请安装最新版本的微信客户端！", 0).show();
						return;
					}
					customProgressDialog.show();
					StatService.onEvent(LoginActivity.this,"loginactivity_wx" ,"微信登录");
					SendAuth.Req req = new SendAuth.Req();
					req.scope = "snsapi_userinfo";
					req.state = "shop_wx_login";
					ShopApplication.api.sendReq(req);
					break;

				default:
					break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	/**
	 * 去登录的方法
	 */
	private void goLogin() {
		final String username = editTextLoginCode.getText().toString().trim();
		final String password = editTextPassword.getText().toString().trim();
		// 判断是否输入内容
		if (null == username || "" == username || "".equals(password)
				|| null == password) {
			Toast.makeText(getApplicationContext(), "请输入用户名和密码！",
					Toast.LENGTH_SHORT).show();
		} else {
			customProgressDialog.show();
			StatService.onEvent(LoginActivity.this,"loginactivity_phone" ,"原生登录");
			String encryptStr = MD5Util.encryptStr("name=" + username
					+ "&crypted_password=" + password);
			// String url=CommonVariable.LoginURL+encryptStr;
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
							logingofor();
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

	/**
	 * 根据传入的标记 决定登录完成之后 需要跳转的界面
	 */
	void logingofor() {
		int flag = getIntent().getIntExtra("flag", 0);
		switch (flag) {
			case 01:
				// 要回到主界面的
				setResult(01);
				finish();
				break;
			case 02:
				// 要回到购物车的
				setResult(02);
				finish();
				break;
			case 03:
				// 要回到会员卡的
				setResult(03);
				finish();
				break;
			case 04:
				// 要回到个人中心
				setResult(04);
				finish();
				break;
			case 06:
				// 要回到商品详情的
				setResult(06);
				finish();
				break;

			default:
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null!=customProgressDialog&&customProgressDialog.isShowing()) {
			customProgressDialog.dismiss();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (null!=customProgressDialog&&customProgressDialog.isShowing()) {
			customProgressDialog.dismiss();
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
