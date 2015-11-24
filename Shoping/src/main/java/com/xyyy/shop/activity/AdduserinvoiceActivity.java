package com.xyyy.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.model.EnnSysArea;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StringUtils;

/**
 * 添加用户发票信息的
 */
public class AdduserinvoiceActivity extends BaseActivity {

	private ImageView top_back;
	private TextView top_text;
	private Button submit;
	private EditText invoice_top;
	private ToggleButton yesornobutton;
	private RelativeLayout yesrel;
	//发票配送地址
	private LinearLayout address_lin;
	private RelativeLayout recevice_address_rel;
	private TextView recevice_address;
	private TextView recevice_addressprovice;
	private TextView recevice_person;
    
    private int flag=0;//进来的标示 0 确认订单界面进来的 1充值界面进来的 	
	private int addressid;
	private String areaAllName;
	private String receAddress;
	private String recename;
	private String recephone;
	private String invoicetitle;//传过来的title
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adduserinvoice);
		flag=getIntent().getIntExtra("flag", 0);
		invoicetitle=getIntent().getStringExtra("invoicetitle");
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("新建发票");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		yesornobutton=(ToggleButton)findViewById(R.id.yesornobutton);
		
		yesornobutton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					//选中
					yesrel.setVisibility(View.VISIBLE);
				}else{
					//未选中
					yesrel.setVisibility(View.GONE);
				}
			}
		});
		yesrel=(RelativeLayout)findViewById(R.id.yesrel);
		
		invoice_top = (EditText) findViewById(R.id.invoice_top);
        
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				goAddinvoice();
			}
		});
		// 发票配送地址
		address_lin=(LinearLayout)findViewById(R.id.address_lin);
		if(flag==1){
			address_lin.setVisibility(View.VISIBLE);
		}else{
			address_lin.setVisibility(View.GONE);
		}
		recevice_address_rel=(RelativeLayout)findViewById(R.id.recevice_address_rel);
		recevice_address_rel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentaddress=new Intent(AdduserinvoiceActivity.this,UseradressActivity.class);
				intentaddress.putExtra("flag",5);
				startActivityForResult(intentaddress, 05);
			}
		});
		recevice_address=(TextView)findViewById(R.id.recevice_address);
		recevice_addressprovice=(TextView)findViewById(R.id.recevice_addressprovice);
		recevice_person=(TextView)findViewById(R.id.recevice_person);
		recevice_address.setText("无");
		
		if(StringUtils.isNotBlank(invoicetitle)){
        	yesornobutton.setChecked(true);
        	invoice_top.setText(invoicetitle);
        	invoice_top.setSelection(invoicetitle.length());
        	if(flag==1){
        		areaAllName=getIntent().getStringExtra("areaAllName");
    			receAddress=getIntent().getStringExtra("receAddress");
    			recename=getIntent().getStringExtra("recename");
    			recephone=getIntent().getStringExtra("recephone");
    			recevice_addressprovice.setText(areaAllName);
    			recevice_address.setText(receAddress);
    			recevice_person.setText(recename+" "+recephone);
    			addressid=getIntent().getIntExtra("addressid", 0);
        	}
        }
	}

	/**
	 * 添加发票信息
	 */
	protected void goAddinvoice() {
		String top = invoice_top.getText().toString().trim();
		if (StringUtils.isBlank(top)) {
              Toast.makeText(AdduserinvoiceActivity.this, "请填写发票抬头！", Toast.LENGTH_SHORT).show();
              return;
		}
		if(top.length()>30){
			 Toast.makeText(AdduserinvoiceActivity.this, "发票抬头过长！", Toast.LENGTH_SHORT).show();
             return;
		}
		switch(flag){
		case 0:
			//购物车确认订单界面
			getIntent().putExtra("text",
					top);
			setResult(02, getIntent());
			finish();
			break;
		case 1:
			//充值界面
			if(addressid==0){
				 Toast.makeText(AdduserinvoiceActivity.this, "请选择发票配送地址！", Toast.LENGTH_SHORT).show();
	             return;
			}
			getIntent().putExtra("text",
					top);
			getIntent().putExtra("addressid",
					addressid);
			getIntent().putExtra("areaAllName",
					areaAllName);
			getIntent().putExtra("receAddress",
					receAddress);
			getIntent().putExtra("recename",
					recename);
			getIntent().putExtra("recephone",
					recephone);
			setResult(01, getIntent());
			finish();
			break;
		}
		
		/*EnnMemberInvoice add=new EnnMemberInvoice();
		add.setInvoiceTitle(top);
		add.setInvoiceCont(content);
		add.setInvoiceType("01");
		//TODO 先默认添加到1 到时
		int userid=0;
		if(ShopApplication.isLogin){
			if(ShopApplication.loginflag==1){
				userid=ShopApplication.userid;
			}
			if(ShopApplication.loginflag==2){
				userid=ShopApplication.useridother;
			}
		}
		add.setMembId(userid);
		customProgressDialog.show();
		VolleyUtil.sendObjectByPostToString(CommonVariable.CartCreatInvocieURL, null, add, new HttpBackBaseListener() {
			
			@Override
			public void onSuccess(String string) {
				customProgressDialog.dismiss();
				invoice_top.setText("");
				invoice_content.setText("");
				Toast.makeText(AdduserinvoiceActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onFail(String failstring) {
				customProgressDialog.dismiss();
				Toast.makeText(AdduserinvoiceActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onError(VolleyError error) {
				Toast.makeText(AdduserinvoiceActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
				customProgressDialog.dismiss();
			}
		}, false, null);*/
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==05&&resultCode==05){
			addressid=data.getIntExtra("addressid",0);
			String province=data.getStringExtra("province");
			String city=data.getStringExtra("city");
			String country=data.getStringExtra("country");
			receAddress=data.getStringExtra("receAddress");
			recename=data.getStringExtra("recename");
			recephone=data.getStringExtra("recephone");
			VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetAreaDetailURL+country, null, null, EnnSysArea.class, new HttpBackListener<EnnSysArea>() {

				

				@Override
				public void onSuccess(EnnSysArea t) {
					areaAllName=t.getAreaAllName();
					recevice_addressprovice.setText(t.getAreaAllName());
				}

				@Override
				public void onFail(String failstring) {
					areaAllName="";
					recevice_addressprovice.setText("");
				}

				@Override
				public void onError(VolleyError error) {
					areaAllName="";
					recevice_addressprovice.setText("");
				}
			}, false, null);
			recevice_address.setText(receAddress);
			recevice_person.setText(recename+" "+recephone);
		}
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
