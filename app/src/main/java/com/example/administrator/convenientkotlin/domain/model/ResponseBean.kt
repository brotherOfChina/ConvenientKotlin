package com.example.administrator.convenientkotlin.domain.model

/**
 * Created by Administrator on 2017/9/1 0001.
 * 返回值实例
 *
 */
data class ResponseBean< T>(val status: Int, val msg: String, val data: DataBean<T>)
data class ResponseNavBean< T>(val status: Int, val msg: String, val data: List<T>)
data class ResponseDataBean(val status: Int, val msg: String, val data: VerifyOrderBean)


data class NavBean(val internal: String,
                   val nav_id: String,
                   val nav_type: String,
                   val nav_name: String,
                   val icon: String,

                   val m_timestamp: String,
                   val is_default: String,
                   val is_layout: String,
                   val category_id: String)
data class DataBean<T>(val list:List<T>)
data class GoodBean(val content_id: String,
                    val name: String,
                    val price: String,
                    val image_default: String,
                    val userid: String,
                    val mtime: Long,
                    val memo: String,
                    val Qrcode: String)

data class TypeBean(val category_id: String,
                    val category_name: String,
                    val icon: String)