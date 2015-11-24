package com.xyyy.shop.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xyyy.shop.R;
import com.xyyy.shop.model.EnnCardRecord;
import com.xyyy.shop.model.EnnPayRecordVO;
import com.xyyy.shop.toolUtil.DateUtil;
import com.xyyy.shop.toolUtil.StrToNumber;

/**
 * 订单支付详细列表条目
 * @author lxn
 */
public class OrderPaydetailItemAdapter extends BaseAdapter {
	private Context _context;
	private List<EnnPayRecordVO> _list;
	public OrderPaydetailItemAdapter(Context context, List<EnnPayRecordVO> list) {
		super();
		this._context = context;
		this._list = list;
	}

	public List<EnnPayRecordVO> get_list() {
		return _list;
	}

	public void set_list(List<EnnPayRecordVO> _list) {
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

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		final EnnPayRecordVO oneitem = (EnnPayRecordVO) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_orderpaydetail_item,
					null);
			holder = new Holder();
			holder.paymoney_item = (TextView) convertView
					.findViewById(R.id.paymoney_item);
			holder.item_text = (TextView) convertView
					.findViewById(R.id.textview);
			holder.item_text1 = (TextView) convertView
					.findViewById(R.id.textview1);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		//判断支付方式
		if(null!=oneitem&&null!=oneitem.getEnnPayRecord()&&null!=oneitem.getEnnPayRecord().getPayMethod()){
			if(oneitem.getEnnPayRecord().getPayMethod().equals("05")){
				//会员卡支付
				if(null!=oneitem.getEnnPayRecord().getPayAmount()){
					holder.paymoney_item.setText(oneitem.getEnnPayRecord().getPayAmount().setScale(2,BigDecimal.ROUND_HALF_UP)+"");
				}
				holder.item_text1.setVisibility(View.VISIBLE);
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
				holder.item_text.setText(membertype.toString());
			}
			if(oneitem.getEnnPayRecord().getPayMethod().equals("02")){
				//在线支付
				if(null!=oneitem.getEnnPayRecord().getPayAmount()){
					holder.paymoney_item.setText(oneitem.getEnnPayRecord().getPayAmount().setScale(2,BigDecimal.ROUND_HALF_UP)+"");
				}
				holder.item_text.setText("微信支付");
				holder.item_text1.setVisibility(View.GONE);
				
			}
			if(oneitem.getEnnPayRecord().getPayMethod().equals("06")){
				//礼品卡支付
				if(null!=oneitem.getEnnPayRecord().getPayAmount()){
					holder.paymoney_item.setText(oneitem.getEnnPayRecord().getPayAmount().setScale(2,BigDecimal.ROUND_HALF_UP)+"");
				}
				holder.item_text.setText("礼品卡支付");
				holder.item_text1.setVisibility(View.GONE);
				
			}
		}
		return convertView;
	}

	private static class Holder {
		TextView paymoney_item;
		TextView item_text;
		TextView item_text1;
	}

}
