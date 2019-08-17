package cn.com.cunw.diandubiapp.uis.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.cunw.diandubiapp.R;
import cn.com.cunw.diandubiapp.base.BaseActivity;
import cn.com.cunw.diandubiapp.base.popup.BasePopupWindow;
import cn.com.cunw.diandubiapp.uis.localsource.LocalSourceActivity;

/**
 * @author YangBin
 * @time 2019/8/9 8:43
 * @des 主页，tianlong完成 TODO
 * @copyright 湖南新云网科技有限公司
 */
public class MainActivity extends BaseActivity implements View.OnTouchListener {
    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    private static MainActivity sActivity;

    public static void startActivity(Context context) {
        if (sActivity != null)
            return;
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private ImageView mIvRecitation;

    @Override
    public void initViews() {
        super.initViews();
        sActivity = this;
        mIvRecitation = findViewById(R.id.iv_recitation);
        mIvRecitation.setOnTouchListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sActivity = null;
    }

    public void joinLoaclActivity(View view) {
        LocalSourceActivity.startActivity(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mTouching) {
                    startVoice();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mTouching) {
                    stopVoice();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouching) {
                    if (motionEvent.getY() < 0) {
                        mTvVoice.setTextColor(Color.RED);
                        mIsCancel = true;
                    } else {
                        mTvVoice.setTextColor(Color.WHITE);
                        mIsCancel = false;
                    }
                }
                break;
        }
        return true;
    }

    private BasePopupWindow mVoicePopupWindow;
    private ImageView mIvVoice;
    private TextView mTvVoice;
    private RelativeLayout mRlWindowContent;
    private TextView mTvChatVoiceCountDown;
    private AnimationDrawable frameAnimation;
    private long mLastTime;
    private boolean mIsCancel;// 是否取消
    private boolean mTouching;// 正在按住按钮

    private VoiceCountDown mCountDown = new VoiceCountDown();

    private void showVoiceWindow() {
        mLastTime = System.currentTimeMillis();
        mCountDown.init();
        mIvRecitation.postDelayed(mCountDown, (VOICE_MAX_LENGTH - VOICE_DJS_LENGTH) * 1000);
        if (mVoicePopupWindow == null) {
            mVoicePopupWindow = new BasePopupWindow.Builder(this)
                    .setContentViewId(R.layout.popup_chat_voice)
                    .setBackgroundDim(false)
                    .build();
            mIvVoice = mVoicePopupWindow.getView(R.id.iv_voice);
            mTvVoice = mVoicePopupWindow.getView(R.id.tv_voice);
            mRlWindowContent = mVoicePopupWindow.getView(R.id.rl_window_content);
            mTvChatVoiceCountDown = mVoicePopupWindow.getView(R.id.tv_chat_voice_count_down);
            mIvVoice.setBackgroundResource(R.drawable.animation_voice);
            frameAnimation = (AnimationDrawable) mIvVoice.getBackground();
        }
        mTvVoice.setTextColor(Color.WHITE);
        mTvChatVoiceCountDown.setVisibility(View.GONE);
        frameAnimation.start();
        mVoicePopupWindow.showAtLocation(mIvRecitation, Gravity.CENTER, 0, 0);
    }

    private void dismissVoiceWindow() {
        mIvRecitation.removeCallbacks(mCountDown);
        if (mVoicePopupWindow != null && mVoicePopupWindow.isShowing()) {
            mVoicePopupWindow.dismiss();
        }
    }

    /**
     * 倒计时任务
     */
    class VoiceCountDown implements Runnable {

        private int second;

        public void init() {
            second = VOICE_DJS_LENGTH;
        }

        @Override
        public void run() {
            mTvChatVoiceCountDown.setText((--second) + "秒");
            mTvChatVoiceCountDown.setVisibility(View.VISIBLE);
            if (second < 0) {
                stopVoice();
            } else {
                mIvRecitation.postDelayed(this, 1000);
            }
        }
    }

    private static final int VOICE_MAX_LENGTH = 10;// 语音能够录制的时间长度限制,单位 s
    private static final int VOICE_DJS_LENGTH = 3;// 倒计时
    private static final int VOICE_MIN_LENGTH = 1;// 最少录音时间

    /**
     * 开始录音
     */
    private void startVoice() {
        mTouching = true;
        showVoiceWindow();
        Log.e("aaron", "----  录音开始！");
    }

    /**
     * 结束录音
     */
    private void stopVoice() {
        mTouching = false;
        dismissVoiceWindow();
        Log.e("aaron", "----  录音结束！");

        if (mIsCancel) {
            Log.e("aaron", "您已经取消！");
            return;
        }

        long currTime = System.currentTimeMillis();
        long temp = currTime - mLastTime;
        if (currTime - mLastTime < 1000 * VOICE_MIN_LENGTH) {
            Log.e("aaron", "录音时间少于1秒！");
            return;
        }
        // 可以在此处上传或者保存录音
        Log.e("aaron", "您录音的时间为：" + (int) (temp / 1000) + "秒");
    }
}
