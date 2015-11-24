package com.xyyy.shop.adapter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xyyy.shop.R;
import com.xyyy.shop.model.DeliveDetailItemDTO;
/**
 * 配送详情清单的适配器
 * @author lxn
 *
 */
public class DeliverydetailItemAdapter extends BaseAdapter {
	private Context _context;
	private List<DeliveDetailItemDTO> _list;
    
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener(); 
	
	public DeliverydetailItemAdapter(Context context, List<DeliveDetailItemDTO> list) {
		super();
		this._context = context;
		this._list = list;
	}

	public List<DeliveDetailItemDTO> get_list() {
		return _list;
	}

	public void set_list(List<DeliveDetailItemDTO> _list) {
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
		DeliveDetailItemDTO oneitem = (DeliveDetailItemDTO) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_deliverydetail_item,
					null);
			holder = new Holder();
			holder.item_img = (ImageView) convertView
					.findViewById(R.id.item_img);
			holder.item_goodname = (TextView) convertView
					.findViewById(R.id.item_goodname);
			holder.item_goodweight = (TextView) convertView
					.findViewById(R.id.item_goodweight);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		 // 使用DisplayImageOptions.Builder()创建DisplayImageOptions   60*60
        DisplayImageOptions options = new DisplayImageOptions.Builder()  
            .showImageOnLoading(R.drawable.loadingimg60)          // 设置图片下载期间显示的图片  
            .showImageForEmptyUri(R.drawable.loadingimg60)  // 设置图片Uri为空或是错误的时候显示的图片  
            .showImageOnFail(R.drawable.loadingimg60)       // 设置图片加载或解码过程中发生错误显示的图片      
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
            .build();                                   // 创建配置过得DisplayImageOption对象  
        if(null!=oneitem.getEnnDishes()){
        	 ImageLoader.getInstance().displayImage(oneitem.getEnnDishes().getDishsImg(), holder.item_img, options,animateFirstListener);
     		 if(null!=oneitem.getEnnDishes().getDishesName())
             holder.item_goodname.setText(oneitem.getEnnDishes().getDishesName());
        }
        if(null!=oneitem.getEnnOrderPick()){
        	if(null!=oneitem.getEnnOrderPick().getPickWeight()&&null!=oneitem.getEnnOrderPick().getPickNum()){
        		holder.item_goodweight.setText(oneitem.getEnnOrderPick().getPickWeight().multiply(new BigDecimal(oneitem.getEnnOrderPick().getPickNum())).multiply(new BigDecimal(1000))+"g");
        	}
        }
		return convertView;
	}

	private static class Holder {
		ImageView item_img;
		TextView item_goodweight;
		TextView item_goodname;
	}
	/** 
     * 图片加载第一次显示监听器 
     * @author Administrator 
     * 
     */  
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {  
          
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());  
  
        @Override  
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {  
            if (loadedImage != null) {  
                ImageView imageView = (ImageView) view;  
                // 是否第一次显示  
                boolean firstDisplay = !displayedImages.contains(imageUri);  
                if (firstDisplay) {  
                    // 图片淡入效果  
                    FadeInBitmapDisplayer.animate(imageView, 500);  
                    displayedImages.add(imageUri);  
                }  
            }  
        }  
    }  
}
