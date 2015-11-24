package com.xyyy.shop.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xyyy.shop.R;
import com.xyyy.shop.model.EnnCardRule;
import com.xyyy.shop.toolUtil.StringUtils;
/**
 * 会员卡充值界面充值规则的adapter
 * @author lxn
 *
 */
public class CardRuleItemAdapter extends BaseAdapter {
	private Context _context;
	private List<EnnCardRule> _list;
	private int flagposition=-1;

	public CardRuleItemAdapter(Context context, List<EnnCardRule> list) {
		super();
		this._context = context;
		this._list = list;
	}

	public int getFlagposition() {
		return flagposition;
	}

	public void setFlagposition(int flagposition) {
		this.flagposition = flagposition;
	}

	public List<EnnCardRule> get_list() {
		return _list;
	}

	public void set_list(List<EnnCardRule> _list) {
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
		EnnCardRule  oneitem = (EnnCardRule)getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_cardrule_item,
					null);
			holder = new Holder();
			holder.item_name = (TextView) convertView
					.findViewById(R.id.item);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if(position==flagposition){
			holder.item_name.setSelected(true);
		}else{
			holder.item_name.setSelected(false);
		}
		if(StringUtils.isBlank(oneitem.getRemark()))oneitem.setRemark("");
		holder.item_name.setText(oneitem.getRemark());
		return convertView;
	}

	private static class Holder {
		TextView item_name;
	}

}
