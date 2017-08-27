package com.example.administrator.convenientkotlin.domain.model

/**
 * Created by Administrator on 2017/8/27 0027.
 */
data class GoodsNavBean(val category_id: String,

                        val category_name: String,

                        val m_timestamp: String)

data class GoodsNavList(val status: Int, val mag: String, val goodsNavList: List<GoodsNavBean>)