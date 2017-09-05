package com.linghao.mychattest.utils.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.linghao.mychattest.utils.LogUtil;

/**
 * Created by linghao on 2017/8/2.
 * QQ：782695971
 * 作用：
 */

public class MyViewPager extends ViewPager{
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private float downX;
    private float downY;
    private float moveX;
    private float moveY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
         super.onTouchEvent(ev);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX=ev.getX();
                downY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                moveX=Math.abs(endX-downX);
                moveY=Math.abs(endY-downY);
                if (moveX>moveY){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.e(moveX+"------"+moveY);
                if (moveX>moveY){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return true;
    }
}
