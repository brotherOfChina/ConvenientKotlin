package com.example.administrator.convenientkotlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.ui.adapters.DeviceAdapter
import com.videogo.openapi.EZOpenSDK
import com.videogo.openapi.bean.EZDeviceInfo
import com.vise.log.ViseLog
import com.vise.xsnow.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_ysy.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class YSYActivity : BaseActivity() {

    val RESULT_CODE = 101
    val REQUEST_CODE = 100
    override fun bindEvent() {

    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun processClick(view: View?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //取消状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_ysy)

        rv_device.layoutManager = GridLayoutManager(this, 5)
//        val devices = mutableListOf<EZDeviceInfo>()

//        devices.addAll(EZOpenSDK.getInstance().getDeviceList(0, 10))
//        devices.addAll(EZOpenSDK.getInstance().getSharedDeviceList(0, 10))
        loadData()
        btn_back.setOnClickListener {
            ViseLog.i("关闭")
            finish()
        }

    }

    private fun loadData() = async(UI) {
        val result = bg {
            EZOpenSDK.getInstance().getDeviceList(0, 10)
        }
        updateUI(result.await())
    }

    private fun updateUI(devives: List<EZDeviceInfo>) {
        val deviceAdapter: DeviceAdapter by lazy {
            DeviceAdapter(devives ) {
                with(it) {
                    val intent = Intent(this@YSYActivity, LensActivity::class.java)
                    intent.putExtra("data", it)
                    startActivity(intent)
                }
            }
        }
        rv_device.adapter = deviceAdapter
    }
}
