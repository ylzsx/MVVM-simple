package com.example.base.model;

/**
 * 不分页数据
 * @author YangZhaoxin.
 * @since 2020/1/27 18:20.
 * email yangzhaoxin@hrsoft.net.
 */

public abstract class BaseModel<T> extends SuperBaseModel<T> {

    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data);
    }

    /**
     * 加载网络数据成功，通知所有订阅者加载结果
     * @param data
     */
    protected void loadSuccess(T data) {
        synchronized (this) {
            mUIHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mModelLiveData.postValue(data);
                    // 如果需要缓存数据，加载成功后保存
                    // TODO: 缓存room
                    if (getCachedPreferenceKey() != null) {
                        saveDataToPreference(data);
                    }
                }
            }, 0);
        }
    }

    /**
     * 加载网络数据失败
     * @param errorMessage
     */
    protected void loadFail(final String errorMessage) {
        synchronized (this) {
            mUIHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // TODO: 错误返回
                }
            }, 0);
        }
    }
}
