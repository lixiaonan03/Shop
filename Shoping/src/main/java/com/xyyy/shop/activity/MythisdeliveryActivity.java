package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.MythisdeliveryItemAdapter;
import com.xyyy.shop.model.OrderDeliveItemDTO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;
import com.xyyy.shop.view.MyListView;

/**
 * 会员卡配送清单的界面
 */
public class MythisdeliveryActivity extends BaseActivity {

    private ImageView top_back;
    private TextView top_text;
    private MyListView listthis;//本次配送
    private MyListView listhistory;//历史配送
    private MyListView listhistorytwo;//历史配送展开更多的
    private MythisdeliveryItemAdapter adapter1;//本次配送的适配器
    private MythisdeliveryItemAdapter adapter2;//历史配送的适配器
    private MythisdeliveryItemAdapter adapter3;//历史配送展开更多的适配器
    private List<OrderDeliveItemDTO> list1 = new ArrayList<OrderDeliveItemDTO>();
    private List<OrderDeliveItemDTO> listhistoryall = new ArrayList<OrderDeliveItemDTO>();//历史配送的全部数据
    private List<OrderDeliveItemDTO> list2 = new ArrayList<OrderDeliveItemDTO>();
    private List<OrderDeliveItemDTO> list3 = new ArrayList<OrderDeliveItemDTO>();//历史配送展开的数据
    private CustomProgressDialog customProgressDialog;
    private ScrollView yesdata;
    private RelativeLayout nodata;
    private LinearLayout nodatalin;
    private TextView textopen;
    private boolean openflag = false;//是否展开标记  false 不展开  true展开

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mythisdelivery);
        customProgressDialog = new CustomProgressDialog(MythisdeliveryActivity.this, "正在加载......");
        top_back = (ImageView) findViewById(R.id.top_back);
        top_text = (TextView) findViewById(R.id.top_text);
        top_text.setText("会员卡配送清单");
        top_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        yesdata = (ScrollView) findViewById(R.id.yesdata);
        nodata = (RelativeLayout) findViewById(R.id.nodata);
        nodatalin = (LinearLayout) findViewById(R.id.nodatalin);
        nodatalin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 购买会员卡
                Intent intent = new Intent(MythisdeliveryActivity.this,
                        MemberListActivity.class);
                startActivity(intent);
            }
        });

        listthis = (MyListView) findViewById(R.id.listthis);
        adapter1 = new MythisdeliveryItemAdapter(MythisdeliveryActivity.this, list1);
        listthis.setAdapter(adapter1);
        listthis.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> paramAdapterView,
                                    View paramView, int paramInt, long paramLong) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MythisdeliveryActivity.this, DeliverydetatilActivity.class);
                intent.putExtra("id", list1.get(paramInt).getEnnOrder().getOrderId());
                startActivity(intent);
            }
        });
        listhistory = (MyListView) findViewById(R.id.listhistory);
        adapter2 = new MythisdeliveryItemAdapter(MythisdeliveryActivity.this, list2);
        listhistory.setAdapter(adapter2);
        listhistory.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> paramAdapterView,
                                    View paramView, int paramInt, long paramLong) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MythisdeliveryActivity.this, DeliverydetatilActivity.class);
                intent.putExtra("id", list2.get(paramInt).getEnnOrder().getOrderId());
                startActivity(intent);
            }
        });

        textopen = (TextView) findViewById(R.id.textopen);
        textopen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openflag) {
                    //当前是展开状态
                    openflag = false;
                    textopen.setText("点击展开");
                    listhistorytwo.setVisibility(View.GONE);
                } else {
                    openflag = true;
                    textopen.setText("点击收起");
                    listhistorytwo.setVisibility(View.VISIBLE);
                }
            }
        });
        listhistorytwo = (MyListView) findViewById(R.id.listhistorytwo);
        adapter3 = new MythisdeliveryItemAdapter(MythisdeliveryActivity.this, list3);
        listhistorytwo.setAdapter(adapter3);
        listhistorytwo.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> paramAdapterView,
                                    View paramView, int paramInt, long paramLong) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MythisdeliveryActivity.this, DeliverydetatilActivity.class);
                intent.putExtra("id", list3.get(paramInt).getEnnOrder().getOrderId());
                startActivity(intent);
            }
        });

        initdata();
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
        VolleyUtil.sendStringRequestByGetToList(CommonVariable.GetDeliveRecodeURL + userid, null, null, OrderDeliveItemDTO.class, new HttpBackListListener<OrderDeliveItemDTO>() {

            @Override
            public void onSuccess(List<OrderDeliveItemDTO> t) {
                customProgressDialog.dismiss();
                if (null != t && t.size() > 0) {
                    yesdata.setVisibility(View.VISIBLE);
                    nodata.setVisibility(View.GONE);
                    list1.clear();
                    listhistoryall.clear();
                    for (OrderDeliveItemDTO orderDeliveItemDTO : t) {
                        if (null != orderDeliveItemDTO.getStatus() && orderDeliveItemDTO.getStatus().equals("01")) {
                            // 01 本次配送
                            list1.add(orderDeliveItemDTO);
                        }
                        if (null != orderDeliveItemDTO.getStatus() && orderDeliveItemDTO.getStatus().equals("02")) {
                            // 02 历史配送
                            listhistoryall.add(orderDeliveItemDTO);
                        }
                    }
                    adapter1.set_list(list1);
                    adapter1.notifyDataSetChanged();
                    // TODO 按配送时间排序
                    Collections.sort(listhistoryall, new Comparator<OrderDeliveItemDTO>() {

                        @Override
                        public int compare(OrderDeliveItemDTO paramT1, OrderDeliveItemDTO paramT2) {
                            if (paramT1.getEnnOrder().getMemReceDate() == null
                                    || paramT2.getEnnOrder().getMemReceDate() == null) {
                                return 1;
                            }
                            return paramT2
                                    .getEnnOrder()
                                    .getMemReceDate()
                                    .compareTo(paramT1.getEnnOrder().getMemReceDate());
                        }

                    });
                    if (listhistoryall.size() > 3) {
                        textopen.setVisibility(View.VISIBLE);
                        list2.add(listhistoryall.get(0));
                        list2.add(listhistoryall.get(1));
                        list2.add(listhistoryall.get(2));
                        adapter2.set_list(list2);
                        adapter2.notifyDataSetChanged();
                        for (int i = 3; i < listhistoryall.size(); i++) {
                            list3.add(listhistoryall.get(i));
                        }
                        if (list3.size() > 12) {
                            list3 = list3.subList(0, 12);
                        }
                        adapter3.set_list(list3);
                        adapter3.notifyDataSetChanged();
                    } else {
                        list2.addAll(listhistoryall);
                        adapter2.set_list(list2);
                        adapter2.notifyDataSetChanged();
                        textopen.setVisibility(View.GONE);
                    }

                } else {
                    yesdata.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String failstring) {
                yesdata.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                customProgressDialog.dismiss();
                Toast.makeText(MythisdeliveryActivity.this, "加载失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VolleyError error) {
                yesdata.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                customProgressDialog.dismiss();
                Toast.makeText(MythisdeliveryActivity.this, "加载失败！", Toast.LENGTH_SHORT).show();
            }
        }, false, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
