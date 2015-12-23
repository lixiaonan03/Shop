package com.xyyy.shop.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.activity.BindMemberActivity;
import com.xyyy.shop.activity.CartActivity;
import com.xyyy.shop.activity.LoginActivity;
import com.xyyy.shop.activity.MemberCardDetailActivity;
import com.xyyy.shop.activity.MemberListActivity;
import com.xyyy.shop.adapter.MymemberItemAdapter;
import com.xyyy.shop.model.MemCardVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.toolUtil.StrToNumber;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.MyListView;

/**
 * 会员卡模块
 * 
 * @author lxn
 *
 */
public class MemberFragment extends Fragment {

	private RelativeLayout gobuymemberLin;// 购买会员卡
	private RelativeLayout gobindmemberLin;// 绑定会员卡
	private MyListView listview;
	private List<MemCardVO> list = new ArrayList<MemCardVO>();
	private MymemberItemAdapter adapter;
	private CustomProgressDialog customProgressDialog;
	private FrameLayout yeslogin;
	private LinearLayout nodata;
	private View login_line;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_member, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		customProgressDialog = new CustomProgressDialog(getActivity(),
				"正在加载......");
		initView();
		changeviewbylogin();
	}

	/**
	 * 根据登录状态改变view
	 */
	void changeviewbylogin() {
		if (ShopApplication.isLogin) {
			yeslogin.setVisibility(View.VISIBLE);
			login_line.setVisibility(View.VISIBLE);
			initdata();
		} else {
			yeslogin.setVisibility(View.GONE);
			login_line.setVisibility(View.GONE);
		}
	}

	private void initView() {
		// TODO 初始化控件
		nodata = (LinearLayout) getView().findViewById(R.id.nodata);
		login_line = (View) getView().findViewById(R.id.login_line);
		yeslogin = (FrameLayout) getView().findViewById(R.id.yeslogin);

		gobuymemberLin = (RelativeLayout) getView().findViewById(
				R.id.gobuymemberLin);
		gobindmemberLin = (RelativeLayout) getView().findViewById(
				R.id.gobindmemberLin);
		gobuymemberLin.setOnClickListener(viewclick);
		gobindmemberLin.setOnClickListener(viewclick);

		listview = (MyListView) getView().findViewById(R.id.listview);
		listview.setEmptyView(nodata);

		adapter = new MymemberItemAdapter(getActivity(), list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				StatService.onEvent(getActivity(), "member_detail" , "进入会员卡详情界面");
				// TODO 点击进入我的会员卡详情界面
				Intent intent = new Intent(getActivity(),
						MemberCardDetailActivity.class);
				int carttype = 0;
				int cardflag = 0;
				int cardid = 0;
				int isfuwu=0;
				if (null != adapter.get_list().get(arg2).getEnnCardType()) {
					carttype = StrToNumber.strToint(adapter.get_list()
							.get(arg2).getEnnCardType().getCardType());
					if(null!=adapter.get_list().get(arg2).getEnnCardType().getServiceType()&&adapter.get_list().get(arg2).getEnnCardType().getServiceType().equals("02")){
						isfuwu=1;
					}
				}
				if (null != adapter.get_list().get(arg2).getEnnCard()) {
					cardflag = StrToNumber.strToint(adapter.get_list()
							.get(arg2).getEnnCard().getCardState());
					cardid = adapter.get_list().get(arg2).getEnnCard()
							.getCardId();
				}
				intent.putExtra("cardtype", carttype);
				intent.putExtra("cardflag", cardflag);
				intent.putExtra("cradid", cardid);
				intent.putExtra("isfuwu", isfuwu);
				getActivity().startActivity(intent);
			}
		});
	}

	private void initdata() {
		// TODO 加载数据
		customProgressDialog.show();
		int userid = 0;
		if (ShopApplication.isLogin) {
			if (ShopApplication.loginflag == 1) {
				userid = ShopApplication.userid;
			}
			if (ShopApplication.loginflag == 2) {
				userid = ShopApplication.useridother;
			}
		}
		VolleyUtil.sendStringRequestByGetToList(
				CommonVariable.GetMyMemberURL + userid, null, null, MemCardVO.class,
				new HttpBackListListener<MemCardVO>() {

					@Override
					public void onSuccess(List<MemCardVO> t) {
						// TODO Auto-generated method stub
						customProgressDialog.dismiss();
						if (null != t && t.size() > 0) {
							nodata.setVisibility(View.GONE);
							listview.setVisibility(View.VISIBLE);
							 List<MemCardVO> listcurrent = new ArrayList<MemCardVO>();
								for (MemCardVO memCardVO : t) {
									if(null!=memCardVO&&null!=memCardVO.getEnnCardType()&&null!=memCardVO.getEnnCardType().getCardType()){
										if(memCardVO.getEnnCardType().getCardType().equals("10")){

										}else{
											listcurrent.add(memCardVO);
										}
									}

								}
							list = listcurrent;
							Collections.sort(list, new Comparator<MemCardVO>() {
								@Override
								public int compare(MemCardVO o1, MemCardVO o2) {
									String sales1 = o1.getEnnCard().getCardState();
									String sales2 = o2.getEnnCard().getCardState();
									return sales1.compareTo(sales2);
									// return result == 1 ? -1 : 1;
								}
							});

							Collections.sort(list, new Comparator<MemCardVO>() {
								@Override
								public int compare(MemCardVO o1, MemCardVO o2) {
									String sales1 = o1.getEnnCard().getIsFreeze();
									String sales2 = o2.getEnnCard().getIsFreeze();
									return sales1.compareTo(sales2);
								}
							});
							adapter.set_list(list);
							adapter.notifyDataSetChanged();
						} else {
							nodata.setVisibility(View.VISIBLE);
							listview.setVisibility(View.GONE);
						}
					}
					@Override
					public void onFail(String failstring) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "加载数据失败！",Toast.LENGTH_SHORT).show();
						nodata.setVisibility(View.VISIBLE);
						listview.setVisibility(View.GONE);
						customProgressDialog.dismiss();
					}

					@Override
					public void onError(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "加载数据失败！",Toast.LENGTH_SHORT).show();
						nodata.setVisibility(View.VISIBLE);
						listview.setVisibility(View.GONE);
						customProgressDialog.dismiss();
					}
				}, false, null);
	}

	OnClickListener viewclick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.gobuymemberLin:
				// 购买会员卡
				StatService.onEvent(getActivity(), "member_gobuy" , "购买会员卡条目点击");
				Intent intent = new Intent(getActivity(),
						MemberListActivity.class);
				getActivity().startActivity(intent);
				break;
			case R.id.gobindmemberLin:
				if (ShopApplication.isLogin) {
					Intent intentgobind = new Intent(getActivity(),
							BindMemberActivity.class);
					getActivity().startActivity(intentgobind);
				} else {
					Intent intentlogin = new Intent(getActivity(),
							LoginActivity.class);
					intentlogin.putExtra("flag", 03);
					startActivityForResult(intentlogin, 03);
				}
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		if (!hidden) {
			changeviewbylogin();
			StatService.onPageStart(getActivity(),	"会员卡模块");
		}else{
			StatService.onPageEnd(getActivity(),"会员卡模块");
		}
		super.onHiddenChanged(hidden);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if (!isHidden()) {
			changeviewbylogin();
		}
		super.onResume();
		if(ShopApplication.mainflag==3)
		StatService.onPageStart(getActivity(),	"会员卡模块");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(ShopApplication.mainflag==3)
		StatService.onPageEnd(getActivity(),"会员卡模块");
	}
}
