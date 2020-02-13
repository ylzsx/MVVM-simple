package com.example.login;

import com.example.network.INetworkRequestInfo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author YangZhaoxin.
 * @since 2020/2/13 17:38.
 * email yangzhaoxin@hrsoft.net.
 * Function
 */

public class NetworkRequestInfo implements INetworkRequestInfo {

    @Override
    public HashMap<String, String> getRequestHeadersMap() {
        return null;
    }

    @Override
    public boolean isDebug() {
        return true;
    }

    @Override
    public String getBaseUrl() {
        return "https://api.apiopen.top/";
    }

    @Override
    public ArrayList<Integer> getNetCorrectCode() {
        ArrayList<Integer> codes = new ArrayList<>();
        codes.add(0);
        codes.add(200);
        return codes;
    }
}
