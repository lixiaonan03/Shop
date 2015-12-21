package com.xyyy.shop.wxapi;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.activity.MainActivity;
import com.xyyy.shop.activity.MemberCompleteActivity;
import com.xyyy.shop.db.Cart;
import com.xyyy.shop.db.CartDao;
import com.xyyy.shop.model.EnnCart;
import com.xyyy.shop.model.EnnCartDTO;
import com.xyyy.shop.model.EnnMember;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.toolUtil.StringUtils;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private Context context = WXEntryActivity.this;

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
	public void onReq(BaseReq arg0) {
		finish();
	}

	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
					Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
					break;
				}
				if(ConstantsAPI.COMMAND_SENDAUTH== resp.getType()){
					String code = ((SendAuth.Resp) resp).code;

					VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetMemberInfoForWxURL+code+"/APP", null, null, EnnMember.class, new HttpBackListener<EnnMember>() {

						@Override
						public void onSuccess(EnnMember t) {
							if(StringUtils.isNotBlank(t.getMembPhone())){
								Intent intent=new Intent(getApplicationContext(), MainActivity.class);
								intent.putExtra("flag", 4);
								ShopApplication.loginflag=2;
								ShopApplication.isLogin=true;
								ShopApplication.userinfo=t;
								ShopApplication.useridother=t.getMembId();
								ShopApplication.usernameOtherlogin=t.getMembNick();
								ShopApplication.userimgurlOtherlogin=t.getMembImg();
								ShopApplication.usersexOtherlogin=StrToNumber.strToint(t.getMembSex());
								sendcart(t.getMembId());
								startActivity(intent);
								finish();
							}else{
								//完善资料
								Intent intentcomplete=new Intent(getApplicationContext(), MemberCompleteActivity.class);
								intentcomplete.putExtra("memberid",t.getMembId());
								startActivity(intentcomplete);
								finish();
							}
						}

						@Override
						public void onFail(String failstring) {
							Toast.makeText(context, "微信登录失败！", Toast.LENGTH_LONG).show();
							finish();
						}

						@Override
						public void onError(VolleyError error) {
							Toast.makeText(context, "微信登录失败！", Toast.LENGTH_LONG).show();
							finish();
						}
					}, false, null);

				}
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				finish();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				Toast.makeText(context, "微信登录失败！", Toast.LENGTH_LONG).show();
				finish();
				break;
			default:
				break;
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
}