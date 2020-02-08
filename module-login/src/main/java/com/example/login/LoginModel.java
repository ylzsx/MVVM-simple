package com.example.login;

import com.example.base.model.BaseModel;
import com.example.base.model.bean.BaseNetworkStatus;
import com.example.base.nettype.type.NetType;


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
        String str = "aaa";
        loadSuccess(str);

    }



    public void test() {

    }

}
