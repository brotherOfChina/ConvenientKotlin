package com.example.administrator.convenientkotlin.ui.fragments

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.data.Server.RequestApi
import com.example.administrator.convenientkotlin.domain.commands.GoodsResult
import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.domain.model.ResponseBean
import com.example.administrator.convenientkotlin.domain.model.TypeBean
import com.example.administrator.convenientkotlin.ui.adapters.GoodsAdapter
import com.example.administrator.convenientkotlin.ui.adapters.TypeAdapter
import com.example.administrator.convenientkotlin.ui.dialog.GoodDialog
import com.example.administrator.convenientkotlin.utils.RequestCallBack
import com.vise.xsnow.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_type.*

/**
 * Created by Administrator on 2017/9/4 0004.
 * 分类fragment
 * c:List
m:Screen
sign:c56bb05340cfc3ae006945cc1520ace3
nav_id:2
v:CV1
 */
class TypeFragment : BaseFragment(),GoodsResult<ResponseBean<GoodBean>> {
    override fun onGoodsResult(result: ResponseBean<GoodBean>) {

    }

    override fun bindEvent() {
    }

    override fun initData() {
        tv_back.visibility=View.INVISIBLE

        rv_type.layoutManager = GridLayoutManager(activity, 4)
        RequestApi.executeType(object : RequestCallBack<ResponseBean<TypeBean>>() {
            override fun success(data: ResponseBean<TypeBean>) {
                val adapter: TypeAdapter by lazy {

                    TypeAdapter(data.data.list) {
                        with(it) {

                            requestGood(category_id)
                        }
                    }
                }
                rv_type.adapter = adapter
            }
        })
    }

    private fun requestGood(category_id: String) {
        tv_back.visibility=View.VISIBLE
        RequestApi.executeGood(category_id, object : RequestCallBack<ResponseBean<GoodBean>>() {
            override fun success(data: ResponseBean<GoodBean>) {
                val adapter: GoodsAdapter by lazy {
                    GoodsAdapter(data.data.list) {
                        GoodDialog(context, it).show()
                    }
                }
                rv_type.layoutManager = GridLayoutManager(activity, 5)
                rv_type.adapter = adapter
            }
        })
    }

    override fun getLayoutID(): Int = R.layout.fragment_type

    override fun processClick(view: View?) {
    }

    override fun initView(contentView: View?) {

        tv_back.setOnClickListener { initData() }
    }

}
