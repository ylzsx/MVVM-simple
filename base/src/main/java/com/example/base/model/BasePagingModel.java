package com.example.base.model;

import java.lang.ref.WeakReference;

/**
 * 分页数据
 * @author YangZhaoxin.
 * @since 2020/1/27 18:18.
 * email yangzhaoxin@hrsoft.net.
 */

public abstract class BasePagingModel<T> extends SuperBaseModel<T> {
    protected boolean isRefresh = true;
    protected int pageNumber = 0;

    // ViewModel可能接收到多个数据
    public interface IModelListener<T> extends IBaseModeListener {
        void onLoadFinish(BasePagingModel model, T data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage);
        void onLoadFail(BasePagingModel model, String errorMessage, boolean isFirstPage);
    }

    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data, false, true, true);
    }

    protected void loadSuccess(T data, final boolean isEmpty, final boolean isFirstPage, final boolean hasNextPage) {
        synchronized (this) {
            mUIHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (WeakReference<IBaseModeListener> weakListener : mWeakListenerArrayList) {
                        if (weakListener.get() instanceof IModelListener) {
                            IModelListener listener = (IModelListener) weakListener.get();
                            if (listener != null) {
                                listener.onLoadFinish(BasePagingModel.this, data, isEmpty, isFirstPage, hasNextPage);
                            }
                        }
                    }
                    // 如果需要缓存数据，加载成功后保存
                    // TODO: 缓存room
                    if (getCachedPreferenceKey() != null && isFirstPage) {
                        saveDataToPreference(data);
                    }
                }
            }, 0);
        }
    }

    protected void loadFail(final String errorMessage, final boolean isFirstPage) {
        synchronized (this) {
            for (WeakReference<IBaseModeListener> weakListener : mWeakListenerArrayList) {
                if (weakListener.get() instanceof IModelListener) {
                    IModelListener listener = (IModelListener) weakListener.get();
                    if (listener != null) {
                        listener.onLoadFail(BasePagingModel.this, errorMessage, isFirstPage);
                    }
                }
            }
        }
    }
}
