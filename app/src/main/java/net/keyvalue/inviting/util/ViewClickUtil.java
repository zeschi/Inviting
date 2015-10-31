package net.keyvalue.inviting.util;

import android.view.MotionEvent;
import android.view.View;

public class ViewClickUtil {

    private static long lastClickTime = 0;

    private ViewClickUtil() {
        throw new AssertionError();
    }

    /**
     * 判断是否快速多次点击View
     */
    public static boolean isFastMultipleClick() {
        long currentTime = System.currentTimeMillis();
        long timeD = currentTime - lastClickTime;
        if (timeD < 300) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }

    /**
     * 设置点击时View正反选效果
     */
    public static void setClickState(View view, final int normalResId, final int pressedResId) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundResource(pressedResId);
                    }
                    break;
                    case MotionEvent.ACTION_MOVE: {
                        v.setBackgroundResource(pressedResId);
                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        v.setBackgroundResource(normalResId);
                    }
                    break;
                }
                // 为了不影响监听按钮的onClick回调，返回值应为false
                return false;
            }
        });
    }
}