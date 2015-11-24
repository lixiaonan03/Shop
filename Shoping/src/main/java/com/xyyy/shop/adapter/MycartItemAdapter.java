package com.xyyy.shop.adapter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.util.VolleyUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.db.Cart;
import com.xyyy.shop.db.CartDao;
import com.xyyy.shop.toolUtil.CommonVariable;
/**
 * 我的购物车适配器
 * @author lxn
 *
 */
public class MycartItemAdapter extends BaseAdapter {
	private Context _context;
	private List<Cart> _list;
    private int flag;
    
    private int goodnum;//当前商品数量
	public MycartItemAdapter(Context context, List<Cart> list) {
		super();
		this._context = context;
		this._list = list;
	}
    
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<Cart> get_list() {
		return _list;
	}

	public void set_list(List<Cart> _list) {
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
		final Cart oneitem = (Cart) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_cart_item,
					null);
			holder = new Holder();
			holder.item_checkBox = (CheckBox) convertView
					.findViewById(R.id.item_checkBox);
			holder.item_img = (ImageView) convertView
					.findViewById(R.id.item_img);
			holder.item_goodname = (TextView) convertView
					.findViewById(R.id.item_goodname);
			holder.item_reduce = (ImageView) convertView
					.findViewById(R.id.item_reduce);
			holder.item_count = (TextView) convertView
					.findViewById(R.id.item_count);
			holder.item_add = (ImageView) convertView
					.findViewById(R.id.item_add);
			holder.item_money = (TextView) convertView
					.findViewById(R.id.item_money);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(oneitem.getFlag()==1){
			holder.item_checkBox.setChecked(false);
		}else{
			holder.item_checkBox.setChecked(true);
		}
		holder.item_checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1){
					oneitem.setFlag(0);
					Intent intent = new Intent("changeallmoney");
					LocalBroadcastManager.getInstance(_context).sendBroadcast(intent);
				}else{
					oneitem.setFlag(1);
					Intent intent = new Intent("changeallmoneyand");
					LocalBroadcastManager.getInstance(_context).sendBroadcast(intent);
				}
				
			}
		});
		 // 使用DisplayImageOptions.Builder()创建DisplayImageOptions    60*60
        DisplayImageOptions options = new DisplayImageOptions.Builder()  
            .showImageOnLoading(R.drawable.loadingimg60)         // 设置图片下载期间显示的图片  
            .showImageForEmptyUri(R.drawable.loadingimg60)  // 设置图片Uri为空或是错误的时候显示的图片  
            .showImageOnFail(R.drawable.loadingimg60)       // 设置图片加载或解码过程中发生错误显示的图片      
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
            .build();                                   // 创建配置过得DisplayImageOption对象  
        ImageLoader.getInstance().displayImage(oneitem.getGoodimgurl(), holder.item_img,options);
		//VolleyUtil.disPlayImage(holder.item_img,"", R.drawable.ic_launcher, R.drawable.ic_launcher);
		holder.item_goodname.setText(oneitem.getGoodname());
		holder.item_count.setText(oneitem.getNum()+"");
		
		holder.item_reduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 减一处理  做登录判断处理
				goodnum = oneitem.getNum();
				if(ShopApplication.isLogin){
					//在登录的情况下 调用接口提交数据
					if(goodnum>=2){
						goodnum=goodnum-1;
					}
					int memberid=0;
					if(ShopApplication.loginflag==1){
						memberid=ShopApplication.userid;
					}
					if(ShopApplication.loginflag==2){
						memberid=ShopApplication.useridother;
					}
					Map<String, String> map=new HashMap<String, String>();
					map.put("cartNum", goodnum+"");
					map.put("membId", memberid+"");
					map.put("goodsId", oneitem.getGoodid()+"");
				    VolleyUtil.sendJsonRequestByPost(CommonVariable.CartUpdateGoodURL, null, map, new HttpBackBaseListener() {
						
						@Override
						public void onSuccess(String string) {
							oneitem.setNum(goodnum);
							Intent intent = new Intent("changeallmoney");
							LocalBroadcastManager.getInstance(_context).sendBroadcast(intent);
							notifyDataSetChanged();
						}
						@Override
						public void onFail(String failstring) {
						}
						@Override
						public void onError(VolleyError error) {
						}
					}, false, null);
				}else{
					//TODO  未登录的情况下 修改数据库的数据
					if(goodnum>=2){
						goodnum=goodnum-1;
					}
					oneitem.setNum(goodnum);
					CartDao.getInstance().updateoneGood(oneitem);
					notifyDataSetChanged();
					Intent intent = new Intent("changeallmoney");
					LocalBroadcastManager.getInstance(_context).sendBroadcast(intent);
				}
				
			}
		});
		holder.item_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 加一处理
				goodnum = oneitem.getNum();
				if(ShopApplication.isLogin){
					//在登录的情况下 调用接口提交数据
						goodnum=goodnum+1;
					int memberid=0;
					if(ShopApplication.loginflag==1){
						memberid=ShopApplication.userid;
					}
					if(ShopApplication.loginflag==2){
						memberid=ShopApplication.useridother;
					}
					Map<String, String> map=new HashMap<String, String>();
					map.put("cartNum", goodnum+"");
					map.put("membId", memberid+"");
					map.put("goodsId", oneitem.getGoodid()+"");
				    VolleyUtil.sendJsonRequestByPost(CommonVariable.CartUpdateGoodURL, null, map, new HttpBackBaseListener() {
						
						@Override
						public void onSuccess(String string) {
							Intent intent = new Intent("changeallmoney");
							LocalBroadcastManager.getInstance(_context).sendBroadcast(intent);
							oneitem.setNum(goodnum);
							notifyDataSetChanged();
						}
						@Override
						public void onFail(String failstring) {
						}
						@Override
						public void onError(VolleyError error) {
						}
					}, false, null);
				}else{
					//TODO  未登录的情况下 修改数据库的数据
					goodnum=goodnum+1;
					oneitem.setNum(goodnum);
					CartDao.getInstance().updateoneGood(oneitem);
					Intent intent = new Intent("changeallmoney");
					LocalBroadcastManager.getInstance(_context).sendBroadcast(intent);
					notifyDataSetChanged();
				}
			}
		});
		if(null!=oneitem.getGoodprice()){
			holder.item_money.setText("￥"+oneitem.getGoodprice().setScale(2, BigDecimal.ROUND_HALF_UP));
		}else{
			holder.item_money.setText("￥0.00");
		}
		return convertView;
	}

	private static class Holder {
		CheckBox item_checkBox;
		ImageView item_img;
		TextView item_goodname;
		ImageView item_reduce;
		TextView item_count;
		ImageView item_add;
		TextView item_money;
	}

}
