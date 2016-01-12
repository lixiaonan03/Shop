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
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.db.Cart;
import com.xyyy.shop.db.CartDao;
import com.xyyy.shop.model.EnnCart;
import com.xyyy.shop.model.EnnCartDTO;
import com.xyyy.shop.model.EnnMember;
import com.xyyy.shop.model.EnnSmsCode;
import com.xyyy.shop.model.PerfectDataDTO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.RegularExpression;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 微信登录完善资料
 */
public class MemberCompleteActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	private EditText edittextphone;
	private EditText edittextcode;
	private Button getcodebutton;
	private int yzm = 1;//1 验证码超时或无效  2 有效 
	private int memberid;
	private CustomProgressDialog customProgressDialog;
	private EditText edittextname;
	private CheckBox checkBox;
	private TextView agreement;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_membercomplete);
		customProgressDialog = new CustomProgressDialog(
				MemberCompleteActivity.this, "正在完善......");
		memberid = getIntent().getIntExtra("memberid", 0);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("完善会员资料");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		edittextname = (EditText) findViewById(R.id.edittextname);
		edittextphone = (EditText) findViewById(R.id.edittextphone);
		edittextcode = (EditText) findViewById(R.id.edittextcode);

		checkBox = (CheckBox) findViewById(R.id.checkBox);
		agreement = (TextView) findViewById(R.id.agreement);
		agreement.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MemberCompleteActivity.this, MemberAgreementActivity.class);
				startActivity(intent);
				finish();
			}
		});
		getcodebutton = (Button) findViewById(R.id.getcodebutton);
		getcodebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				String phone = edittextphone.getText().toString().trim();
				String check = RegularExpression.checkRegularExpression(phone,
						RegularExpression.MOBILE_PHONE, "请输入正确的手机号码！");

				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(MemberCompleteActivity.this, check, Toast.LENGTH_SHORT).show();
					return;
				}
				getcodebutton.setClickable(false);
				String codefalg="";
				codefalg=CommonVariable.SMSCodeForChangePhone;
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
						Toast.makeText(MemberCompleteActivity.this, "获取验证码失败！",Toast.LENGTH_SHORT).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}

					@Override
					public void onError(VolleyError error) {
						Toast.makeText(MemberCompleteActivity.this, "获取验证码失败！",Toast.LENGTH_SHORT).show();
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
				if(!checkBox.isChecked()){
					Toast.makeText(MemberCompleteActivity.this,"请同意协议！",Toast.LENGTH_SHORT).show();
					return;
				}
				final String name = edittextname.getText().toString().trim();
				final String phone = edittextphone.getText().toString().trim();
				String code = edittextcode.getText().toString().trim();
				if(StringUtils.isBlank(phone)||StringUtils.isBlank(code)||StringUtils.isBlank(name)){
					Toast.makeText(MemberCompleteActivity.this,"请输入相关信息！",Toast.LENGTH_SHORT).show();
					return;
				}
				if(yzm==1){
					Toast.makeText(MemberCompleteActivity.this,"验证码失效！",Toast.LENGTH_SHORT).show();
					return;
				}
				customProgressDialog.show();
				PerfectDataDTO dto=new PerfectDataDTO();
				EnnSmsCode smscode=new EnnSmsCode();
				smscode.setVerifyCode(code);
				EnnMember ennmember=new EnnMember();
				ennmember.setMembId(memberid);
				ennmember.setMembName(name);
				ennmember.setMembPhone(phone);
				dto.setEnnSmsCode(smscode);
				dto.setEnnMember(ennmember);
				VolleyUtil.sendObjectByPostToString(CommonVariable.MemberCompleteURL, null, dto, new HttpBackBaseListener() {

					@Override
					public void onSuccess(String string) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						Intent intent=new Intent(getApplicationContext(), MainActivity.class);
						intent.putExtra("flag", 4);
						ShopApplication.loginflag=2;
						ShopApplication.isLogin=true;
						ShopApplication.useridother=memberid;
						if(null!=ShopApplication.userinfo){
							ShopApplication.userinfo.setMembName(name);
							ShopApplication.userinfo.setMembPhone(phone);
						}
						sendcart(memberid);
						startActivity(intent);
						finish();
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(MemberCompleteActivity.this,"完善资料失败！"+failstring,Toast.LENGTH_SHORT).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(MemberCompleteActivity.this,"完善资料失败！", Toast.LENGTH_SHORT).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
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

	/**
	 * 把本地购物车的信息同步到服务器
	 * @param userid 用户id
	 */
	void sendcart(int userid){
		//TODO 把本地购物车的信息同步到服务器
		EnnCartDTO cartdao=new EnnCartDTO();
		List<EnnCart> list=new ArrayList<EnnCart>();
		List<Cart> listcurrent = CartDao.getInstance().queryAllGood();
		if(null!=listcurrent&&listcurrent.size()>0){
			for (Cart cart : listcurrent) {
				EnnCart enncart=new EnnCart();
				enncart.setMembId(userid);
				enncart.setCartNum(cart.getNum());
				enncart.setGoodsId(cart.getGoodid());
				list.add(enncart);
			}
			cartdao.setEnnCart(list);
			VolleyUtil.sendObjectByPostToString(CommonVariable.CartLoginUpdateAllGoodURL, null, cartdao, new HttpBackBaseListener() {

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
