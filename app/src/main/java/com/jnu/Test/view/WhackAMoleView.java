package com.jnu.Test.view;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jnu.Test.R;
import java.util.ArrayList;
public class WhackAMoleView extends SurfaceView implements SurfaceHolder.Callback {
    private MoleThread moleThread;
    public int hitNum=0;
    private float touchX=-10,touchY=-10;
    private double X_X,Y_Y;
    boolean isPaint=true, retry;
    Canvas canvas;
    private Button button;

    public void setButton(Button button) {
        this.button = button;
        this.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartGameDialog();
                invalidate();
            }
        });
    }
    public WhackAMoleView(Context context, AttributeSet ar) {
        super(context, ar);
        init();
    }
    private void init() {

        getHolder().addCallback(this);

        moleThread = new MoleThread(getHolder(), this);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        moleThread.setRunning(true);
        moleThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 略过
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        retry = true;
        moleThread.setRunning(false);
        while (retry) {
            try {
                moleThread.join();
                retry = false;
            } catch (InterruptedException e) {}// 忽略
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.touchX = event.getX();
                this.touchY = event.getY();
                isPaint = true;
                break;
            case MotionEvent.ACTION_UP:
                if (button != null && button.getVisibility() == View.VISIBLE) {
                    int buttonX = (int) button.getX();
                    int buttonY = (int) button.getY();
                    int buttonWidth = button.getWidth();
                    int buttonHeight = button.getHeight();
                    if (touchX >= buttonX && touchX <= buttonX + buttonWidth &&
                            touchY >= buttonY && touchY <= buttonY + buttonHeight) {
                        button.performClick();
                    }
                }
                break;
        }
        return true;
    }
    // 判断点击坐标是否与GameSpriter对象重叠的辅助方法
    private boolean isHit(GameSpriter gameSpriter, float touchX, float touchY)
    {
        float x=(float)(X_X+gameSpriter.X_);
        float y=(float)(Y_Y+gameSpriter.Y_);
        RectF rect = new RectF(Math.abs(x-150), Math.abs(y-150), x + 150, y+ 150);
        return rect.contains(touchX, touchY);
    }

    public void showStartGameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("开始游戏");
        builder.setMessage("是否开始游戏？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                invalidate();
                Toast.makeText(getContext(), "30倒计时开始", Toast.LENGTH_SHORT).show();
                button.setVisibility(View.GONE);
                startGameCountdown();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void startGameCountdown() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                // 每秒执行的操作
            }
            public void onFinish() {
                showScoreDialog();
            }
        }.start();
    }

    private void showScoreDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("游戏结束");
        builder.setMessage("您的成绩是：" + hitNum);
        builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                button.setVisibility(View.VISIBLE);
                hitNum=0;
            }
        });
        builder.show();
        // 在合适的时机调用以下代码，将按钮重新显示
    }

    public class  MoleThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private WhackAMoleView whackAMoleView;
        private boolean running;
        public MoleThread(SurfaceHolder surfaceHolder, WhackAMoleView whackAMoleView) {
            this.surfaceHolder = surfaceHolder;
            this.whackAMoleView = whackAMoleView;
        }
        public void setRunning(boolean running) {
            this.running = running;
        }


        @Override
        public void run() {
            super.run();
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(80);
            paint.setStyle(Paint.Style.FILL);
            ArrayList<GameSpriter> gameSpriters = new ArrayList<GameSpriter>();
            ArrayList<GameSpriter>spritesToRemove = new ArrayList<GameSpriter>();
            int id_[] = {R.drawable.book_1,R.drawable.book_2,R.drawable.book_no_name};
            gameSpriters.add(new GameSpriter(Math.random(),Math.random(),id_[0]));
            while (running) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        canvas.drawColor(Color.GREEN);
                        for(GameSpriter gameSpriter:gameSpriters)
                        {
                            if (isHit(gameSpriter, touchX, touchY)&&isPaint) {
                                spritesToRemove.add(gameSpriter);
                                isPaint=false;
                                hitNum++;
                                canvas.drawText("Hit:" + hitNum, 10, 50, paint);
                                continue;
                            }
                            canvas.drawText("Hit:" + hitNum, 10, 50, paint);
                            gameSpriter.move();
                            gameSpriter.draw(canvas);
                        }
                        for(GameSpriter gameSpriter:spritesToRemove)
                        {
                            gameSpriters.remove(gameSpriter);
                            gameSpriters.add(new GameSpriter(Math.random(), Math.random(),id_[0]));
                        }
                        spritesToRemove.clear();
                    }


                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private class GameSpriter {
        private double X_,Y_;
        private int clockID, direction;
        public GameSpriter(double X_, double Y_, int imageid) {
            this.X_ = X_;
            this.Y_ = Y_;
            this.clockID = imageid;
            this.direction= (int) (Math.random()*Math.PI*2);
        }
        public void draw(Canvas canvas) {
                X_X = X_ * canvas.getWidth();
                Y_Y = Y_ * canvas.getHeight();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), clockID);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                canvas.drawBitmap(scaledBitmap, (float) X_X, (float) Y_Y, null);
        }
        public void move() {
            this.X_ += Math.sin(this.direction) * 0.01;
            this.Y_ += Math.cos(this.direction) * 0.01;
            // 检查X坐标是否越界
            if (this.X_ <=0) this.X_ = 1;
            else if (this.X_>=1) this.X_ = 0;
            // 检查Y坐标是否越界
            if (this.Y_ <=0) this.Y_ =1 ;
            else if (this.Y_ >= 1) this.Y_ =0;
            if (Math.random() < 0.05) this.direction = (int) (Math.random() * Math.PI * 2);
        }
    }
}

