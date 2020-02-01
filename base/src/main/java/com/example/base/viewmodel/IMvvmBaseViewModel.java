package com.example.base.viewmodel;

/**
 * @author YangZhaoxin.
 * @since 2020/1/26 16:38.
 * email yangzhaoxin@hrsoft.net.
 */

public interface IMvvmBaseViewModel<V> {

    /**
     * 绑定UI
     * @param view
     */
    void attachUI(V view);

    V getPageView();

    boolean isUIAttached();

    void detachUI();
}
