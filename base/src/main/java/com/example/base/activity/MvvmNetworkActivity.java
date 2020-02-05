package com.example.base.activity;

import android.os.Bundle;
import android.view.View;

import com.example.base.loadSir.EmptyCallback;
import com.example.base.loadSir.ErrorCallback;
import com.example.base.loadSir.LoadingCallback;
import com.example.base.viewmodel.IMvvmNetworkViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

/**
 * @author YangZhaoxin.
 * @since 2020/1/10 19:23.
 * email yangzhaoxin@hrsoft.net.
 */

public abstract class MvvmNetworkActivity<V extends ViewDataBinding, VM extends IMvvmNetworkViewModel>
        extends MvvmBaseActivity<V,VM> implements IBaseView {

    private LoadService mLoadService;

    // 网络失败重试方法
    protected abstract void onRetryBtnClick();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observeNetWorkStatus();
    }

    /**
     * 检测网络状态方法
     */
    private void observeNetWorkStatus() {

    }

    @Override
    protected void onDestroy() {
        mViewModel.detachModel();
        super.onDestroy();
    }

    /**
     * 网络失败时展示页面（空view，错误的view）
     * @param view  网络失败后要替换的view
     */
    public void setLoadSir(View view) {
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }

    public LoadService getLoadService() {
        return mLoadService;
    }

    @Override
    public void onRefreshEmpty(String message) {
        if (mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if (mLoadService != null) {
            mLoadService.showCallback(ErrorCallback.class);
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void showContent() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }
}
