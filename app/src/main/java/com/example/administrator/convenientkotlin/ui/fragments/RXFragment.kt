package com.example.administrator.convenientkotlin.ui.fragments

import android.view.View
import com.example.administrator.convenientkotlin.R
import com.videogo.openapi.EZOpenSDK
import com.vise.xsnow.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_rxzx.*

/**
 * Created by Administrator on 2017/9/4 0004.
 * 融信在线fragment
 */
class RXFragment :BaseFragment(){
    override fun initData() {
    }

    override fun getLayoutID(): Int = R.layout.fragment_rxzx

    override fun processClick(view: View?) {
    }

    override fun initView(contentView: View?) {
       tv_rx.setOnClickListener {
           EZOpenSDK.getInstance().openLoginPage()
       }
    }

    override fun bindEvent() {
    }

}
