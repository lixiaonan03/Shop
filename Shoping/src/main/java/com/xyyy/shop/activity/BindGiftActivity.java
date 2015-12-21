package com.xyyy.shop.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.model.EnnCard;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 绑定礼品卡的
 */
public class BindGiftActivity extends BaseActivity {

	private CustomProgressDialog customProgressDialog;
	private ImageView top_back;
	private TextView top_text;
	private EditText edittextcardnum_new;
	private EditText edittextcode_new;
	private Button submit_new;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bindgift);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("绑定礼品卡");
		top_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		customProgressDialog = new CustomProgressDialog(BindGiftActivity.this,
				"正在绑定......");
		initView();
	}

	/**
	 * 初始化界面组件
	 */
	private void initView() {
		// 新会员卡
		edittextcardnum_new = (EditText) findViewById(R.id.edittextcardnum_new);
		edittextcode_new = (EditText) findViewById(R.id.edittextcode_new);
		submit_new = (Button) findViewById(R.id.submit_new);
		submit_new.setOnClickListener(viewclick);
	}

	OnClickListener viewclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.submit_new:
				// 新会员的绑定
				gobindnew();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 绑定礼品卡的内容
	 */
	protected void gobindnew() {
		// TODO 去绑定新会员卡
		String carnum = edittextcardnum_new.getText().toString().trim();
		String codenew = edittextcode_new.getText().toString().trim();
		if (StringUtils.isBlank(carnum) || StringUtils.isBlank(codenew)) {
			Toast.makeText(BindGiftActivity.this, "请填写相关信息！",
					Toast.LENGTH_SHORT).show();
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
		VolleyUtil.sendStringRequestByGetToBean(CommonVariable.BindNewMemberURL
				+ carnum + "/" + codenew + "/" + userid+ "/10", null, null,
				EnnCard.class, new HttpBackListener<EnnCard>() {

					@Override
					public void onSuccess(EnnCard t) {
						customProgressDialog.dismiss();
						AlertDialog.Builder builder = new AlertDialog.Builder(
								BindGiftActivity.this);
						builder.setTitle("提示");
						builder.setMessage("绑定成功");
						builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
								finish();
							}
						});
						if (!isFinishing()) {
							builder.create().show();
						}
						edittextcardnum_new.setText("");
						edittextcode_new.setText("");
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(BindGiftActivity.this,
								"绑定失败！" + failstring, Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(BindGiftActivity.this, "绑定失败！",
								Toast.LENGTH_SHORT).show();
					}
				}, false, null);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		  /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
         */
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
