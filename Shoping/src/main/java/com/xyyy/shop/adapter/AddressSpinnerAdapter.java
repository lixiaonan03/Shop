package com.xyyy.shop.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xyyy.shop.R;
import com.xyyy.shop.model.EnnSysArea;


public class AddressSpinnerAdapter  extends BaseAdapter{
	private Context _context;
	private List<EnnSysArea> _list;
	public void setList(List<EnnSysArea> _list) {
		this._list = _list;
	}

	public AddressSpinnerAdapter(Context context, List<EnnSysArea> list) {
		super();
		this._context = context;
		this._list = list;
	}

	@Override
	public int getCount() {
		if(_list==null){
			return 0;
		}
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
		EnnSysArea string=(EnnSysArea)getItem(position);
		Holder holder;
		if(null==convertView){
			convertView=View.inflate(_context, R.layout.spinner_address_item, null);
			holder=new Holder();
			holder.spinnerName=(TextView)convertView.findViewById(R.id.spinner_name);
			convertView.setTag(holder);
		}else{
			holder=(Holder)convertView.getTag();
		}
		holder.spinnerName.setText(string.getAreaName());
		return convertView;
	}
	private static class Holder{
		public TextView spinnerName;
		
	}

}
