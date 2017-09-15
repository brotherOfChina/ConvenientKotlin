package com.example.administrator.convenientkotlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.ui.adapters.LensAdapter
import com.videogo.openapi.bean.EZDeviceInfo
import com.vise.log.ViseLog
import com.vise.xsnow.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_lens.*

class LensActivity : BaseActivity() {
    override fun bindEvent() {
    }

    override fun initView() {



    }

    override fun initData() {
    }

    override fun processClick(view: View?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //取消状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_ysy)
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
                    intent.putExtra("cameraNo",it)
                    intent.putExtra("deviceSerial",it)
                    startActivity(intent)

                }

            }
        }
        rv_lens.adapter=adapter

    }
}
