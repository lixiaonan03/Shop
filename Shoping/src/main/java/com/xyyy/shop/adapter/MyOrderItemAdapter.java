package com.xyyy.shop.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.activity.OrderDetailActivity;
import com.xyyy.shop.activity.OrderEvaluateActivity;
import com.xyyy.shop.activity.OtherWebviewActivity;
import com.xyyy.shop.activity.PayActivity;
import com.xyyy.shop.model.OrderItemVO;
import com.xyyy.shop.model.OrderItemVO2;
import com.xyyy.shop.model.OrderVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.view.MyListView;
/**
 * 我的订单适配器
 * @author lxn
 *o
 */
public class MyOrderItemAdapter extends BaseAdapter {
	private Context _context;
	private List<OrderVO> _list;

	public MyOrderItemAdapter(Context context, List<OrderVO> list) {
		super();
		this._context = context;
		this._list = list;
	}

	public List<OrderVO> get_list() {
		return _list;
	}

	public void set_list(List<OrderVO> _list) {
		this._list = _list;
	}

	@Override
	public int getCount() {
		return _list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return _list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final OrderVO oneitem = (OrderVO) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_myorder_item,
					null);
			holder = new Holder();
			holder.item_allmoney = (TextView) convertView
					.findViewById(R.id.item_allmoney);
			
			holder.item_button = (Button) convertView
					.findViewById(R.id.item_button);
			
			holder.onesizeRel = (RelativeLayout) convertView
					.findViewById(R.id.onesizeRel);
			holder.twosizeLin = (LinearLayout) convertView
					.findViewById(R.id.twosizeLin);
			holder.item_img = (ImageView) convertView
					.findViewById(R.id.item_img);
			holder.item_img1 = (ImageView) convertView
					.findViewById(R.id.item_img1);
			holder.item_img2 = (ImageView) convertView
					.findViewById(R.id.item_img2);
			holder.item_goodname = (TextView) convertView
					.findViewById(R.id.item_goodname);
			
			/*holder.itemlistview = (MyListView) convertView
					.findViewById(R.id.itemlistview);*/
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
	    if(null!=oneitem.getTotalPrice()){
	    	holder.item_allmoney.setText("￥"+oneitem.getTotalPrice());
	    }
	    /**
		 * 订单状态  01 去付款  02 查看物流 03 去评价  04 再次购买
		 */
		int flag=StrToNumber.strToint(oneitem.getOrderStatus());
		switch (flag) {
		case 1:
			holder.item_button.setText("去付款");
			holder.item_button.setBackgroundResource(R.drawable.login_button);
			holder.item_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO 待付款的订单   去付款按钮点击事件
					Intent payintent=new Intent(_context,PayActivity.class);
					payintent.putExtra("orderid", StrToNumber.strToint(oneitem.getOrderId()));
					_context.startActivity(payintent);
				}
			});
			break;
		case 2:
		case 3:
		case 4:
			holder.item_button.setText("查看物流");
			holder.item_button.setBackgroundResource(R.drawable.login_buttonap);
            holder.item_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO 待收货的订单   去查看物流按钮点击事件
					
				}
			});
			break;
		case 5:
			holder.item_button.setText("去评价");
			holder.item_button.setBackgroundResource(R.drawable.login_button);
			holder.item_button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO 待评价的订单 商品评价按钮点击事件
						int userid = 0;
						if (ShopApplication.isLogin) {
							if(ShopApplication.loginflag==1){
								userid=ShopApplication.userid;
							}
							if(ShopApplication.loginflag==2){
								userid=ShopApplication.useridother;
							}
						}
						Intent intent=new Intent(_context, OrderEvaluateActivity.class);
						intent.putExtra("url", CommonVariable.OrderEvaluateURL+oneitem.getOrderId()+"&userId="+userid);
						_context.startActivity(intent);
					}
			});
			break;
		case 7:
			holder.item_button.setText("交易完成");
			holder.item_button.setBackgroundResource(R.drawable.login_button);
			break;
		default:
			break;
		}
		List<OrderItemVO> ordergoodlistvo1 = oneitem.getOrderItems();
		List<OrderItemVO2> ordergoodlistvo2 = new  ArrayList<OrderItemVO2>();
		for (OrderItemVO orderItemVO : ordergoodlistvo1) {
			OrderItemVO2 vo2=new OrderItemVO2();
			if(null==orderItemVO.getGoodId())orderItemVO.setGoodId("0");
			vo2.setGoodId(orderItemVO.getGoodId());
			if(null==orderItemVO.getGoodName())orderItemVO.setGoodName("");
			vo2.setGoodName(orderItemVO.getGoodName());
			if(null==orderItemVO.getImgURL())orderItemVO.setImgURL("");
			vo2.setImgURL(orderItemVO.getImgURL());
			if(null==orderItemVO.getNum())orderItemVO.setNum("0");
			vo2.setNum(orderItemVO.getNum());
			if(null==orderItemVO.getPrice())orderItemVO.setPrice("0");
			vo2.setPrice(orderItemVO.getPrice());
			ordergoodlistvo2.add(vo2);
		}
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions    60*60
        DisplayImageOptions options = new DisplayImageOptions.Builder()  
            .showImageOnLoading(R.drawable.loadingimg60)         // 设置图片下载期间显示的图片  
            .showImageForEmptyUri(R.drawable.loadingimg60)  // 设置图片Uri为空或是错误的时候显示的图片  
            .showImageOnFail(R.drawable.loadingimg60)       // 设置图片加载或解码过程中发生错误显示的图片      
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
            .build();                                   // 创建配置过得DisplayImageOption对象  
		if(ordergoodlistvo2.size()>0&&ordergoodlistvo2.size()==1){
			holder.twosizeLin.setVisibility(View.GONE);
			holder.onesizeRel.setVisibility(View.VISIBLE);
			OrderItemVO2 onesize = (OrderItemVO2)ordergoodlistvo2.get(0);
			ImageLoader.getInstance().displayImage(onesize.getImgURL(), holder.item_img,options);
			if(null!=onesize.getGoodName()){
				holder.item_goodname.setText(onesize.getGoodName());
			}
		}
		if(ordergoodlistvo2.size()>1){
			holder.twosizeLin.setVisibility(View.VISIBLE);
			holder.onesizeRel.setVisibility(View.GONE);
			OrderItemVO2 twosize1 = (OrderItemVO2)ordergoodlistvo2.get(0);
			OrderItemVO2 twosize2 = (OrderItemVO2)ordergoodlistvo2.get(1);
			ImageLoader.getInstance().displayImage(twosize1.getImgURL(), holder.item_img1,options);
			ImageLoader.getInstance().displayImage(twosize2.getImgURL(), holder.item_img2,options);
		}
		/*MyOrderItemItemAdapter adapter = new MyOrderItemItemAdapter(_context, ordergoodlistvo2);
		holder.itemlistview.setAdapter(adapter);
		holder.itemlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(_context,OrderDetailActivity.class);
				intent.putExtra("id", oneitem.getOrderId());
				_context.startActivity(intent);
			}
		});*/
		return convertView;
	}
 
	private static class Holder {
		TextView item_allmoney;
		Button item_button;
		//MyListView itemlistview;
		RelativeLayout onesizeRel;
		LinearLayout twosizeLin;
		
		ImageView item_img;
		ImageView item_img1;
		ImageView item_img2;
		TextView item_goodname;
	}

}
