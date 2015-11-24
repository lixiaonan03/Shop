package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.adapter.MembercardRecordItemAdapter;
import com.xyyy.shop.model.EnnCardRecord;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;
/**
 * 会员卡消费记录
 */
public class MembercardRecordActivity extends BaseActivity {
	
	

	private ImageView top_back;
	private ListView listview;
	private List<EnnCardRecord> list=new ArrayList<EnnCardRecord>();
	private MembercardRecordItemAdapter adapter;
	private CustomProgressDialog customProgressDialog;
	private TextView top_text;
	private String cardid;
	private int isfuwu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_membercardrecordlist);
		cardid=getIntent().getStringExtra("cardid");
		isfuwu = getIntent().getIntExtra("isfuwu", 0);
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
		listview=(ListView)findViewById(R.id.listview);
		adapter=new MembercardRecordItemAdapter(MembercardRecordActivity.this, list);
		adapter.setIsfuwu(isfuwu);
		listview.setAdapter(adapter);
	}
	/**
	 * 加载数据
	 */
	private void initdata() {
		customProgressDialog.show();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("cardId", cardid);
		VolleyUtil.sendObjectByPostToList(CommonVariable.GetMemberCardRecord, null, map, EnnCardRecord.class,new HttpBackListListener<EnnCardRecord>(){

			@Override
			public void onSuccess(List<EnnCardRecord> t) {
				// TODO Auto-generated method stub
				customProgressDialog.dismiss();
				if(null!=t){
					list=t;
					adapter.set_list(list);
					adapter.notifyDataSetChanged();
				}
				
			}

			@Override
			public void onFail(String failstring) {
				customProgressDialog.dismiss();
				Toast.makeText(MembercardRecordActivity.this,"加载数据失败！",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(VolleyError error) {
				customProgressDialog.dismiss();
				Toast.makeText(MembercardRecordActivity.this,"加载数据失败！",Toast.LENGTH_SHORT).show();
			}}, false, null);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initdata();
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
