package com.xyyy.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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
import com.xyyy.shop.model.EnnMember;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.RegularExpression;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 修改用户昵称的界面
 */
public class UsernameActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private EditText edittext;
	private Button submit;
	private TextView texthint;
	private CustomProgressDialog customProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_username);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("修改昵称");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		customProgressDialog=new CustomProgressDialog(UsernameActivity.this, "正在更改......");
		edittext=(EditText)findViewById(R.id.edittext);
		if (StringUtils.isNotBlank(ShopApplication.usernamelogin)) {
			edittext.setText(ShopApplication.usernamelogin);
			edittext.setSelection(ShopApplication.usernamelogin.length());
		}
		texthint=(TextView)findViewById(R.id.texthint);
		texthint.setText("4-20个字符，可由中英文、数字、“－”，“—”组成");
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
			
				final String  name=edittext.getText().toString().trim();
				
				if(StringUtils.isBlank(name)){
					Toast.makeText(UsernameActivity.this, "请输入昵称！", 0).show();
					return;
				}
					String check = RegularExpression.checkRegularExpression(
				edittext.getText().toString().trim(),RegularExpression.username,"请输入正确格式的昵称！"
				);
				if(StringUtils.isNotBlank(check)){
						Toast.makeText(UsernameActivity.this,check, 0).show();
						return;
				}
				EnnMember user=new EnnMember();
				int userid = 0;
				if(ShopApplication.isLogin){
					if(ShopApplication.loginflag==1){
					     userid = ShopApplication.userid;
					}
					if(ShopApplication.loginflag==2){
						userid = ShopApplication.useridother;
					}
				}
				user.setMembId(userid);
				user.setMembNick(name);
				customProgressDialog.show();
				VolleyUtil.sendObjectByPostToString(CommonVariable.UpdateMemberinfoURL, null, user, new HttpBackBaseListener() {
					
					@Override
					public void onSuccess(String string) {
						Intent intent = new Intent("changeuserinfo");
						LocalBroadcastManager.getInstance(UsernameActivity.this).sendBroadcast(intent);
						customProgressDialog.dismiss();
						ShopApplication.usernamelogin=name;
						getIntent().putExtra("name", name);
						setResult(1,getIntent());
						finish();
					}
					
					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(UsernameActivity.this, "修改失败！", 0).show();
					}
					
					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(UsernameActivity.this, "修改失败！", 0).show();
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
