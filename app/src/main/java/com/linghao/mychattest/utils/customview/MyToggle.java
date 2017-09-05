package com.linghao.mychattest.utils.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by linghao on 2017/8/4.
 * QQ：782695971
 * 作用：
 */

public class MyToggle extends View implements View.OnClickListener {

    private int width;
    private int left;
    private int right;
    private boolean first;

    private boolean isOpen=false;
    private myToggleButtonListener listener;

    public MyToggle(Context context) {
        this(context,null);
    }

    public MyToggle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyToggle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
        Log.e("jinlai","3");
        first=true;

    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("jinlai","1");
        width = this.getMeasuredWidth();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("jinlai","2");
        Paint paint=new Paint();
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        canvas.drawRect(0,0,width,200,paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        paint.setStrokeWidth(0);
        String text = "社区";
        Rect bound = new Rect();//此时包裹文本的矩形框没有宽度和高度
        paint.getTextBounds(text,0,text.length(),bound);//使其宽度和高度整好包裹文本内容
        //提供文本区域的左、下
        int left1 = width/6;
        int bottom = 150;
        int left2=width-bound.width()-width/6;
        canvas.drawText(text,left1,bottom,paint);
        canvas.drawText("招募",left2,bottom,paint);

        //绘制按钮长方形
        paint.setColor(Color.RED);
        ;if (first){
            canvas.drawRect(width/2,0,width,200,paint);
            first=false;

        }
        else{
                canvas.drawRect(left,0,right,200,paint);}

    }

    @Override
    public void onClick(View v) {
        isOpen=!isOpen;
        openToggle();
    }

    private void openToggle() {
        if(isOpen){
            left=width/2;
            right=width;
            listener.close();

        }
        else
        {
            left=0;
            right=width/2;
            listener.open();
        }
        invalidate();
    }
    public void setmyToggleButtonListener(myToggleButtonListener listener){
        this.listener=listener;
    }
    public interface myToggleButtonListener{
        void open();
        void close();
    }
}
