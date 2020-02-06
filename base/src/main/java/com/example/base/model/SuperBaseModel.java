package com.example.base.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.base.model.bean.BaseCachedData;
import com.example.base.model.bean.BaseNetworkStatus;
import com.example.base.network.NetWorkStatus;

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
    protected BaseCachedData<T> mData;
    // TODO: 是否需要都用防倒灌的livedata
    protected MutableLiveData<T> mModelLiveData;
    protected MutableLiveData<BaseNetworkStatus> mNetworkStatus;

    public SuperBaseModel() {
        // TODO：是否可以自动释放liveData
        mModelLiveData = new MutableLiveData<>();
        mNetworkStatus = new MutableLiveData<>();
        mNetworkStatus.setValue(new BaseNetworkStatus());
        mData = new BaseCachedData<T>();
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
    protected void saveData(T data) {
        mData.setData(data);
        mData.setUpdateTimeInMills(System.currentTimeMillis());
        if (isSaveToMemory()) {
            saveDataToMemory(mData);
        }
        if (getCachedPreferenceKey() != null) {
            saveDataToPreference(mData);
        }
        if (isSaveToDataBase()) {
            saveDataToDataBase(mData);
        }
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
        if (isSaveToMemory()) {
            T memoryData = getMemoryData();
            if (memoryData == null) {
                Log.e("DATA NULL", "App memory doesn't contain the data you want");
            } else {
                mModelLiveData.postValue(memoryData);
                mData.setData(mModelLiveData.getValue());
            }
        }

        if (getCachedPreferenceKey() != null) {
            T spData = getPreferenceData(getCachedPreferenceKey());
            if (spData == null) {
                Log.e("DATA NULL", "Local Preference doesn't contain the data you want");
            } else {
                mModelLiveData.postValue(spData);
                mData.setData(mModelLiveData.getValue());
            }
        }

        if (isSaveToDataBase()) {
            T dataBaseData = getDataBaseData();
            if (dataBaseData == null) {
                Log.e("DATA NULL", "Local database doesn't contain the data you want");
            } else {
                mModelLiveData.postValue(dataBaseData);
                mData.setData(mModelLiveData.getValue());
            }
        }

        // 倘若缓存中没有数据同时有网，则必须去网络加载
        if (mModelLiveData.getValue() == null) {
            judgeStatusAndLoad();
            return;
        }

        if (isFetchRemote()) {
            judgeStatusAndLoad();
        }

    }

    // TODO: 流量 wifi通知到view时变 类似于观察者模式
    private void judgeStatusAndLoad() {
//        mNetworkStatus.setValue(getNetStatus());
        if (!(mNetworkStatus.getValue().getStatus() == NetWorkStatus.NO_NETWORK)) {
            load();
        }
    }

    private BaseNetworkStatus getNetStatus() {
        return null;
    }

    /**
     * 是否进行网络请求
     * @return
     */
    protected boolean isFetchRemote() {
        return true;
    }

    /**
     * 数据是否存入数据库
     * @return
     */
    protected boolean isSaveToDataBase() {
        return false;
    }

    /**
     * 是否存入内存
     * @return
     */
    protected boolean isSaveToMemory() {
        return false;
    }

    /**
     * 存入内存
     * @param data
     */
    protected abstract void saveDataToMemory(BaseCachedData<T> data);

    /**
     * 存入 saveDataToPreference
     * @param data
     */
    protected abstract void saveDataToPreference(BaseCachedData<T> data);

    /**
     * 数据存入数据库
     * @param data
     */
    protected abstract void saveDataToDataBase(BaseCachedData<T> data);

    /**
     * 获得SharedPreference里的数据
     * @param key
     */
    protected abstract T getPreferenceData(String key);

    /**
     * 获得数据库里的数据
     * @return
     */
    protected abstract T getDataBaseData();

    protected abstract T getMemoryData();
}
