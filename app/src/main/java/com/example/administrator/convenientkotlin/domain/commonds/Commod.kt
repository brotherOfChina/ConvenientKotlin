package com.example.administrator.convenientkotlin.domain.commonds

/**
 * Created by Administrator on 2017/8/27 0027.
 * 接口命令
 */
interface Commod<out T>{
    fun execute():T
}