package com.xyyy.shop.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.MygiftItemgooditemAdapter;
import com.xyyy.shop.model.EnnOrder;
import com.xyyy.shop.model.EnnSysArea;
import com.xyyy.shop.model.GiftCardDetailVO;
import com.xyyy.shop.model.GiftCardItem;
import com.xyyy.shop.model.MemCardVODetail;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.DateUtil;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.MyListView;

/**
 * 用户的礼品卡界面
 */
public class GiftDetailActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;// 顶部所显示的文字
	private int cardflag;// 卡状态 
	private int cradtype;
	private int cradid;

	private RelativeLayout member_state;// 卡状态
	private TextView membertype;// 卡类型
	private TextView memberid;// 卡名称
	private TextView gift_statetext;// 卡状态文字
	private TextView gift_statetime;// 卡日期

	private RelativeLayout recevice_address_rel;
	private TextView recevice_address, recevice_person,
			recevice_addressprovice;
	private Button button;// 底部最后一行的大按钮
	// 备注
	private RelativeLayout remark_rel;
	private TextView remarktext;
	private String remark;// 备注信息

	private CustomProgressDialog customProgressDialog;

	private GiftCardDetailVO currentobject;
	private MyListView listview;
	private MygiftItemgooditemAdapter adapter;
	private List<GiftCardItem> list = new ArrayList<GiftCardItem>();
    
	private List<Integer> goodlist=new ArrayList<Integer>();
	
	private Integer orderid;// 启用完之后的订单
	private String cardcode;//礼品卡cardcode
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mygiftdetail);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("我的礼品卡");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		customProgressDialog = new CustomProgressDialog(
				GiftDetailActivity.this, "正在加载......");
		cradid = getIntent().getIntExtra("cradid", 0);
		cradtype = getIntent().getIntExtra("cradtype", 0);
		cardflag = getIntent().getIntExtra("cardflag", 0);
		initView();
		initdata();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		member_state = (RelativeLayout) findViewById(R.id.member_state);
		membertype = (TextView) findViewById(R.id.membertype);
		memberid = (TextView) findViewById(R.id.memberid);

		gift_statetext = (TextView) findViewById(R.id.gift_statetext);
		gift_statetime = (TextView) findViewById(R.id.gift_statetime);

		listview = (MyListView) findViewById(R.id.listview);
		adapter = new MygiftItemgooditemAdapter(GiftDetailActivity.this, list);
		listview.setAdapter(adapter);
		listview.setFocusable(false);

		recevice_address_rel = (RelativeLayout) findViewById(R.id.recevice_address_rel);
		recevice_address_rel.setOnClickListener(viewclick);
		recevice_address = (TextView) findViewById(R.id.recevice_address);
		recevice_addressprovice = (TextView) findViewById(R.id.recevice_addressprovice);
		recevice_person = (TextView) findViewById(R.id.recevice_person);

		remark_rel = (RelativeLayout) findViewById(R.id.remark_rel);
		remark_rel.setOnClickListener(viewclick);
		remarktext = (TextView) findViewById(R.id.remarktext);

		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(viewclick);
	}

	OnClickListener viewclick = new OnClickListener() {

		@Override
		public void onClick(View paramView) {
			switch (paramView.getId()) {
			case R.id.recevice_address_rel:
				if (cardflag == 10) {
					// 收货地址
					
					VolleyUtil.sendObjectByPostToList(CommonVariable.GetAdressBygoodidURL, null, goodlist, EnnSysArea.class, new HttpBackListListener<EnnSysArea>() {

						@Override
						public void onSuccess(List<EnnSysArea> t) {
							if(null!=t&&t.size()==1){
								if (t.get(0).getAreaId()==111111){
									//配送范围是全国
									Intent intentaddress = new Intent(
											GiftDetailActivity.this,
											UseradressActivity.class);
									intentaddress.putExtra("flag",4);
									List<EnnSysArea>  all=new ArrayList<EnnSysArea>();
									intentaddress.putExtra("addresslist",
											(Serializable) all);
									startActivityForResult(intentaddress,
											04);
								}
							}else{
								Intent intentaddress = new Intent(
										GiftDetailActivity.this,
										UseradressActivity.class);
								intentaddress.putExtra("flag",4);
								intentaddress.putExtra("addresslist",
										(Serializable) t);
								startActivityForResult(intentaddress,
										04);
							}
						}

						@Override
						public void onFail(String failstring) {
							Toast.makeText(GiftDetailActivity.this, "加载配送信息失败！",
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onError(VolleyError error) {
							Toast.makeText(GiftDetailActivity.this, "加载配送信息失败！",
									Toast.LENGTH_SHORT).show();
						}
					}, false, null);	
					
				}
				break;
			case R.id.remark_rel:
				if (cardflag == 10) {
					Intent intentremark = new Intent(GiftDetailActivity.this,
							AdduserremarkActivity.class);
					intentremark.putExtra("flag", 3);
					if(StringUtils.isNotBlank(remark)){
						intentremark.putExtra("remark",remark);
					}
					startActivityForResult(intentremark, 04);
				}
				break;
			case R.id.button:
				// 底部按钮
				if (cardflag == 10) {
					// 尚未使用
					goupateCurrent();
				}
				if (cardflag == 11) {
					// 已使用
					Intent intentorder = new Intent(GiftDetailActivity.this,
							OrderDetailActivity.class);
					intentorder.putExtra("flag", 1);
					intentorder.putExtra("cardcode", cardcode);
					startActivity(intentorder);
				}
				break;

			default:
				break;
			}
		}
	};
	/**
	 * 获取礼品卡的详情信息
	 */
	private void initdata() {
		if (!customProgressDialog.isShowing())
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
		VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetGiftDetailURL
				+ cradid + "/" + userid, null, null, GiftCardDetailVO.class,
				new HttpBackListener<GiftCardDetailVO>() {

					@Override
					public void onSuccess(GiftCardDetailVO t) {
						customProgressDialog.dismiss();
						currentobject = t;
						changeview(t);
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(GiftDetailActivity.this, "加载数据失败！",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(GiftDetailActivity.this, "加载数据失败！",
								Toast.LENGTH_SHORT).show();
					}
				}, false, null);

	}

	/**
	 * 启用当前的会员卡对象
	 */
	protected void goupateCurrent() {
		
		//TODO 判断收货地址
		if(StringUtils.isBlank(currentobject.getEnnCardConfig().getCountry())){
			Toast.makeText(GiftDetailActivity.this,
					"请选择收货地址！", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		customProgressDialog.setContent("......正在启用");
		customProgressDialog.show();
		MemCardVODetail vo=new MemCardVODetail();
		vo.setEnnCard(currentobject.getEnnCard());
		vo.setEnnCardConfig(currentobject.getEnnCardConfig());
		vo.setEnnCardType(currentobject.getEnnCardType());
		VolleyUtil.sendObjectByPostToString(
				CommonVariable.UpdateMembercardDetailURL, null, vo,
				new HttpBackBaseListener() {

					@Override
					public void onSuccess(String string) {
						customProgressDialog.dismiss();
						int userid = 0;
						if (ShopApplication.isLogin) {
							if (ShopApplication.loginflag == 1) {
								userid = ShopApplication.userid;
							}
							if (ShopApplication.loginflag == 2) {
								userid = ShopApplication.useridother;
							}
						}
						VolleyUtil.sendStringRequestByGetToBean(
								CommonVariable.UpdateGiftStateURL + cradid
										+ "/" + userid, null, null, EnnOrder.class,
								new HttpBackListener<EnnOrder>() {
									@Override
									public void onSuccess(EnnOrder t) {
										customProgressDialog.dismiss();
										 if (null != t) {
												orderid = t.getOrderId();
											}
										AlertDialog.Builder builder = new AlertDialog.Builder(GiftDetailActivity.this);
										builder.setTitle("提示");
										builder.setCancelable(false);
										builder.setMessage("您的礼品卡兑换成功!");
								        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								            @Override
								            public void onClick(DialogInterface dialog, int which) {
								               dialog.dismiss();//取消dialog，或dismiss掉
												initdata();
								            }
								        });
								        if (!isFinishing()) {
								            builder.create().show();
								        }
										
									}

									@Override
									public void onFail(String failstring) {
										customProgressDialog.dismiss();
										Toast.makeText(GiftDetailActivity.this,
												"启用失败！", Toast.LENGTH_SHORT)
												.show();
									}

									@Override
									public void onError(VolleyError error) {
										customProgressDialog.dismiss();
										Toast.makeText(GiftDetailActivity.this,
												"启用失败！", Toast.LENGTH_SHORT)
												.show();
									}
								}, false, null);
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(GiftDetailActivity.this, "启用失败！", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(GiftDetailActivity.this, "启用失败！",Toast.LENGTH_SHORT)
								.show();
					}
				}, false, null);

	}

	/**
	 * 根据数据结果 去更新界面
	 * 
	 * @param t
	 *            GiftCardDetailVO 对象的数据结果
	 */
	protected void changeview(GiftCardDetailVO t) {
		int cardstate = StrToNumber.strToint(t.getEnnCard().getCardState());
		cardflag = cardstate;
		// TODO 根据状态改变界面显示
		switch (cardstate) {
		case 10:
			// 待启用
			top_text.setText("未启用");
			gift_statetext.setText("未启用");
			button.setText("启用");
			button.setVisibility(View.VISIBLE);
			member_state.setBackgroundResource(R.drawable.membercard_detail_state01);
			break;
		case 11:
			// 启用
			top_text.setText("已使用");
			button.setText("查看详情");
			button.setVisibility(View.VISIBLE);
			member_state.setBackgroundResource(R.drawable.membercard_detail_state02);
			break;
		case 12:
			// 过期
			top_text.setText("已过期");
			button.setVisibility(View.GONE);
			member_state.setBackgroundResource(R.drawable.membercard_detail_state05);
			break;
		}
		if (null != t.getEnnCard() && null != t.getEnnCard().getCardCode()) {
			cardcode=t.getEnnCard().getCardCode();
			memberid.setText(t.getEnnCard().getCardCode());
		} else {
			memberid.setText("NO.0");
		}
		StringBuilder membertypestring = new StringBuilder();
		if (null != t.getEnnCardType()
				&& null != t.getEnnCardType().getCardKind()) {
			int cardkind = StrToNumber.strToint(t.getEnnCardType()
					.getCardKind());
			switch (cardkind) {
			case 1:
				membertypestring.append("安全蔬菜");
				break;
			case 2:
				membertypestring.append("生态柴鸡蛋");
				break;
			case 3:
				membertypestring.append("生态猪肉");
				break;
			case 4:
				membertypestring.append("生态散养柴鸡");
				break;
			case 5:
				membertypestring.append("生态大米");
				break;
			case 6:
				membertypestring.append("生态杂粮");
				break;
			case 10:
				membertypestring.append("礼品卡");
				break;
			default:
				break;
			}
		}
		if (null != t.getEnnCardType()
				&& null != t.getEnnCardType().getCardType()) {
			int cardtype = StrToNumber.strToint(t.getEnnCardType()
					.getCardType());
			switch (cardtype) {
			case 1:
				membertypestring.append("年卡");
				break;
			case 2:
				membertypestring.append("半年卡");
				break;
			case 3:
				membertypestring.append("季卡");
				break;
			default:
				break;
			}
		}
		if (null != t.getEnnCardType()
				&& null != t.getEnnCardType().getIsElect()) {
			int iselect = StrToNumber.strToint(t.getEnnCardType().getIsElect());
			switch (iselect) {
			case 0:
				membertypestring.append("(实体)");
				break;
			case 1:
				membertypestring.append("(电子)");
				break;
			default:
				break;
			}
		}
		membertype.setText(membertypestring.toString());

		// 截止日期
		if (null != t.getEnnCard() && null != t.getEnnCard().getVaildEndtime()) {
			gift_statetime.setText("有效期:"
					+ DateUtil.getDate(t.getEnnCard().getVaildEndtime()));
		} else {
			gift_statetime.setText("");
		}
		// 商品列表
		list = t.getGiftCardItems();
		if (null != list) {
			for (GiftCardItem i : list) {
				
				goodlist.add(i.getEnnGoods().getGoodsId());
			}
			adapter.set_list(list);
			adapter.notifyDataSetChanged();
		}
		
		// 配送地址
		if (StringUtils.isBlank(t.getEnnCardConfig().getReceAddress())) {
			recevice_address.setText("请选择配送地址");
		} else {
			VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetAreaDetailURL+t.getEnnCardConfig().getCountry(), null, null, EnnSysArea.class, new HttpBackListener<EnnSysArea>() {

				@Override
				public void onSuccess(EnnSysArea t) {
					recevice_addressprovice.setText(t.getAreaAllName());
				}

				@Override
				public void onFail(String failstring) {
					recevice_addressprovice.setText("");
				}

				@Override
				public void onError(VolleyError error) {
					recevice_addressprovice.setText("");
				}
			}, false, null);
			recevice_address.setText(t.getEnnCardConfig().getReceAddress());
			recevice_person.setText(t.getEnnCardConfig().getReceName() + " "
					+ t.getEnnCardConfig().getRecePhone());
		}
		// 备注
		if (null != t.getEnnCardConfig()
				&& StringUtils.isNotBlank(t.getEnnCardConfig().getRemark())) {
			remarktext.setText(t.getEnnCardConfig().getRemark());
		} else {
			remarktext.setText("无");
		}
	}
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 返回处理
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 04 && resultCode == 04) {
			String province = data.getStringExtra("province");
			String city = data.getStringExtra("city");
			String country = data.getStringExtra("country");
			String receAddress = data.getStringExtra("receAddress");
			String recename = data.getStringExtra("recename");
			String recephone = data.getStringExtra("recephone");
			currentobject.getEnnCardConfig().setProvince(province);
			currentobject.getEnnCardConfig().setCity(city);
			currentobject.getEnnCardConfig().setCountry(country);
			currentobject.getEnnCardConfig().setReceAddress(receAddress);

			currentobject.getEnnCardConfig().setReceName(recename);
			currentobject.getEnnCardConfig().setRecePhone(recephone);

			VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetAreaDetailURL+country, null, null, EnnSysArea.class, new HttpBackListener<EnnSysArea>() {

				@Override
				public void onSuccess(EnnSysArea t) {
					recevice_addressprovice.setText(t.getAreaAllName());
				}

				@Override
				public void onFail(String failstring) {
					recevice_addressprovice.setText("");
				}

				@Override
				public void onError(VolleyError error) {
					recevice_addressprovice.setText("");
				}
			}, false, null);
			//recevice_addressprovice.setText(province+" "+city+" "+country);
			recevice_address.setText(receAddress);
			recevice_person.setText(recename + " " + recephone);
		}
		if (requestCode == 04 && resultCode == 03) {
			// 用户备注信息
			String remarkstr = data.getStringExtra("remarkstr");
			currentobject.getEnnCardConfig().setRemark(remarkstr);
			remarktext.setText(remarkstr);
			remark = remarkstr;
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
