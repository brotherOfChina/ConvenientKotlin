package com.example.administrator.convenientkotlin.domain.commands

/**
 * Created by Administrator on 2017/8/27 0027.
 * 接口命令
 */
interface Command<out T>{
    fun execute():T
}
interface GoodsResult< T>{
    fun onGoodsResult(result:T)
}


