package com.jnu.myrecycle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.myrecycle.R;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ClockView extends View {
    private int mHour,mMinute,mSecond;
    private Drawable clockBackground;
    private Drawable hourHand,minuteHand,secondHand;
    float centerX,centerY;


    private android.os.Handler mHandler;
    private Runnable mRunnable;
    public ClockView(Context context) {
        super(context);
        init();
    }

    private void init() {
        // 初始化时钟背景和指针的 Drawable 对象
        clockBackground = getResources().getDrawable(R.drawable.clock);
        hourHand = getResources().getDrawable(R.drawable.hh);
        minuteHand = getResources().getDrawable(R.drawable.mm);
        secondHand = getResources().getDrawable(R.drawable.ss);

        // 初始化Handler和Runnable
        mHandler = new android.os.Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                // 获取当前时间
                Calendar calendar = Calendar.getInstance();
                // 创建Toast显示当前时间
                Toast.makeText(getContext(), "Current time: " + mHour + ":" + mMinute + " : " + mSecond, Toast.LENGTH_SHORT).show();
                // 每五秒后再次执行Runnable
                mHandler.postDelayed(this, 5000);
            }
        };
        mHandler.postDelayed(mRunnable, 5000);
}
    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 计算时针、分针、秒针的位置
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        clockBackground.setBounds(0, 0, getWidth(), getHeight());
        clockBackground.draw(canvas);

        Calendar calendar = Calendar.getInstance();


        setTime(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        // 绘制时针
        drawHand(canvas, hourHand, mHour * 30 + mMinute / 2);
        // 绘制分针
        drawHand(canvas, minuteHand, mMinute * 6);
        // 绘制秒针
        drawHand(canvas, secondHand, mSecond * 6);
        // 每秒钟刷新界面
        postInvalidateDelayed(1000);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    public void setTime(int hour, int minute, int second) {
        mHour = hour;
        mMinute = minute;
        mSecond = second;
        invalidate();
    }
    private void drawHand(Canvas canvas, Drawable hand, int angle) {
        canvas.save();
        canvas.rotate(angle, centerX, centerY);
        hand.setBounds((int)(centerX - hand.getIntrinsicWidth()/1.2) , (int)(centerY - hand.getIntrinsicHeight() /1.2),
                (int)(centerX + hand.getIntrinsicWidth()/1.2) , (int)(centerY + hand.getIntrinsicHeight()/1.2));
        hand.draw(canvas);
        canvas.restore();
    }

}