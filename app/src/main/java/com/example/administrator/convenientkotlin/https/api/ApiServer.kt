package com.example.administrator.convenientkotlin.https.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT
import retrofit2.http.Url

/**
 * Created by Administrator on 2017/8/27 0027.
 * 提供的系列接口
 */
interface ApiServer{
    @FormUrlEncoded
    @PUT
    abstract fun put(@Url url: String, @FieldMap maps: Map<String, String>): Observable<ResponseBody>
}