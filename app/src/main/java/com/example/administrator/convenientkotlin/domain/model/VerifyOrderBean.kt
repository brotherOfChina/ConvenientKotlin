package com.example.administrator.convenientkotlin.domain.model

/**
 * Created by Administrator on 2017/8/2.
 */

data class VerifyOrderBean (val audit_store_id: String,
                            val audit_userid: String,
                            val order_type: String,
                            val order_no: String,
                            val store_id: String,
                            val userid: String,
                            val username: String,
                            val coupon_data: CouponDataBean,
                            val real_amount: String,
                            val total_amount: String,
                            val total_num: String,
                            val ctime: String,
                            val goods_data: List<GoodsDataBean> )



   data class CouponDataBean(  val coupon_type: String,
                               val full_amount: String,
                               val reduce_amount: String)

    data class GoodsDataBean (val item_id: String,
                              val goods_id: String,
                              val goods_sn: String,
                              val goods_name: String,
                              val goods_price: String,
                              val goods_img: String,
                              val goods_num: String,
                              val goods_sett_price: String)



