package com.example.administrator.convenientkotlin.ui.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.domain.model.NavBean
import com.example.administrator.convenientkotlin.domain.model.ResponseNavBean
import com.example.administrator.convenientkotlin.ui.adapters.FrgmentAdapter
import com.example.administrator.convenientkotlin.ui.adapters.NavAdapter
import com.example.administrator.convenientkotlin.ui.fragments.GoodsFragment
import com.example.administrator.convenientkotlin.ui.fragments.RXFragment
import com.example.administrator.convenientkotlin.ui.fragments.TypeFragment
import com.example.administrator.convenientkotlin.ui.fragments.VertifyFregment
import com.example.administrator.convenientkotlin.utils.SignUtil
import com.vise.log.ViseLog
import com.vise.xsnow.http.ViseHttp
import com.vise.xsnow.http.callback.ACallback
import com.vise.xsnow.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun bindEvent() {
        //请求数据所用的参数，sign将用一个通用方法进行添加
        val map = mutableMapOf<String, String>()
        map.put("c", "Nav")
        map.put("m", "Screen")
        map.put("v", "CV1")

        map.put("sign", SignUtil.getSignString(map))
        ViseLog.i(map)
        ViseHttp.POST().addParams(map)
                .request(object : ACallback<ResponseNavBean<NavBean>>() {
                    override fun onSuccess(data: ResponseNavBean<NavBean>?) {
                        if (data != null) {
                            val nav = data.data
                            rv_nav.adapter = NavAdapter(nav) {
                               when{
                                   it.nav_id == "2" ->vp_content.currentItem=1
                                   it.nav_id == "3" ->vp_content.currentItem=0
                                   it.nav_id == "4" ->vp_content.currentItem=2
                                   it.nav_id == "5" ->vp_content.currentItem=3
                               }
                            }
                        }
                    }

                    override fun onFail(errCode: Int, errMsg: String?) {
                       ViseLog.i(errMsg+errCode)
                    }


                })

    }
    override fun initView() {
        val fragmentList = listOf<Fragment>(
                RXFragment() ,TypeFragment() , GoodsFragment(), VertifyFregment()
        )
        vp_content.adapter = FrgmentAdapter(supportFragmentManager, fragmentList)
        vp_content.currentItem = 1
        vp_content.offscreenPageLimit=4
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
        setContentView(R.layout.activity_main)
        rv_nav.layoutManager=LinearLayoutManager(this)

    }

    override fun onResume() {
        super.onResume()
    }

}
