package com.example.network;

import java.util.HashMap;

/**
 * @author YangZhaoxin.
 * @since 2020/2/7 19:18.
 * email yangzhaoxin@hrsoft.net.
 * Function
 */

public interface INetworkRequestInfo {

    HashMap<String, String> getRequestHeadersMap();
    boolean isDebug();
}
