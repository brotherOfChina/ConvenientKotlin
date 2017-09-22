package com.example.administrator.convenientkotlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.example.administrator.convenientkotlin.R
import com.example.administrator.convenientkotlin.base.MyApplication
import com.example.administrator.convenientkotlin.domain.model.BuyData
import com.example.administrator.convenientkotlin.domain.model.NavBean
import com.example.administrator.convenientkotlin.domain.model.ResponseNavBean
import com.example.administrator.convenientkotlin.extensions.DelegatesExt
import com.example.administrator.convenientkotlin.extensions.getName
import com.example.administrator.convenientkotlin.extensions.hidePhone
import com.example.administrator.convenientkotlin.ui.adapters.FrgmentAdapter
import com.example.administrator.convenientkotlin.ui.adapters.NavAdapter
import com.example.administrator.convenientkotlin.ui.dialog.ListDialog
import com.example.administrator.convenientkotlin.ui.fragments.GoodsFragment
import com.example.administrator.convenientkotlin.ui.fragments.RXFragment
import com.example.administrator.convenientkotlin.ui.fragments.TypeFragment
import com.example.administrator.convenientkotlin.ui.fragments.VerifyFragment
import com.example.administrator.convenientkotlin.utils.SignUtil
import com.vise.log.ViseLog
import com.vise.xsnow.http.ViseHttp
import com.vise.xsnow.http.callback.ACallback
import com.vise.xsnow.ui.BaseActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {
    val m_store_id :String by DelegatesExt.preference(MyApplication.instance,UserActivity.STORE_ID, UserActivity.D_STORE_ID)
    val m_store_name :String by DelegatesExt.preference(MyApplication.instance,UserActivity.STORE_NAME, UserActivity.D_STORE_NAME)



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
                                when {
                                    it.nav_id == "2" -> vp_content.currentItem = 1
                                    it.nav_id == "3" -> vp_content.currentItem = 0
                                    it.nav_id == "4" -> vp_content.currentItem = 2
                                    it.nav_id == "5" -> vp_content.currentItem = 3
                                }
                            }
                        }
                    }

                    override fun onFail(errCode: Int, errMsg: String?) {
                        ViseLog.i(errMsg + errCode)
                    }

                })

    }

    override fun initView() {

        store_name.text = "华都店"
        val names = mapOf(
                "华都店" to "64259",
                "迎宾西街店" to "76620",
                "颐景店" to "2342",
                "东阳店" to "1888",
                "新华店" to "93044"
        )
        val storeDialog: ListDialog by lazy {
            ListDialog(this, names)
        }
        store_name.setOnClickListener {
//            storeDialog.show()

//            ViseLog.i(user_id)
            startActivity(Intent(this@MainActivity,UserActivity::class.java))
        }
        val fragmentList = listOf<Fragment>(
                RXFragment(), TypeFragment(), GoodsFragment(), VerifyFragment()
        )
        vp_content.adapter = FrgmentAdapter(supportFragmentManager, fragmentList)
        vp_content.currentItem = 1
        vp_content.offscreenPageLimit = 4
    }

    fun getBuyData(store_id: String) {
        val currentTime = System.currentTimeMillis() / 1000
        val startTime = System.currentTimeMillis() / 1000-24*60*60*7
        /**
         * v:CV1
        m:Order
        c:List
        store_id:1
        rec_type:1
        s_time:1499999520
        e_time:1505183520
        page:1
        perpage:2
        sign:96d72983c955cc5a310e673c07b75328
         */
        val map = mutableMapOf<String, String>()
        map.put("v", "CV1")
        map.put("m", "Order")
        map.put("c", "List")
        map.put("store_id", store_id)
        map.put("rec_type", "1")
        map.put("s_time", "$startTime")
        map.put("e_time", "$currentTime")
        map.put("page", "1")
        map.put("perpage", "10")
        map.put("sign", SignUtil.getSignString(map))
        ViseLog.i(map)
        ViseHttp.POST().addParams(map).request(object : ACallback<BuyData>() {
            override fun onSuccess(data: BuyData?) {
                ViseLog.i(data)
                if (data?.status == 0) {
                    for (bean in data.data.list) {
                        val ll_content: View by lazy {
                            View.inflate(mContext, R.layout.adapter_flipper, null)
                        }
                        val tv:TextView = ll_content.findViewById(R.id.tv_data) as TextView
                        var s = StringBuffer()
                        for (item in bean.items) {
                            s = s.append("“" + item.goods_name + "”*" + item.goods_num)
                        }
                        tv.text = "用户" + bean.phone.hidePhone() + "购买了:" + s
                        vp_buy_data.addView(ll_content)
                    }
                    vp_buy_data.animation
                    vp_buy_data.isAutoStart = true
                    vp_buy_data.startFlipping()
                }
                Observable.create(ObservableOnSubscribe<Int> { emitter ->
                    Thread.sleep(60000*10)
                    emitter.onNext(1)
                }).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            getBuyData(m_store_id)
                        })
            }

            override fun onFail(errCode: Int, errMsg: String?) {
                Observable.create(ObservableOnSubscribe<Int> { emitter ->
                    Thread.sleep(60000*10)
                    emitter.onNext(1)
                }).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            getBuyData(m_store_id)
                        })
                toast(errMsg+"")
            }
        })
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
        rv_nav.layoutManager = LinearLayoutManager(this)
//        getBuyData(m_store_id)

    }

    override fun onResume() {
        super.onResume()
        getBuyData(m_store_id)
        store_name.text=m_store_name.getName()
    }

}
