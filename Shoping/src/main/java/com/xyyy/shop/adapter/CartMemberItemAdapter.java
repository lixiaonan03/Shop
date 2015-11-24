package com.xyyy.shop.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.xyyy.shop.R;
import com.xyyy.shop.model.EnnSysArea;
import com.xyyy.shop.model.MemCardVODetail;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StringUtils;

/**
 * 去结算界面的会员卡的adapter
 * @author lxn
 *
 */
public class CartMemberItemAdapter extends BaseAdapter {
	private Context _context;
	private List<MemCardVODetail> _list;
	public int flagposition=-1;//记录条目选中的位置 -1没有默认的 
	public CartMemberItemAdapter(Context context, List<MemCardVODetail> list) {
		super();
		
		this._context = context;
		this._list = list;
	}

	public List<MemCardVODetail> get_list() {
		return _list;
	}

	public void set_list(List<MemCardVODetail> _list) {
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
	public View getView( final int position, View convertView, ViewGroup parent) {
		final MemCardVODetail oneitem = (MemCardVODetail) getItem(position);
		final Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_cartmember_item,
					null);
			holder = new Holder();
			holder.item_checkbox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			holder.item_text = (TextView) convertView
					.findViewById(R.id.textview);
			holder.item_text1 = (TextView) convertView
					.findViewById(R.id.textview1);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(null!=oneitem&&null!=oneitem.getEnnCardType()){
			if(null!=oneitem.getEnnCardType().getCardName()&&null!=oneitem.getEnnCard()&&null!=oneitem.getEnnCard().getCardCode()){
				holder.item_text.setText(oneitem.getEnnCardType().getCardName()+" "+oneitem.getEnnCard().getCardCode());
			}else{
				holder.item_text.setText("");
			}
		};
		if(null!=oneitem.getEnnCardConfig()){
			VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetAreaDetailURL+oneitem.getEnnCardConfig().getCountry(), null, null, EnnSysArea.class, new HttpBackListener<EnnSysArea>() {

				@Override
				public void onSuccess(EnnSysArea t) {
					if(null==t.getAreaAllName())t.setAreaAllName("");
					if(null==oneitem.getEnnCardConfig().getReceAddress())oneitem.getEnnCardConfig().setReceAddress("");
					holder.item_text1.setText(t.getAreaAllName()+" "+oneitem.getEnnCardConfig().getReceAddress());
				}

				@Override
				public void onFail(String failstring) {
					holder.item_text1.setText(" "+oneitem.getEnnCardConfig().getReceAddress());
				}

				@Override
				public void onError(VolleyError error) {
					holder.item_text1.setText(" "+oneitem.getEnnCardConfig().getReceAddress());
				}
			}, false, null);
			
		}
		if(oneitem.getIsDefault()!=null){
			if(oneitem.getIsDefault().equals("1")){
				flagposition=position;
				holder.item_checkbox.setChecked(true);
			}else{
				holder.item_checkbox.setChecked(false);
			}
		}else{
			holder.item_checkbox.setChecked(false);
		}
		return convertView;
	}

	private static class Holder {
		CheckBox item_checkbox;
		TextView item_text;
		TextView item_text1;
	}

}
