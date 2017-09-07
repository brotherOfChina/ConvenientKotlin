package com.example.administrator.convenientkotlin.data.Server

import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.domain.model.ResponseBean
import com.example.administrator.convenientkotlin.domain.model.TypeBean
import com.example.administrator.convenientkotlin.utils.MyCallBack
import com.example.administrator.convenientkotlin.utils.RequestCallBack
import com.example.administrator.convenientkotlin.utils.SignUtil
import com.vise.xsnow.http.ViseHttp


/**
 * Created by Administrator on 2017/8/27 0027.
 * 网络请求
 *
 */
class RequestApi{
    companion object {
        fun executeGood( category_id:String,callBack: RequestCallBack<ResponseBean<GoodBean>>){
            val map = mutableMapOf<String,String>()
            map.put("c", "GoodsList")
            map.put("category_id", category_id)
            map.put("m", "Screen")
            map.put("v", "CV1")
            map.put("sign",SignUtil.getSignString(map))
            ViseHttp.POST().addParams(map)
                    .request(object :MyCallBack<ResponseBean<GoodBean>>(){
                        override fun onSuccess(data: ResponseBean<GoodBean>) {
                            callBack.success(data)
                        }
                    })
        }
        fun  executeType(callBack: RequestCallBack<ResponseBean<TypeBean>>){
            val map= mutableMapOf<String,String>()
            map.put("c","List")
            map.put("m","Screen")
            map.put("nav_id","2")
            map.put("v","CV1")
            map.put("sign",SignUtil.getSignString(map))
            ViseHttp.POST().addParams(map)
                    .request(object :MyCallBack<ResponseBean<TypeBean>>(){
                        override fun onSuccess(data: ResponseBean<TypeBean>) {
                            callBack.success(data)
                        }
                    })
        }
    }


}