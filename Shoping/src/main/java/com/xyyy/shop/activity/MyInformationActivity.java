package com.xyyy.shop.activity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.activity.PhotoPickActivity.PhotoCrop;
import com.xyyy.shop.model.EnnMember;
import com.xyyy.shop.toolUtil.BitmapUtil;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.toolUtil.UploadUtil;
import com.xyyy.shop.toolUtil.UploadUtil.OnUploadProcessListener;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.RoundImageView;

/**
 * 我的账户信息界面
 */
public class MyInformationActivity extends BaseActivity {

	private ImageView top_back;
	private RelativeLayout userimgRel;// 用户头像布局
	private RelativeLayout usernameRel;// 用户昵称布局
	private RelativeLayout usersexRel;// 用户性别布局
	private RelativeLayout userbirthRel;// 用户出生日期布局
	private RelativeLayout useraccountRel;// 用户账户安全布局
	private RelativeLayout useraddressRel;// 用户收货地址布局
	private RoundImageView userimg;// 用户头像
	private TextView username;// 用户昵称
	private TextView usersex;// 用户性别
	private TextView userbirth;// 用户出生日期
	private CustomProgressDialog customProgressDialog;
	private RelativeLayout membernameRel;
	private TextView membername;
	private RelativeLayout memberphoneRel;
	private TextView memberphone;
	private Button logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinformation);
		top_back = (ImageView) findViewById(R.id.top_back);

		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		customProgressDialog = new CustomProgressDialog(
				MyInformationActivity.this, "正在更改......");
		initview();
	}

	// 初始化控件
	private void initview() {
		userimgRel = (RelativeLayout) findViewById(R.id.userimgRel);
		userimg = (RoundImageView) findViewById(R.id.userimg);
		usernameRel = (RelativeLayout) findViewById(R.id.usernameRel);
		username = (TextView) findViewById(R.id.username);
		usersexRel = (RelativeLayout) findViewById(R.id.usersexRel);
		usersex = (TextView) findViewById(R.id.usersex);
		userbirthRel = (RelativeLayout) findViewById(R.id.userbirthRel);
		userbirth = (TextView) findViewById(R.id.userbirth);

		membernameRel = (RelativeLayout) findViewById(R.id.membernameRel);
		membername = (TextView) findViewById(R.id.membername);
		memberphoneRel = (RelativeLayout) findViewById(R.id.memberphoneRel);
		memberphone = (TextView) findViewById(R.id.memberphone);

		useraccountRel = (RelativeLayout) findViewById(R.id.useraccountRel);
		useraddressRel = (RelativeLayout) findViewById(R.id.useraddressRel);


		logout=(Button)findViewById(R.id.logout);

		userimgRel.setOnClickListener(new ViewOnClickListener());
		usernameRel.setOnClickListener(new ViewOnClickListener());
		usersexRel.setOnClickListener(new ViewOnClickListener());
		userbirthRel.setOnClickListener(new ViewOnClickListener());
		useraccountRel.setOnClickListener(new ViewOnClickListener());
		useraddressRel.setOnClickListener(new ViewOnClickListener());

		membernameRel.setOnClickListener(new ViewOnClickListener());
		memberphoneRel.setOnClickListener(new ViewOnClickListener());

		logout.setOnClickListener(new ViewOnClickListener());

		changeuserinfo();
	}

	/**
	 * 用户信息的更新
	 */
	private void changeuserinfo() {
		//TODO  用户信息修改的地方
		if (ShopApplication.isLogin) {
			// 设置登录完成的信息
			if (ShopApplication.loginflag == 1) {
				// 原生登录
				if (null != ShopApplication.userimgurlrlogin) {
					DisplayImageOptions options = new DisplayImageOptions.Builder()
							.showImageOnLoading(R.drawable.user_img)         // 设置图片下载期间显示的图片
							.showImageForEmptyUri(R.drawable.user_img)  // 设置图片Uri为空或是错误的时候显示的图片
							.showImageOnFail(R.drawable.user_img)       // 设置图片加载或解码过程中发生错误显示的图片
									//.cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
							.build();                                   // 创建配置过得DisplayImageOption对象
					ImageLoader.getInstance().displayImage(ShopApplication.userimgurlrlogin, userimg,options);
				}
				//设置昵称
				if (StringUtils.isNotBlank(ShopApplication.usernamelogin)) {
					username.setText(ShopApplication.usernamelogin);
				}
				//设置性别
				if(null!=ShopApplication.userinfo&&null!=ShopApplication.userinfo.getMembSex()){
					switch (StrToNumber.strToint(ShopApplication.userinfo.getMembSex())) {
						case 0:
							usersex.setText("保密");
							break;
						case 1:
							usersex.setText("男");
							break;
						case 2:
							usersex.setText("女");
							break;

						default:
							break;
					}
				}else{
					usersex.setText("保密");
				}

			}
			if (ShopApplication.loginflag == 2) {
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.showImageOnLoading(R.drawable.user_img)         // 设置图片下载期间显示的图片
						.showImageForEmptyUri(R.drawable.user_img)  // 设置图片Uri为空或是错误的时候显示的图片
						.showImageOnFail(R.drawable.user_img)       // 设置图片加载或解码过程中发生错误显示的图片
						.cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
						.build();                                   // 创建配置过得DisplayImageOption对象
				ImageLoader.getInstance().displayImage(ShopApplication.userimgurlOtherlogin, userimg,options);


				if (StringUtils.isNotBlank(ShopApplication.usernameOtherlogin)) {
					username.setText(ShopApplication.usernameOtherlogin);
				}
				switch (ShopApplication.usersexOtherlogin) {
					case 0:
						usersex.setText("保密");
						break;
					case 1:
						usersex.setText("男");
						break;
					case 2:
						usersex.setText("女");
						break;

					default:
						break;
				}
			}
			//设置出生日期
			if(null!=ShopApplication.userinfo&&null!=ShopApplication.userinfo.getMembBirth()){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Timestamp time = ShopApplication.userinfo.getMembBirth();
				String str = format.format(time);
				userbirth.setText(str);
			}
			//设置会员姓名
			if(null!=ShopApplication.userinfo&&null!=ShopApplication.userinfo.getMembName()){
				membername.setText(ShopApplication.userinfo.getMembName());
			}
			//设置会员手机号
			if(null!=ShopApplication.userinfo&&null!=ShopApplication.userinfo.getMembPhone()){
				String phonenumber;
				if(ShopApplication.userinfo.getMembPhone().trim().length()==11){
					phonenumber=ShopApplication.userinfo.getMembPhone().trim().substring(0,3)+"****"+ShopApplication.userinfo.getMembPhone().trim().substring(7);
					memberphone.setText(phonenumber);
				}else{
					memberphone.setText(ShopApplication.userinfo.getMembPhone().trim());
				}
			}
		}
	}

	private class ViewOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			int id = arg0.getId();
			Intent intent = new Intent();

			switch (id) {
				case R.id.userimgRel:
					// 用户头像
					if(ShopApplication.loginflag==1){
						onClick_Img();
					}
					break;
				case R.id.usernameRel:
					// 用户昵称
					if(ShopApplication.loginflag==1){
						intent.setClass(MyInformationActivity.this,
								UsernameActivity.class);
						startActivityForResult(intent, 2);
					}
					break;
				case R.id.usersexRel:
					// 用户性别
					if(ShopApplication.loginflag==1){
						intent.setClass(MyInformationActivity.this,
								UsersexActivity.class);
						//TODO 用户性别传入数据
						startActivityForResult(intent, 3);
					}
					break;
				case R.id.membernameRel:
					//会员姓名
					intent.setClass(MyInformationActivity.this,
							MembernameActivity.class);
					//TODO 用户性别传入数据
					startActivityForResult(intent, 4);
					break;
				case R.id.memberphoneRel:
					// 会员手机
					intent.setClass(MyInformationActivity.this,
							UserchangephoneTwoActivity.class);
					startActivityForResult(intent, 5);
					break;
				case R.id.userbirthRel:
					// 用户出生日期
					Calendar calendar = Calendar.getInstance();
					final DatePickerDialog dialog = new DatePickerDialog(
							MyInformationActivity.this, null,
							calendar.get(Calendar.YEAR),
							calendar.get(Calendar.MONTH),
							calendar.get(Calendar.DAY_OF_MONTH));
					dialog.setCanceledOnTouchOutside(true);
					//手动设置按钮
					dialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
						@SuppressLint("NewApi")
						@Override
						public void onClick(DialogInterface dialoginter, int which) {
							//通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
							DatePicker datePicker = dialog.getDatePicker();
							int year = datePicker.getYear();
							int month = datePicker.getMonth();
							int day = datePicker.getDayOfMonth();
							// Calendar月份是从0开始,所以month要加1
							EnnMember user = new EnnMember();
							int userid = 0;
							if (ShopApplication.isLogin) {
								if (ShopApplication.loginflag == 1) {
									userid = ShopApplication.userid;
								}
								if(ShopApplication.loginflag == 2){
									userid = ShopApplication.useridother;
								}
							}
							user.setMembId(userid);
							final String str = "" + year + "-" + (month + 1) + "-" + day;
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							java.util.Date d = null;
							try {
								d = format.parse(str);
							} catch (Exception e) {
								e.printStackTrace();
							}
							final java.sql.Timestamp time = new java.sql.Timestamp(d.getTime());
							user.setMembBirth(time);
							customProgressDialog.show();
							VolleyUtil.sendObjectByPostToString(
									CommonVariable.UpdateMemberinfoURL, null, user,
									new HttpBackBaseListener() {

										@Override
										public void onSuccess(String string) {
											customProgressDialog.dismiss();
											userbirth.setText(str);
											if(ShopApplication.loginflag==1){
												ShopApplication.userinfo.setMembBirth(time);
											}
											if(ShopApplication.loginflag==2){
												ShopApplication.userbirthother=str;
											}

										}

										@Override
										public void onFail(String failstring) {
											customProgressDialog.dismiss();
											Toast.makeText(MyInformationActivity.this, "修改失败！",
													Toast.LENGTH_SHORT).show();
										}

										@Override
										public void onError(VolleyError error) {
											customProgressDialog.dismiss();
											Toast.makeText(MyInformationActivity.this, "修改失败！",
													Toast.LENGTH_SHORT).show();
										}
									}, false, null);
						}
					});
					//取消按钮，如果不需要直接不设置即可
					dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					dialog.show();
					break;
				case R.id.useraccountRel:
					// 用户账户安全
					intent.setClass(MyInformationActivity.this,
							UseracountsafeActivity.class);
					startActivity(intent);
					break;
				case R.id.useraddressRel:
					// 用户收货地址
					intent.setClass(MyInformationActivity.this,
							UseradressActivity.class);
					startActivity(intent);
					break;
				case R.id.logout:
					// 退出登录
					StatService.onEvent(MyInformationActivity.this, "logout", "退出登录");
					if (ShopApplication.isLogin) {
						ShopApplication.isLogin = false;
						if(ShopApplication.loginflag==1){
							ShopApplication.userid=0;
							ShopApplication.usernamelogin="";
							ShopApplication.userimgurlrlogin="";
							ShopApplication.userinfo=null;
						}
						if(ShopApplication.loginflag==2){
							ShopApplication.usernameOtherlogin=null;
							ShopApplication.userimgurlOtherlogin=null;
							ShopApplication.usersexOtherlogin=0;
							ShopApplication.userbirthother="";
							ShopApplication.api.unregisterApp();
						}
						intent.setClass(MyInformationActivity.this, LoginActivity.class);
						intent.putExtra("flag", 07);
						startActivity(intent);
					}
					break;

				default:
					break;
			}
		}

	}

	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, final int year,
							  final int month, final int dayOfMonth) {
		}
	};

	/**
	 * 弹出用户选择头像提示框
	 */
	void onClick_Img() {
		final Dialog dialog = new Dialog(MyInformationActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.view_dialog_capture);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCanceledOnTouchOutside(true);

		LinearLayout menu_capture_r1 = (LinearLayout) dialog
				.findViewById(R.id.menu_capture_r1);
		LinearLayout menu_capture_r2 = (LinearLayout) dialog
				.findViewById(R.id.menu_capture_r2);
		Button dialog_contorl_b1 = (Button) dialog
				.findViewById(R.id.dialog_contorl_b1);

		menu_capture_r1.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				dialog.dismiss();
				//拍照的方法
				onClick_Capture();
			}
		});
		menu_capture_r2.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				dialog.dismiss();
				//从图库中选择的
				onClick_Pick();
			}
		});
		dialog_contorl_b1.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	/**
	 * 点击选择本地文件选择头像
	 */
	void onClick_Pick() {
		PhotoCrop crop = new PhotoCrop(1, 1, 150, 150);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(PhotoPickActivity.PHOTO_CROPKEY, crop);

		Intent intent = new Intent(MyInformationActivity.this,
				PhotoPickActivity.class);
		intent.putExtra(PhotoPickActivity.PHOTO_TYPEKEY,
				PhotoPickActivity.PHOTO_REQ_ALBUM);
		intent.putExtra(PhotoPickActivity.PHOTO_STORAGEKEY, Environment
				.getExternalStorageDirectory().toString());
		intent.putExtras(mBundle);
		startActivityForResult(intent, PhotoPickActivity.PHOTO_REQ_ALBUM);
	}

	/**
	 * 点击拍照获取图片
	 */
	void onClick_Capture() {
		PhotoCrop crop = new PhotoCrop(1, 1, 150, 150);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(PhotoPickActivity.PHOTO_CROPKEY, crop);

		Intent intent = new Intent(MyInformationActivity.this,
				PhotoPickActivity.class);
		intent.putExtra(PhotoPickActivity.PHOTO_TYPEKEY,
				PhotoPickActivity.PHOTO_REQ_CAPTURE);
		intent.putExtra(PhotoPickActivity.PHOTO_STORAGEKEY, Environment
				.getExternalStorageDirectory().toString());
		intent.putExtras(mBundle);
		startActivityForResult(intent, PhotoPickActivity.PHOTO_REQ_CAPTURE);
	}

	Bitmap bitmap = null;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PhotoPickActivity.PHOTO_REQ_ALBUM
				|| requestCode == PhotoPickActivity.PHOTO_REQ_CAPTURE) {

			if (resultCode == PhotoPickActivity.PHOTO_BACK_URI) {
				Uri uri = (Uri) data.getExtras().get("data");
				bitmap = BitmapUtil.getRotateBitmap(MyInformationActivity.this,
						uri);
			}
			if (resultCode == PhotoPickActivity.PHOTO_BACK_BMP) {
				bitmap = (Bitmap) data.getExtras().get("data");
			}
			if(null!=bitmap){

				UploadUtil uploadUtil = UploadUtil.getInstance();
				uploadUtil
						.setOnUploadProcessListener(new OnUploadProcessListener() {

							@Override
							public void onUploadProcess(int uploadSize) {
							}

							@Override
							public void onUploadDone(int responseCode,
													 String message) {
								Message msg = Message.obtain();
								msg.what = 10;
								msg.arg1 = responseCode;
								msg.obj = message;
								handler.sendMessage(msg);
							}

							@Override
							public void initUpload(int fileSize) {
							}
						}); // 设置监听器监听上传状态
				Map<String, String> params = new HashMap<String, String>();
				int userid = 0;
				if (ShopApplication.isLogin) {
					if (ShopApplication.loginflag == 1) {
						userid = ShopApplication.userid;
					}
					if(ShopApplication.loginflag == 2){
						userid = ShopApplication.useridother;
					}
				}
				params.put("membId", userid+"");
				uploadUtil.uploadFile(bitmap, "file",
						CommonVariable.UpdateMemberinfoImgURL, params);

			}
		}
		if (requestCode == 2 && resultCode == 1) {
			// 用户昵称的回调
			String name = data.getStringExtra("name");
			username.setText(name);
		}
		if (requestCode == 3 && resultCode == 1) {
			// 用户性别的回调
			String sex = data.getStringExtra("sex");
			usersex.setText(sex);
		}
		if (requestCode == 4 && resultCode == 1) {
			// 会员姓名
			String membernamestr = data.getStringExtra("membername");
			membername.setText(membernamestr);
		}
		if (requestCode == 5 && resultCode == 1) {
			// 会员手机号
			String memberphonestr = data.getStringExtra("memberphone");
			String phonenumber;
			if(memberphonestr.trim().length()==11){
				phonenumber=memberphonestr.trim().substring(0,3)+"****"+memberphonestr.trim().substring(7);
				memberphone.setText(phonenumber);
			}else{
				memberphone.setText(memberphonestr.trim());
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 10:

					if (1 == msg.arg1) {
						userimg.setImageBitmap(bitmap);
						JSONObject object = JSON.parseObject((String) msg.obj);
						String code = (String) object.get("retCode");
						if ("200".equals(code)) {
							if (object.containsKey("responseBody")) {
								JSONObject response = (JSONObject) object
										.get("responseBody");
								if (response.containsKey("membImg")) {
									String userimgurl = (String) response
											.get("membImg");
									if (null != userimgurl) {
										ShopApplication.userimgurlrlogin = userimgurl;
										Intent intent = new Intent("changeuserinfo");
										LocalBroadcastManager.getInstance(MyInformationActivity.this).sendBroadcast(intent);
									}
								}
							}
						}else {
							Toast.makeText(MyInformationActivity.this,
									"头像上传失败！", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(MyInformationActivity.this,
								"头像上传失败！",Toast.LENGTH_SHORT).show();
					}
					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}

	};
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
