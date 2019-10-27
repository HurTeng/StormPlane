package com.hurteng.stormplane.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.hurteng.stormplane.constant.ConstantUtil;
import com.hurteng.stormplane.MainActivity;
import com.hurteng.stormplane.myplane.R;
import com.hurteng.stormplane.plane.BigPlane;
import com.hurteng.stormplane.plane.MiddlePlane;
import com.hurteng.stormplane.plane.MyPlane;
import com.hurteng.stormplane.plane.SmallPlane;
import com.hurteng.stormplane.sounds.GameSoundPool;

/**
 * 游戏结束
 */
@SuppressLint("ViewConstructor")
public class EndView extends BaseView {
    private int score;
    private float button_x;
    private float button_y;
    private float button_y2;
    private float strwid;
    private float strhei;
    private boolean isBtChange;                // ��ťͼƬ�ı�ı��
    private boolean isBtChange2;
    private String startGame = "重新开始";    // ��ť������
    private String exitGame = "退出游戏";
    private Bitmap button;                    // ��ťͼƬ
    private Bitmap button2;                    // ��ťͼƬ
    private Bitmap background;                // ����ͼƬ
    private Rect rect;                        // �������ֵ�����
    private MainActivity mainActivity;

    public EndView(Context context, GameSoundPool sounds) {
        super(context, sounds);
        this.mainActivity = (MainActivity) context;
        rect = new Rect();
        thread = new Thread(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        super.surfaceChanged(arg0, arg1, arg2, arg3);
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        super.surfaceCreated(arg0);
        initBitmap();
        if (thread.isAlive()) {
            thread.start();
        } else {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        super.surfaceDestroyed(arg0);
        release();
    }

    /**
     * 触摸事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1) {
            float x = event.getX();
            float y = event.getY();

            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y && y < button_y + button.getHeight()) {
                sounds.playSound(7, 0);
                isBtChange = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
                MyPlane.setsmallAmount();
                MyPlane.setmiddleAmount();
                MyPlane.setbigAmount();

            }

            else if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y2 && y < button_y2 + button.getHeight()) {
                sounds.playSound(7, 0);
                isBtChange2 = true;
                drawSelf();
                threadFlag = false;
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y && y < button_y + button.getHeight()) {
                isBtChange = true;
            } else {
                isBtChange = false;
            }
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y2 && y < button_y2 + button.getHeight()) {
                isBtChange2 = true;
            } else {
                isBtChange2 = false;
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isBtChange = false;
            isBtChange2 = false;
            return true;
        }
        return false;
    }

    @Override
    public void initBitmap() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
        button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
        button2 = BitmapFactory.decodeResource(getResources(), R.drawable.button2);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        button_x = screen_width / 2 - button.getWidth() / 2;
        button_y = screen_height / 2 + button.getHeight();
        button_y2 = button_y + button.getHeight() + 40;
        paint.setTextSize(40);
        paint.getTextBounds(startGame, 0, startGame.length(), rect);
        strwid = rect.width();
        strhei = rect.height();
    }

    @Override
    public void release() {
        if (!button.isRecycled()) {
            button.recycle();
        }
        if (!button2.isRecycled()) {
            button2.recycle();
        }
        if (!background.isRecycled()) {
            background.recycle();
        }
    }

    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK);                        // 颜色
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);                    // 缩放
            canvas.drawBitmap(background, 0, 0, paint);        // 图片
            canvas.restore();
            if (isBtChange) {
                canvas.drawBitmap(button2, button_x, button_y, paint);
            } else {
                canvas.drawBitmap(button, button_x, button_y, paint);
            }
            if (isBtChange2) {
                canvas.drawBitmap(button2, button_x, button_y2, paint);
            } else {
                canvas.drawBitmap(button, button_x, button_y2, paint);
            }
            paint.setTextSize(40);
            paint.getTextBounds(startGame, 0, startGame.length(), rect);
            canvas.drawText(startGame, screen_width / 2 - strwid / 2, button_y + button.getHeight() / 2 + strhei / 2, paint);
            canvas.drawText(exitGame, screen_width / 2 - strwid / 2, button_y2 + button.getHeight() / 2 + strhei / 2, paint);
            paint.setTextSize(60);
            float textlong = paint.measureText("总分:" + String.valueOf(score));
            canvas.drawText( "战绩", screen_width / 2 - textlong / 2 + 50, screen_height / 2 - 400, paint);
            canvas.drawText( "总分:" + String.valueOf(score), screen_width / 2 - textlong / 2, screen_height / 2 - 300, paint);
            canvas.drawText( "小型机:" + String.valueOf(MyPlane.getsmallAmount()), screen_width / 2 - textlong / 2 , screen_height / 2 - 200, paint);
            canvas.drawText( "中型机:" + String.valueOf(MyPlane.getmiddleAmount()), screen_width / 2 - textlong / 2 , screen_height / 2 - 100, paint);
            canvas.drawText( "大型机:" +String.valueOf( MyPlane.getbigAmount()), screen_width / 2 - textlong / 2 , screen_height / 2 , paint);
            canvas.drawText( "Boss:" +String.valueOf( MyPlane.getbossAmount()), screen_width / 2 - textlong / 2 , screen_height / 2 + 100, paint);
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            drawSelf();
            long endTime = System.currentTimeMillis();
            try {
                if (endTime - startTime < 400)
                    Thread.sleep(400 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
    }

    public void setScore(int score) {
        this.score = score;
    }
}
