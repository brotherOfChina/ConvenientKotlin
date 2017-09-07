package com.example.administrator.convenientkotlin.utils

import com.vise.log.ViseLog
import com.vise.xsnow.http.callback.ACallback

/**
 * Created by Administrator on 2017/9/5 0005.
 * 接口请求的回掉接口
 */
 abstract class RequestCallBack<T>{

     abstract fun success(data:T)
}
abstract class MyCallBack<T> : ACallback<T>() {
    abstract override fun onSuccess(data: T)

    override fun onFail(errCode: Int, errMsg: String?) {
        ViseLog.e("errCode:$errCode\n errMsg:$errMsg")
    }

}