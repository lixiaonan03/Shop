package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.AddressSpinnerAdapter;
import com.xyyy.shop.model.EnnMemberReceipt;
import com.xyyy.shop.model.EnnSysArea;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 添加用户收货地址的界面
 */
public class AdduseradressActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	private EditText address_name;
	private EditText address_phone;
	private EditText address_detail;

	// spinner 省市区
	private RelativeLayout province_lin, city_lin, country_lin;
	private TextView province_text, city_text, country_text;
	private ImageView province_img, city_img, country_img;
	// 省市区数据
	private List<EnnSysArea> province_list = new ArrayList<EnnSysArea>();
	private List<EnnSysArea> city_list = new ArrayList<EnnSysArea>();
	private List<EnnSysArea> country_list = new ArrayList<EnnSysArea>();
	List<EnnSysArea>  showprovince=new ArrayList<EnnSysArea>();
	List<EnnSysArea>  showcity=new ArrayList<EnnSysArea>();
	List<EnnSysArea>  showcountry=new ArrayList<EnnSysArea>();
	private String provinceareacode;
	private String cityareacode;
	private String countryareacode;

	private CustomProgressDialog customProgressDialog;
	private List<EnnSysArea> canuserlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adduseraddress);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("新建地址");
		canuserlist = (List<EnnSysArea>) getIntent().getSerializableExtra(
				"addresslist");
		customProgressDialog=new CustomProgressDialog(AdduseradressActivity.this, "正在添加......");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		address_name = (EditText) findViewById(R.id.address_name);
		address_phone = (EditText) findViewById(R.id.address_phone);
		address_detail = (EditText) findViewById(R.id.address_detail);
		
		initviewspinner();
		initdataaddress(1,"111111");
		
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				goAddaddress();
			}
		});
	}

	/**
	 * 初始化地址选择的控件
	 */
	void initviewspinner() {
		province_lin = (RelativeLayout) findViewById(R.id.province_lin);
		province_text = (TextView) findViewById(R.id.province_text);
		province_img = (ImageView) findViewById(R.id.province_img);

		city_lin = (RelativeLayout) findViewById(R.id.city_lin);
		city_text = (TextView) findViewById(R.id.city_text);
		city_img = (ImageView) findViewById(R.id.city_img);

		country_lin = (RelativeLayout) findViewById(R.id.country_lin);
		country_text = (TextView) findViewById(R.id.country_text);
		country_img = (ImageView) findViewById(R.id.country_img);

		province_lin.setOnClickListener(viewclickspinner);
		city_lin.setOnClickListener(viewclickspinner);
		country_lin.setOnClickListener(viewclickspinner);

	}

	OnClickListener viewclickspinner = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.province_lin:
				// 省
				showPopupWindow(1);
					province_img
							.setImageResource(R.drawable.address_spinner_up);
				break;
			case R.id.city_lin:
				// 市
				showPopupWindow(2);
				city_img.setImageResource(R.drawable.address_spinner_up);
				break;
			case R.id.country_lin:
				// 区
				showPopupWindow(3);
				country_img
				.setImageResource(R.drawable.address_spinner_up);
				break;

			default:
				break;
			}
		}
	};
	private ListView lsvAccount;
	private AddressSpinnerAdapter addressAdapter;
	private PopupWindow popupWindow;

	/**
	 * 添加收货地址
	 */
	protected void goAddaddress() {
		String name = address_name.getText().toString().trim();
		String phone = address_phone.getText().toString().trim();
		String province = provinceareacode;
		String city = cityareacode;
		String country = countryareacode;
		String detail = address_detail.getText().toString().trim();
		if (StringUtils.isBlank(name) || StringUtils.isBlank(phone)
				|| StringUtils.isBlank(province) || StringUtils.isBlank(city)
				|| StringUtils.isBlank(country) || StringUtils.isBlank(detail)) {
			Toast.makeText(AdduseradressActivity.this, "请填写相关信息！", Toast.LENGTH_SHORT).show();
			return;
		}
		EnnMemberReceipt add = new EnnMemberReceipt();
		add.setReceName(name);
		add.setRecePhone(phone);
		add.setProvince(provinceareacode);
		add.setCity(cityareacode);
		add.setCountry(countryareacode);
		add.setReceAddress(detail);
		add.setIsDefault("0");
		// TODO 先默认添加到1 到时
		int userid = 0;
		if (ShopApplication.isLogin) {
			if (ShopApplication.loginflag == 1) {
				userid = ShopApplication.userid;
			}
			if (ShopApplication.loginflag == 2) {
				userid = ShopApplication.useridother;
			}
		}
		add.setMembId(userid);
		customProgressDialog.show();
		VolleyUtil.sendObjectByPostToString(CommonVariable.AddMyAddressURL,
				null, add, new HttpBackBaseListener() {

					@Override
					public void onSuccess(String string) {
						customProgressDialog.dismiss();
						Toast.makeText(AdduseradressActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
						address_name.setText("");
						address_phone.setText("");
						address_detail.setText("");

						provinceareacode = "";
						cityareacode = "";
						countryareacode = "";
						province_text.setText("请选择省");
						city_text.setText("请选择市");
						country_text.setText("请选择区");
						finish();
					}

					@Override
					public void onFail(String failstring) {
						customProgressDialog.dismiss();
						Toast.makeText(AdduseradressActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						Toast.makeText(AdduseradressActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
					}
				}, false, null);
	}

	void initdataaddress(final int flag, String addresscode) {
		VolleyUtil.sendStringRequestByGetToList(CommonVariable.GetAreaListURL
				+ addresscode, null, null, EnnSysArea.class,
				new HttpBackListListener<EnnSysArea>() {

					@Override
					public void onSuccess(List<EnnSysArea> t) {
						if (null != t && t.size() > 0) {
							switch (flag) {
							case 1:
								province_list = t;
								break;
							case 2:
								city_list = t;
								break;
							case 3:
								country_list = t;
								break;

							default:
								break;
							}

						} else {
							switch (flag) {
							case 1:
								province_list = new ArrayList<EnnSysArea>();
								break;
							case 2:
								city_list = new ArrayList<EnnSysArea>();
								break;
							case 3:
								country_list = new ArrayList<EnnSysArea>();
								break;

							default:
								break;
							}
						}
					}

					@Override
					public void onFail(String failstring) {
						Toast.makeText(AdduseradressActivity.this, "获取地区信息失败！", Toast.LENGTH_SHORT).show();
						switch (flag) {
						case 1:
							province_list = new ArrayList<EnnSysArea>();
							break;
						case 2:
							city_list = new ArrayList<EnnSysArea>();
							break;
						case 3:
							country_list = new ArrayList<EnnSysArea>();
							break;

						default:
							break;
						}
					}

					@Override
					public void onError(VolleyError error) {
						Toast.makeText(AdduseradressActivity.this, "获取地区信息失败！", Toast.LENGTH_SHORT).show();
						switch (flag) {
						case 1:
							province_list = new ArrayList<EnnSysArea>();
							break;
						case 2:
							city_list = new ArrayList<EnnSysArea>();
							break;
						case 3:
							country_list = new ArrayList<EnnSysArea>();
							break;

						default:
							break;
						}
					}
				}, false, null);
	}

	// 设置和显示PopupWindow flag 1为父 2为子
	private void showPopupWindow(final int flag) {
		// 获取要显示在PopupWindow上的窗体视图
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.popwindow_addressview, null);
		// 实例化并且设置PopupWindow显示的视图
		// 获取PopupWindow中的控件
		lsvAccount = (ListView) view.findViewById(R.id.lsvAccount);
		// 绑定ListView的数据 1省 2市 3 区
		if (flag == 1) {
			showprovince.clear();
			if(null!=canuserlist&&canuserlist.size()>0){
				      
					for (EnnSysArea allone : province_list) {
						for (EnnSysArea canone : canuserlist) {
							if(StringUtils.isNotBlank(allone.getAreaCode())&&StringUtils.isNotBlank(canone.getAreaCode())){
								if(allone.getAreaCode().length()==6&&canone.getAreaCode().length()==6){
									if(allone.getAreaCode().substring(0, 2).equals(canone.getAreaCode().substring(0,2))){
										showprovince.add(allone);
										break;
									}
								}
							}
						}
						
					}
			}else{
				showprovince.addAll(province_list);
			}
			addressAdapter = new AddressSpinnerAdapter(
					AdduseradressActivity.this, showprovince);
			lsvAccount.setAdapter(addressAdapter);
		} else if (flag == 2) {
			showcity.clear();
			if(null!=canuserlist&&canuserlist.size()>0){
					for (EnnSysArea allone : city_list) {
						for (EnnSysArea canone : canuserlist) {
							if(StringUtils.isNotBlank(allone.getAreaCode())&&StringUtils.isNotBlank(canone.getAreaCode())){
								if(allone.getAreaCode().length()==6&&canone.getAreaCode().length()==6){
									if(allone.getAreaCode().substring(0, 4).equals(canone.getAreaCode().substring(0, 4))){
										showcity.add(allone);
										break;
									}
								}
							}
						}
						
					}
			}else{
				showcity.addAll(city_list);
			}
			
			addressAdapter = new AddressSpinnerAdapter(
					AdduseradressActivity.this, showcity);
			lsvAccount.setAdapter(addressAdapter);
		} else if (flag == 3) {
			showcountry.clear();
			if(null!=canuserlist&&canuserlist.size()>0){
					for (EnnSysArea allone : country_list) {
						for (EnnSysArea canone : canuserlist) {
							if(StringUtils.isNotBlank(allone.getAreaCode())&&StringUtils.isNotBlank(canone.getAreaCode())){
								if(allone.getAreaCode().length()==6&&canone.getAreaCode().length()==6){
									if(allone.getAreaCode().equals(canone.getAreaCode())){
										showcountry.add(allone);
										break;
									}
								}
							}
						}
						
					}
			}else{
				showcountry.addAll(country_list);
			}
			
			addressAdapter = new AddressSpinnerAdapter(
					AdduseradressActivity.this, showcountry);
			lsvAccount.setAdapter(addressAdapter);
		}
		int height = setPullLvHeight(lsvAccount);
		if (height > 500) {
			popupWindow = new PopupWindow(view, province_lin.getWidth(), 500);
		} else {
			popupWindow = new PopupWindow(view, province_lin.getWidth(),
					LayoutParams.WRAP_CONTENT);
		}

		popupWindow.setFocusable(true);
		//popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if (flag == 1) {
					province_img
							.setImageResource(R.drawable.address_spinner_down);
				} else if (flag == 2) {
					city_img.setImageResource(R.drawable.address_spinner_down);
				} else if (flag == 3) {
					country_img
							.setImageResource(R.drawable.address_spinner_down);
				}

			}
		});
		// 设置ListView点击事件
		lsvAccount.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (flag == 1) {

					EnnSysArea data = showprovince.get(arg2);
					province_text.setText(data.getAreaName());
					provinceareacode = data.getAreaCode();
					initdataaddress(2, provinceareacode);
					cityareacode = "";
					countryareacode = "";
					city_text.setText("请选择市");
					country_text.setText("请选择区");
					city_list = new ArrayList<EnnSysArea>();
					country_list = new ArrayList<EnnSysArea>();

				} else if (flag == 2) {
					EnnSysArea data = showcity.get(arg2);
					city_text.setText(data.getAreaName());
					cityareacode = data.getAreaCode();
					initdataaddress(3, cityareacode);
					country_list = new ArrayList<EnnSysArea>();
					countryareacode = "";
					country_text.setText("请选择区");
				} else if (flag == 3) {
					EnnSysArea data = showcountry.get(arg2);
					country_text.setText(data.getAreaName());
					countryareacode = data.getAreaCode();
				}
				dismissPopupWindow(flag);
			}
		});
		if (flag == 1) {
			popupWindow.showAsDropDown(province_lin, 0, 0);
		} else if (flag == 2) {
			popupWindow.showAsDropDown(city_lin, 0, 0);
		} else if (flag == 3) {
			popupWindow.showAsDropDown(country_lin, 0, 0);
		}
		popupWindow.update();
	}

	// 让PopupWindow消失
	private void dismissPopupWindow(int flag) {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
		if (flag == 1) {
			province_img.setImageResource(R.drawable.address_spinner_down);
		} else if (flag == 2) {
			city_img.setImageResource(R.drawable.address_spinner_down);
		} else if (flag == 3) {
			country_img.setImageResource(R.drawable.address_spinner_down);
		}
	}

	public int setPullLvHeight(ListView pull) {
		int height = 0;
		int totalHeight = 0;
		ListAdapter adapter = pull.getAdapter();
		for (int i = 0, len = adapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = adapter.getView(i, null, pull);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = pull.getLayoutParams();
		params.height = totalHeight
				+ (pull.getDividerHeight() * (pull.getCount() - 1));
		height = params.height;

		if (height >500) {
			params.height = 490;
			pull.setLayoutParams(params);
		}
		return height;
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
