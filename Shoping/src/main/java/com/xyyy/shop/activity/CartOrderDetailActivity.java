package com.xyyy.shop.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.CartMemberItemAdapter;
import com.xyyy.shop.adapter.MyCartOrderItemAdapter;
import com.xyyy.shop.adapter.MyCartOrderNobuyItemAdapter;
import com.xyyy.shop.db.Cart;
import com.xyyy.shop.model.CreateOrderDTO;
import com.xyyy.shop.model.EnnMemberReceipt;
import com.xyyy.shop.model.EnnOrder;
import com.xyyy.shop.model.EnnSysArea;
import com.xyyy.shop.model.MemCardVO;
import com.xyyy.shop.model.MemCardVODetail;
import com.xyyy.shop.model.OrderInfoVO;
import com.xyyy.shop.model.OrderItemVO;
import com.xyyy.shop.model.ShowGoodsDTO;
import com.xyyy.shop.model.ShowGoodsItemsDTO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.toolUtil.StringUtils;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.MyListView;
/**
 * 确认订单模块
 */
public class CartOrderDetailActivity extends BaseActivity {
	
	private ImageView top_back;
	private TextView top_text;
	
	private CustomProgressDialog customProgressDialog;
	private TextView cartorder_address_textview;
	private TextView cartorder_address_textview1;
	private TextView cartorder_address_textview2;
	private RelativeLayout cartorder_address_rel;
	private TextView cart_express_money;
	private TextView cart_pay_method;
	
	private RelativeLayout cart_invoice_rel;
	private TextView cart_invoice_textview1;
	private RelativeLayout cart_remark_rel;
	private TextView cart_remark_textview1;
	private TextView cart_favorable_textview;
	private RelativeLayout cart_favorable_rel;
	private TextView membercardpay_textview;
	private RelativeLayout membercardpay_rel;
	private TextView cart_current_xindou;
	private ToggleButton cart_xindou_button;
	private MyListView listview;
	private TextView cart_allproduct_money;
	private TextView cart_yunfei_money;
	private TextView cart_favorable_money;
	private TextView cart_membercardpay_money;
	private TextView cart_xindou_money;
	private TextView cartorder_allmoney;
	private Button cartorder_button;
	
	private List<Cart> list;
	private List<MemCardVODetail> membercardlist=new ArrayList<MemCardVODetail>();

	
	//回传回来的一些的信息
	private int addressid=-1;
	private String payMethod=null;
	private String remark="";
	private String invoiceflag="0";//是否需要发票
	private String invoicetitle="";//发票抬头
	
	
	private int xindouflag=1;//1关 2开
	private int xindou;
	private int ishasmembercard=0;//0 不包含 1包含
	private int membercardpayflag=1;//1不支付 2支付
	private List<Integer>  membercardpaylist =new ArrayList<Integer>();
	
	private RadioGroup radiogroup;
	private RadioButton radiobuttonaddress;
	private RadioButton radiobuttonmember;
	private MyListView listmember;
	private CartMemberItemAdapter adaptermembaer;
	
	
	private int flagaddress;//配送方式选择 0 地址配送 1跟随会员卡
	private MyCartOrderItemAdapter adapter;
	private MyListView nobuylistview;
	private MyCartOrderNobuyItemAdapter nobuyadapter;
	private LinearLayout nobuy_lin;
	
	private BigDecimal orderallmoney=new BigDecimal(0);
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cartorderdetail);
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text=(TextView)findViewById(R.id.top_text);
		top_text.setText("确认订单");
		top_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		list=(List<Cart>) getIntent().getSerializableExtra("list");
		customProgressDialog=new CustomProgressDialog(CartOrderDetailActivity.this, "正在加载......");
		initview();
		initdata();
	}
    /**
     * 初始化控件
     */
	private void initview() {
		//底部按钮
		cartorder_allmoney=(TextView)findViewById(R.id.cartorder_allmoney);
		cartorder_button=(Button)findViewById(R.id.cartorder_button);
		cartorder_button.setOnClickListener(viewclick);
		
		//收货信息选择
		radiogroup=(RadioGroup)findViewById(R.id.radiogroup);
		radiobuttonaddress=(RadioButton)findViewById(R.id.radiobuttonaddress);
		radiobuttonmember=(RadioButton)findViewById(R.id.radiobuttonmember);
		radiogroup.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup paramRadioGroup,
					int paramInt) {
				if(paramInt==R.id.radiobuttonaddress){
					flagaddress=0;
					cartorder_address_rel.setVisibility(View.VISIBLE);
					listmember.setVisibility(View.GONE);
					
					getshowgoodByaddressid(addressid);
				}
				if(paramInt==R.id.radiobuttonmember){
					flagaddress=1;
					listmember.setVisibility(View.VISIBLE);
					cartorder_address_rel.setVisibility(View.GONE);
					if (adaptermembaer.flagposition == -1) {
						if(membercardlist.size()>0){
							membercardlist.get(0).setIsDefault("1");
							int countrycode=StrToNumber.strToint(membercardlist.get(0).getEnnCardConfig().getCountry());
							getshowgoodByaddressid(countrycode);
							adaptermembaer.notifyDataSetChanged();
						}
					} else {
						int countrycode=StrToNumber.strToint(membercardlist.get(adaptermembaer.flagposition).getEnnCardConfig().getCountry());
						getshowgoodByaddressid(countrycode);
					}
				}
			}
		});
		
		//收货地址
		cartorder_address_rel=(RelativeLayout)findViewById(R.id.cartorder_address_rel);
		cartorder_address_rel.setOnClickListener(viewclick);
		cartorder_address_textview=(TextView)findViewById(R.id.cartorder_address_textview);
		cartorder_address_textview1=(TextView)findViewById(R.id.cartorder_address_textview1);
		cartorder_address_textview2=(TextView)findViewById(R.id.cartorder_address_textview2);
		//会员卡列表
		listmember=(MyListView)findViewById(R.id.listmember);
		listmember.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (adaptermembaer.flagposition == -1) {
					membercardlist.get(arg2).setIsDefault("1");
				} else {
					membercardlist.get(adaptermembaer.flagposition).setIsDefault("0");
					membercardlist.get(arg2).setIsDefault("1");
				}
				int countrycode=StrToNumber.strToint(membercardlist.get(arg2).getEnnCardConfig().getCountry());
				getshowgoodByaddressid(countrycode);
				adaptermembaer.notifyDataSetChanged();
			}
		});;
		listmember.setFocusable(false);
		//快递费用
		cart_express_money=(TextView)findViewById(R.id.cart_express_money);
		//支付方式
		cart_pay_method=(TextView)findViewById(R.id.cart_pay_method);
		
		//发票
		cart_invoice_rel=(RelativeLayout)findViewById(R.id.cart_invoice_rel);
		cart_invoice_rel.setOnClickListener(viewclick);
		cart_invoice_textview1=(TextView)findViewById(R.id.cart_invoice_textview1);
		//备注
		cart_remark_rel=(RelativeLayout)findViewById(R.id.cart_remark_rel);
		cart_remark_rel.setOnClickListener(viewclick);
		cart_remark_textview1=(TextView)findViewById(R.id.cart_remark_textview1);
		//优惠
		cart_favorable_rel=(RelativeLayout)findViewById(R.id.cart_favorable_rel);
		cart_favorable_rel.setOnClickListener(viewclick);
		cart_favorable_textview=(TextView)findViewById(R.id.cart_favorable_textview);
		//会员卡支付
		membercardpay_rel=(RelativeLayout)findViewById(R.id.membercardpay_rel);
		membercardpay_rel.setOnClickListener(viewclick);
		membercardpay_textview=(TextView)findViewById(R.id.membercardpay_textview);
		//新豆
		cart_current_xindou=(TextView)findViewById(R.id.cart_current_xindou);
		cart_xindou_button=(ToggleButton)findViewById(R.id.cart_xindou_button);
		cart_xindou_button.setOnClickListener(viewclick);
		cart_xindou_button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){
					//选中
					xindouflag=2;
				}else{
					//未选中
					xindouflag=1;
				}
			}
		});
		//商品列表
		listview=(MyListView)findViewById(R.id.listview);
		adapter = new MyCartOrderItemAdapter(CartOrderDetailActivity.this, list);
		listview.setAdapter(adapter);
		listview.setFocusable(false);
		//不能购买的商品列表
		nobuy_lin=(LinearLayout)findViewById(R.id.nobuy_lin);
		nobuylistview=(MyListView)findViewById(R.id.nobuylistview);
		nobuyadapter = new MyCartOrderNobuyItemAdapter(CartOrderDetailActivity.this, list);
		nobuylistview.setAdapter(nobuyadapter);
		nobuylistview.setFocusable(false);
		
		//费用
		cart_allproduct_money=(TextView)findViewById(R.id.cart_allproduct_money);
		cart_yunfei_money=(TextView)findViewById(R.id.cart_yunfei_money);
		cart_membercardpay_money=(TextView)findViewById(R.id.cart_membercardpay_money);
		cart_favorable_money=(TextView)findViewById(R.id.cart_favorable_money);
		cart_xindou_money=(TextView)findViewById(R.id.cart_xindou_money);
	}
    OnClickListener viewclick=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO 点击事件处理
			switch (arg0.getId()) {
			case R.id.cartorder_button:
				//底部 提交订单
				goCreatOrder();
				break;
			case R.id.cartorder_address_rel:
				//收货地址
				Intent intentaddress=new Intent(CartOrderDetailActivity.this,UseradressActivity.class);
				intentaddress.putExtra("flag",3);
				startActivityForResult(intentaddress, 03);
				break;
			case R.id.cart_invoice_rel:
				//发票信息
				Intent intentinvoice=new Intent(CartOrderDetailActivity.this,AdduserinvoiceActivity.class);
				intentinvoice.putExtra("flag",0);
				if(StringUtils.isNotBlank(invoicetitle)){
					intentinvoice.putExtra("invoicetitle",invoicetitle);
				}
				startActivityForResult(intentinvoice, 02);
				break;
			case R.id.cart_remark_rel:
				//备注
				Intent intentremark=new Intent(CartOrderDetailActivity.this,AdduserremarkActivity.class);
				intentremark.putExtra("flag",2);
				if(StringUtils.isNotBlank(remark)){
					intentremark.putExtra("remark",remark);
				}
				startActivityForResult(intentremark, 04);
				break;
			case R.id.cart_favorable_rel:
				//优惠
				
				break;
			case R.id.membercardpay_rel:
				//会员卡支付
				Intent intentmembercardpay=new Intent(CartOrderDetailActivity.this,UserMembercardPayActivity.class);
				intentmembercardpay.putExtra("flag",1);
				if(null!=membercardpaylistgo&&membercardpaylistgo.size()>0)
				intentmembercardpay.putExtra("list",(Serializable)membercardpaylistgo);
				startActivityForResult(intentmembercardpay, 05);
				break;
			

			default:
				break;
			}
		}
	};
	private List<MemCardVO> membercardpaylistgo;
	/**
	 * 根据当前信息获取收货地址及新豆等一些信息
	 */
	private void initdata(){
		customProgressDialog.show();
		StringBuilder url=new StringBuilder();
		url.append(CommonVariable.CartOrderURL);
		int personid = 0;
		if(ShopApplication.isLogin){
			if(ShopApplication.loginflag==1){
				//原生登录
				personid=ShopApplication.userid;
			}
			if(ShopApplication.loginflag==2){
				//第三方登录
				personid=ShopApplication.useridother;
			}
		}
		url.append(personid+"/");
		if(list!=null){
			for (Cart one : list) {
				url.append(one.getGoodid()+"-"+one.getNum()+"---");
			}
		}
		VolleyUtil.sendStringRequestByGetToBean(url.toString(), null, null, OrderInfoVO.class,new HttpBackListener<OrderInfoVO>(){

			@Override
			public void onSuccess(OrderInfoVO t) {
				customProgressDialog.dismiss();
				changeview(t);
			}

			@Override
			public void onFail(String failstring) {
				customProgressDialog.dismiss();
				Toast.makeText(CartOrderDetailActivity.this,"加载相关信息失败！",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(VolleyError error) {
				customProgressDialog.dismiss();
				Toast.makeText(CartOrderDetailActivity.this,"加载相关信息失败！",Toast.LENGTH_SHORT).show();
			}}, false, null);
		VolleyUtil.sendStringRequestByGetToList(CommonVariable.CartOrderMemberURL+personid, null, null, MemCardVODetail.class,new HttpBackListListener<MemCardVODetail>() {

			@Override
			public void onSuccess(List<MemCardVODetail> t) {
				if(null!=t&&t.size()>0){
					//TODO 随会员卡配送过滤
					membercardlist.clear();
					for (MemCardVODetail one : t) {
						// 判断是不是服务型会员卡
						if (null != one.getEnnCardType().getServiceType()
								&& one.getEnnCardType().getServiceType().equals("01")) {
							//服务型会员卡
							membercardlist.add(one);
						}
					}
					if(membercardlist.size()>0){
						adaptermembaer=new CartMemberItemAdapter(CartOrderDetailActivity.this, membercardlist);
						listmember.setAdapter(adaptermembaer);
					}else{
						radiobuttonmember.setClickable(false);
						radiobuttonmember.setTextColor(getResources().getColor(R.color.common_bottom_btn_black));
					}
					
				}else{
					radiobuttonmember.setClickable(false);
					radiobuttonmember.setTextColor(getResources().getColor(R.color.common_bottom_btn_black));
				}
			}

			@Override
			public void onFail(String failstring) {
				radiobuttonmember.setClickable(false);
				radiobuttonmember.setTextColor(getResources().getColor(R.color.common_bottom_btn_black));
				
			}

			@Override
			public void onError(VolleyError error) {
				radiobuttonmember.setClickable(false);
				radiobuttonmember.setTextColor(getResources().getColor(R.color.common_bottom_btn_black));
				
			}
		}, false, null);
		List<Integer>  cartgoodlist=new ArrayList<Integer>();
		if(list!=null){
			for (Cart one : list) {
				cartgoodlist.add(one.getGoodid());
			}
		}
		VolleyUtil.sendObjectByPostToString(CommonVariable.CartOrderIshasMembercardURL, null, cartgoodlist,new HttpBackBaseListener() {
			
			@Override
			public void onSuccess(String string) {
				// TODO Auto-generated method stub
				if(string.equals("0")){
					ishasmembercard=0;
				}
				if(string.equals("1")){
					ishasmembercard=1;
				}
			}
			
			@Override
			public void onFail(String failstring) {
				ishasmembercard=0;
			}
			
			@Override
			public void onError(VolleyError error) {
				ishasmembercard=0;
			}
		},false,null);
		
	}
	/**
	 * 根据获取到的对象 改变界面上的view数据
	 * @param t OrderInfoVO 对象
	 */
	protected void changeview(OrderInfoVO t) {
		//TODO 会员卡选择
		//收货地址
		if(null!=t.getEmr()){
			if(null!=t.getEmr().getReceAddress()){
				addressid=t.getEmr().getReceId();
				getshowgoodByaddressid(addressid);
				VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetAreaDetailURL+t.getEmr().getCountry(), null, null, EnnSysArea.class, new HttpBackListener<EnnSysArea>() {

					@Override
					public void onSuccess(EnnSysArea t) {
						cartorder_address_textview.setText(t.getAreaAllName());
					}

					@Override
					public void onFail(String failstring) {
						cartorder_address_textview.setText("");
					}

					@Override
					public void onError(VolleyError error) {
						cartorder_address_textview.setText("");
					}
				}, false, null);
				cartorder_address_textview1.setText(t.getEmr().getReceAddress());
				cartorder_address_textview2.setText(t.getEmr().getReceName()+" "+t.getEmr().getRecePhone());
			}
		}else{
			cartorder_address_textview1.setText("请选择收货地址");
		}
		//支付及配送
		if(null!=t.getEo()){
			  /** 01-货到付款 02-在线支付  03-公司转账 04-邮局汇款   05 会员卡 06 礼品卡**/
			payMethod=t.getEo().getPayMethod();
			if("01".equals(t.getEo().getPayMethod())){
				cart_pay_method.setText("支付方式：货到付款");
			}
			if("02".equals(t.getEo().getPayMethod())){
				cart_pay_method.setText("支付方式：在线支付");
			}
			if("03".equals(t.getEo().getPayMethod())){
				cart_pay_method.setText("支付方式：公司转账");
			}
			if("04".equals(t.getEo().getPayMethod())){
				cart_pay_method.setText("支付方式：邮局汇款");
			}
		}else{
			cart_pay_method.setText("支付方式：在线支付");
		}
		if(null!=t.getYunfei()){
			BigDecimal yunfei=new BigDecimal(StrToNumber.strTodouble(t.getYunfei()));
			yunfei=yunfei.setScale(2, BigDecimal.ROUND_HALF_UP);
			cart_express_money.setText("快递费用："+yunfei+"元");
			cart_yunfei_money.setText("配送费用：￥"+yunfei);
		}else{
			cart_express_money.setText("快递费用： 0.00元");
			cart_yunfei_money.setText("配送费用：￥0.00");
		}
		if(null!=t.getEo()){
			if(null!=t.getEo().getInvoiceTitle()){
				cart_invoice_textview1.setText(t.getEo().getInvoiceTitle());
			}
		}
		if(null!=t.getGoodsPrice()){
			cart_allproduct_money.setText("商品价格：￥"+t.getGoodsPrice());
		}else{
			cart_allproduct_money.setText("商品价格：￥0.00");
		}
		if(null!=t.getYouhui()){
			cart_favorable_money.setText("优惠折扣：-￥"+t.getYouhui());
		}else{
			cart_favorable_money.setText("优惠折扣：-￥0.00");
		}
		if(null!=t.getXindou()){
			xindou=StrToNumber.strToint(t.getXindou());
			cart_current_xindou.setText("可用新豆"+xindou+",抵 ￥"+xindou/100);
		}else{
			cart_current_xindou.setText("可用新豆0,抵 ￥0.00");
		}
		//TODO 计算总支付费用
		double allmoney = StrToNumber.strTodouble(t.getGoodsPrice())+StrToNumber.strTodouble(t.getYunfei())-StrToNumber.strTodouble(t.getYouhui());
		orderallmoney=new BigDecimal(allmoney).setScale(2, BigDecimal.ROUND_HALF_UP);
		cartorder_allmoney.setText("应付总额 ：￥"+orderallmoney);
	}
	/**
	 * 去创建订单
	 */
	protected void goCreatOrder() {
		if(flagaddress==0){
			if(addressid==-1){
				Toast.makeText(CartOrderDetailActivity.this, "请选择收货地址！", 0).show();
				return;
			}
		}
		if(flagaddress==1){
			if(adaptermembaer.flagposition==-1){
				Toast.makeText(CartOrderDetailActivity.this, "请选择一起配送的会员卡！", 0).show();
				return;
			}
		}
		
		int userid = 0;
		if(ShopApplication.isLogin){
			if(ShopApplication.loginflag==1){
			     userid = ShopApplication.userid;
			}
			if(ShopApplication.loginflag==2){
				 userid = ShopApplication.useridother;
			}
		}
		final CreateOrderDTO create=new CreateOrderDTO();
		create.setEnnMemId(userid+"");
		if(xindouflag==2){
			create.setXindou(xindou+"");
		}
		if(xindouflag==1){
			create.setXindou(0+"");
		}
		EnnOrder order=new EnnOrder();
		order.setOrderType("01");
		order.setPayMethod(payMethod);
		order.setRemark(remark);
		order.setOrderFrom("07");
		//发票信息
		order.setIsNeed(invoiceflag);
		order.setInvoiceType("01");
		order.setInvoiceTitle(invoicetitle);
		if(flagaddress==0){
			//order.setIsBind("0");
		}
		if(flagaddress==1){
			order.setIsBind("1");
			order.setBindCard(adaptermembaer.get_list().get(adaptermembaer.flagposition).getEnnCard().getCardId());
		}
		create.setEnnOrder(order);
		List<OrderItemVO> orderItemVOs =new ArrayList<OrderItemVO>();
		for (Cart onecart : adapter.get_list()) {
			OrderItemVO one=new OrderItemVO();
			one.setGoodId(onecart.getGoodid()+"");
			one.setNum(onecart.getNum()+"");
			orderItemVOs.add(one);
		}
		if(orderItemVOs.size()==0){
			Toast.makeText(CartOrderDetailActivity.this, "当前配送地址下没有可购买的商品,请重新选择收货地址！", 0).show();
			return;
		}
		if(membercardpayflag==2){
			if(ishasmembercard==1){
				Toast.makeText(CartOrderDetailActivity.this, "您所选的商品中包含会员卡，不能使用会员卡支付！", 0).show();
				return;
			}
			create.setPayCardIds(membercardpaylist);
		}
		create.setOrderItemVOs(orderItemVOs);
		
		customProgressDialog.show();
		
		
		if(flagaddress==0){
			EnnMemberReceipt address=new EnnMemberReceipt();
			address.setReceId(addressid);
			create.setEnnMemberReceipt(address);
			VolleyUtil.sendObjectByPostToBean1(CommonVariable.CartCreatOrderURL, null, create, EnnOrder.class,new HttpBackListener<EnnOrder>() {
				
				@Override
				public void onSuccess(EnnOrder order) {
					customProgressDialog.dismiss();
					//TODO 跳转到去支付界面
					Intent payintent=new Intent(CartOrderDetailActivity.this,PayActivity.class);
					payintent.putExtra("orderseq", order.getOrderSeq());
					payintent.putExtra("orderid", order.getOrderId());
					startActivity(payintent);
					finish();
				}
				
				@Override
				public void onFail(String failstring) {
					customProgressDialog.dismiss();
					Toast.makeText(CartOrderDetailActivity.this,"提交订单失败！"+failstring, 0).show();
				}
				
				@Override
				public void onError(VolleyError error) {
					customProgressDialog.dismiss();
					Toast.makeText(CartOrderDetailActivity.this,"提交订单失败！", 0).show();
				}
			}, false, null);
		}
		if(flagaddress==1){
			MemCardVODetail membercard = adaptermembaer.get_list().get(adaptermembaer.flagposition);
			final EnnMemberReceipt address=new EnnMemberReceipt();
			address.setProvince(membercard.getEnnCardConfig().getProvince());
			address.setCity(membercard.getEnnCardConfig().getCity());
			address.setCountry(membercard.getEnnCardConfig().getCountry());
			address.setReceAddress(membercard.getEnnCardConfig().getReceAddress());
			address.setReceName(membercard.getEnnCardConfig().getReceName());
			address.setRecePhone(membercard.getEnnCardConfig().getRecePhone());
			create.setEnnMemberReceipt(address);
			VolleyUtil.sendObjectByPostToBean1(CommonVariable.CartCreatOrderURL, null, create, EnnOrder.class,new HttpBackListener<EnnOrder>() {
				
				@Override
				public void onSuccess(EnnOrder order) {
					customProgressDialog.dismiss();
					//TODO 跳转到去支付界面
					Intent payintent=new Intent(CartOrderDetailActivity.this,PayActivity.class);
					payintent.putExtra("orderseq", order.getOrderSeq());
					payintent.putExtra("orderid", order.getOrderId());
					startActivity(payintent);
					finish();
				}
				
				@Override
				public void onFail(String failstring) {
					customProgressDialog.dismiss();
					Toast.makeText(CartOrderDetailActivity.this,"提交订单失败！"+failstring, 0).show();
				}
				
				@Override
				public void onError(VolleyError error) {
					customProgressDialog.dismiss();
					Toast.makeText(CartOrderDetailActivity.this,"提交订单失败！", 0).show();
				}
			}, false, null);
		}
	}
	void  getshowgoodByaddressid(int addressid){
		ShowGoodsDTO showdto =new ShowGoodsDTO();
		if(flagaddress==0){
			showdto.setReceId(addressid);
		}
		if(flagaddress==1){
			showdto.setZone(addressid);
		}
		
		List<ShowGoodsItemsDTO> showgoodlist=new ArrayList<ShowGoodsItemsDTO>();
		for (Cart i : list) {
			ShowGoodsItemsDTO one=new ShowGoodsItemsDTO();
			one.setGoodsId(i.getGoodid()+"");
			one.setGoodsnum(i.getNum());
			showgoodlist.add(one);
		}
		showdto.setShowGoodsItemsDTOs(showgoodlist);
		VolleyUtil.sendObjectByPostToBean(CommonVariable.GetShowgoodByAddressidURL, null, showdto, ShowGoodsDTO.class, new HttpBackListener<ShowGoodsDTO>() {

			@Override
			public void onSuccess(ShowGoodsDTO t) {
				changeallmoney(t);
			}

			@Override
			public void onFail(String failstring) {
				
			}

			@Override
			public void onError(VolleyError error) {
			}
		}, false, null);
	}
	/**
	 * 改变金钱总数和列表
	 * @param t
	 */
	void changeallmoney(ShowGoodsDTO t){
		//TODO 地址选完之后的判断
		if(null!=t.getShowGoodsItemsDTOs()&&t.getShowGoodsItemsDTOs().size()>0){
			List<Cart>  canbuylist=new ArrayList<Cart>();
			List<Cart>  cannobuylist=new ArrayList<Cart>();
			for (ShowGoodsItemsDTO i : t.getShowGoodsItemsDTOs()) {
				for (Cart cart : list) {
					if(cart.getGoodid()==StrToNumber.strToint(i.getGoodsId())){
						if(i.getShow().equals("1")){
							canbuylist.add(cart);
							break;
						}
						if(i.getShow().equals("0")){
							cannobuylist.add(cart);
							break;
						}
					}
				}
			}
			if(cannobuylist.size()>0){
				nobuy_lin.setVisibility(View.VISIBLE);
				nobuyadapter.set_list(cannobuylist);
				nobuyadapter.notifyDataSetChanged();
			}else{
				nobuy_lin.setVisibility(View.GONE);
			}
			adapter.set_list(canbuylist);
			adapter.notifyDataSetChanged();
		}
		if(null!=t.getYunfei()){
			BigDecimal yunfei=t.getYunfei();
			yunfei=yunfei.setScale(2, BigDecimal.ROUND_HALF_UP);
			cart_express_money.setText("快递费用："+yunfei+"元");
			cart_yunfei_money.setText("配送费用：￥"+yunfei);
		}else{
			cart_express_money.setText("快递费用：0.00元");
			cart_yunfei_money.setText("配送费用：￥0.00");
		}
		//TODO 地址改变之后计算总支付费用并把会员卡支付的给置为0
		membercardpay_textview.setText("已抵：0.00");
		cart_membercardpay_money.setText("会员卡：-￥ 0.00");
		membercardpaylist.clear();
		if(membercardpaylistgo!=null){
			membercardpaylistgo.clear();
		}
		membercardpayflag=1;
		
		if(null!=t.getTotalPrice()){
			orderallmoney=t.getTotalPrice();
			cartorder_allmoney.setText("应付总额：￥ "+t.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
			double goodmoney = t.getTotalPrice().doubleValue()-t.getYunfei().doubleValue();
			cart_allproduct_money.setText("商品价格：￥"+new BigDecimal(goodmoney).setScale(2, BigDecimal.ROUND_HALF_UP));
		}else{
			cartorder_allmoney.setText("应付总额：￥0.00");
			cart_allproduct_money.setText("商品价格：￥0.00");
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==03&&resultCode==03){
			//收货地址回调回来的
			addressid=data.getIntExtra("addressid",-1);
			getshowgoodByaddressid(addressid);
			String province=data.getStringExtra("province");
			String city=data.getStringExtra("city");
			String country=data.getStringExtra("country");
			String receAddress=data.getStringExtra("receAddress");
			
			String recename=data.getStringExtra("recename");
			String recephone=data.getStringExtra("recephone");
			
			VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetAreaDetailURL+country, null, null, EnnSysArea.class, new HttpBackListener<EnnSysArea>() {

				@Override
				public void onSuccess(EnnSysArea t) {
					cartorder_address_textview.setText(t.getAreaAllName());
				}

				@Override
				public void onFail(String failstring) {
					cartorder_address_textview.setText("");
				}

				@Override
				public void onError(VolleyError error) {
					cartorder_address_textview.setText("");
				}
			}, false, null);
			
			cartorder_address_textview1.setText(receAddress);
			cartorder_address_textview2.setText(recename+" "+recephone);
		}
		if(requestCode==02&&resultCode==02){
			//用户发票信息
			String invoicetext=data.getStringExtra("text");
			if(null!=invoicetext&&!"".equals(invoicetext)){
				invoiceflag="1";
				invoicetitle=invoicetext;
			}
			cart_invoice_textview1.setText(invoicetext);
		}
		if(requestCode==04&&resultCode==02){
			//用户备注信息
			String remarkstr=data.getStringExtra("remarkstr");
			cart_remark_textview1.setText(remarkstr);
			remark=remarkstr;
		}
		if(requestCode==05&&resultCode==02){
			//TODO 会员卡支付信息
			membercardpaylistgo =(List<MemCardVO>)data.getSerializableExtra("list");
			
			if(null!=membercardpaylistgo&&membercardpaylistgo.size()>0){
				membercardpayflag=2;
				membercardpaylist.clear();
				BigDecimal membercardpayallmoney=new BigDecimal(0);
				for (MemCardVO memCardVO : membercardpaylistgo) {
					membercardpayallmoney=membercardpayallmoney.add(memCardVO.getEnnCard().getCardRemain());
					membercardpaylist.add(memCardVO.getEnnCard().getCardId());
				}
				if(membercardpayallmoney.compareTo(orderallmoney)==1){
					membercardpay_textview.setText("已抵："+orderallmoney.setScale(2,BigDecimal.ROUND_HALF_UP));
					cartorder_allmoney.setText("应付总额：￥0.00");
					cart_membercardpay_money.setText("会员卡：-￥"+orderallmoney.setScale(2,BigDecimal.ROUND_HALF_UP));
				}else{
					membercardpay_textview.setText("已抵："+membercardpayallmoney.setScale(2,BigDecimal.ROUND_HALF_UP));
					cartorder_allmoney.setText("应付总额：￥"+orderallmoney.subtract(membercardpayallmoney));
					cart_membercardpay_money.setText("会员卡：-￥"+membercardpayallmoney.setScale(2,BigDecimal.ROUND_HALF_UP));
				}
			}else{
				membercardpayflag=1;
				membercardpay_textview.setText("已抵：￥0.00");
				cartorder_allmoney.setText("应付总额：￥"+orderallmoney.setScale(2,BigDecimal.ROUND_HALF_UP));
				cart_membercardpay_money.setText("会员卡：-￥0.00");
			}
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
