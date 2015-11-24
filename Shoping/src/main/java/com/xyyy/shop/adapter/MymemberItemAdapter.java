package com.xyyy.shop.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xyyy.shop.R;
import com.xyyy.shop.model.MemCardVO;
import com.xyyy.shop.toolUtil.StrToNumber;
/**
 * 我的会员卡适配器
 * @author lxn
 *
 */
public class MymemberItemAdapter extends BaseAdapter {
	private Context _context;
	private List<MemCardVO> _list;

	public MymemberItemAdapter(Context context, List<MemCardVO> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		MemCardVO oneitem = (MemCardVO) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_mymember_item,
					null);
			holder = new Holder();
			holder.item_rel = (RelativeLayout) convertView
					.findViewById(R.id.item_rel);
			holder.item_membertype = (TextView) convertView
					.findViewById(R.id.item_membertype);
			holder.item_memberid = (TextView) convertView
					.findViewById(R.id.item_memberid);
			holder.card_remainsize = (TextView) convertView
					.findViewById(R.id.card_remainsize);
			holder.card_remainmoney = (TextView) convertView
					.findViewById(R.id.card_remainmoney);
			holder.item_button = (TextView) convertView
					.findViewById(R.id.item_button);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(null!=oneitem.getEnnCard()&&null!=oneitem.getEnnCard().getCardCode()){
			holder.item_memberid.setText(oneitem.getEnnCard().getCardCode());
		}else{
			holder.item_memberid.setText("NO.0");
		}

		int cardflag=Integer.parseInt(oneitem.getEnnCard().getCardState());
		switch (cardflag) {
		case 0:
			//0 尚未启用 1 使用中 2 已暂定4 已用完
			holder.item_rel.setBackgroundResource(R.drawable.membercard_list_state01);
			holder.item_button.setText("未启用");
			holder.item_button.setBackgroundResource(R.drawable.membercard_list_backgrouditembuttonstate01);
			break;
		case 1:
			//使用中
			holder.item_rel.setBackgroundResource(R.drawable.membercard_list_state02);
			holder.item_button.setText("启用中");
			holder.item_button.setBackgroundResource(R.drawable.membercard_list_backgrouditembuttonstate02);
			break;
		case 2:
			//已暂停
			holder.item_rel.setBackgroundResource(R.drawable.membercard_list_state03);
			holder.item_button.setText("暂停中");
			holder.item_button.setBackgroundResource(R.drawable.membercard_list_backgrouditembuttonstate03);
			break;
		case 4:
			//已用完
			holder.item_rel.setBackgroundResource(R.drawable.membercard_list_state04);
			holder.item_button.setText("已用完");
			holder.item_button.setBackgroundResource(R.drawable.membercard_list_backgrouditembuttonstate04);
			break;

		default:
			break;
		}
		if(null!=oneitem.getEnnCardType().getServiceType()&&oneitem.getEnnCardType().getServiceType().equals("01")){
			//服务性
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
			holder.item_membertype.setText(membertype.toString());
			holder.card_remainsize.setVisibility(View.VISIBLE);
			if(null!=oneitem.getEnnCard()&&null!=oneitem.getEnnCard().getCardRenum()){
				holder.card_remainsize.setText("剩余次数:"+oneitem.getEnnCard().getCardRenum()+"次");
			}else{
				holder.card_remainsize.setText("剩余次数:0次");
			}
			
		}
		if(null!=oneitem.getEnnCardType().getServiceType()&&oneitem.getEnnCardType().getServiceType().equals("02")){
			//非服务型会员卡
			holder.item_membertype.setText("非服务型会员卡");
			holder.card_remainsize.setVisibility(View.GONE);
			holder.item_rel.setBackgroundResource(R.drawable.membercard_list_state02);
			holder.item_button.setText("启用中");
			holder.item_button.setBackgroundResource(R.drawable.membercard_list_backgrouditembuttonstate02);
		}
		//已冻结判断
		if(null!=oneitem.getEnnCard()&&null!=oneitem.getEnnCard().getIsFreeze()){
			if(oneitem.getEnnCard().getIsFreeze().equals("1")){
				//改卡已冻结
				holder.item_rel.setBackgroundResource(R.drawable.membercard_list_state05);
				holder.item_button.setText("已冻结");
				holder.item_button.setBackgroundResource(R.drawable.membercard_list_backgrouditembuttonstate05);
			}
		}
		
		
		//剩余金额 
		if(null!=oneitem.getEnnCard()&&null!=oneitem.getEnnCard().getCardRemain()){
			holder.card_remainmoney.setText("余额:￥"+oneitem.getEnnCard().getCardRemain().setScale(2,BigDecimal.ROUND_HALF_UP));
		}else{
			holder.card_remainmoney.setText("余额:￥0.00");
		}
		return convertView;
	}

	private static class Holder {
		RelativeLayout item_rel;
		TextView item_membertype;
		TextView item_memberid;
		TextView card_remainsize;
		TextView card_remainmoney;
		TextView item_button;
	}

}
