package com.example.login;

import com.example.base.BaseApplication;
import com.example.base.nettype.netchange.NetChangeWatcherUtil;

/**
 * Time:2020/2/7 11:34
 * Author: han1254
 * Email: 1254763408@qq.com
 * Function:
 */
public class LoginApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
//        NetworkManager.getInstance().init(this);
//        NetTypeUtil.register(this);
        NetChangeWatcherUtil.getInstance().init(this);
    }
}
