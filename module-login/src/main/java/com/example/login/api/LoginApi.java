package com.example.login.api;


import com.example.network.response.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author YangZhaoxin.
 * @since 2020/2/13 18:06.
 * email yangzhaoxin@hrsoft.net.
 * Function
 */

public interface LoginApi {

    @GET("getSongPoetry")
    Observable<ApiResponse<String>> getSongPoetry(@Query("page") int page, @Query("count") int count);
}
