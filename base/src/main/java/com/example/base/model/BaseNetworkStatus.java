package com.example.base.model;

import com.example.base.network.NetWorkStatus;

/**
 * @author YangZhaoxin.
 * @since 2020/2/5 11:47.
 * email yangzhaoxin@hrsoft.net.
 */

public class BaseNetworkStatus {

    NetWorkStatus status;
    String message;

    public BaseNetworkStatus() {
        status = NetWorkStatus.INIT;
    }

    public BaseNetworkStatus(NetWorkStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public NetWorkStatus getStatus() {
        return status;
    }

    public void setStatus(NetWorkStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
