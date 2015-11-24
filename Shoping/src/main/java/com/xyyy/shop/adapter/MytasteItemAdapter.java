package com.xyyy.shop.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackBaseListener;
import com.android.volley.util.VolleyUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.model.EnnDishesVO;
import com.xyyy.shop.model.EnnMemberFlavor;
import com.xyyy.shop.model.EnnMemberFlavorDTO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 我的口味的适配器
 * 
 * @author lxn
 *
 */
public class MytasteItemAdapter extends BaseAdapter implements SectionIndexer {

	private Context _context;
	private List<EnnDishesVO> _list;
	private CustomProgressDialog customProgressDialog;
    private int flag;
	public MytasteItemAdapter(Context context, List<EnnDishesVO> list) {
		super();
		this._context = context;
		this._list = list;
		customProgressDialog=new CustomProgressDialog(_context, "正在删除......");
	}
    
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<EnnDishesVO> get_list() {
		return _list;
	}
    
	public void set_list(List<EnnDishesVO> _list) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final EnnDishesVO oneitem = (EnnDishesVO) getItem(position);
		Holder holder;
		if (null == convertView) {
			convertView = View.inflate(_context, R.layout.adapter_mytaste_item,
					null);
			holder = new Holder();
			holder.catalog = (TextView) convertView.findViewById(R.id.catalog);
			holder.item_checkBox = (CheckBox) convertView
					.findViewById(R.id.item_checkBox);
			holder.item_img = (ImageView) convertView
					.findViewById(R.id.item_img);
			holder.item_del = (ImageView) convertView
					.findViewById(R.id.delview);
			holder.item_goodname = (TextView) convertView
					.findViewById(R.id.item_goodname);
			holder.item_goodtype = (TextView) convertView
					.findViewById(R.id.item_goodtype);
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
			TextPaint tp = holder.catalog.getPaint(); 
			tp.setFakeBoldText(true);
		} else {
			holder.catalog.setVisibility(View.GONE);
		}

		if (oneitem.getFlag() == 1) {
			holder.item_checkBox.setChecked(true);
		} else {
			holder.item_checkBox.setChecked(false);
		}
		
		
       holder.item_del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 弹出删除提示框
				AlertDialog.Builder builder = new AlertDialog.Builder(_context);
		        builder.setTitle("提示");
		        builder.setMessage("您确定要删除该口味信息吗？");
		        builder.setNegativeButton("取消", null);
		        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(final DialogInterface dialog, int which) {
		            	//TODO 删除操作
		            	final EnnMemberFlavorDTO dto=new EnnMemberFlavorDTO();
		        		List<EnnMemberFlavor> ennMemberFlavors=new ArrayList<EnnMemberFlavor>();
		            	EnnMemberFlavor oneMemberFlavor=new EnnMemberFlavor();
						oneMemberFlavor.setDishesId(oneitem.getEnnDishes().getDishesId());
						oneMemberFlavor.setDishesCode(oneitem.getEnnDishes().getDishesCode());
						if(flag==1){
							oneMemberFlavor.setFlavorType("01");
						}
						if(flag==2){
							oneMemberFlavor.setFlavorType("06");
						}
						//TODO 设置进去人员id 目前这先用 1代替
						int userid = 0;
						if (ShopApplication.isLogin) {
							if(ShopApplication.loginflag==1){
								userid=ShopApplication.userid;
							}
							if(ShopApplication.loginflag==2){
								userid=ShopApplication.useridother;
							}
						}
						oneMemberFlavor.setMembId(userid);
						ennMemberFlavors.add(oneMemberFlavor);
						dto.setEnnMemberFlavors(ennMemberFlavors);
		               customProgressDialog.show();
		               VolleyUtil.sendObjectByPostToString(CommonVariable.DeleteMemberTasteURL,null, dto, new HttpBackBaseListener() {
						
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
							Toast.makeText(_context, "删除失败！", 0).show();
							dialog.dismiss();//取消dialog，或dismiss掉
						}
						
						@Override
						public void onError(VolleyError error) {
							customProgressDialog.dismiss();
							Toast.makeText(_context, "删除失败！", 0).show();
							dialog.dismiss();//取消dialog，或dismiss掉
						}
					}, false, null);
		               
		            }
		        });
		        builder.create().show();
			}
		});
		
		holder.item_checkBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						// TODO Auto-generated method stub
						if (arg1) {
							oneitem.setFlag(1);
						} else {
							oneitem.setFlag(0);
						}
					}
				});
		if(null!=oneitem.getEnnDishes()){
			 DisplayImageOptions options = new DisplayImageOptions.Builder()    //46*46
	            .showImageOnLoading(R.drawable.loadingimg46)         // 设置图片下载期间显示的图片  
	            .showImageForEmptyUri(R.drawable.loadingimg46)  // 设置图片Uri为空或是错误的时候显示的图片  
	            .showImageOnFail(R.drawable.loadingimg46)       // 设置图片加载或解码过程中发生错误显示的图片      
	            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
	            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
	            .build();                                   // 创建配置过得DisplayImageOption对象  
			ImageLoader.getInstance().displayImage(oneitem.getEnnDishes().getDishsImg(), holder.item_img,options);
			if(null!=oneitem.getEnnDishes().getDishesName())
			holder.item_goodname.setText(oneitem.getEnnDishes().getDishesName());
		}
		if(null!=oneitem.getParent_ennDishes()&&null!=oneitem.getParent_ennDishes().getDishesName())
		holder.item_goodtype.setText(oneitem.getParent_ennDishes().getDishesName());
		return convertView;
	}

	private static class Holder {
		CheckBox item_checkBox;
		TextView catalog;
		ImageView item_img;
		TextView item_goodname;
		TextView item_goodtype;
		ImageView item_del;
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
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
