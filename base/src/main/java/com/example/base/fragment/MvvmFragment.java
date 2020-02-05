package com.example.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.R;
import com.example.base.loadSir.EmptyCallback;
import com.example.base.loadSir.ErrorCallback;
import com.example.base.loadSir.LoadingCallback;
import com.example.base.utils.ToastUtil;
import com.example.base.viewmodel.IMvvmNetworkViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * @author YangZhaoxin.
 * @since 2020/1/26 16:16.
 * email yangzhaoxin@hrsoft.net.
 */

public abstract class MvvmFragment<V extends ViewDataBinding, VM extends IMvvmNetworkViewModel> extends Fragment
        implements IBasePagingView {

    protected VM mViewModel;
    protected V mViewDataBinding;
    private LoadService mLoadService;

    // 方便打印日志信息
    protected String mFragmentTag = "";

    public abstract @LayoutRes
    int getLayoutId();
    public abstract VM getViewModel();
    public abstract int getBindingVariable();

    /**
     * 设置Fragment标签
     */
    protected abstract String getFragmentTag();

    /**
     * 初始化参数,通常用来得到从之前View传来的Bundle等数据
     */
    protected abstract void initParameters();

    protected abstract void initDataAndView();

    /**
     * 网络失败重试方法
     */
    protected abstract void onRetryBtnClick();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mViewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel();
        if (mViewModel != null) {
            mViewModel.attachUI(this);
        }
        if (getBindingVariable() > 0) {
            mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        }
        mViewDataBinding.executePendingBindings();
        initDataAndView();
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
    public void onLoadMoreFailure(String message) {
        ToastUtil.show(getContext(), message);
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.show(getContext(), getString(R.string.no_more_data));
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(getFragmentTag(), this + ":" + "onActivityCreated");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(getFragmentTag(), this + ":" + "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mViewModel != null && mViewModel.isUIAttached()) {
            mViewModel.detachUI();
        }
        Log.d(getFragmentTag(), this + ":" + "onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getFragmentTag(), this + ":" + "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getFragmentTag(), this + ":" + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getFragmentTag(), this + ":" + "onResume");
    }

    @Override
    public void onDestroy() {
        Log.d(getFragmentTag(), this + ":" + "onDestroy");
        super.onDestroy();
    }
}
