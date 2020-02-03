package com.example.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.base.loadSir.EmptyCallback;
import com.example.base.loadSir.ErrorCallback;
import com.example.base.loadSir.LoadingCallback;
import com.example.base.viewmodel.IMvvmBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author YangZhaoxin.
 * @since 2020/1/10 19:23.
 * email yangzhaoxin@hrsoft.net.
 */

public abstract class MvvmActivity<V extends ViewDataBinding, VM extends IMvvmBaseViewModel>
        extends AppCompatActivity implements IBaseView {

    protected VM mViewModel;
    protected V mViewDataBinding;
    private LoadService mLoadService;

    public abstract @LayoutRes
    int getLayoutId();
    public abstract VM getViewModel();
    public abstract int getBindingVariable();

    // 网络失败重试方法
    protected abstract void onRetryBtnClick();

    /**
     * 初始化参数,通常用来得到从之前View传来的Bundle等数据
     */
    protected abstract void initParameters();

    protected abstract void initDataAndView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
        initViewModel();
        performDataBinding();
        initDataAndView();
    }

    /**
     * 绑定ViewModel
     */
    private void initViewModel() {
        mViewModel = getViewModel();
        if (mViewModel != null) {
            mViewModel.attachUI(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (mViewModel != null && mViewModel.isUIAttached()) {
            mViewModel.detachUI();
        }
        super.onDestroy();
    }

    /**
     * 绑定dataBinding
     */
    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());

        // 进行数据绑定
        if (getBindingVariable() > 0) {
            mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        }
        mViewDataBinding.executePendingBindings();
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

    public void startActivity(Class<?> clazz) {
        startActivity(clazz, null, null);
    }

    public void startActivity(Class<?> clazz,String key, Bundle bundle) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            if (bundle != null && key != null && !key.isEmpty()) {
                intent.putExtra(key, bundle);
            }
            startActivity(intent);
        } else {
            throw new NullPointerException();
        }
    }
}
