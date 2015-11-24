package com.xyyy.shop.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.util.VolleyUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xyyy.shop.R;
import com.xyyy.shop.model.Delivery;
import com.xyyy.shop.model.OrderDeliveItemDTO;
import com.xyyy.shop.toolUtil.DateUtil;
import com.xyyy.shop.toolUtil.StrToNumber;
/**
 * 本次配送清单的适配器
 * @author lxn
 *
 */
public class MythisdeliveryItemAdapter extends BaseAdapter {
	private Context _context;
	private List<OrderDeliveItemDTO> _list;

	public MythisdeliveryItemAdapter(Context context, List<OrderDeliveItemDTO> list) {
		super();
		this._context = context;
		this._list = list;
	}

	public List<OrderDeliveItemDTO> get_list() {
		return _list;
	}

	public void set_list(List<OrderDeliveItemDTO> _list) {
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
		OrderDeliveItemDTO oneitem = (OrderDeliveItemDTO) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_thisdelivery_item,
					null);
			holder = new Holder();
			holder.item_img = (ImageView) convertView
					.findViewById(R.id.item_img);
			holder.item_type = (TextView) convertView
					.findViewById(R.id.item_type);
			holder.item_cardtype = (TextView) convertView
					.findViewById(R.id.item_cardtype);
			holder.item_time = (TextView) convertView
					.findViewById(R.id.item_time);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		 DisplayImageOptions options = new DisplayImageOptions.Builder()  
         .showImageOnLoading(R.drawable.loadingimg60)         // 设置图片下载期间显示的图片    60*60
         .showImageForEmptyUri(R.drawable.loadingimg60)  // 设置图片Uri为空或是错误的时候显示的图片  
         .showImageOnFail(R.drawable.loadingimg60)       // 设置图片加载或解码过程中发生错误显示的图片      
         .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
         .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
         .build();                                   // 创建配置过得DisplayImageOption对象  
		ImageLoader.getInstance().displayImage(oneitem.getImgUrl(),holder.item_img,options);
		if(null!=oneitem.getEnnCard()){
			String name="";
			String type="";
			if(null!=oneitem.getEnnCardType()&&null!=oneitem.getEnnCardType().getCardName()){
				name=oneitem.getEnnCardType().getCardName();
				//holder.item_type.setText(oneitem.getEnnCardType().getCardName());
			}
			int cardtype=StrToNumber.strToint(oneitem.getEnnCardType().getCardType());
			switch (cardtype) {
			case 1:
				holder.item_cardtype.setText("[年卡]");
				type="[年卡]";
				break;
			case 2:
				holder.item_cardtype.setText("[半年卡]");
				type="[半年卡]";
				break;
			case 3:
				holder.item_cardtype.setText("[季卡]");
				type="[季卡]";
				break;
			case 4:
				holder.item_cardtype.setText("[月卡]");
				type="[月卡]";
				break;
			case 5:
				holder.item_cardtype.setText("[体验卡]");
				type="[体验卡]";
				break;
			default:
				break;
			}
			holder.item_type.setText(Html.fromHtml(name + "<font color=\"#45A63A\">"+type+"</font>"));
		}
		if(null!=oneitem.getEnnOrder()&&null!=oneitem.getEnnOrder().getMemReceDate()){
			holder.item_time.setText("配送时间:"+DateUtil.getDateTimeAndWeek(oneitem.getEnnOrder().getMemReceDate()));
		}
		return convertView;
	}

	private static class Holder {
		ImageView item_img;
		TextView item_cardtype;
		TextView item_type;
		TextView item_time;
	}
}
