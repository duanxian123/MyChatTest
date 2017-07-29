package com.linghao.mychattest.model.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.linghao.mychattest.R;
import com.linghao.mychattest.model.Model;
import com.linghao.mychattest.model.bean.Recruit;
import com.linghao.mychattest.utils.LogUtil;

import java.util.List;

/**
 * Created by linghao on 2017/7/16.
 */

public class MytoggleButton extends View implements  View.OnClickListener{
    private Bitmap bt_background;
    private  Bitmap bt_sliding;
    private int leftMax;
    private int left;
    private Paint paint;
    private float startX;
    private float lastX;
    myToggleButtonListener listener;

    public MytoggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private  boolean isopen=false;
    private boolean isEnableClick=true;
    private void initView() {
        paint=new Paint();
        paint.setAntiAlias(true);
        bt_background= BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        bt_sliding=BitmapFactory.decodeResource(getResources(),R.drawable.slide_button);
        leftMax=bt_background.getWidth()-bt_sliding.getWidth();
        setOnClickListener(this);
    }

    public void openToggle() {
        if(isopen){
            LogUtil.e("/////////////////////////////////");
            listener.close();
            left=leftMax;



        }
        else
        {listener.open();
            left=0;



        }
        invalidate();
    }
//    private void closeTheView(View view) {
//        view.setVisibility(View.GONE);
//    }
//    private void openAnotherView(View view) {
//        view.setVisibility(View.VISIBLE);
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bt_background.getWidth(),bt_background.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
       canvas.drawBitmap(bt_background,0,0,paint);
        canvas.drawBitmap(bt_sliding,left,0,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
               lastX= startX=event.getX();
                isEnableClick=true;
                break;
            case MotionEvent.ACTION_MOVE:
                 float endX=event.getX();
                float distanX=endX-startX;
                left= (int) (left+distanX);
                if(left<0){
                    left=0;
                }
                if (left>leftMax){
                    left=leftMax;
                }
                invalidate();
                startX=event.getX();
                if(Math.abs(endX-lastX)>5){
                    isEnableClick=false;
                }

                break;
            case MotionEvent.ACTION_UP:
                if (isEnableClick==false){
                if(left>leftMax/2){
                    isopen=true;
                }
                else
                {
                    isopen=false;
                }
                openToggle();
                }
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(isEnableClick){
            isopen=!isopen;
            openToggle();}
    }
    public void setmyToggleButtonListener(myToggleButtonListener listener){
        this.listener=listener;
    }
    public interface myToggleButtonListener{
        void open();
        void close();
    }

}
