package com.example.administrator.convenientkotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import com.example.administrator.convenientkotlin.base.MyApplication

/**
 * 作者：Tangren on 2017/5/24 13:40
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
class NetWorkUtil {
    companion object {
        fun isNetWorkConnected(): Boolean {
            val cm = MyApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val currentNet = cm.activeNetworkInfo ?: return false
            return currentNet.isAvailable
        }
    }

}