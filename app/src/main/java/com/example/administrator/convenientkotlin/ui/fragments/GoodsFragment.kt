package com.example.administrator.convenientkotlin.ui.fragments

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
class GoodsFragment : BaseFragment() ,GoodsResult<ResponseBean<GoodBean>>{
    override fun onGoodsResult(result: ResponseBean<GoodBean>) {
        rv_goods.adapter = GoodsAdapter(result.data.list) {
            GoodDialog(context, it).show()

        }
    }


    override fun bindEvent() {
        tv_title.text="促销商品"
      getGoods("6",this)

    }

    override fun initData() {

    }

    override fun getLayoutID(): Int = R.layout.fragment_goods

    override fun processClick(view: View?) {
    }

    override fun initView(contentView: View?) {
    }
}