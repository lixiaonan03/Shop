package com.xyyy.shop.activity;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.adapter.NearsearchItemAdapter;
import com.xyyy.shop.adapter.SearchItemAdapter;
import com.xyyy.shop.model.GoodItemVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.PreferencesUtil;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 搜索商品界面还是商品列表界面
 * 
 * @author lxn
 *
 */
public class SearchActivity extends BaseActivity {

	private InputMethodManager inputMethodManager;
	private ImageView top_back;
	private EditText search_edit;// 搜索输入框
	private ImageView search_go;// 去搜索
	private TextView hot_one, hot_two, hot_three;

	private ListView listview;
	private SearchItemAdapter adapter;
	private TextView sort_default;
	private RelativeLayout sort_price;
	private TextView sort_price_text;
	private ImageView sort_price_img;
	private TextView sort_salesvolume;
	private TextView sort_goodevaluate;

	private int PRICE_UP = 1;// 价格升序排列
	private int PRICE_DOWN = 2;// 价格降序排列

	private View linedefault;
	private View lineprice;
	private View linesales;
	private View lineevaluate;

	private int sort_price_current = 0;// 当前价格的排序规则
	public List<GoodItemVO> list;// 要加载的数据

	private RelativeLayout nodata;
	private LinearLayout baselinear;
	private Button clear_allhistory;
	private ListView nearsearchlist;
	private LinearLayout searchanswerLin;
	private List<String> nearList;// 搜索历史列表
	private NearsearchItemAdapter nearsearchadapter;

	private Integer catid;
	private CustomProgressDialog customProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		catid = getIntent().getIntExtra("id", -1);
		customProgressDialog = new CustomProgressDialog(SearchActivity.this,
				"正在加载......");

		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		top_back = (ImageView) findViewById(R.id.top_back);

		search_edit = (EditText) findViewById(R.id.search_edit);
		search_go = (ImageView) findViewById(R.id.searchimg);
		// 热门搜索的
		hot_one = (TextView) findViewById(R.id.hot_one);
		hot_two = (TextView) findViewById(R.id.hot_two);
		hot_three = (TextView) findViewById(R.id.hot_three);
		hot_one.setOnClickListener(hotviewclick);
		hot_two.setOnClickListener(hotviewclick);
		hot_three.setOnClickListener(hotviewclick);
		// 最近搜索的
		baselinear = (LinearLayout) findViewById(R.id.baselinear);

		clear_allhistory = (Button) findViewById(R.id.clear_allhistory);
		nearsearchlist = (ListView) findViewById(R.id.nearsearchlist);
		// 搜索结果的界面
		searchanswerLin = (LinearLayout) findViewById(R.id.searchanswerLin);
		// 排序的
		sort_default = (TextView) findViewById(R.id.sort_default);
		sort_price = (RelativeLayout) findViewById(R.id.sort_price);
		sort_price_text = (TextView) findViewById(R.id.sort_price_text);
		sort_price_img = (ImageView) findViewById(R.id.sort_price_img);
		sort_salesvolume = (TextView) findViewById(R.id.sort_salesvolume);
		sort_goodevaluate = (TextView) findViewById(R.id.sort_goodevaluate);

		linedefault = (View) findViewById(R.id.linedefault);
		lineprice = (View) findViewById(R.id.lineprice);
		linesales = (View) findViewById(R.id.linesales);
		lineevaluate = (View) findViewById(R.id.lineevaluate);

		listview = (ListView) findViewById(R.id.listview);
		// 没有数据的
		nodata = (RelativeLayout) findViewById(R.id.nodata);

		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		search_edit.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					// 先隐藏键盘
					((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(getCurrentFocus()
									.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
					String searchtext = search_edit.getText().toString().trim();
					if (null == searchtext || "".equals(searchtext)) {
						Toast.makeText(SearchActivity.this, "请输入搜索内容！",
								Toast.LENGTH_SHORT).show();
					} else {
						// 修改 Pref 文件中最近搜索的内容
						savehistory(searchtext);
						// 执行搜索方法
						loadData(searchtext);
					}

					return true;
				}
				return false;
			}

		});
		search_go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String searchtext = search_edit.getText().toString().trim();
				if (null == searchtext || "".equals(searchtext)) {
					Toast.makeText(SearchActivity.this, "请输入搜索内容！",
							Toast.LENGTH_SHORT).show();
				} else {
					// 修改 Pref 文件中最近搜索的内容
					savehistory(searchtext);
					// 执行搜索方法
					loadData(searchtext);
				}
			}
		});
		clear_allhistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 清空历史搜索的内容
				if (null != nearList && nearList.size() > 0) {
					PreferencesUtil.setStr(PreferencesUtil.SEARCH_HISTORY, "");
					nearList.clear();
					nearsearchadapter.notifyDataSetChanged();
				}
			}
		});
		sort_default.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 默认的被点击 选中默认的排序
				sort_price_current = 0;
				sort_price_img.setImageResource(R.drawable.sort_price);

				sort_default.setTextColor(getResources().getColor(
						R.color.order_textcolor_checked));
				sort_default
						.setBackgroundResource(R.color.order_background_checked);
				linedefault
						.setBackgroundResource(R.color.order_linecolor_checked);

				sort_price_text.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_price
						.setBackgroundResource(R.color.order_background_nochecked);
				lineprice
						.setBackgroundResource(R.color.order_linecolor_nochecked);

				sort_salesvolume.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_salesvolume
						.setBackgroundResource(R.color.order_background_nochecked);
				linesales
						.setBackgroundResource(R.color.order_linecolor_nochecked);

				sort_goodevaluate.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_goodevaluate
						.setBackgroundResource(R.color.order_background_nochecked);
				lineevaluate
						.setBackgroundResource(R.color.order_linecolor_nochecked);
				// TODO 默认排序数据变化
				Collections.sort(list, new Comparator<GoodItemVO>() {

					@Override
					public int compare(GoodItemVO paramT1, GoodItemVO paramT2) {
						if (paramT1.getEnnGoods().getGoodsName() == null
								|| paramT2.getEnnGoods().getGoodsName() == null) {
							return -1;
						}
						return paramT1
								.getEnnGoods()
								.getGoodsName()
								.compareTo(paramT2.getEnnGoods().getGoodsName());
					}

				});
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
			}
		});
		sort_price.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 价格排序
				sort_price_text.setTextColor(getResources().getColor(
						R.color.order_textcolor_checked));
				sort_price
						.setBackgroundResource(R.color.order_background_checked);
				lineprice
						.setBackgroundResource(R.color.order_linecolor_checked);

				sort_default.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_default
						.setBackgroundResource(R.color.order_background_nochecked);
				linedefault
						.setBackgroundResource(R.color.order_linecolor_nochecked);

				sort_salesvolume.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_salesvolume
						.setBackgroundResource(R.color.order_background_nochecked);
				linesales
						.setBackgroundResource(R.color.order_linecolor_nochecked);

				sort_goodevaluate.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_goodevaluate
						.setBackgroundResource(R.color.order_background_nochecked);
				lineevaluate
						.setBackgroundResource(R.color.order_linecolor_nochecked);
				if (sort_price_current == PRICE_UP) {
					// 当前是升序的 图片箭头和数据变成降序的
					sort_price_img.setImageResource(R.drawable.sort_price_down);
					sort_price_current = PRICE_DOWN;
					Collections.sort(list, new Comparator<GoodItemVO>() {

						@Override
						public int compare(GoodItemVO paramT1,
								GoodItemVO paramT2) {
							if (paramT1.getEnnGoods().getMallPrice() == null
									|| paramT2.getEnnGoods().getMallPrice() == null) {
								return -1;
							}
							return paramT2
									.getEnnGoods()
									.getMallPrice()
									.compareTo(
											paramT1.getEnnGoods()
													.getMallPrice());
						}

					});
				} else {
					sort_price_current = PRICE_UP;
					sort_price_img.setImageResource(R.drawable.sort_price_up);
					Collections.sort(list, new Comparator<GoodItemVO>() {

						@Override
						public int compare(GoodItemVO paramT1,
								GoodItemVO paramT2) {
							if (paramT1.getEnnGoods().getMallPrice() == null
									|| paramT2.getEnnGoods().getMallPrice() == null) {
								return 1;
							}
							return paramT1
									.getEnnGoods()
									.getMallPrice()
									.compareTo(
											paramT2.getEnnGoods()
													.getMallPrice());
						}

					});
				}
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
			}
		});
		sort_salesvolume.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				// TODO 销量排序
				sort_price_current = 0;
				sort_price_img.setImageResource(R.drawable.sort_price);

				sort_salesvolume.setTextColor(getResources().getColor(
						R.color.order_textcolor_checked));
				sort_salesvolume
						.setBackgroundResource(R.color.order_background_checked);
				linesales
						.setBackgroundResource(R.color.order_linecolor_checked);

				sort_default.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_default
						.setBackgroundResource(R.color.order_background_nochecked);
				linedefault
						.setBackgroundResource(R.color.order_linecolor_nochecked);

				sort_price_text.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_price
						.setBackgroundResource(R.color.order_background_nochecked);
				lineprice
						.setBackgroundResource(R.color.order_linecolor_nochecked);

				sort_goodevaluate.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_goodevaluate
						.setBackgroundResource(R.color.order_background_nochecked);
				lineevaluate
						.setBackgroundResource(R.color.order_linecolor_nochecked);

				Collections.sort(list, new Comparator<GoodItemVO>() {

					@Override
					public int compare(GoodItemVO paramT1, GoodItemVO paramT2) {
						if (paramT1.getEnnGoods().getGoodsSales() == null
								|| paramT2.getEnnGoods().getGoodsSales() == null) {
							return -1;
						}
						return paramT2
								.getEnnGoods()
								.getGoodsSales()
								.compareTo(
										paramT1.getEnnGoods().getGoodsSales());
					}

				});
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
			}
		});
		sort_goodevaluate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				// TODO 好评排序
				sort_price_current = 0;
				sort_price_img.setImageResource(R.drawable.sort_price);

				sort_goodevaluate.setTextColor(getResources().getColor(
						R.color.order_textcolor_checked));
				sort_goodevaluate
						.setBackgroundResource(R.color.order_background_checked);
				lineevaluate
						.setBackgroundResource(R.color.order_linecolor_checked);

				sort_default.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_default
						.setBackgroundResource(R.color.order_background_nochecked);
				linedefault
						.setBackgroundResource(R.color.order_linecolor_nochecked);

				sort_price_text.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_price
						.setBackgroundResource(R.color.order_background_nochecked);
				lineprice
						.setBackgroundResource(R.color.order_linecolor_nochecked);

				sort_salesvolume.setTextColor(getResources().getColor(
						R.color.order_textcolor_nochecked));
				sort_salesvolume
						.setBackgroundResource(R.color.order_background_nochecked);
				linesales
						.setBackgroundResource(R.color.order_linecolor_nochecked);

				Collections.sort(list, new Comparator<GoodItemVO>() {

					@Override
					public int compare(GoodItemVO paramT1, GoodItemVO paramT2) {
						if (paramT1.getEnnGoods().getGoodsEvals() == null
								|| paramT2.getEnnGoods().getGoodsEvals() == null) {
							return -1;
						}
						return paramT2
								.getEnnGoods()
								.getGoodsEvals()
								.compareTo(
										paramT1.getEnnGoods().getGoodsEvals());
					}

				});
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
			}
		});
		initListView();

		String nearsearch = PreferencesUtil
				.getStr(PreferencesUtil.SEARCH_HISTORY);
		String[] near = nearsearch.split("#");
		nearList = new ArrayList<String>();
		Collections.addAll(nearList, near);
		if (nearsearch.equals("")) {
			// 没有保存过
			nearList.removeAll(nearList);
		}
		// 在这做判断 如果是商品分类进来的就
		if (catid == -1) {
			// 获取搜索历史内容
			getnearsearch();
			baselinear.setVisibility(View.VISIBLE);
			searchanswerLin.setVisibility(View.GONE);
		} else {
			getGoodsBycatid(catid);
			baselinear.setVisibility(View.GONE);
			searchanswerLin.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 获取最近搜索的内容
	 */
	private void getnearsearch() {
		nearsearchadapter = new NearsearchItemAdapter(SearchActivity.this,
				nearList);
		nearsearchlist.setAdapter(nearsearchadapter);
		nearsearchlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 修改 Pref 文件中最近搜索的内容
				String key = nearList.get(position);
				savehistory(key);
				loadData(key);
			}
		});
	}

	/**
	 * 初始化listview
	 */
	private void initListView() {

		list = new ArrayList<GoodItemVO>();
		adapter = new SearchItemAdapter(SearchActivity.this, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				long id = list.get(arg2).getEnnGoods().getGoodsId();
				String name = list.get(arg2).getEnnGoods().getGoodsName();
				String goodstate = list.get(arg2).getEnnGoods().getGoodsState();
				String imgurl = list.get(arg2).getImgUrl();
				BigDecimal goodprice = list.get(arg2).getEnnGoods()
						.getMallPrice();
				// int iseletron = list.get(arg2).getEnnGoods().getIsdianzi();
				Intent intent = new Intent();
				intent.setClass(SearchActivity.this,
						ProductdeatilActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("name", name);
				intent.putExtra("imgurl", imgurl);
				intent.putExtra("goodprice", goodprice);
				intent.putExtra("goodstate", goodstate);

				startActivity(intent);
			}
		});
		listview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// 隐藏软键盘
				if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
					if (getCurrentFocus() != null) {
						inputMethodManager.hideSoftInputFromWindow(
								getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
					}

				}
				return false;
			}
		});
	}

	/**
	 * 搜索加载数据
	 * 
	 * @param searchtext
	 *            搜索的内容
	 */
	private void loadData(String searchtext) {
		// 向服务端去加载数据
		customProgressDialog.show();
		// String
		// url=URLEncoder.encode(CommonVariable.SearchGoodURL+searchtext);
		String url = CommonVariable.SearchGoodURL
				+ URLEncoder.encode(searchtext);
		VolleyUtil.sendStringRequestByGetToList(url, null, null,
				GoodItemVO.class, new HttpBackListListener<GoodItemVO>() {

					@Override
					public void onSuccess(List<GoodItemVO> t) {
						customProgressDialog.dismiss();
						// TODO 把收到的内容装换成list
						if (null != t && t.size() > 0) {
							list = t;
							Collections.sort(list,
									new Comparator<GoodItemVO>() {

										@Override
										public int compare(GoodItemVO paramT1,
												GoodItemVO paramT2) {
											if (paramT1.getEnnGoods()
													.getGoodsName() == null
													|| paramT2.getEnnGoods()
															.getGoodsName() == null) {
												return -1;
											}
											return paramT1
													.getEnnGoods()
													.getGoodsName()
													.compareTo(
															paramT2.getEnnGoods()
																	.getGoodsName());
										}

									});
							baselinear.setVisibility(View.GONE);
							nodata.setVisibility(View.GONE);
							searchanswerLin.setVisibility(View.VISIBLE);
							adapter.set_list(list);
							adapter.notifyDataSetChanged();
						} else {
							baselinear.setVisibility(View.GONE);
							searchanswerLin.setVisibility(View.GONE);
							nodata.setVisibility(View.VISIBLE);
						}
					}

					@Override
					public void onFail(String failstring) {
						// TODO 没有搜索内容
						customProgressDialog.dismiss();
						baselinear.setVisibility(View.GONE);
						searchanswerLin.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						baselinear.setVisibility(View.GONE);
						searchanswerLin.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}
				}, false, null);
	}

	/**
	 * 通过商品2级分类的id 获取商品列表
	 * 
	 * @param id
	 */
	private void getGoodsBycatid(Integer id) {
		// 向服务端去加载数据
		customProgressDialog.show();
		String url = CommonVariable.GetGoodURL + id;
		VolleyUtil.sendStringRequestByGetToList(url, null, null,
				GoodItemVO.class, new HttpBackListListener<GoodItemVO>() {

					@Override
					public void onSuccess(List<GoodItemVO> t) {
						// TODO 把收到的内容装换成list
						customProgressDialog.dismiss();
						if (null != t && t.size() > 0) {
							list = t;
							Collections.sort(list,
									new Comparator<GoodItemVO>() {

										@Override
										public int compare(GoodItemVO paramT1,
												GoodItemVO paramT2) {
											if (paramT1.getEnnGoods()
													.getGoodsName() == null
													|| paramT2.getEnnGoods()
															.getGoodsName() == null) {
												return -1;
											}
											return paramT1
													.getEnnGoods()
													.getGoodsName()
													.compareTo(
															paramT2.getEnnGoods()
																	.getGoodsName());
										}

									});
							baselinear.setVisibility(View.GONE);
							searchanswerLin.setVisibility(View.VISIBLE);
							nodata.setVisibility(View.GONE);

							adapter.set_list(list);
							adapter.notifyDataSetChanged();
						} else {
							baselinear.setVisibility(View.GONE);
							searchanswerLin.setVisibility(View.GONE);
							nodata.setVisibility(View.VISIBLE);
						}
					}

					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						baselinear.setVisibility(View.GONE);
						searchanswerLin.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}

					@Override
					public void onError(VolleyError error) {
						customProgressDialog.dismiss();
						baselinear.setVisibility(View.GONE);
						searchanswerLin.setVisibility(View.GONE);
						nodata.setVisibility(View.VISIBLE);
					}

				}, false, null);
	}

	OnClickListener hotviewclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.hot_one:
				// 修改 Pref 文件中最近搜索的内容
				savehistory(hot_one.getText().toString().trim());
				// 执行搜索方法
				loadData(hot_one.getText().toString().trim());
				break;
			case R.id.hot_two:
				savehistory(hot_two.getText().toString().trim());
				loadData(hot_two.getText().toString().trim());
				break;
			case R.id.hot_three:
				savehistory(hot_three.getText().toString().trim());
				loadData(hot_three.getText().toString().trim());
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 保存搜索历史
	 * 
	 * @param searchkey
	 */
	private void savehistory(String searchkey) {
		// 判断是否已经存在历史
		if (nearList.contains(searchkey)) {
			nearList.remove(searchkey);
		}
		nearList.add(0, searchkey);
		String sb = "";
		int size = nearList.size();
		for (int i = 0; i < (size > 6 ? 6 : size); i++) {
			String element = nearList.get(i);
			if (sb.equals(""))
				sb = element;
			else
				sb += "#" + element;
		}
		PreferencesUtil.setStr(PreferencesUtil.SEARCH_HISTORY, sb);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
