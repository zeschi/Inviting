package net.keyvalue.inviting.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import net.keyvalue.inviting.R;

import java.lang.ref.WeakReference;

/**
 * Created by sum on 15-9-15.
 */
public class SplashActivity extends BaseActivity {

    private static final int MIN_SPLASH_TIME_MILLIS = 1000;
    private static final int MSG_SPLASH = 0x100;
    private long startTimeMillis;
    private SplashHandler splashHandler = new SplashHandler(this);
    private boolean isFiretInvokingOnResume = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startTimeMillis = System.currentTimeMillis();
        splashHandler.sendEmptyMessage(MSG_SPLASH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFiretInvokingOnResume) {
            isFiretInvokingOnResume = false;
        } else {
            splashHandler.sendEmptyMessage(MSG_SPLASH);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashHandler.removeCallbacksAndMessages(null);
    }

    private static class SplashHandler extends Handler {
        private WeakReference<Activity> activityReference;

        SplashHandler(Activity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity splashActivity = (SplashActivity) activityReference.get();
            if (null == splashActivity) {
                return;
            }
            if (msg.what == MSG_SPLASH) {
                long passedTimeMillis = System.currentTimeMillis() - splashActivity.startTimeMillis;
                if (passedTimeMillis < MIN_SPLASH_TIME_MILLIS) {
                    long waitTimeMillis = MIN_SPLASH_TIME_MILLIS - passedTimeMillis;
                    sendEmptyMessageDelayed(MSG_SPLASH, waitTimeMillis);
                } else {
                    splashActivity.splash();
                }
            }
        }
    }

    private void splash() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
