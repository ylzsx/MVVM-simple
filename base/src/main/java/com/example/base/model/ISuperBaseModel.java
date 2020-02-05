package com.example.base.model;

import com.example.base.network.NetWorkStatus;

import androidx.lifecycle.MutableLiveData;

/**
 * Time:2020/1/31 9:59
 * Author: han1254
 * Email: 1254763408@qq.com
 * Function:
 */

public interface ISuperBaseModel {

    MutableLiveData<NetWorkStatus> getNetworkStatus();

    void setNetworkStatus(NetWorkStatus netWorkStatus);
}
