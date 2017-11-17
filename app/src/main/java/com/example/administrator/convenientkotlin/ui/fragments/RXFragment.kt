package com.example.administrator.convenientkotlin.ui.fragments

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.YsyBean
import com.example.administrator.convenientkotlin.ui.activities.LensActivity
import com.example.administrator.convenientkotlin.ui.adapters.DeviceAdapter
import com.videogo.openapi.EZOpenSDK
import com.videogo.openapi.bean.EZDeviceInfo
import com.vise.log.ViseLog
import com.vise.xsnow.http.ViseHttp
import com.vise.xsnow.http.callback.ACallback
import com.vise.xsnow.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_rxzx.*
import java.util.*

/**
 * Created by Administrator on 2017/9/4 0004.
 * 融信在线fragment
 */
class RXFragment : BaseFragment() {
    override fun initData() {
    }

    override fun getLayoutID(): Int = R.layout.fragment_rxzx

    override fun processClick(view: View?) {
    }

    override fun initView(contentView: View?) {
        rv_devices.layoutManager = GridLayoutManager(activity, 5)
        requestToken()
    }

    private fun requestToken() {
        val map = HashMap<String, String>()
        map.put("appKey", "16c4d77101c8406c8a207b0dd339839c")
        map.put("appSecret", "993336fe7aa09017ee9dcd3a10c8c979")
        ViseHttp.POST().baseUrl("https://open.ys7.com/api/lapp/token/get/").addParams(map)
                .request(object : ACallback<YsyBean>() {
                    override fun onSuccess(data: YsyBean) {
                        loadData(data.data.accessToken)
                        ViseLog.d(data.data.accessToken)
                    }

                    override fun onFail(errCode: Int, errMsg: String) {

                    }
                })
    }

    override fun bindEvent() {
    }

    private fun loadData(token: String) {

        EZOpenSDK.getInstance().setAccessToken(token)
        Thread({
           val result= EZOpenSDK.getInstance().getDeviceList(0, 20)
            if (result!=null){
                 updateUI(result)
            }
        }).start()
    }
    private fun updateUI(devives: List<EZDeviceInfo>) {
        activity.runOnUiThread {
            val deviceAdapter: DeviceAdapter by lazy {
                DeviceAdapter(devives) {
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
}
