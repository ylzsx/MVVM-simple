package com.example.base.model;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Type;

import androidx.annotation.CallSuper;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * model层超类
 * @author YangZhaoxin.
 * @since 2020/1/26 17:27.
 * email yangzhaoxin@hrsoft.net.
 */

public abstract class SuperBaseModel<T> implements ISuperBaseModel {

    protected Handler mUIHandler = new Handler(Looper.getMainLooper());
    // 管理所有订阅
    private CompositeDisposable mCompositeDisposable;
    private BaseCachedData<T> mData;
    // TODO: 是否需要都用防倒灌的livedata
    protected MutableLiveData<T> mModelLiveData;
    protected MutableLiveData<BaseNetworkStatus> mNetworkStatus;

    public SuperBaseModel() {
        // TODO：是否可以自动释放liveData
        mModelLiveData = new MutableLiveData<>();
        mNetworkStatus = new MutableLiveData<>();
        mNetworkStatus.setValue(new BaseNetworkStatus());
        if (getCachedPreferenceKey() != null) {
            mData = new BaseCachedData<T>();
        }
        // TODO:添加room缓存
    }

    @Override
    public MutableLiveData<BaseNetworkStatus> getNetworkStatus() {
        return mNetworkStatus;
    }

    @Override
    public void setNetworkStatus(BaseNetworkStatus netWorkStatus) {
        mNetworkStatus.postValue(netWorkStatus);
    }

    /**
     * 数据刷新
     */
    public abstract void refresh();

    /**
     * 数据加载
     */
    protected abstract void load();

    protected abstract void notifyCachedData(T data);

    /**
     * 该model的数据是否需要sp缓存，如需要则重写该方法，返回缓存的key
     * @return
     */
    protected String getCachedPreferenceKey() {
        return null;
    }

    /**
     * 缓存数据的类型，当需要缓存数据时，重写该方法
     * @return
     */
    protected Type getTClass() {
        return null;
    }

    /**
     * 是否需要更新数据，可以在这个方法中定义更新策略
     * 默认每次请求都更新数据
     * @return
     */
    protected boolean isNeedToUpdate() {
        return true;
    }

    /**
     * 当该model需要apk级缓存时，重写该方法。默认不需要
     * @return
     */
    protected String getApkString() {
        return null;
    }

    public MutableLiveData<T> getModelLiveData() {
        return mModelLiveData;
    }

    /**
     * 当app在打开时由于网络慢或者异常情况下，设置sp级缓存，或者apk级缓存
     * @param data
     */
    protected void saveDataToPreference(T data) {
        mData.setData(data);
        mData.setUpdateTimeInMills(System.currentTimeMillis());
        // TODO：保存数据
    }


    /**
     * 保存数据，防止内存泄露
     * 子类在重写该方法时，必须调用super()
     */
    @CallSuper
    public void cancel() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 添加订阅者
     * @param disposable
     */
    public void addDisposable(Disposable disposable) {
        if (disposable == null) {
            return;
        }

        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }

        mCompositeDisposable.add(disposable);
    }

    public void getCachedDataAndLoad() {
        if (getCachedPreferenceKey() != null) { // 有sp缓存数据
            // TODO: 加入缓存 postValue
        }
        load();
    }
}
