package com.example.administrator.convenientkotlin.domain.model

/**
 * Created by Administrator on 2017/9/1 0001.
 * 返回值实例
 *
 */
data class ResponseBean< T>(val status: Int, val msg: String, val data: DataBean<T>)
data class ResponseNavBean< T>(val status: Int, val msg: String, val data: List<T>)
data class ResponseDataBean<T>(val status: Int, val msg: String, val data: T)
data class ResponseData<T>(val status: String, val msg: String, val data: T)
data class VersionBean(val type: String,val vison:String,val url:String)
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
data class YsyBean(val code:String,
                   val msg:String,
                   val data:YsyDataBean)
data class YsyDataBean(val accessToken:String,
                       val expireTime:String)
data class User(val userid: String,val truename:String)
data class Store(val store_id:String,val  store_name:String)
data class Version(val url:String)