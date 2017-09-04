package com.example.administrator.convenientkotlin.ui.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.domain.model.ResponseBean
import com.example.administrator.convenientkotlin.utils.SignUtil
import com.vise.log.ViseLog
import com.vise.xsnow.http.ViseHttp
import com.vise.xsnow.http.callback.ACallback
import com.vise.xsnow.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_goods.*

/**
 * Created by Administrator on 2017/9/4 0004.
 * 商品fragment
 */
class GoodsFragment :BaseFragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun bindEvent() {
        rv_goods.layoutManager=LinearLayoutManager(activity)

        val map = mutableMapOf<String,String>()
        map.put("c", "GoodsList")
        map.put("category_id", "6")
        map.put("m", "Screen")
        map.put("v", "CV1")
        map.put("sign",SignUtil.getSignString(map))
        ViseLog.i(map)
        ViseHttp.POST().addParams(map)
                .request(object :ACallback<ResponseBean<GoodBean>>(){
                    override fun onFail(errCode: Int, errMsg: String?) {
                        ViseLog.i(errMsg)

                    }

                    override fun onSuccess(data: ResponseBean<GoodBean>?) {
                        ViseLog.i(data)

//                        if (data!=null){
//                            rv_goods.adapter=GoodsAdapter(data.data){
//                                GoodDialog(context,it).show()
//                            }
//
//                        }
                    }

                })
    }

    override fun initData() {
    }

    override fun getLayoutID(): Int = R.layout.fragment_goods

    override fun processClick(view: View?) {
    }

    override fun initView(contentView: View?) {
    }
}