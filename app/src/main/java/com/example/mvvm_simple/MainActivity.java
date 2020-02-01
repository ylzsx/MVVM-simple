package com.example.mvvm_simple;

import com.example.base.activity.MvvmActivity;
import com.example.base.viewmodel.MvvmBaseViewModel;
import com.example.mvvm_simple.databinding.ActivityMainBinding;

public class MainActivity extends MvvmActivity<ActivityMainBinding, MvvmBaseViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MvvmBaseViewModel getViewModel() {
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
