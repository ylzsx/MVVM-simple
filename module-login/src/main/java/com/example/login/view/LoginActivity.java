package com.example.login.view;

import android.util.Log;

import com.example.base.model.bean.BaseNetworkStatus;
import com.example.base.network.NetType;
import com.example.base.utils.ToastUtil;
import com.example.base.view.activity.MvvmNetworkActivity;
import com.example.fw_annotations.BindPath;
import com.example.login.viewmodel.LoginViewModel;
import com.example.login.R;
import com.example.login.databinding.ActivityLoginBinding;
import com.example.login.model.LoginModel;

import androidx.lifecycle.ViewModel;

@BindPath("login/LoginActivity")
public class LoginActivity extends MvvmNetworkActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public Class<? extends ViewModel> getViewModel() {
        return LoginViewModel.class;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }


    @Override
    protected void initParameters() {

    }

    @Override
    protected void initDataAndView() {
        // 点击登录
        mViewModel.login();
    }

    @Override
    public void onNetDone(String key, BaseNetworkStatus status) {
        if (LoginModel.tagName.equals(key)) {
            ToastUtil.show(this, "登录成功");
        }
    }

    @Override
    protected void onNetTypeChange(NetType netType) {
        switch (netType) {
            case NONE:
                Log.e("LoginActivity", "断网了");
                break;
            case WIFI:
                Log.e("LoginActivity", "wifi");
                break;
            case CMWAP:
                Log.e("LoginActivity", "流量");
                break;
            case CMNET:
                break;
            default:
                break;
        }
    }
}
