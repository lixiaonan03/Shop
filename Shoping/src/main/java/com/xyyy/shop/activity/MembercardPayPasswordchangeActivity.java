package com.xyyy.shop.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.model.EnnMemberPaypwd;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.RegularExpression;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 修改用户会员卡支付密码的界面
 */
public class MembercardPayPasswordchangeActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	private EditText edittextold;
	private EditText edittextnew;
	private EditText edittextnewagain;
	private CustomProgressDialog customProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_membercardpaypasswordchange);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("修改支付密码");
		customProgressDialog=new CustomProgressDialog(MembercardPayPasswordchangeActivity.this, "正在修改......");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		edittextold=(EditText)findViewById(R.id.edittextold);
		edittextnew=(EditText)findViewById(R.id.edittextnew);
		edittextnewagain=(EditText)findViewById(R.id.edittextnewagain);
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			
			private int userid;

			@Override
			public void onClick(View paramView) {
				final String old=edittextold.getText().toString().trim();
				final String newpass=edittextnew.getText().toString().trim();
				String newagain=edittextnewagain.getText().toString().trim();
				if(StringUtils.isBlank(old)||StringUtils.isBlank(newpass)||StringUtils.isBlank(newagain)){
					Toast.makeText(MembercardPayPasswordchangeActivity.this,"请填写相关信息！",Toast.LENGTH_SHORT).show();
					return;
				}
				String check = RegularExpression.checkRegularExpression(newpass,
						RegularExpression.paypassword, "请输入6位支付密码！");
				
				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(MembercardPayPasswordchangeActivity.this, check, Toast.LENGTH_SHORT).show();
					return;
				}
				if(!newagain.equals(newpass)){
					Toast.makeText(MembercardPayPasswordchangeActivity.this,"2次输入的新密码不一致！",Toast.LENGTH_SHORT).show();
					return;
				}
				customProgressDialog.show();
				userid = 0;
				if(ShopApplication.isLogin){
					if(ShopApplication.loginflag==1){
					     userid = ShopApplication.userid;
					}
					if(ShopApplication.loginflag==2){
						userid = ShopApplication.useridother;
					}
				}
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("membId", userid+"");
				VolleyUtil.sendObjectByPostToList(CommonVariable.MembercardPayPasswordGetURL,null, map, EnnMemberPaypwd.class, new HttpBackListListener<EnnMemberPaypwd>() {

					@Override
					public void onSuccess(List<EnnMemberPaypwd> t) {
						if(null!=t&&t.size()>0){
							if(t.get(0).getPayPwd().equals(old)){
								VolleyUtil.sendStringRequestByGetToString(CommonVariable.MembercardPayPasswordUpdateURL+userid+"/"+newpass, null, null, new HttpBackBaseListener() {
									
									@Override
									public void onSuccess(String string) {
										customProgressDialog.dismiss();
										edittextold.setText("");
										edittextnew.setText("");
										edittextnewagain.setText("");
										AlertDialog.Builder builder = new AlertDialog.Builder(MembercardPayPasswordchangeActivity.this);
								        builder.setTitle("提示");
								        builder.setMessage("亲，您的支付密码已修改成功。");
								        builder.setNegativeButton("确定", null);
								        if (!isFinishing()) {
								            builder.create().show();
								        }
									}
									
									@Override
									public void onFail(String failstring) {
										customProgressDialog.dismiss();
										Toast.makeText(MembercardPayPasswordchangeActivity.this,"修改失败！",Toast.LENGTH_SHORT).show();
									}
									
									@Override
									public void onError(VolleyError error) {
										customProgressDialog.dismiss();
										Toast.makeText(MembercardPayPasswordchangeActivity.this,"修改失败！",Toast.LENGTH_SHORT).show();
									}
								}, false, null);
							}else{
								customProgressDialog.dismiss();
								Toast.makeText(MembercardPayPasswordchangeActivity.this,"旧密码确认失败！",Toast.LENGTH_SHORT).show();
							}
						}else{
							customProgressDialog.dismiss();
							Toast.makeText(MembercardPayPasswordchangeActivity.this,"旧密码确认失败！",Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(MembercardPayPasswordchangeActivity.this,"旧密码确认失败！",Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(MembercardPayPasswordchangeActivity.this,"旧密码确认失败！",Toast.LENGTH_SHORT).show();
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
