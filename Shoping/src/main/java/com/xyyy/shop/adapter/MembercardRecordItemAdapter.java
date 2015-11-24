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
import com.xyyy.shop.toolUtil.DateUtil;
import com.xyyy.shop.toolUtil.StrToNumber;

/**
 * 会员卡消费记录的adapter
 * @author lxn
 */
public class MembercardRecordItemAdapter extends BaseAdapter {
	private Context _context;
	private List<EnnCardRecord> _list;
	private int isfuwu;
	public MembercardRecordItemAdapter(Context context, List<EnnCardRecord> list) {
		super();
		this._context = context;
		this._list = list;
	}
     
	public int getIsfuwu() {
		return isfuwu;
	}

	public void setIsfuwu(int isfuwu) {
		this.isfuwu = isfuwu;
	}

	public List<EnnCardRecord> get_list() {
		return _list;
	}

	public void set_list(List<EnnCardRecord> _list) {
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
		final EnnCardRecord oneitem = (EnnCardRecord) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_membercardrecord_item,
					null);
			holder = new Holder();
			holder.consume_money = (TextView) convertView
					.findViewById(R.id.consume_money);
			holder.item_text = (TextView) convertView
					.findViewById(R.id.textview);
			holder.item_text1 = (TextView) convertView
					.findViewById(R.id.textview1);
			holder.item_text2 = (TextView) convertView
					.findViewById(R.id.textview2);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(oneitem.getBlanceType().equals("01")){
			//收入
			holder.consume_money.setTextColor(_context.getResources().getColor(R.color.logo_color));
			holder.consume_money.setText("+"+oneitem.getAmount().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(oneitem.getBlanceType().equals("02")){
			//支出
			holder.consume_money.setTextColor(_context.getResources().getColor(R.color.red));
			holder.consume_money.setText("-"+oneitem.getAmount().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		StringBuilder remainstr=new StringBuilder();
		if(null!=oneitem.getCardRemain()){
			remainstr.append("余额:"+oneitem.getCardRemain().setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		if(null!=oneitem.getCardRenum()){
			remainstr.append("(剩余次数:"+oneitem.getCardRenum()+"次)");
		}
		if(isfuwu==1){
			holder.item_text1.setVisibility(View.GONE);
		}else{
			holder.item_text1.setVisibility(View.VISIBLE);
			holder.item_text1.setText(remainstr.toString());
		}
		
		if(null!=oneitem.getPaymentsTime())
		holder.item_text2.setText(DateUtil.getDateTime(oneitem.getPaymentsTime()));
		StringBuilder  textone=new StringBuilder();
		/**
	      * 消费记录类型
	            01-充值
	            02-赠送
	            03-消费
	            04-退款
	            99-其他
	      **/
		switch (StrToNumber.strToint(oneitem.getPaymentsType())) {
		case 1:
			textone.append("充值");
			break;
		case 2:
			textone.append("赠送");
			break;
		case 3:
			textone.append("消费");
			break;
		case 4:
			textone.append("退款");
			break;
		case 99:
			textone.append("其他");
			break;

		default:
			break;
		}
		if(null!=oneitem.getOrderSeq()){
			textone.append("(会员卡订单:"+oneitem.getOrderSeq()+")");
		}
		holder.item_text.setText(textone.toString());
		return convertView;
	}

	private static class Holder {
		TextView consume_money;
		TextView item_text;
		TextView item_text1;
		TextView item_text2;
	}

}
