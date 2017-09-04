package com.example.administrator.convenientkotlin.domain.commands

import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.domain.model.NavBean
import com.example.administrator.convenientkotlin.domain.model.ResponseBean
import com.example.administrator.convenientkotlin.domain.model.TypeBean

/**
 * Created by Administrator on 2017/8/27 0027.
 * 接口命令
 */
interface Command<out T>{
    fun execute():T
}
class RequestType():Command<ResponseBean<TypeBean>>{
    override fun execute(): ResponseBean<TypeBean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
class RequestGoods():Command<ResponseBean<GoodBean>>{
    override fun execute(): ResponseBean<GoodBean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
class RequestNav():Command<ResponseBean<NavBean>>{
    override fun execute(): ResponseBean<NavBean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}