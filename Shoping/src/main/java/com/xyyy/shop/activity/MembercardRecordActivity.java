package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xyyy.shop.R;
import com.xyyy.shop.adapter.MembercardRecordItemAdapter;
import com.xyyy.shop.model.EnnCardRecord;
import com.xyyy.shop.model.PageParameter;
import com.xyyy.shop.model.QueryReqBean;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 会员卡消费记录
 */
public class MembercardRecordActivity extends BaseActivity {



	private ImageView top_back;
	private PullToRefreshListView listview;
	private List<EnnCardRecord> list=new ArrayList<EnnCardRecord>();
	private MembercardRecordItemAdapter adapter;
	private CustomProgressDialog customProgressDialog;
	private TextView top_text;
	private String cardid;

	int pageNum = 0;
	int pagesize = 5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_membercardrecordlist);
		cardid=getIntent().getStringExtra("cardid");
		customProgressDialog=new CustomProgressDialog(MembercardRecordActivity.this, "正在加载......");
		top_back = (ImageView) findViewById(R.id.top_back);
		top_text = (TextView) findViewById(R.id.top_text);
		top_text.setText("消费记录");
		top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listview=(PullToRefreshListView)findViewById(R.id.listview);
		  /* 
         * Mode.BOTH：同时支持上拉下拉 
         * Mode.PULL_FROM_START：只支持下拉Pulling Down 
         * Mode.PULL_FROM_END：只支持上拉Pulling Up 
         */  
        /* 
         * 如果Mode设置成Mode.BOTH，需要设置刷新Listener为OnRefreshListener2，并实现onPullDownToRefresh()、onPullUpToRefresh()两个方法。  
         * 如果Mode设置成Mode.PULL_FROM_START或Mode.PULL_FROM_END，需要设置刷新Listener为OnRefreshListener，同时实现onRefresh()方法。 
         * 当然也可以设置为OnRefreshListener2，但是Mode.PULL_FROM_START的时候只调用onPullDownToRefresh()方法， 
         * Mode.PULL_FROM的时候只调用onPullUpToRefresh()方法.  
         */  
        /* 
         * setOnRefreshListener(OnRefreshListener listener):设置刷新监听器； 
         * setOnLastItemVisibleListener(OnLastItemVisibleListener listener):设置是否到底部监听器； 
         * setOnPullEventListener(OnPullEventListener listener);设置事件监听器； 
         * onRefreshComplete()：设置刷新完成 
         */  
        /* 
         * pulltorefresh.setOnScrollListener() 
         */
		// SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
		// SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
		// SCROLL_STATE_IDLE(0) 停止滚动
        /* 
         * setOnLastItemVisibleListener 
         * 当用户拉到底时调用   
         */  
        /* 
         * setOnTouchListener是监控从点下鼠标 （可能拖动鼠标）到放开鼠标（鼠标可以换成手指）的整个过程 ，他的回调函数是onTouchEvent（MotionEvent event）, 
         * 然后通过判断event.getAction()是MotionEvent.ACTION_UP还是ACTION_DOWN还是ACTION_MOVE分别作不同行为。 
         * setOnClickListener的监控时间只监控到手指ACTION_DOWN时发生的行为 
         */

		listview.setMode(Mode.BOTH);
		ILoadingLayout startLabels = listview
				.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在载入...");// 刷新时
		startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

		ILoadingLayout endLabels = listview.getLoadingLayoutProxy(
				false, true);
		endLabels.setPullLabel("上拉加载...");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在加载...");// 刷新时
		endLabels.setReleaseLabel("放开加载...");// 下来达到一定距离时，显示的提示


		adapter=new MembercardRecordItemAdapter(MembercardRecordActivity.this, list);
		listview.setAdapter(adapter);

		listview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// 下拉刷新
				pageNum = 0;
				initdata(true);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// 上拉加载更多
				initdata(false);
			}
		});

	}

	/**
	 * 加载数据
	 */
	private void initdata(final boolean clear) {
		pageNum++;
		customProgressDialog.show();
		QueryReqBean req=new QueryReqBean();
		PageParameter pageparameter=new PageParameter();
		pageparameter.setCurrentPage(pageNum);
		pageparameter.setPageSize(pagesize);
		req.setPageParameter(pageparameter);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cardId", cardid);
		req.setSearchParams(map);
		req.setSortExp("PAYMENTS_TIME desc");
		VolleyUtil.sendObjectByPostToListBypage(CommonVariable.GetMemberCardRecord, null, req, EnnCardRecord.class, new HttpBackListListener<EnnCardRecord>() {

			@Override
			public void onSuccess(List<EnnCardRecord> t) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				List<EnnCardRecord> newlist = (List<EnnCardRecord>) t;
				if (clear)
					list.clear();
				if (newlist != null)
					list.addAll(newlist);
				adapter.set_list(list);
				adapter.notifyDataSetChanged();
				listview.onRefreshComplete();
				if (newlist != null && newlist.size() > 0) {
					if (newlist.size() < pagesize) {
						listview.setMode(Mode.PULL_FROM_START);
					} else {
						listview.setMode(Mode.BOTH);
					}
				} else {
					listview.setMode(Mode.PULL_FROM_START);
				}

			}

			@Override
			public void onFail(String failstring) {
				customProgressDialog.dismiss();
				Toast.makeText(MembercardRecordActivity.this, "加载数据失败！", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(VolleyError error) {
				customProgressDialog.dismiss();
				Toast.makeText(MembercardRecordActivity.this, "加载数据失败！", Toast.LENGTH_SHORT).show();
			}
		}, false, null);
	}
	@Override
	protected void onResume() {
		super.onResume();
		pageNum = 0;
		initdata(true);
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}
}
