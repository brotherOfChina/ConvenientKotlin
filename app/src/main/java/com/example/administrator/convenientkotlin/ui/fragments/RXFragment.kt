package com.example.administrator.convenientkotlin.ui.fragments

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.ui.activities.LensActivity
import com.example.administrator.convenientkotlin.ui.adapters.DeviceAdapter
import com.videogo.openapi.EZOpenSDK
import com.videogo.openapi.bean.EZDeviceInfo
import com.vise.xsnow.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_rxzx.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

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
        rv_devices  .layoutManager = GridLayoutManager(activity, 5)
        loadData()
    }

    override fun bindEvent() {
    }
    private fun loadData() = async(UI) {
        EZOpenSDK.getInstance().setAccessToken("at.6jota6qx699vutle2jwam14scxtiylqc-9rgk0you04-0cbo99c-laufedsbj")
        val result = bg {
            EZOpenSDK.getInstance().getDeviceList(0, 20)
        }
        updateUI(result.await())
    }

    private fun updateUI(devives: List<EZDeviceInfo>) {
        val deviceAdapter: DeviceAdapter by lazy {
            DeviceAdapter(devives ) {
                with(it) {
                    val intent = Intent(activity, LensActivity::class.java)
                    intent.putExtra("data", it)
                    startActivity(intent)
                }
            }
        }
        rv_devices.adapter = deviceAdapter
    }
}
