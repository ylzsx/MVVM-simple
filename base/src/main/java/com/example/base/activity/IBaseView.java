package com.example.base.activity;

/**
 * @author YangZhaoxin.
 * @since 2020/1/11 11:27.
 * email yangzhaoxin@hrsoft.net.
 */

public interface IBaseView {

    void onRefreshEmpty(String message);

    void onRefreshFailure(String message);

    void showLoading();

    void showContent();
}
