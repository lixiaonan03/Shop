package com.xyyy.shop.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xyyy.shop.R;
import com.xyyy.shop.model.DeliveNoticeItemDTO;
import com.xyyy.shop.model.Membfavor;

public class MydeliverynoticeItemAdapter extends BaseExpandableListAdapter {
	private Context _context;
	private List<DeliveNoticeItemDTO> _list;
	
	
	
	public MydeliverynoticeItemAdapter(Context _context, List<DeliveNoticeItemDTO> _list) {
		super();
		this._context = _context;
		this._list = _list;
	}

	public List<DeliveNoticeItemDTO> get_list() {
		return _list;
	}

	public void set_list(List<DeliveNoticeItemDTO> _list) {
		this._list = _list;
	}

	@Override
	public Object getChild(int paramInt1, int paramInt2) {
		// TODO Auto-generated method stub
		return _list.get(paramInt1).getMembfavors();
	}

	@Override
	public long getChildId(int paramInt1, int paramInt2) {
		// TODO Auto-generated method stub
		return paramInt2;
	}

	@Override
	public View getChildView(int paramInt1, int paramInt2,
			boolean paramBoolean, View paramView, ViewGroup paramViewGroup) {
		final Membfavor childergood=_list.get(paramInt1).getMembfavors().get(paramInt2);
		ChilderHolder holder;
		if (null == paramView) {
			paramView = View.inflate(_context, R.layout.adapter_mydeliverynotice_childer,
					null);
			holder = new ChilderHolder();
			holder.item_radiobuttonyes = (RadioButton) paramView
					.findViewById(R.id.childer_radiobuttonyes);
			holder.item_radiobuttonno = (RadioButton) paramView
					.findViewById(R.id.childer_radiobuttonno);
			holder.item_radiobuttonother = (RadioButton) paramView
					.findViewById(R.id.childer_radiobuttonother);
			holder.item_radiogroup = (RadioGroup) paramView
					.findViewById(R.id.childer_radiogroup);
			holder.item_img = (ImageView) paramView
					.findViewById(R.id.childer_img);
			holder.item_text = (TextView) paramView
					.findViewById(R.id.childer_name);
			paramView.setTag(holder);
		} else {
			holder = (ChilderHolder) paramView.getTag();
		}
		if(null!=childergood.getEnnDishes()){
			 // 使用DisplayImageOptions.Builder()创建DisplayImageOptions    46*46
	        DisplayImageOptions options = new DisplayImageOptions.Builder()  
	            .showImageOnLoading(R.drawable.loadingimg46)         // 设置图片下载期间显示的图片  
	            .showImageForEmptyUri(R.drawable.loadingimg46)  // 设置图片Uri为空或是错误的时候显示的图片  
	            .showImageOnFail(R.drawable.loadingimg46)       // 设置图片加载或解码过程中发生错误显示的图片      
	            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
	            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
	            .build();                                   // 创建配置过得DisplayImageOption对象  
			ImageLoader.getInstance().displayImage(childergood.getEnnDishes().getDishsImg(),holder.item_img,options);
			if(null!=childergood.getEnnDishes().getDishesName()){
				holder.item_text.setText(childergood.getEnnDishes().getDishesName());
			}
			holder.item_radiogroup.clearCheck();
			holder.item_radiogroup.setOnCheckedChangeListener(null);
			if(null!=childergood.getFlavorType()){
				if(childergood.getFlavorType().equals("01")){
					//最近想吃
					holder.item_radiobuttonyes.setChecked(true);
				}
				if(childergood.getFlavorType().equals("06")){
					//最近不想吃
					holder.item_radiobuttonno.setChecked(true);
				}
				if(childergood.getFlavorType().equals("03")){
					//最近不想吃
					holder.item_radiobuttonother.setChecked(true);
				}
			}else{
				holder.item_radiogroup.clearCheck();
			}
			holder.item_radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt) {
					// TODO Auto-generated method stub
					if(paramInt==R.id.childer_radiobuttonyes){
						childergood.setFlavorType("01");
					}
					if(paramInt==R.id.childer_radiobuttonno){
						childergood.setFlavorType("06");
					}
					if(paramInt==R.id.childer_radiobuttonother){
						childergood.setFlavorType("03");
					}
				}
			});
		}
		return paramView;
	}

	@Override
	public int getChildrenCount(int paramInt) {
		return _list.get(paramInt).getMembfavors().size();
	}

	@Override
	public Object getGroup(int paramInt) {
		// TODO Auto-generated method stub
		return _list.get(paramInt);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return _list.size();
	}

	@Override
	public long getGroupId(int paramInt) {
		// TODO Auto-generated method stub
		return paramInt;
	}

	@Override
	public View getGroupView(int paramInt, boolean paramBoolean,
			View paramView, ViewGroup paramViewGroup) {
		DeliveNoticeItemDTO type=_list.get(paramInt);
		GroupHolder holder;
		if (null == paramView) {
			paramView = View.inflate(_context, R.layout.adapter_mydeliverynotice_group,
					null);
			holder = new GroupHolder();
			holder.group_text = (TextView) paramView
					.findViewById(R.id.group_name);
			paramView.setTag(holder);
		} else {
			holder = (GroupHolder) paramView.getTag();
		}
		if(null!=type.getParentDish()&&null!=type.getParentDish().getDishesName()){
			holder.group_text.setText(type.getParentDish().getDishesName());
		}
		return paramView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int paramInt1, int paramInt2) {
		// TODO Auto-generated method stub
		return false;
	}
	private static class ChilderHolder {
		RadioButton item_radiobuttonyes;
		RadioButton item_radiobuttonno;
		RadioButton item_radiobuttonother;
		RadioGroup item_radiogroup;
		ImageView item_img;
		TextView item_text;
	}
	private static class GroupHolder {
		TextView group_text;
	}
}
