package com.xyyy.shop.adapter;

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
import com.xyyy.shop.model.GiftCardItem;
/**
 * 我的礼品卡商品适配器
 * @author lxn
 *
 */
public class MygiftItemgooditemAdapter extends BaseAdapter {
	private Context _context;
	private List<GiftCardItem> _list;

	public MygiftItemgooditemAdapter(Context context, List<GiftCardItem> list) {
		super();
		this._context = context;
		this._list = list;
	}

	public List<GiftCardItem> get_list() {
		return _list;
	}

	public void set_list(List<GiftCardItem> _list) {
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
		GiftCardItem oneitem = (GiftCardItem) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_mygift_gooditem,
					null);
			holder = new Holder();
			holder.item_img = (ImageView) convertView
					.findViewById(R.id.item_img);
			holder.item_goodname = (TextView) convertView
					.findViewById(R.id.item_goodname);
			holder.item_goodcount = (TextView) convertView
					.findViewById(R.id.item_goodcount);
			holder.item_goodprice = (TextView) convertView
					.findViewById(R.id.item_goodprice);
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
        ImageLoader.getInstance().displayImage(oneitem.getGoodsImg(), holder.item_img,options);
        if(null!=oneitem.getEnnGoods()&&null!=oneitem.getEnnGoods().getGoodsName()){
        	holder.item_goodname.setText(oneitem.getEnnGoods().getGoodsName());
        }else{
        	holder.item_goodname.setText("");
        }
        
		holder.item_goodcount.setText("X"+oneitem.getNumber());
		
		holder.item_goodprice.setText(oneitem.getSumPrice()+"");
		
		return convertView;
	}

	private static class Holder {
		ImageView item_img;
		TextView item_goodname;
		TextView item_goodcount;
		TextView item_goodprice;
	}

}
