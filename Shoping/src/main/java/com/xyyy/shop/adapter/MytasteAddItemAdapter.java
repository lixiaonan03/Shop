package com.xyyy.shop.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xyyy.shop.R;
import com.xyyy.shop.model.EnnDishesVO2;

/**
 * 添加我的口味的适配器
 * 
 * @author lxn
 *
 */
public class MytasteAddItemAdapter extends BaseAdapter implements
		SectionIndexer {
	private Context _context;
	private List<EnnDishesVO2> _list;

	public MytasteAddItemAdapter(Context context, List<EnnDishesVO2> list) {
		super();
		this._context = context;
		this._list = list;
	}

	public List<EnnDishesVO2> get_list() {
		return _list;
	}

	public void set_list(List<EnnDishesVO2> _list) {
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
		final EnnDishesVO2 oneitem = (EnnDishesVO2) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_mytaste_add,
					null);
			holder = new Holder();
			holder.catalog = (TextView) convertView.findViewById(R.id.catalog);
			holder.item_img = (ImageView) convertView
					.findViewById(R.id.item_img);
			holder.item_childername = (TextView) convertView
					.findViewById(R.id.item_childername);

			holder.item_parentname = (TextView) convertView
					.findViewById(R.id.item_parentname);
			holder.item_radiogroup = (RadioGroup) convertView
					.findViewById(R.id.item_radiogroup);
			holder.item_radiobuttonyes = (RadioButton) convertView
					.findViewById(R.id.item_radiobuttonyes);
			holder.item_radiobuttonno = (RadioButton) convertView
					.findViewById(R.id.item_radiobuttonno);
			holder.item_radiobuttonnocare = (RadioButton) convertView
					.findViewById(R.id.item_radiobuttonnocare);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			holder.catalog.setVisibility(View.VISIBLE);
			holder.catalog.setText(oneitem.getSortLetters());
		} else {
			holder.catalog.setVisibility(View.GONE);
		}
		holder.item_radiogroup.clearCheck();
		holder.item_radiogroup.setOnCheckedChangeListener(null);
		if (null != oneitem.getFlavorType()) {
			if (oneitem.getFlavorType().equals("01")) {
				holder.item_radiobuttonyes.setChecked(true);
			}
			if (oneitem.getFlavorType().equals("06")) {
				holder.item_radiobuttonno.setChecked(true);
			}
			if (oneitem.getFlavorType().equals("03")) {
				holder.item_radiobuttonnocare.setChecked(true);
			}
		} else {
			holder.item_radiogroup.clearCheck();
		}
		holder.item_radiogroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup arg0, int arg1) {
						// TODO Auto-generated method stub
						if (arg1 == R.id.item_radiobuttonyes) {
							oneitem.setFlavorType("01");
						}
						if (arg1 == R.id.item_radiobuttonno) {
							oneitem.setFlavorType("06");
						}
						if (arg1 == R.id.item_radiobuttonnocare) {
							// 无所谓
							oneitem.setFlavorType("03");
						}
					}
				});
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions 46*46
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.loadingimg46) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.loadingimg46) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.loadingimg46) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.build(); // 创建配置过得DisplayImageOption对象
		ImageLoader.getInstance().displayImage(
				oneitem.getEnnDishes().getDishsImg(), holder.item_img, options);
		holder.item_childername.setText(oneitem.getEnnDishes().getDishesName());
		holder.item_parentname.setText(oneitem.getParent_ennDishes()
				.getDishesName());
		return convertView;
	}

	private class Holder {
		TextView catalog;
		ImageView item_img;
		TextView item_childername;
		TextView item_parentname;
		RadioGroup item_radiogroup;
		RadioButton item_radiobuttonyes;
		RadioButton item_radiobuttonno;
		RadioButton item_radiobuttonnocare;
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int arg0) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = _list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == arg0) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	@Override
	public int getSectionForPosition(int arg0) {
		return _list.get(arg0).getSortLetters().charAt(0);
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

}
