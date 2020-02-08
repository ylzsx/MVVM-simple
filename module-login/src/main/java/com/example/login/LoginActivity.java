package com.example.login;
import com.example.base.nettype.type.NetworkDetailType;
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

    @Override
    protected void onNetWorkChange(NetworkDetailType networkDetailType) {
       switch (networkDetailType) {
           case NETWORK_NO:
               ToastUtil.show(this, "没网了没网了");
               break;
           case NETWORK_WIFI:
               ToastUtil.show(this, "wifi网络可放心食用");
               break;
           default:
               ToastUtil.show(this, "流量小心");
               break;
       }
    }
}
