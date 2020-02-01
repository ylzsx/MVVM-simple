package com.example.base;

import android.app.Application;

/**
 * @author YangZhaoxin.
 * @since 2020/1/10 12:20.
 * email yangzhaoxin@hrsoft.net.
 */

public class BaseApplication extends Application {

    public static Application sApplication;

    public static boolean sDebug;

    public void setDebug(boolean isDebug) {
        sDebug = isDebug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
