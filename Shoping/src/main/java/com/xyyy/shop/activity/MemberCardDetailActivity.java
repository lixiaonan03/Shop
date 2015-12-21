package com.xyyy.shop.activity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.model.EnnSysArea;
import com.xyyy.shop.model.IsDeliveNoticeDTO;
import com.xyyy.shop.model.MemCardVODetail;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 用户所绑定的会员卡的详情界面
 */
public class MemberCardDetailActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;// 顶部所显示的文字
	private int cardflag;// 卡状态 //0 尚未启用 1 使用中 2 已暂定 3 注销 4 已用完
	private int cradtype;// 卡类型 // 1 年卡 2 半年卡 3季卡 4 月卡 5 体验卡
	private int cradid;// 卡id
	private String cardcode;// 卡cardcode

	private int size;// 配送时间能选择的

	private RelativeLayout member_state;// 会员卡状态
	private TextView membertype;// 会员卡类型
	private TextView memberid;// 会员卡名称

	private LinearLayout tab_linview;// 头部tab切换
	private TextView membercard_money, membercard_setmeal;// 头部tab切换
	private RelativeLayout money_rel, setmal_rel;
	// 卡详情界面的控件
	private TextView card_remainmoney;
	private Button money_recharge;
	private Button money_gobuy;
	private RelativeLayout money_consumelist;

	private TextView card_typesize;// 配送规格
	private TextView allsize;// 总共配送的次数
	private TextView remaindersize;// 剩余配送的次数
	private TextView card_rate;// 配送的频次 2周1次 或其他

	private RelativeLayout card_time_rel;
	private TextView card_time;// 配送时间
	private RelativeLayout recevice_address_rel;
	private TextView recevice_address, recevice_person,
			recevice_addressprovice;
	private Button submit;// 底部最后一行的大按钮
	private LinearLayout userLin;
	private LinearLayout bottom_button;
	private Button update;// 使用中的会员卡的修改按钮
	private Button useing;// 使用中的会员卡的暂停按钮

	private CustomProgressDialog customProgressDialog;

	private MemCardVODetail currentobject;

	private Integer goodid;// 关联的商品id

	private int flag;// 1、卡详情点击 2、卡套餐点击
	private int saveflag;// 0 不保存 1需要保存
	private int isfreezeflag;// 会员卡冻结状态 0 未冻结 1冻结
	private TextView telephone_xiangqing;
	private TextView telephone_taocan;
	private int isfuwu;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mymembercarddetail);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("我的会员卡");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		customProgressDialog = new CustomProgressDialog(
				MemberCardDetailActivity.this, "正在加载......");
		cradid = getIntent().getIntExtra("cradid", 0);
		cradtype = getIntent().getIntExtra("cradtype", 0);
		cardflag = getIntent().getIntExtra("cardflag", 0);
		isfuwu = getIntent().getIntExtra("isfuwu", 0);
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

		// 头部切换头
		tab_linview = (LinearLayout) findViewById(R.id.tab_linview);
		membercard_money = (TextView) findViewById(R.id.membercard_money);
		membercard_setmeal = (TextView) findViewById(R.id.membercard_setmeal);
		money_rel = (RelativeLayout) findViewById(R.id.money_rel);
		setmal_rel = (RelativeLayout) findViewById(R.id.setmal_rel);
		membercard_money.setOnClickListener(viewclick);
		membercard_setmeal.setOnClickListener(viewclick);

		// 卡详情界面的控件
		card_remainmoney = (TextView) findViewById(R.id.card_remainmoney);
		money_recharge = (Button) findViewById(R.id.money_recharge);
		money_gobuy = (Button) findViewById(R.id.money_gobuy);
		money_consumelist = (RelativeLayout) findViewById(R.id.money_consumelist);
		money_recharge.setOnClickListener(viewclick);
		money_consumelist.setOnClickListener(viewclick);
		money_gobuy.setOnClickListener(viewclick);

		card_typesize = (TextView) findViewById(R.id.card_typesize);
		allsize = (TextView) findViewById(R.id.allsize);
		remaindersize = (TextView) findViewById(R.id.remaindersize);
		card_rate = (TextView) findViewById(R.id.card_rate);

		card_time_rel = (RelativeLayout) findViewById(R.id.card_time_rel);
		card_time_rel.setOnClickListener(viewclick);
		card_time = (TextView) findViewById(R.id.card_time);

		recevice_address_rel = (RelativeLayout) findViewById(R.id.recevice_address_rel);
		recevice_address_rel.setOnClickListener(viewclick);
		recevice_address = (TextView) findViewById(R.id.recevice_address);
		recevice_addressprovice = (TextView) findViewById(R.id.recevice_addressprovice);
		recevice_person = (TextView) findViewById(R.id.recevice_person);

		// 冻结卡联系电话
		telephone_xiangqing = (TextView) findViewById(R.id.xiangqing_telephone);
		telephone_taocan = (TextView) findViewById(R.id.taocan_telephone);

		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(viewclick);
		userLin = (LinearLayout) findViewById(R.id.userLin);
		bottom_button = (LinearLayout) findViewById(R.id.bottom_button);
		update = (Button) findViewById(R.id.update);
		update.setOnClickListener(viewclick);
		useing = (Button) findViewById(R.id.useing);
		useing.setOnClickListener(viewclick);
		if (cardflag == 1 || cardflag == 2) {
			userLin.setVisibility(View.VISIBLE);
			submit.setVisibility(View.GONE);
		} else {
			userLin.setVisibility(View.GONE);
			submit.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 顶部tab页改变之
	 */
	private void changetab() {
		membercard_money.setTextColor(getResources().getColor(
				R.color.order_textcolor_nochecked));
		membercard_setmeal.setTextColor(getResources().getColor(
				R.color.order_textcolor_nochecked));

		membercard_money
				.setBackgroundResource(R.drawable.membercard_detail_tabbackgroudnocheck);
		membercard_setmeal
				.setBackgroundResource(R.drawable.membercard_detail_tabbackgroudnocheck);

		switch (flag) {
			case 1:
				membercard_money.setTextColor(getResources().getColor(
						R.color.order_textcolor_checked));
				membercard_money
						.setBackgroundResource(R.drawable.membercard_detail_tabbackgroudcheck);

				money_rel.setVisibility(View.VISIBLE);
				setmal_rel.setVisibility(View.GONE);
				break;
			case 2:
				membercard_setmeal.setTextColor(getResources().getColor(
						R.color.order_textcolor_checked));
				membercard_setmeal
						.setBackgroundResource(R.drawable.membercard_detail_tabbackgroudcheck);

				setmal_rel.setVisibility(View.VISIBLE);
				money_rel.setVisibility(View.GONE);
				break;
			default:
				break;
		}
	}

	OnClickListener viewclick = new OnClickListener() {

		@Override
		public void onClick(View paramView) {
			// TODO 控件点击事件
			switch (paramView.getId()) {
			/*
			 * case R.id.presentmember_rel: if(cardflag!=3&&cardflag!=4){
			 * //赠送会员卡 Intent intent=new
			 * Intent(MemberCardDetailActivity.this,PresentMembercardActivity
			 * .class); startActivity(intent); } break;
			 */
				case R.id.membercard_money:
					// 卡详情
					if (flag == 1) {
						return;
					}
					flag = 1;
					changetab();

					break;
				case R.id.membercard_setmeal:
					// 卡套餐
					if (flag == 2) {
						return;
					}
					flag = 2;
					changetab();
					break;
				case R.id.money_recharge:
					// 卡充值
					if (isfreezeflag == 0) {
						Intent intentrecharge = new Intent(
								MemberCardDetailActivity.this,
								MembercardPayrechargeActivity.class);
						intentrecharge.putExtra("cardflag", cardflag);
						intentrecharge.putExtra("cardcode", cardcode);
						intentrecharge.putExtra("cardid", memberid.getText()
								.toString().trim());
						intentrecharge.putExtra("cardname", membertype.getText()
								.toString().trim());
						intentrecharge.putExtra("cardremainmoney", card_remainmoney
								.getText().toString().trim().substring(1));
						startActivity(intentrecharge);
					}
					break;
				case R.id.money_gobuy:
					// 去购买
					Intent money_gobuy = new Intent(MemberCardDetailActivity.this,
							ClassifyActivity.class);
					startActivity(money_gobuy);
					break;
				case R.id.money_consumelist:
					// 卡消费列表
					if (isfreezeflag == 0) {
						Intent intent = new Intent(MemberCardDetailActivity.this,
								MembercardRecordActivity.class);
						intent.putExtra("cardid", cradid + "");
						intent.putExtra("isfuwu", isfuwu);
						startActivity(intent);
					}

					break;
				case R.id.card_time_rel:
					if (isfreezeflag == 0) {
						if (cardflag != 3) {
							// 配送时间的布局
							Intent intenttime = new Intent(
									MemberCardDetailActivity.this,
									MembercardTimeActivity.class);
							intenttime.putExtra("size", size);
							startActivityForResult(intenttime, 01);
						}
					}

					break;
				case R.id.recevice_address_rel:
					if (isfreezeflag == 0) {
						if (cardflag != 3) {
							// 收货地址
							List<Integer> goodidlist = new ArrayList<Integer>();
							goodidlist.add(goodid);
							VolleyUtil.sendObjectByPostToList(
									CommonVariable.GetAdressBygoodidURL, null,
									goodidlist, EnnSysArea.class,
									new HttpBackListListener<EnnSysArea>() {

										@Override
										public void onSuccess(List<EnnSysArea> t) {
											// TODO Auto-generated method stub
											if(null!=t&&t.size()==1){
												if (t.get(0).getAreaId()==111111){
													//配送范围是全国
													Intent intentaddress = new Intent(
															MemberCardDetailActivity.this,
															UseradressActivity.class);
													intentaddress.putExtra("flag", 2);
													List<EnnSysArea>  all=new ArrayList<EnnSysArea>();
													intentaddress.putExtra("addresslist",
															(Serializable) all);
													startActivityForResult(intentaddress,
															02);
												}
											}else{
												Intent intentaddress = new Intent(
														MemberCardDetailActivity.this,
														UseradressActivity.class);
												intentaddress.putExtra("flag", 2);
												intentaddress.putExtra("addresslist",
														(Serializable) t);
												startActivityForResult(intentaddress,
														02);
											}

										}

										@Override
										public void onFail(String failstring) {
											Toast.makeText(
													MemberCardDetailActivity.this,
													"加载配送信息失败！", Toast.LENGTH_SHORT)
													.show();
										}

										@Override
										public void onError(VolleyError error) {
											Toast.makeText(
													MemberCardDetailActivity.this,
													"加载配送信息失败！", Toast.LENGTH_SHORT)
													.show();
										}
									}, false, null);

						}

					}
					break;
				case R.id.submit:
					// 底部按钮

					if (cardflag == 0) {
						// 尚未使用
						submit.setText("启用");

						currentobject.getEnnCard().setCardState("01");
						currentobject.getEnnCard().setStartTime(new java.sql.Timestamp(new java.util.Date().getTime()));
					}

					goupateCurrent();
					break;
				case R.id.update:
					// 使用中和暂停状态下的 保存按钮
					goupateCurrent();
					break;
				case R.id.useing:
					// 使用和暂停状态下的 修改状态的按钮
					if (cardflag == 1) {
						//暂停
						currentobject.getEnnCard().setPauseTime(new java.sql.Timestamp(new java.util.Date().getTime()));
						currentobject.getEnnCard().setPauseTimeEnd(null);
						currentobject.getEnnCard().setCardState("02");
					}
					if (cardflag == 2) {
						currentobject.getEnnCard().setCardState("01");
					}
					goupateCurrent();
					break;

				default:
					break;
			}
		}
	};

	/**
	 * 获取会员卡的详情信息
	 */
	private void initdata() {
		if (!customProgressDialog.isShowing())
			customProgressDialog.show();
		VolleyUtil.sendStringRequestByGetToBean(
				CommonVariable.GetMembercardDetailURL + cradid, null, null,
				MemCardVODetail.class, new HttpBackListener<MemCardVODetail>() {

					@Override
					public void onSuccess(MemCardVODetail t) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						currentobject = t;
						changeview(t);
					}

					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						Toast.makeText(MemberCardDetailActivity.this,
								"加载数据失败！", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(MemberCardDetailActivity.this,
								"加载数据失败！", Toast.LENGTH_SHORT).show();
					}
				}, false, null);

	}

	/**
	 * 向服务器提交当前修改的对象
	 */
	protected void goupateCurrent() {
		if (StringUtils.isBlank(currentobject.getEnnCardConfig()
				.getReceAddress())) {
			recevice_address.setText("请选择配送地址");
			Toast.makeText(MemberCardDetailActivity.this,
					"请选择配送地址！", Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.isBlank(currentobject.getEnnCardConfig()
				.getDeliveWeek())) {
			recevice_address.setText("请选择配送时间");
			Toast.makeText(MemberCardDetailActivity.this,
					"请选择配送时间！",Toast.LENGTH_SHORT).show();
			return;
		}
		/*if (null != currentobject.getEnnCardConfig().getCity()
				&& currentobject.getEnnCardConfig().getCity().equals("131000")) {
			if (null != currentobject.getEnnCardConfig().getDeliveWeek()
					&& currentobject.getEnnCardConfig().getDeliveWeek()
							.contains("3")) {
				Toast.makeText(MemberCardDetailActivity.this,
						"廊坊地区不支持周三配送，请重新选择！", 0).show();
				return;
			}
		}*/
		customProgressDialog.setContent("......正在提交");
		customProgressDialog.show();

		VolleyUtil.sendObjectByPostToString(
				CommonVariable.UpdateMembercardDetailURL, null, currentobject,
				new HttpBackBaseListener() {

					@Override
					public void onSuccess(String string) {

						if (saveflag == 1) {
							customProgressDialog.dismiss();
							AlertDialog.Builder builder = new AlertDialog.Builder(
									MemberCardDetailActivity.this);
							builder.setTitle("提示");
							builder.setCancelable(false);
							builder.setMessage("您的会员卡信息已保存!");
							builder.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();// 取消dialog，或dismiss掉
											saveflag = 0;
											useing.setBackgroundResource(R.drawable.login_button);
											useing.setClickable(true);
											update.setBackgroundResource(R.drawable.login_buttonap);
											update.setClickable(false);
											// initdata();
										}
									});
							if (!isFinishing()) {
								builder.create().show();
							}

						}
						if (saveflag == 0) {
							if(null!=currentobject&&null!=currentobject.getEnnCardType()&&currentobject.getEnnCardType().getCardKind().equals("01")){
								//判断是否是蔬菜卡
								if(cardflag==0||cardflag==2){
									int userid = 0;
									if (ShopApplication.isLogin) {
										if (ShopApplication.loginflag == 1) {
											userid = ShopApplication.userid;
										}
										if (ShopApplication.loginflag == 2) {
											userid = ShopApplication.useridother;
										}
									}
									VolleyUtil.sendStringRequestByGetToBean(CommonVariable.IsDelivenNoticeURL+userid, null, null, IsDeliveNoticeDTO.class, new HttpBackListener<IsDeliveNoticeDTO>() {

										@Override
										public void onSuccess(IsDeliveNoticeDTO t) {
											customProgressDialog.dismiss();
											if(t.getIsEnable()){
												AlertDialog.Builder builder = new AlertDialog.Builder(
														MemberCardDetailActivity.this);
												builder.setTitle("提示");
												builder.setCancelable(false);
												if(cardflag==0){
													builder.setMessage("会员卡启动成功,现在还在选菜阶段,您可以去配送预告选菜了!");
												}else if(cardflag==2){
													builder.setMessage("亲，您的会员卡已恢复配送！现在还在选菜阶段您可以去配送预告选菜了!");
												}

												builder.setPositiveButton("去选择",
														new DialogInterface.OnClickListener() {
															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																Intent intent=new Intent();
																intent.setClass(MemberCardDetailActivity.this, MydeliverynoticeActivity.class);
																startActivity(intent);
																finish();
															}
														});
												builder.setNegativeButton("关闭",
														new DialogInterface.OnClickListener() {
															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																dialog.dismiss();//取消dialog，或dismiss掉
																finish();
															}
														});
												if (!isFinishing()) {
													builder.create().show();
												}
											}else{
												AlertDialog.Builder builder = new AlertDialog.Builder(
														MemberCardDetailActivity.this);
												builder.setTitle("提示");
												builder.setCancelable(false);
												if(cardflag==0){
													builder.setMessage("会员卡启动成功，现在不在本周选菜阶段请您关注下周的选菜预告!");
												}else if(cardflag==2){
													builder.setMessage("亲，您的会员卡已恢复配送！现在不在本周选菜阶段请您关注下周的选菜预告!");
												}


												builder.setPositiveButton("关闭",
														new DialogInterface.OnClickListener() {
															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {
																dialog.dismiss();//取消dialog，或dismiss掉
																finish();
															}
														});
												if (!isFinishing()) {
													builder.create().show();
												}
											}
										}

										@Override
										public void onFail(String failstring) {
											customProgressDialog.dismiss();
										}

										@Override
										public void onError(VolleyError error) {
											customProgressDialog.dismiss();
										}
									}, false, null);
								}else{
									customProgressDialog.dismiss();
									AlertDialog.Builder builder = new AlertDialog.Builder(
											MemberCardDetailActivity.this);
									builder.setTitle("提示");
									builder.setCancelable(false);
									switch (cardflag) {
										case 1:
											builder.setMessage("您的会员卡已暂停!");
											break;


										default:
											break;
									}
									builder.setPositiveButton("确定",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													dialog.dismiss();// 取消dialog，或dismiss掉
													finish();
												}
											});
									if (!isFinishing()) {
										builder.create().show();
									}
								}
							}else{
								//不是蔬菜卡
								customProgressDialog.dismiss();
								AlertDialog.Builder builder = new AlertDialog.Builder(
										MemberCardDetailActivity.this);
								builder.setTitle("提示");
								builder.setCancelable(false);
								switch (cardflag) {
									case 0:
										builder.setMessage("您的会员卡已启用!");
										break;
									case 1:
										builder.setMessage("您的会员卡已暂停!");
										break;
									case 2:
										builder.setMessage("您的会员卡已恢复!");
										break;

									default:
										break;
								}
								builder.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												dialog.dismiss();// 取消dialog，或dismiss掉
												finish();
											}
										});
								if (!isFinishing()) {
									builder.create().show();
								}
							}

						}
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(MemberCardDetailActivity.this, "修改出错！",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(MemberCardDetailActivity.this, "修改出错！",
								Toast.LENGTH_SHORT).show();
					}
				}, false, null);

	}

	/**
	 * 根据数据结果 去更新界面
	 *
	 * @param t
	 *            MemCardVODetail 对象的数据结果
	 */
	protected void changeview(MemCardVODetail t) {
		goodid = t.getEnnCard().getGoodsId();
		if (null != t.getEnnCard() && null != t.getEnnCard().getCardCode()) {
			cardcode = t.getEnnCard().getCardCode();
		}

		// 剩余金额
		if (null != t.getEnnCard() && null != t.getEnnCard().getCardRemain()) {
			card_remainmoney.setText("￥ "
					+ t.getEnnCard().getCardRemain()
					.setScale(2, BigDecimal.ROUND_HALF_UP));
		} else {
			card_remainmoney.setText("￥0.00");
		}
		int cardstate = StrToNumber.strToint(t.getEnnCard().getCardState());
		cardflag = cardstate;
		switch (cardstate) {
			case 0:
				// 待启用
				top_text.setText("未启用");
				submit.setText("启用");
				userLin.setVisibility(View.GONE);
				submit.setVisibility(View.VISIBLE);
				member_state
						.setBackgroundResource(R.drawable.membercard_detail_state01);
				break;
			case 1:
				// 启用
				top_text.setText("启用中");
				update.setText("保存");
				useing.setText("暂停");
				update.setClickable(false);
				userLin.setVisibility(View.VISIBLE);
				submit.setVisibility(View.GONE);
				member_state
						.setBackgroundResource(R.drawable.membercard_detail_state02);
				break;
			case 2:
				// 暂停
				top_text.setText("暂停中");
				update.setText("保存");
				useing.setText("恢复配送");
				update.setClickable(false);
				userLin.setVisibility(View.VISIBLE);
				submit.setVisibility(View.GONE);
				member_state
						.setBackgroundResource(R.drawable.membercard_detail_state03);
				break;
			case 3:
				// 注销
				top_text.setText("已注销");
				userLin.setVisibility(View.GONE);
				submit.setVisibility(View.GONE);
				break;
			case 4:
				// 已完成
				top_text.setText("已用完");
				userLin.setVisibility(View.GONE);
				submit.setVisibility(View.GONE);
				member_state
						.setBackgroundResource(R.drawable.membercard_detail_state04);
				break;
			default:
				break;
		}
		// 判断是不是服务型会员卡
		if (null != t.getEnnCardType().getServiceType()
				&& t.getEnnCardType().getServiceType().equals("02")) {
			// 非服务型会员卡
			tab_linview.setVisibility(View.GONE);
			setmal_rel.setVisibility(View.GONE);
			top_text.setText("启用中");

		}
		// 判断是不是服务型会员卡
		if (null != t.getEnnCardType().getServiceType()
				&& t.getEnnCardType().getServiceType().equals("02")) {
			// 非服务型会员卡
			tab_linview.setVisibility(View.GONE);
			setmal_rel.setVisibility(View.GONE);
			member_state
					.setBackgroundResource(R.drawable.membercard_detail_state02);
			top_text.setText("启用中");
		}
		// 已冻结判断
		if (null != t.getEnnCard() && null != t.getEnnCard().getIsFreeze()) {
			if (t.getEnnCard().getIsFreeze().equals("1")) {
				isfreezeflag = 1;
				// 改卡已冻结
				member_state
						.setBackgroundResource(R.drawable.membercard_detail_state05);
				bottom_button.setVisibility(View.GONE);
				money_gobuy.setVisibility(View.GONE);
				telephone_xiangqing.setVisibility(View.VISIBLE);
				telephone_taocan.setVisibility(View.VISIBLE);
			}
		}

		if (null != t.getEnnCard() && null != t.getEnnCard().getCardCode()) {
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
		if (null != t.getEnnCardType().getServiceType()
				&& t.getEnnCardType().getServiceType().equals("02")) {
			// 非服务型会员卡
			membertype.setText("非服务型会员卡");
		} else {
			membertype.setText(membertypestring.toString());
		}

		card_typesize.setText("规格：" + t.getEnnCardType().getDeliveStand());
		allsize.setText(t.getEnnCard().getTimes() + "");
		remaindersize.setText(t.getEnnCard().getCardRenum() + "");
		if (null == t.getEnnCardType().getDeliveFreq()) {
			t.getEnnCardType().setDeliveFreq(0);
		}
		if (null == t.getEnnCardConfig().getDeliveFreq()) {
			t.getEnnCardConfig().setDeliveFreq(0);
		}
		size = t.getEnnCardType().getDeliveFreq();
		if (null == t.getEnnCardType().getInterWeek()) {
			t.getEnnCardType().setInterWeek(0);
		}
		if (null == t.getEnnCardConfig().getInterWeek()) {
			t.getEnnCardConfig().setInterWeek(0);
		}
		if (null == t.getEnnCardConfig().getDeliveWeek()) {
			t.getEnnCardConfig().setDeliveWeek("");
		}
		if (t.getEnnCardType().getInterWeek() == 1) {
			card_rate.setText("每周" + t.getEnnCardType().getDeliveFreq() + "次");
		} else {
			card_rate.setText(t.getEnnCardType().getInterWeek() + "周"
					+ t.getEnnCardType().getDeliveFreq() + "次");
		}

		card_time.setText(changetimes(t.getEnnCardConfig().getDeliveWeek()));

		if (StringUtils.isBlank(t.getEnnCardConfig().getReceAddress())) {
			recevice_address.setText("请选择配送地址");
		} else {
			VolleyUtil.sendStringRequestByGetToBean(
					CommonVariable.GetAreaDetailURL
							+ t.getEnnCardConfig().getCountry(), null, null,
					EnnSysArea.class, new HttpBackListener<EnnSysArea>() {

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
			// recevice_addressprovice.setText(t.getEnnCardConfig().getProvince()+" "+t.getEnnCardConfig().getCity()+" "+t.getEnnCardConfig().getCountry());
			recevice_address.setText(t.getEnnCardConfig().getReceAddress());
			recevice_person.setText(t.getEnnCardConfig().getReceName() + " "
					+ t.getEnnCardConfig().getRecePhone());
		}

	}

	/**
	 *
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == 1) {
			String timestrs = data.getStringExtra("timestrs");
			currentobject.getEnnCardConfig().setDeliveWeek(timestrs);
			card_time.setText(changetimes(timestrs));
			if (cardflag == 1 || cardflag == 2) {
				saveflag = 1;
			}
			update.setBackgroundResource(R.drawable.login_button);
			update.setClickable(true);
			useing.setBackgroundResource(R.drawable.login_buttonap);
			useing.setClickable(false);
		}
		if (requestCode == 2 && resultCode == 2) {
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
			VolleyUtil.sendStringRequestByGetToBean(
					CommonVariable.GetAreaDetailURL + country, null, null,
					EnnSysArea.class, new HttpBackListener<EnnSysArea>() {

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
			// recevice_addressprovice.setText(province+" "+city+" "+country);
			recevice_address.setText(receAddress);
			recevice_person.setText(recename + " " + recephone);
			if (cardflag == 1 || cardflag == 2) {
				saveflag = 1;
			}
			update.setBackgroundResource(R.drawable.login_button);
			update.setClickable(true);
			useing.setBackgroundResource(R.drawable.login_buttonap);
			useing.setClickable(false);
		}
	}

	/**
	 * 改变配送时间
	 */
	String changetimes(String timestr) {
		if (StringUtils.isBlank(timestr)) {
			return "";
		}
		String[] strs = timestr.split(",");
		StringBuilder strtext = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals("1")) {
				strtext.append("周一，");
			}
			if (strs[i].equals("2")) {
				strtext.append("周二，");
			}
			if (strs[i].equals("3")) {
				strtext.append("周三，");
			}
			if (strs[i].equals("4")) {
				strtext.append("周四，");
			}
			if (strs[i].equals("5")) {
				strtext.append("周五，");
			}
			if (strs[i].equals("6")) {
				strtext.append("周六，");
			}
			if (strs[i].equals("7")) {
				strtext.append("周日，");
			}
		}
		return strtext.subSequence(0, strtext.length() - 1).toString();
	}
	@Override
	protected void onResume() {

		super.onResume();
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {

		super.onPause();
		/**
		 * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
		 */
		StatService.onPause(this);
	}
}
