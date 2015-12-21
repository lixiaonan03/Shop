package com.xyyy.shop.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.model.EnnCard;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.RegularExpression;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 绑定会员卡的
 */
public class BindMemberActivity extends BaseActivity {
	
	private CustomProgressDialog customProgressDialog;
	private ImageView top_back;
	private TextView top_text;
	private TextView older_member;
	private TextView new_member;
	private View lineolder;
	private View linenew;
	private int flag;//1、旧会员被点击 2、新会员被点击
	private EditText edittextcardnum;
	private EditText edittextcode;
	private Button submit;
	private EditText edittextphone;
	private RelativeLayout older_rel;
	private RelativeLayout new_rel;
	private EditText edittextcardnum_new;
	private EditText edittextcode_new;
	private Button submit_new;
	private Button getcodebutton;
    
	private int yzm = 1;//1 验证码超时或无效  2 有效 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bindmember);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("绑定会员卡");
		top_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		customProgressDialog=new CustomProgressDialog(BindMemberActivity.this, "正在绑定......");
		initView();
	}
	/**
	 * 初始化界面组件
	 */
	private void initView() {
		older_member=(TextView)findViewById(R.id.older_member);
		new_member=(TextView)findViewById(R.id.new_member);
		
		older_member.setOnClickListener(viewclick);
		new_member.setOnClickListener(viewclick);
	
		lineolder=(View)findViewById(R.id.lineolder);
		linenew=(View)findViewById(R.id.linenew);
		
		//旧会员卡的
		older_rel=(RelativeLayout)findViewById(R.id.older_rel);
		edittextcardnum=(EditText)findViewById(R.id.edittextcardnum);
		edittextphone=(EditText)findViewById(R.id.edittextphone);
		edittextcode=(EditText)findViewById(R.id.edittextcode);
		getcodebutton=(Button)findViewById(R.id.getcodebutton);
		getcodebutton.setOnClickListener(viewclick);
		
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(viewclick);
		
		//新会员卡
		new_rel=(RelativeLayout)findViewById(R.id.new_rel);
		edittextcardnum_new=(EditText)findViewById(R.id.edittextcardnum_new);
		edittextcode_new=(EditText)findViewById(R.id.edittextcode_new);
		submit_new=(Button)findViewById(R.id.submit_new);
		submit_new.setOnClickListener(viewclick);
	}
    /**
     * 顶部tab页改变之
     */
	private void changetab(){
		older_member.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
		new_member.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
		
		older_member.setBackgroundResource(R.color.order_background_nochecked);
		new_member.setBackgroundResource(R.color.order_background_nochecked);
	
		lineolder.setBackgroundResource(R.color.order_linecolor_nochecked);
		linenew.setBackgroundResource(R.color.order_linecolor_nochecked);
		switch (flag) {
		case 1:
			older_member.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
			older_member.setBackgroundResource(R.color.order_background_checked);
			lineolder.setBackgroundResource(R.color.order_linecolor_checked);
			older_rel.setVisibility(View.VISIBLE);
			new_rel.setVisibility(View.GONE);
			break;
		case 2:
			new_member.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
			new_member.setBackgroundResource(R.color.order_background_checked);
			linenew.setBackgroundResource(R.color.order_linecolor_checked);
			new_rel.setVisibility(View.VISIBLE);
			older_rel.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
	
	OnClickListener viewclick=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.older_member:
				//旧会员
				flag=1;
				changetab();
		
				break;
			case R.id.new_member:
				//新会员
				flag=2;
				changetab();
				break;
			case R.id.submit:
				//确认绑定
				gobind();
				break;
			case R.id.submit_new:
				//新会员的绑定
				gobindnew();
				break;
			case R.id.getcodebutton:
				//旧会员卡获取短信验证码
				String phone = edittextphone.getText().toString().trim();
				String check = RegularExpression.checkRegularExpression(phone,
						RegularExpression.MOBILE_PHONE, "请输入正确的手机号码！");
				
				if (StringUtils.isNotBlank(check)) {
					Toast.makeText(BindMemberActivity.this, check, 0).show();
					return;
				}
				getcodebutton.setBackgroundResource(R.drawable.getcode_onbutton);
				getcodebutton.setClickable(false);
				VolleyUtil.sendStringRequestByGetToString(CommonVariable.GetCodeURL+phone+"/"+CommonVariable.SMSCodeForBindOldmember, null, null, new HttpBackBaseListener() {
					
					@Override
					public void onSuccess(String string) {
						// TODO Auto-generated method stub
						edittextphone.setEnabled(false);
						yzm = 2;
						new MyCount(120000,1000).start();
					}
					
					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						Toast.makeText(BindMemberActivity.this, "获取验证码失败！", 0).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}
					
					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(BindMemberActivity.this, "获取验证码失败！", 0).show();
						getcodebutton.setBackgroundResource(R.drawable.login_button);
						getcodebutton.setClickable(true);
					}
				}, false, null);
				
				break;
			default:
				break;
			}
		}
	};
	
	protected void gobind() {
		// TODO 去绑定会员卡
		String carnum=edittextcardnum.getText().toString().trim();
		//String phone=edittextphone.getText().toString().trim();
		String code=edittextcode.getText().toString().trim();
		if(StringUtils.isBlank(carnum)||StringUtils.isBlank(code)){
			Toast.makeText(BindMemberActivity.this, "请填写相关信息！", Toast.LENGTH_SHORT).show();
		     return;
		}
		customProgressDialog.show();
		int userid = 0;
		if(ShopApplication.isLogin){
			if(ShopApplication.loginflag==1){
			     userid = ShopApplication.userid;
			}
			if(ShopApplication.loginflag==2){
				userid = ShopApplication.useridother;
			}
		}
		VolleyUtil.sendStringRequestByGetToString(CommonVariable.BindMemberURL+carnum+"/"+code+"/"+userid, null, null, new HttpBackBaseListener() {
			
			@Override
			public void onSuccess(String string) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(BindMemberActivity.this);
		        builder.setTitle("提示");
		        builder.setMessage("绑定成功");
		        builder.setNegativeButton("确定", null);
		        if (!isFinishing()) {
		            builder.create().show();
		        }
		        edittextcardnum_new.setText("");
		        edittextcode.setText("");
			}
			
			@Override
			public void onFail(String failstring) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				Toast.makeText(BindMemberActivity.this, "绑定失败！"+failstring, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onError(VolleyError error) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				Toast.makeText(BindMemberActivity.this, "绑定失败！", Toast.LENGTH_SHORT).show();
			}
		}, false, null);
	}
	/**
	 * 绑定新会员卡的内容
	 */
	protected void gobindnew() {
		// TODO 去绑定新会员卡
		String carnum=edittextcardnum_new.getText().toString().trim();
		String codenew=edittextcode_new.getText().toString().trim();
		if(StringUtils.isBlank(carnum)||StringUtils.isBlank(codenew)){
			Toast.makeText(BindMemberActivity.this, "请填写相关信息！", Toast.LENGTH_SHORT).show();
			return;
		}
		customProgressDialog.show();
		int userid = 0;
		if(ShopApplication.isLogin){
			if(ShopApplication.loginflag==1){
			     userid = ShopApplication.userid;
			}
			if(ShopApplication.loginflag==2){
				userid=ShopApplication.useridother;
			}
		}
		VolleyUtil.sendStringRequestByGetToBean(CommonVariable.BindNewMemberURL+carnum+"/"+codenew+"/"+userid+"/88", null, null,EnnCard.class,new HttpBackListener<EnnCard>(){
			
			@Override
			public void onSuccess(EnnCard t) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(BindMemberActivity.this);
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
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				Toast.makeText(BindMemberActivity.this, "绑定失败！"+failstring, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onError(VolleyError error) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				Toast.makeText(BindMemberActivity.this, "绑定失败！", Toast.LENGTH_SHORT).show();
			}
		}, false, null);
	}
	
	class MyCount extends CountDownTimer {  
		  
        public MyCount(long millisInFuture, long countDownInterval) {  
            super(millisInFuture, countDownInterval);  
        }  
  
        @Override  
        public void onFinish() {  
        	//倒计时完要做的事情
        	getcodebutton.setClickable(true);
        	edittextphone.setEnabled(true);
        	
        	getcodebutton.setBackgroundResource(R.drawable.login_button);
        	getcodebutton.setText("获取验证码");
        	yzm = 1;
        }  
  
        @Override  
        public void onTick(long millisUntilFinished) {  
        	getcodebutton.setText(millisUntilFinished / 1000+"秒");  
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
