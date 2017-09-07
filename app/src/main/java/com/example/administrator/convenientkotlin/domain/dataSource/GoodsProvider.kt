package com.example.administrator.convenientkotlin.domain.dataSource

import com.example.administrator.convenientkotlin.data.Server.RequestApi
import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.domain.model.ResponseBean
import com.example.administrator.convenientkotlin.ui.fragments.GoodsFragment
import com.example.administrator.convenientkotlin.utils.RequestCallBack

/**
 * Created by Administrator on 2017/9/7 0007.
 * 商品列表数据
 */
fun getGoods(category_id: String,   fragment:GoodsFragment ){
    RequestApi.executeGood(category_id,object :RequestCallBack<ResponseBean<GoodBean>>(){
        override fun success(data: ResponseBean<GoodBean>) {
            fragment.onGoodsResult(data)
        }
    })
}
