package com.example.base.model;

import com.example.base.network.NetWorkStatus;

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
                    mData.setData(data);
                    BaseNetworkStatus status = mNetworkStatus.getValue();
                    if (status == null) {
                        status = new BaseNetworkStatus();
                    }
                    status.setStatus(NetWorkStatus.DONE);
                    mNetworkStatus.postValue(status);
                    // TODO: 缓存room
                    if (isSaveToDataBase()) {
                        saveData(data);
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

    @Override
    public boolean isLoadFromMemory() {
        return false;
    }


    @Override
    public boolean isLoadFromDataBase() {
        return false;
    }

    @Override
    public boolean isFetchRemote() {
        return true;
    }

    @Override
    public boolean isSaveToDataBase() {
        return true;
    }

    @Override
    public boolean isSaveToMemory() {
        return false;
    }

    @Override
    public void saveDataToMemory(T data) {

    }

    @Override
    public void saveDataToDataBase(BaseCachedData<T> data) {

    }

    @Override
    public void getSpData(String key) {

    }

    @Override
    protected T getDataBaseData() {
        return null;
    }

    @Override
    protected T getMemoryData() {
        return null;
    }
}
