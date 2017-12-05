package com.example.administrator.convenientkotlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.base.MyApplication
import com.example.administrator.convenientkotlin.extensions.DelegatesExt
import com.example.administrator.convenientkotlin.ui.adapters.LensAdapter
import com.videogo.openapi.bean.EZDeviceInfo
import com.vise.log.ViseLog
import com.vise.xsnow.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_lens.*

class LensActivity : BaseActivity() {
    override fun bindEvent() {
    }

    override fun initData() {
    }

    override fun processClick(view: View?) {
    }

    companion object {
        val URL_Top="lens_url_top"
        val DEFAULT_URL_TOP="ezopen://open.ys7.com/813756259/2.hd.live"
        val URL_BOTTOM="lens_url_bottom"
        val DEFAULT_URL_BOTTOM="ezopen://open.ys7.com/715570205/1.hd.live"
        val ACCESS_TOKEN="access_token"
        val DEFAULT_ACCESS_TOKEN="0"
        val STORE_NAME_TOP="STORE_NAME_TOP"
        val STORE_NAME_BOTTOM="STORE_NAME_BOTTOM"
    }
    var url : String by DelegatesExt.preference(MyApplication.instance, URL_Top, DEFAULT_URL_TOP)
    var accessToken:String by DelegatesExt.preference(MyApplication.instance, ACCESS_TOKEN, DEFAULT_ACCESS_TOKEN)


    override fun initView() {

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //取消状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lens)
        rv_lens.layoutManager= GridLayoutManager(this,4)
        btn_back.setOnClickListener{
            finish()
        }

        val e:EZDeviceInfo=intent.getParcelableExtra("data")
        ViseLog.i(e)
        val adapter :LensAdapter by lazy {
            LensAdapter(e.cameraInfoList){
                with(it){
                    val intent=Intent(this@LensActivity,PlayActivity::class.java)
                    url = "ezopen://open.ys7.com/$deviceSerial/$cameraNo.hd.live"
                    ViseLog.d(url)
                    intent.putExtra("cameraNo",it)
                    intent.putExtra("deviceSerial",it)
                    startActivity(intent)

                }

            }
        }
        rv_lens.adapter=adapter

    }
}
