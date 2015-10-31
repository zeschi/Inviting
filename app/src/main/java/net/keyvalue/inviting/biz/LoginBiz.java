package net.keyvalue.inviting.biz;

import android.app.Activity;
import android.support.annotation.Nullable;

import net.keyvalue.inviting.model.User;
import net.keyvalue.inviting.util.SharedPreferencesUtil;

/**
 * Created by sum on 15-10-31.
 */
public class LoginBiz {

    private final String IS_REMEMBER_USER = "isRememberUser";
    private Activity activity;
    private OnLoginListener onLoginListener;

    public void login(String url, User user) {
        if (isRememberUser()) {
            /**
             * TODO：保存用户
             */
        }
        /**
         * TODO:实现登录逻辑，并回调登录结果
         */
        if (null != onLoginListener) {
            onLoginListener.onLoginSuccess();
        }
    }

    public void attachView(Activity activity) {
        this.activity = activity;
    }

    public void detachView() {
        this.activity = null;
    }

    public boolean isRememberUser() {
        if (null == activity) {
            throw new NullPointerException("Must invoke attachView() first.");
        }
        return SharedPreferencesUtil.getBoolean(activity, IS_REMEMBER_USER, true);
    }

    public void setIsRememberUser(boolean isRemember) {
        if (null == activity) {
            throw new NullPointerException("Must invoke attachView() first.");
        }
        SharedPreferencesUtil.putBoolean(activity, IS_REMEMBER_USER, isRemember);
    }

    @Nullable
    public User getRememberUser() {
        /**
         * TODO: 获取保存的User
         */
        return new User("test_account", "test_password");
    }

    public interface OnLoginListener {
        void onLoginSuccess();

        void onLoginFailure();
    }

    public void setOnLoginListener(OnLoginListener listener) {
        this.onLoginListener = listener;
    }
}
