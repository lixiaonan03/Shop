package com.xyyy.shop.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xyyy.shop.R;
import com.xyyy.shop.model.MemCardVO;
import com.xyyy.shop.toolUtil.StrToNumber;

/**
 * 用户会员卡支付的adapter
 * @author lxn
 *
 */
public class UserMembercardPayItemAdapter extends BaseAdapter {
	private Context _context;
	private List<MemCardVO> _list;
	public UserMembercardPayItemAdapter(Context context, List<MemCardVO> list) {
		super();
		this._context = context;
		this._list = list;
	}

	public List<MemCardVO> get_list() {
		return _list;
	}

	public void set_list(List<MemCardVO> _list) {
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
		final MemCardVO oneitem = (MemCardVO) getItem(position);
		final Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_usermembercardpay_item,
					null);
			holder = new Holder();
			holder.item_checkbox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			holder.remaintext = (TextView) convertView
					.findViewById(R.id.remaintext);
			holder.item_text = (TextView) convertView
					.findViewById(R.id.textview);
			holder.item_text1 = (TextView) convertView
					.findViewById(R.id.textview1);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(null!=oneitem.getEnnCard()&&null!=oneitem.getEnnCard().getCardCode()){
			holder.item_text1.setText(oneitem.getEnnCard().getCardCode());
		}else{
			holder.item_text1.setText("NO.0");
		}
		StringBuilder  membertype=new StringBuilder();
		if(null!=oneitem.getEnnCardType()&&null!=oneitem.getEnnCardType().getCardKind()){
			int cardkind=StrToNumber.strToint(oneitem.getEnnCardType().getCardKind());
			switch (cardkind) {
			case 1:
				membertype.append("安全蔬菜");
				break;
			case 2:
				membertype.append("生态柴鸡蛋");
				break;
			case 3:
				membertype.append("生态猪肉");
				break;
			case 4:
				membertype.append("生态散养柴鸡");
				break;
			case 5:
				membertype.append("生态大米");
				break;
			case 6:
				membertype.append("生态杂粮");
				break;
			case 7:
				membertype.append("生态粮油");
				break;
			case 10:
				membertype.append("礼品卡");
				break;
			default:
				break;
			}	
		}
		if(null!=oneitem.getEnnCardType()&&null!=oneitem.getEnnCardType().getCardType()){
			int cardtype=StrToNumber.strToint(oneitem.getEnnCardType().getCardType());
			switch (cardtype) {
			case 1:
				membertype.append("年卡");
				break;
			case 2:
				membertype.append("半年卡");
				break;
			case 3:
				membertype.append("季卡");
				break;
			default:
				break;
			}	
		}
		if(null!=oneitem.getEnnCardType()&&null!=oneitem.getEnnCardType().getIsElect()){
			int iselect=StrToNumber.strToint(oneitem.getEnnCardType().getIsElect());
			switch (iselect) {
			case 0:
				membertype.append("(实体)");
				break;
			case 1:
				membertype.append("(电子)");
				break;
			default:
				break;
			}	
		}
		if(null!=oneitem.getEnnCardType().getServiceType()&&oneitem.getEnnCardType().getServiceType().equals("02")){
			//非服务型会员卡
			holder.item_text.setText("非服务型会员卡");
		}else{
			holder.item_text.setText(membertype.toString());
		}
	    if(oneitem.getFlag()==1){
				holder.item_checkbox.setChecked(true);
		}else{
				holder.item_checkbox.setChecked(false);
		}
		if(null!=oneitem.getEnnCard().getCardRemain()){
			holder.remaintext.setText("余额："+oneitem.getEnnCard().getCardRemain().setScale(2, BigDecimal.ROUND_HALF_UP)+"元");
		}else{
			holder.remaintext.setText("余额：0.00元");
		}
		return convertView;
	}

	private static class Holder {
		CheckBox item_checkbox;
		TextView item_text;
		TextView remaintext;
		TextView item_text1;
	}

}
