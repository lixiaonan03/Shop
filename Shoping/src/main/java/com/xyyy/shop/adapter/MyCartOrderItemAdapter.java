package com.xyyy.shop.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xyyy.shop.R;
import com.xyyy.shop.db.Cart;
/**
 * 购物车订单展示商品类表使用的适配器
 * @author lxn
 *
 */
public class MyCartOrderItemAdapter extends BaseAdapter {
	private Context _context;
	private List<Cart> _list;

	public MyCartOrderItemAdapter(Context context, List<Cart> list) {
		super();
		this._context = context;
		this._list = list;
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
		Cart oneitem = (Cart) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_mycartorder_item,
					null);
			holder = new Holder();
			holder.item_img = (ImageView) convertView
					.findViewById(R.id.item_img);
			holder.item_goodname = (TextView) convertView
					.findViewById(R.id.item_goodname);
			holder.item_goodprice = (TextView) convertView
					.findViewById(R.id.item_goodprice);
			holder.item_goodcount = (TextView) convertView
					.findViewById(R.id.item_goodcount);
			holder.item_goodmoney = (TextView) convertView
					.findViewById(R.id.item_goodmoney);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		 // 使用DisplayImageOptions.Builder()创建DisplayImageOptions    60*60
        DisplayImageOptions options = new DisplayImageOptions.Builder()  
            .showImageOnLoading(R.drawable.loadingimg60)         // 设置图片下载期间显示的图片  
            .showImageForEmptyUri(R.drawable.loadingimg60)  // 设置图片Uri为空或是错误的时候显示的图片  
            .showImageOnFail(R.drawable.loadingimg60)       // 设置图片加载或解码过程中发生错误显示的图片      
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
            .build();                                   // 创建配置过得DisplayImageOption对象  
		ImageLoader.getInstance().displayImage(oneitem.getGoodimgurl(), holder.item_img,options);
		//VolleyUtil.disPlayImage(holder.item_img, oneitem.getGoodImgs().get(0).getImgPath(), R.drawable.ic_launcher,R.drawable.ic_launcher);
		if(null!=oneitem.getGoodname())
		holder.item_goodname.setText(oneitem.getGoodname());
		if(null!=oneitem.getGoodprice())
		holder.item_goodprice.setText("价格 : ￥"+oneitem.getGoodprice());
		holder.item_goodcount.setText("数量 ： "+oneitem.getNum());
		if(null!=oneitem.getGoodprice())
		holder.item_goodmoney.setText("小计 ： "+(oneitem.getGoodprice().multiply(new BigDecimal(oneitem.getNum()))).setScale(2, BigDecimal.ROUND_HALF_UP));
		return convertView;
	}

	private static class Holder {
		ImageView item_img;
		TextView item_goodname;
		TextView item_goodprice;
		TextView item_goodcount;
		TextView item_goodmoney;
	}

}
