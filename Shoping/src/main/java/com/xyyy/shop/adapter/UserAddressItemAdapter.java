package com.xyyy.shop.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.listener.HttpBackListener;
import com.android.volley.util.VolleyUtil;
import com.xyyy.shop.R;
import com.xyyy.shop.activity.UseradressActivity;
import com.xyyy.shop.model.EnnMemberReceipt;
import com.xyyy.shop.model.EnnSysArea;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 用户收货地址的adapter
 * @author lxn
 *
 */
public class UserAddressItemAdapter extends BaseAdapter {
	private Context _context;
	private List<EnnMemberReceipt> _list;
	public int flagposition=-1;//记录条目选中的位置 -1没有默认的 
	private CustomProgressDialog customProgressDialog;
	public UserAddressItemAdapter(Context context, List<EnnMemberReceipt> list) {
		super();
		
		this._context = context;
		this._list = list;
		customProgressDialog=new CustomProgressDialog(_context, "正在删除......");
	}

	public List<EnnMemberReceipt> get_list() {
		return _list;
	}

	public void set_list(List<EnnMemberReceipt> _list) {
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
		final EnnMemberReceipt oneitem = (EnnMemberReceipt) getItem(position);
		final Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_useraddress_item,
					null);
			holder = new Holder();
			holder.item_checkbox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			holder.item_del = (ImageView) convertView
					.findViewById(R.id.delview);
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
		
		VolleyUtil.sendStringRequestByGetToBean(CommonVariable.GetAreaDetailURL+oneitem.getCountry(), null, null, EnnSysArea.class, new HttpBackListener<EnnSysArea>() {

			@Override
			public void onSuccess(EnnSysArea t) {
				// TODO Auto-generated method stub
				holder.item_text.setText(t.getAreaAllName());
			}

			@Override
			public void onFail(String failstring) {
				// TODO Auto-generated method stub
				holder.item_text.setText("");
			}

			@Override
			public void onError(VolleyError error) {
				// TODO Auto-generated method stub
				holder.item_text.setText("");
			}
		}, false, null);
	
		holder.item_text1.setText(oneitem.getReceAddress());
		holder.item_text2.setText(oneitem.getReceName()+" "+oneitem.getRecePhone());
		holder.item_del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 弹出删除提示框
				AlertDialog.Builder builder = new AlertDialog.Builder(_context);
		        builder.setTitle("提示");
		        builder.setMessage("您确定要删除该收货地址吗？");
		        builder.setNegativeButton("取消", null);
		        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(final DialogInterface dialog, int which) {
		               int id=oneitem.getReceId();
		               customProgressDialog.show();
		               VolleyUtil.sendStringRequestByGetToString(CommonVariable.DeleteMyAddressURL+id, null, null, new HttpBackBaseListener() {
						
						@Override
						public void onSuccess(String string) {
							// TODO Auto-generated method stub
							customProgressDialog.dismiss();
							dialog.dismiss();//取消dialog，或dismiss掉
							get_list().remove(position);
							notifyDataSetChanged();
						}
						
						@Override
						public void onFail(String failstring) {
							customProgressDialog.dismiss();
							dialog.dismiss();//取消dialog，或dismiss掉
							Toast.makeText(_context,"删除失败！",Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void onError(VolleyError error) {
							customProgressDialog.dismiss();
							dialog.dismiss();//取消dialog，或dismiss掉
							Toast.makeText(_context,"删除失败！",Toast.LENGTH_SHORT).show();
						}
					}, false, null);
		               
		            }
		        });
		        builder.create().show();
			}
		});
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
		ImageView item_del;
		TextView item_text;
		TextView item_text1;
		TextView item_text2;
	}

}
