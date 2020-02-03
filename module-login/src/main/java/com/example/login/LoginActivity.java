package com.example.login;

import com.example.base.activity.MvvmActivity;
import com.example.fw_annotations.BindPath;
import com.example.login.databinding.ActivityLoginBinding;

@BindPath("login/LoginActivity")
public class LoginActivity extends MvvmActivity<ActivityLoginBinding, LoginViewModel> implements LoginViewModel.ILoginVIew {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        return new LoginViewModel();
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void initParameters() {

    }

    @Override
    protected void initDataAndView() {
        // 点击登录
        mViewModel.login();
    }
}
