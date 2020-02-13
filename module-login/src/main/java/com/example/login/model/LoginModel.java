package com.example.login.model;

import com.example.base.model.BaseModel;
import com.example.login.api.LoginRepository;
import com.example.network.exception.ApiException;
import com.example.network.observer.BaseObserver;

/**
 * @author YangZhaoxin.
 * @since 2020/2/1 13:42.
 * email yangzhaoxin@hrsoft.net.
 */

public class LoginModel extends BaseModel<String> {

    public static final String tagName = "LoginModel";

    @Override
    public void refresh() {

    }

    @Override
    protected void load() {
        // 网络请求
        LoginRepository.getInstance().getSongPoetry(1, 20, new BaseObserver<String>(this) {
            @Override
            public void onError(ApiException e) {
                loadFail(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                loadSuccess(s);
            }
        });


    }
}
