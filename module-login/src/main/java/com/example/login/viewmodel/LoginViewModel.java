package com.example.login.viewmodel;

import com.example.base.viewmodel.MvvmNetworkViewModel;
import com.example.login.model.LoginModel;
import com.example.login.model.RegisterModel;

import androidx.lifecycle.MutableLiveData;

/**
 * @author YangZhaoxin.
 * @since 2020/2/1 13:33.
 * email yangzhaoxin@hrsoft.net.
 */

public class LoginViewModel extends MvvmNetworkViewModel {

    @Override
    protected void initModels() {
        registerModel(LoginModel.tagName, new LoginModel());
        registerModel(RegisterModel.tagName, new RegisterModel());
    }

    public void login() {
        getCachedDataAndLoad(LoginModel.tagName);
    }

    public MutableLiveData getLoginLiveData() {
        return getDataLiveData(LoginModel.tagName);
    }

    public void register() {
        getCachedDataAndLoad(RegisterModel.tagName);
    }

    public MutableLiveData getRegisterLiveData() {
        return getDataLiveData(RegisterModel.tagName);
    }

}
