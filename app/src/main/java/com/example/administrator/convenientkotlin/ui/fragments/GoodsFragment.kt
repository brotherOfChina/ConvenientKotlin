package com.example.administrator.convenientkotlin.ui.fragments

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.commands.GoodsResult
import com.example.administrator.convenientkotlin.domain.dataSource.getGoods
import com.example.administrator.convenientkotlin.domain.model.GoodBean
import com.example.administrator.convenientkotlin.domain.model.ResponseBean
import com.example.administrator.convenientkotlin.ui.adapters.GoodsAdapter
import com.example.administrator.convenientkotlin.ui.dialog.GoodDialog
import com.vise.xsnow.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_goods.*

/**
 * Created by Administrator on 2017/9/4 0004.
 * 商品fragment
 */
class GoodsFragment : BaseFragment(), GoodsResult<ResponseBean<GoodBean>> {

    override fun onGoodsResult(result: ResponseBean<GoodBean>) {
        val adapter: GoodsAdapter by lazy {
            GoodsAdapter(result.data.list) {
                GoodDialog(context, it).show()
            }
        }
        rv_goods.adapter = adapter
    }


    override fun bindEvent() {
        tv_title.text = "促销商品"
        rv_goods.layoutManager = GridLayoutManager(activity, 4)
        getGoods("6", this)

    }

    override fun initData() {

    }

    override fun getLayoutID(): Int = R.layout.fragment_goods

    override fun processClick(view: View?) {
    }

    override fun initView(contentView: View?) {
    }
}