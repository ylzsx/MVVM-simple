package com.example.login;

import com.example.base.BaseApplication;
import com.example.network.HttpClient;

/**
 * @author YangZhaoxin.
 * @since 2020/2/12 20:59.
 * email yangzhaoxin@hrsoft.net.
 * Function
 */

public class LoginApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpClient.getInstance().init(new NetworkRequestInfo());
    }
}
