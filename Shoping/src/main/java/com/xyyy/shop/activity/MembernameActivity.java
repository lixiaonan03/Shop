package com.xyyy.shop.activity;

import android.os.Bundle;
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
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 修改会员姓名的界面
 */
public class MembernameActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;
	private EditText edittext;
	private Button submit;
	private CustomProgressDialog customProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_membername);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("会员姓名");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		customProgressDialog = new CustomProgressDialog(
				MembernameActivity.this, "正在更改......");
		edittext = (EditText) findViewById(R.id.edittext);
		if (null != ShopApplication.userinfo
				&& null != ShopApplication.userinfo.getMembName()) {
			edittext.setText(ShopApplication.userinfo.getMembName());
			edittext.setSelection(ShopApplication.userinfo.getMembName()
					.length());
		}
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {

				final String name = edittext.getText().toString().trim();

				if (StringUtils.isBlank(name)) {
					Toast.makeText(MembernameActivity.this, "请输入姓名！", 0).show();
					return;
				}
				EnnMember user = new EnnMember();
				int userid = 0;
				if (ShopApplication.isLogin) {
					if (ShopApplication.loginflag == 1) {
						userid = ShopApplication.userid;
					}
					if (ShopApplication.loginflag == 2) {
						userid = ShopApplication.useridother;
					}
				}
				user.setMembId(userid);
				user.setMembName(name);
				customProgressDialog.show();
				VolleyUtil.sendObjectByPostToString(
						CommonVariable.UpdateMemberinfoURL, null, user,
						new HttpBackBaseListener() {

							@Override
							public void onSuccess(String string) {
								customProgressDialog.dismiss();
								if (null != ShopApplication.userinfo) {
									ShopApplication.userinfo.setMembName(name);
								}
								getIntent().putExtra("membername", name);
								setResult(1, getIntent());
								finish();
							}

							@Override
							public void onFail(String failstring) {
								customProgressDialog.dismiss();
								Toast.makeText(MembernameActivity.this,
										"修改失败！", 0).show();
							}

							@Override
							public void onError(VolleyError error) {
								customProgressDialog.dismiss();
								Toast.makeText(MembernameActivity.this,
										"修改失败！", 0).show();
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
