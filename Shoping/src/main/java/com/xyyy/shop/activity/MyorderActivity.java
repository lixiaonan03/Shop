package com.xyyy.shop.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.listener.HttpBackListListener;
import com.android.volley.util.VolleyUtil;
import com.baidu.mobstat.StatService;
import com.xyyy.shop.R;
import com.xyyy.shop.ShopApplication;
import com.xyyy.shop.adapter.MyOrderItemAdapter;
import com.xyyy.shop.model.OrderStatusDTO;
import com.xyyy.shop.model.OrderVO;
import com.xyyy.shop.toolUtil.CommonVariable;
import com.xyyy.shop.view.CustomProgressDialog;

/**
 * 我的订单的界面
 */
public class MyorderActivity extends BaseActivity {

    private ImageView top_back;
    private TextView top_text;
    private TextView all_order;
    private TextView nopay_order;
    private TextView nodelivery_order;
    private TextView noevaluate_order;
    private TextView reimburse_order;
    private View lineall;
    private View linenopay;
    private View linenodelivery;
    private View linenoevaluate;
    private View linereimburse;
    private ListView listview;

    private List<OrderVO> list = new ArrayList<OrderVO>();//全部订单的数据
    private List<OrderVO> currentshowlist = new ArrayList<OrderVO>();//当前展示用的list
    private MyOrderItemAdapter adapter;
    private int flag = 1;//那个选项卡被选中 1全部订单 2未付款 3未收货 4未评价 5已退款暂时先不做已退款的

    private int inflag = 0;//进入标志  1 表示从首页进入的
    private CustomProgressDialog customProgressDialog;
    private RelativeLayout noorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);
        top_back = (ImageView) findViewById(R.id.top_back);
        top_text = (TextView) findViewById(R.id.top_text);
        top_text.setText("我的订单");

        inflag = getIntent().getIntExtra("inflag", 0);

        top_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (inflag == 1) {
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("flag", 4);
                    startActivity(intent);
                    finish();
                }

            }
        });

        customProgressDialog = new CustomProgressDialog(MyorderActivity.this, "正在加载......");
        initView();
        initdata();
    }

    /**
     * 初始化界面组件
     */
    private void initView() {
        all_order = (TextView) findViewById(R.id.all_order);
        nopay_order = (TextView) findViewById(R.id.nopay_order);
        nodelivery_order = (TextView) findViewById(R.id.nodelivery_order);
        noevaluate_order = (TextView) findViewById(R.id.noevaluate_order);
        reimburse_order = (TextView) findViewById(R.id.reimburse_order);

        noorder = (RelativeLayout) findViewById(R.id.noorder);

        all_order.setOnClickListener(viewclick);
        nopay_order.setOnClickListener(viewclick);
        nodelivery_order.setOnClickListener(viewclick);
        noevaluate_order.setOnClickListener(viewclick);
        reimburse_order.setOnClickListener(viewclick);

        lineall = (View) findViewById(R.id.lineall);
        linenopay = (View) findViewById(R.id.linenopay);
        linenodelivery = (View) findViewById(R.id.linenodelivery);
        linenoevaluate = (View) findViewById(R.id.linenoevaluate);
        linereimburse = (View) findViewById(R.id.linereimburse);

        listview = (ListView) findViewById(R.id.listview);

        adapter = new MyOrderItemAdapter(MyorderActivity.this, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO 条目点击的事件 进入订单详情的界面
                Intent intent = new Intent(MyorderActivity.this, OrderDetailActivity.class);
                intent.putExtra("id", adapter.get_list().get(arg2).getOrderId());
                startActivity(intent);
            }
        });
    }

    /**
     * 顶部tab页改变之
     */
    private void changetab() {
        all_order.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
        nopay_order.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
        nodelivery_order.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
        noevaluate_order.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));
        reimburse_order.setTextColor(getResources().getColor(R.color.order_textcolor_nochecked));

        all_order.setBackgroundResource(R.color.order_background_nochecked);
        nopay_order.setBackgroundResource(R.color.order_background_nochecked);
        nodelivery_order.setBackgroundResource(R.color.order_background_nochecked);
        noevaluate_order.setBackgroundResource(R.color.order_background_nochecked);
        reimburse_order.setBackgroundResource(R.color.order_background_nochecked);

        lineall.setBackgroundResource(R.color.order_linecolor_nochecked);
        linenopay.setBackgroundResource(R.color.order_linecolor_nochecked);
        linenodelivery.setBackgroundResource(R.color.order_linecolor_nochecked);
        linenoevaluate.setBackgroundResource(R.color.order_linecolor_nochecked);
        linereimburse.setBackgroundResource(R.color.order_linecolor_nochecked);

        switch (flag) {
            case 1:
                all_order.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
                all_order.setBackgroundResource(R.color.order_background_checked);
                lineall.setBackgroundResource(R.color.order_linecolor_checked);
                break;
            case 2:
                nopay_order.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
                nopay_order.setBackgroundResource(R.color.order_background_checked);
                linenopay.setBackgroundResource(R.color.order_linecolor_checked);
                break;
            case 3:
                nodelivery_order.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
                nodelivery_order.setBackgroundResource(R.color.order_background_checked);
                linenodelivery.setBackgroundResource(R.color.order_linecolor_checked);
                break;
            case 4:
                noevaluate_order.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
                noevaluate_order.setBackgroundResource(R.color.order_background_checked);
                linenoevaluate.setBackgroundResource(R.color.order_linecolor_checked);
                break;
            case 5:
                reimburse_order.setTextColor(getResources().getColor(R.color.order_textcolor_checked));
                reimburse_order.setBackgroundResource(R.color.order_background_checked);
                linereimburse.setBackgroundResource(R.color.order_linecolor_checked);
                break;

            default:
                break;
        }
    }

    /**
     * 当前数据list改变
     */
    private void changecurrentlist() {
        if (currentshowlist.size() > 0) {
            noorder.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            adapter.set_list(currentshowlist);
            adapter.notifyDataSetChanged();
        } else {
            noorder.setVisibility(View.VISIBLE);
            listview.setVisibility(View.GONE);
        }
    }

    OnClickListener viewclick = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.all_order:
                    //全部订单
                    flag = 1;
                    changetab();
                    currentshowlist.clear();
                    currentshowlist.addAll(list);
                    changecurrentlist();
                    break;
                case R.id.nopay_order:
                    //未付款
                    flag = 2;
                    changetab();
                    currentshowlist.clear();
                    for (OrderVO order : list) {
                        if (order.getOrderStatus().equals("01")) {
                            currentshowlist.add(order);
                        }
                    }
                    changecurrentlist();

                    break;
                case R.id.nodelivery_order:
                    //未收货
                    flag = 3;
                    changetab();
                    currentshowlist.clear();
                    for (OrderVO order : list) {
                        if (order.getOrderStatus().equals("02") || order.getOrderStatus().equals("03") || order.getOrderStatus().equals("04")) {
                            currentshowlist.add(order);
                        }
                    }
                    changecurrentlist();
                    break;
                case R.id.noevaluate_order:
                    //未评价
                    flag = 4;
                    changetab();
                    currentshowlist.clear();
                    for (OrderVO order : list) {
                        if (order.getOrderStatus().equals("05")) {
                            currentshowlist.add(order);
                        }
                    }
                    changecurrentlist();
                    break;
                case R.id.reimburse_order:
                    //已退款
                /*flag=5;
                changetab();
				currentshowlist.clear();
				for (OrderVO order : list) {
					if(order.getOrderStatus().equals("04")){
						currentshowlist.add(order);
					}
				}
				changecurrentlist();*/
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 加载数据
     */
    private void initdata() {
        // TODO Auto-generated method stub
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

        OrderStatusDTO orderstatusdto = new OrderStatusDTO();
        orderstatusdto.setMembId(userid);
        List<String> orderStatuses = new ArrayList<String>();
        orderStatuses.add("01");
        orderStatuses.add("02");
        orderStatuses.add("03");
        orderStatuses.add("04");
        orderStatuses.add("05");
        orderStatuses.add("06");
        orderStatuses.add("07");
        orderstatusdto.setOrderStatuses(orderStatuses);
        VolleyUtil.sendObjectByPostToList(CommonVariable.GetAllOrderURL, null, orderstatusdto, OrderVO.class, new HttpBackListListener<OrderVO>() {

            @Override
            public void onSuccess(List<OrderVO> t) {
                // TODO Auto-generated method stub
                if (null != t && t.size() > 0) {
				/*	noorder.setVisibility(View.GONE);
					listview.setVisibility(View.VISIBLE);*/
                    customProgressDialog.dismiss();
                    list.clear();
                    for (OrderVO one : t) {
                        if (null != one.getOrderType() && !one.getOrderType().equals("07")) {
                            if (one.getOrderStatus().equals("06")) {
                                if (one.getPayFlag() != null && one.getPayFlag().equals("1")) {
                                    one.setOrderStatus("07");
                                    list.add(one);
                                }
                            }
                            if (one.getOrderStatus().equals("01") || one.getOrderStatus().equals("02") || one.getOrderStatus().equals("03")
                                    || one.getOrderStatus().equals("04") || one.getOrderStatus().equals("05") || one.getOrderStatus().equals("07")) {
                                list.add(one);
                            }
                        }
                    }

                    switch (flag) {
                        case 1:
						/*adapter.set_list(list);
						adapter.notifyDataSetChanged();*/
                            currentshowlist.clear();
                            currentshowlist.addAll(list);
                            changecurrentlist();
                            break;
                        case 2:
                            currentshowlist.clear();
                            for (OrderVO order : list) {
                                if (order.getOrderStatus().equals("01")) {
                                    currentshowlist.add(order);
                                }
                            }
                            changecurrentlist();
                            break;
                        case 3:
                            currentshowlist.clear();
                            for (OrderVO order : list) {
                                if (order.getOrderStatus().equals("02") || order.getOrderStatus().equals("03") || order.getOrderStatus().equals("04")) {
                                    currentshowlist.add(order);
                                }
                            }
                            changecurrentlist();
                            break;
                        case 4:
                            currentshowlist.clear();
                            for (OrderVO order : list) {
                                if (order.getOrderStatus().equals("05")) {
                                    currentshowlist.add(order);
                                }
                            }
                            changecurrentlist();
                            break;

                        default:
                            break;
                    }
                } else {
                    customProgressDialog.dismiss();
                    noorder.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(String failstring) {
                // TODO Auto-generated method stub
                customProgressDialog.dismiss();
                noorder.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                Toast.makeText(MyorderActivity.this, "加载数据失败！", 0).show();
            }

            @Override
            public void onError(VolleyError error) {
                // TODO Auto-generated method stub
                customProgressDialog.dismiss();
                noorder.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                Toast.makeText(MyorderActivity.this, "加载数据失败！", 0).show();
            }
        }, false, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        int goflag = intent.getIntExtra("goflag", 0);
        switch (goflag) {
            case 1:

                break;
            case 4:
                //待评价
                flag = 4;
                changetab();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (inflag == 1) {
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("flag", 4);
            startActivity(intent);
            finish();
        }
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
