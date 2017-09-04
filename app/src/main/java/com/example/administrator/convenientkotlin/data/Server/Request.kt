package com.example.administrator.convenientkotlin.data.Server

import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.domain.model.ResponseBean
import com.vise.xsnow.http.ViseHttp
import com.vise.xsnow.http.callback.ACallback


/**
 * Created by Administrator on 2017/8/27 0027.
 * 网络请求
 *
 */
class Request(val map:Map<String,String> ){
    fun excuteGoods(){
        ViseHttp.POST().addParams(map)
                .request(object : ACallback<ResponseBean<GoodBean>>() {
                    override fun onFail(errCode: Int, errMsg: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onSuccess(data: ResponseBean<GoodBean>?) {
                      setData<GoodBean>(data)
                    }


                })


    }
    fun <T> setData(data: ResponseBean<GoodBean>?): ResponseBean<GoodBean>? = data
}