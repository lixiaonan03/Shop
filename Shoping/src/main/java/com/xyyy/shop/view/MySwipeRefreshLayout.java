package com.xyyy.shop.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 处理下拉刷新监听和首页轮播图滑动的监听
 * @author lxn
 *
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {

	private float xDistance;
	private float yDistance;
	private float xLast;
	private float yLast;

	public MySwipeRefreshLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
   
	public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		//正常情况下 如果执行了下拉刷新方法  这个方法返回了true  现在改成了如果左右滑动距离大于上下滑动距离  让它返回false 交个下一个view处理
        case MotionEvent.ACTION_DOWN:
            xDistance = yDistance = 0f;
            xLast = ev.getX();
            yLast = ev.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            final float curX = ev.getX();
            final float curY = ev.getY();

            xDistance += Math.abs(curX - xLast);
            yDistance += Math.abs(curY - yLast);
            xLast = curX;
            yLast = curY;
            
            if (xDistance > yDistance) {
                return false;
            }
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return super.onTouchEvent(arg0);
	}
    
}
