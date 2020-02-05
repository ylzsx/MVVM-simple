package com.example.mvvm_simple;

import com.example.base.activity.MvvmNetworkActivity;
import com.example.base.viewmodel.MvvmNetworkViewModel;
import com.example.mvvm_simple.databinding.ActivityMainBinding;

public class MainActivity extends MvvmNetworkActivity<ActivityMainBinding, MvvmNetworkViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MvvmNetworkViewModel getViewModel() {
        return null;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected void initParameters() {

    }

    @Override
    protected void initDataAndView() {

    }
}
