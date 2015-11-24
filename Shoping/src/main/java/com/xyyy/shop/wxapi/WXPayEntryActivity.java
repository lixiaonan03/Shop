package com.xyyy.shop.wxapi;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.util.VolleyUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.activity.OrderDetailActivity;
import com.xyyy.shop.toolUtil.CommonVariable;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	

	private void handleIntent(Intent paramIntent) {
		ShopApplication.api.handleIntent(paramIntent, this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent(intent);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode==-2){
				if(ShopApplication.payflag==1){
						VolleyUtil.sendStringRequestByGetToString(CommonVariable.PayErrorURL+ShopApplication.orderid, null, null,new  HttpBackBaseListener(){

							@Override
							public void onSuccess(String string) {
							}
							@Override
							public void onFail(String failstring) {
							}
							@Override
							public void onError(VolleyError error) {
							}}, false, null);
					
				}
				//用户取消了支付
				 Toast.makeText(getApplication(), "支付取消！",Toast.LENGTH_SHORT).show();
				finish();
			}
			if(resp.errCode==0){
				//根据相关的标记  调整不同的页面  用户支付成功
				if(ShopApplication.payflag==1){
					Intent intent=new Intent(WXPayEntryActivity.this,OrderDetailActivity.class);
					intent.putExtra("id", ShopApplication.orderid);
					startActivity(intent);
					finish();
				}
				if(ShopApplication.payflag==2){
					finish();
				}
			}
			if(resp.errCode==-1){
				if(ShopApplication.payflag==1){
						VolleyUtil.sendStringRequestByGetToString(CommonVariable.PayErrorURL+ShopApplication.orderid, null, null,new  HttpBackBaseListener(){

							@Override
							public void onSuccess(String string) {
							}
							@Override
							public void onFail(String failstring) {
							}
							@Override
							public void onError(VolleyError error) {
							}}, false, null);
				}
			    Toast.makeText(getApplication(), "支付失败！",Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}
}