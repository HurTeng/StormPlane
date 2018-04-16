package com.hurteng.stormplane;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hurteng.stormplane.constant.ConstantUtil;
import com.hurteng.stormplane.constant.DebugConstant;
import com.hurteng.stormplane.sounds.GameSoundPool;
import com.hurteng.stormplane.view.EndView;
import com.hurteng.stormplane.view.MainView;
import com.hurteng.stormplane.view.ReadyView;

public class MainActivity extends Activity {
    private EndView endView;
    private MainView mainView;
    private ReadyView readyView;
    private GameSoundPool sounds;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ConstantUtil.TO_MAIN_VIEW) {
                toMainView();
            } else if (msg.what == ConstantUtil.TO_END_VIEW) {
                toEndView(msg.arg1);
            } else if (msg.what == ConstantUtil.END_GAME) {
                endGame();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sounds = new GameSoundPool(this);
        sounds.initGameSound();

        readyView = new ReadyView(this, sounds);
        setContentView(readyView);

    }

    /**
     * 进入游戏界面
     */
    public void toMainView() {
        if (mainView == null) {
            mainView = new MainView(this, sounds);
        }
        setContentView(mainView);
        readyView = null;
        endView = null;
    }

    /**
     * 进入结束分数统计界面
     *
     * @param score
     */
    public void toEndView(int score) {
        if (endView == null) {
            endView = new EndView(this, sounds);
            endView.setScore(score);
        }
        setContentView(endView);
        mainView = null;
    }

    /**
     * 结束游戏
     */
    public void endGame() {
        if (readyView != null) {
            readyView.setThreadFlag(false);
        } else if (mainView != null) {
            mainView.setThreadFlag(false);
        } else if (endView != null) {
            endView.setThreadFlag(false);
        }
        this.finish();
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 双击退出函数
     */
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (DebugConstant.DOUBLECLICK_EXIT) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (System.currentTimeMillis() - firstTime > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
