package com.example.base.viewmodel;

import com.example.base.model.SuperBaseModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.lifecycle.ViewModel;

/**
 * @author YangZhaoxin.
 * @since 2020/1/10 19:28.
 * email yangzhaoxin@hrsoft.net.
 */

public abstract class MvvmBaseViewModel<V> extends ViewModel implements IMvvmBaseViewModel<V> {

    private Reference<V> mUIRef;
    protected Map<String, SuperBaseModel> mModelMap;

    /**
     * 初始化model
     */
    protected abstract void initModels();

    public MvvmBaseViewModel() {
        mModelMap = new HashMap<>();
        initModels();
    }

    @Override
    public void attachUI(V view) {
        mUIRef = new WeakReference<>(view);
    }

    @Override
    public V getPageView() {
        if (mUIRef == null) {
            return null;
        }
        return mUIRef.get();
    }

    @Override
    public boolean isUIAttached() {
        return mUIRef != null && mUIRef.get() != null;
    }

    @Override
    public void detachUI() {
        if (mUIRef != null) {
            mUIRef.clear();
            mUIRef = null;
        }
        unBindModel();
    }

    private void unBindModel() {
        if (mModelMap != null) {
            Iterator<String> iterator = mModelMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                mModelMap.get(key).cancel();
            }
            mModelMap.clear();
        }
        mModelMap = null;
    }

    protected void registerModel(String key, SuperBaseModel model) {
        if (mModelMap == null) {
            mModelMap = new HashMap<>();
        }
        mModelMap.put(key, model);
    }

    /**
     * 根据map的 key 调用model的加载方法
     * @param key
     */
    protected void getCachedDataAndLoad(String key) {
        if (mModelMap == null) {
            return;
        }
        SuperBaseModel model;
        if ((model = mModelMap.get(key)) != null) {
            model.getCachedDataAndLoad();
        } else {
            throw new IllegalArgumentException("无该key: " + key + "对应的model");
        }
    }
}