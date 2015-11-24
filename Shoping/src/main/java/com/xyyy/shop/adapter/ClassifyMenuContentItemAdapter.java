package com.xyyy.shop.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.util.VolleyUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xyyy.shop.R;
import com.xyyy.shop.model.EnnGoodsCat;
import com.xyyy.shop.toolUtil.CommonVariable;
/**
 * 商品分类右边九宫格条目的adapter
 * @author lxn
 *
 */
public class ClassifyMenuContentItemAdapter extends BaseAdapter {
	private Context _context;
	private List<EnnGoodsCat> _list;

	public ClassifyMenuContentItemAdapter(Context context, List<EnnGoodsCat> list) {
		super();
		this._context = context;
		this._list = list;
	}
	public List<EnnGoodsCat> get_list() {
		return _list;
	}

	public void set_list(List<EnnGoodsCat> _list) {
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
		EnnGoodsCat  oneitem = (EnnGoodsCat)getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_classifymenucontent_item,
					null);
			holder = new Holder();
			holder.item_img = (ImageView) convertView
					.findViewById(R.id.item_img);
			holder.item_name = (TextView) convertView
					.findViewById(R.id.item_name);
			holder.item_example = (TextView) convertView
					.findViewById(R.id.item_example);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		 // 使用DisplayImageOptions.Builder()创建DisplayImageOptions    76*76
        DisplayImageOptions options = new DisplayImageOptions.Builder()  
            .showImageOnLoading(R.drawable.loadingimg76)         // 设置图片下载期间显示的图片  
            .showImageForEmptyUri(R.drawable.loadingimg76)  // 设置图片Uri为空或是错误的时候显示的图片  
            .showImageOnFail(R.drawable.loadingimg76)       // 设置图片加载或解码过程中发生错误显示的图片      
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
           // .displayer(new RoundedBitmapDisplayer(180))  // 设置成圆角图片  
            .build();                                   // 创建配置过得DisplayImageOption对象  
        ImageLoader.getInstance().displayImage(oneitem.getCatImg(), holder.item_img, options);
        //VolleyUtil.disPlayImage(holder.item_img, oneitem.getCatImg(), R.drawable.ic_launcher, R.drawable.ic_launcher);
		holder.item_name.setText(oneitem.getCatName());
		return convertView;
	}

	private static class Holder {
		ImageView item_img;
		TextView item_name;
		TextView item_example;
	}

}
