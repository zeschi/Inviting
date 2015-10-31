package net.keyvalue.inviting.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import net.keyvalue.inviting.R;
import net.keyvalue.inviting.biz.LoginBiz;
import net.keyvalue.inviting.constant.UrlConstant;
import net.keyvalue.inviting.model.User;
import net.keyvalue.inviting.util.LogUtil;
import net.keyvalue.inviting.util.ViewClickUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sum on 15-9-17.
 */
public class LoginActivity extends BaseActivity implements LoginBiz.OnLoginListener {

    @Bind(R.id.accountInputLayout)
    TextInputLayout accountInputLayout;
    @Bind(R.id.passwordInputLayout)
    TextInputLayout passwordInputLayout;
    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.cb_is_save_user)
    CheckBox cbIsSaveUser;
    @Bind(R.id.tv_retrieve_password)
    TextView tvRetrievePassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_sign_in)
    Button btnSignIn;

    private static LoginBiz loginBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setBackgroundDrawable(null);
        ButterKnife.bind(this);

        ViewClickUtil.setClickState(btnLogin, R.color.normal_red, R.color.pressed_red);
        ViewClickUtil.setClickState(btnSignIn, R.color.normal_grey, R.color.pressed_grey);
        ViewClickUtil.setClickState(tvRetrievePassword, 0, R.color.pressed_light_grey);

        if (null == loginBiz) {
            loginBiz = new LoginBiz();
        }
        loginBiz.attachView(this);
        loginBiz.setOnLoginListener(this);

        boolean isRememberUser = loginBiz.isRememberUser();
        cbIsSaveUser.setChecked(isRememberUser);
        if (isRememberUser) {
            User user = loginBiz.getRememberUser();
            if (null != user) {
                etAccount.setText(user.getAccount());
                etPassword.setText(user.getPassword());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginBiz.detachView();
        if (isFinishing()) {
            loginBiz = null;
        }
    }

    @OnClick(R.id.btn_login)
    public void login() {
        if (ViewClickUtil.isFastMultipleClick()) {
            return;
        }

        String account = etAccount.getText().toString();
        if (!checkAccount(account)) {
            return;
        }
        String password = etPassword.getText().toString();
        if (!checkPassword(password)) {
            return;
        }

        User user = new User(account, password);
        loginBiz.login(UrlConstant.LOGIN, user);
    }

    private boolean checkAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            accountInputLayout.setErrorEnabled(true);
            accountInputLayout.setError("帐号不能为空");
            return false;
        }
        int MIN_ACCOUNT_LENGTH = 6;
        int MAX_ACCOUNT_LENGTH = 16;
        if (account.length() < MIN_ACCOUNT_LENGTH) {
            accountInputLayout.setErrorEnabled(true);
            accountInputLayout.setError("帐号长度不能小于" + MIN_ACCOUNT_LENGTH);
            return false;
        } else if (account.length() > MAX_ACCOUNT_LENGTH) {
            accountInputLayout.setErrorEnabled(true);
            accountInputLayout.setError("帐号长度不能超过" + MAX_ACCOUNT_LENGTH);
            return false;
        }
        accountInputLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError("密码不能为空");
            return false;
        }
        int MIN_PASSWORD_LENGTH = 6;
        int MAX_PASSWORD_LENGTH = 16;
        if (password.length() < MIN_PASSWORD_LENGTH) {
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError("密码长度不能小于" + MIN_PASSWORD_LENGTH);
            return false;
        } else if (password.length() > MAX_PASSWORD_LENGTH) {
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError("密码长度不能超过" + MAX_PASSWORD_LENGTH);
            return false;
        }
        passwordInputLayout.setErrorEnabled(false);
        return true;
    }

    @Override
    public void onLoginSuccess() {
        LogUtil.e("onLoginSuccess");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure() {
        LogUtil.e("onLoginFailure");
    }

    @OnClick(R.id.btn_sign_in)
    public void signIn() {
    }

    @OnClick(R.id.cb_is_save_user)
    public void saveUser() {
        loginBiz.setIsRememberUser(cbIsSaveUser.isChecked());
    }

    @OnClick(R.id.tv_retrieve_password)
    public void retrievePassword() {

    }
}
