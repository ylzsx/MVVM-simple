package com.example.login;

import android.view.View;

import com.example.base.activity.IBaseView;
import com.example.base.model.BaseModel;
import com.example.base.viewmodel.MvvmBaseViewModel;

/**
 * @author YangZhaoxin.
 * @since 2020/2/1 13:33.
 * email yangzhaoxin@hrsoft.net.
 */

public class LoginViewModel extends MvvmBaseViewModel<View> {

    @Override
    protected void initModels() {
        LoginModel loginModel = new LoginModel();
        loginModel.register(new BaseModel.IModelListener<String>() {
            @Override
            public void onLoadFinish(BaseModel model, String data) {
                // TODO：通过livedata操作界面数据，太菜还未封装
            }

            @Override
            public void onLoadFail(BaseModel model, String errorMessage) {

            }
        });
        registerModel(LoginModel.tagName, loginModel);


        RegisterModel registerModel = new RegisterModel();
        registerModel.register(new BaseModel.IModelListener<Integer>() {
            @Override
            public void onLoadFinish(BaseModel model, Integer data) {

            }

            @Override
            public void onLoadFail(BaseModel model, String errorMessage) {

            }
        });
        registerModel(RegisterModel.tagName, registerModel);
    }


    public void login() {
        getCachedDataAndLoad(LoginModel.tagName);
    }

    public void register() {
        getCachedDataAndLoad(RegisterModel.tagName);
    }

    interface ILoginVIew extends IBaseView {

    }
}
