package com.xyyy.shop.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
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
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 修改用户性别的界面
 */
public class UsersexActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	private RadioGroup radiogroup;
	private String sextext="";
	private CustomProgressDialog customProgressDialog;
	private RadioButton sex_men;
	private RadioButton sex_women;
	private RadioButton sex_secret;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersex);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("修改性别");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		customProgressDialog=new CustomProgressDialog(UsersexActivity.this, "正在更改......");
		radiogroup=(RadioGroup)findViewById(R.id.radiogroup);
		sex_men=(RadioButton)findViewById(R.id.sex_men);
		sex_women=(RadioButton)findViewById(R.id.sex_women);
		sex_secret=(RadioButton)findViewById(R.id.sex_secret);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.sex_men:
					sextext="男";
					break;
				case R.id.sex_women:
					sextext="女";
					break;
				case R.id.sex_secret:
					sextext="保密";
					break;

				default:
					break;
				}
			}
		});
		
		if(null!=ShopApplication.userinfo&&null!=ShopApplication.userinfo.getMembSex()){
			switch (StrToNumber.strToint(ShopApplication.userinfo.getMembSex())) {
			case 0:
				sex_secret.setChecked(true);
				break;
			case 1:
				sex_men.setChecked(true);
				break;
			case 2:
				sex_women.setChecked(true);
				break;

			default:
				break;
			}
		}else{
			sex_secret.setChecked(true);
		}
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				if(sextext.equals("")){
					Toast.makeText(UsersexActivity.this, "请选择性别！", Toast.LENGTH_SHORT).show();
					return;
				}
				EnnMember user=new EnnMember();
				int userid = 0;
				if(ShopApplication.isLogin){
					if(ShopApplication.loginflag==1){
					     userid = ShopApplication.userid;
					}
				}
				user.setMembId(userid);
				if(sextext.equals("保密")){
					user.setMembSex("0");
				}
				if(sextext.equals("男")){
					user.setMembSex("1");
				}
				if(sextext.equals("女")){
					user.setMembSex("2");
				}
				
				customProgressDialog.show();
				VolleyUtil.sendObjectByPostToString(CommonVariable.UpdateMemberinfoURL, null, user, new HttpBackBaseListener() {
					
					@Override
					public void onSuccess(String string) {
						customProgressDialog.dismiss();
						if(null!=ShopApplication.userinfo){
							if(sextext.equals("保密")){
								ShopApplication.userinfo.setMembSex("0");
							}
							if(sextext.equals("男")){
								ShopApplication.userinfo.setMembSex("1");
							}
							if(sextext.equals("女")){
								ShopApplication.userinfo.setMembSex("2");
							}
							
						}
						getIntent().putExtra("sex", sextext);
						setResult(1,getIntent());
						finish();
					}
					
					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(UsersexActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(UsersexActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
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
