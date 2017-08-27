package com.example.administrator.convenientkotlin.domain.model

/**
 * Created by Administrator on 2017/8/27 0027.
 */
data class GoodsBean
(var content_id: String,

 var name: String,

 var price: String,

 var image_default: String,

 var userid: String,

 var mtime: Int = 0,

 var memo: String,

 var Qrcode: String)
data class GoodsList(val status:Int ,val mag :String,val goodsList: List<GoodsBean>)