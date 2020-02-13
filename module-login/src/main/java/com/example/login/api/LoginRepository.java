package com.example.login.api;

import com.example.network.HttpClient;

import io.reactivex.Observer;


/**
 * @author YangZhaoxin.
 * @since 2020/2/13 18:04.
 * email yangzhaoxin@hrsoft.net.
 * Function
 */

public class LoginRepository {

    private LoginApi mApi;

    private LoginRepository(LoginApi api) {
        mApi = api;
    }

    private static class InstanceHolder {
        private static final LoginRepository INSTANCE = new LoginRepository(
                HttpClient.getInstance().createService(LoginApi.class)
        );
    }

    public static LoginRepository getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void getSongPoetry(int page, int count, Observer<String> observer) {
        HttpClient.getInstance().apiSubscribe(mApi.getSongPoetry(page, count), observer);
    }


}
