package com.example.login;

import android.util.Log;

import com.example.base.nettype.NetworkManager;
import com.example.base.nettype.annotation.Network;
import com.example.base.nettype.type.NetType;
import com.example.base.view.activity.MvvmNetworkActivity;
import com.example.base.model.bean.BaseNetworkStatus;
import com.example.base.utils.ToastUtil;
import com.example.fw_annotations.BindPath;
import com.example.login.databinding.ActivityLoginBinding;

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

        mViewDataBinding.btnLoad.setOnClickListener(v  -> mViewModel.login());
//        mViewModel.login();
    }

    @Override
    public void onNetDone(String key, BaseNetworkStatus status) {
        if (LoginModel.tagName.equals(key)) {
            ToastUtil.show(this, "登录成功");
        }
    }

    @Override
    public void onNoNetwork(String key, BaseNetworkStatus status) {
        if (LoginModel.tagName.equals(key)) {
            ToastUtil.show(this, "没有网络怎么请求？");
        }
    }
}
