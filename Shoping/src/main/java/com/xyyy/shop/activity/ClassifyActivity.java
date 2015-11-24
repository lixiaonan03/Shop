package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.adapter.ClassifyMenuContentItemAdapter;
import com.xyyy.shop.adapter.ClassifyMenuItemAdapter;
import com.xyyy.shop.model.EnnGoodsCat;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 商品分类的activity
 */
public class ClassifyActivity extends BaseActivity {

	private ImageView top_back;
	private RelativeLayout search;// 搜索框的按钮
	private ListView menu_listview;// 左边的分类列表
	private GridView menu_gridview_content;// 右边的详细分类列表
	private ClassifyMenuItemAdapter menuadapter;
	private ClassifyMenuContentItemAdapter menucontentadapter;
	private List<EnnGoodsCat> menulist = new ArrayList<EnnGoodsCat>();// 左边一级分类的数据
	private List<EnnGoodsCat> menucontentlist = new ArrayList<EnnGoodsCat>();// 右边二级分类的数据
	private CustomProgressDialog customProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classify);
		top_back = (ImageView) findViewById(R.id.top_back);

		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		initView();
		customProgressDialog = new CustomProgressDialog(ClassifyActivity.this,
				"正在加载......");
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		search = (RelativeLayout) findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ClassifyActivity.this, SearchActivity.class);
				startActivity(intent);
			}
		});
		menu_listview = (ListView) findViewById(R.id.menu_list);
		menu_gridview_content = (GridView) findViewById(R.id.menu_list_content);
		// 左边 一级分类的
		menuadapter = new ClassifyMenuItemAdapter(ClassifyActivity.this,
				menulist);
		menu_listview.setAdapter(menuadapter);
		menu_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				smoothScroollListView(arg2);
				menuadapter.setViewBackGround(arg2);
				menuadapter.notifyDataSetChanged();
			}
		});
		// 右边九宫格布局的
		menucontentadapter = new ClassifyMenuContentItemAdapter(
				ClassifyActivity.this, menucontentlist);
		menu_gridview_content.setAdapter(menucontentadapter);
		menu_gridview_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Integer id = menucontentlist.get(arg2).getCatId();
				String catname = menucontentlist.get(arg2).getCatName();
				Intent intent = new Intent();
				intent.setClass(ClassifyActivity.this, SearchActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("catname", catname);
				startActivity(intent);
			}
		});
	}

	/**
	 * 加载一级分类的数据
	 */
	private void initdata() {
		customProgressDialog.show();
		VolleyUtil.sendStringRequestByGetToList(CommonVariable.GetCatURL
				+ "-100", null, null, EnnGoodsCat.class,
				new HttpBackListListener<EnnGoodsCat>() {

					@Override
					public void onSuccess(List<EnnGoodsCat> t) {
						// TODO 请求数据成功
						menulist = t;
						menuadapter.set_list(menulist);
						menuadapter.setViewBackGround(0);
						menuadapter.notifyDataSetChanged();
						smoothScroollListView(0);
					}

					@Override
					public void onFail(String failstring) {
						// TODO 业务访问失败
						customProgressDialog.dismiss();
						Toast.makeText(ClassifyActivity.this, "加载数据失败！", 0)
								.show();
					}

					@Override
					public void onError(VolleyError error) {
						// TODO 接口访问失败
						customProgressDialog.dismiss();
						Toast.makeText(ClassifyActivity.this, "加载数据失败！", 0)
								.show();
					}

				}, false, "");
	}

	/**
	 * 加载右边条目中分类加载的数据
	 * 
	 * @param string
	 */
	private void loadContentData(Integer id) {
		String url = CommonVariable.GetCatURL + id;
		VolleyUtil.sendStringRequestByGetToList(url, null, null,
				EnnGoodsCat.class, new HttpBackListListener<EnnGoodsCat>() {

					@Override
					public void onSuccess(List<EnnGoodsCat> t) {
						// TODO 请求数据成功
						customProgressDialog.dismiss();
						if (t != null) {
							menucontentlist = t;
						} else {
							menucontentlist = new ArrayList<EnnGoodsCat>();
						}
						menucontentadapter.set_list(menucontentlist);
						menucontentadapter.notifyDataSetChanged();
					}

					@Override
					public void onFail(String failstring) {
						// TODO 业务访问失败
						customProgressDialog.dismiss();
						Toast.makeText(ClassifyActivity.this, "加载数据失败！", 0)
								.show();
						menucontentlist = new ArrayList<EnnGoodsCat>();
						menucontentadapter.set_list(menucontentlist);
						menucontentadapter.notifyDataSetChanged();
					}

					@Override
					public void onError(VolleyError error) {
						// TODO 接口访问失败
						customProgressDialog.dismiss();
						Toast.makeText(ClassifyActivity.this, "加载数据失败！", 0)
								.show();
						menucontentlist = new ArrayList<EnnGoodsCat>();
						menucontentadapter.set_list(menucontentlist);
						menucontentadapter.notifyDataSetChanged();
					}

				}, false, "");
	}

	/**
	 * listView scroll
	 * 
	 * @param position
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public void smoothScroollListView(int position) {
		if (Build.VERSION.SDK_INT >= 21) {
			menu_listview.setSelectionFromTop(position, 0);
		} else if (Build.VERSION.SDK_INT >= 11) {
			menu_listview.smoothScrollToPositionFromTop(position, 0, 500);
		} else if (Build.VERSION.SDK_INT >= 8) {
			int firstVisible = menu_listview.getFirstVisiblePosition();
			int lastVisible = menu_listview.getLastVisiblePosition();
			if (position < firstVisible) {
				menu_listview.smoothScrollToPosition(position);
			} else {
				if (firstVisible == 0) {
					menu_listview.smoothScrollToPosition(position + lastVisible
							- firstVisible);
				} else {
					menu_listview.smoothScrollToPosition(position + lastVisible
							- firstVisible - 1);
				}
			}
		} else {
			menu_listview.setSelection(position);
		}
		// TODO 根据左边点击条目的数据 获取右边表格布局中的数据
		if (!customProgressDialog.isShowing()) {
			customProgressDialog.show();
		}
		if (menulist.size() > 0) {
			loadContentData(menulist.get(position).getCatId());
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (null != menulist && menulist.size() > 0) {

		} else {
			initdata();
		}
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		/**
		 * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
		 */
		StatService.onPause(this);
	}
}
